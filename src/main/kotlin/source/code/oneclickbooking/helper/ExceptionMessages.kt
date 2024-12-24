package source.code.oneclickbooking.helper

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
}