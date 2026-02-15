package org.iesalandalus.programacion.cuatroenraya.modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class JugadorTest {

	@ParameterizedTest(name = "Cuando se llama al constructor con nombre {0} y color de fichas {1} se crea el jugador correctamente.")
	@CsvSource({"José Ramón, AZUL", "Inés, VERDE"})
	void constructorNombreValidoFichaValidaCreaJugadorCorrectamente(String nombre, Ficha colorFichas) {
		Jugador jugador = new Jugador(nombre, colorFichas);
		assertEquals(nombre, jugador.nombre());
		assertEquals(colorFichas, jugador.colorFichas());
	}

	@Test
	void constructorNombreNuloLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Jugador(null, Ficha.AZUL));
		assertEquals("El nombre no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando se llama al constructor con nombre `{0}` se lanza la excepción correspondiente")
	@CsvSource({"''", "' '", "'   '"})
	void constructorNombreNoValidoFichaValidaLanzaExcepcion(String nombre) {
		IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> new Jugador(nombre, Ficha.AZUL));
		assertEquals("El nombre no puede estar en blanco.",  iae.getMessage());
	}

	@Test
	void constructorNombreValidoFichaNulaLanzaExcepcion() {
		NullPointerException npe = assertThrows(NullPointerException.class, () -> new Jugador("José Ramón", null));
		assertEquals("El color de las fichas no puede ser nulo.", npe.getMessage());
	}

	@ParameterizedTest(name = "Cuando se llama a toString para un jugador con nombre: {0} y color de fichas: {1}, la salida es: {2}")
	@CsvSource({"José Ramón, AZUL, José Ramón (A)", "Inés, VERDE, Inés (V)"})
	void toStringDevuelveCadenaCorrectamente(String nombre, Ficha colorFichas, String salida) {
		Jugador jugador = new Jugador(nombre, colorFichas);
		assertEquals(salida, jugador.toString());
	}

}