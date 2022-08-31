package com.javafxui.sampletestrun.pages;

import com.aventstack.extentreports.ExtentTest;
import com.javafxui.sampletestrun.AppTestBase;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class SideBarPage extends AppTestBase {

	public SideBarPage(JavaDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public HomePage clickOnHomeIcon() throws InterruptedException {
		locateElement("homepagebutton").click();
		waitAndSwitchToNewWindow(2);
		return new HomePage(driver, node);
	}

	public HelpPage clickOnHelpIcon() throws InterruptedException {
		click(locateElement("HelpButton"));
		waitAndSwitchToNewWindow(2);
		return new HelpPage(driver, node);
	}

	public void clickOnCalenderIcon() {
		// write similar locator scripts for each of the element in this page.
	}

}
