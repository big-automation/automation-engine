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
package org.bigtester.ate.model.casestep;

import java.util.ArrayList;
import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.asserter.IExpectedResultAsserter;
import org.bigtester.ate.model.data.ICaseServiceParsedDataParser;
import org.bigtester.ate.model.data.IDataParser;
import org.bigtester.ate.model.data.IStepInputData;
import org.bigtester.ate.model.data.exception.RuntimeDataException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.model.page.exception.PageValidationException2;
import org.bigtester.ate.model.page.exception.StepExecutionException2;
import org.bigtester.ate.model.page.page.IPageObject;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

// TODO: Auto-generated Javadoc
/**
 * This class CaseTypeService defines ....
 * 
 * @author Peidong Hu
 *
 */
public class CaseTypeService extends TestCase implements ITestStep { // NOPMD

	/** The test case file name. */
	final private String testCaseFileName;

	/** The parent test case. */
	// TODO current version needs user to manually set the parentTestCase in xml
	// file
	final private TestCase parentTestCase;

	/** The data holders. */
	private List<IDataParser> dataHolders = new ArrayList<IDataParser>();

	/**
	 * @param testCaseName
	 */
	public CaseTypeService(String testCaseName, String testCaseFileName,
			TestCase parentTestCase) {
		super(testCaseName);
		this.testCaseFileName = testCaseFileName;
		this.parentTestCase = parentTestCase;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isTargetStep() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isPageValidation() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public IPageObject getPageObject() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IMyWebDriver getMyWebDriver() {
		return super.getCurrentWebDriver();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getStepName() {
		return getTestCaseName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public MyWebElement getMyWebElement() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Nullable
	public String getStepDescription() {
		return getTestCaseName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isElementStepFlag() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void doStep() throws StepExecutionException2,
			PageValidationException2, RuntimeDataException {
		String testCaseFileName = getTestCaseFileName();
		WebDriver mainDriver;
		ApplicationContext context;
		context = new FileSystemXmlApplicationContext(testCaseFileName);
		IMyWebDriver myWebD = (IMyWebDriver) GlobalUtils
				.findMyWebDriver(context);
		mainDriver = myWebD.createDriver();
		try {
			super.setCurrentWebDriver(myWebD);
			setStepThinkTime(getParentTestCase().getStepThinkTime());
			/* remove all the asserters */
			TestCase newTestcase = (TestCase) GlobalUtils
					.findTestCaseBean(context);
			newTestcase.cleanUpAsserters();
			newTestcase.goSteps();

			/* transfer all the dataholders value to parent test case */
			for (int j = 0; j < getDataHolders().size(); j++) {
				IDataParser parentDataHolder = getDataHolders().get(j);
				if (parentDataHolder instanceof ICaseServiceParsedDataParser) {
					ICaseServiceParsedDataParser parentDataHolderTemp = (ICaseServiceParsedDataParser) parentDataHolder;
					parentDataHolderTemp
							.setStrDataValue(((IStepInputData) context
									.getBean(parentDataHolderTemp
											.getSubCaseMappedDataHolderID()))
									.getStrDataValue());
				}
			}

		} catch (Throwable t) { // NOPMD
			mainDriver.quit();
			throw t;
		}

	}

	/**
	 * @return the testCaseFileName
	 */
	public String getTestCaseFileName() {
		return testCaseFileName;
	}

	/**
	 * @return the parentTestCase
	 */
	public TestCase getParentTestCase() {
		return parentTestCase;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IExpectedResultAsserter> getExpectedResultAsserter() {

		return new ArrayList<IExpectedResultAsserter>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IDataParser> getDataHolders() {

		return dataHolders;
	}

	/**
	 * @param dataHolders
	 *            the dataHolders to set
	 */
	public void setDataHolders(List<IDataParser> dataHolders) {
		this.dataHolders = dataHolders;
	}

}
