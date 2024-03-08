package muri.ramos.vendas.exeptions;

public class RecordNotFoundedException extends RuntimeException{
    public RecordNotFoundedException(String message) {
        super(message);
    }
}
