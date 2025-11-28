package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.EditPen: ImageVector
    get() {
        if (_EditPen != null) {
            return _EditPen!!
        }
        _EditPen = ImageVector.Builder(
            name = "EditPen",
            defaultWidth = 110.dp,
            defaultHeight = 110.dp,
            viewportWidth = 110f,
            viewportHeight = 110f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(21.56f, 83.38f)
                lineToRelative(14.81f, -1.41f)
                curveToRelative(1.44f, -0.16f, 2.81f, -0.78f, 3.81f, -1.81f)
                lineToRelative(2.82f, -2.82f)
                lineToRelative(-18.81f, -18.81f)
                lineToRelative(-2.82f, 2.82f)
                curveToRelative(-1.03f, 1f, -1.66f, 2.38f, -1.81f, 3.81f)
                lineToRelative(-1.41f, 14.81f)
                curveToRelative(-0.19f, 1.97f, 1.44f, 3.59f, 3.41f, 3.41f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(92.2f, 94.07f)
                horizontalLineToRelative(-3.61f)
                curveToRelative(-1.73f, 0f, -3.13f, 1.4f, -3.13f, 3.13f)
                curveToRelative(0f, 1.73f, 1.4f, 3.13f, 3.13f, 3.13f)
                horizontalLineToRelative(3.61f)
                curveToRelative(1.73f, 0f, 3.13f, -1.4f, 3.13f, -3.13f)
                curveToRelative(0f, -1.73f, -1.4f, -3.13f, -3.13f, -3.13f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(72.38f, 94.07f)
                horizontalLineToRelative(-54.59f)
                curveToRelative(-1.73f, 0f, -3.13f, 1.4f, -3.13f, 3.13f)
                curveToRelative(0f, 1.73f, 1.4f, 3.13f, 3.13f, 3.13f)
                horizontalLineToRelative(54.59f)
                curveToRelative(1.73f, 0f, 3.13f, -1.4f, 3.13f, -3.13f)
                curveToRelative(0f, -1.73f, -1.4f, -3.13f, -3.13f, -3.13f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(90.03f, 21.53f)
                lineToRelative(-10f, -10f)
                curveToRelative(-2.44f, -2.47f, -6.41f, -2.47f, -8.84f, 0f)
                lineToRelative(-4.72f, 4.72f)
                lineToRelative(18.83f, 18.83f)
                lineToRelative(4.73f, -4.73f)
                curveToRelative(2.44f, -2.44f, 2.44f, -6.38f, 0f, -8.81f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(28.62f, 54.1f)
                lineToRelative(33.43f, -33.43f)
                lineToRelative(18.82f, 18.82f)
                lineToRelative(-33.43f, 33.43f)
                close()
            }
        }.build()

        return _EditPen!!
    }

@Suppress("ObjectPropertyName")
private var _EditPen: ImageVector? = null
