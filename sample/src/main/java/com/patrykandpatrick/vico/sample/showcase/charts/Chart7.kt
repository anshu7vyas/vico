/*
 * Copyright 2024 by Patryk Goworowski and Patrick Michalik.
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

package com.patrykandpatrick.vico.sample.showcase.charts

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.patrykandpatrick.vico.R
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.chart.zoom.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.component.shape.roundedCornerShape
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.rememberLegendItem
import com.patrykandpatrick.vico.compose.legend.rememberVerticalLegend
import com.patrykandpatrick.vico.compose.theme.vicoTheme
import com.patrykandpatrick.vico.core.axis.vertical.VerticalAxis
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries
import com.patrykandpatrick.vico.databinding.Chart7Binding
import com.patrykandpatrick.vico.sample.showcase.Defaults
import com.patrykandpatrick.vico.sample.showcase.UISystem
import com.patrykandpatrick.vico.sample.showcase.rememberMarker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlin.random.Random

@Composable
internal fun Chart7(
    uiSystem: UISystem,
    modifier: Modifier,
) {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.tryRunTransaction {
                    lineSeries {
                        repeat(Defaults.MULTI_SERIES_COUNT) {
                            series(
                                List(Defaults.ENTRY_COUNT) {
                                    Defaults.COLUMN_LAYER_MIN_Y +
                                        Random.nextFloat() * Defaults.COLUMN_LAYER_RELATIVE_MAX_Y
                                },
                            )
                        }
                    }
                }
                delay(Defaults.TRANSACTION_INTERVAL_MS)
            }
        }
    }

    when (uiSystem) {
        UISystem.Compose -> ComposeChart7(modelProducer, modifier)
        UISystem.Views -> ViewChart7(modelProducer, modifier)
    }
}

@Composable
private fun ComposeChart7(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
) {
    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(
                    lines =
                        chartColors.map { color ->
                            rememberLineSpec(
                                shader = DynamicShaders.color(color),
                                backgroundShader = null,
                            )
                        },
                ),
                startAxis =
                    rememberStartAxis(
                        label = rememberStartAxisLabel(),
                        horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                    ),
                bottomAxis = rememberBottomAxis(),
                legend = rememberLegend(),
            ),
        modelProducer = modelProducer,
        modifier = modifier,
        marker = rememberMarker(),
        runInitialAnimation = false,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

@Composable
private fun ViewChart7(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
) {
    val startAxisLabel = rememberStartAxisLabel()
    val marker = rememberMarker()
    val legend = rememberLegend()
    AndroidViewBinding(Chart7Binding::inflate, modifier) {
        with(chartView) {
            runInitialAnimation = false
            this.modelProducer = modelProducer
            (chart?.startAxis as VerticalAxis).horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside
            (chart?.startAxis as VerticalAxis).label = startAxisLabel
            this.marker = marker
            chart?.legend = legend
        }
    }
}

@Composable
private fun rememberStartAxisLabel() =
    rememberAxisLabelComponent(
        color = Color.Black,
        background =
            rememberShapeComponent(
                shape = Shapes.roundedCornerShape(4.dp),
                color = Color(0xfffab94d),
            ),
        padding = dimensionsOf(horizontal = 8.dp, vertical = 2.dp),
        margins = dimensionsOf(all = 4.dp),
    )

@Composable
private fun rememberLegend() =
    rememberVerticalLegend(
        items =
            chartColors.mapIndexed { index, chartColor ->
                rememberLegendItem(
                    icon = rememberShapeComponent(Shapes.pillShape, chartColor),
                    label =
                        rememberTextComponent(
                            color = vicoTheme.textColor,
                            textSize = 12.sp,
                            typeface = Typeface.MONOSPACE,
                        ),
                    labelText = stringResource(R.string.series_x, index + 1),
                )
            },
        iconSize = 8.dp,
        iconPadding = 8.dp,
        spacing = 4.dp,
        padding = dimensionsOf(top = 8.dp),
    )

private val chartColors = listOf(Color(0xffb983ff), Color(0xff91b1fd), Color(0xff8fdaff))
