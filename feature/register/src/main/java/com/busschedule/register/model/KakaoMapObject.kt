package com.busschedule.register.model

import com.busschedule.model.exception.BusStopInfo
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.camera.CameraAnimation
import com.kakao.vectormap.camera.CameraUpdate
import com.kakao.vectormap.camera.CameraUpdateFactory
import com.kakao.vectormap.label.LabelOptions
import com.kakao.vectormap.label.LabelStyle
import com.kakao.vectormap.label.LabelStyles
import com.kakao.vectormap.label.LabelTextBuilder

class KakaoMapObject(val map: KakaoMap? = null) {

    private fun removeAllLabels() = map?.labelManager?.removeAllLabelLayer()
    private var labels: List<BusStopInfo> = emptyList()
    var region: String = ""

    private fun addLabel(icon: Int, text: String, lat: Double, lng: Double) {
        val styles = map?.labelManager?.addLabelStyles(
            LabelStyles.from(
                LabelStyle.from(icon).setTextStyles(20, 0x2E2E34, 20, 0xFFFFFF)
            )
        )
        val options = LabelOptions.from(LatLng.from(lat, lng))
            .setStyles(styles).setTexts(
                LabelTextBuilder().setTexts(text)
            )
        val layer = map?.labelManager?.layer
        layer?.addLabel(options)
    }

    fun removeAndAddLabel(icon: Int, labels: List<BusStopInfo>, region: String) {
        removeAllLabels()
        this.region = region
        this.labels = labels
        this.labels.forEach {
            addLabel(icon, it.name, it.tmX, it.tmY)
        }
        moveCamera(LatLng.from(this.labels.first().tmX, this.labels.first().tmY))
    }
    private fun makeCameraUpdate(x: Double, y: Double, zoomLevel: Int = 16): CameraUpdate {
        return CameraUpdateFactory.newCenterPosition(LatLng.from(x, y), zoomLevel)
    }

    fun moveCamera(latLng: LatLng, isUpCamera: Boolean = false) {
        val lat = if(isUpCamera) -0.000500 else 0.0
        val cameraUpdate = makeCameraUpdate(latLng.latitude + lat, latLng.longitude, 18)

        // 카메라를 지정된 위치로 이동
        map?.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true))
    }

    fun findBusStop(name: String, lat: Double, lng: Double) =
        requireNotNull(labels.find { it.name == name && it.tmX == lat && it.tmY == lng }) {
            "BusStop not found"
        }

    fun getLabels() = labels
}