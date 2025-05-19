package com.dinlurceis.smartmed.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception!", HttpStatus.INTERNAL_SERVER_ERROR),
    KEY_INVALID(1001, "Invalid message key!", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least 8 characters!", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed!", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated!", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "Do not have permission", HttpStatus.FORBIDDEN),
    UPLOAD_FILE_ERROR(1008, "Can not upload file", HttpStatus.INTERNAL_SERVER_ERROR),
    DELETE_FILE_ERROR(1009, "Can not delete file", HttpStatus.INTERNAL_SERVER_ERROR),
    TOKEN_EXPIRED(1010, "Token expired or you haven't signed in yet", HttpStatus.UNAUTHORIZED),
    WRONG_PASSWORD(1011, "Wrong password", HttpStatus.UNAUTHORIZED),
    PASSWORD_MISMATCH(1012, "Password mismatch", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_FOUND(1013, "Category not found", HttpStatus.NOT_FOUND),
    MEDICINE_NOT_FOUND(1014, "Medicine not found", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(1015, "Address not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(1016, "Cart item not found", HttpStatus.NOT_FOUND),
    QUANTITY_NOT_ENOUGH(1017, "Quantity not enough", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1018, "Order not found", HttpStatus.NOT_FOUND),
    CAN_NOT_CANCEL_ORDER(1019, "Can not cancel order", HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_FOUND(1020, "Schedule not found", HttpStatus.NOT_FOUND ), 
    SCHEDULE_TIME_CONFLICT(1021, "Schedule time conflict", HttpStatus.BAD_REQUEST),
    INVALID_SCHEDULE_TIME(1022, "Invalid schedule time", HttpStatus.BAD_REQUEST), 
    SCHEDULE_CANNOT_UPDATE(1023, "Schedule cannot be updated", HttpStatus.BAD_REQUEST),
    SCHEDULE_TIME_PAST(1024, "Schedule time is past", HttpStatus.BAD_REQUEST ),
    INVALID_STATUS_TRANSITION(1025, "Invalid status transition", HttpStatus.BAD_REQUEST),
    DOCTOR_NOT_FOUND(1026, "Doctor not found", HttpStatus.NOT_FOUND),
    SCHEDULE_TIME_MUST_BE_LESS_THAN_ONE_HOUR(1027, "Schedule time must be less than one hour", HttpStatus.BAD_REQUEST),
    CANNOT_REVIEW_MEDICINE( 1028, "Can not review medicine", HttpStatus.BAD_REQUEST),
    ALREADY_REVIEWED( 1029, "Already reviewed", HttpStatus.BAD_REQUEST),
    INVALID_RATING( 1030, "Invalid rating", HttpStatus.BAD_REQUEST),
    REVIEW_NOT_FOUND( 1031, "Review not found", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND( 1032, "Order item not found", HttpStatus.NOT_FOUND),
    BILL_NOT_FOUND( 1033, "Bill not found", HttpStatus.NOT_FOUND),
    PHARMACIST_NOT_FOUND( 1034, "Pharmacist not found", HttpStatus.NOT_FOUND),
    PHARMACIST_EMAIL_ALREADY_EXISTS( 1035, "Pharmacist email already exists", HttpStatus.BAD_REQUEST),
    PHARMACIST_PHONE_ALREADY_EXISTS( 1036, "Pharmacist phone already exists", HttpStatus.BAD_REQUEST),
    DISEASE_NAME_EXISTS( 1037, "Disease name already exists", HttpStatus.BAD_REQUEST),
    DISEASE_NOT_FOUND( 1038, "Disease not found", HttpStatus.NOT_FOUND),
    SYMPTOM_NOT_FOUND( 1039, "Symptom not found", HttpStatus.NOT_FOUND),
    DISEASE_IN_USE( 1040, "Disease is used in some appointments", HttpStatus.BAD_REQUEST),
    SYMPTOM_NAME_EXISTS( 1041, "Symptom name already exists", HttpStatus.BAD_REQUEST),
    SYMPTOM_IN_USE( 1042, "Symptom is used in some connection with disease", HttpStatus.BAD_REQUEST),
    APPOINTMENT_NOT_FOUND( 1043, "Appointment not found", HttpStatus.NOT_FOUND),
    ACCESS_DENIED( 1044, "Access denied", HttpStatus.FORBIDDEN),
    CANNOT_UPDATE_OTHER_USERS( 1045, "Can not update other users", HttpStatus.BAD_REQUEST),
    SPECIALTY_NOT_FOUND( 1046, "Specialty not found", HttpStatus.NOT_FOUND),
    BODY_PART_NOT_FOUND( 1047, "Body part not found", HttpStatus.NOT_FOUND),
    STATUS_IS_NOT_CONFIRMED( 1048, "Status is not confirmed", HttpStatus.BAD_REQUEST),
    STATUS_IS_NOT_COMPLETED( 1049, "Status is not completed", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }
}
