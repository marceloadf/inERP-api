package com.inerpapi.exception.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.inerpapi.constants.ConstantsInErp;
import com.inerpapi.exception.ErrorMessages;
import com.inerpapi.exception.ErrorMessagesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class InerpApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        String msgDev = Optional.ofNullable(ex.getCause()).orElse(ex).toString();
        String msgClient = messageSource.getMessage(ConstantsInErp.MESANGEM_INVALIDA, null, LocaleContextHolder.getLocale());
        List<ErrorMessages> errorMessages = Arrays.asList(new ErrorMessages(msgDev, msgClient));

        return super.handleExceptionInternal(ex, errorMessages, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<ErrorMessages> errorMessages = createListErrors(ex.getBindingResult());
        return handleExceptionInternal(ex, errorMessages, headers, status, request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(RuntimeException ex, WebRequest request){

        String msgClient = messageSource.getMessage(ConstantsInErp.MENSAGEM_RECURSO_NAO_EXISTE, null, LocaleContextHolder.getLocale());
        List<ErrorMessages> errorMessages = ErrorMessagesUtil.createErrorMessages(ex, msgClient);

        return handleExceptionInternal(ex, errorMessages, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccessException(RuntimeException ex, WebRequest request) {

        String msgClient = messageSource.getMessage(ConstantsInErp.MENSAGEM_RECURSO_OPERACAO_NAO_PERMITIDA, null, LocaleContextHolder.getLocale());
        List<ErrorMessages> errorMessages = ErrorMessagesUtil.createErrorMessages(ex, msgClient);

        return handleExceptionInternal(ex, errorMessages, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    
    private List<ErrorMessages> createListErrors(BindingResult bindingResult) {
        List<ErrorMessages> errorMessages = new ArrayList<>();

        for (FieldError fiedlError : bindingResult.getFieldErrors()) {
            String msgClient = messageSource.getMessage(fiedlError, LocaleContextHolder.getLocale());
            String msgDev = fiedlError.toString();
            errorMessages.add(new ErrorMessages(msgClient, msgDev));
        }

        return errorMessages;
    }
}