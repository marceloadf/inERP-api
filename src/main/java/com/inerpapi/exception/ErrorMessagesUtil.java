package com.inerpapi.exception;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class ErrorMessagesUtil {

    public static List<ErrorMessages> createErrorMessages(RuntimeException ex, String msgClient) {
        
        String msgDev = ExceptionUtils.getRootCauseMessage(ex);//Optional.ofNullable(ex.getCause()).orElse(ex).toString();
        List<ErrorMessages> errorMessages = Arrays.asList(new ErrorMessages(msgDev, msgClient));
        return errorMessages;
    }
}