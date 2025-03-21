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

public val MyIconPack.IcLogout: ImageVector
    get() {
        if (_icLogout != null) {
            return _icLogout!!
        }
        _icLogout = Builder(name = "IcLogout", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(20.7807f, 13.0236f)
                lineTo(14.9607f, 18.7833f)
                curveTo(14.6678f, 19.0722f, 14.1935f, 19.0722f, 13.9007f, 18.7833f)
                curveTo(13.7577f, 18.6457f, 13.6771f, 18.4568f, 13.6771f, 18.2597f)
                curveTo(13.6771f, 18.0625f, 13.7577f, 17.8737f, 13.9007f, 17.7361f)
                lineTo(18.4407f, 13.2409f)
                lineTo(7.2107f, 13.2409f)
                curveTo(6.7964f, 13.2409f, 6.4607f, 12.9092f, 6.4607f, 12.5f)
                curveTo(6.4607f, 12.0908f, 6.7964f, 11.759f, 7.2107f, 11.759f)
                lineTo(18.4407f, 11.759f)
                lineTo(13.9007f, 7.2639f)
                curveTo(13.7581f, 7.1263f, 13.6786f, 6.9372f, 13.6807f, 6.7403f)
                curveTo(13.6797f, 6.5436f, 13.759f, 6.3549f, 13.9007f, 6.2167f)
                curveTo(14.1935f, 5.9277f, 14.6678f, 5.9277f, 14.9607f, 6.2167f)
                lineTo(20.7807f, 11.9764f)
                curveTo(21.0731f, 12.2656f, 21.0731f, 12.7343f, 20.7807f, 13.0236f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(4.8592f, 3.084f)
                lineTo(4.8592f, 21.843f)
                curveTo(4.8592f, 22.2522f, 4.5234f, 22.584f, 4.1092f, 22.584f)
                curveTo(3.695f, 22.584f, 3.3592f, 22.2522f, 3.3592f, 21.843f)
                lineTo(3.3592f, 3.084f)
                curveTo(3.3592f, 2.6748f, 3.695f, 2.343f, 4.1092f, 2.343f)
                curveTo(4.5234f, 2.343f, 4.8592f, 2.6748f, 4.8592f, 3.084f)
                close()
            }
        }
        .build()
        return _icLogout!!
    }

private var _icLogout: ImageVector? = null
