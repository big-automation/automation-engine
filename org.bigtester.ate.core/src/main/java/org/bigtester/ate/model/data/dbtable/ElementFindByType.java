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
package org.bigtester.ate.model.data.dbtable;

import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class ElementFindByType defines ....
 * 
 * @author Peidong Hu
 * 
 */
//@Entity
///@Table
public class ElementFindByType extends BaseDBTable {

	/** The Find type. */
	@Nullable
	private String FindType; //NOPMD

	/**
	 * Gets the find type.
	 *
	 * @return the findType
	 */
	public String getFindType() {
		final String findType2 = FindType;
		if (null == findType2 ) {
			throw new IllegalStateException("FindType is not correctly populated in DB xml file.");
		} else {
			return findType2;
		}
	}

	/**
	 * Sets the find type.
	 *
	 * @param findType the findType to set
	 */
	public void setFindType(String findType) {
		
		FindType = findType;
	}
}
