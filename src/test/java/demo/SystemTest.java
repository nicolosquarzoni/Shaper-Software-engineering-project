package demo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.commons.lang3.SystemUtils;

import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SystemTest {

    private WebDriver driver;
    @Before
    public void setUp() {

        org.openqa.selenium.chrome.ChromeOptions chrome_options = new ChromeOptions();
        chrome_options.addArguments("--headless"); //non apre la finestra del browswer
        if(SystemUtils.IS_OS_WINDOWS){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_win32_98/chromedriver.exe").toString());
        }
        else if (SystemUtils.IS_OS_MAC){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_mac64_96/chromedriver").toString());
        }
        else if (SystemUtils.IS_OS_LINUX){
            System.setProperty("webdriver.chrome.driver",
                    Paths.get("src/test/resources/chromedriver_linux64_96/chromedriver").toString());
        }
        if (driver == null)
            driver = new ChromeDriver(chrome_options);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit(); //chiude il browser dopo il test
        }
    }

    @Test //scenario 1a
    public void test1a(){
        driver.get("http://localhost:8080/");
        WebElement title = driver.findElement(By.tagName("h1"));
        String titleMessage = title.getText();
        assertEquals("WELCOME TO SHAPER", titleMessage);
        WebElement link = driver.findElement(By.linkText("Create a new account"));
        link.click();
        assertEquals("CEATE NEW ACCOUNT 1/2", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.name("firstName")).sendKeys("Paziente");
        driver.findElement(By.name("lastName")).sendKeys("3");
        driver.findElement(By.name("dateofbirth")).sendKeys("10-04-1999");
        WebElement elementRadio = driver.findElement(By.id("patient"));
        elementRadio.click();
        driver.findElement(By.name("userName")).sendKeys("paziente3");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.name("repeatpassword")).sendKeys("12");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[8]/td[2]/input[1]"));
        submit.click();
        assertEquals("Form not correctly filled, try again", driver.findElement(By.xpath("//body/form[1]/div[1]/div[1]")).getText());
        driver.findElement(By.name("firstName")).sendKeys("Paziente");
        driver.findElement(By.name("lastName")).sendKeys("3");
        driver.findElement(By.name("dateofbirth")).sendKeys("10-04-1999");
        WebElement elementRadio1 = driver.findElement(By.id("patient"));
        elementRadio1.click();
        driver.findElement(By.name("userName")).sendKeys("paziente3");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.name("repeatpassword")).sendKeys("123");
        WebElement submit1 = driver.findElement(By.xpath("//tbody/tr[8]/td[2]/input[1]"));
        submit1.click();
        assertEquals("CEATE NEW ACCOUNT 2/2", driver.findElement(By.tagName("h1")).getText());
        assertEquals("Weight", driver.findElement(By.xpath("//tbody/tr[2]/td[1]")).getText());
        driver.findElement(By.name("weight")).sendKeys("80");
        submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        assertEquals("SIGN IN", driver.findElement(By.tagName("h1")).getText());
    }

    @Test //scenario 1b
    public void test1b(){
        driver.get("http://localhost:8080/home");
        WebElement title = driver.findElement(By.tagName("h1"));
        String titleMessage = title.getText();
        assertEquals("WELCOME TO SHAPER", titleMessage);
        WebElement link = driver.findElement(By.linkText("Create a new account"));
        link.click();
        assertEquals("CEATE NEW ACCOUNT 1/2", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.name("firstName")).sendKeys("Professionista");
        driver.findElement(By.name("lastName")).sendKeys("4");
        driver.findElement(By.name("dateofbirth")).sendKeys("10-10-1980");
        WebElement elementRadio = driver.findElement(By.id("professional"));
        elementRadio.click();
        driver.findElement(By.name("userName")).sendKeys("professionista4");
        driver.findElement(By.name("password")).sendKeys("1234");
        driver.findElement(By.name("repeatpassword")).sendKeys("12");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[8]/td[2]/input[1]"));
        submit.click();
        assertEquals("Form not correctly filled, try again", driver.findElement(By.xpath("//body/form[1]/div[1]/div[1]")).getText());
        driver.findElement(By.name("firstName")).sendKeys("Professionista");
        driver.findElement(By.name("lastName")).sendKeys("4");
        driver.findElement(By.name("dateofbirth")).sendKeys("10-10-1980");
        WebElement elementRadio1 = driver.findElement(By.id("professional"));
        elementRadio1.click();
        driver.findElement(By.name("userName")).sendKeys("professionista4");
        driver.findElement(By.name("password")).sendKeys("1234");
        driver.findElement(By.name("repeatpassword")).sendKeys("1234");
        WebElement submit1 = driver.findElement(By.xpath("//tbody/tr[8]/td[2]/input[1]"));
        submit1.click();
        assertEquals("CEATE NEW ACCOUNT 2/2", driver.findElement(By.tagName("h1")).getText());
        assertEquals("Profession", driver.findElement(By.xpath("//td[contains(text(),'Profession')]")).getText());
        driver.findElement(By.name("profession")).sendKeys("Dietologist");
        driver.findElement(By.name("services")).sendKeys("Diets");
        submit = driver.findElement(By.xpath("//tbody/tr[4]/td[2]/input[1]"));
        submit.click();
        assertEquals("SIGN IN", driver.findElement(By.tagName("h1")).getText());
    }

    @Test // test scenario 2a
    public void test2a(){
        driver.get("http://localhost:8080/signin");
        assertEquals("SIGN IN", driver.findElement(By.tagName("h1")).getText());
        driver.findElement(By.name("userName")).sendKeys("paziente2");
        driver.findElement(By.name("password")).sendKeys("paziente");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        assertEquals("Username or Password not valid", driver.findElement(By.xpath("//body/form[1]/div[1]/div[1]")).getText());
        driver.findElement(By.name("userName")).sendKeys("paziente2");
        driver.findElement(By.name("password")).sendKeys("paziente2");
        WebElement submit1 = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit1.click();
        assertEquals("Shaper", driver.findElement(By.tagName("h1")).getText());
        assertEquals("Paziente 2", driver.findElement(By.xpath("//div[contains(text(),'Paziente 2')]")).getText());
        WebElement link = driver.findElement(By.linkText("View/Edit Profile"));
        link.click();
        assertEquals("Paziente", driver.findElement(By.name("firstname")).getAttribute("value"));
        driver.findElement(By.name("dateofb")).sendKeys("02/12/1967");
        submit1 = driver.findElement(By.xpath("//tbody/tr[7]/td[1]/input[1]"));
        submit1.click();
        link = driver.findElement(By.linkText("Search Professionals"));
        link.click();
        assertEquals("Fill the fields below to apply filters", driver.findElement(By.tagName("h3")).getText());
        driver.findElement(By.name("profession")).clear();
        driver.findElement(By.name("profession")).sendKeys("personal trainer");
        submit = driver.findElement(By.xpath("//tbody/tr[7]/td[1]/input[1]"));
        submit.click();
        assertEquals("Nicolo Squarzoni", driver.findElement(By.xpath("//a[contains(text(),'Nicolo Squarzoni')]")).getText());
        link = driver.findElement(By.linkText("Nicolo Squarzoni"));
        link.click();
        assertEquals("Nicolo Squarzoni", driver.findElement(By.xpath("//div[contains(text(),'Nicolo Squarzoni')]")).getText());
        driver.findElement(By.name("services")).sendKeys("dieta");
        driver.findElement(By.name("message")).sendKeys("ciao");
        submit = driver.findElement(By.xpath("//tbody/tr[7]/td[1]/form[1]/div[5]/input[1]"));
        submit.click();
        link = driver.findElement(By.linkText("Home"));
        link.click();
        assertEquals("Services requested: dieta", driver.findElement(By.xpath("//td[contains(text(),'Services requested:')]")).getText());
    }

    @Test // test scenario 2b
    public void test2b(){
        driver.get("http://localhost:8080/signin");
        driver.findElement(By.name("userName")).clear();
        driver.findElement(By.name("userName")).sendKeys("userName");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("password");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        assertEquals("Nicolo Squarzoni", driver.findElement(By.xpath("//div[contains(text(),'Nicolo Squarzoni')]")).getText());
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'Paziente 2')]"));
        link.click();
        assertEquals("Paziente 2", driver.findElement(By.xpath("//div[contains(text(),'Paziente 2')]")).getText());
        assertEquals("ciao", driver.findElement(By.xpath("//td[contains(text(),'ciao')]")).getText());
        submit = driver.findElement(By.xpath("//body/form[1]/input[4]"));
        submit.click();
        driver.findElement(By.xpath("//body/form[1]/div[1]/input[1]")).sendKeys("1");
        driver.findElement(By.xpath("//body/form[1]/div[2]/input[1]")).sendKeys("2");
        submit = driver.findElement(By.xpath("//body/form[1]/input[5]"));
        submit.click();
        driver.findElement(By.xpath("//body/form[1]/div[3]/input[1]")).sendKeys("3");
        submit = driver.findElement(By.xpath("//body/form[1]/input[4]"));
        submit.click();
        assertEquals(Collections.EMPTY_LIST, driver.findElements(By.xpath("/html[1]/body[1]/table[3]/tbody[1]/tr[2]/td[1]/div[1]/a[1]")));
        assertEquals("Paziente 2", driver.findElement(By.xpath("/html[1]/body[1]/table[2]/tbody[1]/tr[3]/td[1]/div[1]/a[1]")).getText());
    }

    @Test // test scenario 3
    public void test3(){
        driver.get("http://localhost:8080/signin");
        driver.findElement(By.name("userName")).sendKeys("paziente2");
        driver.findElement(By.name("password")).sendKeys("paziente2");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'Nicolo Squarzoni')]"));
        link.click();
        assertEquals("2", driver.findElement(By.xpath("//tbody/tr[3]/td[1]/div[1]/span[2]")).getText());
        driver.findElement(By.xpath("//tbody/tr[2]/td[2]/div[1]/input[1]")).sendKeys("11");
        driver.findElement(By.xpath("//tbody/tr[3]/td[2]/div[1]/input[1]")).sendKeys("22");
        driver.findElement(By.xpath("//tbody/tr[4]/td[2]/div[1]/input[1]")).sendKeys("33");
        submit = driver.findElement(By.xpath("//tbody/tr[5]/td[1]/div[1]/input[1]"));
        submit.click();
        link = driver.findElement(By.xpath("//a[contains(text(),'Nicolo Squarzoni')]"));
        link.click();
        assertEquals("33", driver.findElement(By.xpath("//span[contains(text(),'33')]")).getText());
        link = driver.findElement(By.linkText("Back"));
        link.click();
    }

    @Test // test scenario 4
    public void test4(){
        driver.get("http://localhost:8080/signin");
        driver.findElement(By.name("userName")).clear();
        driver.findElement(By.name("userName")).sendKeys("userName");
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys("password");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        assertEquals("Nicolo Squarzoni", driver.findElement(By.xpath("//div[contains(text(),'Nicolo Squarzoni')]")).getText());
        WebElement link = driver.findElement(By.xpath("/html[1]/body[1]/table[2]/tbody[1]/tr[3]/td[1]/div[1]/a[1]"));
        link.click();
        link = driver.findElement(By.linkText("View Filled Questionnaire"));
        link.click();
        assertEquals("33", driver.findElement(By.xpath("//span[contains(text(),'33')]")).getText());
        link = driver.findElement(By.linkText("Back"));
        link.click();
        link = driver.findElement(By.linkText("Add Diet"));
        link.click();
        link = driver.findElement(By.linkText("New Diet"));
        link.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/h4[1]/input[1]")).sendKeys("1");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[1]/div[1]/input[1]")).sendKeys("c");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[1]/div[2]/input[1]")).sendKeys("uova");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[2]/input[1]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[2]/div[1]/input[1]")).sendKeys("p");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[2]/div[2]/input[1]")).sendKeys("pasta");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[3]/input[2]"));
        submit.click();
        submit = driver.findElement(By.xpath("//tbody/tr[2]/td[1]/h5[1]/input[1]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/h4[1]/input[1]")).sendKeys("2");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/div[1]/div[1]/input[1]")).sendKeys("c2");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/div[1]/div[2]/input[1]")).sendKeys("uova2");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/div[3]/input[2]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h1[1]/input[1]")).clear();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h1[1]/input[1]")).sendKeys("Dieta 1");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h3[1]/input[1]")).sendKeys("massa");
        submit = driver.findElement(By.xpath("//tbody/tr[2]/td[1]/h5[2]/input[1]"));
        submit.click();
        link = driver.findElement(By.linkText("Back"));
        link.click();
        assertEquals("Dieta 1", driver.findElement(By.xpath("//a[contains(text(),'Dieta 1')]")).getText());
        link = driver.findElement(By.xpath("/html[1]/body[1]/table[1]/tbody[1]/tr[2]/td[2]/a[1]"));
        link.click();
        link = driver.findElement(By.linkText("Back"));
        link.click();
        link = driver.findElement(By.linkText("Add Workout Plan"));
        link.click();
        link = driver.findElement(By.linkText("New Workout Plan"));
        link.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/h4[1]/input[1]")).sendKeys("1");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[1]/div[1]/input[2]")).sendKeys("s");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[1]/div[2]/input[1]")).sendKeys("ss");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[1]/div[3]/input[1]")).sendKeys("sss");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[3]/input[1]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[3]/div[1]/input[2]")).sendKeys("p");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[1]/div[4]/input[2]"));
        submit.click();
        submit = driver.findElement(By.xpath("//tbody/tr[2]/td[1]/h5[1]/input[1]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/h4[1]/input[1]")).sendKeys("2");
        submit = driver.findElement(By.xpath("//tbody/tr[1]/td[1]/form[2]/div[3]/input[2]"));
        submit.click();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h1[1]/input[1]")).clear();
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h1[1]/input[1]")).sendKeys("wp 1");
        driver.findElement(By.xpath("//tbody/tr[1]/td[1]/h3[1]/input[1]")).sendKeys("massa");
        submit = driver.findElement(By.xpath("//tbody/tr[2]/td[1]/h5[2]/input[1]"));
        submit.click();
        link = driver.findElement(By.linkText("Back"));
        link.click();
        assertEquals("wp 1", driver.findElement(By.xpath("//a[contains(text(),'wp 1')]")).getText());
        link = driver.findElement(By.linkText("Back"));
        link.click();
    }

    @Test // test scenario 5
    public void test5(){
        driver.get("http://localhost:8080/signin");
        driver.findElement(By.name("userName")).sendKeys("paziente2");
        driver.findElement(By.name("password")).sendKeys("paziente2");
        WebElement submit = driver.findElement(By.xpath("//tbody/tr[3]/td[2]/input[1]"));
        submit.click();
        WebElement link = driver.findElement(By.xpath("//a[contains(text(),'Dieta 1')]"));
        link.click();
        link = driver.findElement(By.xpath("//a[contains(text(),'1')]"));
        link.click();
        assertEquals("pasta", driver.findElement(By.xpath("//div[contains(text(),'pasta')]")).getText());
        link = driver.findElement(By.linkText("Back to Diet"));
        link.click();
        link = driver.findElement(By.xpath("//a[contains(text(),'2')]"));
        link.click();
        assertEquals("uova2", driver.findElement(By.xpath("//div[contains(text(),'uova2')]")).getText());
        link = driver.findElement(By.linkText("Back to Diet"));
        link.click();
        link = driver.findElement(By.linkText("Home"));
        link.click();
        link = driver.findElement(By.xpath("//a[contains(text(),'wp 1')]"));
        link.click();
        link = driver.findElement(By.xpath("//a[contains(text(),'1')]"));
        link.click();
        assertEquals("ss", driver.findElement(By.xpath("//div[contains(text(),'ss')]")).getText());
        assertEquals("p", driver.findElement(By.xpath("//div[contains(text(),'p')]")).getText());
        link = driver.findElement(By.linkText("Back to Workout Plan"));
        link.click();

    }
}
