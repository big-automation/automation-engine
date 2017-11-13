package org.bigtester.ate.test;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Tests for selenium standalone server.
 * @author parsentev
 * @since 19.11.2015
 */
public class SeleniumStandaloneServerTest {

	@Test
	public void executeFirefoxDriver() throws MalformedURLException {
		this.execute(DesiredCapabilities.firefox());
	}

	@Test
	public void executeChrome() throws MalformedURLException {
		this.execute(DesiredCapabilities.chrome());
	}

	private void execute(final DesiredCapabilities capability) throws MalformedURLException {
		WebDriver driver = new RemoteWebDriver(
				new URL("http://192.168.0.105:4444/wd/hub"), capability
		);
		driver.get("http://www.javacodegeeks.com/");
		WebElement element = driver.findElement(By.name("s"));
		element.sendKeys("selenuim");
		element.submit();
		assertThat(
				driver.getTitle(),
				is("You searched for selenuim | Java Code Geeks")
		);
		driver.quit();
	}
}