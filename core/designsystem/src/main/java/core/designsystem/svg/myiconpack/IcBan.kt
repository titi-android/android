package core.designsystem.svg.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack

public val MyIconPack.IcBan: ImageVector
    get() {
        if (_icBan != null) {
            return _icBan!!
        }
        _icBan = Builder(name = "IcCancel", defaultWidth = 24.0.dp, defaultHeight = 25.0.dp,
                viewportWidth = 24.0f, viewportHeight = 25.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(12.0f, 2.3721f)
                curveTo(6.4771f, 2.3721f, 2.0f, 6.8492f, 2.0f, 12.3721f)
                curveTo(2.0f, 17.8949f, 6.4771f, 22.3721f, 12.0f, 22.3721f)
                curveTo(17.5228f, 22.3721f, 22.0f, 17.8949f, 22.0f, 12.3721f)
                curveTo(22.0f, 9.7199f, 20.9464f, 7.1764f, 19.0711f, 5.301f)
                curveTo(17.1957f, 3.4256f, 14.6522f, 2.3721f, 12.0f, 2.3721f)
                close()
                moveTo(3.5f, 12.3721f)
                curveTo(3.4903f, 8.99f, 5.4912f, 5.9255f, 8.5916f, 4.5741f)
                curveTo(11.692f, 3.2227f, 15.299f, 3.8428f, 17.77f, 6.1521f)
                lineTo(5.77f, 18.1521f)
                curveTo(4.3054f, 16.5844f, 3.4936f, 14.5174f, 3.5f, 12.3721f)
                close()
                moveTo(6.9f, 19.1521f)
                curveTo(8.3667f, 20.2666f, 10.1578f, 20.8707f, 12.0f, 20.8721f)
                curveTo(15.2228f, 20.8797f, 18.1714f, 19.0598f, 19.6093f, 16.1755f)
                curveTo(21.0472f, 13.2912f, 20.7259f, 9.8412f, 18.78f, 7.2721f)
                lineTo(6.9f, 19.1521f)
                close()
            }
        }
        .build()
        return _icBan!!
    }

private var _icBan: ImageVector? = null
