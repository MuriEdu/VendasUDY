package muri.ramos.vendas.rest;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErros {

    @Getter
    private List<String> errors;

    public ApiErros(String message){
        this.errors = Arrays.asList(message);
    }
    public ApiErros(List<String> message){
        this.errors = message;
    }
}