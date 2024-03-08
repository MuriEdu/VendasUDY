package muri.ramos.vendas.rest.controller;

import muri.ramos.vendas.exeptions.BusinessRuleException;
import muri.ramos.vendas.exeptions.InvalidPasswordException;
import muri.ramos.vendas.exeptions.RecordNotFoundedException;
import muri.ramos.vendas.rest.ApiErros;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRuleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleBusinesException(BusinessRuleException ex){
        String message = ex.getMessage();
        return new ApiErros(message);
    }

    @ExceptionHandler(RecordNotFoundedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErros handleRecordNotFoundedException(RecordNotFoundedException ex){
        String message = ex.getMessage();
        return new ApiErros(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> message = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map( error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        return new ApiErros(message);
    }




}
