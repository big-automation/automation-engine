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

import java.util.List;

import org.bigtester.ate.constant.ExceptionErrorCode;
import org.bigtester.ate.constant.ExceptionMessage;
import org.bigtester.ate.model.page.exception.PageValidationException;
import org.bigtester.ate.model.page.page.MyWebElement;
import org.openqa.selenium.NoSuchElementException;

// TODO: Auto-generated Javadoc
/**
 * This class PageElementExistenceAsserter defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class PageElementExistenceAsserter extends AbstractExpectedResultAsserter implements IExpectedResultAsserter {
	
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean assertER() throws PageValidationException {
		
		boolean retVal = false; // NOPMD
		List<MyWebElement> myWebElementList = getResultPage().getMyWebElementList();
		if (!myWebElementList.isEmpty()) {
			MyWebElement webelement;
			for (int index = 0; index < myWebElementList.size(); index++) {
				webelement = myWebElementList.get(index);
				try {
					webelement.getElementFind().doFind(getResultPage().getMyWd(),
							webelement.getElementFind().getFindByValue());
				} catch (NoSuchElementException e) {
					PageValidationException pve = new PageValidationException(
							ExceptionMessage.MSG_WEBELEMENT_NOTFOUND,
							ExceptionErrorCode.WEBELEMENT_NOTFOUND,
							webelement.getElementFind());
					pve.initCause(e);
					throw pve;
				} 
			}
			retVal = true;
		}
		return retVal;
	}

}
