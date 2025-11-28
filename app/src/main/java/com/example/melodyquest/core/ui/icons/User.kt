package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.User: ImageVector
    get() {
        if (_User != null) {
            return _User!!
        }
        _User = ImageVector.Builder(
            name = "User",
            defaultWidth = 36.dp,
            defaultHeight = 36.dp,
            viewportWidth = 36f,
            viewportHeight = 36f
        ).apply {
            path(fill = SolidColor(Color.Black)) {
                moveTo(18f, 9.35f)
                moveToRelative(-1.177f, 7.255f)
                arcToRelative(7.35f, 7.35f, 54.217f, isMoreThanHalf = true, isPositiveArc = true, 2.355f, -14.51f)
                arcToRelative(7.35f, 7.35f, 54.217f, isMoreThanHalf = true, isPositiveArc = true, -2.355f, 14.51f)
            }
            path(fill = SolidColor(Color.Black)) {
                moveTo(31.41f, 32.7f)
                arcToRelative(1.304f, 1.304f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.3f, 1.3f)
                horizontalLineTo(5.89f)
                arcToRelative(1.304f, 1.304f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.3f, -1.3f)
                arcTo(13.41f, 13.41f, 0f, isMoreThanHalf = false, isPositiveArc = true, 31.41f, 32.7f)
                close()
            }
        }.build()

        return _User!!
    }

@Suppress("ObjectPropertyName")
private var _User: ImageVector? = null
