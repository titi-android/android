package com.busschedule.register.constant

enum class City(val value: String) {
    // 서울특별시
    SEOUL("서울특별시"),

    // 세종특별시
    SEJONG("세종특별시"),

    // [광역시]
    BUSAN("부산광역시"),
    DAEGU("대구광역시"),
    INCHEON("인천광역시"),
    GWANGJU("광주광역시"),
    DAEJEON("대전광역시"),
    ULSAN("울산광역시"),
    GYERYONG("계룡시"),

    // [제주]
    JEJU("제주도"),

    // [경기도]
    SUWON("수원시"),
    SEONGNAM("성남시"),
    UIJEONGBU("의정부시"),
    ANYANG("안양시"),
    BUCHEON("부천시"),
    GWANGMYEONG("광명시"),
    PYEONGTAEK("평택시"),
    DONGDUCHEON("동두천시"),
    ANSAN("안산시"),
    GOYANG("고양시"),
    GWACHEON("과천시"),
    GURI("구리시"),
    NAMYANGJU("남양주시"),
    OSAN("오산시"),
    SIHEUNG("시흥시"),
    GUNPO("군포시"),
    UIWANG("의왕시"),
    HANAM("하남시"),
    YONGIN("용인시"),
    PAJU("파주시"),
    ICHEON("이천시"),
    ANSEONG("안성시"),
    GIMPO("김포시"),
    HWAHSEONG("화성시"),
    GWANGJU_GYEONGGI("광주시"),
    YANGJU("양주시"),
    POCHEON("포천시"),
    YEOJU("여주시"),
    YEONCHEON("연천군"),
    GAPYEONG("가평군"),
    YANGPYEONG("양평군"),

    // [강원도]
    CHUNCHEON("춘천시"),
    WONJU("원주시"),
    HONGCHEON("홍천군"),
    TAEBAEK("태백시"),
    HOENGSEONG("횡성군"),
    CHEORWON("철원군"),
    YANGYANG("양양군"),

    // [충청북도]
    CHEONGJU("청주시"),
    CHUNGJU("충주시"),
    JECHON("제천시"),
    BOEUN("보은군"),
    OKCHEON("옥천군"),
    YEONGDONG("영동군"),
    JINCHEON("진천군"),
    GWAESAN("괴산군"),
    EUMSEONG("음성군"),
    DANYANG("단양군"),

    // [충청남도]
    CHEONAN("천안시"),
    GONGJU("공주시"),
    ASAN("아산시"),
    SEOSAN("서산시"),
    NONSAN("논산시"),
    BUYEOGUN("부여군"),
    DANGJIN("당진시"),

    // [전라북도]
    JEONJU("전주시"),
    GUNSAN("군산시"),
    JEONGEUP("정읍시"),
    NAMWON("남원시"),
    GIMJE("김제시"),
    JINAN("진안군"),
    MUJU("무주군"),
    JANGSU("장수군"),
    IMSIL("임실군"),
    SUNCHEON("순천시"),
    GOCHANG("고창군"),
    BUAN("부안군"),

    // [전라남도]
    MOKPO("목포시"),
    YEOSU("여수시"),
    NAKSAN("나주시"),
    GWANGYANG("광양시");
}