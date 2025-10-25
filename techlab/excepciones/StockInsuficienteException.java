package com.techlab.excepciones;

/**
 * Excepción personalizada para manejar casos donde se intenta
 * pedir más stock del disponible.
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String message) {
        super(message);
    }
}