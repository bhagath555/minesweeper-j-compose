/*
 * Copyright 2021 Jaya Surya Thotapalli
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jayasuryat.minesweeperenginedebug.test

import com.jayasuryat.minesweeperengine.gridgenerator.MineGridGenerator
import com.jayasuryat.minesweeperengine.model.block.GridSize
import com.jayasuryat.minesweeperengine.model.block.Position
import com.jayasuryat.minesweeperengine.model.cell.MineCell
import com.jayasuryat.minesweeperengine.model.cell.RawCell
import kotlinx.coroutines.runBlocking

// TODO: 10/11/21 Migrate to tests
private fun main() {

    val size = GridSize.of(size = 10)
    val grid = runBlocking {
        MineGridGenerator()
            .generateGrid(
                gridSize = size,
                starCell = Position(row = 0, column = 0),
                mineCount = 50,
            )
    }

    val message = grid.cells.prettyToString()
    println(message)
}

private fun List<List<RawCell>>.prettyToString(): String {

    fun String.formatted(): String = String.format("%3s", this)

    fun RawCell.pretty(): String {

        val cell = when (this) {
            is RawCell.UnrevealedCell -> this.asRevealed().cell
            is RawCell.RevealedCell -> this.cell
        }

        return when (cell) {
            is MineCell.ValuedCell.Cell -> cell.value.toString()
            is MineCell.ValuedCell.EmptyCell -> "0"
            is MineCell.Mine -> "\uD83D\uDD34"
        }.formatted()
    }

    val message = StringBuilder()

    message.append("-----\n")

    this.forEach { row ->
        message.append("[")
        row.forEach { column -> message.append("${column.pretty()}, ") }
        message.append("]")
        message.append("\n")
    }

    message.append("-----\n")

    return message.toString()
}
