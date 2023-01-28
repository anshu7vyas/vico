/*
 * Copyright 2022 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.core.chart.values

import com.patrykandpatrick.vico.core.chart.Chart
import com.patrykandpatrick.vico.core.entry.EntryModel
import kotlin.math.abs

/**
 * Where [Chart]s get their data from.
 *
 * By default, [minX], [maxX], [minY], and [maxY] are equal to [EntryModel.minX],
 * [EntryModel.maxX], [EntryModel.minY], and [EntryModel.maxY], respectively,
 * but you can use [AxisValuesOverrider] to override these values.
 */
public interface ChartValues {

    /**
     * The minimum value displayed on the x-axis. By default, this is equal to [EntryModel.minX] (the
     * [EntryModel] instance being [entryModel]), but you can use [AxisValuesOverrider] to override this
     * value.
     */
    public val minX: Float

    /**
     * The maximum value displayed on the x-axis. By default, this is equal to [EntryModel.maxX] (the
     * [EntryModel] instance being [entryModel]), but you can use [AxisValuesOverrider] to override this
     * value.
     */
    public val maxX: Float

    /**
     * The value by which the associated [Chart] increments the _x_ value between each segment.
     */
    public val stepX: Float

    /**
     * The minimum value displayed on the y-axis. By default, this is equal to [EntryModel.minY] (the
     * [EntryModel] instance being [entryModel]), but you can use [AxisValuesOverrider] to override this
     * value.
     */
    public val minY: Float

    /**
     * The maximum value displayed on the y-axis. By default, this is equal to [EntryModel.maxY] (the
     * [EntryModel] instance being [entryModel]), but you can use [AxisValuesOverrider] to override this
     * value.
     */
    public val maxY: Float

    /**
     * The source of the associated [Chart]’s entries. The [EntryModel] defines the default values for [minX],
     * [maxX], [minY], and [maxY].
     */
    public val entryModel: EntryModel<*>?

    /**
     * The difference between [maxX] and [minX].
     */
    public val lengthX: Float
        get() = maxX - minX

    /**
     * The difference between [maxY] and [minY].
     */
    public val lengthY: Float
        get() = maxY - minY

    /**
     * The number of segments displayed on the associated [Chart].
     */
    public fun getDrawnEntryCount(): Int =
        ((abs(maxX) - abs(minX)) / stepX + 1).toInt()
}
