package AppiumDemo;
import io.appium.java_client.android.AndroidDriver;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
 
/**
 * Created by Chen Chen 
 */

public class SampleSauceCheckBoxTest2 {
 
  public static final String USERNAME = "chencheninca";
  public static final String ACCESS_KEY = "aa7e009b-e265-48a1-9a7b-a4917d5f27b3";
  public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
  
  
 
  public static void main(String[] args) throws Exception {
 
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("deviceName", "Samsung Galaxy S4 Emulator");
    capabilities.setCapability("platformVersion", "4.4");
    //capabilities.setCapability("app", "http://saucelabs.com/example_files/ContactManager.apk");
    capabilities.setCapability("browserName", "");
    capabilities.setCapability("deviceOrientation", "portrait");
    capabilities.setCapability("appiumVersion", "1.5.3");
    capabilities.setCapability("appPackage", "com.android.calculator2");
    
    // This is Launcher activity of your app (you can get it from apk info app)
    capabilities.setCapability("appActivity","com.android.calculator2.Calculator");
//http://chen.ipd9.info/Calculator.apk
    
    //Create RemoteWebDriver instance and connect to the Appium server
    //It will launch the Calculator App in Android Device using the configurations
    //specified in Desired Capabilities
    //driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    
 
    WebDriver driver2 = new AndroidDriver<>(new URL(URL), capabilities);
    WebDriver driver = new RemoteWebDriver(new URL(URL), capabilities);
    
    
 
    /**
     * Test Actions here...
     */
 
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
    
    driver.quit();
  }
  
  
}