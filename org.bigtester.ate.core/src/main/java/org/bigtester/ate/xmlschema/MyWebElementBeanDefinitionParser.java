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
import org.bigtester.ate.model.page.page.MyWebElement;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
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
public class MyWebElementBeanDefinitionParser extends AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractBeanDefinition parseInternal(@Nullable Element element,
			@Nullable ParserContext parserContext) {
		// Here we parse the Spring elements such as < property>
		if (parserContext==null || element == null ) throw GlobalUtils.createNotInitializedException("element and parserContext");
		// Here we parse the Spring elements such as < property>
        BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(element);
        BeanDefinition bDef = holder.getBeanDefinition();
        bDef.setBeanClassName(MyWebElement.class.getName());
        
        String elementFind = element
				.getAttribute(XsdElementConstants.ATTR_MYWEBELEMENT_ELEMENTFIND);
        if (StringUtils.hasText(elementFind)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					new RuntimeBeanReference(elementFind));
		}
        String elementAction = element
				.getAttribute(XsdElementConstants.ATTR_MYWEBELEMENT_ELEMENTACTION);
		if (StringUtils.hasText(elementAction)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					new RuntimeBeanReference(elementAction));
		}
//		String parent = element
//				.getAttribute("parent");
//		if (StringUtils.hasText(parent)) {
		bDef.setParentName("myBasePageModel");
//		}
       
//        String text = element.getAttribute("text");
//        bd.getPropertyValues().addPropertyValue("text", text);
        parserContext.getRegistry().registerBeanDefinition(element.getAttribute("id"), bDef);
        return (AbstractBeanDefinition) bDef;
      
	}
}
