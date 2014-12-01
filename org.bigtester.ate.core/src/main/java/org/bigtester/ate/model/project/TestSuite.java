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
package org.bigtester.ate.model.project;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class TestSuite defines ....
 * 
 * @author Peidong Hu
 */
public class TestSuite {

	/** The test case list. */
	private List<TestCase> testCaseList;

	/** The suite name. */
	private String suiteName;

	/**
	 * Gets the test case list.
	 * 
	 * @return the testCaseList
	 */
	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}

	/**
	 * Sets the test case list.
	 * 
	 * @param testCaseList
	 *            the testCaseList to set
	 */
	public void setTestCaseList(final List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

	/**
	 * Gets the suite name.
	 * 
	 * @return the suite name
	 */
	public String getSuiteName() {
		return suiteName;
	}

	/**
	 * Sets the suite name.
	 * 
	 * @param suiteName
	 *            the new suite name
	 */
	public void setSuiteName(final String suiteName) {
		this.suiteName = suiteName;
	}

	
}
