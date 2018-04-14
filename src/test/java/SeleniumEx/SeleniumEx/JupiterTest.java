package SeleniumEx.SeleniumEx;

import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JupiterTest {

  private static final int WAIT_MAX = 4;
  static WebDriver driver;


  @BeforeClass
  public static void setup() {
    /*########################### IMPORTANT ######################*/
    /*## Change this, according to your own OS and location of driver(s) ##*/    
    /*############################################################*/    
    System.setProperty("webdriver.gecko.driver", "C:\\Users\\przyg\\Desktop\\drivers\\geckodriver.exe");
    System.setProperty("webdriver.chrome.driver","C:\\Users\\przyg\\Desktop\\drivers\\chromedriver.exe");
    
    //Reset Database
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
    driver = new ChromeDriver();
    driver.get("http://localhost:3000");
  }

  @AfterClass
  public static void tearDown() {
    driver.quit();
    //Reset Database 
    com.jayway.restassured.RestAssured.given().get("http://localhost:3000/reset");
  }

  @Test
  //Verify that page is loaded and all expected data are visible
  public void test1() throws Exception {
    (new WebDriverWait(driver, WAIT_MAX)).until((ExpectedCondition<Boolean>) (WebDriver d) -> {
      WebElement e = d.findElement(By.tagName("tbody"));
      List<WebElement> rows = e.findElements(By.tagName("tr"));
      Assert.assertThat(rows.size(), is(5));
      return true;
    });
  }

  @Test
  //Verify the filter functionality 
  public void test2() throws Exception {
    //No need to WAIT, since we are running test in a fixed order, we know the DOM is ready (because of the wait in test1)
    WebElement body = driver.findElement(By.id("tbodycars"));
    //Complete this
    List<WebElement> cars = body.findElements(By.tagName("tr"));
    Assertions.assertTrue(cars.size() == 5);
  }
  
  @Test
  public void test3(){
      WebElement filter = driver.findElement(By.id("filter"));
      filter.sendKeys("2002");
      WebElement body = driver.findElement(By.id("tbodycars"));
      List<WebElement> cars = body.findElements(By.tagName("tr"));
      Assertions.assertTrue(cars.size() == 2);
  }

  @Test
  public void test4(){
      WebElement filter = driver.findElement(By.id("filter"));
      filter.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
      WebElement body = driver.findElement(By.id("tbodycars"));
      List<WebElement> cars = body.findElements(By.tagName("tr"));
      Assertions.assertTrue(cars.size() == 5);
  }

  @Test
  public void test5(){
      WebElement years = driver.findElement(By.id("h_year"));
      years.click();
      WebElement body = driver.findElement(By.id("tbodycars"));
      List<WebElement> cars = body.findElements(By.tagName("tr"));

      WebElement car1 = cars.get(0);
      WebElement car2 = cars.get(cars.size()-1);
      List<WebElement> row = car1.findElements(By.tagName("td"));
      assertThat(row.get(0).getText(), is("938"));
      row = car2.findElements(By.tagName("td"));
      assertThat(row.get(0).getText(), is("940"));
  }
  @Test
  public void test6(){
	  WebElement filter = driver.findElement(By.id("filter"));
      filter.sendKeys("938");
      WebElement body = driver.findElement(By.id("tbodycars"));
      List<WebElement> cars = body.findElements(By.tagName("tr"));
      WebElement car = cars.get(0);
      car.findElements(By.tagName("a")).get(0).click();
      driver.findElement(By.id("description")).clear();
      driver.findElement(By.id("description")).sendKeys("Cool car");
      driver.findElement(By.id("save")).click();

      filter.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
      filter.sendKeys("938");
      body = driver.findElement(By.id("tbodycars"));
      cars = body.findElements(By.tagName("tr"));
      car = cars.get(0);
      List<WebElement> collum = car.findElements(By.tagName("td"));
      filter.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
      assertThat(collum.get(5).getText(), is("Cool car"));
  }
  
  @Test
  public void test7(){
      driver.findElement(By.id("new")).click();
      driver.findElement(By.id("save")).click();
      WebElement text = driver.findElement(By.id("submiterr"));
      assertThat(text.getText(), is("All fields are required"));
  }
  
  @Test
  public void test8(){

      driver.findElement(By.id("year")).sendKeys("2008");
      driver.findElement(By.id("registered")).sendKeys("2002-5-5");
      driver.findElement(By.id("make")).sendKeys("Kia");
      driver.findElement(By.id("model")).sendKeys("Rio");
      driver.findElement(By.id("description")).sendKeys("As new");
      driver.findElement(By.id("price")).sendKeys("31000");
      driver.findElement(By.id("save")).click();

      WebElement body = driver.findElement(By.id("tbodycars"));
      List<WebElement> cars = body.findElements(By.tagName("tr"));
      Assertions.assertTrue(cars.size() == 6);

  }
}
