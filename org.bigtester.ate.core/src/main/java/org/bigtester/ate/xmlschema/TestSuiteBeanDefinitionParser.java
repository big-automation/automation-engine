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

import java.util.List;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.project.TestSuite;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;


// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class TestSuiteBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		// this will never be null since the schema explicitly requires that a value be supplied
        String testSuiteName = element.getAttribute(XsdElementConstants.ATTR_TESTSUITE_SUITENAME);
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(TestSuite.class);
        if (StringUtils.hasText(testSuiteName))
        	factory.addConstructorArgValue(testSuiteName);
        
        
		@SuppressWarnings("null")
		List<Element> suiteListElements = (List<Element>) DomUtils.getChildElementsByTagName(element, XsdElementConstants.ELEMENT_XMLTESTCASE);
        
        if (suiteListElements != null && !suiteListElements.isEmpty()) {
        	if (null == factory) throw GlobalUtils.createNotInitializedException("factory");
            parseXmlTestCaseComponents(suiteListElements, factory,  parserContext);
        }
        
        return factory.getBeanDefinition();
	}
	
	@SuppressWarnings("null")
	private static void parseXmlTestCaseComponents(List<Element> childElements, BeanDefinitionBuilder factory, ParserContext parserContext) {
        ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>(childElements.size());
        for (Element element : childElements) {
        	XmlTestCaseBeanDefinitionParser xmltestcase = new XmlTestCaseBeanDefinitionParser();
        	children.add(xmltestcase.parse(element, parserContext));
        }
        factory.addPropertyValue(XsdElementConstants.PROP_TESTSUITE_TESTCASELIST, children);
    }

}
