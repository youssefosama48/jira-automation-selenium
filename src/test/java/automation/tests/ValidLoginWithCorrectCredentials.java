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

public class ValidLoginWithCorrectCredentials {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "Test123!";

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
    public void testValidLoginWithCorrectCredentials() {
        driver.get(APP_URL);
        
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("uid")));
        assertTrue(usernameField.isDisplayed(), "Login page should load successfully");
        
        usernameField.sendKeys(USERNAME);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(PASSWORD);
        assertEquals(USERNAME, usernameField.getAttribute("value"), "Username should be entered correctly");
        
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        loginButton.click();
        
        wait.until(ExpectedConditions.or(
            ExpectedConditions.urlContains("Managerhomepage"),
            ExpectedConditions.titleContains("Guru99 Bank Manager HomePage")
        ));
        
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("Managerhomepage") || 
                   driver.getTitle().contains("Manager"), 
                   "User should be redirected to dashboard");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}