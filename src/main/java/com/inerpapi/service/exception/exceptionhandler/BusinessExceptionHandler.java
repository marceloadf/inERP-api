package com.inerpapi.service.exception.exceptionhandler;

import java.util.List;

import com.inerpapi.constants.ConstantsInErp;
import com.inerpapi.exception.ErrorMessages;
import com.inerpapi.exception.ErrorMessagesUtil;
import com.inerpapi.service.exception.PessoaInexistenteOuInativaException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BusinessExceptionHandler extends ResponseEntityExceptionHandler{

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ PessoaInexistenteOuInativaException.class })
    public ResponseEntity<Object> handlerPessoaInexistenteOuInativaException(RuntimeException ex, WebRequest request) {

        String msgClient = messageSource.getMessage(ConstantsInErp.MENSAGEM_PESSOA_INEXISTENTE_OU_INATIVA, null, LocaleContextHolder.getLocale());
        List<ErrorMessages> errorMessages = ErrorMessagesUtil.createErrorMessages(ex, msgClient);       
        
        return handleExceptionInternal(ex, errorMessages, null, HttpStatus.BAD_REQUEST, request);
    }
}