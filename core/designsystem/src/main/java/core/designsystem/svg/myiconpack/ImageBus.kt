package core.designsystem.svg.myiconpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack

public val MyIconPack.ImageBus: ImageVector
    get() {
        if (_imageBus != null) {
            return _imageBus!!
        }
        _imageBus = Builder(name = "ImageBus", defaultWidth = 80.0.dp, defaultHeight = 80.0.dp,
                viewportWidth = 80.0f, viewportHeight = 80.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(14.264f, 9.978f)
                curveTo(14.264f, 9.33f, 14.789f, 8.805f, 15.436f, 8.805f)
                horizontalLineTo(64.564f)
                curveTo(65.213f, 8.805f, 65.738f, 9.33f, 65.738f, 9.978f)
                verticalLineTo(34.286f)
                horizontalLineTo(14.264f)
                verticalLineTo(9.978f)
                close()
                moveTo(74.309f, 61.835f)
                curveTo(74.309f, 66.417f, 71.147f, 70.26f, 66.886f, 71.301f)
                verticalLineTo(74.236f)
                curveTo(66.886f, 77.391f, 64.328f, 79.95f, 61.172f, 79.95f)
                curveTo(58.016f, 79.95f, 55.458f, 77.391f, 55.458f, 74.236f)
                verticalLineTo(71.58f)
                horizontalLineTo(24.543f)
                verticalLineTo(74.236f)
                curveTo(24.543f, 77.391f, 21.985f, 79.95f, 18.829f, 79.95f)
                curveTo(15.673f, 79.95f, 13.115f, 77.391f, 13.115f, 74.236f)
                verticalLineTo(71.301f)
                curveTo(8.854f, 70.26f, 5.692f, 66.417f, 5.692f, 61.835f)
                verticalLineTo(9.978f)
                curveTo(5.692f, 4.596f, 10.055f, 0.233f, 15.436f, 0.233f)
                horizontalLineTo(64.564f)
                curveTo(69.946f, 0.233f, 74.309f, 4.596f, 74.309f, 9.978f)
                verticalLineTo(61.835f)
                close()
                moveTo(22.858f, 57.143f)
                curveTo(26.014f, 57.143f, 28.572f, 54.585f, 28.572f, 51.429f)
                curveTo(28.572f, 48.273f, 26.014f, 45.715f, 22.858f, 45.715f)
                curveTo(19.702f, 45.715f, 17.143f, 48.273f, 17.143f, 51.429f)
                curveTo(17.143f, 54.585f, 19.702f, 57.143f, 22.858f, 57.143f)
                close()
                moveTo(57.143f, 57.143f)
                curveTo(60.299f, 57.143f, 62.858f, 54.585f, 62.858f, 51.429f)
                curveTo(62.858f, 48.273f, 60.299f, 45.715f, 57.143f, 45.715f)
                curveTo(53.987f, 45.715f, 51.429f, 48.273f, 51.429f, 51.429f)
                curveTo(51.429f, 54.585f, 53.987f, 57.143f, 57.143f, 57.143f)
                close()
            }
        }
        .build()
        return _imageBus!!
    }

private var _imageBus: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = MyIconPack.ImageBus, contentDescription = "")
    }
}
