# Test Assignment (Selenium)

## Test
![Selenium Tests](https://github.com/Ekskursantas/TestEx/blob/master/tests.png?raw=true)


The Source Code for the test can be found [HERE](https://github.com/Ekskursantas/TestEx/blob/master/src/test/java/SeleniumEx/SeleniumEx/JupiterTest.java).
- **Discuss Pros and Cons with manual versus automated tests** 
  - *Time consumption is very different when testing UI manually and with automated test. In most cases if not all the automated testing will beat tesing manually. The more complex the code the more difference we can see between automated and manual testing. Especially, if we want to repeat the same test several times by just manipulating the data a little bit.*
  - *By testing manually you sometimes may find some tiny bugs and issues you could have missed when implementing the automated testing. So if detail is the focus manually going through the UI is better.* 
  - *When working manually you have to put a lot of determination to repeatedly do the same action over and over again. While with automated testing you generally do not need to do anything.*
- **Explain about the Test Pyramid and whether this exercise supports the ideas in the Test Pyramid** 
  - *Three layers of different type of testing. Unit testing, Integration testing and System testing. As the pyramid inclines the higher we reach the less space there is left on the pyramid. The concept is that we want to focus on making as many unit test as possible to cover all the possibilities. Then we want to have few, but no too little Integration test which consume more time and are more pricey. Finally, we have System test which are very costly and consumes a lot of time we don't want many of these because of how costly they are.*
  - *I believe that the exercise supports the idea of the Test Pyramid because we can see it covering all three layers of the pyramid. But it barely manages to fit in because of how scarce amount of test we have.*
- **Discuss some of the problems with automated GUI tests and what makes such tests "vulnerable"** 
  - *Most obvious and common reason would be that the GUI is very often changed and never stays the same. Meaning that some changes might totally destroy the test cases. Just by trying to optimize the UI for different devices can already ruin the test cases. They might still work if we test on PC, but when applying the same test on a mobile device it might be a fatal flaw.*
##Selenium Test


First we need to locate and create an instance of a webdriver. As I am a chrome user and Chrome is installed on a device. I went with ChromeDriver.
```java
System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Eclipse\\Workspace\\SeleniumEx\\drivers\\chromedriver.exe");
driver = new ChromeDriver();
```
The we dirrect the instance to the webpage.

```java
driver.get("http://localhost:3000");
```
Then we locate an element which we will be using.
```java
WebElement e = d.findElement(By.tagName("tbody"));
```
When we find the required elements we assert them to match the values we expect. 
```java
      List<WebElement> rows = e.findElements(By.tagName("tr"));
      Assert.assertThat(rows.size(), is(5));
```
We also can perform actions. Which allows to assert the response afterwards.
```java
filter.sendKeys("2002");
years.click();
```

This line assures us that the webpage is loaded before the test cases start. Because otherwise we might start the test without actually receiving any values. Because as we know, some pages take longer time to actual return a response because of latency. The further the server or the smaller the servers the slower it will load the web page meaning there is a chance that the test will begin before receiving any values. So to avoid this problem we "wait". WebDriverWait is created to wait for 10 seconds unless the page starts/return a title of "document" then we are clear and we continue to the tests.
```java
        (new WebDriverWait(driver, 10)).until((ExpectedCondition<Boolean>) d -> d.getTitle().toLowerCase().startsWith("document"));
```
