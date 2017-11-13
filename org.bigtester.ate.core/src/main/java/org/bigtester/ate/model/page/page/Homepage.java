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
package org.bigtester.ate.model.page.page;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.WebDriver;




// TODO: Auto-generated Javadoc
/**
 * The Class Homepage defines ....
 * 
 * @author Peidong Hu
 */
public class Homepage extends BasePageObject implements IHomepage{

	

	/** The home url. */
	private String homeUrl;
	/**
	 * @param myWd
	 */
	public Homepage(IMyWebDriver myWd, String homeUrl) {
		super(myWd);
		this.homeUrl = homeUrl;
	}
	/**
	 * @param myWd
	 */
	public Homepage(String homeUrl) {
		
		this.homeUrl = homeUrl;
	}
	/**
	 * Sets the home url.
	 * 
	 * @param homeUrl
	 *            the new home url
	 */
	public void setHomeUrl(final String homeUrl) {
		this.homeUrl = homeUrl;
	}

	/**
	 * Gets the home url.
	 * 
	 * @return the home url
	 */
	public String getHomeUrl() {
		return homeUrl;
	}

	/**
	 * Start homepage.
	 */
	public void startHomepage() {
		WebDriver webD = super.getMyWd().getWebDriver();
		if (null == webD) {
			throw new IllegalStateException("webdriver is not correctly populated.");
		} else {
			webD.get(homeUrl);
		}
	}

	
	

		
}
