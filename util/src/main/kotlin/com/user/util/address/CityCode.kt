package com.user.util.address

enum class CityCode(
    val code: Long,
    val koreanName: String,
) {
    SEOUL(11, "서울"),
    BUSAN(21, "부산"),
    DAEGU(22, "대구"),
    INCHEON(23, "인천"),
    GWANGJU(24, "광주"),
    DAEJEON(25, "대전"),
    ULSAN(26, "울산"),
    SEJONG(29, "세종"),
    GYEONGGI(31, "경기"),
    GANGWON(32, "강원"),
    CHEONGBUK(33, "충북"),
    CHEONGNAM(34, "충남"),
    JEONNAM(35, "전남"),
    JEONBUK(36, "전북"),
    GYEONGBUK(37, "경북"),
    GYEONGNAM(38, "경남"),
    JEJU(39, "제주"),
}