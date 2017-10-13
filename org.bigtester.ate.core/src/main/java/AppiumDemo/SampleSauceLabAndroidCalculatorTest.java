package AppiumDemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import io.appium.java_client.ios.IOSDriver;
import java.net.URL;
 
public class SampleSauceLabAndroidCalculatorTest {
 
public static final String USERNAME = "chencheninca";
public static final String ACCESS_KEY = "aa7e009b-e265-48a1-9a7b-a4917d5f27b3";
public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
 
    //public static void main(String[] args) throws Exception {
@Test
public void sampleTest() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "iPhone 6");
        capabilities.setCapability("platformVersion", "8.4");
        capabilities.setCapability("app", "https://s3.amazonaws.com/appium/TestApp8.4.app.zip");
        capabilities.setCapability("browserName", "");
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("appiumVersion", "1.6.5");
 
        WebDriver driver = new IOSDriver(new URL(URL), capabilities);
 
        /**
         * Test Actions here...
         */
 
        driver.quit();
        
    }
}