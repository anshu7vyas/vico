/*
 * Copyright 2023 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.core.candlestickentry

import com.patrykandpatrick.vico.core.chart.Chart
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.chart.values.ChartValues
import com.patrykandpatrick.vico.core.entry.EntryModel

/**
 * Contains the data for a [Chart]. Pre-calculates values needed for the rendering of the [Chart].
 *
 * The [Chart] may override [minX], [maxX], [minY], or [maxY] via [AxisValuesOverrider]. These overrides will be used
 * in the [Chart]’s [ChartValues] instance.
 *
 * It’s recommended to delegate the creation of [CandlestickEntryModel] to [CandlestickEntryModelProducer].
 *
 * @see [ChartValues]
 * @see [CandlestickEntryModelProducer]
 */
public interface CandlestickEntryModel : EntryModel<CandlestickTypedEntry>
