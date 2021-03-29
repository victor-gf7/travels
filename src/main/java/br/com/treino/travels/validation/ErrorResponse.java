package br.com.treino.travels.validation;

public class ErrorResponse {

    private final String field;
    private final String error;

    public ErrorResponse(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public String getError() {
        return error;
    }
}
