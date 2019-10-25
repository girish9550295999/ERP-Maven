package DriverFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.ScreenshotException;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.view.ScreenshotHtml;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;
import Utilities.ScreenShot;


public class DriverScript {
ExtentReports report;
ExtentTest test;
WebDriver driver;

@Test
//Create a Method
public void startTest()throws Throwable
{
	//creating reference object for excel util methods
ExcelFileUtil excel= new ExcelFileUtil();
	//iterating all row in Master Testcase sheet
for(int i=1; i<=excel.rowCount("MasterTestCases");i++)
	{
	String ModuleStatus="";
 	if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
 	{
	 //store module name into TCModule
	String TCModule=excel.getData("MasterTestCases", i, 1);
	
	//Generate html Report
	report=new ExtentReports("./Reports/"+TCModule+FunctionLibrary.generatedate()+".html");
	//iterate all rows in TCModule sheet
	for(int j=1;j<=excel.rowCount(TCModule);j++)
	{
	test=report.startTest(TCModule);
	//read all columns in TCModule testcase
	String Description=excel.getData(TCModule, j, 0);
	String Object_Type=excel.getData(TCModule, j, 1);
	String Locator_Type=excel.getData(TCModule, j, 2);
	String Locator_Value=excel.getData(TCModule, j, 3);
	String Test_Data=excel.getData(TCModule, j, 4);
	
	//calling methods from function library
	try
	{
if(Object_Type.equalsIgnoreCase("startBrowser"))
	{
	driver=FunctionLibrary.startBrowser(driver);
	System.out.println("Executing startbrowser");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("openApplication"))
	{
	FunctionLibrary.openApplication(driver);
	System.out.println("Executing openApplication");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("waitForElement"))
	{
	FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
	System.out.println("Executing waitforElement");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("typeAction"))
	{
	FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value, Test_Data);
	System.out.println("Executing typeAction");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("clickAction"))
	{
	FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
	System.out.println("Executing clickAction");
	test.log(LogStatus.INFO, Description);
	}
	else if(Object_Type.equalsIgnoreCase("closeBrowser"))
	{
	FunctionLibrary.closeBrowser(driver);
	System.out.println("Executing closeBrowser");
	test.log(LogStatus.INFO, Description);
	}
	else if (Object_Type.equalsIgnoreCase("captureData"))
	{
	FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
	test.log(LogStatus.INFO, Description);
	}
	else if (Object_Type.equalsIgnoreCase("tableValidation"))
	{
	FunctionLibrary.tableValidation(driver, Test_Data);	
	}
	else if (Object_Type.equalsIgnoreCase("stockCategories"))
	{
	FunctionLibrary.stockCategories(driver);
	test.log(LogStatus.INFO, Description);
	}
	else if (Object_Type.equalsIgnoreCase("tableValidation1")) 
	{
	FunctionLibrary.tableValidation1(driver, Test_Data);
	System.out.println("Executing tableValidation1");
	test.log(LogStatus.INFO, Description);
	}
	//write as pass into status column
	excel.setCellData(TCModule, j, 5, "PASS");
	
	//ScreenShot.PrntScrn(driver, Description);
	test.log(LogStatus.PASS, Description);
	ModuleStatus="true";	
	}
	catch(Exception e)
	{
	excel.setCellData(TCModule, j, 5, "FAIL");
	//ScreenShot.PrntScrn(driver, Description);
	test.log(LogStatus.FAIL, Description);
	ModuleStatus="false";
	System.out.println(e.getMessage());
	break;
	}
	if(ModuleStatus.equalsIgnoreCase("TRUE"))
	{
	excel.setCellData("MasterTestCases", i, 3, "Pass");
	}
	else if(ModuleStatus.equalsIgnoreCase("FALSE"))
	{
	excel.setCellData("MasterTestCases", i, 3, "Fail");
	}
	}
	report.flush(); //-- to push the reports to html
	report.endTest(test);
	}
	else
	{
	//write as not executed in status column for flag N
	excel.setCellData("MasterTestCases", i, 3, "Not Executed");
	 		}
		}
	}
}
