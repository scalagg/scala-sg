package gg.scala.survivalgames.shared

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.information.arena.CgsGameArena
import net.evilblock.cubed.util.bukkit.cuboid.Cuboid
import org.bukkit.Bukkit
import org.bukkit.Location

/**
 * @author Topu
 * @date 10/30/2024
 */
interface SurvivalGamesMap : CgsGameArena
{

    fun getXMaterialIcon(): XMaterial
}