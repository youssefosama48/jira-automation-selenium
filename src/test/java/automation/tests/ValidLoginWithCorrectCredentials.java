package automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidLoginWithCorrectCredentials {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String APP_URL = "https://demo.guru99.com/V4/";
    private static final String USERNAME = "testuser";
    private static final String PASSWORD = "Test123";

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
    void testValidLoginWithCorrectCredentials() {
        driver.get(APP_URL);
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("uid")));
        assertTrue(driver.findElement(By.id("uid")).isDisplayed());
        
        driver.findElement(By.id("uid")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        
        driver.findElement(By.name("btnLogin")).click();
        
        wait.until(ExpectedConditions.urlContains("Managerhomepage"));
        assertTrue(driver.getCurrentUrl().contains("Managerhomepage"));
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}