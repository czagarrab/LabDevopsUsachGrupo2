package com.grupo2.calculadoratiempo;

import com.grupo2.calculadoratiempo.model.Tiempo;
import com.grupo2.calculadoratiempo.service.CalculadoraServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculadoraServiceImplTest {

    private final CalculadoraServiceImpl service = new CalculadoraServiceImpl();

    @Test
    void calcular_retornaTiempoFormateado() throws Exception {
        List<Tiempo> tiempos = Arrays.asList(
                new Tiempo(1, 20, 30),
                new Tiempo(0, 40, 30)
        );
        String resultado = service.calcular(tiempos);
        assertEquals("02:01:00", resultado);
    }

    @Test
    void calcular_lanzaExcepcionSiListaVacia() {
        Exception ex = assertThrows(Exception.class, () -> service.calcular(Collections.emptyList()));
        assertEquals("La lista de tiempos no puede estar vacía", ex.getMessage());
    }

    @Test
    void calcular_lanzaExcepcionSiHorasInvalidas() {
        List<Tiempo> tiempos = List.of(new Tiempo(-1, 10, 10));
        Exception ex = assertThrows(Exception.class, () -> service.calcular(tiempos));
        assertTrue(ex.getMessage().contains("horas"));
    }

    @Test
    void calcular_lanzaExcepcionSiMinutosInvalidos() {
        List<Tiempo> tiempos = List.of(new Tiempo(1, 60, 10));
        Exception ex = assertThrows(Exception.class, () -> service.calcular(tiempos));
        assertTrue(ex.getMessage().contains("minutos"));
    }

    @Test
    void calcular_lanzaExcepcionSiSegundosInvalidos() {
        List<Tiempo> tiempos = List.of(new Tiempo(1, 10, 60));
        Exception ex = assertThrows(Exception.class, () -> service.calcular(tiempos));
        assertTrue(ex.getMessage().contains("segundos"));
    }
}