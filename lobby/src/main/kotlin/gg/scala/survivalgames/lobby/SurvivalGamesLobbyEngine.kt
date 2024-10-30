package gg.scala.survivalgames.lobby

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.instance.CgsServerInstance
import gg.scala.cgs.lobby.gamemode.CgsGameLobby
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.cgs.lobby.updater.CgsGameInfoUpdater
import gg.scala.survivalgames.lobby.scoreboard.SurvivalGamesLobbyScoreboard
import gg.scala.survivalgames.shared.SurvivalGamesCgsInfo
import gg.scala.survivalgames.shared.SurvivalGamesCgsStatistics
import gg.scala.survivalgames.shared.gamemode.SurvivalGamesSoloGameMode
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bungee.BungeeUtil
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object SurvivalGamesLobbyEngine : CgsGameLobby<SurvivalGamesCgsStatistics>(
    SurvivalGamesCgsStatistics::class
)
{
    override fun getFormattedButton(
        info: CgsServerInstance, player: Player
    ): Button
    {
        return ItemBuilder.of(XMaterial.FIRE_CHARGE)
            .name("${CC.GREEN}${info.internalServerId}")
            .addToLore(
                "${CC.SEC}Participants: ${CC.PRI}${
                    info.gameServerInfo!!.participants.size
                }",
                "${CC.SEC}Status: ${CC.PRI}${
                    info.gameServerInfo!!.state.name
                                        .lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                }",
                "",
                "${CC.SEC}Map: ${CC.PRI}${
                    info.gameServerInfo!!.arenaId
                }",
                "${CC.SEC}Mode: ${CC.PRI}${
                    info.gameServerInfo!!.gameMode.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                }",
                "",
                "${CC.GREEN}Click to spectate!"
            )
            .toButton { _, _ ->
                BungeeUtil.sendToServer(player, info.internalServerId)
            }
    }

    override fun getGameInfo() = SurvivalGamesCgsInfo

    override fun getGameModeButtons() = mutableMapOf(
        13 to ItemBuilder(Material.FISHING_ROD)
            .name("${CC.D_AQUA}Survival Games")
            .addToLore(
                "${CC.SEC}Join a game of",
                "${CC.SEC}Survival Games!",
                "",
                " ${CC.SEC}In Game: ${CC.AQUA}${CgsGameInfoUpdater.gameModeCounts["solo"]}",
                "",
                "${CC.GREEN}Click to join game!"
            )
            .toButton { player, _ ->
                val server = CgsGameInfoUpdater
                    .findAvailableServer(
                        SurvivalGamesSoloGameMode, "Survival Games"
                    )

                if (server != null)
                {
                    player!!.sendMessage("${CC.SEC}Connecting you to ${CC.PRI}${server.internalServerId}${CC.SEC}...")
                    BungeeUtil.sendToServer(player, server.internalServerId)
                } else
                {
                    player!!.sendMessage("${CC.RED}Sorry! We were unable to find a suitable server for you to join.")
                }
            },
    )

    override fun getRankingEntries(): Collection<CgsLobbyRankingEntry> {
        TODO("Not yet implemented")
    }

    override fun getScoreboardAdapter() =
        SurvivalGamesLobbyScoreboard
}
