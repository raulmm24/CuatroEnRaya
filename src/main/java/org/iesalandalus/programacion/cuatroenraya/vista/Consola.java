package org.iesalandalus.programacion.cuatroenraya.vista;

import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.utilidades.Entrada;

public class Consola {

    private Consola() {}

    public static String leerNombre() {
        System.out.print("Introduce el nombre del Jugador: ");
        return Entrada.cadena();
    }

    public static Ficha elegirColorFichas() {
        int opcion;
        do {
            System.out.print("Elige el color de las fichas (0 - AZUL, 1 - VERDE): ");
            opcion = Entrada.entero();
        } while (opcion < 0 || opcion > 1);

        return (opcion == 0) ? Ficha.AZUL : Ficha.VERDE;
    }

    public static Jugador leerJugador() {
        String nombre = leerNombre();
        Ficha ficha = elegirColorFichas();
        return new Jugador(nombre,ficha);
    }

    public static Jugador leerJugador(Ficha ficha) {
        String nombre = leerNombre();
        return new Jugador(nombre,ficha);
    }

    public static int leerColumna(Jugador jugador) {
        System.out.printf("%s, introduce la columna en la que deseas introducir la ficha: (0 - 6): ", jugador.nombre());
        return Entrada.entero();
    }

    public static void mostrarGanador(Jugador jugador) {
        System.out.printf("ENHORABUENA, %s has ganado!!!%n", jugador.nombre());
    }
}
