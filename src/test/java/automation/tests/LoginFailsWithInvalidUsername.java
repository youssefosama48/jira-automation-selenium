package automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFailsWithInvalidUsername {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    
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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
    
    @Test
    public void testLoginFailsWithInvalidUsername() {
        driver.get(APP_URL);
        
        WebElement loginForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        assertTrue(loginForm.isDisplayed(), "Login form should be displayed");
        
        WebElement usernameField = driver.findElement(By.id("uid"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        
        usernameField.sendKeys("wronguser");
        passwordField.sendKeys("Test123!");
        loginButton.click();
        
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("message23")));
        assertTrue(errorMessage.isDisplayed(), "Error message should be displayed");
        assertEquals(APP_URL, driver.getCurrentUrl(), "Should remain on login page");
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}