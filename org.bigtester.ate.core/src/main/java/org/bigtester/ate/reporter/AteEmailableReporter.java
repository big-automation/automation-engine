/*******************************************************************************
 * ATE, Automation Test Engine
 *
 * Copyright 2017, Montreal PROT, or individual contributors as
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
package org.bigtester.ate.reporter;
import org.bigtester.ate.model.project.TestProject;
import org.bigtester.ate.model.project.TestProjectListener;
import org.bigtester.ate.model.project.TestSuite;
import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestRunner;
import org.testng.collections.Lists;
import org.testng.collections.Sets;
import org.testng.internal.Utils;
import org.testng.log4testng.Logger;
import org.testng.reporters.XMLReporterConfig;
import org.testng.reporters.XMLStringBuffer;
import org.testng.xml.XmlSuite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Reported designed to render self-contained HTML top down view of a testing
 * suite.
 *
 * @author Paul Mendelson
 * @since 5.2
 * @version $Revision: 719 $
 */
public class AteEmailableReporter implements IReporter {
  private static final Logger L = Logger.getLogger(AteEmailableReporter.class);

  // ~ Instance fields ------------------------------------------------------

  private PrintWriter m_out;

  private int m_row;

  private Integer m_testIndex;

  private int m_methodIndex;

  // ~ Methods --------------------------------------------------------------

  /** Creates summary of the run */
  @Override
  public void generateReport(List<XmlSuite> xml, List<ISuite> suites, String outdir) {
	  
	  TestProject testProj = this.getTestProject(suites.get(0).getResults().entrySet().iterator().next().getValue().getTestContext());
    try {
      m_out = createWriter(outdir);
    }
    catch (IOException e) {
      L.error("output file", e);
      return;
    }
    startHtml(m_out);
    generateSuiteSummaryReport(suites, testProj);
    generateMethodSummaryReport(suites, testProj);
    //generateMethodDetailReport(suites);
    endHtml(m_out);
    m_out.flush();
    m_out.close();
  }

  protected PrintWriter createWriter(String outdir) throws IOException {
    new File(outdir).mkdirs();
    return new PrintWriter(new BufferedWriter(new FileWriter(new File(outdir,
        "ate-emailable-report.html"))));
  }
  
  
  private Set<ITestResult> getAllTestResults(ISuiteResult suiteResult) {
	    
	    Set<ITestResult> testResults = Sets.newHashSet();
	    ITestContext testContext = suiteResult.getTestContext();
	    addAllTestResults(testResults, testContext.getPassedTests());
	    addAllTestResults(testResults, testContext.getFailedTests());
	    addAllTestResults(testResults, testContext.getSkippedTests());
	    addAllTestResults(testResults, testContext.getPassedConfigurations());
	    addAllTestResults(testResults, testContext.getSkippedConfigurations());
	    addAllTestResults(testResults, testContext.getFailedConfigurations());
	    addAllTestResults(testResults, testContext.getFailedButWithinSuccessPercentageTests());
	    return testResults;
	    
	  }

	  private void addAllTestResults(Set<ITestResult> testResults, IResultMap resultMap) {
	    if (resultMap != null) {
	      // Sort the results chronologically before adding them
	      List<ITestResult> allResults = new ArrayList<ITestResult>();
	      allResults.addAll(resultMap.getAllResults());

	      Collections.sort(new ArrayList<>(allResults), new Comparator<ITestResult>() {
	        @Override
	        public int compare(ITestResult o1, ITestResult o2) {
	          return (int) (o1.getStartMillis() - o2.getStartMillis());
	        }
	      });

	      testResults.addAll(allResults);
	    }
	  }


  /** Creates a table showing the highlights of each test method with links to the method details */
  protected void generateMethodSummaryReport(List<ISuite> suites, TestProject testProject) {
    m_methodIndex = 0;
    startResultSummaryTable("methodOverview");
    int testIndex = 1;
    ISuiteResult testResult = suites.iterator().next().getResults().values().iterator().next();
    
    List<ITestNGMethod> allTestCases = Arrays.asList(testResult.getTestContext().getAllTestMethods());
    Set<ITestResult> allTestCasesResult = getAllTestResults(testResult);
    testProject.getSuiteList().forEach(suite-> {
      
    	titleRow(suite.getSuiteName() + " Suite", 6);
      
      Set<String> allSuiteCaseFNames = suite.getTestCaseList().stream().map(testC->testC.getTestCaseFilePathName()).collect(Collectors.toSet());
      List<ITestResult> thisSuiteTestCases = allTestCasesResult.stream().filter(testCase->allSuiteCaseFNames.contains(testCase.getName())).collect(Collectors.toList());
      
      for (ITestResult testCaseResult : thisSuiteTestCases) {
    	  printCaseResult(testCaseResult, suite.getSuiteName());
        
      }
    });
    m_out.println("</table>");
  }

  /** Creates a section showing known results for each method */
  protected void generateMethodDetailReport(List<ISuite> suites) {
    m_methodIndex = 0;
    for (ISuite suite : suites) {
      Map<String, ISuiteResult> r = suite.getResults();
      for (ISuiteResult r2 : r.values()) {
        ITestContext testContext = r2.getTestContext();
        if (r.values().size() > 0) {
          m_out.println("<h1>" + testContext.getName() + "</h1>");
        }
        resultDetail(testContext.getFailedConfigurations());
        resultDetail(testContext.getFailedTests());
        resultDetail(testContext.getSkippedConfigurations());
        resultDetail(testContext.getSkippedTests());
        resultDetail(testContext.getPassedTests());
      }
    }
  }

  private void printCaseResult(ITestResult caseResult, String suiteName) {
	  StringBuffer buff = new StringBuffer();
	  String status = "SUCEESS";
	  switch (caseResult.getStatus()) {
	  case -1: status = "CREATED";
	  	break;
	  case 2: status = "FAILURE";
	  break;
	  case 3: status = "SKIP";
	  break;
	  case 16: status = "STARTED";
	  break;
	  case 4: status = "SUCESS_PERCENTAGE_FAILURE";
	  break;
	  default:
		  	status = "SUCESS";
	  }
	  buff.append("<tr><td>" + suiteName + "</td><td>" + caseResult.getName() + "</td><td>" 
			  + caseResult.getMethod().getMethodName() + "</td><td>" 
			  + status + "</td><td>"
			  + caseResult.getStartMillis() + "</td><td>"
			  + (caseResult.getEndMillis() - caseResult.getStartMillis()) + "</td></tr>");
	  m_out.print(buff);
	  
	  
  }
  
  /**
   * @param tests
   */
  private void resultSummary(ISuite suite, IResultMap tests, String testname, String style,
      String details) {
    if (tests.getAllResults().size() > 0) {
      StringBuffer buff = new StringBuffer();
      String lastClassName = "";
      int mq = 0;
      int cq = 0;
      for (ITestNGMethod method : getMethodSet(tests, suite)) {
        m_row += 1;
        m_methodIndex += 1;
        ITestClass testClass = method.getTestClass();
        String className = testClass.getName();
        if (mq == 0) {
          String id = (m_testIndex == null ? null : "t" + Integer.toString(m_testIndex));
          titleRow(testname + " &#8212; " + style + details, 5, id);
          m_testIndex = null;
        }
        if (!className.equalsIgnoreCase(lastClassName)) {
          if (mq > 0) {
            cq += 1;
            m_out.print("<tr class=\"" + style
                + (cq % 2 == 0 ? "even" : "odd") + "\">" + "<td");
            if (mq > 1) {
              m_out.print(" rowspan=\"" + mq + "\"");
            }
            m_out.println(">" + lastClassName + "</td>" + buff);
          }
          mq = 0;
          buff.setLength(0);
          lastClassName = className;
        }
        Set<ITestResult> resultSet = tests.getResults(method);
        long end = Long.MIN_VALUE;
        long start = Long.MAX_VALUE;
        for (ITestResult testResult : tests.getResults(method)) {
          if (testResult.getEndMillis() > end) {
            end = testResult.getEndMillis();
          }
          if (testResult.getStartMillis() < start) {
            start = testResult.getStartMillis();
          }
        }
        mq += 1;
        if (mq > 1) {
          buff.append("<tr class=\"" + style + (cq % 2 == 0 ? "odd" : "even")
              + "\">");
        }
        String description = method.getDescription();
        String testInstanceName = resultSet.toArray(new ITestResult[]{})[0].getTestName();
        buff.append("<td><a href=\"#m" + m_methodIndex + "\">"
            + qualifiedName(method)
            + " " + (description != null && description.length() > 0
                ? "(\"" + description + "\")"
                : "")
            + "</a>" + (null == testInstanceName ? "" : "<br>(" + testInstanceName + ")")
            + "</td>"
            + "<td class=\"numi\">" + resultSet.size() + "</td>"
            + "<td>" + start + "</td>"
            + "<td class=\"numi\">" + (end - start) + "</td>"
            + "</tr>");
      }
      if (mq > 0) {
        cq += 1;
        m_out.print("<tr class=\"" + style + (cq % 2 == 0 ? "even" : "odd")
            + "\">" + "<td");
        if (mq > 1) {
          m_out.print(" rowspan=\"" + mq + "\"");
        }
        m_out.println(">" + lastClassName + "</td>" + buff);
      }
    }
  }

  /** Starts and defines columns result summary table */
  private void startResultSummaryTable(String style) {
    tableStart(style, "summary");
    m_out.println("<tr><th>Suite</th>"
            + "<th>TestCase</th><th>TestFunction</td><th>Status</th><th>Start</th><th>Time<br/>(ms)</th></tr>");
    m_row = 0;
  }

  private String qualifiedName(ITestNGMethod method) {
    StringBuilder addon = new StringBuilder();
    String[] groups = method.getGroups();
    int length = groups.length;
    if (length > 0 && !"basic".equalsIgnoreCase(groups[0])) {
      addon.append("(");
      for (int i = 0; i < length; i++) {
        if (i > 0) {
          addon.append(", ");
        }
          addon.append(groups[i]);
        }
      addon.append(")");
    }

    return "<b>" + method.getMethodName() + "</b> " + addon;
  }

  private void resultDetail(IResultMap tests) {
    for (ITestResult result : tests.getAllResults()) {
      ITestNGMethod method = result.getMethod();
        m_methodIndex++;
        String cname = method.getTestClass().getName();
        m_out.println("<h2 id=\"m" + m_methodIndex + "\">" + cname + ":"
            + method.getMethodName() + "</h2>");
        Set<ITestResult> resultSet = tests.getResults(method);
        generateForResult(result, method, resultSet.size());
        m_out.println("<p class=\"totop\"><a href=\"#summary\">back to summary</a></p>");

    }
  }

  private void generateForResult(ITestResult ans, ITestNGMethod method, int resultSetSize) {
    Object[] parameters = ans.getParameters();
    boolean hasParameters = parameters != null && parameters.length > 0;
    if (hasParameters) {
      tableStart("result", null);
      m_out.print("<tr class=\"param\">");
      for (int x = 1; x <= parameters.length; x++) {
        m_out.print("<th>Parameter #" + x + "</th>");
      }
      m_out.println("</tr>");
      m_out.print("<tr class=\"param stripe\">");
      for (Object p : parameters) {
        m_out.println("<td>" + Utils.escapeHtml(Utils.toString(p)) + "</td>");
      }
      m_out.println("</tr>");
    }
    List<String> msgs = Reporter.getOutput(ans);
    boolean hasReporterOutput = msgs.size() > 0;
    Throwable exception=ans.getThrowable();
    boolean hasThrowable = exception!=null;
    if (hasReporterOutput||hasThrowable) {
      if (hasParameters) {
        m_out.print("<tr><td");
        if (parameters.length > 1) {
          m_out.print(" colspan=\"" + parameters.length + "\"");
        }
        m_out.println(">");
      }
      else {
        m_out.println("<div>");
      }
      if (hasReporterOutput) {
        if(hasThrowable) {
          m_out.println("<h3>Test Messages</h3>");
        }
        for (String line : msgs) {
          m_out.println(line + "<br/>");
        }
      }
      if(hasThrowable) {
        boolean wantsMinimalOutput = ans.getStatus()==ITestResult.SUCCESS;
        if(hasReporterOutput) {
          m_out.println("<h3>"
              +(wantsMinimalOutput?"Expected Exception":"Failure")
              +"</h3>");
        }
        generateExceptionReport(exception,method);
      }
      if (hasParameters) {
        m_out.println("</td></tr>");
      }
      else {
        m_out.println("</div>");
      }
    }
    if (hasParameters) {
      m_out.println("</table>");
    }
  }

  protected void generateExceptionReport(Throwable exception,ITestNGMethod method) {
    m_out.print("<div class=\"stacktrace\">");
    m_out.print(Utils.stackTrace(exception, true)[0]);
    m_out.println("</div>");
  }

  /**
   * Since the methods will be sorted chronologically, we want to return
   * the ITestNGMethod from the invoked methods.
   */
  private Collection<ITestNGMethod> getMethodSet(IResultMap tests, ISuite suite) {
    List<IInvokedMethod> r = Lists.newArrayList();
    List<IInvokedMethod> invokedMethods = suite.getAllInvokedMethods();
    for (IInvokedMethod im : invokedMethods) {
      if (tests.getAllMethods().contains(im.getTestMethod())) {
        r.add(im);
      }
    }
    Arrays.sort(r.toArray(new IInvokedMethod[r.size()]), new TestSorter());
    List<ITestNGMethod> result = Lists.newArrayList();

    // Add all the invoked methods
    for (IInvokedMethod m : r) {
      result.add(m.getTestMethod());
    }

    // Add all the methods that weren't invoked (e.g. skipped) that we
    // haven't added yet
    for (ITestNGMethod m : tests.getAllMethods()) {
      if (!result.contains(m)) {
        result.add(m);
      }
    }
    return result;
  }

  private TestProject getTestProject(ITestContext testContext) {
	  return ((TestProjectListener)((TestRunner)testContext).getTestListeners().stream().filter(listener->listener instanceof TestProjectListener).collect(Collectors.toList()).get(0)).getMytp();
  }
  
  public void generateSuiteSummaryReport(List<ISuite> suites, TestProject testProj) {
    tableStart("testOverview", null);
    m_out.print("<tr>");
    tableColumnStart("Test Suites");
    tableColumnStart("Passed");
    //tableColumnStart("Scenarios<br/>Passed");
    tableColumnStart("# skipped");
    tableColumnStart("# failed");
    tableColumnStart("Total<br/>Time");
    //tableColumnStart("Included<br/>Groups");
    //tableColumnStart("Excluded<br/>Groups");
    m_out.println("</tr>");
    NumberFormat formatter = new DecimalFormat("#,##0.0");
    int qty_tests = 0;
    int qty_pass_m = 0;
    int qty_pass_s = 0;
    int qty_skip = 0;
    int qty_fail = 0;
    long time_start = Long.MAX_VALUE;
    long time_end = Long.MIN_VALUE;
    m_testIndex = 1;
    for (ISuite suite : suites) {
      if (suites.size() > 1) {
        titleRow(suite.getName(), 8);
      }
      Map<String, ISuiteResult> tests = suite.getResults();
      for (ISuiteResult r : tests.values()) {
        qty_tests += 1;
        ITestContext overview = r.getTestContext();
        startSummaryRow(testProj.getSuiteList());
        int q = getMethodSet(overview.getPassedTests(), suite).size();
        qty_pass_m += q;
        summaryCell(q,Integer.MAX_VALUE);
        //q = overview.getPassedTests().size();
        //qty_pass_s += q;
        //summaryCell(q,Integer.MAX_VALUE);
        q = getMethodSet(overview.getSkippedTests(), suite).size();
        qty_skip += q;
        summaryCell(q,0);
        q = getMethodSet(overview.getFailedTests(), suite).size();
        qty_fail += q;
        summaryCell(q,0);
        time_start = Math.min(overview.getStartDate().getTime(), time_start);
        time_end = Math.max(overview.getEndDate().getTime(), time_end);
        summaryCell(formatter.format(
            (overview.getEndDate().getTime() - overview.getStartDate().getTime()) / 1000.)
            + " seconds", true);
        //summaryCell(overview.getIncludedGroups());
        //summaryCell(overview.getExcludedGroups());
        m_out.println("</tr>");
        m_testIndex++;
      }
    }
    if (qty_tests > 1) {
      m_out.println("<tr class=\"total\"><td>Total</td>");
      summaryCell(qty_pass_m,Integer.MAX_VALUE);
      summaryCell(qty_pass_s,Integer.MAX_VALUE);
      summaryCell(qty_skip,0);
      summaryCell(qty_fail,0);
      summaryCell(formatter.format((time_end - time_start) / 1000.) + " seconds", true);
      m_out.println("<td colspan=\"2\">&nbsp;</td></tr>");
    }
    m_out.println("</table>");
  }

  private void summaryCell(String[] val) {
    StringBuffer b = new StringBuffer();
    for (String v : val) {
      b.append(v + " ");
    }
    summaryCell(b.toString(),true);
  }

  private void summaryCell(String v,boolean isgood) {
    m_out.print("<td class=\"numi"+(isgood?"":"_attn")+"\">" + v + "</td>");
  }

  private void startSummaryRow(String label) {
    m_row += 1;
    m_out.print("<tr" + (m_row % 2 == 0 ? " class=\"stripe\"" : "")
            + "><td style=\"text-align:left;padding-right:2em\"><a href=\"#t"
            + m_testIndex + "\">" + label + "</a>"
            + "</td>");
  }
  
  private void startSummaryRow(List<TestSuite> ateSuites) {
	    m_row += 1;
	    String cellContents = ateSuites.stream().map(suite->suite.getSuiteName()).collect(Collectors.joining("<br/>"));
	    m_out.print("<tr><td style=\"text-align:left;padding-right:2em\">" + cellContents
	            + "</td>");
  }

  private void summaryCell(int v,int maxexpected) {
    summaryCell(String.valueOf(v),v<=maxexpected);
  }

  private void tableStart(String cssclass, String id) {
    m_out.println("<table cellspacing=\"0\" cellpadding=\"0\""
        + (cssclass != null ? " class=\"" + cssclass + "\""
            : " style=\"padding-bottom:2em\"")
        + (id != null ? " id=\"" + id + "\"" : "")
        + ">");
    m_row = 0;
  }

  private void tableColumnStart(String label) {
    m_out.print("<th>" + label + "</th>");
  }

  private void titleRow(String label, int cq) {
    titleRow(label, cq, null);
  }

  private void titleRow(String label, int cq, String id) {
    m_out.print("<tr");
    if (id != null) {
      m_out.print(" id=\"" + id + "\"");
    }
    m_out.println( "><th colspan=\"" + cq + "\">" + label + "</th></tr>");
    m_row = 0;
  }

  /** Starts HTML stream */
  protected void startHtml(PrintWriter out) {
    out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
    out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
    out.println("<head>");
    out.println("<title>TestNG Report</title>");
    out.println("<style type=\"text/css\">");
    out.println("table {margin-bottom:10px;border-collapse:collapse;empty-cells:show}");
    out.println("td,th {border:1px solid #009;padding:.25em .5em}");
    out.println(".result th {vertical-align:bottom}");
    out.println(".param th {padding-left:1em;padding-right:1em}");
    out.println(".param td {padding-left:.5em;padding-right:2em}");
    out.println(".stripe td,.stripe th {background-color: #E6EBF9}");
    out.println(".numi,.numi_attn {text-align:right}");
    out.println(".total td {font-weight:bold}");
    out.println(".passedodd td {background-color: #0A0}");
    out.println(".passedeven td {background-color: #3F3}");
    out.println(".skippedodd td {background-color: #CCC}");
    out.println(".skippedodd td {background-color: #DDD}");
    out.println(".failedodd td,.numi_attn {background-color: #F33}");
    out.println(".failedeven td,.stripe .numi_attn {background-color: #D00}");
    out.println(".stacktrace {white-space:pre;font-family:monospace}");
    out.println(".totop {font-size:85%;text-align:center;border-bottom:2px solid #000}");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
  }

  /** Finishes HTML stream */
  protected void endHtml(PrintWriter out) {
    out.println("</body></html>");
  }

  // ~ Inner Classes --------------------------------------------------------
  /** Arranges methods by classname and method name */
  private class TestSorter implements Comparator<IInvokedMethod> {
    // ~ Methods -------------------------------------------------------------

    /** Arranges methods by classname and method name */
    @Override
    public int compare(IInvokedMethod o1, IInvokedMethod o2) {
//      System.out.println("Comparing " + o1.getMethodName() + " " + o1.getDate()
//          + " and " + o2.getMethodName() + " " + o2.getDate());
      return (int) (o1.getDate() - o2.getDate());
//      int r = ((T) o1).getTestClass().getName().compareTo(((T) o2).getTestClass().getName());
//      if (r == 0) {
//        r = ((T) o1).getMethodName().compareTo(((T) o2).getMethodName());
//      }
//      return r;
    }
  }
}