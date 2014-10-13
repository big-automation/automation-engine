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
package org.bigtester.ate.systemlogger.problemhandler;

import java.util.Properties;

import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.constant.LogbackTag;
import org.bigtester.ate.model.casestep.ITestStep;
import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.systemlogger.LogbackWriter;
import org.bigtester.ate.systemlogger.problems.IATECaseExecProblem;
import org.bigtester.problomatic2.InitException;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.ProblemHandler;
import org.bigtester.problomatic2.handlers.AbstractProblemHandler;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionProblemHandler defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class ProblemLogbackHandler extends AbstractProblemHandler implements
		ProblemHandler {

	/** The Constant slf4jLogger. */

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void handleProblem(Problem aProblem) {

		//TODO add code to handle the other problems
		//problems are clasified as the following categories,
		//1) target Step element not found: test error; (failed test)
		//2) non-target Step exeception or error: test dependency error; (test dependency error)
		//3) page validation error : test error; (failed test)
		//4) page validation exception: test pass with bug; (passed test with bug)
		TestCase pTC;
		ITestStep pTS;
		String logMsg;
		if (aProblem instanceof IATECaseExecProblem) {
			IATECaseExecProblem caseExecProblem = (IATECaseExecProblem) aProblem;
			switch (caseExecProblem.getErrorCode()) {
			case ExceptionErrorCode.WEBELEMENT_NOTFOUND:
				pTC = caseExecProblem.getCurrentTestCase();
				pTS = caseExecProblem.getCurrentTestStep();
				logMsg = pTC.getTestCaseName() + LogbackTag.TAG_SEPERATOR
						+ pTS.getStepName()	+ LogbackTag.TAG_SEPERATOR
						+ pTS.getStepDescription()	+ LogbackTag.TAG_SEPERATOR
						+ caseExecProblem.getProblemMessage();
				if (pTS.isTargetStep()) {
					LogbackWriter.writeAppError(logMsg);
				} else {
					LogbackWriter.writeAppWarning(logMsg);
				}
				break;
			case ExceptionErrorCode.UNKNOWN_ERROR:
				LogbackWriter.writeAppError(ExceptionMessage.MSG_UNCAUGHT_APP_ERRORS);
				break;
			default:
				LogbackWriter
						.writeAppInfo("//TODO problem default handling msg.");
				break;
			}
		} else {
			LogbackWriter.writeAppInfo(ExceptionMessage.MSG_UNCAUGHT_APP_ERRORS + LogbackTag.TAG_SEPERATOR
										+ aProblem.getSource().toString() + aProblem.getMessages().toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(Properties properties) throws InitException {
		// TODO Auto-generated method stub

	}

}
