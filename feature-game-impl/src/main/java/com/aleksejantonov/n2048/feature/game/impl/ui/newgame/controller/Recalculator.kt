package com.aleksejantonov.n2048.feature.game.impl.ui.newgame.controller

import androidx.recyclerview.widget.ItemTouchHelper
import com.aleksejantonov.n2048.feature.game.impl.ui.newgame.adapter.Cell
import kotlin.random.Random

class Recalculator {

    fun recalculateList(direction: Int, list: List<Cell>): List<Cell> {
        val resultList = mutableListOf<Cell>().apply { addAll(list) }
        val size: Int = Math.sqrt(list.size.toDouble()).toInt()
        when (direction) {
            ItemTouchHelper.UP    -> {
                for (i in 0 until size) {
                    val currentIndices = listOf(i, i + size, i + size * 2, i + size * 3)
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                    val cells = listOf(resultList[i], resultList[i + size], resultList[i + size * 2], resultList[i + size * 3])
                    when {
                        allNullsEquality(cells)     -> {
                            // noop
                        }
                        doublePairEquality(cells)   -> {
                            resultList[i + size * 3] = list[i + size * 3].copy(value = null)
                            resultList[i + size * 2] = list[i + size * 2].copy(value = null)
                            resultList[i + size] = list[i + size].copy(value = list[i + size * 3].value!! * 2)
                            resultList[i] = list[i].copy(value = list[i].value!! * 2)
                        }
                        pairEquality(cells) != null -> {
                            val indices = pairEquality(cells)
                            resultList[indices!!.first] = resultList[indices.first].copy(value = resultList[indices.first].value!! * 2)
                            resultList[indices.second] = resultList[indices.second].copy(value = null)
                        }
                    }
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                }
            }
            ItemTouchHelper.DOWN  -> {
                for (i in 0 until size) {
                    val currentIndices = listOf(i, i + size, i + size * 2, i + size * 3)
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                    val cells = listOf(resultList[i], resultList[i + size], resultList[i + size * 2], resultList[i + size * 3])
                    when {
                        allNullsEquality(cells)     -> {
                            // noop
                        }
                        doublePairEquality(cells)   -> {
                            resultList[i + size * 3] = list[i + size * 3].copy(value = list[i + size * 3].value!! * 2)
                            resultList[i + size * 2] = list[i + size * 2].copy(value = list[i].value!! * 2)
                            resultList[i + size] = list[i + size].copy(value = null)
                            resultList[i] = list[i].copy(value = null)
                        }
                        pairEquality(cells) != null -> {
                            val indices = pairEquality(cells)
                            resultList[indices!!.second] = resultList[indices.second].copy(value = resultList[indices.second].value!! * 2)
                            resultList[indices.first] = resultList[indices.first].copy(value = null)
                        }
                    }
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                }
            }
            ItemTouchHelper.LEFT  -> {
                for (i in 0 until size) {
                    val currentIndices = listOf(i * size, i * size + 1, i * size + 2, i * size + 3)
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                    val cells = listOf(resultList[i * size], resultList[i * size + 1], resultList[i * size + 2], resultList[i * size + 3])
                    when {
                        allNullsEquality(cells)     -> {
                            // noop
                        }
                        doublePairEquality(cells)   -> {
                            resultList[i * size + 3] = list[i * size + 3].copy(value = null)
                            resultList[i * size + 2] = list[i * size + 2].copy(value = null)
                            resultList[i * size + 1] = list[i * size + 1].copy(value = list[i * size + 3].value!! * 2)
                            resultList[i * size] = list[i * size].copy(value = list[i * size].value!! * 2)
                        }
                        pairEquality(cells) != null -> {
                            val indices = pairEquality(cells)
                            resultList[indices!!.first] = resultList[indices.first].copy(value = resultList[indices.first].value!! * 2)
                            resultList[indices.second] = resultList[indices.second].copy(value = null)
                        }
                    }
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                }
            }
            ItemTouchHelper.RIGHT -> {
                for (i in 0 until size) {
                    val currentIndices = listOf(i * size, i * size + 1, i * size + 2, i * size + 3)
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                    val cells = listOf(resultList[i * size], resultList[i * size + 1], resultList[i * size + 2], resultList[i * size + 3])
                    when {
                        allNullsEquality(cells)     -> {
                            // noop
                        }
                        doublePairEquality(cells)   -> {
                            resultList[i * size + 3] = list[i * size + 3].copy(value = list[i * size + 3].value!! * 2)
                            resultList[i * size + 2] = list[i * size + 2].copy(value = list[i * size].value!! * 2)
                            resultList[i * size + 1] = list[i * size + 1].copy(value = null)
                            resultList[i * size] = list[i * size].copy(value = null)
                        }
                        pairEquality(cells) != null -> {
                            val indices = pairEquality(cells)
                            resultList[indices!!.second] = resultList[indices.second].copy(value = resultList[indices.second].value!! * 2)
                            resultList[indices.first] = resultList[indices.first].copy(value = null)
                        }
                    }
                    shiftNullsIfPossible(indices = currentIndices, initialList = resultList, direction = direction)
                }
            }
        }
        return resultList.addRandom()
    }

    private fun allNullsEquality(cells: List<Cell>): Boolean {
        var allNulls = true
        for (cell in cells) if (cell.value != null) allNulls = false
        return allNulls
    }

    private fun doublePairEquality(cells: List<Cell>): Boolean {
        return cells[0].value != null && cells[0].value == cells[1].value
                && cells[2].value != null && cells[2].value == cells[3].value
    }

    private fun pairEquality(cells: List<Cell>): Pair<Int, Int>? {
        // Neighbors
        if (cells[0].value != null && cells[0].value == cells[1].value) return Pair(cells[0].id, cells[1].id)
        if (cells[1].value != null && cells[1].value == cells[2].value) return Pair(cells[1].id, cells[2].id)
        if (cells[2].value != null && cells[2].value == cells[3].value) return Pair(cells[2].id, cells[3].id)
        // Through the holes
        if (cells[0].value != null && cells[1].value == null && cells[0].value == cells[2].value) {
            return Pair(cells[0].id, cells[2].id)
        }
        if (cells[0].value != null && cells[1].value == null && cells[2].value == null && cells[0].value == cells[3].value) {
            return Pair(cells[0].id, cells[3].id)
        }
        if (cells[1].value != null && cells[2].value == null && cells[1].value == cells[3].value) {
            return Pair(cells[1].id, cells[3].id)
        }
        return null
    }

    private fun shiftNullsIfPossible(indices: List<Int>, initialList: MutableList<Cell>, direction: Int) {
        when (direction) {
            ItemTouchHelper.UP    -> {
                for (i in 0 until indices.size - 1) {
                    if (initialList[indices[i]].value == null) {
                        for (j in i + 1 until indices.size) {
                            if (initialList[indices[j]].value != null) {
                                initialList[indices[i]] = initialList[indices[i]].copy(value = initialList[indices[j]].value)
                                initialList[indices[j]] = initialList[indices[j]].copy(value = null)
                                break
                            }
                        }
                    }
                }
            }
            ItemTouchHelper.DOWN  -> {
                for (i in indices.size - 1 downTo 1) {
                    if (initialList[indices[i]].value == null) {
                        for (j in i - 1 downTo 0) {
                            if (initialList[indices[j]].value != null) {
                                initialList[indices[i]] = initialList[indices[i]].copy(value = initialList[indices[j]].value)
                                initialList[indices[j]] = initialList[indices[j]].copy(value = null)
                                break
                            }
                        }
                    }
                }
            }
            ItemTouchHelper.LEFT  -> {
                for (i in 0 until indices.size - 1) {
                    if (initialList[indices[i]].value == null) {
                        for (j in i + 1 until indices.size) {
                            if (initialList[indices[j]].value != null) {
                                initialList[indices[i]] = initialList[indices[i]].copy(value = initialList[indices[j]].value)
                                initialList[indices[j]] = initialList[indices[j]].copy(value = null)
                                break
                            }
                        }
                    }
                }
            }
            ItemTouchHelper.RIGHT -> {
                for (i in indices.size - 1 downTo 1) {
                    if (initialList[indices[i]].value == null) {
                        for (j in i - 1 downTo 0) {
                            if (initialList[indices[j]].value != null) {
                                initialList[indices[i]] = initialList[indices[i]].copy(value = initialList[indices[j]].value)
                                initialList[indices[j]] = initialList[indices[j]].copy(value = null)
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    private fun MutableList<Cell>.addRandom(): MutableList<Cell> {
        val filteredList = this.filter { it.value == null }
        if (filteredList.isEmpty()) return this

        val upTo = Random.nextInt(0, 100)
        val nextValue = if (upTo > 91) 4 else 2

        if (filteredList.size == 1) {
            this[filteredList[0].id] = this[filteredList[0].id].copy(value = nextValue)
        } else {
            val randomIndexId = filteredList[Random.nextInt(0, filteredList.size - 1)].id
            this[randomIndexId] = this[randomIndexId].copy(value = nextValue)
        }
        return this
    }
}