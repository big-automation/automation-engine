/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
 * By Chen Chen This is a AppiumDemo on Local machine + appium 1.6.5 desktop and Android Emulator
 *******************************************************************************/
package AppiumDemo;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
 
import java.net.MalformedURLException;
import java.net.URL;
/**
 * Created by Chen Chen 
 */
public class CalculatorTestingDemo {
 
    WebDriver driver;
 
    @BeforeClass
    public void setUp() throws MalformedURLException{
        //Set up desired capabilities and pass the Android app-activity
        //and app-package to Appium
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("BROWSER_NAME", "Android");
        capabilities.setCapability("VERSION", "6.0");
        capabilities.setCapability("deviceName","Android6");
        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("device","Android");
        
        // This package name of your app (you can get it from apk info app)
        capabilities.setCapability("appPackage", "com.android.calculator2");
 
        // This is Launcher activity of your app (you can get it from apk info app)
        capabilities.setCapability("appActivity","com.android.calculator2.Calculator");
 
        //Create RemoteWebDriver instance and connect to the Appium server
        //It will launch the Calculator App in Android Device using the configurations
        //specified in Desired Capabilities
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
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