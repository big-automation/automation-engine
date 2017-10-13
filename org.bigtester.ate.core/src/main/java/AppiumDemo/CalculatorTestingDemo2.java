package AppiumDemo;

import org.bigtester.ate.model.page.atewebdriver.MySauceLabAndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Chen Chen 
 */
public class CalculatorTestingDemo2 {
 
    WebDriver driver;
    public static final String USERNAME = "chencheninca";
    public static final String ACCESS_KEY = "aa7e009b-e265-48a1-9a7b-a4917d5f27b3";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    
 
    @BeforeClass
    public void setUp() throws MalformedURLException{
    	
    	DesiredCapabilities capabilities = new DesiredCapabilities();
    	capabilities.setCapability("testobjectApiKey", "5A9C6A605A91413FA8266FB09272261F");
    	
        capabilities.setCapability("platformName", "Android");
        //capabilities.setCapability("deviceName", "LG Nexus 5X");
        capabilities.setCapability("deviceName", "Android6");
        capabilities.setCapability("platformVersion", "6");
        //capabilities.setCapability("app", "http://saucelabs.com/example_files/ContactManager.apk");
        capabilities.setCapability("browserName", "");
        //capabilities.setCapability(“phoneOnly”, true);
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("appiumVersion", "1.6.5");
     
        WebDriver driver = new AndroidDriver<>(new URL(URL), capabilities);
        
        //Set up desired capabilities and pass the Android app-activity
        //and app-package to Appium
        
        // This package name of your app (you can get it from apk info app)
        capabilities.setCapability("appPackage", "com.android.calculator2");
 
        // This is Launcher activity of your app (you can get it from apk info app)
        capabilities.setCapability("appActivity","com.android.calculator2.Calculator");
 
        //Create RemoteWebDriver instance and connect to the Appium server
        //It will launch the Calculator App in Android Device using the configurations
        //specified in Desired Capabilities
        //driver = new RemoteWebDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
        
        //driver = new MySauceLabAndroidDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
    }
 
    @Test
    public void testCal() throws Exception {
        //locate the Text on the calculator by using By.Id() (use UIAutomator ResourceId )
    
    	WebElement one = driver.findElement(By.id("com.android.calculator2:id/digit_1"));
    	one.click();
    	WebElement plus = driver.findElement(By.id("com.android.calculator2:id/op_add"));
    	plus.click();
    	one.click();
    	WebElement equalTo=driver.findElement(By.id("com.android.calculator2:id/eq"));
        equalTo.click();
        //locate the edit box of the calculator by using By.tagName()
        WebElement results=driver.findElement(By.className("android.widget.EditText"));
        //Check the calculated value on the edit box
        assert results.getText().toString().equals("2"):"Actual value is : "
                +results.getText()+" did not match with expected value: 2";
        
    }
 
    @AfterClass
    public void teardown(){
        //close the app
        driver.quit();
    }
}