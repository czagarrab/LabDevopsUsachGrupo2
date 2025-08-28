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
    private static final String formView = "calculadoraForm";
    private static final String totalAttribute = "total";
    private static final String errorAttribute = "error";
    private static final String zeroTime = "00:00:00";
    private static final String templateHoras = "horas";

    @GetMapping
    public String form(Model model) {
        model.addAttribute(formView, new CalculadoraForm());
        model.addAttribute(totalAttribute, zeroTime);
        model.addAttribute(errorAttribute, null);
        return templateHoras;
    }

    // 2) Agregar fila en servidor
    @PostMapping(params = "add")
    public String agregarFila(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        calculadoraForm.getTiempos().add(new Tiempo(0, 0, 0));
        // No calculamos nada aquí; solo render con los datos actuales
        model.addAttribute(formView, calculadoraForm);
        model.addAttribute(totalAttribute, zeroTime);
        model.addAttribute(errorAttribute, null);
        return templateHoras;
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
        model.addAttribute(formView, calculadoraForm);
        model.addAttribute(totalAttribute, zeroTime);
        model.addAttribute(errorAttribute, null);
        return templateHoras;
    }

    @PostMapping
    public String calcular(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        String total = zeroTime;
        try {
            if (calculadoraForm.getTiempos() == null) {
                total = zeroTime;
            } else {
                total = calculadoraService.calcular(calculadoraForm.getTiempos());
            }
            model.addAttribute(formView, calculadoraForm);
            model.addAttribute(totalAttribute, total);
            model.addAttribute(errorAttribute, null);
            return templateHoras;
        } catch (Exception e) {
            model.addAttribute(formView, calculadoraForm);
            model.addAttribute(totalAttribute, total);
            model.addAttribute(errorAttribute, e.getMessage());
            return templateHoras;
        }
    }

}
