/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2018, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.model.project;

// TODO: Auto-generated Javadoc
/**
 * This class Mailer defines ....
 * @author Peidong Hu
 *
 */
public class Mailer {
	
	/** The from mail address. */
	private String fromMailAddress;
	
	/** The to mail address. */
	private String toMailAddress;
	
	/** The smtp mail server host. */
	private String smtpMailServerHost;
	
	/** The smtp mail server port. */
	private int smtpMailServerPort;
	
	/** The smtp mail user name. */
	private String smtpMailUserName;
	
	/** The smtp mail user password. */
	private String smtpMailUserPassword;
	
	/**
	 * Gets the from mail address.
	 *
	 * @return the fromMailAddress
	 */
	public String getFromMailAddress() {
		return fromMailAddress;
	}
	
	/**
	 * Sets the from mail address.
	 *
	 * @param fromMailAddress the fromMailAddress to set
	 */
	public void setFromMailAddress(String fromMailAddress) {
		this.fromMailAddress = fromMailAddress;
	}
	
	/**
	 * Gets the to mail address.
	 *
	 * @return the toMailAddress
	 */
	public String getToMailAddress() {
		return toMailAddress;
	}
	
	/**
	 * Sets the to mail address.
	 *
	 * @param toMailAddress the toMailAddress to set
	 */
	public void setToMailAddress(String toMailAddress) {
		this.toMailAddress = toMailAddress;
	}
	
	/**
	 * Gets the smtp mail server host.
	 *
	 * @return the smtpMailServerHost
	 */
	public String getSmtpMailServerHost() {
		return smtpMailServerHost;
	}
	
	/**
	 * Sets the smtp mail server host.
	 *
	 * @param smtpMailServerHost the smtpMailServerHost to set
	 */
	public void setSmtpMailServerHost(String smtpMailServerHost) {
		this.smtpMailServerHost = smtpMailServerHost;
	}
	
	/**
	 * Gets the smtp mail server port.
	 *
	 * @return the smtpMailServerPort
	 */
	public int getSmtpMailServerPort() {
		return smtpMailServerPort;
	}
	
	/**
	 * Sets the smtp mail server port.
	 *
	 * @param smtpMailServerPort the smtpMailServerPort to set
	 */
	public void setSmtpMailServerPort(int smtpMailServerPort) {
		this.smtpMailServerPort = smtpMailServerPort;
	}
	
	/**
	 * Gets the smtp mail user name.
	 *
	 * @return the smtpMailUserName
	 */
	public String getSmtpMailUserName() {
		return smtpMailUserName;
	}
	
	/**
	 * Sets the smtp mail user name.
	 *
	 * @param smtpMailUserName the smtpMailUserName to set
	 */
	public void setSmtpMailUserName(String smtpMailUserName) {
		this.smtpMailUserName = smtpMailUserName;
	}
	
	/**
	 * Gets the smtp mail user password.
	 *
	 * @return the smtpMailUserPassword
	 */
	public String getSmtpMailUserPassword() {
		return smtpMailUserPassword;
	}
	
	/**
	 * Sets the smtp mail user password.
	 *
	 * @param smtpMailUserPassword the smtpMailUserPassword to set
	 */
	public void setSmtpMailUserPassword(String smtpMailUserPassword) {
		this.smtpMailUserPassword = smtpMailUserPassword;
	}
		

}
