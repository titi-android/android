package core.designsystem.svg.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
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

public val MyIconPack.IcClock: ImageVector
    get() {
        if (_icClock != null) {
            return _icClock!!
        }
        _icClock = Builder(name = "IcClock", defaultWidth = 16.0.dp, defaultHeight = 17.0.dp,
                viewportWidth = 16.0f, viewportHeight = 17.0f).apply {
            path(fill = SolidColor(Color(0xFF54545F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.7666f, 5.5599f)
                curveTo(5.5696f, 5.3763f, 5.2626f, 5.3818f, 5.0722f, 5.5722f)
                curveTo(4.8818f, 5.7626f, 4.8763f, 6.0696f, 5.0599f, 6.2666f)
                lineTo(7.5799f, 8.7866f)
                verticalLineTo(11.9133f)
                curveTo(7.5799f, 12.1894f, 7.8038f, 12.4133f, 8.0799f, 12.4133f)
                curveTo(8.3561f, 12.4133f, 8.5799f, 12.1894f, 8.5799f, 11.9133f)
                verticalLineTo(8.5799f)
                curveTo(8.5798f, 8.4474f, 8.5271f, 8.3203f, 8.4333f, 8.2266f)
                lineTo(5.7666f, 5.5599f)
                close()
            }
            path(fill = SolidColor(Color(0xFF54545F)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(6.6666f, 1.8333f)
                horizontalLineTo(9.4932f)
                curveTo(10.9077f, 1.8333f, 12.2643f, 2.3952f, 13.2645f, 3.3953f)
                curveTo(14.2647f, 4.3955f, 14.8266f, 5.7521f, 14.8266f, 7.1666f)
                verticalLineTo(9.9932f)
                curveTo(14.8266f, 12.9388f, 12.4388f, 15.3266f, 9.4932f, 15.3266f)
                horizontalLineTo(6.6666f)
                curveTo(3.7211f, 15.3266f, 1.3333f, 12.9388f, 1.3333f, 9.9932f)
                verticalLineTo(7.1666f)
                curveTo(1.3333f, 4.2211f, 3.7211f, 1.8333f, 6.6666f, 1.8333f)
                close()
                moveTo(9.4932f, 14.3266f)
                curveTo(11.885f, 14.3229f, 13.8229f, 12.385f, 13.8266f, 9.9932f)
                verticalLineTo(7.1666f)
                curveTo(13.8229f, 4.7749f, 11.885f, 2.8369f, 9.4932f, 2.8333f)
                horizontalLineTo(6.6666f)
                curveTo(4.2749f, 2.8369f, 2.3369f, 4.7749f, 2.3333f, 7.1666f)
                verticalLineTo(9.9932f)
                curveTo(2.3369f, 12.385f, 4.2749f, 14.3229f, 6.6666f, 14.3266f)
                horizontalLineTo(9.4932f)
                close()
            }
        }
        .build()
        return _icClock!!
    }

private var _icClock: ImageVector? = null
