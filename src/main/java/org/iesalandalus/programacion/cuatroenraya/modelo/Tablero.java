package org.iesalandalus.programacion.cuatroenraya.modelo;

import java.util.Objects;

public class Tablero {

    public static final int FILAS = 6;
    public static final int COLUMNAS = 7;
    public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;

    private final Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    public boolean columnaVacia(int columna) {
        comprobarColumna(columna);
        return !casillas[FILAS - 1][columna].estaOcupada();
    }

    public boolean estaVacio() {
        for (int c = 0; c < COLUMNAS; c++) {
            if (!columnaVacia(c)) return false;
        }
        return true;
    }

    public boolean columnaLlena(int columna) {
        comprobarColumna(columna);
        return casillas[0][columna].estaOcupada();
    }

    public boolean estaLleno() {
        for (int c = 0; c < COLUMNAS; c++) {
            if (!columnaLlena(c)) return false;
        }
        return true;
    }

    private void comprobarFicha(Ficha ficha) {
        Objects.requireNonNull(ficha, "La ficha no puede ser nula.");
    }

    private void comprobarColumna(int columna) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int f = FILAS - 1; f >= 0; f--) {
            if (!casillas[f][columna].estaOcupada()) return f;
        }
        return -1;
    }

    private boolean objetivoAlcanzado(int consecutivas) {
        return consecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
    }

    public boolean introducirFicha(int columna, Ficha ficha) {
        comprobarColumna(columna);
        comprobarFicha(ficha);

        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);

        return comprobarTirada(fila, columna, ficha);
    }

    private boolean comprobarTirada(int fila, int columna, Ficha ficha) {
        return comprobarHorizontal(fila, ficha) ||
                comprobarVertical(columna, ficha) ||
                comprobarDiagonalNE(fila, columna, ficha) ||
                comprobarDiagonalNO(fila, columna, ficha);
    }

    private boolean comprobarHorizontal(int fila, Ficha ficha) {
        int consecutivas = 0;
        for (int c = 0; c < COLUMNAS; c++) {
            if (casillas[fila][c].estaOcupada() && casillas[fila][c].getFicha().equals(ficha)) {
                consecutivas++;
                if (objetivoAlcanzado(consecutivas)) return true;
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    private boolean comprobarVertical(int columna, Ficha ficha) {
        int consecutivas = 0;
        for (int f = 0; f < FILAS; f++) {
            if (casillas[f][columna].estaOcupada() && casillas[f][columna].getFicha().equals(ficha)) {
                consecutivas++;
                if (objetivoAlcanzado(consecutivas)) return true;
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    private int menor(int a, int b) {
        return Math.min(a, b);
    }

    private boolean comprobarDiagonalNE(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(fila, columna);
        int filaInicio = fila - desplazamiento;
        int colInicio = columna - desplazamiento;

        int consecutivas = 0;
        for (int f = filaInicio, c = colInicio; f < FILAS && c < COLUMNAS; f++, c++) {
            if (casillas[f][c].estaOcupada() && casillas[f][c].getFicha().equals(ficha)) {
                consecutivas++;
                if (objetivoAlcanzado(consecutivas)) return true;
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    private boolean comprobarDiagonalNO(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(fila, COLUMNAS - 1 - columna);
        int filaInicio = fila - desplazamiento;
        int colInicio = columna + desplazamiento;

        int consecutivas = 0;
        for (int f = filaInicio, c = colInicio; f < FILAS && c >= 0; f++, c--) {
            if (casillas[f][c].estaOcupada() && casillas[f][c].getFicha().equals(ficha)) {
                consecutivas++;
                if (objetivoAlcanzado(consecutivas)) return true;
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int f = 0; f < FILAS; f++) {
            sb.append("|");
            for (int c = 0; c < COLUMNAS; c++) {
                sb.append(casillas[f][c].toString());
            }
            sb.append("|\n");
        }
        sb.append(" -------\n");
        return sb.toString();
    }
}