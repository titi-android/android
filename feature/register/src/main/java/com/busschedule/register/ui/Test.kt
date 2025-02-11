//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import com.busschedule.domain.model.response.busstop.BusStopInfoResponse
//import com.busschedule.register.ui.addLabel
//import com.busschedule.register.ui.makeCameraUpdate
//import com.kakao.vectormap.KakaoMap
//import com.kakao.vectormap.KakaoMapReadyCallback
//import com.kakao.vectormap.LatLng
//import com.kakao.vectormap.camera.CameraAnimation
//import com.kakao.vectormap.label.LabelOptions
//import com.kakao.vectormap.label.LabelStyle
//import com.kakao.vectormap.label.LabelStyles
//import com.kakao.vectormap.label.LabelTextBuilder
//import core.designsystem.R
//
//@Composable
//fun Test() {
//    DisposableEffect {
//        onDispose {  }
//    }
//}
//
//
//class KakaoMapReadyCallbackImpl(private val startLatLng: LatLng, val firstBusStop: List<BusStopInfoResponse>): KakaoMapReadyCallback() {
//    lateinit var kakaoMap: KakaoMap
//    override fun onMapReady(kakaoMap: KakaoMap) {
//        this.kakaoMap = kakaoMap
//        var cameraUpdate = makeCameraUpdate(
//            startLatLng.latitude,
//            startLatLng.longitude
//        )
//        // 카메라를 지정된 위치로 이동
//        kakaoMap.moveCamera(cameraUpdate)
//
//        firstBusStop.forEach {
//            addLabel(
//                kakaoMap = kakaoMap,
//                icon = R.drawable.image_busstop_label,
//                text = it.name,
//                it.tmX,
//                it.tmY
//            )
//        }
//
//        kakaoMap.setOnLabelClickListener { kakaoMap, labelLayer, label ->
//            val t = findBusStop(
//                busStop = firstBusStop,
//                name = label.texts.first(),
//                lat = label.position.latitude,
//                lng = label.position.longitude
//            )
//            cameraUpdate = makeCameraUpdate(
//                label.position.latitude,
//                label.position.longitude,
//                18
//            )
//            kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
//            false
//        }
//
//    }
//
//    fun addLabel(kakaoMap: KakaoMap, icon: Int, text: String, lat: Double, lng: Double) {
//        val styles = kakaoMap.labelManager?.addLabelStyles(
//            LabelStyles.from(
//                LabelStyle.from(icon).setTextStyles(20, 0x2E2E34, 20, 0xFFFFFF)
//            )
//        )
//        val options = LabelOptions.from(LatLng.from(lat, lng))
//            .setStyles(styles).setTexts(
//                LabelTextBuilder().setTexts(text)
//            )
//        val layer = kakaoMap.labelManager?.layer
//        layer?.addLabel(options)
//    }
//
//    fun findBusStop(busStop: List<BusStopInfoResponse>, name: String, lat: Double, lng: Double) =
//        requireNotNull(busStop.filter { it.name == name }.find { it.tmX == lat && it.tmY == lng }) {
//            "BusStop not found"
//        }
//}