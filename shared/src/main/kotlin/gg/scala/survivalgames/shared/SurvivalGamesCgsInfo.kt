package gg.scala.survivalgames.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.survivalgames.shared.gamemode.SurvivalGamesSoloGameMode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object SurvivalGamesCgsInfo : CgsGameGeneralInfo(
    "Survival Games", 0.01F, 4,
    10, SurvivalGamesCgsAwards, true, true,
    true, false, true,
    true, listOf(SurvivalGamesSoloGameMode)
)
