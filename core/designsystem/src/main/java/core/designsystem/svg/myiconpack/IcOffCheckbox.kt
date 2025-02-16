package core.designsystem.svg.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack

public val MyIconPack.IcOffCheckbox: ImageVector
    get() {
        if (_icOffCheckbox != null) {
            return _icOffCheckbox!!
        }
        _icOffCheckbox = Builder(name = "IcOffCheckbox", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF2E2E34)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(12.0f, 0.5f)
                lineTo(12.0f, 0.5f)
                arcTo(11.5f, 11.5f, 0.0f, false, true, 23.5f, 12.0f)
                lineTo(23.5f, 12.0f)
                arcTo(11.5f, 11.5f, 0.0f, false, true, 12.0f, 23.5f)
                lineTo(12.0f, 23.5f)
                arcTo(11.5f, 11.5f, 0.0f, false, true, 0.5f, 12.0f)
                lineTo(0.5f, 12.0f)
                arcTo(11.5f, 11.5f, 0.0f, false, true, 12.0f, 0.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(18.0f, 7.9463f)
                lineTo(9.7714f, 16.0f)
                lineTo(6.0f, 12.3087f)
                lineTo(6.9669f, 11.3624f)
                lineTo(9.7714f, 14.1007f)
                lineTo(17.0331f, 7.0f)
                lineTo(18.0f, 7.9463f)
                close()
            }
        }
        .build()
        return _icOffCheckbox!!
    }

private var _icOffCheckbox: ImageVector? = null
