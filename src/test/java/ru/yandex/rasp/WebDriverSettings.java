package ru.yandex.rasp;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriverSettings {
  protected WebDriver driver;
  protected WebDriverWait wait;

  @Before
  public void testEnd() {
    System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver_76.exe");
    driver = new ChromeDriver();
/*    System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver_0.24x32.exe");
    driver = new FirefoxDriver();*/

    wait = new WebDriverWait(driver, 9, 500);
  }

  @After
  public void testStart() {  if
    (driver!=null) driver.quit();
  }
}
