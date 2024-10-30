package gg.scala.survivalgames.shared

import gg.scala.cgs.common.player.statistic.GameSpecificStatistics
import gg.scala.cgs.common.player.statistic.value.CgsGameStatistic
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import gg.scala.survivalgames.shared.tickable.TickableBukkitRunnable
import java.lang.reflect.Type
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class SurvivalGamesCgsStatistics(uniqueId: UUID) : GameSpecificStatistics(uniqueId)
{
    init
    {
        this.gameKills.reset()
    }

    @Transient
    var noCleanTimer: TickableBukkitRunnable? = null

    override fun getAbstractType(): Type
    {
        return SurvivalGamesCgsStatistics::class.java
    }

    override fun save() = DataStoreObjectControllerCache
        .findNotNull<SurvivalGamesCgsStatistics>()
        .save(this, DataStoreStorageType.MONGO)
}
