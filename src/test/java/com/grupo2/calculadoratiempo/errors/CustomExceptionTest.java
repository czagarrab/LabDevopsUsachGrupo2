package com.grupo2.calculadoratiempo.errors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomExceptionTest {

    @Test
    void testCustomExceptionMessage() {
        String mensaje = "Mensaje de error";
        CustomException ex = new CustomException(mensaje);
        assertEquals(mensaje, ex.getMessage());
    }
}
