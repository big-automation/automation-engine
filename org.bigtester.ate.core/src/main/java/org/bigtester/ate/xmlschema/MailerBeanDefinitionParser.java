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
package org.bigtester.ate.xmlschema;


import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.project.Mailer;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class MailerBeanDefinitionParser extends
AbstractBeanDefinitionParser {
	
	/** The Constant ATTR_FROM_MAIL_ADDRESS. */
	public static final String ATTR_FROM_MAIL_ADDRESS = "fromMailAddress";
	
	/** The Constant ATTR_TO_MAIL_ADDRESS. */
	public static final String ATTR_TO_MAIL_ADDRESS = "toMailAddress";
	
	/** The Constant ATTR_SMTP_MAIL_SERVER_HOST. */
	public static final String ATTR_SMTP_MAIL_SERVER_HOST = "smtpMailServerHost";
	
	/** The Constant ATTR_SMTP_MAIL_SERVER_PORT. */
	public static final String ATTR_SMTP_MAIL_SERVER_PORT = "smtpMailServerPort";
	
	/** The Constant ATTR_MAIL_USERNAME. */
	public static final String ATTR_MAIL_USERNAME = "smtpMailUserName";
	
	/** The Constant ATTR_MAIL_USER_PASSWORD. */
	public static final String ATTR_MAIL_USER_PASSWORD = "smtpMailUserPassword";


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		// this will never be null since the schema explicitly requires that a value be supplied
		BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(Mailer.class);

		String from = element.getAttribute(ATTR_FROM_MAIL_ADDRESS);
		if (StringUtils.hasText(from))
			factory.addPropertyValue(ATTR_FROM_MAIL_ADDRESS, from);

		String toaddr = element.getAttribute(ATTR_TO_MAIL_ADDRESS);
		if (StringUtils.hasText(toaddr))
			factory.addPropertyValue(ATTR_TO_MAIL_ADDRESS, toaddr);

		String host = element.getAttribute(ATTR_SMTP_MAIL_SERVER_HOST);
		if (StringUtils.hasText(host))
			factory.addPropertyValue(ATTR_SMTP_MAIL_SERVER_HOST, host);
		String port = element.getAttribute(ATTR_SMTP_MAIL_SERVER_PORT);
		if (StringUtils.hasText(port))
			factory.addPropertyValue(ATTR_SMTP_MAIL_SERVER_PORT, port);
		String username = element.getAttribute(ATTR_MAIL_USERNAME);
		if (StringUtils.hasText(username))
			factory.addPropertyValue(ATTR_MAIL_USERNAME, username);

		String password = element.getAttribute(ATTR_MAIL_USER_PASSWORD);
		if (StringUtils.hasText(password))
			factory.addPropertyValue(ATTR_MAIL_USER_PASSWORD, password);


		return factory.getBeanDefinition();
	}


}
