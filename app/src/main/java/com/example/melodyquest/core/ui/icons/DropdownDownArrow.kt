package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.DropdownDownArrow: ImageVector
    get() {
        if (_DropdownDownArrow != null) {
            return _DropdownDownArrow!!
        }
        _DropdownDownArrow = ImageVector.Builder(
            name = "DropdownDownArrow",
            defaultWidth = 80.dp,
            defaultHeight = 80.dp,
            viewportWidth = 80f,
            viewportHeight = 80f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(71f, 26.36f)
                arcToRelative(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.58f, 4.05f)
                lineToRelative(-25f, 27.29f)
                arcToRelative(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, -8.84f, 0f)
                lineToRelative(-25f, -27.29f)
                arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8.84f, -8.11f)
                lineTo(40f, 44.76f)
                lineTo(60.58f, 22.3f)
                arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = true, 71f, 26.36f)
                close()
            }
        }.build()

        return _DropdownDownArrow!!
    }

@Suppress("ObjectPropertyName")
private var _DropdownDownArrow: ImageVector? = null
