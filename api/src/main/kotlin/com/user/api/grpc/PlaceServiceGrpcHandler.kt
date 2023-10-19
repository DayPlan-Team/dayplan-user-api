package com.user.api.grpc

import com.user.domain.location.port.PlacePort
import com.user.util.Logger
import com.user.util.exception.UserException
import com.user.util.exceptioncode.SystemExceptionCode
import io.grpc.Status
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import place.Place
import place.PlaceServiceGrpc

@GrpcService
class PlaceServiceGrpcHandler(
    private val placePort: PlacePort
) : PlaceServiceGrpc.PlaceServiceImplBase() {

    override fun getPlace(
        request: Place.GetPlaceRequest,
        responseObserver: StreamObserver<Place.PlaceResponse>
    ) {

        try {
            val placeItems = placePort.getPlaceByIds(request.placeIdsList)
                .map {
                    Place.PlaceItem.newBuilder()
                        .setPlaceName(it.placeName)
                        .setPlaceCategory(Place.PlaceCategory.valueOf(it.placeCategory.name))
                        .setLatitude(it.latitude)
                        .setLongitude(it.longitude)
                        .setAddress(it.address)
                        .setRoadAddress(it.roadAddress)
                        .setPlaceId(it.id)
                        .build()
                }

            val response = Place.PlaceResponse.newBuilder()
                .addAllPlaces(placeItems)
                .build()

            responseObserver.onNext(response)
            responseObserver.onCompleted()

        } catch (e: UserException) {
            log.error("[PlaceService Grpc Handler UserException]", e)
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        } catch (e: Exception) {
            log.error("[PlaceService Grpc Handler Exception]", e)
            responseObserver.onError(
                Status.INTERNAL
                    .withDescription(SystemExceptionCode.NETWORK_SERVER_ERROR.message)
                    .asRuntimeException()
            )
        }
    }

    companion object : Logger()
}