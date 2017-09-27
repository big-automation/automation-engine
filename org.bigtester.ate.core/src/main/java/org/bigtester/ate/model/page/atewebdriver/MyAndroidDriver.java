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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyandroidDriver defines ....
 * 
 * @author Wei Shao
 */
public class MyAndroidDriver extends AbstractWebDriverBase implements IMyWebDriver {
			
	/** The Constant BROWSERNAME. */
	/*private static final String BROWSERNAME = "android";*/
	/** The Constant BROWSERDRVNAME. */
	private static final String BROWSERDRVNAME = "webdriver.android.driver";
	/** The Constant BROWSERWIN32PATH. */
	private static final String BROWSERWIN32PATH = "windows/android/32bit/";
	/** The Constant BROWSERWIN64PATH. */
	private static final String BROWSERWIN64PATH = "windows/android/64bit/";	
	/** The Constant BROWSERWINFILENAME. */
	private static final String BROWSERWINFILENAME = "MicrosoftWebDriver.exe";	

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
		WebDriver retVal = getWebDriver();
		if (null == retVal) {
			OSinfo osinfo = new OSinfo();
			EPlatform platform = osinfo.getOSname();
			String driverPath = GlobalUtils.getDriverPath(); //NOPMD
			
			switch (platform) {
				case Windows_32:
					if (driverPath == null)
						System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
								           + BROWSERWIN32PATH + BROWSERWINFILENAME);
					else
						System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
								           + BROWSERWIN32PATH + BROWSERWINFILENAME);
					break;
				case Windows_64:
					if (driverPath == null)
						System.setProperty(BROWSERDRVNAME, GlobalUtils.DEFAULT_DRIVER_PATH + GlobalUtils.PATH_DELIMITER 
								           + BROWSERWIN64PATH + BROWSERWINFILENAME);
					else
						System.setProperty(BROWSERDRVNAME, driverPath + GlobalUtils.PATH_DELIMITER 
								           + BROWSERWIN64PATH + BROWSERWINFILENAME);
					break;
				default:
					throw GlobalUtils.createNotInitializedException("operating system is not supported ");
			}				
			retVal = new RemoteWebDriver(DesiredCapabilities.android());
			setWebDriver(retVal);
		}		
		return retVal;
	}

}