package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Metronome: ImageVector
    get() {
        if (_Metronome != null) {
            return _Metronome!!
        }
        _Metronome = ImageVector.Builder(
            name = "Metronome",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(29.15f, 79f)
                horizontalLineToRelative(41.7f)
                arcToRelative(9.15f, 9.15f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8f, -13.53f)
                lineTo(64.57f, 39.2f)
                lineToRelative(9f, -15.71f)
                lineToRelative(-5.2f, -3f)
                lineTo(61.2f, 33f)
                lineToRelative(-2.68f, -4.92f)
                arcToRelative(9.71f, 9.71f, 0f, isMoreThanHalf = false, isPositiveArc = false, -17f, 0f)
                lineTo(21.12f, 65.47f)
                arcToRelative(9.15f, 9.15f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8f, 13.53f)
                close()
                moveTo(68.52f, 59f)
                horizontalLineTo(53.19f)
                lineToRelative(7.88f, -13.7f)
                close()
                moveTo(46.74f, 31f)
                arcToRelative(3.71f, 3.71f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.51f, 0f)
                lineToRelative(4.44f, 8.15f)
                lineTo(46.26f, 59f)
                horizontalLineTo(31.48f)
                close()
                moveTo(26.38f, 68.34f)
                lineTo(28.21f, 65f)
                horizontalLineTo(71.79f)
                lineToRelative(1.82f, 3.34f)
                arcTo(3.15f, 3.15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 70.85f, 73f)
                horizontalLineTo(29.15f)
                arcToRelative(3.15f, 3.15f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.77f, -4.66f)
                close()
            }
        }.build()

        return _Metronome!!
    }

@Suppress("ObjectPropertyName")
private var _Metronome: ImageVector? = null
