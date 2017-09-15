package AppiumDemo;


import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalDemo {

private AndroidDriver driver;

@Before
public void calc() throws Exception {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability(CapabilityType.BROWSER_NAME, "");// 这句不是必须的
    capabilities.setCapability("deviceName", "Android6");
    capabilities.setCapability("platformVersion", "6.0");
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("appPackage", "com.android.calculator2");
    capabilities.setCapability("appActivity", ".Calculator");
    driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
    int timeout = 5000;
    int interval = 500;

    //public static AppiumDriver driver;
    //driver2 = new AndroidDriver(new URL(remoteAddress), capabilities);
    //((AndroidDriver) driver).setNetworkConnection(new NetworkConnectionSetting(false, true, true));
    
    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    
}

@After
public void tearDown() throws Exception {
    driver.quit();
}

@Test
public void add() {
    driver.findElementByName("1").click();
    
    driver.findElementByName("+").click();
    driver.findElementByName("2").click();
    driver.findElementByName("=").click();
    driver.quit();
    }
}