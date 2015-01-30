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

import org.bigtester.ate.GlobalUtils;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class MyIEDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyIEDriver extends WebDriverBase implements IMyWebDriver{
	
	/** The Constant BROWSERNAME. */
	final static private String BROWSERNAME = "webdriver.ie.driver";
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public @Nullable WebDriver getWebDriver() {
		return super.getWebDriver();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebDriver createDriver() {
		OSinfo osinfo = new OSinfo(); 
		EPlatform platform = osinfo.getOSname();
//		System.setProperty("webdriver.ie.driver.loglevel", "ERROR");
//		System.setProperty("webdriver.ie.driver.logfile", "d:/develop/IEDriver64.log");
//		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
//      ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		switch (platform)
		{
			case Windows_32:	
				System.setProperty(BROWSERNAME, "browserdriver/windows/internetexplorer/32bit/2.44.0/IEDriverServer.exe");
				break;
			case Windows_64:	
				System.setProperty(BROWSERNAME, "browserdriver/windows/internetexplorer/64bit/2.44.0/IEDriverServer.exe");
				break;	
			default:
				throw GlobalUtils.createNotInitializedException("operating system is not supported ");
		}        
		WebDriver retVal = new InternetExplorerDriver();
		setWebDriver(retVal);
		return retVal;
	}
	
}
