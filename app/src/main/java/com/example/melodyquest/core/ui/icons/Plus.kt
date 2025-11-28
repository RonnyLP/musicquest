package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.Plus: ImageVector
    get() {
        if (_Plus != null) {
            return _Plus!!
        }
        _Plus = ImageVector.Builder(
            name = "Plus",
            defaultWidth = 100.dp,
            defaultHeight = 100.dp,
            viewportWidth = 100f,
            viewportHeight = 100f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveToRelative(45.25f, 92.75f)
                curveToRelative(0f, 2.63f, 2.13f, 4.75f, 4.75f, 4.75f)
                reflectiveCurveToRelative(4.75f, -2.13f, 4.75f, -4.75f)
                verticalLineToRelative(-38f)
                horizontalLineToRelative(38f)
                curveToRelative(2.63f, 0f, 4.75f, -2.13f, 4.75f, -4.75f)
                reflectiveCurveToRelative(-2.13f, -4.75f, -4.75f, -4.75f)
                horizontalLineToRelative(-38f)
                verticalLineToRelative(-38f)
                curveToRelative(0f, -2.63f, -2.13f, -4.75f, -4.75f, -4.75f)
                reflectiveCurveToRelative(-4.75f, 2.13f, -4.75f, 4.75f)
                verticalLineToRelative(38f)
                horizontalLineToRelative(-38f)
                curveToRelative(-2.63f, 0f, -4.75f, 2.13f, -4.75f, 4.75f)
                reflectiveCurveToRelative(2.13f, 4.75f, 4.75f, 4.75f)
                horizontalLineToRelative(38f)
                close()
            }
        }.build()

        return _Plus!!
    }

@Suppress("ObjectPropertyName")
private var _Plus: ImageVector? = null
