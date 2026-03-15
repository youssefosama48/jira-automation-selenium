package automation.tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFormFieldValidation {
    
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless","true"));
        ChromeOptions opts = new ChromeOptions();
        if (headless) opts.addArguments("--headless=new","--no-sandbox","--disable-dev-shm-usage");
        driver = new ChromeDriver(opts);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    
    @Test
    public void testNavigateToLoginPage() {
        driver.get(APP_URL);
        wait.until(ExpectedConditions.titleContains("Guru99 Bank"));
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("guru99.com"), "Login page should load successfully");
    }
    
    @Test
    public void testUsernameFieldPresent() {
        driver.get(APP_URL);
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uid")));
        assertTrue(usernameField.isDisplayed(), "Username field should be visible");
        assertTrue(usernameField.isEnabled(), "Username field should be editable");
        assertEquals("input", usernameField.getTagName().toLowerCase(), "Username should be input field");
    }
    
    @Test
    public void testPasswordFieldPresent() {
        driver.get(APP_URL);
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        assertTrue(passwordField.isDisplayed(), "Password field should be visible");
        assertTrue(passwordField.isEnabled(), "Password field should be editable");
        assertEquals("input", passwordField.getTagName().toLowerCase(), "Password should be input field");
        assertEquals("password", passwordField.getAttribute("type"), "Password field should have type password");
    }
    
    @Test
    public void testAllLoginFormFieldsPresent() {
        driver.get(APP_URL);
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uid")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.name("btnLogin")));
        
        assertAll("All login form fields should be present",
            () -> assertTrue(usernameField.isDisplayed() && usernameField.isEnabled()),
            () -> assertTrue(passwordField.isDisplayed() && passwordField.isEnabled()),
            () -> assertTrue(loginButton.isDisplayed() && loginButton.isEnabled())
        );
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}