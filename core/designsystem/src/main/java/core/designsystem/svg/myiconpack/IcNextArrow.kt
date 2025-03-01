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

public val MyIconPack.IcNextArrow: ImageVector
    get() {
        if (_icNextArrow != null) {
            return _icNextArrow!!
        }
        _icNextArrow = Builder(name = "IcNextArrow", defaultWidth = 18.0.dp, defaultHeight =
                17.0.dp, viewportWidth = 18.0f, viewportHeight = 17.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 0.6f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(16.7783f, 7.9925f)
                lineTo(10.0106f, 1.2066f)
                curveTo(9.8241f, 1.0248f, 9.555f, 0.9566f, 9.3047f, 1.0276f)
                curveTo(9.0544f, 1.0987f, 8.8609f, 1.2982f, 8.7971f, 1.5511f)
                curveTo(8.7334f, 1.804f, 8.809f, 2.0718f, 8.9955f, 2.2536f)
                lineTo(14.5159f, 7.7792f)
                horizontalLineTo(1.7251f)
                curveTo(1.3246f, 7.7792f, 1.0f, 8.1047f, 1.0f, 8.5063f)
                curveTo(1.0f, 8.9078f, 1.3246f, 9.2333f, 1.7251f, 9.2333f)
                horizontalLineTo(14.5063f)
                lineTo(8.9955f, 14.759f)
                curveTo(8.8573f, 14.894f, 8.7793f, 15.0793f, 8.7793f, 15.2728f)
                curveTo(8.7793f, 15.4663f, 8.8573f, 15.6515f, 8.9955f, 15.7866f)
                curveTo(9.1287f, 15.9268f, 9.3148f, 16.0042f, 9.5079f, 15.9998f)
                curveTo(9.7003f, 16.0007f, 9.8851f, 15.9239f, 10.0203f, 15.7866f)
                lineTo(16.7879f, 9.0007f)
                curveTo(17.0707f, 8.7168f, 17.0707f, 8.257f, 16.7879f, 7.9731f)
                lineTo(16.7783f, 7.9925f)
                close()
            }
        }
        .build()
        return _icNextArrow!!
    }

private var _icNextArrow: ImageVector? = null
