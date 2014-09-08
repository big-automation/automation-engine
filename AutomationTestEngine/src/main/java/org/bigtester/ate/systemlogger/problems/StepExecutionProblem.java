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

import org.bigtester.ate.model.page.exception.StepExecutionException;
import org.bigtester.ate.model.casestep.TestCase;


// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionProblem defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class StepExecutionProblem extends GenericATEProblem {
	
	
	
	/** The problem test case. */
	private final TestCase problemTestCase;	
	/** The step exec exception. */
	private final StepExecutionException stepExecException;
	
	/**
	 * Instantiates a new page validation problem.
	 *
	 * @param source the source
	 * @param see the see
	 * @param pTc the tc
	 */
	public StepExecutionProblem(Object source, StepExecutionException see, TestCase pTc) {
		super(source, see);
		stepExecException = see;
		problemTestCase = pTc;
	}
	
	

	/**
	 * Gets the step exec exception.
	 *
	 * @return the stepExecException
	 */
	public StepExecutionException getStepExecException() {
		return stepExecException;
	}

	/**
	 * Gets the problem test case.
	 *
	 * @return the problemTestCase
	 */
	public TestCase getProblemTestCase() {
		return problemTestCase;
	}
	

}
