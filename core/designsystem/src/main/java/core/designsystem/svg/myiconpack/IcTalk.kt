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
import core.designsystem.svg.IconPack

public val IconPack.IcTalk: ImageVector
    get() {
        if (_icTalk != null) {
            return _icTalk!!
        }
        _icTalk = Builder(name = "IcTalk", defaultWidth = 24.0.dp, defaultHeight = 25.0.dp,
                viewportWidth = 24.0f, viewportHeight = 25.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(9.4999f, 3.0922f)
                curveTo(10.1802f, 2.9459f, 10.8741f, 2.8722f, 11.5699f, 2.8722f)
                curveTo(14.3518f, 2.8586f, 17.0031f, 4.0511f, 18.8385f, 6.1417f)
                curveTo(20.6739f, 8.2322f, 21.5134f, 11.0155f, 21.1399f, 13.7722f)
                curveTo(20.5399f, 18.3722f, 15.9999f, 22.0922f, 11.3599f, 22.0922f)
                horizontalLineTo(4.6999f)
                curveTo(4.1338f, 22.0924f, 3.6092f, 21.7953f, 3.3182f, 21.3097f)
                curveTo(3.0272f, 20.824f, 3.0127f, 20.2213f, 3.2799f, 19.7222f)
                lineTo(3.5499f, 19.2022f)
                curveTo(3.8187f, 18.7013f, 3.7996f, 18.0952f, 3.4999f, 17.6122f)
                curveTo(1.8216f, 14.9738f, 1.5335f, 11.6833f, 2.7277f, 8.7934f)
                curveTo(3.9219f, 5.9035f, 6.4487f, 3.7762f, 9.4999f, 3.0922f)
                close()
                moveTo(11.2799f, 20.5822f)
                curveTo(15.3566f, 20.5179f, 18.8235f, 17.5904f, 19.5699f, 13.5822f)
                curveTo(19.909f, 11.2593f, 19.2106f, 8.9046f, 17.6599f, 7.1422f)
                curveTo(16.1239f, 5.3845f, 13.9042f, 4.3749f, 11.5699f, 4.3722f)
                curveTo(10.9787f, 4.3733f, 10.3891f, 4.4336f, 9.8099f, 4.5522f)
                curveTo(7.2351f, 5.1251f, 5.0997f, 6.9145f, 4.0852f, 9.3494f)
                curveTo(3.0706f, 11.7843f, 3.3037f, 14.5605f, 4.7099f, 16.7922f)
                curveTo(5.3082f, 17.7321f, 5.3503f, 18.9224f, 4.8199f, 19.9022f)
                lineTo(4.5499f, 20.4122f)
                curveTo(4.5279f, 20.4456f, 4.5279f, 20.4888f, 4.5499f, 20.5222f)
                curveTo(4.5899f, 20.5822f, 4.6499f, 20.5822f, 4.6499f, 20.5822f)
                horizontalLineTo(11.2799f)
                close()
            }
        }
        .build()
        return _icTalk!!
    }

private var _icTalk: ImageVector? = null
