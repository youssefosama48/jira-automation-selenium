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
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class LoginWithSQLInjectionAttempt {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private final String APP_URL = "https://demo.guru99.com/V4/";
    
    @BeforeEach
    public void setUp() {
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
    public void testLoginWithSQLInjectionAttempt() {
        driver.get(APP_URL);
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        assertTrue(usernameField.isDisplayed(), "Login page should load successfully");
        
        usernameField.sendKeys("admin' OR '1'='1");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("anything");
        
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        loginButton.click();
        
        wait.until(ExpectedConditions.or(
            ExpectedConditions.presenceOfElementLocated(By.id("uid")),
            ExpectedConditions.urlContains("Managerhomepage")
        ));
        
        String currentUrl = driver.getCurrentUrl();
        assertFalse(currentUrl.contains("Managerhomepage"), "SQL injection should not bypass authentication");
        assertTrue(driver.findElements(By.id("uid")).size() > 0, "Should remain on login page after failed attempt");
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}