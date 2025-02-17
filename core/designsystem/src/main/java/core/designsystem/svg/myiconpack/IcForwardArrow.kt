package core.designsystem.svg.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack

public val MyIconPack.IcForwardArrow: ImageVector
    get() {
        if (_icForwardArrow != null) {
            return _icForwardArrow!!
        }
        _icForwardArrow = Builder(name = "IcForwardArrow", defaultWidth = 24.0.dp, defaultHeight =
                25.0.dp, viewportWidth = 24.0f, viewportHeight = 25.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.0003f, 21.1219f)
                curveTo(8.1994f, 21.1229f, 8.3904f, 21.0436f, 8.5303f, 20.9019f)
                lineTo(16.5303f, 12.9019f)
                curveTo(16.8228f, 12.6091f, 16.8228f, 12.1347f, 16.5303f, 11.8419f)
                lineTo(8.5303f, 3.8419f)
                curveTo(8.2348f, 3.5666f, 7.7743f, 3.5747f, 7.4887f, 3.8603f)
                curveTo(7.2031f, 4.1459f, 7.195f, 4.6064f, 7.4703f, 4.9019f)
                lineTo(14.9403f, 12.3719f)
                lineTo(7.4703f, 19.8419f)
                curveTo(7.1779f, 20.1347f, 7.1779f, 20.6091f, 7.4703f, 20.9019f)
                curveTo(7.6102f, 21.0436f, 7.8012f, 21.1229f, 8.0003f, 21.1219f)
                close()
            }
        }
        .build()
        return _icForwardArrow!!
    }

private var _icForwardArrow: ImageVector? = null
