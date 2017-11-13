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
	WebDriver getWebDriverInstance(boolean forceToNew);
}
