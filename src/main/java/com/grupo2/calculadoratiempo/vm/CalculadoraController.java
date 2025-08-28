package com.grupo2.calculadoratiempo.vm;

import com.grupo2.calculadoratiempo.service.CalculadoraService;
import com.grupo2.calculadoratiempo.model.Tiempo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/horas")
@AllArgsConstructor
public class CalculadoraController {

    private final CalculadoraService calculadoraService;
    private final String FORM_VIEW = "calculadoraForm";
    private final String TOTAL_ATTRIBUTE = "total";
    private final String ERROR_ATTRIBUTE = "error";
    private final String ZERO_TIME = "00:00:00";
    private final String TEMPLATE_HORAS = "horas";

    @GetMapping
    public String form(Model model) {
        model.addAttribute(FORM_VIEW, new CalculadoraForm());
        model.addAttribute(TOTAL_ATTRIBUTE, ZERO_TIME);
        model.addAttribute(ERROR_ATTRIBUTE, null);
        return TEMPLATE_HORAS;
    }

    // 2) Agregar fila en servidor
    @PostMapping(params = "add")
    public String agregarFila(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        calculadoraForm.getTiempos().add(new Tiempo(0, 0, 0));
        // No calculamos nada aquí; solo render con los datos actuales
        model.addAttribute(FORM_VIEW, calculadoraForm);
        model.addAttribute(TOTAL_ATTRIBUTE, ZERO_TIME);
        model.addAttribute(ERROR_ATTRIBUTE, null);
        return TEMPLATE_HORAS;
    }

    @PostMapping(params = "remove")
    public String eliminarFila(@ModelAttribute CalculadoraForm calculadoraForm,
                               @RequestParam("remove") int index,
                               Model model) {
        if (index >= 0 && index < calculadoraForm.getTiempos().size()) {
            calculadoraForm.getTiempos().remove(index);
        }
        if (calculadoraForm.getTiempos().isEmpty()) {
            calculadoraForm.getTiempos().add(new Tiempo(0, 0, 0));
        }
        model.addAttribute(FORM_VIEW, calculadoraForm);
        model.addAttribute(TOTAL_ATTRIBUTE, ZERO_TIME);
        model.addAttribute(ERROR_ATTRIBUTE, null);
        return TEMPLATE_HORAS;
    }

    @PostMapping
    public String calcular(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        String total = ZERO_TIME;
        try {
            if (calculadoraForm.getTiempos() == null) {
                total = ZERO_TIME;
            } else {
                total = calculadoraService.calcular(calculadoraForm.getTiempos());
            }
            model.addAttribute(FORM_VIEW, calculadoraForm);
            model.addAttribute(TOTAL_ATTRIBUTE, total);
            model.addAttribute(ERROR_ATTRIBUTE, null);
            return TEMPLATE_HORAS;
        } catch (Exception e) {
            model.addAttribute(FORM_VIEW, calculadoraForm);
            model.addAttribute(TOTAL_ATTRIBUTE, total);
            model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
            return TEMPLATE_HORAS;
        }
    }

}
