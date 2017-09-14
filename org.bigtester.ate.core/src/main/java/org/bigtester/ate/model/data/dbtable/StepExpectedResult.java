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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.bigtester.ate.constant.EnumAssertPriority;
import org.bigtester.ate.constant.EnumElementFindType;
import org.eclipse.jdt.annotation.Nullable;

// TODO: Auto-generated Javadoc
/**
 * This class StepResult defines ....
 * This is a desperate dbtable
 * @author Peidong Hu
 *
 */
@Entity
@Table
public class StepExpectedResult extends BaseTestDataTable {

	/** The steper set id. */	
	@Column
	@Nullable
	private String stepERSetID; // NOPMD
	
	/**
	 * @return the stepERSetID
	 */
	public String getStepERSetID() {
		final String retVal = stepERSetID;
		if (null == retVal) {
			throw new IllegalStateException("stepERSetID collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param stepERSetID the stepERSetID to set
	 */
	public void setStepERSetID(String stepERSetID) {
		this.stepERSetID = stepERSetID;
	}

	/**
	 * @return the elementFindBy
	 */
	public EnumElementFindType getElementFindBy() {
		final EnumElementFindType retVal = ElementFindBy;
		if (null == retVal) {
			throw new IllegalStateException("elementfindbytype collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param elementFindBy the elementFindBy to set
	 */
	public void setElementFindBy(EnumElementFindType elementFindBy) {
		ElementFindBy = elementFindBy;
	}

	/**
	 * @return the assertPriority
	 */
	public EnumAssertPriority getAssertPriority() {
		final EnumAssertPriority retVal = assertPriority;
		if (null == retVal) {
			throw new IllegalStateException("assertpriority collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param assertPriority the assertPriority to set
	 */
	public void setAssertPriority(EnumAssertPriority assertPriority) {
		this.assertPriority = assertPriority;
	}

	/**
	 * @return the assertProperty
	 */
	public String getAssertProperty() {
		final String retVal = AssertProperty;
		if (null == retVal) {
			throw new IllegalStateException("asertproperty collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param assertProperty the assertProperty to set
	 */
	public void setAssertProperty(String assertProperty) {
		AssertProperty = assertProperty;
	}

	/**
	 * @return the assertValue
	 */
	public String getAssertValue() {
		final String retVal = AssertValue;
		if (null == retVal) {
			throw new IllegalStateException("assertvalue collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param assertValue the assertValue to set
	 */
	public void setAssertValue(String assertValue) {
		AssertValue = assertValue;
	}

	/**
	 * @return the pageName
	 */
	public String getPageName() {
		final String retVal = pageName;
		if (null == retVal) {
			throw new IllegalStateException("pagename collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param pageName the pageName to set
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	// @ManyToOne
	// @JoinColumn(name = "elementFindByType_idColumn", referencedColumnName =
	// "idColumn")
	@Enumerated(EnumType.STRING)
	@Column
	@Nullable
	private EnumElementFindType ElementFindBy; // NOPMD

	/** The assert priority. */
	
	@Enumerated(EnumType.STRING)
	@Column
	@Nullable
	private EnumAssertPriority assertPriority; // NOPMD

	@Column
	/** The Element find by value. */
	@Nullable
	private String ElementFindByValue; // NOPMD

	/**
	 * @return the elementFindByValue
	 */
	public String getElementFindByValue() {
		final String retVal = ElementFindByValue;
		if (null == retVal) {
			throw new IllegalStateException("elementfindbyvalue collumn is not correctly populated");
			
		} else {
			return retVal;
		}
	}

	/**
	 * @param elementFindByValue
	 *            the elementFindByValue to set
	 */
	public void setElementFindByValue(String elementFindByValue) {
		ElementFindByValue = elementFindByValue;
	}

	@Column
	/** The Assert property. */
	@Nullable
	private String AssertProperty; // NOPMD

	@Column
	/** The Assert value. */
	@Nullable
	private String AssertValue; // NOPMD

	@Column
	@Nullable
	private String pageName; // NOPMD

}
