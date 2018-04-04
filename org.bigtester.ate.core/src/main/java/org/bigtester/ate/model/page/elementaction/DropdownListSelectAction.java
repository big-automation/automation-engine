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
package org.bigtester.ate.model.page.elementaction;


import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

// TODO: Auto-generated Javadoc
/**
 * The Class SendKeysAction defines ....
 * 
 * @author Peidong Hu
 */
public class DropdownListSelectAction extends BaseElementAction implements
		IElementAction, ITestObjectActionImpl {
	
	/**
	 * The Enum SELECTIONTYPE.
	 *
	 * @author Peidong Hu
	 */
	public enum SelectionType {
		INDEX, VISIBLE_TEXT, VALUE
	}

	/** The selection type. */
	final private SelectionType selectionType;
	
	
	/** The selections. */
	final private String selections;

	
	/**
	 * Instantiates a new assign value action.
	 *
	 * @param myWd
	 *            the my wd
	 * @param dataValue
	 *            the data value
	 */
	public DropdownListSelectAction(IMyWebDriver myWd, String selections, SelectionType selectionType) {
		super(myWd);
		this.selections = selections;
		this.selectionType = selectionType;
	}
	
	/**
	 * Instantiates a new dropdown list select action.
	 *
	 * @param selections the selections
	 */
	public DropdownListSelectAction(String selections, SelectionType selectionType) {
		super();
		this.selections = selections;
		this.selectionType = selectionType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable (level=ATELogLevel.INFO)
	public void doAction(final WebElement webElm) {
		String multipleSel[] = selections.split(",");

		   for (String valueToBeSelected : multipleSel) {
			   if (this.selectionType.equals(SelectionType.INDEX)) {
				   new Select(webElm).selectByIndex(Integer.valueOf(valueToBeSelected));
			   } else if (this.selectionType.equals(SelectionType.VALUE)){
				   new Select(webElm).selectByValue(valueToBeSelected);
			   } else {
				   new Select(webElm).selectByVisibleText(valueToBeSelected);
			   }
			   if (multipleSel.length > 1) //NOPMD
				   webElm.sendKeys(Keys.CONTROL);

		}


	}

	
	/**
	 * @return the selections
	 */
	public String getSelections() {
		return selections;
	}

	/**
	 * @return the selectionType
	 */
	public SelectionType getSelectionType() {
		return selectionType;
	}

}
