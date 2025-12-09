package com.example.melodyquest.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val AppIcons.DoubleChevronArrow: ImageVector
    get() {
        if (_DoubleChevronArrow != null) {
            return _DoubleChevronArrow!!
        }
        _DoubleChevronArrow = ImageVector.Builder(
            name = "DoubleChevronArrow",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                strokeLineWidth = 1f
            ) {
                moveTo(22.586f, 16f)
                lineTo(12.293f, 5.707f)
                lineTo(13.707f, 4.293f)
                lineTo(25.414f, 16f)
                lineTo(13.707f, 27.707f)
                lineTo(12.293f, 26.293f)
                lineTo(22.586f, 16f)
                close()
                moveTo(16.586f, 16f)
                lineTo(6.293f, 5.707f)
                lineTo(7.707f, 4.293f)
                lineTo(19.414f, 16f)
                lineTo(7.707f, 27.707f)
                lineTo(6.293f, 26.293f)
                lineTo(16.586f, 16f)
                close()
            }
        }.build()

        return _DoubleChevronArrow!!
    }

@Suppress("ObjectPropertyName")
private var _DoubleChevronArrow: ImageVector? = null
