/**
 *
 */
package org.bigtester.ate.model.page.atewebdriver;

import org.openqa.selenium.WebDriver;

/**
 * @author peidong
 *
 */
public interface IMyWebDriver2 extends IMyWebDriver {
	
	/**
	 * Gets the web driver instance.
	 *
	 * @param forceToNew the force to new
	 * @return the web driver instance
	 */
	WebDriver getWebDriverInstance(boolean forceToNew);
}
