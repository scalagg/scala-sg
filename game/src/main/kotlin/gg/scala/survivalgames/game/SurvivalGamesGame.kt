package gg.scala.survivalgames.game

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cloudsync.shared.discovery.CloudSyncDiscoveryService
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.config.annotations.ContainerConfig
import gg.scala.commons.core.plugin.Plugin
import gg.scala.commons.core.plugin.PluginDependency
import gg.scala.commons.core.plugin.PluginDependencyComposite
import gg.scala.survivalgames.game.listener.SurvivalGamesListener
import gg.scala.survivalgames.game.sponsors.SurvivalGamesSponsorConfig
import gg.scala.survivalgames.shared.SurvivalGamesCgsInfo
import org.bukkit.Difficulty

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Plugin(
    name = "SurvivalGames",
    version = "%remote%/%branch%/%id%"
)
@PluginDependencyComposite(
    PluginDependency("scala-commons"),
    PluginDependency("Lemon"),
    PluginDependency("ScGameFramework"),
    PluginDependency("cloudsync")
)
@ContainerConfig(
    model = SurvivalGamesConfig::class,
    value = "game-configuration",
    crossSync = true
)
@ContainerConfig(
    model = SurvivalGamesSponsorConfig::class,
    value = "sponsors",
    crossSync = true
)
class SurvivalGamesGame : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        val orchestration =
            loadConfig("orchestration.yml")

        val mode = orchestration.getString("mode")
            ?: "gg.scala.survivalgames.shared.gamemode.SurvivalGamesSoloGameMode"

        val modeClass = Class.forName(mode)

        val modeClassObject = modeClass
            .getField("INSTANCE")
            .get(null)

        val engine = SurvivalGamesEngine(
            this, SurvivalGamesCgsInfo,
            modeClassObject as CgsGameMode
        )

        engine.initialLoad()

        flavor {
            inject(SurvivalGamesListener)
        }

        server.pluginManager.registerEvents(
            SurvivalGamesListener, this
        )

        SurvivalGamesEngine.INSTANCE = engine

        CloudSyncDiscoveryService
            .discoverable.assets
            .add("gg.scala.survivalgames:game:sg-game")
    }
}
