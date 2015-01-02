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
package org.bigtester.ate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.bigtester.ate.model.casestep.TestCase;
import org.bigtester.ate.model.data.TestDatabaseInitializer;
import org.bigtester.ate.model.page.page.Homepage;
import org.bigtester.ate.model.page.page.Lastpage;
import org.bigtester.ate.model.page.page.RegularPage;
import org.bigtester.ate.model.project.TestProject;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

// TODO: Auto-generated Javadoc
/**
 * This class GlobalUtils defines ....
 * 
 * @author Peidong Hu
 *
 */
public final class GlobalUtils {

	/**
	 * Find test case bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the xml test case
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static TestCase findTestCaseBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, TestCase> testcases = appCtx.getBeansOfType(TestCase.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestCase.class);
		} else {
			return testcases.values().iterator().next();
		}
	}

	/**
	 * Find db initializer.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the test database initializer
	 */
	public static TestDatabaseInitializer findDBInitializer(
			ApplicationContext appCtx) {
		Map<String, TestDatabaseInitializer> dbInit = appCtx
				.getBeansOfType(TestDatabaseInitializer.class);

		if (dbInit.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestCase.class);
		} else {
			return dbInit.values().iterator().next();
		}
	}

	/**
	 * Find db initializer.
	 *
	 * @param beanFac
	 *            the bean factory
	 * @return the test database initializer
	 */
	public static TestDatabaseInitializer findDBInitializer(BeanFactory beanFac) {

		TestDatabaseInitializer dbInit = beanFac
				.getBean(TestDatabaseInitializer.class);

		return dbInit;

	}

	/**
	 * Gets the case data files.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the case data files
	 */
	public static List<Resource> getCaseDataFiles(ApplicationContext appCtx) {
		Map<String, Homepage> homepages = appCtx.getBeansOfType(Homepage.class,
				true, true);
		Map<String, Lastpage> lastpages = appCtx.getBeansOfType(Lastpage.class);
		Map<String, RegularPage> regularpages = appCtx
				.getBeansOfType(RegularPage.class);

		List<Resource> dataFiles = new ArrayList<Resource>();
		for (int i = 0; i < homepages.size(); i++) {
			if (null != homepages.values().iterator().next().getDataFile())
				dataFiles.add(homepages.values().iterator().next()
						.getDataFile());
		}
		for (int i = 0; i < lastpages.size(); i++) {
			if (null != lastpages.values().iterator().next().getDataFile())
				dataFiles.add(lastpages.values().iterator().next()
						.getDataFile());
		}
		for (int i = 0; i < regularpages.size(); i++) {
			if (null != regularpages.values().iterator().next().getDataFile())
				dataFiles.add(regularpages.values().iterator().next()
						.getDataFile());
		}
		return dataFiles;

	}

	/**
	 * Find test project bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the test project
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static TestProject findTestProjectBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, TestProject> testcases = appCtx
				.getBeansOfType(TestProject.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestProject.class);
		} else {
			return testcases.values().iterator().next();
		}

	}

	/**
	 * Find data source bean.
	 *
	 * @param appCtx
	 *            the app ctx
	 * @return the data source
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static DataSource findDataSourceBean(ApplicationContext appCtx)
			throws NoSuchBeanDefinitionException {
		Map<String, DataSource> testcases = appCtx
				.getBeansOfType(DataSource.class);

		if (testcases.isEmpty()) {
			throw new NoSuchBeanDefinitionException(TestProject.class);
		} else {
			return testcases.values().iterator().next();
		}

	}

	
	/**
	 * Find data source bean.
	 *
	 * @param beanFac
	 *            the bean factory
	 * @return the data source
	 * @throws NoSuchBeanDefinitionException
	 *             the no such bean definition exception
	 */
	public static DataSource findDataSourceBean(BeanFactory beanFac) {
		DataSource dataSrc = beanFac.getBean(DataSource.class);

		return dataSrc;

	}

	private GlobalUtils() {
	}

}
