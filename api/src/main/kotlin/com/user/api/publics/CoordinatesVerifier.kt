package com.user.api.publics

import com.user.util.exception.UserException
import com.user.util.exceptioncode.UserExceptionCode
import org.springframework.stereotype.Component

@Component
object CoordinatesVerifier {

    private val polygon = listOf(
        39.11 to 129.30,
        37.48 to 131.60,
        34.75 to 129.26,
        33.83 to 128.91,
        32.60 to 125.16,
        34.46 to 124.16,
        37.66 to 124.98,
    )

    fun verifyCoordinates(p: Pair<Double, Double>) {
        var intersection = 0

        for (i in polygon.indices) {
            val point1 = polygon[i]
            val point2 = polygon[(i + 1) % polygon.size]

            if (p == point1 || p == point2) return

            if (p.second > minOf(point1.second, point2.second) && p.second <= maxOf(point1.second, point2.second)) {

                val xIntersection =
                    point1.first + (p.second - point1.second) * (point2.first - point1.first) / (point2.second - point1.second)

                if (p.first < xIntersection) {
                    intersection++
                }
            }
        }

        require(intersection % 2 == 1) { throw UserException(UserExceptionCode.BAD_REQUEST_COORDINATES) }
    }
}