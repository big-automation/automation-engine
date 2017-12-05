package org.bigtester.ate.model.cucumber;

public class CucumberFilter {

	/** The suite name. */
	final private String suiteName;

	/** The case name. */
	final private String caseName;

	/** The step name. */
	final private String stepName;

	/**
	 * Instantiates a new ate project filter.
	 * @param suiteName : the suite name
	 * @param caseName  : the case name
	 * @param stepName  : the step name
	 */
	public CucumberFilter(String suiteName, String caseName, String stepName) {
		this.suiteName = suiteName;
		this.caseName = caseName;
		this.stepName = stepName;
	}

	/**
	 * @return the suiteName
	 */
	public String getSuiteName() {
		return suiteName;
	}

	/**
	 * @return the caseName
	 */
	public String getCaseName() {
		return caseName;
	}

	/**
	 * @return the stepName
	 */
	public String getStepName() {
		return stepName;
	}
	
}
