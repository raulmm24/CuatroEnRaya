package org.iesalandalus.programacion.cuatroenraya.modelo;

import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class CuatroEnRaya {
    private static final int NUMERO_JUGADORES = 2;

    private final Jugador[] jugadores;
    private final Tablero tablero;

    public CuatroEnRaya(Jugador jugador1, Jugador jugador2) {
        if (jugador1 == null || jugador2 == null) {
            throw new NullPointerException("Los jugadores no pueden ser nulos.");
        }
        this.jugadores = new Jugador[]{jugador1,jugador2};
        this.tablero = new Tablero();
    }

    public void jugar() {
        int turno = 0;
        boolean victoria = false;
        Jugador jugadorActual = null;

        while (!tablero.estaLleno() && !victoria) {
            jugadorActual = jugadores[turno % NUMERO_JUGADORES];
            victoria = tirar(jugadorActual);

            System.out.println(tablero);

            if (!victoria) {
                turno++;
            }
        }

        if (victoria) {
            System.out.printf("ENHORABUENA, %s has ganado", jugadorActual.nombre());
        } else {
            System.out.println("¡Habéis empatado! El tablero está lleno.");
        }
    }

    private boolean tirar(Jugador jugador) {
        boolean tiradaValida = false;
        boolean objetivoAlcanzado = false;

        do {
            try {
                int columna = Consola.leerColumna(jugador);
                objetivoAlcanzado = tablero.introducirFicha(columna, jugador.colorFichas());
                tiradaValida = true;
            } catch (IllegalArgumentException | CuatroEnRayaExcepcion e) {
                System.out.println(e.getMessage());
            }
        } while (!tiradaValida);

        return objetivoAlcanzado;
    }

}
