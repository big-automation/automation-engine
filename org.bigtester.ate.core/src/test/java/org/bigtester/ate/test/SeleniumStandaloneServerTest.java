/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2017, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.test;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for selenium standalone server.
 * @author parsentev
 * @since 19.11.2015
 */
public class SeleniumStandaloneServerTest {

	/**
	 * Execute firefox driver.
	 *
	 * @throws MalformedURLException the malformed url exception
	 */
	@Test
	public void executeFirefoxDriver() throws MalformedURLException {
		this.execute(DesiredCapabilities.firefox());
	}

	/**
	 * Execute chrome.
	 *
	 * @throws MalformedURLException the malformed url exception
	 */
	@Test
	public void executeChrome() throws MalformedURLException {
		this.execute(DesiredCapabilities.chrome());
	}

	/**
	 * Execute.
	 *
	 * @param capability the capability
	 * @throws MalformedURLException the malformed url exception
	 */
	private void execute(final DesiredCapabilities capability) throws MalformedURLException {
		WebDriver driver = new RemoteWebDriver(
				new URL("http://192.168.0.105:4444/wd/hub"), capability
		);
		driver.get("http://www.javacodegeeks.com/");
		WebElement element = driver.findElement(By.name("s"));
		element.sendKeys("selenuim");
		element.submit();
		assertThat(
				driver.getTitle(),
				is("You searched for selenuim | Java Code Geeks")
		);
		driver.quit();
	}
}