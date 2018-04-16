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
package org.bigtester.ate.model.page.elementaction;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class SendKeysAction defines ....
 * 
 * @author Peidong Hu
 */
public class InjectJsAction extends BaseElementAction implements
		IElementAction, ITestObjectActionImpl, IXsdBeanDefinitionParser {

	/** The selections. */
	final private Optional<String> javaScriptCode;

	/** The xsd element javacodedstep. */
	final public static String XSD_ELEMENT_INJECTJSACTION = "injectJsAction";

	/** The Constant ATTR_JAVASCRIPTCODE. */
	final public static String ATTR_JAVASCRIPTCODE = "javaScriptCode";

	/** The name space parser. */
	@Nullable
	private static InjectJsActionNameSpaceParser nameSpaceParser = new InjectJsActionNameSpaceParser();

	/**
	 * The Class InjectJsActionNameSpaceParser.
	 *
	 * @author Peidong Hu
	 */
	public static class InjectJsActionNameSpaceParser extends
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
			BeanDefinitionHolder holder = parserContext.getDelegate()
					.parseBeanDefinitionElement(element);
			BeanDefinition bDef = holder.getBeanDefinition();
			bDef.setBeanClassName(InjectJsAction.class.getName());

			String injectJsAction = element.getAttribute(ATTR_JAVASCRIPTCODE);
			if (!StringUtils.isEmpty(injectJsAction)) {

				// bDef.setAttribute(
				// ATTR_JAVASCRIPTCODE,
				// injectJsAction);
				bDef.getConstructorArgumentValues().addGenericArgumentValue(
						injectJsAction);
			}

			bDef.setParentName(XsdElementConstants.ELEMENT_ID_BASEELEMENTACTION);

			bDef.setAttribute("id", element.getAttribute("id"));
			parserContext.getRegistry().registerBeanDefinition(
					element.getAttribute("id"), bDef);
			return (AbstractBeanDefinition) bDef;
		}

	}

	/**
	 * Instantiates a new assign value action.
	 *
	 * @param myWd
	 *            the my wd
	 * @param dataValue
	 *            the data value
	 */
	public InjectJsAction(IMyWebDriver myWd, String javaScriptCode) {
		super(myWd);
		this.javaScriptCode = Optional.of(javaScriptCode);
	}

	/**
	 * Instantiates a new dropdown list select action.
	 *
	 * @param selections
	 *            the selections
	 */
	public InjectJsAction(String javaScriptCode) {
		super();
		this.javaScriptCode = Optional.of(javaScriptCode);
	}

	/**
	 * Instantiates a new inject js action.
	 */
	public InjectJsAction() {
		super();
		this.javaScriptCode = Optional.empty();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable(level = ATELogLevel.INFO)
	public void doAction(final WebElement webElm) {

		if (javaScriptCode.isPresent()) // NOPMD
			((JavascriptExecutor) this.getMyWd().getWebDriverInstance())
					.executeScript(javaScriptCode.get(), webElm);

	}

	/**
	 * @return the javaScriptCode
	 */
	public Optional<String> getJavaScriptCode() {
		return javaScriptCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getXsdElementTag() {
		return XSD_ELEMENT_INJECTJSACTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanDefinitionParser getParser() {
		return nameSpaceParser;
	}

}
