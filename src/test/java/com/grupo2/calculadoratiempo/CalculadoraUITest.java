package com.grupo2.calculadoratiempo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculadoraUITest {

    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @BeforeAll
    void setupClass() {
        // URL base: usar variable de entorno si está definida (ideal en CI)
        String envUrl = System.getenv("BASE_URL");
        baseUrl = (envUrl != null && !envUrl.isBlank())
                ? envUrl
                : "http://localhost:8080/horas";

        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1366,768");
        // Opcional: desactivar GPU/infobars para entornos CI
        options.addArguments("--disable-gpu", "--disable-infobars");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @Test
    void testCargaPaginaPrincipal() {
        driver.get("http://localhost:8080/horas"); // Cambia la URL si es necesario
        System.out.println(driver.getTitle());
        Assertions.assertEquals("Calculadora de Horas", driver.getTitle());
    }

    @Test
    void testCampoEntradaPresente() {
        driver.get("http://localhost:8080/horas");
        WebElement input = driver.findElement(By.id("tiempos0.horas"));
        Assertions.assertNotNull(input);
        WebElement input2 = driver.findElement(By.id("tiempos0.minutos"));
        Assertions.assertNotNull(input2);
        WebElement input3 = driver.findElement(By.id("tiempos0.segundos"));
        Assertions.assertNotNull(input3);
    }

    @Test
    void testOperacionSumaHoras() {
        driver.get("http://localhost:8080/horas");
        //Primer field de horas
        WebElement inputHours1 = driver.findElement(By.id("tiempos0.horas"));
        inputHours1.clear();
        inputHours1.sendKeys("2");
        //Primer field de minutos
        WebElement inputMinute1 = driver.findElement(By.id("tiempos0.minutos"));
        inputMinute1.clear();
        inputMinute1.sendKeys("50");
        //Primer field de segundos
        WebElement inputSecond1 = driver.findElement(By.id("tiempos0.segundos"));
        inputSecond1.clear();
        inputSecond1.sendKeys("23");
        //Boton para agregar elemento
        WebElement addButton = driver.findElement(By.id("inputAdd"));
        addButton.click();
        //Segundo field de horas
        WebElement inputHours2 = driver.findElement(By.id("tiempos1.horas"));
        inputHours2.clear();
        inputHours2.sendKeys("6");
        //Segundo field de minutos
        WebElement inputMinute2 = driver.findElement(By.id("tiempos1.minutos"));
        inputMinute2.clear();
        inputMinute2.sendKeys("42");
        //Segundo field de segundos
        WebElement inputSecond2 = driver.findElement(By.id("tiempos1.segundos"));
        inputSecond2.clear();
        inputSecond2.sendKeys("10");
        //Boton para calcular
        WebElement calculateButton = driver.findElement(By.id("inputCalculate"));
        calculateButton.click();
        //Espera a que el resultado aparesca
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));

        //Obtiene resultado
        WebElement result = driver.findElement(By.id("result"));
        String resultText = result.getText();
        Assertions.assertEquals("09:32:33", resultText);

        //Quita el segundo elemento
        WebElement removeButton = driver.findElement(By.id("0.remove"));
        removeButton.click();
        //Espera a que el elemento desaparesca
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.invisibilityOf(inputHours2));
        //Agrega otro elemento
        addButton = driver.findElement(By.id("inputAdd"));
        addButton.click();
        //Espera a que el nuevo elemento aparezca
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.id("tiempos1.horas")));
        //Segundo field de horas
        inputHours2 = driver.findElement(By.id("tiempos1.horas"));
        inputHours2.clear();
        inputHours2.sendKeys("6");
        //Segundo field de minutos
        inputMinute2 = driver.findElement(By.id("tiempos1.minutos"));
        inputMinute2.clear();
        inputMinute2.sendKeys("42");
        //Segundo field de segundos
        inputSecond2 = driver.findElement(By.id("tiempos1.segundos"));
        inputSecond2.clear();
        inputSecond2.sendKeys("10");
        //Boton para calcular
        calculateButton = driver.findElement(By.id("inputCalculate"));
        calculateButton.click();
        //Espera a que el resultado aparesca
        new WebDriverWait(driver, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        //Obtiene resultado
        result = driver.findElement(By.id("result"));
        resultText = result.getText();
        Assertions.assertEquals("13:24:20", resultText);
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
