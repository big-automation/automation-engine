package org.bigtester.ate.model.page.atewebdriver;


import org.openqa.selenium.Platform;


// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MySauceLabDriver extends MyRemoteDriver implements IMyWebDriver {

	/** The caps. */
	private String userName;
	
	/** The url. */
	private String accesskey;
	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	
	
	@SuppressWarnings("null")
	public MySauceLabDriver(String userName, String accesskey) {
		
		super("chrome", "43", Platform.ANY.toString(), "https://" + userName + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		this.setUserName(userName);
		this.setAccesskey(accesskey);
	}
	
	@SuppressWarnings("null")
	public MySauceLabDriver( String browserName, String version, String userName, String accesskey) {
		
		super(browserName, version, Platform.ANY.toString(), "https://" + userName + ":" + accesskey + "@ondemand.saucelabs.com:443/wd/hub");
		
		this.setUserName(userName);
		this.setAccesskey(accesskey);
	}
		
	/**
	 * Instantiates a new my Chrome driver.
	 */
	@SuppressWarnings("null")
	public MySauceLabDriver() {
		
		super();
		 
		 
	}
	
		

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the accesskey
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * @param accesskey the accesskey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	
}

