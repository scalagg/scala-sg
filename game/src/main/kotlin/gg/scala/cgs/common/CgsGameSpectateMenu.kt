package gg.scala.cgs.common

import gg.scala.cgs.common.teams.CgsGameTeamService
import gg.scala.survivalgames.game.sponsors.SurvivalGamesSponsorService
import gg.scala.survivalgames.game.sponsors.menu.SponsorMenu
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
// Overwrite other SpectateMenu to support sponsors
class CgsGameSpectateMenu : PaginatedMenu()
{
    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf<Int, Button>().also { buttons ->
            CgsGameTeamService.teams.values.forEach { team ->
                team.alive.mapNotNull {
                    Bukkit.getPlayer(it)
                }.forEach second@{
                    if (it.hasMetadata("spectator"))
                    {
                        return@second
                    }

                    buttons[buttons.size] = SpectateButton(it)
                }
            }
        }
    }

    override fun getPrePaginatedTitle(player: Player) = "Spectate Menu"

    inner class SpectateButton(
        private val player: Player
    ) : Button()
    {
        override fun getButtonItem(player: Player): ItemStack
        {
            val builder = ItemBuilder(Material.SKULL_ITEM)
                .owner(this.player.name)
                .name(this.player.displayName)
                .data(3)

            if (!SurvivalGamesSponsorService.sponsorsLock.containsKey(player.uniqueId))
            {
                builder.addToLore(
                    "${CC.YELLOW}Left-Click to teleport.",
                    "${CC.YELLOW}Right-Click to sponsor."
                )
            } else {
                builder.addToLore("${CC.YELLOW}Click to teleport.")
            }
            return builder.build()
        }

        override fun clicked(
            player: Player, slot: Int,
            clickType: ClickType, view: InventoryView
        )
        {
            if (SurvivalGamesSponsorService.sponsorsLock.containsKey(player.uniqueId)) {
                player.teleport(this.player)
                player.sendMessage("${CC.GREEN}You've been teleported to ${CC.ID_GREEN}${this.player.displayName}${CC.GREEN}.")
            } else {

                if (clickType.isLeftClick) {
                    player.teleport(this.player)
                    player.sendMessage("${CC.GREEN}You've been teleported to ${CC.ID_GREEN}${this.player.displayName}${CC.GREEN}.")
                } else if (clickType.isRightClick) {
                    SponsorMenu(this.player).openMenu(player)
                }
            }
        }
    }
}