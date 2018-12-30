package Main;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.Select;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NopCommerce {
    protected static WebDriver driver;

    @BeforeEach
    public void open_browser() {
        // the method that will be implemented each time before the below tests runs
        //set path for chrome driver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Rohan Patel\\Src\\Nop-Commerce HW\\src\\main\\broswerDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
        driver.manage().window().fullscreen();
        //website where the tests will run on
        //will run in google chrome browser
        driver.get("https://demo.nopcommerce.com/");

    }

    @AfterEach
    public void close_broswer(){
        //method that will run at the end of each test case
        driver.close();
    }

    @Test
    public void verify_registration (){
        //click on Register button located on the homepage
       click_element(By.linkText("Register"));
       //click on male gender
       click_element(By.id("gender-male"));
       //enter First name
       enter_Text(By.id("FirstName"),("Rohan"));
       //enter Second name
       enter_Text(By.id("LastName"),("Patel"));
       //Declare the drop-down element as an object of the Select class.
        Select dob = new Select(driver.findElement(By.name("DateOfBirthDay")));
        //select an option from the drop down list using index
        dob.selectByIndex(3);
        Select dob1 = new Select(driver.findElement(By.name("DateOfBirthMonth")));
        //select month
        dob1.selectByVisibleText("October");
        Select dob2 = new Select(driver.findElement(By.name("DateOfBirthYear")));
        //select year
        dob2.selectByValue("1995");
        //enter email
        enter_Text(By.id("Email"),("rohanp"+date2+"@gmail.com"));
        //enter company name
        enter_Text(By.name("Company"),("Tester"));
        //enter password
        enter_Text(By.id("Password"),("test123"));
        //confirm password by entering same password again
        enter_Text(By.id("ConfirmPassword"),("test123"));
        //click on the register button
        click_element(By.id("register-button"));
        //declare your expected message
        String expected_Registration_Successful_Message = "Your registration completed";
        //find xpath for the actual message that appears when user successfully registers
        String actual_Registration_Successful_Message = driver.findElement(By.xpath("//div[@class=\"result\"]")).getText();
        //input assertion
        Assert.assertEquals("Test case failed",expected_Registration_Successful_Message,actual_Registration_Successful_Message);
        //cick on log out
        click_element(By.xpath("//ul/li[2]/a[@href=\"/logout\"]"));
    }
    @Test
    public void send_email_with_product_successfully(){
        //call method from previous test into current method
        verify_registration();
        //click on log in button
        click_element(By.linkText("Log in"));
        //enter email
        enter_Text(By.id("Email"),("rohanp"+a+"@gmail.com"));
        //enter password
        enter_Text(By.id("Password"),("test123"));
        //click on login button once details have been entered
        click_element(By.xpath("//input[@class=\"button-1 login-button\"]"));
        //find xpath for one of the products on the homepage, and click on item
        click_element(By.xpath("//div[@class=\"product-item\"]/div[@class=\"picture\"]/a[@href=\"/apple-macbook-pro-13-inch\"]"));
        //click on email a friend option
        click_element(By.xpath("//input[@value=\"Email a friend\"]"));
        //enter friends email
        enter_Text(By.id("FriendEmail"),("jay"+date2+"@gmail.com"));
        //your email does not need to be entered as user is already logged in and option box has been automatically filled
        //enter text in the personal message box
        enter_Text(By.id("PersonalMessage"),("Here is the product"));
        //click on send email
        click_element(By.name("send-email"));
        //create the expected message that should match with the actual message
        String expected_message = "Your message has been sent.";
        //find xpath for actual message user should see when emailing a product to a friend
        String actual_message = driver.findElement(By.xpath("//div[@class=\"result\"]")).getText();
        Assert.assertEquals("Test case failed",expected_message,actual_message);

    }
    @Test
    public void unregistered_user_not_able_to_send_email(){
        //click on a product from the homepage
        click_element(By.xpath("//div[@class=\"product-item\"]/div[@class=\"picture\"]/a[@href=\"/apple-macbook-pro-13-inch\"]"));
        //click on email a friend option
        click_element(By.xpath("//input[@value=\"Email a friend\"]"));
        //enter friends email
        enter_Text(By.id("FriendEmail"),("jay"+date2+"@gmail.com"));
        //enter a new email in the your email box
        enter_Text(By.id("YourEmailAddress"),("Raj19"+date2+"@gmail.com"));
        //enter a text in the personal message box
        enter_Text(By.id("PersonalMessage"),("Hello"));
        //click on send email
        click_element(By.name("send-email"));
        //expected message
        String expected_message = "Only registered customers can use email a friend feature";
        //xpath for the actual message user should see
        String actual_message = driver.findElement(By.xpath("//div[@class=\"message-error validation-summary-errors\"]")).getText();
        //compare expected value/message with actual/message with assert
        Assert.assertEquals("Test case failed",expected_message,actual_message);

    }
    @Test
    public void user_must_accept_terms_and_conditions(){
        //click on any product from the homepage
        click_element(By.xpath("//div[@class=\"product-item\"]/div[@class=\"picture\"]/a[@href=\"/htc-one-m8-android-l-50-lollipop\"]"));
        //click on add to cart button
        click_element(By.id("add-to-cart-button-18"));
        //click on shopping cart button located at the top of the page
        click_element(By.linkText("Shopping cart"));
        //click on box for T&C
        click_element(By.id("termsofservice"));
        //click on checkout
        click_element(By.id("checkout"));
        String expected_message = "Welcome, Please Sign In!";
        String actual_message = driver.findElement(By.xpath("//div[@class=\"page-title\"]")).getText();
        Assert.assertEquals("Test case failed",expected_message,actual_message);
    }
    @Test
    public void Registered_user_should_be_able_to_buy_product_successfully(){
        //calling previous method into current test
        verify_registration();
        //click log in button to log back in
        click_element(By.linkText("Log in"));
        //enter email
        enter_Text(By.id("Email"),("rohanp"+a+"@gmail.com"));
        //enter password
        enter_Text(By.id("Password"),("test123"));
        //click login button
        click_element(By.xpath("//input[@class=\"button-1 login-button\"]"));
        //click on product
        click_element(By.xpath("//div[@class=\"product-item\"]/div[@class=\"picture\"]/a[@href=\"/htc-one-m8-android-l-50-lollipop\"]"));
        //click add to cart button to add product to basket
        click_element(By.id("add-to-cart-button-18"));
        //click on shopping cart
        click_element(By.linkText("Shopping cart"));
        //select box fin order to accept T&C
        click_element(By.id("termsofservice"));
        //click on the checkout button
        click_element(By.id("checkout"));
        //Declare the drop-down element as an object of the Select class.
        Select country = new Select(driver.findElement(By.id("BillingNewAddress_CountryId")));
        //select United Kinngdom by Visible Text
        country.selectByVisibleText("United Kingdom");
        //enter City: London
        enter_Text(By.id("BillingNewAddress_City"),("London"));
        //Enter billing address
        enter_Text(By.id("BillingNewAddress_Address1"),("245 New Gardens Road"));
        //enter post code
        enter_Text(By.id("BillingNewAddress_ZipPostalCode"),("HA3 0PD"));
        //enter phone number
        enter_Text(By.id("BillingNewAddress_PhoneNumber"),("078564223474"));
        //click on continue
        click_element(By.xpath("//input[@onclick=\"Billing.save()\"]"));
        //click on continue
        click_element(By.xpath("//input[@onclick=\"ShippingMethod.save()\"]"));
        //click on the pay with card option
        click_element(By.id("paymentmethod_1"));
        click_element(By.xpath("//input[@onclick=\"PaymentMethod.save()\"]"));
        //enter cardholder name
        enter_Text(By.id("CardholderName"),("R PATEL"));
        //enter card number
        enter_Text(By.id("CardNumber"),("4111111111111111"));
        //enter expiry month
        Select expiry_month = new Select(driver.findElement(By.id("ExpireMonth")));
        expiry_month.selectByIndex(2);
        Select expiry_year = new Select(driver.findElement(By.id("ExpireYear")));
        //enter expiry year
        expiry_year.selectByValue("2020");
        //enter cvv
        enter_Text(By.id("CardCode"),("737"));
        click_element(By.xpath("//input[@onclick=\"PaymentInfo.save()\"]"));
        //click on confirm order
        click_element(By.xpath("//input[@onclick=\"ConfirmOrder.save()\"]"));
        //enter expected message
        String expected_message = "Your order has been successfully processed!";
        //enter xpth for actual message user should see upon purchasing an item successfully
        String actual_message = driver.findElement(By.xpath("//div[@class=\"section order-completed\"]/div[@class=\"title\"]/strong")).getText();
        Assert.assertEquals("Test case failed",expected_message,actual_message);

    }

    @Test
    public void user_should_be_able_to_sort_price_High_to_Low(){
        //select computers from the homepage
        click_element(By.linkText("Computers"));
        //select notebooks
        click_element(By.xpath("//div[@class=\"picture\"]/a[@href=\"/notebooks\"]"));
        //select sort by price: high to low option
        Select sort_by = new Select(driver.findElement(By.id("products-orderby")));
        sort_by.selectByIndex(4);
    }




    public void click_element(By by){
        driver.findElement(by).click();
    }
    public void enter_Text(By by, String a){
        driver.findElement(by).sendKeys(a);
    }

    DateFormat dateFormat = new SimpleDateFormat("MMddHHmmss");
    Date date = new Date();
    //store date as date2
    //each time test script runs, the date format will change
    //allowing for new emails to be used each time
    String date2 = dateFormat.format(date);
    String a = date2;
}







