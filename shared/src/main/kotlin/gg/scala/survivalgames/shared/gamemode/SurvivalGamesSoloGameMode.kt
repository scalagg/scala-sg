package gg.scala.survivalgames.shared.gamemode

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.survivalgames.shared.arena.SurvivalGamesAlaskanVillage
import gg.scala.survivalgames.shared.arena.SurvivalGamesValleyside
import net.evilblock.cubed.util.CC
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object SurvivalGamesSoloGameMode : CgsGameMode
{
    override fun getId() = "solo"
    override fun getName() = "Solo"

    override fun getMaterial() = Pair(Material.FISHING_ROD, 0)

    override fun getDescription() = "${CC.GRAY}A solo game of Survival Games!"

    override fun getArenas() = listOf(
        SurvivalGamesAlaskanVillage,
        SurvivalGamesValleyside
    )

    override fun getTeamSize() = 1 // team size
    override fun getMaxTeams() = 32 // max teams
}
