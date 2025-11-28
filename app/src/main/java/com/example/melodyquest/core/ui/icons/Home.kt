package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Home: ImageVector
    get() {
        if (_Home != null) {
            return _Home!!
        }
        _Home = ImageVector.Builder(
            name = "Home",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(90.85f, 46.31f)
                horizontalLineTo(85.32f)
                verticalLineTo(92.12f)
                arcToRelative(2.1f, 2.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.1f, 2.1f)
                horizontalLineTo(61.89f)
                arcToRelative(2.1f, 2.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.1f, -2.1f)
                verticalLineTo(69.66f)
                arcToRelative(9.8f, 9.8f, 0f, isMoreThanHalf = false, isPositiveArc = false, -19.59f, 0f)
                verticalLineTo(92.12f)
                arcToRelative(2.1f, 2.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.1f, 2.1f)
                horizontalLineTo(16.77f)
                arcToRelative(2.1f, 2.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.1f, -2.1f)
                verticalLineTo(46.31f)
                horizontalLineTo(9.15f)
                arcTo(4.15f, 4.15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.56f, 38.92f)
                lineToRelative(39.8f, -31.84f)
                arcToRelative(5.76f, 5.76f, 0f, isMoreThanHalf = false, isPositiveArc = true, 7.28f, 0f)
                lineToRelative(39.8f, 31.84f)
                arcTo(4.15f, 4.15f, 0f, isMoreThanHalf = false, isPositiveArc = true, 90.85f, 46.31f)
                close()
            }
        }.build()

        return _Home!!
    }

@Suppress("ObjectPropertyName")
private var _Home: ImageVector? = null
