package com.busschedule.util.entity

import androidx.compose.ui.graphics.Color

enum class BusType(val type: String, val color:  Color, val colorT1: Color, val colorT2: Color, val iconColor: Color) {
    인천("인천", Color(0xFFE65F51), Color(0xFF62261C), Color(0xFFF5B9AF), Color(0xFFFF7961)),
    경기지선("경기 지선", Color(0xFF64E5D9), Color(0xFF2B605B), Color(0xFFB7EDE8), Color(0xFF83EAE1)),
    간선("간선", Color(0xFF3C89F5), Color(0xFF004099), Color(0xFFCDE2FF), Color(0xFF4F95F6)),
    지선("지선", Color(0xFF1DC26A), Color(0xFF206B3D), Color(0xFFD2FFE3), Color(0xFF34C879 )),
    일반("일반", Color(0xFF1DC26A), Color(0xFF206B3D), Color(0xFFD2FFE3), Color(0xFF34C879 )),
    마을("마을", Color(0xFF43D47B ), Color(0xFF27844B), Color(0xFFC3F7D7 ), Color(0xFF5FDA8F )),
    급행("급행", Color(0xFFE83123 ), Color(0xFF7A0000), Color(0xFFFFDDD9 ), Color(0xFFE86F62 )),
    광역("광역", Color(0xFFE83123 ), Color(0xFF7A0000), Color(0xFFFFDDD9 ), Color(0xFFE86F62 )),
    직행("직행", Color(0xFFE83123 ), Color(0xFF7A0000), Color(0xFFFFDDD9 ), Color(0xFFE86F62 )),
    순환("순환", Color(0xFFF4C24F ), Color(0xFF6E5824), Color(0xFFFFEDC4 ), Color(0xFFF5C860 )),
    폐지("폐지", Color(0xFF7C7C7C ), Color(0xFF434343), Color(0xFFC4C4C4 ), Color(0xFFA2A2A2 ));

    companion object {
        fun find(type: String) =
            requireNotNull(BusType.entries.find { it.type == type }) {
                "일치하는 버스의 종류가 없습니다."
            }
    }
}