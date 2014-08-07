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
package org.bigtester.ate.constant;

// TODO: Auto-generated Javadoc
/**
 * This class ExceptionMessage defines ....
 * @author Peidong Hu
 *
 */
public final class ExceptionMessage {
	
	
	/** The Constant MSG_SEPERATOR. */
	public static final String MSG_SEPERATOR =" ==> "; 
	/** The Constant MSG_WEBELEMENT_NOTFOUND. */
	public static final String MSG_WEBELEMENT_NOTFOUND = "Web Element not Found.";
	
	/** The Constant MSG_UNSUPPORTED_LOGBACK_LEVEL. */
	public static final String MSG_UNSUPPORTED_LOGBACK_LEVEL = "Unsupported logback error level.";
	
	/**
	 * Instantiates a new exception error code.
	 */
	private ExceptionMessage(){
	    throw new AssertionError();
	  }
}