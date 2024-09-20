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

package com.patrykandpatrick.vico.compose.cartesian.layer

import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.LineCartesianLayerDrawingModel
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.layer.getDefaultAreaFill
import com.patrykandpatrick.vico.core.common.Defaults
import com.patrykandpatrick.vico.core.common.VerticalPosition
import com.patrykandpatrick.vico.core.common.component.Component
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.CartesianLayerDrawingModelInterpolator

/** Creates and remembers a [LineCartesianLayer]. */
@Composable
public fun rememberLineCartesianLayer(
  lineProvider: LineCartesianLayer.LineProvider =
    LineCartesianLayer.LineProvider.series(
      vicoTheme.lineCartesianLayerColors.map { color ->
        LineCartesianLayer.rememberLine(LineCartesianLayer.LineFill.single(fill(color)))
      }
    ),
  pointSpacing: Dp = Defaults.POINT_SPACING.dp,
  rangeProvider: CartesianLayerRangeProvider = remember { CartesianLayerRangeProvider.auto() },
  verticalAxisPosition: Axis.Position.Vertical? = null,
  drawingModelInterpolator:
    CartesianLayerDrawingModelInterpolator<
      LineCartesianLayerDrawingModel.PointInfo,
      LineCartesianLayerDrawingModel,
    > =
    remember {
      CartesianLayerDrawingModelInterpolator.default()
    },
): LineCartesianLayer =
  remember { LineCartesianLayer(lineProvider) }
    .apply {
      this.lineProvider = lineProvider
      this.pointSpacingDp = pointSpacing.value
      this.rangeProvider = rangeProvider
      this.verticalAxisPosition = verticalAxisPosition
      this.drawingModelInterpolator = drawingModelInterpolator
    }

/** Creates and remembers a [LineCartesianLayer.Line]. */
@Composable
public fun LineCartesianLayer.Companion.rememberLine(
  fill: LineCartesianLayer.LineFill =
    vicoTheme.lineCartesianLayerColors.first().let { color ->
      remember(color) { LineCartesianLayer.LineFill.single(fill(color)) }
    },
  pattern: LineCartesianLayer.LinePattern = remember {
    LineCartesianLayer.LinePattern.continuous()
  },
  thickness: Dp = Defaults.LINE_SPEC_THICKNESS_DP.dp,
  areaFill: LineCartesianLayer.AreaFill? = remember(fill) { fill.getDefaultAreaFill() },
  cap: StrokeCap = StrokeCap.Round,
  pointProvider: LineCartesianLayer.PointProvider? = null,
  pointConnector: LineCartesianLayer.PointConnector = remember {
    LineCartesianLayer.PointConnector.cubic()
  },
  dataLabel: TextComponent? = null,
  dataLabelVerticalPosition: VerticalPosition = VerticalPosition.Top,
  dataLabelValueFormatter: CartesianValueFormatter = remember { CartesianValueFormatter.decimal() },
  dataLabelRotationDegrees: Float = 0f,
): LineCartesianLayer.Line =
  remember(
    fill,
    pattern,
    thickness,
    areaFill,
    cap,
    pointProvider,
    pointConnector,
    dataLabel,
    dataLabelVerticalPosition,
    dataLabelRotationDegrees,
    dataLabelRotationDegrees,
  ) {
    LineCartesianLayer.Line(
      fill,
      pattern,
      thickness.value,
      areaFill,
      cap.paintCap,
      pointProvider,
      pointConnector,
      dataLabel,
      dataLabelVerticalPosition,
      dataLabelValueFormatter,
      dataLabelRotationDegrees,
    )
  }

/** Creates a [LineCartesianLayer.Point]. */
public fun LineCartesianLayer.Companion.point(
  component: Component,
  size: Dp = Defaults.POINT_SIZE.dp,
): LineCartesianLayer.Point = LineCartesianLayer.Point(component, size.value)

private val StrokeCap.paintCap: Paint.Cap
  get() =
    when (this) {
      StrokeCap.Butt -> Paint.Cap.BUTT
      StrokeCap.Round -> Paint.Cap.ROUND
      StrokeCap.Square -> Paint.Cap.SQUARE
      else ->
        throw IllegalArgumentException(
          "Not `StrokeCap.Butt`, `StrokeCap.Round`, or `StrokeCap.Square`."
        )
    }

/** Returns a [LineCartesianLayer.LinePattern.Continuous] instance. */
public fun LineCartesianLayer.LinePattern.Companion.continuous(): LineCartesianLayer.LinePattern =
  LineCartesianLayer.LinePattern.Continuous

/** Returns a [LineCartesianLayer.LinePattern.Dashed] instance. */
public fun LineCartesianLayer.LinePattern.Companion.dashed(
  dashLength: Dp = Defaults.LINE_PATTERN_DASHED_LENGTH.dp,
  gapLength: Dp = Defaults.LINE_PATTERN_DASHED_GAP.dp,
): LineCartesianLayer.LinePattern.Dashed =
  LineCartesianLayer.LinePattern.Dashed(
    dashLength.value,
    gapLength.value,
  )
