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
import java.util.Optional; 

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyRemoteDriver extends AbstractWebDriverBase implements IMyWebDriver {

	/** The caps. */
	@SuppressWarnings("null")
	private Optional<DesiredCapabilities> caps = Optional.empty();
	
	/** The url. */
	private String url;
	/**
	 * Instantiates a new my Chrome driver.
	 */
	@SuppressWarnings("null")
	public MyRemoteDriver(String browserName, String version, String platform, String url) {
		
		super();
		if (StringUtils.isEmpty(browserName)){
			caps = Optional.of(DesiredCapabilities.chrome());
			caps.get().setBrowserName("chrome");
		}
		else {
			switch (browserName) {
			case "chrome":
				caps = Optional.of(DesiredCapabilities.chrome());
				caps.get().setBrowserName("chrome");
				break;
			case "firefox":
				caps = Optional.of(DesiredCapabilities.firefox());
				caps.get().setBrowserName("firefox");
				break;
			case "IOS":
				caps = Optional.of(DesiredCapabilities.iphone());
				caps.get().setBrowserName("IOS");
				break;
			default:
				break;
			}
				
		}
		if (!StringUtils.isEmpty(version))
			caps.get().setVersion(version);
		if (!StringUtils.isEmpty(platform))
			caps.get().setPlatform(Platform.valueOf(platform));
		caps.get().setCapability("maxDuration", 10800);
		this.setUrl(url);
	}
		
	/**
	 * Instantiates a new my Chrome driver.
	 */
	@SuppressWarnings("null")
	public MyRemoteDriver() {		
		super();		 
	}	
		
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
	@SuppressWarnings("null")
	@Override
	public WebDriver getWebDriverInstance() {
		WebDriver retVal = getWebDriver();
		if (null == retVal || !(((EventFiringWebDriver)retVal).getWrappedDriver() instanceof RemoteWebDriver)) {
			
			try {
				RemoteWebDriver remoteVal = new RemoteWebDriver(new URL(url), caps.get());
				remoteVal.setFileDetector(new LocalFileDetector());
				retVal = remoteVal;
			} catch (MalformedURLException e) {
				//TODO add handling for bad url
				e.printStackTrace();
			}
			
			setWebDriver(retVal);
		}
		return retVal;

	}

	/**
	 * @return the caps
	 */
	public Optional<DesiredCapabilities> getCaps() {
		return caps;
	}

	/**
	 * @param caps the caps to set
	 */
	public void setCaps(Optional<DesiredCapabilities> caps) {
		this.caps = caps;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
