package gg.scala.survivalgames.shared.arena

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.information.arena.CgsGameArena
import gg.scala.survivalgames.shared.SurvivalGamesMap
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.io.File
import java.nio.file.Path

/**
 * @author Topu
 * @date 10/30/2024
 */
object SurvivalGamesValleyside : SurvivalGamesMap
{
 override fun getId() = "valleyside"
 override fun getName() = "Valleyside"

 override fun getMaterial() = Pair(Material.GRASS, 0)
 override fun getDescription() = "Sunny!"

 override fun getDirectory(): Path? =
  File("plugins${File.separator}maps${File.separator}valleyside")
   .toPath()

 override fun getXMaterialIcon() = XMaterial.GRASS_BLOCK

 override fun getBukkitWorldName() = "valleyside"

 override fun getPreLobbyLocation() = Location(
  Bukkit.getWorld("valleyside"),
  257.500, 78.000, 256.500
 )

 override fun getSpectatorLocation() = Location(
  Bukkit.getWorld("valleyside"),
  257.500, 100.000, 256.500
 )
}