package com.grupo2.calculadoratiempo.vm;
import com.grupo2.calculadoratiempo.model.Tiempo;
import com.grupo2.calculadoratiempo.service.CalculadoraService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CalculadoraControllerTest {

    private CalculadoraService calculadoraService;
    private CalculadoraController controller;
    private Model model;

    @BeforeEach
    void setUp() {
        calculadoraService = mock(CalculadoraService.class);
        controller = new CalculadoraController(calculadoraService);
        model = mock(Model.class);
    }

    @Test
    void testFormDevuelveTemplateHoras() {
        String view = controller.form(model);
        assertEquals("horas", view);
        verify(model, times(3)).addAttribute(any(), any());
    }

    @Test
    void testAgregarFilaAgregaTiempo() {
        CalculadoraForm form = new CalculadoraForm();
        form.setTiempos(new ArrayList<>());
        String view = controller.agregarFila(form, model);
        assertEquals("horas", view);
        verify(model, times(3)).addAttribute(any(), any());
        assertEquals(1, form.getTiempos().size());
    }

    @Test
    void testEliminarFilaRemueveTiempo() {
        CalculadoraForm form = new CalculadoraForm();
        ArrayList<Tiempo> tiempos = new ArrayList<>();
        tiempos.add(new Tiempo(1,2,3));
        form.setTiempos(tiempos);
        String view = controller.eliminarFila(form, 0, model);
        assertEquals("horas", view);
        verify(model, times(3)).addAttribute(any(), any());
        assertEquals(1, form.getTiempos().size()); // Se agrega uno nuevo si queda vacío
    }

    @Test
    void testCalcularLlamaService() {
        try {
            CalculadoraForm form = new CalculadoraForm();
            ArrayList<Tiempo> tiempos = new ArrayList<>();
            tiempos.add(new Tiempo(1, 2, 3));
            form.setTiempos(tiempos);
            when(calculadoraService.calcular(any())).thenReturn("01:02:03");
            String view = controller.calcular(form, model);
            assertEquals("horas", view);
            verify(calculadoraService).calcular(any());
            verify(model, times(3)).addAttribute(any(), any());
        } catch (Exception e) {
            Assertions.fail();
        }
    }
}