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
package org.bigtester.ate.model.casestep;//NOPMD

import java.util.List;
import java.util.stream.Collectors;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.TestCaseLoggable;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.data.BaseInputDataValue;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementaction.BaseElementAction;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.project.TestProject;
import org.bigtester.ate.model.utils.ThinkTime;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testng.Reporter;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * The Class TestCase defines ....
 * 
 * @author Peidong Hu
 */
public class TestCase implements ITestCase, IStepJumpingEnclosedContainer, ApplicationContextAware{

	/** The current web driver. */
	@Nullable
	private IMyWebDriver currentWebDriver;
	/** The test case name. */
	private String testCaseName;

	/** The step think time. */
	private int stepThinkTime;

	/** The current test step. */
	@Nullable
	private ITestStep currentTestStep;
	

	/** The test step list. */
	@Nullable
	private List<ITestStep> testStepList;

	/** The parent test project. */
	@Nullable
	@XStreamOmitField
	private TestProject parentTestProject;

	/** The test case context. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext testCaseContext;
	/**
	 * Instantiates a new test case.
	 *
	 * @param testCaseName
	 *            the test case name
	 */
	public TestCase(String testCaseName) {
		this.testCaseName = testCaseName;

	}

	/**
	 * @return the stepThinkTime
	 */
	public int getStepThinkTime() {
		return stepThinkTime;
	}

	/**
	 * @param stepThinkTime
	 *            the stepThinkTime to set
	 */
	public void setStepThinkTime(int stepThinkTime) {
		this.stepThinkTime = stepThinkTime;
	}

	/**
	 * Clean up asserters.
	 */
	@SuppressWarnings("null")
	protected void cleanUpAsserters() {
		for (int i = 0; i < getTestStepList().size(); i++) {
			if (!getTestStepList().get(i).getExpectedResultAsserter().isEmpty()) {
				getTestStepList()
						.get(i)
						.getExpectedResultAsserter()
						.removeAll(
								getTestStepList().get(i)
										.getExpectedResultAsserter());
			}
		}
	}

	/**
	 * Gets the test step list.
	 * 
	 * @return the testStepList
	 */
	public List<ITestStep> getTestStepList() throws IllegalStateException {
		final List<ITestStep> testStepList2 = testStepList;
		if (null == testStepList2) {
			throw new IllegalStateException(
					"Test Step List was not successfully initialized by ApplicationContext");
		} else {
			return testStepList2;
		}
	}

	/**
	 * Sets the test step list.
	 * 
	 * @param testStepList
	 *            the testStepList to set
	 */
	public final void setTestStepList(final List<ITestStep> testStepList) {
		this.testStepList = testStepList;
	}

	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	@SuppressWarnings("null")
	@TestCaseLoggable (level=ATELogLevel.INFO)
	public void goSteps() throws StepExecutionException,
			PageValidationException, IllegalStateException,
			RuntimeDataException {
		if (getParentTestProject().getCucumberActionNameValuePairs()!=null) {
			getParentTestProject().getCucumberActionNameValuePairs().forEach(pair->{
				Object bea = this.getTestCaseContext().getBean(pair.getActionName());
				if (GlobalUtils.getTargetObject(bea) instanceof BaseElementAction) {
					IStepInputData inputData = ((BaseElementAction)GlobalUtils.getTargetObject(bea)).getDataValue();
					if (GlobalUtils.getTargetObject(inputData) instanceof BaseInputDataValue) {
						((BaseInputDataValue)GlobalUtils.getTargetObject(inputData)).setStrDataValue(pair.getValue());
					} else {
						throw GlobalUtils.createInternalError("can't find the value holder for: "+pair.getValue()+" in test case");
					}
				} else {
					throw GlobalUtils.createInternalError("can't find the action: "+pair.getActionName()+" in test case");
				}
			});
		}
		goSteps(getParentTestProject().getFilteringStepName());
	}

	/**
	 * Gets the test case name.
	 *
	 * @return the testCaseName
	 */
	public String getTestCaseName() {
		return testCaseName;
	}

	/**
	 * Sets the test case name.
	 *
	 * @param testCaseName
	 *            the testCaseName to set
	 */
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	/**
	 * Gets the current test step.
	 *
	 * @return the currentTestStep
	 */
	public ITestStep getCurrentTestStep() throws IllegalStateException {
		final ITestStep currentTestStep2 = currentTestStep;
		if (null == currentTestStep2) {
			throw new IllegalStateException(
					"Current Test Step was not successfully initialized by ate");
		} else {
			return currentTestStep2;
		}
	}

	/**
	 * Sets the current test step.
	 *
	 * @param currentTestStep
	 *            the currentTestStep to set
	 */
	public void setCurrentTestStep(ITestStep currentTestStep) {
		this.currentTestStep = currentTestStep;
	}

	/**
	 * Gets the current web driver.
	 *
	 * @return the currentWebDriver
	 */
	public IMyWebDriver getCurrentWebDriver() throws IllegalStateException {
		final IMyWebDriver currentWebDriver2 = currentWebDriver;
		if (null == currentWebDriver2) {
			throw new IllegalStateException(
					"Current web driver was not successfully initialized by ate");
		} else {
			return currentWebDriver2;
		}
	}

	/**
	 * Sets the current web driver.
	 *
	 * @param currentWebDriver
	 *            the currentWebDriver to set
	 */
	public void setCurrentWebDriver(IMyWebDriver currentWebDriver) {
		this.currentWebDriver = currentWebDriver;
	}

	/**
	 * @return the parentTestProject
	 */
	public TestProject getParentTestProject() {
		final TestProject parentTestProject2 = parentTestProject;
		if (parentTestProject2 == null) {
			throw GlobalUtils
					.createNotInitializedException("parent test project");
		} else {
			return parentTestProject2;
		}
	}

	/**
	 * @param parentTestProject
	 *            the parentTestProject to set
	 */
	public void setParentTestProject(TestProject parentTestProject) {
		this.parentTestProject = parentTestProject;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ITestStep> getContainerStepList() {
		
		return getTestStepList();
	}

	
	/**
	 * Go steps.
	 *
	 * @param filteringStepName the filtering step name
	 * @throws StepExecutionException the step execution exception
	 * @throws PageValidationException the page validation exception
	 * @throws IllegalStateException the illegal state exception
	 * @throws RuntimeDataException the runtime data exception
	 */
	@SuppressWarnings({ "null", "unused" })
	private void goSteps(String filteringStepName) throws StepExecutionException,
			PageValidationException, IllegalStateException,
			RuntimeDataException {
		int startIndex=0;
		int endIndex = getTestStepList().size()-1;
		if (filteringStepName!=null ) {
			List<ITestStep> filteredSteps = getTestStepList().stream().filter(tStep->tStep.getStepName().equalsIgnoreCase(filteringStepName) ).collect(Collectors.toList());
			if (filteredSteps.size()==1) {//NOPMD
				if ( GlobalUtils.getTargetObject(filteredSteps.get(0)) instanceof RepeatStep) {
					RepeatStep rStep = (RepeatStep) GlobalUtils.getTargetObject(filteredSteps.get(0));
					startIndex = RepeatStep.getStepIndex(getTestStepList(), rStep.getStartStepName());
					endIndex = getTestStepList().indexOf(filteredSteps.get(0));
				} else {
					startIndex = getTestStepList().indexOf(filteredSteps.get(0));
					endIndex = getTestStepList().indexOf(filteredSteps.get(0));
				}
			} else if (filteredSteps.size()>1){//NOPMD
				if ( GlobalUtils.getTargetObject(filteredSteps.get(0)) instanceof RepeatStep) {
					throw GlobalUtils.createInternalError("there are more than 1 same named (repeat) steps in xml implementation.");
				} else {
					throw GlobalUtils.createInternalError("there are more than 1 same named steps in xml implementation.");
				}
				
			} else {
				throw GlobalUtils.createInternalError("there are 0 step name found in xml implementation.");
			}
		}
		for (int i = startIndex; i <= endIndex; i++) {
			
			ITestStep currentTestStepTmp = getTestStepList().get(i);

			if (null == currentTestStepTmp) {
				throw new IllegalStateException(
						"Test Step List was not successfully initialized by ApplicationContext at list index"
								+ i);
			} else {
				setCurrentTestStep(currentTestStepTmp);
			}

			try {
				currentTestStepTmp.doStep(this);// NOPMD
				currentTestStepTmp.setStepResultStatus(StepResultStatus.PASS);
				setCurrentTestStep(currentTestStepTmp);
			} catch (Exception e) { // NOPMD
				setCurrentTestStep(currentTestStepTmp);
				IATEProblem prob;
				if (e instanceof IATEProblemCreator) {//NOPMD
					prob = ((IATEProblemCreator) e).getAteProblem();
					ITestStep exceptionRaisingStep = ((AbstractATEException) e).getOriginalStepRaisingException();
					if (prob == null) {
						if (null == exceptionRaisingStep)
							prob = ((IATEProblemCreator) e)
								.initAteProblemInstance(currentTestStepTmp);
						else
							prob = ((IATEProblemCreator) e)
							.initAteProblemInstance(exceptionRaisingStep);
					} 
					boolean optionalStepRaisingException = false;//NOPMD
					if (exceptionRaisingStep != null)
						optionalStepRaisingException = exceptionRaisingStep.isOptionalStep(); //NOPMD
					if (!prob.isFatalProblem() && prob.getStepIndexSkipTo() > -1) { // NOPMD
						i = prob.getStepIndexSkipTo(); // NOPMD
						if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.NEUTRAL);
						else
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.SKIP);
					} else if (!prob.isFatalProblem() && optionalStepRaisingException) {
						int correlatedOptionalStepsUtilInclusiveIndex = -1;//NOPMD
						if (exceptionRaisingStep != null)
							correlatedOptionalStepsUtilInclusiveIndex = exceptionRaisingStep.getCorrelatedOptionalStepsUtilInclusiveIndex(this); //NOPMD
						if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class)
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.NEUTRAL);
						else
							currentTestStepTmp
									.setStepResultStatus(StepResultStatus.SKIP);
						if (correlatedOptionalStepsUtilInclusiveIndex > i) {
							i = correlatedOptionalStepsUtilInclusiveIndex;// NOPMD

						}
					} else {
						Reporter.getCurrentTestResult().setThrowable(e);
						throw e;
						
					}
				} else {
						Reporter.getCurrentTestResult().setThrowable(e);
						throw e;
				}
				
			}

			if (stepThinkTime > 0) {
				ThinkTime thinkTimer = new ThinkTime(stepThinkTime);
				thinkTimer.setTimer();
			}

		}
		Reporter.getCurrentTestResult().setThrowable(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(@SuppressWarnings("null") ApplicationContext applicationContext)
			throws BeansException {
		// TODO Auto-generated method stub
		this.testCaseContext = applicationContext;
		
	}

	/**
	 * @return the testCaseContext
	 */
	@SuppressWarnings("null")
	public ApplicationContext getTestCaseContext() {
		return testCaseContext;
	}

	/**
	 * @param testCaseContext the testCaseContext to set
	 */
	public void setTestCaseContext(ApplicationContext testCaseContext) {
		this.testCaseContext = testCaseContext;
	}

}
