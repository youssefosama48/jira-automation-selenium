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

public class LoginFormUIValidation {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        ChromeOptions opts = new ChromeOptions();
        if (headless) opts.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @Test
    public void testNavigateToLoginPage() {
        driver.get(APP_URL);
        wait.until(ExpectedConditions.titleContains("Guru99 Bank"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("demo.guru99.com"), "Login page loaded successfully");
    }
    
    @Test
    public void testFormElementsPresent() {
        driver.get(APP_URL);
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uid")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.name("btnLogin"));
        
        assertTrue(username.isDisplayed(), "Username field visible");
        assertTrue(password.isDisplayed(), "Password field visible");
        assertTrue(loginBtn.isDisplayed(), "Login button visible");
    }
    
    @Test
    public void testFormStylingAndLayout() {
        driver.get(APP_URL);
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uid")));
        WebElement password = driver.findElement(By.id("password"));
        WebElement loginBtn = driver.findElement(By.name("btnLogin"));
        
        assertTrue(username.getSize().getWidth() > 0, "Username field has width");
        assertTrue(password.getSize().getHeight() > 0, "Password field has height");
        assertTrue(loginBtn.isEnabled(), "Login button is enabled");
        assertEquals("text", username.getAttribute("type"), "Username field type is text");
        assertEquals("password", password.getAttribute("type"), "Password field type is password");
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}