package com.grupo2.calculadoratiempo.service;

import com.grupo2.calculadoratiempo.model.Tiempo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculadoraServiceImpl implements CalculadoraService {

    @Override
    public String calcular(List<Tiempo> tiempos) throws Exception {
        int totalSegundos = 0;
        if (tiempos == null || tiempos.isEmpty()) {
            throw new Exception("La lista de tiempos no puede estar vacía");
        }
        for (int i = 0; i < tiempos.size(); i++) {
            Tiempo tiempo = tiempos.get(i);
            if (tiempo.getHoras() < 0 || tiempo.getHoras() > 9999)
                throw new Exception("Las horas de la fila " + (i + 1) + " deben estar entre 0 y 9999");
            if (tiempo.getMinutos() < 0 || tiempo.getMinutos() > 59)
                throw new Exception("Los minutos de la fila " + (i + 1) + " deben estar entre 0 y 59");
            if (tiempo.getSegundos() < 0 || tiempo.getSegundos() > 59)
                throw new Exception("Los segundos de la fila " + (i + 1) + " deben estar entre 0 y 59");
            totalSegundos = totalSegundos + tiempo.getTotal();
        }
        int horas = totalSegundos / 3600;
        int minutos = (totalSegundos % 3600) / 60;
        int segundos = totalSegundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}
