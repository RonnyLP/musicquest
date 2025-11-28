package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.ThreeDots: ImageVector
    get() {
        if (_ThreeDots != null) {
            return _ThreeDots!!
        }
        _ThreeDots = ImageVector.Builder(
            name = "ThreeDots",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(4.5f, 12f)
                curveTo(4.5f, 12.828f, 5.172f, 13.5f, 6f, 13.5f)
                curveTo(6.828f, 13.5f, 7.5f, 12.828f, 7.5f, 12f)
                curveTo(7.5f, 11.172f, 6.828f, 10.5f, 6f, 10.5f)
                curveTo(5.172f, 10.5f, 4.5f, 11.172f, 4.5f, 12f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(12f, 13.5f)
                curveTo(11.172f, 13.5f, 10.5f, 12.828f, 10.5f, 12f)
                curveTo(10.5f, 11.172f, 11.172f, 10.5f, 12f, 10.5f)
                curveTo(12.828f, 10.5f, 13.5f, 11.172f, 13.5f, 12f)
                curveTo(13.5f, 12.828f, 12.828f, 13.5f, 12f, 13.5f)
                close()
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 13.5f)
                curveTo(17.172f, 13.5f, 16.5f, 12.828f, 16.5f, 12f)
                curveTo(16.5f, 11.172f, 17.172f, 10.5f, 18f, 10.5f)
                curveTo(18.828f, 10.5f, 19.5f, 11.172f, 19.5f, 12f)
                curveTo(19.5f, 12.828f, 18.828f, 13.5f, 18f, 13.5f)
                close()
            }
        }.build()

        return _ThreeDots!!
    }

@Suppress("ObjectPropertyName")
private var _ThreeDots: ImageVector? = null
