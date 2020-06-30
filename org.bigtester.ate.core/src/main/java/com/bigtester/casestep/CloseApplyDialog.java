/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2015, Montreal PROT, or individual contributors as
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
package com.bigtester.casestep;

import java.io.File;
import java.io.IOException;
import java.util.HashSet; 
import java.util.Set;

import org.apache.commons.io.FileUtils; 
import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.casestep.AbstractBaseJavaCodedStep;
import org.bigtester.ate.model.casestep.IJavaCodedStep;
import org.bigtester.ate.model.casestep.IStepJumpingEnclosedContainer;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.MyRemoteDriver;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.elementfind.AbstractElementFind;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException; 
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * This class SaveAppliedJobReference defines ....
 * @author Peidong Hu
 *
 */
public class CloseApplyDialog extends AbstractBaseJavaCodedStep
		implements IJavaCodedStep {

	/** The Constant JOBREFERENCESSAVEFILE. */
	final public static String JOBREFERENCESSAVEFILE = "temp_data/jobReferencesSaveFile"; 
	
	/**
	 * {@inheritDoc}
	 */
	public void doStep(MyRemoteDriver myWebDriver, @Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		myWebDriver.getWebDriver().switchTo().defaultContent();
		if (myWebDriver.getWebDriver() instanceof JavascriptExecutor) {

			JavascriptExecutor jst = (JavascriptExecutor) myWebDriver// NOPMD
					.getWebDriver();
			
				jst.executeScript("arguments[0].click(); ",
						myWebDriver.getWebDriver().findElement(By.xpath("//*[@id='vjs-x']/a")));
			

		} else {
			
			myWebDriver.getWebDriver().findElement(By.xpath("//*[@id='vjs-x']/a")).click();
		}
		/*try {
			AbstractElementFind.findElementWithMyDriver(By.xpath("//*[@id='vjs-x']/a"), AbstractElementFind.generateWaitInstance(getMyWebDriver().getWebDriver()), getMyWebDriver(), 0, false).click();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrowserUnexpectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/	

	}

	@Override
	public void doStep(@Nullable IStepJumpingEnclosedContainer jumpingContainer)
			throws StepExecutionException, PageValidationException, RuntimeDataException {
		getMyWebDriver().getWebDriver().switchTo().defaultContent();
		if (getMyWebDriver().getWebDriver() instanceof JavascriptExecutor) {

			JavascriptExecutor jst = (JavascriptExecutor) getMyWebDriver()// NOPMD
					.getWebDriver();
			
				jst.executeScript("arguments[0].click(); ",
						getMyWebDriver().getWebDriver().findElement(By.xpath("//*[@id='vjs-x']/a")));
			

		} else {
			
			getMyWebDriver().getWebDriver().findElement(By.xpath("//*[@id='vjs-x']/a")).click();
		}
		/*try {
			AbstractElementFind.findElementWithMyDriver(By.xpath("//*[@id='vjs-x']/a"), AbstractElementFind.generateWaitInstance(getMyWebDriver().getWebDriver()), getMyWebDriver(), 0, false).click();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrowserUnexpectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/		
	}

	 

}
