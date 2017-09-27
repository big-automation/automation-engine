package AppiumDemo;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SeleniumPageRankDemo {

	private static WebDriver driver = null;
	private static WebElement element = null;

	public static void main(String[] args) {

		
		// create a scanner so we can read the command-line input
	    Scanner scanner = new Scanner (new InputStreamReader(System.in));

	    //  prompt user to Enter the search criteria in Google
	    System.out.print("Enter your search keyword: \n");

	    // get their input as a String
	    String keyword = scanner.nextLine();

	    // prompt user to Enter the website to search
	    System.out.print("Enter the target Website: \n");
	    
	    // get their input as a String
	    String websiteName = scanner.nextLine();
	    
	    // prompt user to Enter the number of pages to search
	    System.out.print("Enter Total number of pages to search: \n");

	    // get the page number as an int
	    int pageNo = scanner.nextInt();
	    
	    driver = new FirefoxDriver();
		Boolean found = false;
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.google.com");

		element = driver.findElement(By.id("lst-ib"));
		//element.sendKeys("Selenium WebDriver Interview questions");
		element.sendKeys(keyword);
		element.sendKeys(Keys.RETURN);
		int page = 0;
		while (!found && page<=pageNo){
		try{
		List<WebElement> list = driver.findElements(By.className("_Rm"));
		//System.out.println(list.size());
		Thread.sleep(10000);
		page++;
		for(int i=0;i<list.size();i++)
		{
		String link = list.get(i).getText();
		//System.out.println(link);
		if(link.contains(websiteName))
		{
			System.out.println("Website Found at Page" + page);
			found = true;
			fnHighlightMe(driver,list.get(i));
			break;
		}
		/*else 
		{
			System.out.println("Website Not Found in page" + page);	
		}	*/
		}
		}catch (Exception e)
		{
			   System.out.println(e);
		}
		
		if (!found) {
			try {
		         driver.findElement(By.xpath(".//*[@id='pnnext']/span[2]")).click();
		         Thread.sleep(10000);
			}catch (Exception e)
			{
				   System.out.println(e);
			}
		
		//driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		}
		}
        //driver.close();
	}
	
	 public static void fnHighlightMe(WebDriver driver,WebElement element) throws InterruptedException{
		  //Creating JavaScriptExecuter Interface
		   JavascriptExecutor js = (JavascriptExecutor)driver;
		   js.executeScript("arguments[0].scrollIntoView();", element);
		   //for (int iCnt = 0; iCnt < 3; iCnt++) {
		      //Execute javascript
		         //js.executeScript("arguments[0].style.border='4px groove green'", element);
		   js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element, "color: red; border: 3px solid red;");
		         Thread.sleep(1000);
		     //    js.executeScript("arguments[0].style.border=''", element);
		   //}
		 }
	
}