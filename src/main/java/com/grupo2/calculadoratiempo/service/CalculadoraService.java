package com.grupo2.calculadoratiempo.service;

import com.grupo2.calculadoratiempo.errors.CustomException;
import com.grupo2.calculadoratiempo.model.Tiempo;

import java.util.List;

public interface CalculadoraService {

    String calcular(List<Tiempo> tiempos) throws CustomException;
}
