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

public val MyIconPack.IcBus: ImageVector
    get() {
        if (_icBus != null) {
            return _icBus!!
        }
        _icBus = Builder(name = "IcBus", defaultWidth = 16.0.dp, defaultHeight = 16.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF1DC26A)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(2.8525f, 1.9957f)
                curveTo(2.8525f, 1.8662f, 2.9575f, 1.7612f, 3.0871f, 1.7612f)
                horizontalLineTo(12.9127f)
                curveTo(13.0423f, 1.7612f, 13.1473f, 1.8662f, 13.1473f, 1.9957f)
                verticalLineTo(6.8574f)
                horizontalLineTo(2.8525f)
                verticalLineTo(1.9957f)
                close()
                moveTo(14.8616f, 12.3673f)
                curveTo(14.8616f, 13.2836f, 14.2291f, 14.0522f, 13.377f, 14.2604f)
                verticalLineTo(14.8474f)
                curveTo(13.377f, 15.4785f, 12.8653f, 15.9902f, 12.2341f, 15.9902f)
                curveTo(11.6029f, 15.9902f, 11.0913f, 15.4785f, 11.0913f, 14.8474f)
                verticalLineTo(14.3162f)
                horizontalLineTo(4.9084f)
                verticalLineTo(14.8474f)
                curveTo(4.9084f, 15.4785f, 4.3967f, 15.9902f, 3.7655f, 15.9902f)
                curveTo(3.1344f, 15.9902f, 2.6227f, 15.4785f, 2.6227f, 14.8474f)
                verticalLineTo(14.2604f)
                curveTo(1.7706f, 14.0522f, 1.1382f, 13.2836f, 1.1382f, 12.3673f)
                verticalLineTo(1.9957f)
                curveTo(1.1382f, 0.9194f, 2.0107f, 0.0469f, 3.0871f, 0.0469f)
                horizontalLineTo(12.9127f)
                curveTo(13.989f, 0.0469f, 14.8616f, 0.9194f, 14.8616f, 1.9957f)
                verticalLineTo(12.3673f)
                close()
                moveTo(4.5713f, 11.4289f)
                curveTo(5.2025f, 11.4289f, 5.7141f, 10.9172f, 5.7141f, 10.286f)
                curveTo(5.7141f, 9.6548f, 5.2025f, 9.1431f, 4.5713f, 9.1431f)
                curveTo(3.9401f, 9.1431f, 3.4284f, 9.6548f, 3.4284f, 10.286f)
                curveTo(3.4284f, 10.9172f, 3.9401f, 11.4289f, 4.5713f, 11.4289f)
                close()
                moveTo(11.4284f, 11.4289f)
                curveTo(12.0596f, 11.4289f, 12.5713f, 10.9172f, 12.5713f, 10.286f)
                curveTo(12.5713f, 9.6548f, 12.0596f, 9.1431f, 11.4284f, 9.1431f)
                curveTo(10.7973f, 9.1431f, 10.2856f, 9.6548f, 10.2856f, 10.286f)
                curveTo(10.2856f, 10.9172f, 10.7973f, 11.4289f, 11.4284f, 11.4289f)
                close()
            }
        }
        .build()
        return _icBus!!
    }

private var _icBus: ImageVector? = null
