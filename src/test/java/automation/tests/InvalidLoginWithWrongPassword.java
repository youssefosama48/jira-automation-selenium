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
import org.junit.jupiter.api.Assertions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;

public class InvalidLoginWithWrongPassword {

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
        driver.manage().window().maximize();
    }

    @Test
    public void testInvalidLoginWithWrongPassword() {
        driver.get(APP_URL);
        
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("uid")));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.name("btnLogin"));
        
        Assertions.assertTrue(usernameField.isDisplayed(), "Login form should be displayed");
        
        usernameField.sendKeys("testuser");
        passwordField.sendKeys("wrongpass");
        loginButton.click();
        
        wait.until(ExpectedConditions.alertIsPresent());
        String alertText = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        
        Assertions.assertTrue(alertText.contains("User or Password is not valid"), "Error message should be displayed");
        Assertions.assertEquals(APP_URL, driver.getCurrentUrl(), "User should remain on login page");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}