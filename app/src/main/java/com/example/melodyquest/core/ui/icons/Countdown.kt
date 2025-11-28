package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Countdown: ImageVector
    get() {
        if (_Countdown != null) {
            return _Countdown!!
        }
        _Countdown = ImageVector.Builder(
            name = "Countdown",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveToRelative(51.94f, 10.59f)
                curveToRelative(20.22f, 0.99f, 36.45f, 17.23f, 37.44f, 37.44f)
                horizontalLineToRelative(4.34f)
                curveToRelative(1.08f, 0f, 1.95f, 0.88f, 1.95f, 1.95f)
                reflectiveCurveToRelative(-0.88f, 1.95f, -1.95f, 1.95f)
                horizontalLineToRelative(-4.34f)
                curveToRelative(-0.99f, 20.22f, -17.23f, 36.45f, -37.44f, 37.44f)
                verticalLineToRelative(4.34f)
                curveToRelative(0f, 1.08f, -0.88f, 1.95f, -1.95f, 1.95f)
                reflectiveCurveToRelative(-1.95f, -0.88f, -1.95f, -1.95f)
                verticalLineToRelative(-4.34f)
                curveToRelative(-20.22f, -0.99f, -36.45f, -17.23f, -37.44f, -37.44f)
                horizontalLineToRelative(-4.34f)
                curveToRelative(-1.08f, 0f, -1.95f, -0.88f, -1.95f, -1.95f)
                reflectiveCurveToRelative(0.88f, -1.95f, 1.95f, -1.95f)
                horizontalLineToRelative(4.34f)
                curveToRelative(0.99f, -20.22f, 17.23f, -36.45f, 37.44f, -37.44f)
                verticalLineToRelative(-4.34f)
                curveToRelative(0f, -1.08f, 0.88f, -1.95f, 1.95f, -1.95f)
                reflectiveCurveToRelative(1.95f, 0.88f, 1.95f, 1.95f)
                close()
                moveTo(48.03f, 14.5f)
                curveToRelative(-18.06f, 0.98f, -32.55f, 15.47f, -33.53f, 33.53f)
                horizontalLineToRelative(4.38f)
                curveToRelative(0.97f, -15.65f, 13.5f, -28.18f, 29.15f, -29.15f)
                close()
                moveTo(48.03f, 22.79f)
                curveToRelative(-13.49f, 0.96f, -24.28f, 11.75f, -25.24f, 25.24f)
                horizontalLineToRelative(2.2f)
                curveToRelative(1.08f, 0f, 1.95f, 0.88f, 1.95f, 1.95f)
                reflectiveCurveToRelative(-0.88f, 1.95f, -1.95f, 1.95f)
                horizontalLineToRelative(-2.2f)
                curveToRelative(0.96f, 13.49f, 11.75f, 24.28f, 25.24f, 25.24f)
                verticalLineToRelative(-2.2f)
                curveToRelative(0f, -1.08f, 0.88f, -1.95f, 1.95f, -1.95f)
                reflectiveCurveToRelative(1.95f, 0.88f, 1.95f, 1.95f)
                verticalLineToRelative(2.2f)
                curveToRelative(13.49f, -0.96f, 24.28f, -11.75f, 25.24f, -25.24f)
                horizontalLineToRelative(-2.2f)
                curveToRelative(-1.08f, 0f, -1.95f, -0.88f, -1.95f, -1.95f)
                reflectiveCurveToRelative(0.88f, -1.95f, 1.95f, -1.95f)
                horizontalLineToRelative(10.49f)
                curveToRelative(-0.98f, -18.06f, -15.47f, -32.55f, -33.53f, -33.53f)
                verticalLineToRelative(10.49f)
                curveToRelative(0f, 1.08f, -0.88f, 1.95f, -1.95f, 1.95f)
                reflectiveCurveToRelative(-1.95f, -0.88f, -1.95f, -1.95f)
                close()
                moveTo(58.86f, 46.01f)
                curveToRelative(1.29f, 0.91f, 2.05f, 2.4f, 2.05f, 3.98f)
                reflectiveCurveToRelative(-0.76f, 3.06f, -2.05f, 3.98f)
                lineToRelative(-12.08f, 8.61f)
                curveToRelative(-1.49f, 1.06f, -3.45f, 1.2f, -5.07f, 0.36f)
                curveToRelative(-1.63f, -0.84f, -2.64f, -2.51f, -2.64f, -4.34f)
                verticalLineToRelative(-17.22f)
                curveToRelative(0f, -1.83f, 1.02f, -3.5f, 2.64f, -4.34f)
                curveToRelative(1.63f, -0.84f, 3.58f, -0.7f, 5.07f, 0.36f)
                close()
                moveTo(56.59f, 49.19f)
                lineTo(44.51f, 40.58f)
                curveToRelative(-0.3f, -0.21f, -0.69f, -0.24f, -1.02f, -0.07f)
                curveToRelative(-0.32f, 0.17f, -0.53f, 0.5f, -0.53f, 0.87f)
                verticalLineToRelative(17.22f)
                curveToRelative(0f, 0.37f, 0.2f, 0.7f, 0.53f, 0.87f)
                curveToRelative(0.32f, 0.17f, 0.71f, 0.14f, 1.02f, -0.07f)
                lineToRelative(12.08f, -8.61f)
                curveToRelative(0.26f, -0.18f, 0.41f, -0.48f, 0.41f, -0.8f)
                curveToRelative(0f, -0.32f, -0.15f, -0.61f, -0.41f, -0.8f)
                close()
                moveTo(51.94f, 85.46f)
                curveToRelative(18.06f, -0.98f, 32.55f, -15.47f, 33.53f, -33.53f)
                horizontalLineToRelative(-4.38f)
                curveToRelative(-0.97f, 15.65f, -13.5f, 28.18f, -29.15f, 29.15f)
                close()
                moveTo(48.03f, 81.09f)
                curveToRelative(-15.65f, -0.97f, -28.18f, -13.5f, -29.15f, -29.15f)
                horizontalLineToRelative(-4.38f)
                curveToRelative(0.98f, 18.06f, 15.47f, 32.55f, 33.53f, 33.53f)
                close()
            }
        }.build()

        return _Countdown!!
    }

@Suppress("ObjectPropertyName")
private var _Countdown: ImageVector? = null
