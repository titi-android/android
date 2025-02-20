package core.designsystem.component.loading

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.busschedule.designsystem.R

@Composable
fun BoxScope.LoadingBusCoil() {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context).data(data = R.raw.loading_bus_coil).apply { size(Size.ORIGINAL) }.build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = "loading_of_coil",
        modifier = Modifier.fillMaxSize(0.5f).align(Alignment.Center)
    )
}

@Composable
fun LoadingOfCoilDialog() {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000).copy(alpha = 0.7f))) {
        LoadingBusCoil()
    }
}