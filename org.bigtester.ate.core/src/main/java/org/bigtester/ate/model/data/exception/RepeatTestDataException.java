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
package org.bigtester.ate.model.data.exception;

// TODO: Auto-generated Javadoc
/**
 * This class StepExecutionException defines ....
 * 
 * @author Peidong Hu
 * 
 */
public class RepeatTestDataException extends RuntimeDataException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8009838033578209075L;

	/** The repeat step name. */
	final private String repeatStepName;
	
	/** The external repeat loop path. */
	final private String externalRepeatLoopPath;
	
	/** The iteration. */
	final private int iteration;
	/**
	 * Instantiates a new step execution exception.
	 *
	 * @param message            the message
	 * @param errorCode            the error code
	 */
	public RepeatTestDataException(String message, String errorCode, String repeatStepName, String externalRepeatLoopPath, int iteration) {
		super(message, errorCode);
		this.repeatStepName = repeatStepName;
		this.iteration = iteration;
		this.externalRepeatLoopPath = externalRepeatLoopPath;
		setMessage(getMessage() + ":repeatStepName:" + repeatStepName + ":externalRepeatLoopPath"+externalRepeatLoopPath+":iteration:" + iteration);
		
	}
	/**
	 * @return the repeatStepName
	 */
	public String getRepeatStepName() {
		return repeatStepName;
	}
	/**
	 * @return the iteration
	 */
	public int getIteration() {
		return iteration;
	}
	/**
	 * @return the externalRepeatLoopPath
	 */
	public String getExternalRepeatLoopPath() {
		return externalRepeatLoopPath;
	}
}
