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

public val MyIconPack.IcClose: ImageVector
    get() {
        if (_icClose != null) {
            return _icClose!!
        }
        _icClose = Builder(name = "IcClose", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(13.1665f, 12.008f)
                lineTo(20.762f, 4.4382f)
                curveTo(21.0793f, 4.1179f, 21.0793f, 3.599f, 20.762f, 3.2787f)
                curveTo(20.4503f, 2.9526f, 19.9355f, 2.9429f, 19.6118f, 3.2568f)
                lineTo(12.0163f, 10.8266f)
                lineTo(4.5184f, 3.2568f)
                curveTo(4.3647f, 3.0929f, 4.1508f, 3.0f, 3.927f, 3.0f)
                curveTo(3.7033f, 3.0f, 3.4894f, 3.0929f, 3.3357f, 3.2568f)
                curveTo(3.0543f, 3.5663f, 3.0543f, 4.0412f, 3.3357f, 4.3507f)
                lineTo(10.8335f, 11.9096f)
                lineTo(3.238f, 19.4685f)
                curveTo(2.9207f, 19.7888f, 2.9207f, 20.3077f, 3.238f, 20.628f)
                curveTo(3.3891f, 20.784f, 3.5968f, 20.871f, 3.8131f, 20.8687f)
                curveTo(4.0335f, 20.8867f, 4.252f, 20.8159f, 4.4207f, 20.6718f)
                lineTo(12.0163f, 13.102f)
                lineTo(19.6118f, 20.7593f)
                curveTo(19.7629f, 20.9153f, 19.9707f, 21.0022f, 20.1869f, 21.0f)
                curveTo(20.4029f, 21.001f, 20.6102f, 20.9142f, 20.762f, 20.7593f)
                curveTo(21.0793f, 20.439f, 21.0793f, 19.9201f, 20.762f, 19.5998f)
                lineTo(13.1665f, 12.008f)
                close()
            }
        }
        .build()
        return _icClose!!
    }

private var _icClose: ImageVector? = null
