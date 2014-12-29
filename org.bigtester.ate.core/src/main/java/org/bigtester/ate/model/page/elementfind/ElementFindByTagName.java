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
package org.bigtester.ate.model.page.elementfind;

import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.base.Function;

// TODO: Auto-generated Javadoc
/**
 * This class ElementFindByTagName defines ....
 * @author Grace Hu
 *
 */
public class ElementFindByTagName extends AbstractElementFind implements IElementFind{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public WebElement doFind(IMyWebDriver myWebDriver,final String findByValue) {
		//return myWebDriver.getWebDriver().findElement(By.tagName(findByValue));
		createWait(myWebDriver.getWebDriver());
		return wait.until(new Function<WebDriver, WebElement>() {
	        public WebElement apply(WebDriver driver) { //NOPMD
	            return driver.findElement(By.tagName(findByValue));
	        }
	    });
	}


	
}
