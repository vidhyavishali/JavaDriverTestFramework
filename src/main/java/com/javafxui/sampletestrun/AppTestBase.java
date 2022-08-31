package com.javafxui.sampletestrun;

import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import com.javafxui.sampletestrun.pages.SideBarPage;
import com.javafxui.test.appUtils.enums.RoleType;
import com.javafxui.test.marathon.base.MarathonBase;
import com.javafxui.test.marathon.utils.DataLibrary;

public class AppTestBase extends MarathonBase {

	public String dataSheetName;
	String filePath = "C:\\path\\to\\AppToTest.jar";
	public Properties coreProperties;
	public static RoleType loginRole;

	@DataProvider(name = "fetchData")
	public Object[][] fetchData() throws IOException {
		return DataLibrary.readExcelData(dataSheetName);
	}

	@DataProvider(name = "credentials")
	private Object[][] credentials() throws IOException {
		String[][] cred = new String[1][2];
		cred[0][0] = "sampleusername";
		cred[0][1] = "samplepassword";
		return cred;

	}

	@BeforeSuite
	public void loadProperties() {

		try {
			coreProperties = loadFromResource("bundle/propertyfile.properties"); // usually stored under
																					// src/main/resources/bundle

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = startApp(filePath);
		node = test.createNode(testCaseName);
	}

	@AfterMethod
	public void afterMethod() throws InterruptedException {
		Thread.sleep(60000);
		driver.close();
	}

	public SideBarPage getSideBar() {
		return new SideBarPage(driver, node);
	}

	/** NOTE: This method is just for reference to help further scripting. */
	public static void printAllDetails(WebElement element) {
		String con = "value";
		System.out.println("content :" + element.getAttribute("content"));
		System.out.println("value :" + element.getAttribute(con));
		try {
			System.out.println("Tagname :" + element.getTagName());
		} catch (Exception c) {

			System.out.println("The element is a pseudo element.");
		}
		System.out.println("Style class :" + element.getAttribute("StyleClass"));
//		System.out.println("IdValue :" + element.getAttribute("IdValue"));
		System.out.println("Name :" + element.getAttribute("name"));
		System.out.println("Children :" + element.getAttribute("children"));
		System.out.println("Parent :" + element.getAttribute("parent"));
		System.out.println("Class :" + element.getAttribute("class"));
		System.out.println("Items :" + element.getAttribute("items"));
		try {
			System.out.println("Text :" + element.getText());
		} catch (ClassCastException c) {

			System.out.println("Cannot print Text due to exception");
		}
	}

}
