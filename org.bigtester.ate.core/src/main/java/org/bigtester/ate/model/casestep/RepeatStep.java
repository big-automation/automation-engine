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
package org.bigtester.ate.model.casestep;//NOPMD

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.StepLoggable;
import org.bigtester.ate.constant.StepResultStatus;
import org.bigtester.ate.model.casestep.RepeatStepInOutEvent.RepeatStepInOut;
import org.bigtester.ate.model.data.IOnTheFlyData;
import org.bigtester.ate.model.data.IRepeatIncrementalIndex;
import org.bigtester.ate.model.data.IStepERValue;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.elementaction.IElementAction;
import org.bigtester.ate.model.page.elementaction.ITestObjectAction;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.bigtester.ate.model.utils.ThinkTime;
import org.bigtester.ate.systemlogger.IATEProblemCreator;
import org.bigtester.ate.systemlogger.problems.IATEProblem;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.aop.support.AopUtils;

// TODO: Auto-generated Javadoc
/**
 * This class RepeatStep defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RepeatStep extends BaseTestStep implements ITestStep, Cloneable {

	/** The start step id. */
	private String startStepName;

	/** The end step id. */
	private String endStepName;

	/** The continue on failure. */
	private boolean continueOnFailure;

	/** The asserter value remain same. */
	private boolean asserterValuesRemainSame;
	/** The repeat times. */
	private int numberOfIterations;

	/** The step i ds. */
	final private List<Integer> stepIndexes = new ArrayList<Integer>();

	/** The repeating steps. */
	final private List<ITestStep> repeatingSteps = new ArrayList<ITestStep>();

	/** The refresh data values. */
	final private List<IStepInputData> refreshDataValues = new ArrayList<IStepInputData>();

	/** The refresh er values. */
	final private List<IStepERValue> refreshERValues = new ArrayList<IStepERValue>();

	/** The refresh on the fly values. */
	final private List<IRepeatIncrementalIndex> refreshIndexValues = new ArrayList<IRepeatIncrementalIndex>();

	/** The external repeat node of this step. */
	private transient @Nullable RepeatStepExecutionLoggerNode externalRepeatNodeOfThisStep;

	/** The current repeat node of this step. */
	private transient @Nullable RepeatStepExecutionLoggerNode currentRepeatNodeOfThisStep;

	// /** The input data holders. */
	// final private List<IStepInputData> inputDataHolders;
	//
	// /** The data parsers. */
	// final private List<IDataParser> dataParsers;
	//
	// final private List<IExpectedResultAsserter> expectedResultAsserters;

	/**
	 * Instantiates a new repeat step.
	 *
	 * @param startStepName
	 *            the start step name
	 * @param endStepName
	 *            the end step name
	 * @param testCase
	 *            the test case
	 */
	public RepeatStep(String startStepName, String endStepName) {
		super();
		this.startStepName = startStepName;
		this.endStepName = endStepName;
		this.continueOnFailure = false;
		this.numberOfIterations = 1;
		// this.testCase = testCase;
		this.asserterValuesRemainSame = true;

	}

	private void buildRepeatStepContext() {
		stepIndexes.clear();
		repeatingSteps.clear();
		refreshERValues.clear();
		refreshDataValues.clear();

		int startIndex = -1; // NOPMD
		int endIndex = -1; // NOPMD

		for (int i = 0; i < getTestCase().getTestStepList().size(); i++) {
			if (getTestCase().getTestStepList().get(i).getStepName()
					.equals(this.startStepName)) {
				startIndex = i;// NOPMD
			}
			if (getTestCase().getTestStepList().get(i).getStepName()
					.equals(this.endStepName)) {
				endIndex = i;// NOPMD
			}
		}
		if (startIndex == -1 || endIndex == -1)
			throw GlobalUtils
					.createNotInitializedException("startStepName or endStepName");
		for (int i = 0; i < getTestCase().getTestStepList().size(); i++) {
			if (i >= startIndex && i <= endIndex) {
				stepIndexes.add(i);
				ITestStep thisStep = getTestCase().getTestStepList().get(i);
				repeatingSteps.add(thisStep);
				for (int asserterIndex = 0; asserterIndex < thisStep
						.getExpectedResultAsserter().size(); asserterIndex++) {
					refreshERValues.add((IStepERValue) GlobalUtils
							.getTargetObject(thisStep
									.getExpectedResultAsserter()
									.get(asserterIndex).getStepERValue()));
				}

				if (thisStep instanceof IElementStep) {
					MyWebElement<?> webE = ((IElementStep) thisStep)
							.getMyWebElement();
					if (webE.getTestObjectAction() instanceof IElementAction) {
						ITestObjectAction<?> iTOA = webE.getTestObjectAction();
						if (null != iTOA
								&& ((IElementAction) iTOA).getDataValue() != null) {
							refreshDataValues.add((IStepInputData) GlobalUtils
									.getTargetObject(((IElementAction) iTOA)
											.getDataValue()));
						}
					}
				}

			}
		}

	}

	private void buildRepeatIndexes() {
		refreshIndexValues.clear();
		StepDataLogger sdl = GlobalUtils
				.findStepDataLoggerBean(getApplicationContext());
		if (null != sdl.getRepeatStepOnTheFlies().get(
				GlobalUtils.getTargetObject(this))
				&& !sdl.getRepeatStepOnTheFlies()
						.get(GlobalUtils.getTargetObject(this)).isEmpty()) {
			for (IOnTheFlyData<?> data : sdl.getRepeatStepOnTheFlies().get(
					GlobalUtils.getTargetObject(this))) {
				if (data instanceof IRepeatIncrementalIndex) {
					refreshIndexValues.add((IRepeatIncrementalIndex) data);
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@StepLoggable(level = org.bigtester.ate.annotation.ATELogLevel.INFO)
	@Override
	public void doStep() throws StepExecutionException,
			PageValidationException, RuntimeDataException {

		repeatSteps();

	}

	/**
	 * run steps.
	 * 
	 * @throws RuntimeDataException
	 * @throws StepExecutionException
	 * @throws PageValidationException
	 */
	private void repeatSteps() throws StepExecutionException,
			PageValidationException, RuntimeDataException {
		buildRepeatStepContext();
		getApplicationContext().publishEvent(
				new RepeatStepInOutEvent(this, RepeatStepInOut.IN));
		buildRepeatIndexes();
		Exception thr = null;// NOPMD
		for (int iteration = 1; iteration <= getNumberOfIterations(); iteration++) {

			setCurrentIteration(iteration);
			getApplicationContext().publishEvent(
					new RepeatDataRefreshEvent(this, getRepeatStepLogger()
							.getCurrentRepeatStepPathNodes(), iteration));

			for (int i = 0; i < getStepIndexes().size(); i++) {
				ITestStep currentTestStepTmp = getTestCase().getTestStepList()
						.get(getStepIndexes().get(i));
				if (null == currentTestStepTmp) {
					throw new IllegalStateException(
							"Test Step List was not successfully initialized by ApplicationContext at list index"
									+ i);
				} else {
					getTestCase().setCurrentTestStep(currentTestStepTmp);
				}

				if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class) {
					((RepeatStep) GlobalUtils.getTargetObject(getTestCase()
							.getCurrentTestStep()))
							.setAsserterValuesRemainSame(this
									.isAsserterValuesRemainSame());
					((RepeatStep) GlobalUtils.getTargetObject(getTestCase()
							.getCurrentTestStep()))
							.setContinueOnFailure(this.continueOnFailure);
				} else {
					currentTestStepTmp.setStepDescription(currentTestStepTmp
							.getStepDescription()
							+ " | "
							+ getRepeatStepLogger()
									.getCurrentRepeatStepFullPathString());
				}
				String tmpStepDesc = currentTestStepTmp.getStepDescription();// NOPMD
				try {
					getTestCase().getCurrentTestStep().doStep();// NOPMD
					getTestCase().getCurrentTestStep().setStepResultStatus(
							StepResultStatus.PASS);
					// } catch (BaseATECaseExecE baee) {
					//
					// if (((BaseATECaseExecE) baee).getStepIndexJumpTo() > -1)
					// { //NOPMD
					// i = ((BaseATECaseExecE) baee).getStepIndexJumpTo();
					// //NOPMD
					// } else if
					// (getTestCase().getCurrentTestStep().isOptionalStep()) {
					// getTestCase().getCurrentTestStep().setStepResultStatus(
					// StepResultStatus.SKIP);
					// if
					// (currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex()
					// > getStepIndexes().get(i)) {
					// i =
					// getStepIndexes().indexOf(currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex());//
					// NOPMD
					// if (-1 == i) {
					// baee.setStepIndexJumpTo(currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex());
					// thr = baee;//NOPMD
					// }
					// }
					// } else {
					// if (!this.continueOnFailure)
					// thr = baee;//NOPMD
					// }
				} catch (Exception e) { // NOPMD
					IATEProblem prob;
					if (e instanceof IATEProblemCreator) {// NOPMD
						prob = ((IATEProblemCreator) e).getAteProblem();
						if (prob == null) {
							prob = ((IATEProblemCreator) e)
									.initAteProblemInstance(getTestCase()
											.getCurrentTestStep());
						}
						// if (prob.getStepIndexJumpTo() > -1) { // NOPMD
						// i = prob.getStepIndexJumpTo(); // NOPMD
						// } else if (getCurrentTestStep().isOptionalStep()) {
						// getCurrentTestStep().setStepResultStatus(
						// StepResultStatus.SKIP);
						// if (currentTestStepTmp
						// .getCorrelatedOptionalStepsUtilInclusiveIndex() > i)
						// {
						// i = currentTestStepTmp
						// .getCorrelatedOptionalStepsUtilInclusiveIndex();//
						// NOPMD
						//
						// }
						// } else {
						// prob.setFatality(false);
						// throw e;
						// }
						if (!prob.isFatalProblem()
								&& prob.getStepIndexJumpTo() > -1) { // NOPMD
							i = prob.getStepIndexJumpTo(); // NOPMD
						} else if (!prob.isFatalProblem()
								&& getTestCase().getCurrentTestStep()
										.isOptionalStep()) {
							getTestCase().getCurrentTestStep()
									.setStepResultStatus(StepResultStatus.SKIP);
							if (currentTestStepTmp
									.getCorrelatedOptionalStepsUtilInclusiveIndex() > getStepIndexes()
									.get(i)) {
								i = getStepIndexes()
										.indexOf(
												currentTestStepTmp
														.getCorrelatedOptionalStepsUtilInclusiveIndex());// NOPMD
								if (-1 == i) {
									prob.setStepIndexJumpTo(currentTestStepTmp
											.getCorrelatedOptionalStepsUtilInclusiveIndex());
									thr = e;// NOPMD
								}
							}
						} else {
							if (!this.continueOnFailure)
								thr = e;// NOPMD
						}
					} else {
						// if
						// (getTestCase().getCurrentTestStep().isOptionalStep())
						// {
						// getTestCase().getCurrentTestStep().setStepResultStatus(
						// StepResultStatus.SKIP);
						// if
						// (currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex()
						// > getStepIndexes().get(i)) {
						// i =
						// getStepIndexes().indexOf(currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex());//
						// NOPMD
						// }
						// } else if
						// (getTestCase().getCurrentTestStep().isCorrectedOnTheFly())
						// {
						// getTestCase().getCurrentTestStep().setStepResultStatus(
						// StepResultStatus.PASS);
						// } else {
						thr = e;// If exception was not handled, we don't know
								// what the exception/throwable could cause in
								// the ate, so we just stop the testcase.
						// }
					}

					// ///
					// if (getTestCase().getCurrentTestStep().isOptionalStep())
					// {
					// getTestCase().getCurrentTestStep().setStepResultStatus(
					// StepResultStatus.SKIP);
					// if
					// (currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex()
					// > getStepIndexes().get(i)) {
					// i =
					// getStepIndexes().indexOf(currentTestStepTmp.getCorrelatedOptionalStepsUtilInclusiveIndex());//
					// NOPMD
					// }
					// } else if
					// (getTestCase().getCurrentTestStep().isCorrectedOnTheFly())
					// {
					// getTestCase().getCurrentTestStep().setStepResultStatus(
					// StepResultStatus.PASS);
					// } else {
					// thr = e;
					// }

				}

				if (AopUtils.getTargetClass(currentTestStepTmp) == RepeatStep.class) {
					getApplicationContext().publishEvent(
							new RepeatDataRefreshEvent(this,
									getRepeatStepLogger()
											.getCurrentRepeatStepPathNodes(),
									iteration));

				}
				if (null == tmpStepDesc)
					tmpStepDesc = ""; // NOPMD
				else
					currentTestStepTmp.setStepDescription(tmpStepDesc);

				if (getTestCase().getStepThinkTime() > 0) {
					ThinkTime thinkTimer = new ThinkTime(getTestCase()
							.getStepThinkTime());
					thinkTimer.setTimer();
				}
				if (null != thr) {
					break;
				}
			}
			if (null != thr) {
				break;
			}
		}
		getApplicationContext().publishEvent(
				new RepeatStepInOutEvent(this, RepeatStepInOut.OUT));
		if (null != thr) {
			if (thr instanceof StepExecutionException)
				throw (StepExecutionException) thr;
			else if (thr instanceof PageValidationException)
				throw (PageValidationException) thr;
			else if (thr instanceof RuntimeDataException)
				throw (RuntimeDataException) thr;
			else
				throw GlobalUtils.createInternalError("uncaught throwable", thr);
		}
	}

	/**
	 * @return the startStepID
	 */
	public String getStartStepName() {
		return startStepName;
	}

	/**
	 * @param startStepName
	 *            the startStepID to set
	 */
	public void setStartStepName(String startStepName) {
		this.startStepName = startStepName;
	}

	/**
	 * @return the endStepID
	 */
	public String getEndStepName() {
		return endStepName;
	}

	/**
	 * @param endStepName
	 *            the endStepID to set
	 */
	public void setEndStepName(String endStepName) {
		this.endStepName = endStepName;
	}

	/**
	 * @return the continueOnFailure
	 */
	public boolean isContinueOnFailure() {
		return continueOnFailure;
	}

	/**
	 * @param continueOnFailure
	 *            the continueOnFailure to set
	 */
	public void setContinueOnFailure(boolean continueOnFailure) {
		this.continueOnFailure = continueOnFailure;
	}

	/**
	 * @return the repeatTimes
	 */
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	/**
	 * @param numberOfIterations
	 *            the repeatTimes to set
	 */
	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	/**
	 * @return the stepIndexes
	 */
	public List<Integer> getStepIndexes() {
		return stepIndexes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IMyWebDriver getMyWebDriver() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public RepeatStep clone() throws CloneNotSupportedException {
		RepeatStep retVal = (RepeatStep) super.clone();
		if (null == retVal)
			throw GlobalUtils.createInternalError("jvm clone");
		return retVal;
	}

	/**
	 * @return the refreshDataValues
	 */
	public List<IStepInputData> getRefreshDataValues() {
		return refreshDataValues;
	}

	/**
	 * @return the refreshERValues
	 */
	public List<IStepERValue> getRefreshERValues() {
		return refreshERValues;
	}

	/**
	 * @return the asserterValueRemainSame
	 */
	public boolean isAsserterValuesRemainSame() {
		return asserterValuesRemainSame;
	}

	/**
	 * @param asserterValueRemainSame
	 *            the asserterValueRemainSame to set
	 */
	public void setAsserterValuesRemainSame(boolean asserterValueRemainSame) {
		this.asserterValuesRemainSame = asserterValueRemainSame;
	}

	/**
	 * @return the refreshOnTheFlyValues
	 */
	public List<IRepeatIncrementalIndex> getRefreshIndexValues() {
		return refreshIndexValues;
	}

	/**
	 * @return the repeatingSteps
	 */
	public List<ITestStep> getRepeatingSteps() {
		return repeatingSteps;
	}

	/**
	 * @return the externalRepeatNodeOfThisStep
	 */
	@Nullable
	public RepeatStepExecutionLoggerNode getExternalRepeatNodeOfThisStep() {
		return externalRepeatNodeOfThisStep;
	}

	/**
	 * @param externalRepeatNodeOfThisStep
	 *            the externalRepeatNodeOfThisStep to set
	 */
	public void setExternalRepeatNodeOfThisStep(
			RepeatStepExecutionLoggerNode externalRepeatNodeOfThisStep) {
		this.externalRepeatNodeOfThisStep = externalRepeatNodeOfThisStep;
	}

	/**
	 * @return the currentRepeatNodeOfThisStep
	 */
	@Nullable
	public RepeatStepExecutionLoggerNode getCurrentRepeatNodeOfThisStep() {
		return currentRepeatNodeOfThisStep;
	}

	/**
	 * @param currentRepeatNodeOfThisStep
	 *            the currentRepeatNodeOfThisStep to set
	 */

	public void setCurrentRepeatNodeOfThisStep(
			RepeatStepExecutionLoggerNode currentRepeatNodeOfThisStep) {
		this.currentRepeatNodeOfThisStep = currentRepeatNodeOfThisStep;
	}

}
