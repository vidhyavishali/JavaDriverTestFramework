package com.javafxui.sampletestrun.pages;

import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentTest;
import com.javafxui.sampletestrun.AppTestBase;
import com.javafxui.test.marathon.webFxElements.enums.FindBy;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class LoginPage extends AppTestBase {

	public LoginPage(JavaDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public LoginPage enterUsername(String userName) throws InterruptedException {

		Thread.sleep(10000);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		waitAndSwitchToNewWindow(8);
		clearAndType(locateElement("userName"), userName);
		return this;
	}

	public LoginPage enterPassword(String passWord) {
		clearAndType(locateElement("password"), passWord);
		return this;

	}

	public RoleSelectPage clickLogin() {
		click(locateElement(FindBy.STYLECLASS, "button[text = 'Login']"));
		 return new RoleSelectPage(driver, node);

	}
}
