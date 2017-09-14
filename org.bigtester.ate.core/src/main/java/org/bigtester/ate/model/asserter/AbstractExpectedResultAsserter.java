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
package org.bigtester.ate.model.asserter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ReportMessage;
import org.bigtester.ate.model.casestep.RepeatDataRefreshEvent;
import org.bigtester.ate.model.data.ItemCompareResult;
import org.bigtester.ate.model.page.page.IPageObject;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

// TODO: Auto-generated Javadoc
/**
 * This class AbstractExpectedResultAsserter defines ....
 * 
 * @author Peidong Hu
 *
 */
public abstract class AbstractExpectedResultAsserter implements	ApplicationContextAware,ApplicationListener<RepeatDataRefreshEvent>{ // NOPMD
	/** The result page. */
	@Nullable
	
	private IPageObject resultPage;
	
	/**
	 * Compared item results.
	 *
	 * @return the map
	 */
	final protected Map<Long, ItemCompareResult> comparedItemResults = new ConcurrentHashMap<Long, ItemCompareResult>();//NOPMD
	
	
	/**
	 * Failed item results.
	 *
	 * @return the map
	 */
	final protected Map<Long, ItemCompareResult> failedItemResults = new ConcurrentHashMap<Long, ItemCompareResult>(); //NOPMD
	
	/** The Flag fail case. */
	private boolean flagFailCase;

	/** The exec result. 
	 * NOTE: desperated
	 * */
	@Nullable
	protected IStepExecutionResult execResult;

	/** The application context. */
	@Nullable
	@XStreamOmitField
	private ApplicationContext applicationContext;

	/** The Constant EXIST. */
	public final static String CORRECT = "Correct";

	/** The Constant NOTEXIST. */
	public final static String NOTCORRECT = "NotCorrect";

	/** The Constant EXIST. */
	public final static String EXIST = "Exist";

	/** The Constant NOTEXIST. */
	public final static String NOTEXIST = "NotExist";

	/** The assert report msg. */
	protected String assertReportMSG = "";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent(@Nullable RepeatDataRefreshEvent arg0) {
		if (null == arg0) return;
		getComparedItemResults().clear();
		
		
		getFailedItemResults().clear();
		
		setFlagFailCase(false);

		assertReportMSG = "";
	}
	
	/**
	 * @return the assertReportMSG
	 */
	public String getAssertReportMSG() {
		return assertReportMSG;
	}

	
	/**
	 * Gets the result page.
	 *
	 * @return the resultPage
	 */

	public IPageObject getResultPage() throws IllegalStateException{
		final IPageObject resultPage2 = resultPage;
		if (null == resultPage2) {
			throw new IllegalStateException ("Page Object is not correct initialized in asserter");
			
		} else {
			return resultPage2;
		}

	}

	/**
	 * Sets the result page.
	 *
	 * @param resultPage
	 *            the resultPage to set
	 */
	public void setResultPage(final IPageObject resultPage) {
		this.resultPage = resultPage;
	}


	/**
	 * @return the execResult
	 */
	public IStepExecutionResult getExecResult() {
		final IStepExecutionResult execResult2 = execResult;
		if (null == execResult2) {
			throw GlobalUtils.createNotInitializedException("execResult");
		} else {
			return execResult2;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(
			@Nullable ApplicationContext applicationContext)
			throws BeansException {
		if (null == applicationContext) {
			throw new NoSuchBeanDefinitionException(ApplicationContext.class);
		} else {
			this.applicationContext = applicationContext;
		}

	}

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() throws IllegalStateException{
		final ApplicationContext applicationContext2 = applicationContext;
		if (null == applicationContext2) {
			throw new IllegalStateException("Couldn't get test case application context in asserter.");
		} else {
			return applicationContext2;
		}
	}

	/**
	 * @param execResult the execResult to set
	 */
	public void setExecResult(IStepExecutionResult execResult) {
		this.execResult = execResult;
	}


	/**
	 * @return the comparedItemResults
	 */
	public Map<Long, ItemCompareResult> getComparedItemResults() {
		return comparedItemResults;
	}


	/**
	 * @return the failedItemResults
	 */
	public Map<Long, ItemCompareResult> getFailedItemResults() {
		return failedItemResults;
	}


	/**
	 * @return the flagFailCase
	 */
	public boolean isFlagFailCase() {
		return flagFailCase;
	}


	/**
	 * @param flagFailCase the flagFailCase to set
	 */
	public void setFlagFailCase(boolean flagFailCase) {
		this.flagFailCase = flagFailCase;
	}
	

	/**
	 * Append assert report msg.
	 *
	 * @param icr the icr
	 */
	protected void appendAssertReportMSG(ItemCompareResult icr) {
		assertReportMSG += icr.getCompareItem();
		assertReportMSG += ReportMessage.MSG_SEPERATOR
				+ icr.getExpectedValue();
		assertReportMSG += ReportMessage.MSG_SEPERATOR
				+ icr.getActualValue();
		assertReportMSG += ReportMessage.MSG_SEPERATOR
				+ icr.getAssertPriority();
		assertReportMSG += ReportMessage.MSG_SEPERATOR
				+ ReportMessage.MSG_SEPERATOR
				+ icr.getComparedResult();
		assertReportMSG += "</br>";
	}

	/**
	 * @param assertReportMSG the assertReportMSG to set
	 */
	public void setAssertReportMSG(String assertReportMSG) {
		this.assertReportMSG = assertReportMSG;
	}
}
