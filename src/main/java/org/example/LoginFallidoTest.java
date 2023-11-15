package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.openqa.selenium.By;
import static org.testng.Assert.*;
/*
* Caso de Prueba 2: Inicio de Sesión Fallido en DZone
Título: Intento de inicio de sesión sin proporcionar credenciales

Precondiciones:

El usuario debe estar en la página de inicio de sesión de DZone.
Pasos:

Navegar a "https://dzone.com/users/login.html".
Sin introducir ningún dato, hacer clic directamente en el botón "Login".
Esperar a que el sistema responda al intento de inicio de sesión.
Resultado Esperado:

El mensaje "Incorrect Username or Password" aparece, indicando que no se proporcionaron credenciales válidas.
* */

public class LoginFallidoTest {
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
    public void unsuccessfulLoginTest() {

        driver.get("https://dzone.com/users/login.html");

        // Localizar el botón de inicio de sesión y hacer clic en él
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();

        // Esperar hasta que el elemento span este visible
        WebDriverWait wait = new WebDriverWait(driver, 10);
        By errorSpanLocator = By.cssSelector("span[ng-if='loginFailed']");
        wait.until(ExpectedConditions.presenceOfElementLocated(errorSpanLocator));

        // Obtener el mensaje de error
        WebElement errorDiv = driver.findElement(By.cssSelector("div.alert-validate"));
        String actualErrorMessage = errorDiv.getAttribute("data-validate").trim();

        // Verificar que el mensaje de error es el esperado...
        String expectedErrorMessage = "Incorrect Username or Password";
        assertTrue(actualErrorMessage.contains(expectedErrorMessage), "El mensaje de error no es el esperado: " + actualErrorMessage);
    }
}
