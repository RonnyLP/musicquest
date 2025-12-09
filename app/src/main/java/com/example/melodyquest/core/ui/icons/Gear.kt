package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Gear: ImageVector
    get() {
        if (_Gear != null) {
            return _Gear!!
        }
        _Gear = ImageVector.Builder(
            name = "Gear2",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(41.99f, 5f)
                curveToRelative(-1.45f, 0.02f, -2.69f, 1.28f, -2.94f, 2.44f)
                lineToRelative(-1.63f, 8.97f)
                curveToRelative(-3.48f, 1.31f, -6.7f, 3.12f, -9.56f, 5.38f)
                lineToRelative(-8.47f, -3.28f)
                curveToRelative(-1.3f, -0.51f, -2.93f, 0.05f, -3.66f, 1.25f)
                lineToRelative(-8.34f, 13.66f)
                curveToRelative(-0.71f, 1.19f, -0.44f, 2.87f, 0.59f, 3.78f)
                lineToRelative(6.72f, 5.97f)
                curveToRelative(-0.43f, 2.22f, -0.72f, 4.5f, -0.72f, 6.84f)
                curveToRelative(0f, 2.34f, 0.29f, 4.6f, 0.72f, 6.81f)
                lineToRelative(-6.72f, 5.97f)
                curveToRelative(-1.05f, 0.92f, -1.32f, 2.62f, -0.59f, 3.81f)
                lineToRelative(8.34f, 13.66f)
                curveToRelative(0.73f, 1.19f, 2.36f, 1.73f, 3.66f, 1.22f)
                lineToRelative(8.47f, -3.25f)
                curveToRelative(2.86f, 2.25f, 6.09f, 4.04f, 9.56f, 5.34f)
                lineToRelative(1.63f, 8.97f)
                curveToRelative(0.25f, 1.37f, 1.55f, 2.46f, 2.94f, 2.47f)
                lineToRelative(16f, 0f)
                curveToRelative(1.4f, 0.01f, 2.72f, -1.09f, 2.97f, -2.47f)
                lineToRelative(1.63f, -8.97f)
                curveToRelative(3.48f, -1.31f, 6.7f, -3.09f, 9.56f, -5.34f)
                lineToRelative(8.47f, 3.25f)
                curveToRelative(1.29f, 0.5f, 2.9f, -0.04f, 3.63f, -1.22f)
                lineToRelative(8.34f, -13.66f)
                curveToRelative(0.73f, -1.19f, 0.48f, -2.89f, -0.56f, -3.81f)
                lineToRelative(-6.72f, -6f)
                curveToRelative(0.42f, -2.21f, 0.69f, -4.47f, 0.69f, -6.78f)
                curveToRelative(0f, -2.32f, -0.27f, -4.6f, -0.69f, -6.81f)
                lineToRelative(6.72f, -6f)
                curveToRelative(1.03f, -0.92f, 1.28f, -2.6f, 0.56f, -3.78f)
                lineToRelative(-8.34f, -13.66f)
                curveToRelative(-0.72f, -1.19f, -2.33f, -1.74f, -3.63f, -1.25f)
                lineToRelative(-8.47f, 3.28f)
                curveToRelative(-2.87f, -2.26f, -6.08f, -4.06f, -9.56f, -5.38f)
                lineToRelative(-1.63f, -8.97f)
                curveToRelative(-0.26f, -1.36f, -1.58f, -2.45f, -2.97f, -2.44f)
                lineToRelative(-16f, 0f)
                close()
                moveTo(44.53f, 11f)
                lineTo(55.49f, 11f)
                lineTo(56.96f, 19.03f)
                curveToRelative(0.2f, 1.07f, 1.03f, 1.99f, 2.06f, 2.31f)
                curveToRelative(4f, 1.26f, 7.63f, 3.36f, 10.72f, 6.06f)
                curveToRelative(0.82f, 0.72f, 2.05f, 0.93f, 3.06f, 0.53f)
                lineToRelative(7.63f, -2.94f)
                lineToRelative(5.72f, 9.41f)
                lineToRelative(-6.09f, 5.44f)
                curveToRelative(-0.81f, 0.73f, -1.17f, 1.91f, -0.91f, 2.97f)
                curveToRelative(0.56f, 2.29f, 0.84f, 4.7f, 0.84f, 7.19f)
                curveToRelative(0f, 2.49f, -0.28f, 4.9f, -0.84f, 7.19f)
                curveToRelative(-0.25f, 1.05f, 0.11f, 2.22f, 0.91f, 2.94f)
                lineToRelative(6.13f, 5.47f)
                lineToRelative(-5.75f, 9.38f)
                lineToRelative(-7.63f, -2.94f)
                curveToRelative(-1.01f, -0.4f, -2.24f, -0.18f, -3.06f, 0.53f)
                curveToRelative(-3.09f, 2.71f, -6.72f, 4.81f, -10.72f, 6.06f)
                curveToRelative(-1.04f, 0.32f, -1.86f, 1.25f, -2.06f, 2.31f)
                lineToRelative(-1.47f, 8.06f)
                lineToRelative(-10.97f, 0f)
                lineToRelative(-1.47f, -8.06f)
                curveToRelative(-0.2f, -1.07f, -1.03f, -1.99f, -2.06f, -2.31f)
                curveToRelative(-4f, -1.26f, -7.63f, -3.36f, -10.72f, -6.06f)
                curveToRelative(-0.82f, -0.71f, -2.05f, -0.93f, -3.06f, -0.53f)
                lineToRelative(-7.66f, 2.94f)
                lineToRelative(-5.72f, -9.38f)
                lineToRelative(6.13f, -5.44f)
                curveToRelative(0.81f, -0.71f, 1.18f, -1.89f, 0.94f, -2.94f)
                curveToRelative(-0.57f, -2.32f, -0.91f, -4.73f, -0.91f, -7.22f)
                curveToRelative(0f, -2.49f, 0.33f, -4.9f, 0.91f, -7.22f)
                curveToRelative(0.26f, -1.06f, -0.12f, -2.25f, -0.94f, -2.97f)
                lineToRelative(-6.13f, -5.44f)
                lineToRelative(5.75f, -9.38f)
                lineToRelative(7.63f, 2.94f)
                curveToRelative(1.01f, 0.4f, 2.24f, 0.18f, 3.06f, -0.53f)
                curveToRelative(3.09f, -2.71f, 6.72f, -4.81f, 10.72f, -6.06f)
                curveToRelative(1.04f, -0.32f, 1.86f, -1.25f, 2.06f, -2.31f)
                lineToRelative(1.47f, -8.03f)
                close()
                moveTo(49.99f, 29f)
                curveToRelative(-11.56f, 0f, -21f, 9.44f, -21f, 21f)
                curveToRelative(0f, 11.56f, 9.44f, 21f, 21f, 21f)
                curveToRelative(11.56f, 0f, 21f, -9.44f, 21f, -21f)
                curveToRelative(0f, -11.56f, -9.44f, -21f, -21f, -21f)
                close()
                moveTo(49.99f, 35f)
                curveToRelative(8.32f, 0f, 15f, 6.68f, 15f, 15f)
                curveToRelative(0f, 8.32f, -6.68f, 15f, -15f, 15f)
                curveToRelative(-8.32f, 0f, -15f, -6.68f, -15f, -15f)
                curveToRelative(0f, -8.32f, 6.68f, -15f, 15f, -15f)
                close()
            }
        }.build()

        return _Gear!!
    }

@Suppress("ObjectPropertyName")
private var _Gear: ImageVector? = null
