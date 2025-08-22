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

    @GetMapping
    public String form(Model model) {
        model.addAttribute("calculadoraForm", new CalculadoraForm());
        model.addAttribute("total", "00:00:00");
        model.addAttribute("error", null);
        return "horas";
    }

    // 2) Agregar fila en servidor
    @PostMapping(params = "add")
    public String agregarFila(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        calculadoraForm.getTiempos().add(new Tiempo(0, 0, 0));
        // No calculamos nada aquí; solo render con los datos actuales
        model.addAttribute("calculadoraForm", calculadoraForm);
        model.addAttribute("total", "00:00:00");
        model.addAttribute("error", null);
        return "horas";
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
        model.addAttribute("calculadoraForm", calculadoraForm);
        model.addAttribute("total", "00:00:00");
        model.addAttribute("error", null);
        return "horas";
    }

    @PostMapping
    public String calcular(@ModelAttribute CalculadoraForm calculadoraForm, Model model) {
        String total = "00:00:00";
        try {
            if (calculadoraForm.getTiempos() == null) {
                total = "00:00:00";
            } else {
                total = calculadoraService.calcular(calculadoraForm.getTiempos());
            }
            model.addAttribute("calculadoraForm", calculadoraForm);
            model.addAttribute("total", total);
            model.addAttribute("error", null);
            return "horas";
        } catch (Exception e) {
            model.addAttribute("calculadoraForm", calculadoraForm);
            model.addAttribute("total", total);
            model.addAttribute("error", e.getMessage());
            return "horas";
        }
    }

}
