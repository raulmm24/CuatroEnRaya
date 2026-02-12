package org.iesalandalus.programacion.cuatroenraya.modelo;

public enum Ficha {

    AZUL,
    VERDE;

    @Override
    public String toString() {
        return String.valueOf(name().charAt(0));
    }
}

