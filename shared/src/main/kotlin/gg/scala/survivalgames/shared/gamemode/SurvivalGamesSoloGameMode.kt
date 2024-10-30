package gg.scala.survivalgames.shared.gamemode

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.survivalgames.shared.SurvivalGamesMap
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

    private val maps by lazy {
        listOf(SurvivalGamesAlaskanVillage)
    }

    fun getSGMaps() = maps
    override fun getArenas() = maps


    override fun getMaterial() = Pair(Material.FISHING_ROD, 0)

    override fun getDescription() = "${CC.GRAY}A solo game of Survival Games!"

    override fun getTeamSize() = 1 // team size
    override fun getMaxTeams() = 32 // max teams
}
