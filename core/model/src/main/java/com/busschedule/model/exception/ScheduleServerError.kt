package com.busschedule.model.exception

enum class ScheduleServerError(val code: String, val exception: Exception) {
//    /** Common Error Code */
//    BAD_REQUEST_ERROR(BadRequestException()),
//    INVALID_INPUT_VALUE_ERROR(InvalidInputValueException()),
//    INVALID_TYPE_VALUE_ERROR(InvalidTypeValueException()),
//    METHOD_NOT_ALLOWED_ERROR(MethodNotAllowedException()),
//    INVALID_MEDIA_TYPE_ERROR(InvalidMediaTypeException()),
//    QUERY_DSL_NOT_EXISTS_ERROR(QueryDslNotExistsException()),
//    COROUTINE_CANCELLATION_ERROR(CoroutineCancellationException()),
//    NO_AUTHORITY_ERROR(NoAuthorityException()),

    /** User Error Code */
    USER_ALREADY_NAME("USER401", AlreadyRegisteredUserException()),
    USER_NOT_FOUND("USER402", UserNotFoundException()),
    USER_INVALID_PASSWORD("USER403", InvalidUserPasswordException()),
    USER_UNAUTHORIZED("USER404", NotAuthorizedUserException()),

    /** REFRESH_TOKEN **/
    REFRESH_TOKEN_NOT_FOUND("REFRESH401", RefreshTokenNotFoundException()),
    REFRESH_TOKEN_INVALID("REFRESH402", RefreshTokenInvalidException()),
    REFRESH_TOKEN_EXPIRED("REFRESH403", RefreshTokenExpiredException()),

    /** REGION_NAME **/
    CITY_CODE_NOT_FOUND("CITY_CODE401", CityCodeNotFoundException()),

    /** BUS_STOP **/
    BUS_STOP_NOT_FOUND("BUS_STOP401", BusStopNotFoundException()),

    /** SCHEDULE **/
    SCHEDULE_NOT_FOUND("SCHEDULE401", ScheduleNotFoundException()),
    SCHEDULE_CONFLICT("SCHEDULE402", ScheduleConflictException()),

    /** ACCESS_TOKEN **/
    ACCESS_TOKEN_EXPIRED("JWT401", AccessTokenExpiredException()),
    ACCESS_TOKEN_SIGNATURE_INVALID( "JWT402", AccessTokenSignatureInvalidException()),
    ACCESS_TOKEN_MALFORMED("JWT403", AccessTokenMalformedException()),
    ACCESS_TOKEN_UNSUPPORTED("JWT404", AccessTokenUnsupportedException()),
    ACCESS_TOKEN_ILLEGAL_ARGUMENT( "JWT405", AccessTokenIllegalArgumentException()),
    ACCESS_TOKEN_USER_NOT_FOUND( "JWT406", AccessTokenUserNotFoundException()),

    /** ETC **/
    UNSUPPORTED_ENCODING("UNSUPPORTED_ENCODING", UnsupportedEncoding());

    companion object {
        fun find(code: String) =
            requireNotNull(ScheduleServerError.entries.find { it.code == code }) {
                "일치하는 에러의 종류가 없습니다."
            }
    }
}

/** Common Exception Code */
//class BadRequestException(
//    override val message: String = "bad request",
//) : RuntimeException()
//
//class InvalidInputValueException(
//    override val message: String = "input is invalid value",
//) : RuntimeException()
//
//class InvalidTypeValueException(
//    override val message: String = "invalid type value",
//) : RuntimeException()
//
//class MethodNotAllowedException(
//    override val message: String = "Method type is invalid",
//) : RuntimeException()
//
//class InvalidMediaTypeException(
//    override val message: String = "invalid media type",
//) : RuntimeException()
//
//class QueryDslNotExistsException(
//    override val message: String = "not found query dsl",
//) : RuntimeException()
//
//class CoroutineCancellationException(
//    override val message: String = "coroutine cancellation Exception",
//) : RuntimeException()
//
//class NoAuthorityException(
//    override val message: String = "수정 권한이 없습니다.",
//) : RuntimeException()

/** User Exception Code */
class UserNotFoundException(
    override val message: String = "유저 정보를 찾을 수 없습니다.",
) : RuntimeException()

class AlreadyRegisteredUserException(
    override val message: String = "이미 가입된 유저입니다.",
) : RuntimeException()

class InvalidUserPasswordException(
    override val message: String = "비밀번호가 일치하지 않습니다.",
) : RuntimeException()

class NotAuthorizedUserException(
    override val message: String = "사용자의 권한이 없습니다.",
) : RuntimeException()

/** REFRESH_TOKEN **/
class RefreshTokenNotFoundException(
    override val message: String = "리프레시 토큰이 DB에 존재하지 않습니다.",
) : RuntimeException()

class RefreshTokenInvalidException(
    override val message: String = "해당 유저에 등록된 리프레시과 일치하지 않습니다."
) : RuntimeException()

class RefreshTokenExpiredException(
    override val message: String = "리프레시 토큰이 만료되었습니다.",
) : RuntimeException()

/** REGION_NAME **/
class CityCodeNotFoundException(
    override val message: String = "해당 이름과 매칭되는 도시코드가 존재하지 않습니다.",
) : RuntimeException()

/** BUS_STOP **/
class BusStopNotFoundException(
    override val message: String = "해당 이름을 포함하는 버스정류장이 존재하지 않습니다.",
) : RuntimeException()

/** SCHEDULE **/
class ScheduleNotFoundException(
    override val message: String = "해당 ID의 스케줄이 존재하지 않습니다.",
) : RuntimeException()

class ScheduleConflictException(
    override val message: String = "겹치는 스케줄이 존재합니다.",
) : RuntimeException()

/** ACCESS_TOKEN **/
class AccessTokenExpiredException(
    override val message: String = "엑세스 토큰이 만료되었습니다.",
) : RuntimeException()

class AccessTokenSignatureInvalidException(
    override val message: String = "엑세스 토큰의 서명이 유효하지 않습니다.",
) : RuntimeException()

class AccessTokenMalformedException(
    override val message: String = "엑세스 토큰 형식이 올바르지 않습니다.",
) : RuntimeException()

class AccessTokenUnsupportedException(
    override val message: String = "지원되지 않는 엑세스 토큰입니다.",
) : RuntimeException()

class AccessTokenIllegalArgumentException(
    override val message: String = "잘못된 엑세스 토큰입니다."
): RuntimeException()

class AccessTokenUserNotFoundException(
    override val message: String = "JWT 를 통해 추출한 유저의 ID가 DB에 존재하지 않습니다."
): RuntimeException()

/** ETC **/
class UnsupportedEncoding(
    override val message: String = "지원되지 않는 인코딩 형식입니다.",
): RuntimeException()
