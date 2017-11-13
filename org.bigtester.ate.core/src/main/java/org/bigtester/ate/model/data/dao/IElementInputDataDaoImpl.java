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
package org.bigtester.ate.model.data.dao;

import java.util.List;

import org.bigtester.ate.model.data.dbtable.ElementInputData;
import org.bigtester.ate.model.data.exception.RepeatTestDataException;
import org.bigtester.ate.model.data.exception.TestDataException;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class IElementInputDataDaoImpl defines ....
 * @author Peidong Hu
 *
 */
public interface IElementInputDataDaoImpl {
	/**
	 * Save.
	 *
	 * @param eid
	 *            the eid
	 * @return the long
	 */
	String save(ElementInputData eid) ;

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	@Nullable List<ElementInputData> getAll() ;

	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	String getValue(String inputDataID) throws TestDataException ;

	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	String getValue(String inputDataID, String repeatStepName,
			String repeatStepExternalLoopPath, int iteration)
			throws RepeatTestDataException;
	
	/**
	 * Gets the value.
	 *
	 * @param inputDataID
	 *            the input data id
	 * @return the value
	 */
	String getValue(String inputDataID, String repeatStepName,
			int iteration)
			throws RepeatTestDataException;
}
