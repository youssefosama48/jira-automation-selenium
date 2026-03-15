package automation.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class LoginWithInvalidPassword {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    private static final String USERNAME = "testuser";
    private static final String INVALID_PASSWORD = "wrongpass";
    
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
        driver.navigate().to(APP_URL);
    }
    
    @Test
    @DisplayName("TE-T195 - Login with invalid password")
    public void testLoginWithInvalidPassword() {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        
        usernameField.sendKeys(USERNAME);
        passwordField.sendKeys(INVALID_PASSWORD);
        loginButton.click();
        
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("V4"), "User should remain on login page");
        Assertions.assertFalse(currentUrl.contains("manager"), "User should not reach manager page");
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}