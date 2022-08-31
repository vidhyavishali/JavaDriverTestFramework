package com.javafxui.sampletestrun.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;
import com.javafxui.sampletestrun.AppTestBase;
import com.javafxui.test.appUtils.enums.RoleType;
import com.javafxui.test.marathon.webFxElements.ComboBoxElement;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class RoleSelectPage extends AppTestBase {

	public RoleSelectPage(JavaDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public SideBarPage selectRole(RoleType roleType) throws InterruptedException {

		waitAndSwitchToNewWindow(2);
		WebElement rolechoose = driver.findElement(By.id("roles"));
		ComboBoxElement rcCombo = new ComboBoxElement(rolechoose);
		rcCombo.selectOptionByIndex(roleType.getIndex());
		loginRole = roleType;
		driver.findElement(By.cssSelector("button[text = 'OK']")).click();
		maximizeWindow();
		waitAndSwitchToNewWindow(4);
		return new SideBarPage(driver, node);
	}
}
