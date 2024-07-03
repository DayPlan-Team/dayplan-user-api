package com.user.api.publics

import com.user.util.exception.UserException
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec

class CoordinatesVerifierTest : FunSpec({

    isolationMode = IsolationMode.InstancePerLeaf

    context("접점에 속한 좌표들이 주어져요") {
        val possibleCoordinates =
            listOf(
                39.11 to 129.30,
                37.48 to 131.60,
                34.75 to 129.26,
                33.83 to 128.91,
                32.60 to 125.16,
                34.46 to 124.16,
                37.66 to 124.98,
            )

        test("접점은 예외 발생없이 통과해요") {
            shouldNotThrow<UserException> {
                possibleCoordinates.forEach {
                    CoordinatesVerifier.verifyCoordinates(it)
                }
            }
        }
    }

    context("접점에 밖에 속한 거점이 주어져요") {
        val possibleCoordinates =
            listOf(
                39.11 to 129.31,
                39.12 to 129.30,
                37.48 to 131.61,
                37.49 to 131.60,
                34.74 to 129.26,
                34.75 to 129.27,
                33.83 to 128.92,
                33.81 to 128.91,
                32.60 to 125.17,
                32.59 to 125.16,
                34.46 to 124.15,
                34.47 to 124.16,
                37.66 to 124.97,
                37.67 to 124.98,
            )

        test("거점은 예외가 발생해야해요") {
            shouldThrow<UserException> {
                possibleCoordinates.forEach {
                    CoordinatesVerifier.verifyCoordinates(it)
                }
            }
        }
    }

    context("접점 내부 점이 주어져요") {
        val possibleCoordinates =
            listOf(
                39.10 to 129.30,
                37.48 to 131.59,
                34.76 to 129.26,
                33.84 to 128.91,
                32.61 to 125.16,
                34.46 to 124.17,
                37.65 to 124.98,
            )

        test("내부 점은 예외가 발생하면 안돼요") {
            shouldNotThrow<UserException> {
                possibleCoordinates.forEach {
                    CoordinatesVerifier.verifyCoordinates(it)
                }
            }
        }
    }
})
