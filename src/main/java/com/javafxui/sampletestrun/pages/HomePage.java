package com.javafxui.sampletestrun.pages;

import com.aventstack.extentreports.ExtentTest;
import com.javafxui.sampletestrun.AppTestBase;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class HomePage extends AppTestBase {

	public HomePage(JavaDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public Object goToRole1HomePage() {

		switch (loginRole) {
		// return objects of home page based on login role
		default:
			return null;
		}

	}

}
