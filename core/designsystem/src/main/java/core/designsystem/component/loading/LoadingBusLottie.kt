package core.designsystem.component.loading

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LoadingBusLottie(isShow: Boolean = false) {
    val preLoaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(com.busschedule.designsystem.R.raw.loading_bus_coil)

    )
    //애니메이션 동작 설정
    val preLoaderProgress = animateLottieCompositionAsState(
        preLoaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isShow
    )

    LottieAnimation(
        composition = preLoaderLottieComposition,
        progress = preLoaderProgress.value,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun LoadingDialog(isLoading: Boolean = false) {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF000000).copy(alpha = 0.7f))) {
        LoadingBusLottie(isLoading)
    }
}