package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha {
    AZUL('A'),
    VERDE('V');

    private final char inicial;

    Ficha (char inicial) {
        this.inicial = inicial;
    }

    @Override
    public String toString() {
        return String.format("%c",name().charAt(0));
    }
}
