package core.designsystem.svg.myiconpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack

public val MyIconPack.ImageSubway: ImageVector
    get() {
        if (_imageSubway != null) {
            return _imageSubway!!
        }
        _imageSubway = Builder(name = "ImageSubway", defaultWidth = 62.0.dp, defaultHeight =
                80.0.dp, viewportWidth = 62.0f, viewportHeight = 80.0f).apply {
            path(fill = SolidColor(Color(0xFF2E2E34)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(53.893f, 0.0f)
                curveTo(58.108f, 0.0f, 61.524f, 3.416f, 61.524f, 7.631f)
                verticalLineTo(63.593f)
                curveTo(61.524f, 67.714f, 58.257f, 71.07f, 54.172f, 71.217f)
                lineTo(59.85f, 76.826f)
                curveTo(60.55f, 77.517f, 60.557f, 78.644f, 59.866f, 79.344f)
                curveTo(59.175f, 80.043f, 58.048f, 80.05f, 57.348f, 79.359f)
                lineTo(49.111f, 71.225f)
                horizontalLineTo(14.671f)
                lineTo(6.305f, 79.486f)
                curveTo(5.606f, 80.177f, 4.479f, 80.17f, 3.788f, 79.471f)
                curveTo(3.097f, 78.771f, 3.104f, 77.644f, 3.803f, 76.953f)
                lineTo(9.603f, 71.225f)
                horizontalLineTo(8.106f)
                curveTo(3.892f, 71.225f, 0.475f, 67.807f, 0.475f, 63.593f)
                verticalLineTo(7.631f)
                curveTo(0.476f, 3.417f, 3.892f, 0.0f, 8.106f, 0.0f)
                horizontalLineTo(53.893f)
                close()
                moveTo(15.228f, 50.366f)
                curveTo(12.419f, 50.366f, 10.141f, 52.644f, 10.141f, 55.454f)
                curveTo(10.142f, 58.264f, 12.419f, 60.541f, 15.228f, 60.541f)
                curveTo(18.038f, 60.541f, 20.316f, 58.264f, 20.316f, 55.454f)
                curveTo(20.316f, 52.644f, 18.038f, 50.366f, 15.228f, 50.366f)
                close()
                moveTo(46.771f, 50.366f)
                curveTo(43.961f, 50.366f, 41.684f, 52.644f, 41.684f, 55.454f)
                curveTo(41.685f, 58.264f, 43.962f, 60.541f, 46.771f, 60.541f)
                curveTo(49.581f, 60.541f, 51.859f, 58.264f, 51.859f, 55.454f)
                curveTo(51.859f, 52.644f, 49.581f, 50.366f, 46.771f, 50.366f)
                close()
                moveTo(6.581f, 19.586f)
                curveTo(6.019f, 19.586f, 5.563f, 20.042f, 5.563f, 20.604f)
                verticalLineTo(38.944f)
                curveTo(5.563f, 39.506f, 6.019f, 39.961f, 6.581f, 39.961f)
                horizontalLineTo(27.185f)
                curveTo(27.747f, 39.961f, 28.202f, 39.506f, 28.202f, 38.944f)
                verticalLineTo(20.604f)
                curveTo(28.202f, 20.042f, 27.747f, 19.586f, 27.185f, 19.586f)
                horizontalLineTo(6.581f)
                close()
                moveTo(34.815f, 19.586f)
                curveTo(34.254f, 19.586f, 33.799f, 20.042f, 33.799f, 20.604f)
                verticalLineTo(38.944f)
                curveTo(33.799f, 39.506f, 34.254f, 39.961f, 34.815f, 39.961f)
                horizontalLineTo(55.42f)
                curveTo(55.981f, 39.961f, 56.437f, 39.506f, 56.437f, 38.944f)
                verticalLineTo(20.604f)
                curveTo(56.437f, 20.042f, 55.982f, 19.586f, 55.42f, 19.586f)
                horizontalLineTo(34.815f)
                close()
                moveTo(16.741f, 7.631f)
                curveTo(15.766f, 7.631f, 14.975f, 8.428f, 14.975f, 9.411f)
                curveTo(14.975f, 10.394f, 15.766f, 11.191f, 16.741f, 11.191f)
                horizontalLineTo(45.259f)
                curveTo(46.235f, 11.191f, 47.026f, 10.394f, 47.026f, 9.411f)
                curveTo(47.026f, 8.428f, 46.235f, 7.631f, 45.259f, 7.631f)
                horizontalLineTo(16.741f)
                close()
            }
        }
        .build()
        return _imageSubway!!
    }

private var _imageSubway: ImageVector? = null

@Preview
@Composable
private fun Preview(): Unit {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = MyIconPack.ImageSubway, contentDescription = "")
    }
}
