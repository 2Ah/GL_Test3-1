package ru.yandex.rasp;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Calendar;
import java.util.List;

public class FindTransport extends WebDriverSettings {
  @Test
  public void findTransportGood() throws InterruptedException {
    List<WebElement> webElements;
    driver.get("https://rasp.yandex.ru"); // 1. Пользователь открывает сайт https://rasp.yandex.ru
    driver.findElement(By.id("from")).clear();
    driver.findElement(By.id("from")).sendKeys("Кемерово"); // 2. Вводит пункт отправления “Кемерово”
    driver.findElement(By.id("to")).sendKeys("Москва");; // 3. Вводит пункт назначения “Москва”
    driver.findElement(By.id("when")).sendKeys("12.08.2019"); // 4. Вводит дату
    driver.findElement(By.className("SearchForm__submit")).click(); // 5. Нажимает Найти
    do { // Цикл ожидания полной загрузки списка элементов расписания.
      webElements = wait.until( ExpectedConditions.visibilityOfAllElementsLocatedBy( By.cssSelector("article") ) );
      Thread.sleep(666);
    }
    while ( webElements.size() < driver.findElements(By.cssSelector("article") ).size() );

    for (WebElement webElement : webElements ) {
      // 6. Проверяет, что отображается название рейса.
      System.out.print( webElement.findElement(By.className("SegmentTitle__number") ).getText() + "\t\t" );  // В случае ненахождения класса, тест упадет с результатом "failed".
      // 7. Проверяет, что у направления есть время в пути.
      System.out.print( webElement.findElement(By.className("SearchSegment__duration") ).getText() + "\t\t" );  // В случае ненахождения класса, тест упадет с результатом "failed".
      // 8. Проверяет, что у всех рейсов есть иконка транспорта.
      System.out.print( (webElement.findElement(By.cssSelector("svg.TransportIcon__icon") ).isEnabled()==true) ? "Тэг иконки обнаружен" : "Тэг иконки отсутсвует" );  // В случае ненахождения элемента и его атрибута класса, тест упадет с результатом "failed".
      System.out.println();
    }
    System.out.println("Количество найденных рейсов: " + webElements.size() );
    Assert.assertTrue( webElements.size() == 5); // 9. Проверяет, что рейсов отобразилось 5
  }

  @Test
  public void findTransportBad()   {
    WebElement webElement;
    Calendar cal = Calendar.getInstance();
    Integer daysToWednesday = (4 + 7 - cal.get(Calendar.DAY_OF_WEEK)) % 7; // Кол-во дней до ближайшей среды.
    cal.add(Calendar.DAY_OF_MONTH, daysToWednesday); // Получение даты ближайшей среды.

    driver.get("https://rasp.yandex.ru"); // 1. Пользователь открывает сайт https://rasp.yandex.ru
    driver.findElement(By.id("from")).clear();
    driver.findElement(By.id("from")).sendKeys("Кемерово проспект Ленина"); // 2. Вводит пункт отправления “Кемерово проспект Ленина”
    driver.findElement(By.id("to")).sendKeys("Кемерово Бакинский переулок"); // 3. Вводит пункт назначения “Кемерово Бакинский переулок”
    driver.findElement(By.id("when")).sendKeys(cal.get(Calendar.DAY_OF_MONTH) +"."+ Integer.toString( cal.get(Calendar.MONTH) + 1 )  +"."+ cal.get(Calendar.YEAR)); // 4. Вводит дату на ближайшую среду
    driver.findElement(By.cssSelector("[value=\"bus\"]")).sendKeys(" "); // 5. Нажимает на «Автобус»
    driver.findElement(By.className("SearchForm__submit")).click(); // 6. Нажимает Найти
    webElement = wait.until( ExpectedConditions.visibilityOfElementLocated( By.cssSelector(".ErrorPageSearchForm__title + .ErrorPageSearchForm__title") ) );
    System.out.println( webElement.getText() );
    Assert.assertEquals(webElement.getText(), "Пункт прибытия не найден. Проверьте правильность написания или выберите другой город."); // 7. Проверяет, что отображается ошибка с текстом «Пункт прибытия не найден. Проверьте правильность написания или выберите другой город.»
  }
}
