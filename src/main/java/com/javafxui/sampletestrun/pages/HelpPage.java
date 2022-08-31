package com.javafxui.sampletestrun.pages;

import com.aventstack.extentreports.ExtentTest;
import com.javafxui.sampletestrun.AppTestBase;

import net.sourceforge.marathon.javadriver.JavaDriver;

public class HelpPage extends AppTestBase {

	public HelpPage(JavaDriver driver, ExtentTest node) {
		this.driver = driver;
		this.node = node;
	}

	public Object goToRole1HelpPage() {

		switch (loginRole) {

		default:
			return null;
		}

	}
}
