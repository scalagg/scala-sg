package gg.scala.survivalgames.game.sponsors

import gg.scala.commons.config.convert.annotations.ConfigConverted
import gg.scala.commons.config.convert.annotations.ConfigFileName
import gg.scala.survivalgames.game.util.EffectType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
@ConfigConverted("")
@ConfigFileName("sponsors.json")
class SurvivalGamesSponsorConfig
{

    var sponsorCoinMulti: Int = 2

    var sponsorEffects = mutableListOf(
        SponsorEffect(Material.FEATHER, EffectType.SPEED)
    )

    var sponsorItems = mutableListOf(
        SponsorItem(ItemStack(Material.GOLDEN_APPLE), 25)
    )
}