package gg.scala.survivalgames.game

import xyz.mkotb.configapi.comment.Comment

/**
 * @author GrowlyX
 * @since 7/31/2022
 */
@Comment("The survival games config file.")
class SurvivalGamesConfig
{
    var deathmathPlayers = 2

    @Comment("Soup heal amount (1 unit = 0.5 hearts)")
    var soupHealAmount = 6

    @Comment("Chance of a locked compass spawning in a chest")
    var lockedCompassSpawnRate = 5

    @Comment("", "List of blocks that can be placed")
    var placeableBlocks = listOf(
        "TNT", "WEB", "CAKE", "CAKE_BLOCK",
        "WATER", "STATIONARY_WATER", "LAVA",
        "STATIONARY_LAVA", "FIREBALL"
    )

    @Comment("", "List of blocks that can be broken")
    var breakableBlocks = listOf(
        "LONG_GRASS", "LEAVES", "RED_FLOWER", "BROWN_MUSHROOM",
        "RED_MUSHROOM", "VINE", "WATER_LILY", "THIN_GLASS",
        "STAINED_GLASS_PANE", "SNOW", "DOUBLE_PLANT",
        "CAKE_BLOCK", "WEB", "TNT"
    )

    @Comment("", "List of items for chest loot (material, amount, enchantment, enchantment level)")
    var chestItems = listOf(
        "WOOD_SWORD,1,null,0",
        "STONE_SWORD,1,null,0",
        "BOW,1,null,0",
        "IRON_HELMET,1,null,0",
        "IRON_CHESTPLATE,1,null,0",
        "IRON_BOOTS,1,null,0",
        "IRON_LEGGINGS,1,null,0",
        "IRON_SWORD,1,null,0",
        "CHAINMAIL_CHESTPLATE,1,null,0",
        "CHAINMAIL_HELMET,1,null,0",
        "CHAINMAIL_LEGGINGS,1,null,0",
        "CHAINMAIL_BOOTS,1,null,0",
        "LEATHER_HELMET,1,null,0",
        "LEATHER_CHESTPLATE,1,null,0",
        "LEATHER_LEGGINGS,1,null,0",
        "LEATHER_BOOTS,1,null,0",
        "GOLDEN_APPLE,1,null,0",
        "APPLE,1-9,null,0",
        "CARROT,1-9,null,0",
        "PUMPKIN_PIE,1-9,null,0",
        "POTATO,1-5,null,0",
        "RAW_FISH,1-9,null,0",
        "COOKIE,1-15,null,0",
        "PORK,1-9,null,0",
        "MUSHROOM_SOUP,1,null,0",
        "GRILLED_PORK,1-2,null,0",
        "CAKE,1,null,0",
        "RED_MUSHROOM,1,null,0",
        "BROWN_MUSHROOM,1,null,0",
        "STICK,1,null,0",
        "IRON_INGOT,1,null,0",
        "GOLD_INGOT,1,null,0",
        "FLINT,1,null,0",
        "DIAMOND,1,null,0",
        "SNOW_BALL,3-5,null,0",
        "STRING,1,null,0",
        "FLINT_AND_STEEL,1,null,0",
        "DIAMOND_PICKAXE,1,null,0",
        "IRON_AXE,1,null,0",
        "FISHING_ROD,1,null,0",
        "EXP_BOTTLE,1-6,null,0",
        "FIREBALL,3-5,null,0",
        "ARROW,1-5,null,0",
        "WEB,3-4,null,0",
        "TNT,3-5,null,0"
    )
}
