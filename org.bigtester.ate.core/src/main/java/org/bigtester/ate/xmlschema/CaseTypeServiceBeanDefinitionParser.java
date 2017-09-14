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
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.casestep.CaseTypeService;
import org.bigtester.ate.model.casestep.ICucumberTestStep.CucumberStepType;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class CaseTypeServiceBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("null")
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		
		// Here we parse the Spring elements such as < property>
				if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
				// Here we parse the Spring elements such as < property>
		        BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(element);
		        BeanDefinition bDef = holder.getBeanDefinition();
		        bDef.setBeanClassName(CaseTypeService.class.getName());
		        String cucumberStepType = element.getAttribute(XsdElementConstants.ATTR_CUCUMBER_STEP_TYPE);
		        
		        if (StringUtils.hasText(cucumberStepType)) {
		        	bDef.getConstructorArgumentValues().addGenericArgumentValue(CucumberStepType.valueOf(cucumberStepType));
				}		
		        String testCaseName = element.getAttribute(XsdElementConstants.ATTR_TESTCASE_TESTCASENAME);
		        if (StringUtils.hasText(testCaseName)) {
		        	bDef.getConstructorArgumentValues().addGenericArgumentValue(testCaseName);
				}
				String testCaseFName = element.getAttribute(XsdElementConstants.ATTR_TESTCASE_TESTCASEFILENAME);
				if (StringUtils.hasText(testCaseFName)) {
					bDef.getConstructorArgumentValues().addGenericArgumentValue(testCaseFName);
				}
				String parentTestCase = element.getAttribute(XsdElementConstants.ATTR_TESTCASE_PARENTTESTCASE);
				if (StringUtils.hasText(parentTestCase)) {
					bDef.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(parentTestCase));
				}
		        
		        
		        String idstring = element.getAttribute("id");
		        
		        parserContext.getRegistry().registerBeanDefinition(idstring, bDef);
		        return (AbstractBeanDefinition) bDef;
		 
	}

}
