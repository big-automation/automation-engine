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
package org.bigtester.ate.model.page.elementaction;

import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * The Class SendKeysAction defines ....
 * 
 * @author Peidong Hu
 */
public class SendKeysAction extends BaseElementAction implements
		IElementAction, ITestObjectActionImpl  {

	/**
	 * @param myWd
	 */
	public SendKeysAction(IMyWebDriver myWd, IStepInputData dataValue) {
		super(myWd);
		setDataValue(dataValue);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doAction(final WebElement webElm) {
		IStepInputData inputData = getDataValue();
		if (null == inputData) {
			throw new IllegalStateException("inputDatavalue is not correctly populated.");
		} else {
			if (inputData.getStrDataValue().equals("[TAB]")) {
				webElm.sendKeys(Keys.TAB);
			} else if (inputData.getStrDataValue().equals("[ENTER]")) {
				webElm.sendKeys(Keys.ENTER);
			} else {
				webElm.sendKeys(inputData.getStrDataValue());
			}
			LogbackWriter.writeAppInfo("action tracing: send keys to browser: " + inputData.getStrDataValue());
		}

	}

	

}
