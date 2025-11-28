package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Search: ImageVector
    get() {
        if (_Search != null) {
            return _Search!!
        }
        _Search = ImageVector.Builder(
            name = "Search",
            defaultWidth = 110.dp,
            defaultHeight = 110.dp,
            viewportWidth = 110f,
            viewportHeight = 110f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveToRelative(39.9f, 16.99f)
                curveToRelative(16.5f, -4.42f, 33.46f, 5.37f, 37.88f, 21.87f)
                curveToRelative(4.42f, 16.5f, -5.37f, 33.46f, -21.87f, 37.88f)
                curveToRelative(-16.5f, 4.42f, -33.46f, -5.37f, -37.88f, -21.87f)
                curveToRelative(-4.42f, -16.5f, 5.37f, -33.45f, 21.87f, -37.88f)
                close()
                moveTo(90.84f, 102.68f)
                curveToRelative(2.57f, -2.13f, 2.93f, -5.98f, 0.79f, -8.55f)
                lineToRelative(-14.73f, -17.74f)
                lineToRelative(-9.35f, 7.77f)
                lineToRelative(-1.07f, -1.29f)
                lineToRelative(15.81f, 19.03f)
                curveToRelative(2.13f, 2.57f, 5.98f, 2.93f, 8.55f, 0.79f)
                close()
                moveTo(63.9f, 76.75f)
                lineTo(67.23f, 80.76f)
                lineTo(73.61f, 75.46f)
                lineTo(70.7f, 71.96f)
                curveToRelative(-2.05f, 1.87f, -4.34f, 3.48f, -6.8f, 4.79f)
                close()
                moveTo(41.89f, 24.38f)
                curveToRelative(12.41f, -3.32f, 25.18f, 4.04f, 28.51f, 16.46f)
                reflectiveCurveToRelative(-4.04f, 25.18f, -16.46f, 28.5f)
                curveToRelative(-12.41f, 3.33f, -25.18f, -4.04f, -28.51f, -16.46f)
                curveToRelative(-3.33f, -12.42f, 4.04f, -25.18f, 16.46f, -28.51f)
                close()
            }
        }.build()

        return _Search!!
    }

@Suppress("ObjectPropertyName")
private var _Search: ImageVector? = null
