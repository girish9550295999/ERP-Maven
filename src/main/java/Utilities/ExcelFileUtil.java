package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
Workbook wb;
//To read excel path
public ExcelFileUtil () throws Throwable
{
	FileInputStream fis=new FileInputStream("D:\\mrng930batch\\ERP_Maven\\TestInput\\InputSheet.xlsx");
	wb=WorkbookFactory.create(fis);
}
//Count number of rows from the excel sheet
public int rowCount (String sheetname)
{
	return wb.getSheet(sheetname).getLastRowNum();
}
//Count Column in a row
public int colCount (String sheetname)
{
	return wb.getSheet(sheetname).getRow(0).getLastCellNum();
}
//Get cell data from the sheet
public String getData(String sheetname, int row, int column) 
{
	String data="";
if (wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
{
int celldata=(int)wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
//Convert cell data numeric column in to String
data=String.valueOf(celldata);
}else
{
	data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
}
return data;
}
//To write in to workbook
public void setCellData(String sheetname, int row, int coloumn, String status) throws Throwable
{
//Get Sheet from Workbook
Sheet ws=wb.getSheet(sheetname);
//Get row from the sheet
Row rownum=ws.getRow(row);
//Create Cell in a row
Cell cell=rownum.createCell(coloumn);
//Write the status in to the cell
cell.setCellValue(status);
if (status.equalsIgnoreCase("Pass"));
{
//Create a Cell style
CellStyle style=wb.createCellStyle();
//Create a font
Font font=wb.createFont();
//Apply Colour to the text
font.setColor(IndexedColors.GREEN.getIndex());
//Format the text in to Bold
font.setBold(true);
//Set Font
style.setFont(font);
//Set Cell Style
rownum.getCell(coloumn).setCellStyle(style);
}
if (status.equalsIgnoreCase("Fail"));
{
//Create a Cell style
CellStyle style=wb.createCellStyle();
//Create a font
Font font=wb.createFont();
//Apply Colour to the text
font.setColor(IndexedColors.RED.getIndex());
//Format the text in to Bold
font.setBold(true);
//Set Font
style.setFont(font);
//Set Cell Style
rownum.getCell(coloumn).setCellStyle(style);
}
if (status.equalsIgnoreCase("Not Executed"));
{
	
//Create a Cell style
CellStyle style=wb.createCellStyle();
//Create a font
Font font=wb.createFont();
//Apply Colour to the text
font.setColor(IndexedColors.BLUE.getIndex());
//Format the text in to Bold
font.setBold(true);
//Set Font
style.setFont(font);

//Set Cell Style
rownum.getCell(coloumn).setCellStyle(style);
}
FileOutputStream fos=new FileOutputStream("D:\\mrng930batch\\ERP_Maven\\TestOutput\\Maven.xlsx");
wb.write(fos);
fos.close();
//wb.close(); -- should not close the workbook with in the loop (so as to continue with the next iterations)
}	
}
