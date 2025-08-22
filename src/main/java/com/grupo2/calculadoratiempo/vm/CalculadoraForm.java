package com.grupo2.calculadoratiempo.vm;

import com.grupo2.calculadoratiempo.model.Tiempo;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CalculadoraForm {

    private List<Tiempo> tiempos = new ArrayList<>();

    public CalculadoraForm() {
        this.tiempos.add(new Tiempo(0, 0, 0));
    }

}
