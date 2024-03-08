package muri.ramos.vendas.exeptions;

public class ExpiredTokenException extends RuntimeException{
    public ExpiredTokenException(String message) {
        super("Expired token: " + message);
    }

    public ExpiredTokenException() {
        super("Expired token: " );
    }
}
