package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Casilla {

    private Ficha ficha;

    public Casilla() {
        this.ficha = null;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public void setFicha(Ficha ficha) throws CuatroEnRayaExcepcion {
        Objects.requireNonNull(ficha, "No se puede poner una ficha nula.");

        if (estaOcupada()) {
            throw new CuatroEnRayaExcepcion("La casilla ya contiene una ficha.");
        }

        this.ficha = ficha;
    }

    public boolean estaOcupada() {
        return ficha != null;
    }

    @Override
    public String toString() {
        // Devuelve " " si es null, o el toString de la ficha ("A" o "V") si está ocupada
        return (!estaOcupada()) ? " " : ficha.toString();
    }
}