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

public class LoginFormFieldValidation {
    
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
        driver.get(APP_URL);
    }
    
    @Test
    public void testNavigateToLoginPage() {
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        WebElement passwordField = driver.findElement(By.id("password"));
        
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
    }
    
    @Test
    public void testUsernameFieldInput() {
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("uid")));
        usernameField.clear();
        usernameField.sendKeys("testinput");
        
        String enteredValue = usernameField.getAttribute("value");
        assertEquals("testinput", enteredValue, "Username field should contain entered text");
    }
    
    @Test
    public void testPasswordFieldMasking() {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.id("password")));
        passwordField.clear();
        passwordField.sendKeys("testpass");
        
        String fieldType = passwordField.getAttribute("type");
        String enteredValue = passwordField.getAttribute("value");
        assertEquals("password", fieldType, "Password field should be of type 'password'");
        assertEquals("testpass", enteredValue, "Password field should contain entered value");
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}