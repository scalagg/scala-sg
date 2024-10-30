package gg.scala.survivalgames.game

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.deathmatch.DeathmatchConfiguration
import gg.scala.cgs.common.deathmatch.DeathmatchStartStrategy
import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cgs.common.snapshot.CgsGameSnapshot
import gg.scala.cgs.common.voting.VotingMapConfiguration
import gg.scala.cgs.common.voting.VotingMapEntry
import gg.scala.cgs.common.voting.selection.VoteSelectionType
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.lemon.util.CubedCacheUtil
import gg.scala.survivalgames.game.chest.SurvivalGamesChestService
import gg.scala.survivalgames.game.listener.SurvivalGamesListener
import gg.scala.survivalgames.game.nametag.SurvivalGamesNametagAdapter
import gg.scala.survivalgames.game.scoreboard.SurvivalGamesScoreboard
import gg.scala.survivalgames.game.visibility.SurvivalGamesVisibilityAdapter
import gg.scala.survivalgames.shared.SurvivalGamesCgsStatistics
import gg.scala.survivalgames.shared.gamemode.SurvivalGamesSoloGameMode
import me.lucko.helper.utils.Players
import net.evilblock.cubed.util.CC
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
class SurvivalGamesEngine(
    plugin: ExtendedScalaPlugin,
    gameInfo: CgsGameGeneralInfo,
    gameMode: CgsGameMode
) : CgsGameEngine<SurvivalGamesCgsStatistics>(
    plugin, gameInfo, gameMode,
    SurvivalGamesCgsStatistics::class
)
{
    companion object
    {
        @JvmStatic
        var INSTANCE by Delegates.notNull<SurvivalGamesEngine>()
    }

    override fun getScoreboardRenderer() = SurvivalGamesScoreboard
    override fun getVisibilityAdapter() = SurvivalGamesVisibilityAdapter
    override fun getNametagAdapter() = SurvivalGamesNametagAdapter

    private val votingMapConfiguration = object : VotingMapConfiguration
    {
        override val minimumPlayersForVotingStart = 4
        override val selectionType = VoteSelectionType.GUI
        override val votingAutoCloseDuration = Duration.ofSeconds(20L)

        override fun entries() = SurvivalGamesSoloGameMode.getSGMaps().map {
            VotingMapEntry(
                id = it.getId(),
                item = it.getXMaterialIcon(),
                description = it.getDescription(),
                displayName = it.getName()
            )
        }

        override fun preStartLobby() = Location(
            Bukkit.getWorld("world"),
            -44.5, 66.0, -0.5, -90.0f, 0.0f
        )
    }

    override fun getVotingConfig() = votingMapConfiguration

    override fun onAsyncPreStartResourceInitialization(): CompletableFuture<Boolean>
    {
        return CompletableFuture.supplyAsync {
            val arenaData = File(
                CgsGameArenaHandler.arena.getDirectory()!!.toFile(),
                "data.yml"
            )

            val yaml = YamlConfiguration
                .loadConfiguration(arenaData)

            Bukkit.getServer().getWorld(CgsGameArenaHandler.arena.getId()).difficulty = Difficulty.EASY

            fun mapToLocation(line: String): Location
            {
                val split = line.split(", ")

                return Location(
                    Bukkit.getWorld(CgsGameArenaHandler.arena.getDirectory()!!.toFile().nameWithoutExtension),
                    split[0].toDouble(),
                    split[1].toDouble(),
                    split[2].toDouble(),
                    split[3].toFloat(),
                    split[4].toFloat()
                )
            }

            SurvivalGamesListener.spawnPoints
                .addAll(
                    yaml.getStringList("spawnpoints")
                        .map(::mapToLocation)
                )

            SurvivalGamesListener.deathMatchSpawnPoints
                .addAll(
                    yaml.getStringList("deathmatchPoints")
                        .map(::mapToLocation)
                )

            SurvivalGamesListener.centerLocation =
                mapToLocation(
                    yaml.getString("center-location")
                )

            return@supplyAsync true
        }
    }

    override fun getDeathmatchConfig() = object : DeathmatchConfiguration
    {
        override fun timeUntilForcedDeathmatch() = Duration.ofSeconds(1L)
        override fun deathmatchStartStrategy() = DeathmatchStartStrategy.AlivePlayerCount
        override fun getMinimumToStartDeathMatch() = 4

        override fun getStartTime() = Duration.ofSeconds(10L)
        override fun getTeleportationTime() = Duration.ofSeconds(45L)

        override fun onStart()
        {
            val world = Bukkit.getWorld(gameArena!!.getId()) ?: return
            val borderSize = 50.0
            val centerX = SurvivalGamesListener.centerLocation.x
            val centerZ = SurvivalGamesListener.centerLocation.z

            val worldBorder = world.worldBorder
            worldBorder.center = Location(world, centerX, 0.0, centerZ)
            worldBorder.size = borderSize

            Players.all()
                .filter {
                    !it.hasMetadata("spectator")
                }
                .forEach {
                    it sit false
                }
        }

        override fun onTeleporation()
        {
            SurvivalGamesChestService.chests.clear()

            for (
            (indexed, player) in Players.all()
                .filter {
                    !it.hasMetadata("spectator")
                }
                .withIndex()
            )
            {
                player.teleport(
                    SurvivalGamesListener.deathMatchSpawnPoints[indexed]
                )
                player sit true
            }
        }
    }

    override fun getGameSnapshot(): CgsGameSnapshot
    {
        return object : CgsGameSnapshot
        {
            override fun getExtraInformation(): List<String>
            {
                val topKills = mutableListOf<String>()

                val sortedKills = SurvivalGamesListener.killsTracker
                    .entries.sortedByDescending { it.value }

                for (i in 0 until 3.coerceAtMost(sortedKills.size))
                {
                    val (key, value) = sortedKills[i]
                    topKills.add("  ${CC.YELLOW}#${i + 1} ${CC.GRAY}- ${CC.WHITE}${CubedCacheUtil.fetchName(key)}${CC.GRAY} - ${CC.PRI}$value")
                }

                return topKills
            }
        }
    }
}
