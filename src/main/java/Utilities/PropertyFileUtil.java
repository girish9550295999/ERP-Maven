package Utilities;

import java.io.FileInputStream;
import java.util.Properties;


public class PropertyFileUtil {
public static String getvalueForKey(String key) throws Throwable
{
	Properties configProp=new Properties();
	FileInputStream fis=new FileInputStream("D:\\mrng930batch\\ERP_Maven\\PropertyFile\\Environment.properties");
	configProp.load(fis);
	return configProp.getProperty(key);
}

}






