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

import org.springframework.util.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.constant.EnumRunTimeDataType;
import org.bigtester.ate.constant.RumtimeDataHolderType;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.data.CaseServiceParsedDataHolder;
import org.bigtester.ate.model.data.GmailParsedDataHolder;
import org.bigtester.ate.model.data.ManualAssignedValueDataHolder;
import org.bigtester.ate.model.data.PageParsedDataHolder;
import org.bigtester.ate.model.data.RandomAlphaNumericValueDataHolder;
import org.bigtester.ate.model.data.RandomAlphaTextValueDataHolder;
import org.bigtester.ate.model.data.RandomEmailDataHolder;
import org.bigtester.ate.model.data.RandomNumericValueDataHolder;
import org.bigtester.ate.model.data.StepServiceParsedDataHolder;
import org.eclipse.jdt.annotation.Nullable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * This class SimpleDateFormatBeanDefinitionParser defines ....
 * 
 * @author Peidong Hu
 *
 */
public class RunTimeDataHolderBeanDefinitionParser extends
		AbstractBeanDefinitionParser {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected @Nullable AbstractBeanDefinition parseInternal(
			@Nullable Element element, @Nullable ParserContext parserContext) {

		// Here we parse the Spring elements such as < property>
		if (parserContext == null || element == null)
			throw GlobalUtils
					.createNotInitializedException("element and parserContext");
		// Here we parse the Spring elements such as < property>

		String dataHolderType = element.getAttribute(
				XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_DATAHOLDERTYPE)
				.toUpperCase();
		if (null == dataHolderType || !StringUtils.hasText(dataHolderType)) {
			throw GlobalUtils
					.createNotInitializedException("dataHolderType attribute in xml test case");
		}
		RumtimeDataHolderType enumDataHolderType = RumtimeDataHolderType
				.valueOf(dataHolderType);
		BeanDefinitionHolder holder = parserContext.getDelegate()
				.parseBeanDefinitionElement(element);
		BeanDefinition bDef = holder.getBeanDefinition();
		switch (enumDataHolderType) {
		case PAGESOURCEPARSED:
			bDef.setBeanClassName(PageParsedDataHolder.class.getName());
			break;
		case GMAILPARSED:
			bDef.setBeanClassName(GmailParsedDataHolder.class.getName());
			break;
		case MANUALASSIGNED:
			bDef.setBeanClassName(ManualAssignedValueDataHolder.class.getName());
			break;
		case CASESERVICEPARSED:
			bDef.setBeanClassName(CaseServiceParsedDataHolder.class.getName());
			break;
		case STEPSERVICEPARSED:
			bDef.setBeanClassName(StepServiceParsedDataHolder.class.getName());
			break;
		case RANDOMALPHATEXT:
			bDef.setBeanClassName(RandomAlphaTextValueDataHolder.class.getName());
			break;
		case RANDOMNUMBERS:
			bDef.setBeanClassName(RandomNumericValueDataHolder.class.getName());
			break;
		case RANDOMTEXTNUMBERS:
			bDef.setBeanClassName(RandomAlphaNumericValueDataHolder.class.getName());
			break;
		case RANDOMEMAIL:
			bDef.setBeanClassName(RandomEmailDataHolder.class.getName());
			break;
		default:
			break;

		}
		String dataType = element.getAttribute(
				XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_DATATYPE)
				.toUpperCase();
		if (null != dataType && StringUtils.hasText(dataType)) {
			EnumRunTimeDataType enumDataType = EnumRunTimeDataType
					.valueOf(dataType);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					enumDataType);
		}

		String dataValue = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_DATAVALUE);
		if (StringUtils.hasText(dataValue)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					dataValue);
		}

		String leftBoundry = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_LEFTBOUNDRY);
		if (StringUtils.hasText(leftBoundry)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					leftBoundry);
		}

		String rightBoundry = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_RIGHTBOUNDRY);
		if (StringUtils.hasText(rightBoundry)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					rightBoundry);
		}

		String page = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_PAGE);
		if (StringUtils.hasText(page)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					new RuntimeBeanReference(page));
		}

		String beanID = element.getAttribute("id");
		bDef.getConstructorArgumentValues().addGenericArgumentValue(beanID);

		String mappedBeanID = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_SUBCASEMAPPEDDATAHOLDERID);
		if (StringUtils.hasText(mappedBeanID)) {
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					mappedBeanID);
		}
		
		String indexOfSameTypeDataOnPage = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_INDEXOFSAMETYPEDATAONPAGE);
		if (StringUtils.hasText(indexOfSameTypeDataOnPage)) {
			int index = Integer.parseInt(indexOfSameTypeDataOnPage);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					index);
		}
		
		String randomTextLength = element
				.getAttribute(XsdElementConstants.ATTR_RUNTIMEDATAHOLDER_RANDOMINPUTLENGTH);
		if (StringUtils.hasText(randomTextLength)) {
			int length = Integer.parseInt(randomTextLength);
			bDef.getConstructorArgumentValues().addGenericArgumentValue(
					length);
		}
		
		parserContext.getRegistry().registerBeanDefinition(
				element.getAttribute("id"), bDef);
		return (AbstractBeanDefinition) bDef;

	}

}
