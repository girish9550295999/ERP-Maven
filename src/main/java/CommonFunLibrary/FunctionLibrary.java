package CommonFunLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import Utilities.PropertyFileUtil;

public class FunctionLibrary {
public static WebDriver driver;
private static String Act_data;

//Method for browser launching
public static WebDriver startBrowser(WebDriver driver) throws Throwable
{
if(PropertyFileUtil.getvalueForKey("Browser").equalsIgnoreCase("chrome"))
{
	System.setProperty("webdriver.chrome.driver", "./CommonJars/chromedriver.exe");
	driver=new ChromeDriver();
}
else if(PropertyFileUtil.getvalueForKey("Browser").equalsIgnoreCase("firefox"))
{
}
else if(PropertyFileUtil.getvalueForKey("Browser").equalsIgnoreCase("ie"))
{
}
else
{
System.out.println("No Browser is matching");
}
return driver;
}

//Launch Url -- by defining web driver as a object (so as to override the values)

public static void openApplication(WebDriver driver) throws Throwable
{
	driver.get(PropertyFileUtil.getvalueForKey("Url"));
	driver.manage().window().maximize();
}

//Method for waitForElement
public static void waitForElement(WebDriver driver, String locatortype, String locatorvalue, String waittime)
{
WebDriverWait mywaittime=new WebDriverWait(driver, Integer.parseInt(waittime));
if (locatortype.equalsIgnoreCase("id"))
{
mywaittime.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
}
else if (locatortype.equalsIgnoreCase("name"))
{
mywaittime.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
}
else if (locatortype.equalsIgnoreCase("xpath"))
{
mywaittime.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));	
}
else
{
System.out.println("Unable to wait for Element");
}
}

//Method for Type Action
public static void typeAction(WebDriver driver, String locatortype, String locatorvalue, String testdata)
{
if (locatortype.equalsIgnoreCase("id"))
{
driver.findElement(By.id(locatorvalue)).clear();
driver.findElement(By.id(locatorvalue)).sendKeys(testdata);
}	
else if (locatortype.equalsIgnoreCase("name"))
{
driver.findElement(By.name(locatorvalue)).clear();
driver.findElement(By.name(locatorvalue)).sendKeys(testdata);	
}
else if (locatortype.equalsIgnoreCase("xpath"))
{
driver.findElement(By.xpath(locatorvalue)).clear();
driver.findElement(By.xpath(locatorvalue)).sendKeys(testdata);	
}
else
{
System.out.println("Unable to execute typeaction method");	
}
}

//Method for Click Action
public static void clickAction(WebDriver driver, String locatortype, String locatorvalue) {
{
if (locatortype.equalsIgnoreCase("id"))
{
	driver.findElement(By.id(locatorvalue)).sendKeys(Keys.ENTER);
}
else if (locatortype.equalsIgnoreCase("name"))
{
	driver.findElement(By.name(locatorvalue)).click();	
}
else if (locatortype.equalsIgnoreCase("xpath"))
{
	driver.findElement(By.xpath(locatorvalue)).click();
}
else {
	System.out.println("Unable to execute Click Action");
}
}
}
//Method for Closing the browser
public static void closeBrowser(WebDriver driver)
{
	driver.close();
}

//Method for Date stamp (used for extent reports/screenshots)
public static String generatedate()
{
	Date date=new Date();
	SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
	return sdf.format(date);
}

//Capture data in to Notepad
public static void captureData(WebDriver driver, String locatortype, String locatorvalue) throws Throwable
{
	String supplierdata=null;
	if (locatortype.equalsIgnoreCase("id"))
	{
	supplierdata=driver.findElement(By.id(locatorvalue)).getAttribute("value");	
	}
	else if (locatortype.equalsIgnoreCase("name"))
	{
	supplierdata=driver.findElement(By.name(locatorvalue)).getAttribute("value");		
	}
	else if (locatortype.equalsIgnoreCase("xpath"))
	{
	supplierdata=driver.findElement(By.xpath(locatorvalue)).getAttribute("value");
	}
	FileWriter fr=new FileWriter("D:\\mrng930batch\\ERP_Maven\\CaptureData\\Supplier.txt");
BufferedWriter bw=new BufferedWriter(fr);
bw.write(supplierdata);
bw.flush();
bw.close();
}

// Table validation for Supplier Creation
public static void tableValidation(WebDriver driver, String column) throws Throwable
{
	FileReader fr=new FileReader("D:\\mrng930batch\\ERP_Maven\\CaptureData\\Supplier.txt");
	BufferedReader br=new BufferedReader(fr);
	String Exp_data=br.readLine();
	
	//Convert column in to Integer for selenium to recognise the column number 6 (integer) mentioned in the test data sheet -- table validation will be on integer & selenium recognizes string
	int column6=Integer.parseInt(column);
	if (driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box"))).isDisplayed())
	{
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box"))).clear();	
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("click-search"))).click();
	Thread.sleep(5000);
	}
	else
	{
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("Click-searchpanel"))).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box"))).clear();	
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box"))).sendKeys(Exp_data);
		driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("click-search"))).click();
	Thread.sleep(5000);	
	}

//Table Validation
	WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("sup-table")));
	List<WebElement>rows=table.findElements(By.tagName("tr"));
	System.out.println("No of rows are::"+rows.size());
	for (int i = 1; i <= rows.size(); i++)
	{
	String Act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column6+"]/div/span/span")).getText();
	Thread.sleep(5000);
	Assert.assertEquals(Exp_data, Act_data, "Supplier Number is not matching");
	System.out.println(Exp_data+" "+Act_data);
	break;
	}
}

//Mouse Click for Stock Items
public static void stockCategories(WebDriver driver) throws Throwable
{
	Actions ac=new Actions(driver);
	ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_items']//a[contains(text(),'Stock Items')]"))).perform();
	Thread.sleep(2000);
	ac.moveToElement(driver.findElement(By.xpath("//li[@id='mi_a_stock_categories']//a[contains(text(),'Stock Categories')]"))).click().perform();
	Thread.sleep(3000);
}

//Table Validation for Stock Categories
public static void tableValidation1(WebDriver driver, String Exp_data) throws Throwable
{
if (driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box1"))).isDisplayed())
{
//System.out.println("element displayed");
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box1"))).clear();
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box1"))).sendKeys(Exp_data);
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("click-search1"))).click();
}
else
{
//System.out.println("element not displayed");
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("Click-searchpanel1"))).click();
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box1"))).clear();
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("search-box1"))).sendKeys(Exp_data);
driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("click-search1"))).click();
}
WebElement table=driver.findElement(By.xpath(PropertyFileUtil.getvalueForKey("cat-table")));
List<WebElement>rows=table.findElements(By.tagName("tr"));
System.out.println("No of rows are::"+rows.size());
for (int i = 1; i<=rows.size(); i++)
{

//Get Table text from a column
String Act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
Thread.sleep(3000);
Assert.assertEquals(Exp_data, Act_Data, "Data not found");
System.out.println(Exp_data+" "+Act_data);
break;
}
}
}



