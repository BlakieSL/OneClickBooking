package source.code.oneclickbooking.utils

object ExceptionMessages {
    //validations
    const val UNIQUE_EMAIL = "UniqueEmailDomain.message"
    const val PASSWORD_DIGITS = "PasswordDigitsDomain.message"
    const val PASSWORD_LOWERCASE = "PasswordLowercaseDomain.message"
    const val PASSWORD_SPECIAL = "PasswordSpecialDomain.message"
    const val PASSWORD_UPPERCASE = "PasswordUppercaseDomain.message"
    const val PASSWORD_CHANGE = "PasswordChangeRequireOldPassword.message"
    const val EMAIL_CHANGE = "EmailChangeRequireOldPassword.message"

    //exceptions
    const val RECORD_NOT_FOUND = "RecordNotFoundException.message"
    const val VALIDATION = "ValidationException.message"
    const val INVALID_FILTER_KEY = "InvalidFilterKeyException.message"
    const val INVALID_FILTER_OPERATION = "InvalidFilterOperationException.message"
    const val FILE_PROCESSING = "FileProcessingException.message"

    const val USERNAME_CANNOT_BE_NULL = "IllegalArgumentException.usernameCannotBeNull.message"
    const val OLD_PASSWORD_INCORRECT = "IllegalArgumentException.oldPasswordIncorrect.message"
    const val UPDATE_PAST_BOOKING = "IllegalArgumentException.updatePastBooking.message"
    const val EMPLOYEE_NOT_PROVIDE_TREATMENT = "IllegalArgumentException.employeeNotProvideTreatment.message"
    const val EMPLOYEE_NOT_ASSOCIATED_WITH_SERVICE_POINT = "IllegalArgumentException.employeeNotAssociatedWithServicePoint.message"
    const val EMPLOYEE_HAS_NO_AVAILABILITIES_ON_DATE = "IllegalArgumentException.employeeNotHaveAvailabilitiesOnDay.message"
    const val EMPLOYEE_HAS_NO_AVAILABILITIES_ON_TIME = "IllegalArgumentException.employeeNotHaveAvailabilitiesOnTime.message"
    const val SERVICE_POINT_REQUIRED = "IllegalArgumentException.servicePointRequired.message"
    const val DATE_REQUIRED = "IllegalArgumentException.dateRequired.message"
    const val DATE_IN_PAST = "IllegalArgumentException.dateInPast.message"
    const val UNSUPPORTED_DATE_VALUE_TYPE = "IllegalArgumentException.unsupportedDateValueType.message"
    const val INVALID_DATE_FORMAT = "IllegalArgumentException.invalidDateFormat.message"
    const val BOOKING_NOT_COMPLETED = "IllegalArgumentException.bookingNotCompleted.message"
    const val BOOKING_NOT_PENDING =  "IllegalArgumentException.bookingNotPending.message"

    const val JWT_INVALID_TOKEN = "JwtAuthenticationException.invalidToken.message"
    const val JWT_INVALID_TOKEN_TYPE = "JwtAuthenticationException.invalidTokenType.message"
    const val JWT_SIGNATURE_INVALID = "JwtAuthenticationException.signatureInvalid.message"
    const val JWT_NOT_VERIFIED = "JwtAuthenticationException.notVerified.message"
    const val JWT_EXPIRED_TOKEN = "JwtAuthenticationException.expiredToken.message"
    const val JWT_TOKEN_PARSE_ERROR = "JwtAuthenticationException.tokenParseError.message"

    const val REFRESH_TOKEN_NO_SUBJECT = "InvalidRefreshTokenException.subjectNotProvided.message"
    const val REFRESH_TOKEN_NO_USER_ID = "InvalidRefreshTokenException.userIdNotProvided.message"
    const val REFRESH_TOKEN_NO_AUTHORITIES = "InvalidRefreshTokenException.authoritiesNotProvided.message"

}