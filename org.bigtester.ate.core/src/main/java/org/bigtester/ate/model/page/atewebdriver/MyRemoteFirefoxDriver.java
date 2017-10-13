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
package org.bigtester.ate.model.page.atewebdriver;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.model.casestep.JavaCodedStep.JavaCodedStepNameSpaceParser;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.bigtester.ate.xmlschema.RemoteDriverBeanDefinitionParser;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * The Class MyChromeDriver defines ....
 * 
 * @author Jun Yang
 */
public class MyRemoteFirefoxDriver extends MyRemoteDriver implements IMyWebDriver, IXsdBeanDefinitionParser {
	/** The xsd element javacodedstep. */
	final public static String XSD_ELEMENT_NAME = "remoteFirefoxDriver";
	
	/** The name space parser. */
	@Nullable
	private static RemoteFirefoxDriverNameSpaceParser nameSpaceParser;
	
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyRemoteFirefoxDriver(String url) {
		
		super("firefox", null, null, url);
	}
		
	/**
	 * Instantiates a new my Chrome driver.
	 */
	public MyRemoteFirefoxDriver() {
		
		super();
		 
		 
	}
	public class RemoteFirefoxDriverNameSpaceParser extends
	RemoteDriverBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Class<?> getBeanClass(@Nullable Element element) {
		return MyRemoteFirefoxDriver.class;
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParse(@Nullable Element element,
			@Nullable BeanDefinitionBuilder bean) {
		
		if (bean == null || element == null)
			throw GlobalUtils.createNotInitializedException("element and bean");
		super.doParse(element, bean);
				
//		String userName = element
//				.getAttribute(XsdElementConstants.ATTR_REMOTECHROMEDRIVER_URL);
//		if (!StringUtils.isEmpty(userName)) {
//			bean.addConstructorArgValue(userName);
//		}
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory
	 *      .xml.AbstractBeanDefinitionParser#resolveId(org.w3c.dom.Element,
	 *      org.springframework.beans.factory.support.AbstractBeanDefinition,
	 *      org.springframework.beans.factory.xml.ParserContext)
	 */
	protected String resolveId(@Nullable Element element,
			@Nullable AbstractBeanDefinition definition,
			@Nullable ParserContext parserContext)
			throws BeanDefinitionStoreException {

		return "MyWebDriver2";
	}
}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getXsdElementTag() {
		// TODO Auto-generated method stub
		return XSD_ELEMENT_NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanDefinitionParser getParser() {
		final RemoteFirefoxDriverNameSpaceParser nameSpaceParser2 = nameSpaceParser;
		if (nameSpaceParser2 == null) {
			return new RemoteFirefoxDriverNameSpaceParser();// NOPMD
		} else {
			return nameSpaceParser2;

		}
	}

	/**
	 * @return the nameSpaceParser
	 */
	public static RemoteFirefoxDriverNameSpaceParser getNameSpaceParser() {
		return nameSpaceParser;
	}

	/**
	 * @param nameSpaceParser the nameSpaceParser to set
	 */
	public static void setNameSpaceParser(RemoteFirefoxDriverNameSpaceParser nameSpaceParser) {
		MyRemoteFirefoxDriver.nameSpaceParser = nameSpaceParser;
	}

	

}
