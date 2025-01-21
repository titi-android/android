package com.busschedule.register.constant

enum class Region(val value: String, val cities: List<City>) {

    METROPOLITAN(
        "특별, 광역시",
        listOf(
            City.SEOUL,
            City.SEJONG,
            City.BUSAN,
            City.DAEGU,
            City.INCHEON,
            City.GWANGJU,
            City.DAEJEON,
            City.ULSAN,
            City.GYERYONG,
            City.JEJU
        )
    ),

    // [제주]
    JEJU("제주도", listOf(City.JEJU)),

    GYEONGGUDO(
    "경기도",
    listOf(
    City.SUWON,
    City.SEONGNAM,
    City.UIJEONGBU,
    City.ANYANG,
    City.BUCHEON,
    City.GWANGMYEONG,
    City.PYEONGTAEK,
    City.DONGDUCHEON,
    City.ANSAN,
    City.GOYANG,
    City.GWACHEON,
    City.GURI,
    City.NAMYANGJU,
    City.OSAN,
    City.SIHEUNG,
    City.GUNPO,
    City.UIWANG,
    City.HANAM,
    City.YONGIN,
    City.PAJU,
    City.ICHEON,
    City.ANSEONG,
    City.GIMPO,
    City.HWAHSEONG,
    City.GWANGJU_GYEONGGI,
    City.YANGJU,
    City.POCHEON,
    City.YEOJU,
    City.YEONCHEON,
    City.GAPYEONG,
    City.YANGPYEONG

    )
    ),

    GANGWONDO(
    "강원도",
    listOf(
    City.CHUNCHEON,
    City.WONJU,
    City.HONGCHEON,
    City.TAEBAEK,
    City.HOENGSEONG,
    City.CHEORWON,
    City.YANGYANG
    )
    ),

    CHUNGCHEONGNAMDO(
    "충청남도", listOf(
    City.CHEONAN,
    City.GONGJU,
    City.ASAN,
    City.SEOSAN,
    City.NONSAN,
    City.BUYEOGUN,
    City.DANYANG
    )
    ),

    CHUNGCHEONGBOKDO(
    "충청북도", listOf(
    City.CHEONGJU,
    City.CHUNGJU,
    City.JECHON,
    City.BOEUN,
    City.OKCHEON,
    City.YEONGDONG,
    City.JINCHEON,
    City.GWAESAN,
    City.EUMSEONG,
    City.DANYANG
    )
    ),

    JEOLLABUKDO(
    "전라북도", listOf(
    City.JEONJU,
    City.GUNSAN,
    City.JEONGEUP,
    City.NAMWON,
    City.GIMJE,
    City.JINAN,
    City.MUJU,
    City.JANGSU,
    City.IMSIL,
    City.SUNCHEON,
    City.GOCHANG,
    City.BUAN
    )
    ),
    JEOLLANAMDO("전라남도", listOf(City.MOKPO, City.YEOSU, City.NAKSAN, City.GWANGYANG))
}
