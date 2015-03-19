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
package org.bigtester.ate.model.casestep;


import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.data.IDataParser;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.page.IPageObject;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractTestStep defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class BaseTestStep implements ApplicationContextAware {//NOPMD
	/** The current iteration. */
	private int currentIteration;
	
	/** The current repeat step name. */
	private String currentRepeatStepName = "";
	
	/** The application context. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext applicationContext;
	
	/** The step logger. */
	@Nullable
	private RepeatStepExecutionLogger repeatStepLogger; 

	/** The data holders. */
	private List<IDataParser> dataHolders = new ArrayList<IDataParser>();

	/** The element step flag. */
	
	private transient boolean elementStepFlag;

	/** The i expected result asserter. */
	private List<IExpectedResultAsserter> expectedResultAsserter = new ArrayList<IExpectedResultAsserter>();
	
	/** The forced page validation. */
	private boolean forcedPageValidation;

	/** The my web element. */
	@Nullable
	protected MyWebElement myWebElement;

	/** The optional step. default is false*/
	private boolean optionalStep;
	
	/** The page object. */
	@Nullable
	private IPageObject pageObject;
	
	/** The step description. */
	@Nullable
	private String stepDescription = "";
	
	/** The step name. */
	private String stepName = "";
	
	/** The passed. */
	private StepResultStatus stepResultStatus = StepResultStatus.FAIL;
	
	/** The target step. */
	private boolean targetStep;
	
	/**
	 * Instantiates a new base test step.
	 */
	public BaseTestStep() {
		elementStepFlag = false;
	}
	
	/**
	 * Instantiates a new base test step.
	 *
	 * @param pageObject the page object
	 */
	public BaseTestStep( IPageObject pageObject ) {
		this.pageObject = pageObject;
	}
	/**
	 * Instantiates a new base test step.
	 *
	 * @param pageObject the page object
	 * @param myWebElement the my web element
	 */
	public BaseTestStep( IPageObject pageObject, @Nullable MyWebElement myWebElement) {
		this.pageObject = pageObject;
		this.myWebElement = myWebElement;
	}
	
	/**
	 * Instantiates a new base test step.
	 *
	 * @param myWebElement the my web element
	 */
	public BaseTestStep( MyWebElement myWebElement) {
		this.myWebElement = myWebElement;
	}
	
	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 * @throws IllegalStateException the illegal state exception
	 */
	public ApplicationContext getApplicationContext() throws IllegalStateException{
		
		final ApplicationContext applicationContext2 = applicationContext;
		if (null == applicationContext2) {
			throw new IllegalStateException("applicationContext is not correctly initialized in test step");
		} else {
			return applicationContext2;
		}
	}
	
	/**
	 * Gets the data holders.
	 *
	 * @return the dataHolders
	 */
	public List<IDataParser> getDataHolders() {
		return dataHolders;
		
	}
	
	/**
	 * Gets the expected result asserter.
	 *
	 * @return the iExpectedResultAsserter
	 */
	public List<IExpectedResultAsserter> getExpectedResultAsserter() {
		return expectedResultAsserter;
	}

	/**
	 * Gets the my web element.
	 * 
	 * @return the myWebElement
	 */
	@Nullable
	public MyWebElement getMyWebElement() {
		return myWebElement;
	}

	/**
	 * Gets the page object.
	 * 
	 * @return the pageObject
	 */
	@Nullable
	public IPageObject getPageObject() {
		return pageObject;
	}

	/**
	 * Gets the step description.
	 * 
	 * @return the stepDescription
	 */
	@Nullable
	public String getStepDescription() {
		return stepDescription;
	}

	/**
	 * Gets the step name.
	 * 
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Gets the step result status.
	 *
	 * @return the stepResultStatus
	 */
	public StepResultStatus getStepResultStatus() {
		return stepResultStatus;
	}

	/**
	 * Checks if is element step flag.
	 *
	 * @return the elementStepFlag
	 */
	public boolean isElementStepFlag() {
		return elementStepFlag;
	}

	/**
	 * Checks if is forced page validation.
	 *
	 * @return the forcedPageValidation
	 */
	public boolean isForcedPageValidation() {
		return forcedPageValidation;
	}

	/**
	 * Checks if is optional step.
	 *
	 * @return the optionalStep
	 */
	public boolean isOptionalStep() {
		return optionalStep;
	}

	/**
	 * Checks if is page validation.
	 *
	 * @return true, if is page validation
	 */
	public boolean isPageValidation() {
		
		return forcedPageValidation ? true : targetStep;
	
	}

	
	/**
	 * Checks if is target step.
	 * 
	 * @return the targetStep
	 */
	public boolean isTargetStep() {
		return targetStep;
	}

	/**
	 * Parses the data holder.
	 *
	 * @throws RuntimeDataException the runtime data exception
	 */
	protected void parseDataHolder() throws RuntimeDataException {
		for (int i=0; i<getDataHolders().size(); i++) {
			getDataHolders().get(i).parseData();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(@Nullable ApplicationContext applicationContext)
			throws BeansException {
		if (null == applicationContext) {
			throw new NoSuchBeanDefinitionException(ApplicationContext.class);
		} else {
			this.applicationContext = applicationContext;
		}
		
	}

	/**
	 * Sets the data holders.
	 *
	 * @param dataHolders the dataHolders to set
	 */
	public void setDataHolders(List<IDataParser> dataHolders) {
		this.dataHolders = dataHolders;
	}

	/**
	 * Sets the expected result asserter.
	 *
	 * @param iExpectedResultAsserter the iExpectedResultAsserter to set
	 */
	public void setExpectedResultAsserter(List<IExpectedResultAsserter> iExpectedResultAsserter) {
		this.expectedResultAsserter = iExpectedResultAsserter;
	}

	/**
	 * Sets the forced page validation.
	 *
	 * @param forcedPageValidation the forcedPageValidation to set
	 */
	public void setForcedPageValidation(boolean forcedPageValidation) {
		this.forcedPageValidation = forcedPageValidation;
	}

	/**
	 * Sets the my web element.
	 * 
	 * @param myWebElement
	 *            the myWebElement to set
	 */
	public final void setMyWebElement(final MyWebElement myWebElement) {
		this.elementStepFlag = true;
		this.myWebElement = myWebElement;
	}
	
	/**
	 * Sets the optional step.
	 *
	 * @param optionalStep the optionalStep to set
	 */
	public void setOptionalStep(boolean optionalStep) {
		this.optionalStep = optionalStep;
	}
	/**
	 * Sets the page object.
	 * 
	 * @param pageObject
	 *            the pageObject to set
	 */
	public void setPageObject(IPageObject pageObject) {
		this.pageObject = pageObject;
	}
	/**
	 * Sets the step description.
	 * 
	 * @param stepDescription
	 *            the stepDescription to set
	 */
	public void setStepDescription(String stepDescription) {
		this.stepDescription = stepDescription;
	}
	
	/**
	 * Sets the step name.
	 * 
	 * @param stepName
	 *            the stepName to set
	 */
	public void setStepName(final String stepName) {
		this.stepName = stepName;
	}
	
	
	/**
	 * Sets the step result status.
	 *
	 * @param stepResultStatus the stepResultStatus to set
	 */
	public void setStepResultStatus(StepResultStatus stepResultStatus) {
		this.stepResultStatus = stepResultStatus;
	}
	/**
	 * Sets the target step.
	 * 
	 * @param targetStep
	 *            the targetStep to set
	 */
	public void setTargetStep(boolean targetStep) {
		this.targetStep = targetStep;
	}

	/**
	 * @return the currentIteration
	 */
	public int getCurrentIteration() {
		return currentIteration;
	}

	/**
	 * @param currentIteration the currentIteration to set
	 */
	public void setCurrentIteration(int currentIteration) {
		this.currentIteration = currentIteration;
	}

	/**
	 * @return the currentRepeatStepName
	 */
	public String getCurrentRepeatStepName() {
		return currentRepeatStepName;
	}

	/**
	 * @param currentRepeatStepName the currentRepeatStepName to set
	 */
	public void setCurrentRepeatStepName(String currentRepeatStepName) {
		this.currentRepeatStepName = currentRepeatStepName;
	}

	/**
	 * @param elementStepFlag the elementStepFlag to set
	 */
	public void setElementStepFlag(boolean elementStepFlag) {
		this.elementStepFlag = elementStepFlag;
	}

	/**
	 * @return the stepLogger
	 */
	public RepeatStepExecutionLogger getRepeatStepLogger() {
		final RepeatStepExecutionLogger repeatStepLogger2 = repeatStepLogger;
		if (repeatStepLogger2 == null) {
			throw GlobalUtils.createNotInitializedException("repeatStepLogger");
		} else {
			return repeatStepLogger2;
		}
	}

	/**
	 * @param repeatStepLogger the repeatStepLogger to set
	 */
	public void setRepeatStepLogger(RepeatStepExecutionLogger repeatStepLogger) {
		this.repeatStepLogger = repeatStepLogger;
	}



	
}
