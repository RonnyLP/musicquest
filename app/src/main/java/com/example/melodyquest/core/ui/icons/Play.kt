package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Play: ImageVector
    get() {
        if (_Play != null) {
            return _Play!!
        }
        _Play = ImageVector.Builder(
            name = "Play",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(86.18f, 46.79f)
                curveToRelative(2.65f, 1.32f, 2.65f, 5.1f, 0f, 6.42f)
                lineTo(17.03f, 90.63f)
                curveToRelative(-2.39f, 1.2f, -5.2f, -0.54f, -5.2f, -3.21f)
                verticalLineTo(12.58f)
                curveToRelative(0f, -2.67f, 2.81f, -4.41f, 5.2f, -3.21f)
                lineTo(86.18f, 46.79f)
                close()
            }
        }.build()

        return _Play!!
    }

@Suppress("ObjectPropertyName")
private var _Play: ImageVector? = null
