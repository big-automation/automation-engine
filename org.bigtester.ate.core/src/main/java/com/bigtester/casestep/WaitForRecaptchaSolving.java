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
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.atewebdriver.MyRemoteDriver;
import org.bigtester.ate.model.page.atewebdriver.exception.BrowserUnexpectedException;
import org.bigtester.ate.model.page.elementfind.AbstractElementFind;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException; 
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

// TODO: Auto-generated Javadoc
/**
 * This class SaveAppliedJobReference defines ....
 * @author Peidong Hu
 *
 */
public class WaitForRecaptchaSolving extends AbstractBaseJavaCodedStep
		implements IJavaCodedStep {

	/** The Constant JOBREFERENCESSAVEFILE. */
	private boolean isSolving = true;
	private boolean solved = false;
	final long timeoutInMilliseconds = 30000;
	final long sleepInternalInMilliseconds = 2000;
	
	@SuppressWarnings("null")
	private void checkREcaptchaStatus(IMyWebDriver myDrv) throws  RuntimeDataException {
		long timeHasLooped = 0;
		while (isSolving && !solved) {
		
			try {
				AbstractElementFind.findElementWithMyDriver(By.xpath("//a[contains(text(),'Solving')]"), AbstractElementFind.generateWaitInstance(myDrv.getWebDriver()), myDrv, 0, true);
				isSolving = true;
				solved = false;
			} catch (Throwable e) {
				try {
					AbstractElementFind.findElementWithMyDriver(By.xpath("//a[contains(text(),'Solved')]"), AbstractElementFind.generateWaitInstance(myDrv.getWebDriver()), myDrv, 0, true);
					isSolving = false;
					solved = true;
				} catch (Throwable notInRecaptchaPage) {
					solved = false;
					if (!isSolving) {
						RuntimeDataException rde = new RuntimeDataException(ExceptionMessage.MSG_RUNTIMEDATA_NOTFOUND, ExceptionErrorCode.RUNTIMEDATA_NOTFOUND, notInRecaptchaPage);
						rde.setTestCaseName(this.getTestCase().getTestCaseName());
						rde.setTestStepName(this.getTestCase().getCurrentTestStep().getStepName());
						rde.initAteProblemInstance(this).setFatalProblem(true);
						throw rde;
					}
				}
				
			}
			try {
				Thread.sleep(this.sleepInternalInMilliseconds);
			} catch (InterruptedException e) {
				throw new RuntimeDataException(ExceptionMessage.MSG_RUNTIMEDATA_NOTFOUND, ExceptionErrorCode.RUNTIMEDATA_NOTFOUND, e);
			}
			timeHasLooped = timeHasLooped + this.sleepInternalInMilliseconds;
			if (timeHasLooped > this.timeoutInMilliseconds) break;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void doStep(@Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		checkREcaptchaStatus(getMyWebDriver());

	}

	/**
	 * {@inheritDoc}
	 */
	public void doStep(MyRemoteDriver myDrv, @Nullable IStepJumpingEnclosedContainer jumpingContainer) throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		checkREcaptchaStatus(myDrv);

	}	 

}
