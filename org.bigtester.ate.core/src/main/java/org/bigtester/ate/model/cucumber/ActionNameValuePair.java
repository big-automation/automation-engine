/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2017, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.model.cucumber;

/**
 * This class ActionNameValuePair defines ....
 * @author Peidong Hu
 *
 */
public class ActionNameValuePair {
	
	/** The action name. */
	private final String actionName;
	
	/** The value. */
	private final String value;
	
	/**
	 * Instantiates a new action name value pair.
	 *
	 * @param actionName the action name
	 * @param value the value
	 */
	public ActionNameValuePair(String actionName, String value) {
		this.actionName = actionName;
		this.value = value;
	}
	
	/**
	 * @return the actionName
	 */
	public String getActionName() {
		return actionName;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
}