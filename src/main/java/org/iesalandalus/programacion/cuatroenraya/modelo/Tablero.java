package org.iesalandalus.programacion.cuatroenraya.modelo;

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

    public boolean estaVacio() {
        for (int f = 0; f < FILAS; f++) {
            for (int c = 0; c < COLUMNAS; c++) {
                if (casillas[f][c].estaOcupada()) return false;
            }
        }
        return true;
    }

    public boolean estaLleno() {
        for (int c = 0; c < COLUMNAS; c++) {
            if (!columnaLlena(c)) return false;
        }
        return true;
    }

    public boolean columnaLlena(int columna) {
        return casillas[0][columna].estaOcupada();
    }

    public boolean introducirFicha(int columna, Ficha ficha) {
        if (columna < 0 || columna >= COLUMNAS) {
            throw new IllegalArgumentException("Columna incorrecta.");
        }
        if (ficha == null) {
            throw new NullPointerException("La ficha no puede ser nula.");
        }
        if (columnaLlena(columna)) {
            throw new CuatroEnRayaExcepcion("Columna llena.");
        }

        int fila = getPrimeraFilaVacia(columna);
        casillas[fila][columna].setFicha(ficha);

        return comprobarTirada(fila, columna, ficha);
    }

    private int getPrimeraFilaVacia(int columna) {
        for (int f = FILAS - 1; f >= 0; f--) {
            if (!casillas[f][columna].estaOcupada()) return f;
        }
        return -1;
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
                if (consecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) return true;
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
                if (consecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) return true;
            } else {
                consecutivas = 0;
            }
        }
        return false;
    }

    private boolean comprobarDiagonalNE(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(FILAS - 1 - fila, columna);
        int filaInicio = fila + desplazamiento;
        int colInicio = columna - desplazamiento;

        int consecutivas = 0;
        while (filaInicio >= 0 && colInicio < COLUMNAS) {
            if (casillas[filaInicio][colInicio].estaOcupada() && casillas[filaInicio][colInicio].getFicha().equals(ficha)) {
                consecutivas++;
                if (consecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) return true;
            } else {
                consecutivas = 0;
            }
            filaInicio--;
            colInicio++;
        }
        return false;
    }

    private boolean comprobarDiagonalNO(int fila, int columna, Ficha ficha) {
        int desplazamiento = menor(FILAS - 1 - fila, COLUMNAS - 1 - columna);
        int filaInicio = fila + desplazamiento;
        int colInicio = columna + desplazamiento;

        int consecutivas = 0;
        while (filaInicio >= 0 && colInicio >= 0) {
            if (casillas[filaInicio][colInicio].estaOcupada() && casillas[filaInicio][colInicio].getFicha().equals(ficha)) {
                consecutivas++;
                if (consecutivas == FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS) return true;
            } else {
                consecutivas = 0;
            }
            filaInicio--;
            colInicio--;
        }
        return false;
    }

    private int menor(int a, int b) {
        return Math.min(a, b);
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
