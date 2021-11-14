package com.jayasuryat.uigame

import androidx.compose.runtime.Stable
import com.jayasuryat.minesweeperengine.controller.MinefieldController
import com.jayasuryat.minesweeperengine.controller.model.MinefieldAction
import com.jayasuryat.minesweeperengine.controller.model.MinefieldEvent
import com.jayasuryat.minesweeperengine.state.StatefulGrid
import com.jayasuryat.minesweeperengine.state.getCurrentGrid
import com.jayasuryat.minesweeperui.composable.event.MinefieldActionsListener
import com.jayasuryat.uigame.util.exhaustive

@Stable
internal class ActionListener(
    private val statefulGrid: StatefulGrid,
    private val minefieldController: MinefieldController,
) : MinefieldActionsListener {

    override fun action(action: MinefieldAction) {

        val state: MinefieldEvent = minefieldController.onAction(
            action = action,
            mineGrid = statefulGrid.getCurrentGrid(),
        )

        when (state) {

            is MinefieldEvent.OnGridUpdated -> {
                statefulGrid.updateStatesWith(cells = state.mineGrid.cells)
            }

            is MinefieldEvent.OnGameOver -> Unit

        }.exhaustive
    }
}