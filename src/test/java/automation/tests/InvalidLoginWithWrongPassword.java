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
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InvalidLoginWithWrongPassword {
    
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
        driver.manage().window().maximize();
    }
    
    @Test
    public void testInvalidLoginWithWrongPassword() {
        driver.get(APP_URL);
        
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        assertTrue(usernameField.isDisplayed(), "Login page should load successfully");
        
        usernameField.sendKeys("testuser");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("wrongpass");
        
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        loginButton.click();
        
        WebElement errorAlert = wait.until(ExpectedConditions.alertIsPresent()).switchTo().alert();
        String errorMessage = errorAlert.getText();
        assertTrue(errorMessage.contains("User or Password is not valid"), "Error message should be displayed");
        errorAlert.accept();
    }
    
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}