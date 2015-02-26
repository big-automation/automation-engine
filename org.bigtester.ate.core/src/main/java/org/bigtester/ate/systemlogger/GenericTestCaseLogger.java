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
package org.bigtester.ate.systemlogger;

import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.AbstractATECaseExecE;
import org.bigtester.ate.model.AbstractATEException;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.systemlogger.problemhandler.ProblemBrowserHandler;
import org.bigtester.ate.systemlogger.problemhandler.ProblemLogbackHandler;
import org.bigtester.ate.systemlogger.problems.ATEProblemFactory;
import org.bigtester.ate.systemlogger.problems.GenericATEProblem;
import org.bigtester.ate.systemlogger.problems.IATEProblemFactory;
import org.bigtester.problomatic2.Problem;
import org.bigtester.problomatic2.Problomatic;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

// TODO: Auto-generated Javadoc
/**
 * This class GenericTestCaseLogger will handle errors happened inside of a
 * specific test case execution
 * 
 * @author Peidong Hu
 * 
 */
@Aspect
public class GenericTestCaseLogger implements ApplicationContextAware {

	/** The app context. */
	@Nullable
	private transient ApplicationContext appContext;

	/**
	 * @return the appContext
	 */
	public ApplicationContext getAppContext() {
		final ApplicationContext appContext2 = appContext;
		if (null == appContext2) {
			throw GlobalUtils.createNotInitializedException("appContext");
		} else {
			return appContext2;
		}
	}

	/**
	 * Select all method as pointcuts.
	 */
	@Pointcut("within(org.bigtester.ate..*)")
	private void selectAll() { // NOPMD
	}

	private boolean isAlreadyCasePointCut(Throwable error) {
		boolean retVal = false; // NOPMD
		for (int i = 0; i < error.getSuppressed().length; i++) {
			if (error.getSuppressed()[i].getMessage().equalsIgnoreCase(
					ExceptionMessage.MSG_ALREADY_CASEPOINTCUT)) {
				retVal = true; // NOPMD
			}
		}
		return retVal;
	}

	/**
	 * Sets the already case point cut.
	 *
	 * @param error
	 *            the new already case point cut
	 */
	private void setAlreadyCasePointCut(Throwable error) {
		Throwable flagT = new Exception(
				ExceptionMessage.MSG_ALREADY_CASEPOINTCUT);
		error.addSuppressed(flagT);
	}

	/**
	 * After throwing advice.
	 * 
	 * @param joinPoint
	 *            the join point
	 * @param error
	 *            the error
	 * @throws InstantiationException
	 *             the instantiation exception
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 */
	@AfterThrowing(pointcut = "selectAll()", throwing = "error")
	public void afterThrowingAdvice(JoinPoint joinPoint, Throwable error) {
		if (isAlreadyCasePointCut(error)) {
			return;
		}

		IMyWebDriver myWebDriver;
		Problem prb;
		ProblemBrowserHandler pbh;

		setAlreadyCasePointCut(error);

		if (error instanceof AbstractATEException
				&& error instanceof AbstractATECaseExecE) {

			Object obj = joinPoint.getTarget();
			if (obj == null)
				throw GlobalUtils.createInternalError("GenericTestCaseLogger");
			{
				IATEProblemFactory ipf = ATEProblemFactory.getInstance();
				prb = ipf.getATEProblem(obj, (AbstractATECaseExecE) error);
				
			}

//			myWebDriver = ((AbstractATECaseExecE) error).getMyWebDriver();
//			pbh = new ProblemBrowserHandler(myWebDriver);
//			Problomatic.addProblemHandlerForProblem(prb, pbh);
		} else {
			Object obj = joinPoint.getTarget();
			if (obj == null)
				throw GlobalUtils.createInternalError("GenericTestCaseLogger");
			prb = new GenericATEProblem(obj, error); // NOPMD
//			try {
//				Map<String, IMyWebDriver> myWebDrivers = getAppContext()
//						.getBeansOfType(IMyWebDriver.class);
//				for (IMyWebDriver myWebDriver2 : myWebDrivers.values()) {
//					if (null == myWebDriver2)
//						throw GlobalUtils
//								.createInternalError("application context my web driver");
//					else {
//						pbh = new ProblemBrowserHandler(myWebDriver2);
//						Problomatic.addProblemHandlerForProblem(prb, pbh);
//					}
//				}
//			} catch (Exception ext) { // NOPMD
//				// if webdriver can't be successfully accessed, do nothing
//			}
		}
		ProblemLogbackHandler plbh = new ProblemLogbackHandler();

		Problomatic.addProblemHandlerForProblem(prb, plbh);
		Problomatic.handleProblem(prb);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext(@Nullable ApplicationContext arg0)
			throws BeansException {
		this.appContext = arg0;

	}

}
