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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyPhantomjs defines ....
 * 
 * @author Jun Yang
 */
public class MyPhantomjsDriver extends AbstractWebDriverBase implements IMyWebDriver {
	
	/** The Constant BROWSERTYPENAME. */
	final public static String BROWSERTYPENAME = "Phantomjs"; 
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "windows/phantomjs/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "windows/phantomjs/64bit/";
	/** The Constant BROWSERLINUX32PATH. */
	private static final String BROWSERLINUX32PATH = "linux/phantomjs/32bit/";
	/** The Constant BROWSERLINUX64PATH. */
	private static final String BROWSERLINUX64PATH = "linux/phantomjs/64bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX32PATH = "osx/phantomjs/32bit/";
	/** The Constant BROWSEROSX32PATH. */
	private static final String BROWSEROSX64PATH = "osx/phantomjs/64bit/";
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "phantomjs.exe";
	/** The Constant BROWSERLINUXFILENAME. */
	private static final String BROWSERLINUXFILENAME = "phantomjs";
	/** The Constant BROWSERMACFILENAME. */
	private static final String BROWSEROSXFILENAME = "phantomjs";

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public WebDriver getWebDriver() {
		return super.getWebDriver();

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver getWebDriverInstance() {
		ArrayList<String> cliArgsCap = new ArrayList<String>();
		DesiredCapabilities caps = DesiredCapabilities.phantomjs();
		cliArgsCap.add("--web-security=false");		
		/* To launch PhantomJS in Remote WebDriver mode at the intended PORT number 
		cliArgsCap.add("--webdriver=4444"); */		
		cliArgsCap.add("--ssl-protocol=any");
		cliArgsCap.add("--ignore-ssl-errors=true");
		caps.setCapability("takesScreenshot", true);
		caps.setCapability("fixSessionCapabilities", false); 
		caps.setCapability(
		    PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
		caps.setCapability(
		    PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
		        new String[] { "--logLevel=2" });
		caps.setJavascriptEnabled(true);        
		WebDriver retVal = getWebDriver();
		if (null == retVal) {
			OSinfo osinfo = new OSinfo();
			EPlatform platform = osinfo.getOSname();
			String driverPath = GlobalUtils.getDriverPath(); //NOPMD
			
			switch (platform) {
			case Windows_32:
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
							           + BROWSERWIN32PATH + BROWSERWINFILENAME);
				else
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
							           + BROWSERWIN32PATH + BROWSERWINFILENAME);
				break;
			case Windows_64:
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
							           + BROWSERWIN64PATH + BROWSERWINFILENAME);
				else
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
							           + BROWSERWIN64PATH + BROWSERWINFILENAME);
				break;
			case Linux_32:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
							           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);
				else
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
							           + BROWSERLINUX32PATH + BROWSERLINUXFILENAME);
				break;
			case Linux_64:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "linux", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
					                   + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);
			    else
			    	caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
						               + BROWSERLINUX64PATH + BROWSERLINUXFILENAME);
				
				break;
			case Mac_OS_X_32:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
				                       + BROWSEROSX32PATH + BROWSEROSXFILENAME);
			    else
			    	caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
				    		           + BROWSEROSX32PATH + BROWSEROSXFILENAME);
				break;
			case Mac_OS_X_64:
				/*versionNum = ReadXmlFile.parserXml(ReadXmlFile.REPOFILENAME, "osx", BROWSERNAME, ReadXmlFile.VERSION);*/
				if (driverPath == null)
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
							           + BROWSEROSX64PATH + BROWSEROSXFILENAME);
				else
					caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, driverPath + GlobalUtils.PATH_DELIMITER 
							           + BROWSEROSX64PATH + BROWSEROSXFILENAME);
				break;
			default:
				throw GlobalUtils.createNotInitializedException("operating system is not supported ");
			}			
			retVal = new PhantomJSDriver(caps);
			setWebDriver(retVal);
		}		
		return retVal;
	}

}
