/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2014, Montreal PROT, or individual contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Montreal PROT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.bigtester.ate.model.page.atewebdriver;

import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

//import AppiumDemo.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Chen Chen
 */
//public class MySauceLabAndroidDriver extends MyRemoteDriver implements IMyWebDriver {
public class MySauceLabAndroidDriver extends RemoteWebDriver  {
	/** The caps. */
	public static final String USERNAME = "chencheninca";
    public static final String ACCESS_KEY = "aa7e009b-e265-48a1-9a7b-a4917d5f27b3";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:443/wd/hub";
    
	private String userName;
	
	/** The url. */
	private String accesskey;
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MySauceLabAndroidDriver(String browserName,String userName, String accesskey) {
		
		//super("chrome", "", Platform.ANY.toString(), "https://" + userName + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		//super("chrome", "", Platform.ANY.toString(), "https://" + userName + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		
		this.setUserName(userName);
		this.setAccesskey(accesskey);
		
		
	}
		
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MySauceLabAndroidDriver() {
		
		super();
		 
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		capabilities.setCapability("platformName", "Android");
		//capabilities.setCapability("deviceName", "Android6");
		capabilities.setCapability("deviceName", "LG Nexus 5X");
		//capabilities.setCapability("platformVersion", "11.0");
		//capabilities.setCapability("app", "https://s3.amazonaws.com/appium/TestApp8.4.app.zip");
		capabilities.setCapability("browserName", "");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("appiumVersion", "1.6.5");
		//
		
        capabilities.setCapability("platformVersion", "7");
        //capabilities.setCapability("app", "http://saucelabs.com/example_files/ContactManager.apk");
        capabilities.setCapability("browserName", "");
        //capabilities.setCapability(“phoneOnly”, true);
     
        //WebDriver driver = new AndroidDriver<>(new URL(URL), capabilities);
        
        //Set up desired capabilities and pass the Android app-activity
        //and app-package to Appium
        
        // This package name of your app (you can get it from apk info app)
        capabilities.setCapability("appPackage", "com.android.calculator2");
 
        // This is Launcher activity of your app (you can get it from apk info app)
        capabilities.setCapability("appActivity","com.android.calculator2.Calculator");
        
        //WebDriver driver = new AndroidDriver<>(new URL(url), capabilities);
        try {
			WebDriver driver = new RemoteWebDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //WebDriver retVal = getWebDriver();

        /*
		try {
			retVal = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//setWebDriver(retVal);
		 
	}


	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	
}
