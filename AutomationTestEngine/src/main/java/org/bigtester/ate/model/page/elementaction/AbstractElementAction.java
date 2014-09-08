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


import org.bigtester.ate.model.data.StepDataValue;
import org.bigtester.ate.model.page.AbstractPageModelBase;
import org.openqa.selenium.WebElement;


// TODO: Auto-generated Javadoc
/**
 * The Class _ElementAction defines ....
 * 
 * @author Peidong Hu
 */
public abstract class AbstractElementAction extends AbstractPageModelBase{
	
	
	/** The data valued action flag. */
	private transient boolean dataValuedActionFlag; 
	
	/** The data value. */
	//private String dataValue;
	
	private StepDataValue dataValue;
	
	/**
	 * Gets the data value.
	 * 
	 * @return the data value
	 */
	public StepDataValue getDataValue() {
		return dataValue;
	}

	/**
	 * Sets the data value.
	 * 
	 * @param dataValue
	 *            the new data value
	 */
	public void setDataValue(final StepDataValue dataValue) {
		this.dataValuedActionFlag = true;
		this.dataValue = dataValue;
	}

	
	/**
	 * Do action.
	 *
	 * @param webElm the web elm
	 */
	public abstract void doAction(WebElement webElm);

	/**
	 * Checks if is data value action flag.
	 *
	 * @return the dataValueFlag
	 */
	public boolean isDataValuedActionFlag() {
		return dataValuedActionFlag;
	}

	
	
}
