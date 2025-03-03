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

public val MyIconPack.IcVersionInfo: ImageVector
    get() {
        if (_icVersionInfo != null) {
            return _icVersionInfo!!
        }
        _icVersionInfo = Builder(name = "IcVersionInfo", defaultWidth = 24.0.dp, defaultHeight =
                25.0.dp, viewportWidth = 24.0f, viewportHeight = 25.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.79f, 8.54f)
                curveTo(11.4065f, 8.5842f, 11.117f, 8.9089f, 11.117f, 9.295f)
                curveTo(11.117f, 9.6811f, 11.4065f, 10.0058f, 11.79f, 10.05f)
                curveTo(11.9915f, 10.0528f, 12.1854f, 9.9733f, 12.327f, 9.8299f)
                curveTo(12.4685f, 9.6864f, 12.5455f, 9.4915f, 12.54f, 9.29f)
                curveTo(12.5346f, 8.878f, 12.202f, 8.5454f, 11.79f, 8.54f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(11.79f, 11.42f)
                curveTo(11.5903f, 11.4173f, 11.3979f, 11.4954f, 11.2567f, 11.6367f)
                curveTo(11.1154f, 11.7779f, 11.0373f, 11.9703f, 11.04f, 12.17f)
                verticalLineTo(15.29f)
                curveTo(11.04f, 15.7042f, 11.3758f, 16.04f, 11.79f, 16.04f)
                curveTo(12.2042f, 16.04f, 12.54f, 15.7042f, 12.54f, 15.29f)
                verticalLineTo(12.19f)
                curveTo(12.5454f, 11.9876f, 12.4688f, 11.7917f, 12.3275f, 11.6467f)
                curveTo(12.1863f, 11.5017f, 11.9924f, 11.4199f, 11.79f, 11.42f)
                close()
            }
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(11.79f, 2.5f)
                curveTo(6.3854f, 2.5055f, 2.0055f, 6.8854f, 2.0f, 12.29f)
                curveTo(2.0f, 17.6969f, 6.3831f, 22.08f, 11.79f, 22.08f)
                curveTo(17.1969f, 22.08f, 21.58f, 17.6969f, 21.58f, 12.29f)
                curveTo(21.5745f, 6.8854f, 17.1946f, 2.5055f, 11.79f, 2.5f)
                close()
                moveTo(11.79f, 20.58f)
                curveTo(7.2116f, 20.58f, 3.5f, 16.8684f, 3.5f, 12.29f)
                curveTo(3.5f, 7.7116f, 7.2116f, 4.0f, 11.79f, 4.0f)
                curveTo(16.3684f, 4.0f, 20.08f, 7.7116f, 20.08f, 12.29f)
                curveTo(20.0745f, 16.8662f, 16.3662f, 20.5745f, 11.79f, 20.58f)
                close()
            }
        }
        .build()
        return _icVersionInfo!!
    }

private var _icVersionInfo: ImageVector? = null
