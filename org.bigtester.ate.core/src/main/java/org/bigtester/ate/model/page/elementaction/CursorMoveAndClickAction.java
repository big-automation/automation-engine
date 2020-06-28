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


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import org.bigtester.ate.GlobalUtils;
import org.bigtester.ate.annotation.ATELogLevel;
import org.bigtester.ate.annotation.ActionLoggable;
import org.bigtester.ate.constant.XsdElementConstants;
import org.bigtester.ate.model.casestep.JavaCodedStep.JavaCodedStepNameSpaceParser;
import org.bigtester.ate.model.page.atewebdriver.IMyWebDriver;
import org.bigtester.ate.xmlschema.IXsdBeanDefinitionParser;
import org.eclipse.jdt.annotation.Nullable;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.Locatable;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

// TODO: Auto-generated Javadoc
/**
 * The Class ClickAction defines ....
 * 
 * @author Peidong Hu
 */
public class CursorMoveAndClickAction extends BaseElementAction
		implements IElementAction, ITestObjectActionImpl, IXsdBeanDefinitionParser {
	int xOffset = 0;

	/** The xsd element javacodedstep. */
	final public static String XSD_ELEMENT_NAME = "mouseMoveAndClickAction";
	
	final public static String ATTR_CURSORMOVE_XOFFSET = "xOffset";
	
	final public static String ATTR_CURSORMOVE_YOFFSET = "yOffset";

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	int yOffset = 0;

	/**
	 * @param myWd
	 */
	public CursorMoveAndClickAction(IMyWebDriver myWd) {
		super(myWd);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new cursor move action.
	 */
	public CursorMoveAndClickAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@ActionLoggable(level = ATELogLevel.INFO)
	public void doAction(final WebElement webElm) {
		Actions act = new Actions(getMyWd().getWebDriver());
		Point loc = ((Locatable)webElm).getCoordinates().onPage();
		//Robot robot;
		//try {
			//robot = new Robot();
		
			//robot.mouseMove((1080 - coordinates.getWidth())/4, 768/2);
			//robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			//robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		//moveToElement not supported
		//if (xOffset == 0 && yOffset == 0)
			//act.moveToElement(webElm).build().perform();
		//else
			//act.
			//act.moveToElement(webElm, -200, yOffset).build().perform();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public String getXsdElementTag() {
		return XSD_ELEMENT_NAME;
	}

	public class CursorMoveAndClickActionBeanDefinitionParser extends AbstractBeanDefinitionParser {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected @Nullable AbstractBeanDefinition parseInternal(@Nullable Element element,
				@Nullable ParserContext parserContext) {

			// Here we parse the Spring elements such as < property>
			if (parserContext == null || element == null)
				throw GlobalUtils.createNotInitializedException("element and parserContext");
			// Here we parse the Spring elements such as < property>
			BeanDefinitionHolder holder = parserContext.getDelegate().parseBeanDefinitionElement(element);
			BeanDefinition bDef = holder.getBeanDefinition();
			bDef.setBeanClassName(CursorMoveAndClickAction.class.getName());
			int offset = Integer.parseInt(element.getAttribute(ATTR_CURSORMOVE_XOFFSET));
			bDef.getPropertyValues().addPropertyValue(ATTR_CURSORMOVE_XOFFSET, offset);

			offset = Integer.parseInt(element.getAttribute(ATTR_CURSORMOVE_YOFFSET));
			bDef.getPropertyValues().addPropertyValue(ATTR_CURSORMOVE_YOFFSET, offset);

			bDef.setParentName(XsdElementConstants.ELEMENT_ID_BASEELEMENTACTION);

			parserContext.getRegistry().registerBeanDefinition(element.getAttribute("id"), bDef);
			return (AbstractBeanDefinition) bDef;

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BeanDefinitionParser getParser() {
		return new CursorMoveAndClickActionBeanDefinitionParser();
	}

}
