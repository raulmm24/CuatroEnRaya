package org.iesalandalus.programacion.cuatroenraya;

import org.iesalandalus.programacion.cuatroenraya.modelo.CuatroEnRaya;
import org.iesalandalus.programacion.cuatroenraya.modelo.Ficha;
import org.iesalandalus.programacion.cuatroenraya.modelo.Jugador;
import org.iesalandalus.programacion.cuatroenraya.vista.Consola;

public class Main {
	
	public static void main(String[] args) {

		System.out.println("Introduce los datos del Primer Jugador: ");
		Jugador jugador1 = Consola.leerJugador();

		Ficha colorJ2 = (jugador1.colorFichas() == Ficha.AZUL) ? Ficha.VERDE : Ficha.AZUL;

		System.out.println("Introduce los datos del Segundo Jugador: ");
		Jugador jugador2 = Consola.leerJugador(colorJ2);

		try {
			CuatroEnRaya partida = new CuatroEnRaya(jugador1, jugador2);
			partida.jugar();
		} catch (Exception e) {
			System.out.println("No se pudo iniciar la partida." + e.getMessage());
		}
	}
}
