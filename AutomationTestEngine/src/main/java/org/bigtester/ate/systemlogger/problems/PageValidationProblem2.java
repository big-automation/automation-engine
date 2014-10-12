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
package org.bigtester.ate.systemlogger.problems;


import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.exception.PageValidationException2;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionProblem defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class PageValidationProblem2 extends GenericATEProblem {

	/** The test data exception. */
	private final transient PageValidationException2 pageValException;

	/**
	 * Instantiates a new page validation problem.
	 * 
	 * @param source
	 *            the source
	 * @param tde
	 *            the see
	 */
	public PageValidationProblem2(Object source, PageValidationException2 pageValException) {
		super(source, pageValException);
		this.pageValException = pageValException;
	}

	/**
	 * Gets the step exec exception.
	 * 
	 * @return the step exec exception
	 */
	public PageValidationException2 getStepExecException() {
		return pageValException;
	}

}
