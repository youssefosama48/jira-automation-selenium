package automation.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginWithEmptyCredentials {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        ChromeOptions opts = new ChromeOptions();
        if (headless) {
            opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    void testLoginWithEmptyCredentials() {
        driver.get(APP_URL);
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        
        assertTrue(usernameField.getAttribute("value").isEmpty());
        assertTrue(passwordField.getAttribute("value").isEmpty());
        
        loginButton.click();
        
        String alertText = wait.until(ExpectedConditions.alertIsPresent()).getText();
        assertEquals("User or Password is not valid", alertText);
        driver.switchTo().alert().accept();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}