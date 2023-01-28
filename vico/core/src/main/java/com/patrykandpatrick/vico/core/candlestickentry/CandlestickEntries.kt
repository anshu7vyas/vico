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

/**
 * TODO
 */
public fun CandlestickEntry(
    x: Float,
    low: Float,
    high: Float,
    open: Float,
    close: Float,
): CandlestickEntry = object : CandlestickEntry {

    override val x: Float = x
    override val low: Float = low
    override val high: Float = high
    override val open: Float = open
    override val close: Float = close

    override fun withValuesAndType(
        low: Float,
        high: Float,
        open: Float,
        close: Float,
        type: CandlestickEntryType,
    ): CandlestickTypedEntry = CandlestickTypedEntry(
        x = x,
        low = low,
        high = high,
        open = open,
        close = close,
        type = type,
    )
}

/**
 * TODO
 */
public fun CandlestickTypedEntry(
    x: Float,
    low: Float,
    high: Float,
    open: Float,
    close: Float,
    type: CandlestickEntryType = CandlestickEntryType.standard(open, close),
): CandlestickTypedEntry = object : CandlestickTypedEntry {

    override val x: Float = x
    override val low: Float = low
    override val high: Float = high
    override val open: Float = open
    override val close: Float = close
    override val type: CandlestickEntryType = type

    override fun withValuesAndType(
        low: Float,
        high: Float,
        open: Float,
        close: Float,
        type: CandlestickEntryType,
    ): CandlestickTypedEntry = CandlestickTypedEntry(
        x = x,
        low = low,
        high = high,
        open = open,
        close = close,
        type = type,
    )
}
