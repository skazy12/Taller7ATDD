package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static org.testng.Assert.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.List;
import java.util.concurrent.TimeUnit;
/*
* Historia de Usuario
Como un usuario de DZone,
quiero poder buscar artículos usando la barra de búsqueda,
para que pueda encontrar información relevante sobre temas específicos como "Java".

Caso de Prueba 1: Búsqueda Exitosa en DZone
Título: Búsqueda exitosa de artículos sobre Java

Precondiciones:

El usuario debe tener acceso a la página de inicio de DZone.
Pasos:

Abrir el navegador y navegar a "https://dzone.com".
Localizar y hacer clic en el botón de búsqueda.
Esperar a que la página de búsqueda se cargue completamente.
Introducir "Java" en la barra de búsqueda y enviar la consulta.
Esperar a que aparezcan los resultados de la búsqueda.
*
Resultado Esperado:

Una lista de artículos relacionados con "Java" se muestra en la página de resultados de búsqueda.
Cada artículo en la lista de resultados contiene la palabra "Java".
*
* */
public class BuscarTzoneTest {
    private WebDriver driver;

    private WebDriverWait wait;
    @BeforeTest
    public void setDriver() throws Exception {
        String path = "/F/ALL_STUDIES/SEMESTRE2_2023/CALIDAD/tareas/ATDD/chromeDriver/chromedriver-win64/chromedriver-win64/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void searchDZoneWithResults() {
        // 1. Navegar a DZone
        driver.get("https://dzone.com");

        // 2. Encontrar el boton de busqueda e ir a la pagina de busqueda
        WebElement searchButton = driver.findElement(By.xpath("//*[@id=\"ftl-header\"]/div[1]/div/div[2]/div[3]/a"));
        searchButton.click();

        //Esperamos 3 segundos
        try{
            TimeUnit.SECONDS.sleep(3);
        }
        catch(InterruptedException e){
            e.printStackTrace();

        }
        WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"searchComp\"]"));
        // 5. Enviamos la consulta de búsqueda
        searchBox.sendKeys("Java");
        searchBox.sendKeys(Keys.RETURN);

        // 6. Esperamos a que la pagina de resultados cargue
        wait = new WebDriverWait(driver, 4);
        By resultsListLocator = By.cssSelector("ul.results-list > li.itemsList");
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsListLocator));

        // Obtener la lista de resultados de búsqueda
        List<WebElement> searchResults = driver.findElements(resultsListLocator);

        // Asegurarse de que la lista de resultados de búsqueda no esté vacía
        assertFalse(searchResults.isEmpty(), "La lista de resultados de búsqueda está vacía.");

        // Validar que cada resultado contenga el término de búsqueda
        for (WebElement result : searchResults) {
            String resultText = result.getText();
            assertTrue(resultText.toLowerCase().contains("java"), "Un resultado de búsqueda no contiene la palabra 'Java'.");
        }
    }
}
