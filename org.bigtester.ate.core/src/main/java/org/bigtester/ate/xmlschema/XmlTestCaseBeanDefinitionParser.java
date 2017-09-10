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
import org.bigtester.ate.model.project.XmlTestCase;
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
public class XmlTestCaseBeanDefinitionParser extends
AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
        // this will never be null since the schema explicitly requires that a value be supplied
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		BeanDefinitionBuilder factory = BeanDefinitionBuilder
				.rootBeanDefinition(XmlTestCase.class);
		String testCaseName = element.getAttribute(XsdElementConstants.ATTR_XMLTESTCASE_TESTCASEFILEPATHNAME);
        if (StringUtils.hasText(testCaseName)) {
        	factory.addConstructorArgValue( testCaseName);
        }
        @SuppressWarnings("null")
		List<Element> dependencies = (List<Element>) DomUtils.getChildElementsByTagName(element, XsdElementConstants.ELEMENT_CASEDEPENDENCIES);
        if (null != dependencies && dependencies.size() == 1) {
	        @SuppressWarnings("null")
			List<Element> allDependencies = (List<Element>) DomUtils.getChildElementsByTagName(dependencies.get(0), XsdElementConstants.ELEMENT_CASEDEPENDENCY);
	        
	        if (allDependencies != null && !allDependencies.isEmpty()) {
	        	if (null == factory) throw GlobalUtils.createNotInitializedException("factory");
	            parseCaseDependenciesInnerComponents(allDependencies, factory,  parserContext);
	        }
        }
        return factory.getBeanDefinition();
    }
	
	@SuppressWarnings("null")
	private static void parseCaseDependenciesInnerComponents(List<Element> allDependencies, BeanDefinitionBuilder factory, ParserContext parserContext) {
        ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>(allDependencies.size());
        for (Element element : allDependencies) {
        	CaseDependencyBeanDefinitionParser dependency = new CaseDependencyBeanDefinitionParser();
        	children.add(dependency.parse(element, parserContext));
        }
        factory.addPropertyValue(XsdElementConstants.PROP_XMLTESTCASE_DEPENDONTESTCASES, children);
    }
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean shouldGenerateId() {
		return true;
	}

}
