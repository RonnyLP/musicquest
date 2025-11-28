package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Library: ImageVector
    get() {
        if (_Library != null) {
            return _Library!!
        }
        _Library = ImageVector.Builder(
            name = "Library",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(52.38f, 30.65f)
                horizontalLineToRelative(-20.31f)
                verticalLineToRelative(39.06f)
                horizontalLineToRelative(20.31f)
                close()
                moveTo(42.22f, 61.9f)
                curveToRelative(-1.3f, 0f, -2.34f, -1.05f, -2.34f, -2.34f)
                verticalLineToRelative(-18.75f)
                curveToRelative(0f, -1.3f, 1.05f, -2.34f, 2.34f, -2.34f)
                reflectiveCurveToRelative(2.34f, 1.05f, 2.34f, 2.34f)
                verticalLineToRelative(18.75f)
                curveToRelative(0f, 1.3f, -1.05f, 2.34f, -2.34f, 2.34f)
                close()
                moveTo(55.11f, 20.84f)
                curveToRelative(-0.23f, -0.79f, -0.13f, -1.62f, 0.26f, -2.33f)
                curveToRelative(0.4f, -0.72f, 1.05f, -1.23f, 1.84f, -1.46f)
                lineToRelative(13.26f, -3.82f)
                curveToRelative(1.63f, -0.46f, 3.33f, 0.47f, 3.79f, 2.09f)
                lineToRelative(2.18f, 7.56f)
                lineToRelative(-19.15f, 5.52f)
                close()
                moveTo(77.74f, 27.38f)
                lineTo(58.59f, 32.9f)
                lineTo(69.41f, 70.43f)
                lineTo(88.56f, 64.91f)
                close()
                moveTo(76.81f, 60.17f)
                curveToRelative(-0.22f, 0.06f, -0.44f, 0.09f, -0.65f, 0.09f)
                curveToRelative(-1.02f, 0f, -1.95f, -0.67f, -2.25f, -1.7f)
                lineToRelative(-5.2f, -18.02f)
                curveToRelative(-0.36f, -1.24f, 0.36f, -2.54f, 1.6f, -2.9f)
                curveToRelative(1.24f, -0.36f, 2.54f, 0.36f, 2.9f, 1.6f)
                lineToRelative(5.2f, 18.02f)
                curveToRelative(0.36f, 1.24f, -0.36f, 2.54f, -1.6f, 2.9f)
                close()
                moveTo(28.35f, 30.65f)
                horizontalLineToRelative(-20.31f)
                verticalLineToRelative(39.06f)
                horizontalLineToRelative(20.31f)
                close()
                moveTo(18.2f, 61.9f)
                curveToRelative(-1.3f, 0f, -2.34f, -1.05f, -2.34f, -2.34f)
                verticalLineToRelative(-18.75f)
                curveToRelative(0f, -1.3f, 1.05f, -2.34f, 2.34f, -2.34f)
                curveToRelative(1.3f, 0f, 2.34f, 1.05f, 2.34f, 2.34f)
                verticalLineToRelative(18.75f)
                curveToRelative(0f, 1.3f, -1.05f, 2.34f, -2.34f, 2.34f)
                close()
                moveTo(92.42f, 84.55f)
                curveToRelative(0f, 1.3f, -1.05f, 2.34f, -2.34f, 2.34f)
                horizontalLineToRelative(-80.14f)
                curveToRelative(-1.3f, 0f, -2.34f, -1.05f, -2.34f, -2.34f)
                curveToRelative(0f, -0.58f, 0.22f, -1.1f, 0.57f, -1.52f)
                curveToRelative(-0.06f, -0.25f, -0.11f, -0.5f, -0.11f, -0.77f)
                verticalLineToRelative(-7.88f)
                horizontalLineToRelative(20.31f)
                verticalLineToRelative(7.81f)
                horizontalLineToRelative(3.71f)
                verticalLineToRelative(-7.81f)
                horizontalLineToRelative(20.31f)
                verticalLineToRelative(7.81f)
                horizontalLineToRelative(20.41f)
                lineToRelative(-2.09f, -7.27f)
                lineToRelative(19.14f, -5.52f)
                lineToRelative(2.18f, 7.56f)
                curveToRelative(0.47f, 1.63f, -0.48f, 3.32f, -2.09f, 3.79f)
                lineToRelative(-4.95f, 1.43f)
                horizontalLineToRelative(5.1f)
                curveToRelative(1.29f, 0.01f, 2.34f, 1.05f, 2.34f, 2.35f)
                close()
                moveTo(8.04f, 25.96f)
                verticalLineToRelative(-7.88f)
                curveToRelative(0f, -1.69f, 1.38f, -3.06f, 3.06f, -3.06f)
                horizontalLineToRelative(14.19f)
                curveToRelative(1.69f, 0f, 3.06f, 1.38f, 3.06f, 3.06f)
                verticalLineToRelative(7.88f)
                close()
                moveTo(32.06f, 25.96f)
                verticalLineToRelative(-7.88f)
                curveToRelative(0f, -1.69f, 1.38f, -3.06f, 3.06f, -3.06f)
                horizontalLineToRelative(14.19f)
                curveToRelative(1.69f, 0f, 3.06f, 1.38f, 3.06f, 3.06f)
                verticalLineToRelative(7.88f)
                close()
            }
        }.build()

        return _Library!!
    }

@Suppress("ObjectPropertyName")
private var _Library: ImageVector? = null
