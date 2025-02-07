package org.iesalandalus.programacion.cuatroenraya.modelo;

    public class Tablero {
        public static final int FILAS = 6;
        public static final int COLUMNAS = 7;
        public static final int FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS = 4;
        private final Casilla[][] casillas;

        public Tablero() {
            casillas = new Casilla[FILAS][COLUMNAS];

            for (int fila = 0; fila < FILAS; fila++) {
                for (int columna = 0; columna < COLUMNAS; columna++) {
                    casillas[fila][columna] = new Casilla();
                }
            }
        }

        public boolean estaVacio() {
            for (int i = 0; i < COLUMNAS; i++) {
                if (!columnaVacia(i)) {
                    return false;
                }
            }
            return true;
        }

        private boolean columnaVacia(int columna) {
            if (columna < 0 || columna >= COLUMNAS) {
                throw new IllegalArgumentException("Columna incorrecta.");
            }

            for (int fila = 0; fila < FILAS; fila++) {
                if (casillas[fila][columna].estaOcupada()) {
                    return false;
                }
            }
            return true;
        }

        public boolean estaLleno() {
            for (int i = 0; i < COLUMNAS; i++) {
                if (columnaVacia(i)) {
                    return false;
                }
            }
            return true;
        }

        public boolean columnaLlena(int columna) {
            for (int fila = 0; fila < casillas.length; fila++) {
                if (!casillas[fila][columna].estaOcupada()) {
                    return false;
                }
            }
            return true;
        }

        private void comprobarFicha(Ficha ficha) {
            if (ficha == null) {
                throw new NullPointerException("La ficha no puede ser nula.");
            }
        }

        private void comprobarColumna(int columna) {
            if (columna < 0 || columna >= COLUMNAS) {
                throw new IllegalArgumentException("Columna incorrecta.");
            }
        }

        private int getPrimeraFilaVacia(int columna) {
            comprobarColumna(columna);

            for (int fila = FILAS - 1; fila >= 0; fila--) {
                if (!casillas[fila][columna].estaOcupada()) {
                    return fila;
                }
            }
            return 0;
        }

        private boolean objetivoAlcanzado(int fichasIgualesConsecutivas) {
            return fichasIgualesConsecutivas >= FICHAS_IGUALES_CONSECUTIVAS_NECESARIAS;
        }

        private boolean comprobarHorizontal(int fila, Ficha ficha) {
            comprobarFicha(ficha);
            int contador = 0;
            for (int columna = 0; columna < COLUMNAS; columna++) {
                if (casillas[fila][columna].getFicha() != null && casillas[fila][columna].getFicha().equals(ficha)) {
                    contador++;
                    if (objetivoAlcanzado(contador)) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
            return false;
        }

        private boolean comprobarVertical(int columna, Ficha ficha) {
            comprobarFicha(ficha);
            comprobarColumna(columna);
            int contador = 0;
            for (int fila = FILAS - 1; fila >= 0; fila--) {
                if (casillas[fila][columna].getFicha() != null && casillas[fila][columna].getFicha().equals(ficha)) {
                    contador++;
                    if (objetivoAlcanzado(contador)) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
            return false;
        }

        private int menor(int fila, int columna) {
            return (fila < columna) ? fila : columna;
        }

        private boolean comprobarDiagonalNE(int filaActual, int columnaActual, Ficha ficha) {
            comprobarFicha(ficha);
            comprobarColumna(columnaActual);

            int desplazamiento = menor(filaActual, columnaActual);
            int filaInicial = filaActual - desplazamiento;
            int columnaInicial = columnaActual - desplazamiento;
            int contador = 0;

            for (int i = 0; filaInicial + i < FILAS && columnaInicial + i < COLUMNAS; i++) {
                if (casillas[filaInicial + i][columnaInicial + i].getFicha() != null &&
                        casillas[filaInicial + i][columnaInicial + i].getFicha().equals(ficha)) {
                    contador++;
                    if (objetivoAlcanzado(contador)) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
            return false;
        }

        private boolean comprobarDiagonalNO(int filaActual, int columnaActual, Ficha ficha) {
            comprobarFicha(ficha);
            comprobarColumna(columnaActual);

            int desplazamiento = menor(filaActual, COLUMNAS - 1 - columnaActual);
            int filaInicial = filaActual - desplazamiento;
            int columnaInicial = columnaActual + desplazamiento;
            int contador = 0;

            for (int i = 0; filaInicial + i < FILAS && columnaInicial - i >= 0; i++) {
                if (casillas[filaInicial + i][columnaInicial - i].getFicha() != null && casillas[filaInicial + i][columnaInicial - i].getFicha().equals(ficha)) {
                    contador++;
                    if (objetivoAlcanzado(contador)) {
                        return true;
                    }
                } else {
                    contador = 0;
                }
            }
            return false;
        }

        private boolean comprobarTirada(int fila, int columna) {
            if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS) {
                throw new IllegalArgumentException("Fila o columna fuera de rango");
            }

            Ficha ficha = casillas[fila][columna].getFicha();
            if (ficha == null) {
                return false;
            }

            return comprobarHorizontal(fila,ficha) ||
                    comprobarVertical(columna, ficha) ||
                    comprobarDiagonalNE(fila, columna, ficha) ||
                    comprobarDiagonalNO(fila, columna, ficha);
        }

        public boolean introducirFicha(int columna, Ficha ficha) throws CuatroEnRayaExcepcion {
            comprobarFicha(ficha);
            comprobarColumna(columna);

            int filaDisponible = getPrimeraFilaVacia(columna);
            if (filaDisponible == -1) {
                throw new IllegalArgumentException("Columna " + columna + "llena.");
            }

            casillas[filaDisponible][columna].setFicha(ficha);

            return comprobarTirada(filaDisponible, columna);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();


            for (int i = 0; i < FILAS; i++) {
                sb.append("|");
                for (int j = 0; j < COLUMNAS; j++) {
                    if (casillas[i][j].estaOcupada()) {
                        sb.append(casillas[i][j].getFicha().toString());
                    } else {
                        sb.append(" ");
                    }
                }
                sb.append("|\n");
            }

            sb.append(" ");
            for (int j = 0; j < FILAS; j++) {
                sb.append("-");
            }
            sb.append("-\n");

            return sb.toString();
        }
    }

