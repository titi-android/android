package core.designsystem.svg.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
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

public val MyIconPack.IcInfomationCircle: ImageVector
    get() {
        if (_icInfomationCircle != null) {
            return _icInfomationCircle!!
        }
        _icInfomationCircle = Builder(name = "IcInfomationCircle", defaultWidth = 12.0.dp,
                defaultHeight = 12.0.dp, viewportWidth = 12.0f, viewportHeight = 12.0f).apply {
            path(fill = SolidColor(Color(0xFF808991)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.895f, 4.02f)
                curveTo(5.7032f, 4.0421f, 5.5585f, 4.2045f, 5.5585f, 4.3975f)
                curveTo(5.5585f, 4.5905f, 5.7032f, 4.7529f, 5.895f, 4.775f)
                curveTo(5.9957f, 4.7764f, 6.0927f, 4.7366f, 6.1635f, 4.6649f)
                curveTo(6.2343f, 4.5932f, 6.2727f, 4.4957f, 6.27f, 4.395f)
                curveTo(6.2673f, 4.189f, 6.101f, 4.0227f, 5.895f, 4.02f)
                close()
            }
            path(fill = SolidColor(Color(0xFF808991)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(5.895f, 5.46f)
                curveTo(5.7951f, 5.4586f, 5.699f, 5.4977f, 5.6283f, 5.5683f)
                curveTo(5.5577f, 5.639f, 5.5186f, 5.7351f, 5.52f, 5.835f)
                verticalLineTo(7.395f)
                curveTo(5.52f, 7.6021f, 5.6879f, 7.77f, 5.895f, 7.77f)
                curveTo(6.1021f, 7.77f, 6.27f, 7.6021f, 6.27f, 7.395f)
                verticalLineTo(5.845f)
                curveTo(6.2727f, 5.7438f, 6.2344f, 5.6459f, 6.1638f, 5.5733f)
                curveTo(6.0931f, 5.5008f, 5.9962f, 5.46f, 5.895f, 5.46f)
                close()
            }
            path(fill = SolidColor(Color(0xFF808991)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(5.895f, 1.0f)
                curveTo(3.1927f, 1.0028f, 1.0028f, 3.1927f, 1.0f, 5.895f)
                curveTo(1.0f, 8.5984f, 3.1916f, 10.79f, 5.895f, 10.79f)
                curveTo(8.5984f, 10.79f, 10.79f, 8.5984f, 10.79f, 5.895f)
                curveTo(10.7872f, 3.1927f, 8.5973f, 1.0028f, 5.895f, 1.0f)
                close()
                moveTo(5.895f, 10.04f)
                curveTo(3.6058f, 10.04f, 1.75f, 8.1842f, 1.75f, 5.895f)
                curveTo(1.75f, 3.6058f, 3.6058f, 1.75f, 5.895f, 1.75f)
                curveTo(8.1842f, 1.75f, 10.04f, 3.6058f, 10.04f, 5.895f)
                curveTo(10.0372f, 8.1831f, 8.1831f, 10.0372f, 5.895f, 10.04f)
                close()
            }
        }
        .build()
        return _icInfomationCircle!!
    }

private var _icInfomationCircle: ImageVector? = null
