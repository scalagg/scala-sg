package gg.scala.survivalgames.game.scoreboard

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.information.arena.CgsGameArena
import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.cgs.common.player.scoreboard.CgsGameScoreboardRenderer
import gg.scala.cgs.common.runnable.state.StartingStateRunnable
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.commons.agnostic.sync.ServerSync
import gg.scala.lemon.LemonConstants
import gg.scala.survivalgames.game.SurvivalGamesEngine
import gg.scala.survivalgames.game.listener.SurvivalGamesListener
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.nms.MinecraftReflection.getPing
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object SurvivalGamesScoreboard : CgsGameScoreboardRenderer
{

    private val footerPadding = "${CC.GRAY} ".repeat(10)
    private var remaining = 0

    init
    {
        Tasks.asyncTimer({
            remaining = Bukkit.getOnlinePlayers()
                .filter { !it.hasMetadata("spectator") }
                .size
        }, 0L, 10L)
    }

    override fun getTitle() = "${CC.B_PRI}Survival Games"

    override fun render(lines: LinkedList<String>, player: Player, state: CgsGameState)
    {
        lines.add("")

        if (state == CgsGameState.WAITING || state == CgsGameState.STARTING)
        {
            if (state == CgsGameState.WAITING)
            {
                lines.add("${CC.WHITE}State: ${CC.PRI}Waiting")
            } else
            {
                lines.add("${CC.WHITE}Starting in ${CC.PRI}${TimeUtil.formatIntoAbbreviatedString(StartingStateRunnable.PRE_START_TIME)}")
            }

            lines.add("${CC.WHITE}Players: ${CC.PRI}${
                Bukkit.getOnlinePlayers().size
            }/${
                Bukkit.getMaxPlayers()
            }")
            lines.add("")
            lines.add("${CC.WHITE}Mode: ${CC.PRI}${
                CgsGameEngine.INSTANCE.gameMode.getName()
            }")
            lines.add("${CC.WHITE}Server: ${CC.PRI}${
                ServerSync.getLocalGameServer().id
            }")
        } else if (state.isAfter(CgsGameState.STARTED))
        {
            lines.add("${CC.WHITE}Remaining: ${CC.PRI + remaining + "/" + CgsGameEngine.INSTANCE.originalRemaining.size}")
            lines.add("${CC.WHITE}Kills: ${CC.PRI + SurvivalGamesListener.killsTracker[player.uniqueId]}")
            lines.add("")
            lines.add("${CC.WHITE}Server: ${CC.PRI}${
                ServerSync.getLocalGameServer().id
            }")
            lines.add("${CC.WHITE}Map: ${CC.PRI + CgsGameEngine.INSTANCE.gameArena!!.getName()}")
        }
        lines.add("")
        lines += "${CC.GRAY}${LemonConstants.WEB_LINK} $footerPadding"
    }
}
