package com.javafxui.test.marathon.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.javafxui.test.appUtils.enums.Environment;
import com.javafxui.test.marathon.design.JarExecution;
import com.javafxui.test.marathon.design.WebFxElement;
import com.javafxui.test.marathon.utils.Reporter;
import com.javafxui.test.marathon.webFxElements.enums.FindBy;

import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javadriver.JavaProfile;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchMode;
import net.sourceforge.marathon.javadriver.JavaProfile.LaunchType;
import net.sourceforge.marathon.javafxagent.NoSuchWindowException;
import net.sourceforge.marathon.javafxagent.StaleElementReferenceException;

public class MarathonBase extends Reporter implements WebFxElement, JarExecution {

	public JavaDriver driver;
	public WebDriverWait wait;

	public void click(WebElement ele) {
		String text = "";
		try {
			wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			try {
				text = ele.getText();
			} catch (ClassCastException c) {

				text = "Cannot print Text due to exception :"+c;
			}
		
			ele.click();
			reportStep("The Element " + text + " clicked", "pass");
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + text + " could not be clicked", "fail");
			throw new RuntimeException();
		}
	}

	public void clickWithNoSnap(WebElement ele) {
		String text = "";
		try {
			text = ele.getText();
			wait = new WebDriverWait(driver, 30);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			ele.click();
			reportStep("The Element with text: " + text + " clicked", "pass", false);
		} catch (StaleElementReferenceException e) {
			reportStep("The Element " + ele + " could not be clicked", "fail");
			throw new RuntimeException();
		} catch (Exception e) {
			System.err.println(e);
		}

	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------------------
	 * Implementing Jar Execution Methods
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */

	@Override
	public JavaDriver startApp(String filePath) {
		try {
			JavaProfile profile = new JavaProfile(LaunchMode.EXECUTABLE_JAR);
			profile.setLaunchType(LaunchType.FX_APPLICATION);
			profile.setExecutableJar(filePath);
			driver = new JavaDriver(profile);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			// waitAndSwitchToNewWindow(driver);
		} catch (Exception e) {
			reportStep("The JAR Application could not be lauched.", "fail");
			throw new RuntimeException();
		}
		return driver;
	}

	@Override
	public WebElement locateElement(FindBy locatorType, String value) {
		try {
			switch (locatorType) {
			case ID:
				return driver.findElementById(value);
			case NAME:
				return driver.findElementByName(value);
			case STYLECLASS:
				return driver.findElementByCssSelector(value);
			case TAG:
				return driver.findElementByTagName(value);
			case TEXT:
				return driver.findElementByLinkText(value);

			}
		} catch (NoSuchElementException e) {
			reportStep("The Element with locator:" + locatorType.getAsString() + " Not Found with value: " + value,
					"fail");
			throw new RuntimeException();
		} catch (Exception e) {
			reportStep("The Element with locator:" + locatorType.getAsString() + " Not Found with value: " + value,
					"fail");
		}
		return null;
	}

	@Override
	public WebElement locateElement(String id) {
		WebElement findElementById;
		try {
			findElementById = driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			reportStep("The Element Not Found with id: " + id, "fail");
			throw new RuntimeException();
		}
		return findElementById;
	}

	@Override
	public List<WebElement> locateElements(FindBy locatorType, String value) {
		try {
			switch (locatorType) {
			case ID:
				return driver.findElementsById(value);
			case NAME:
				return driver.findElementsByName(value);
			case TAG:
				return driver.findElementsByTagName(value);
			case TEXT:
				return driver.findElementsByLinkText(value);
			case STYLECLASS:
				return driver.findElementsByCssSelector(value);
			}
		} catch (NoSuchElementException e) {
			System.err.println(
					"The Element with locator:" + locatorType.getAsString() + " Not Found with value: " + value);
			throw new RuntimeException();
		}
		return null;
	}

	@Override
	public void switchToWindow(int index) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			List<String> allhandles = new ArrayList<String>(allWindows);
			String exWindow = allhandles.get(index);
			driver.switchTo().window(exWindow);
			System.out.println("The Window With index: " + index + " switched successfully");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With index: " + index + " not found");
		}
	}

	@Override
	public void switchToWindow(String title) {
		try {
			Set<String> allWindows = driver.getWindowHandles();
			for (String eachWindow : allWindows) {
				driver.switchTo().window(eachWindow);
				if (driver.getTitle().equals(title)) {
					break;
				}
			}
			System.out.println("The Window With Title: " + title + "is switched ");
		} catch (NoSuchWindowException e) {
			System.err.println("The Window With Title: " + title + " not found");
		} finally {
			takeSnap();
		}
	}

	@Override
	public boolean verifyEnvironment(Environment environment) {
		if (driver.getTitle().contains(environment.getAsString())) {
			System.out.println("The environment: " + environment.getAsString() + " matched successfully");
			return true;
		} else {
			System.out.println("The environment: " + environment.getAsString() + " not matched");
		}
		return false;
	}

	@Override
	public boolean verifyVersion(String version) {
		if (driver.getTitle().contains(version)) {
			System.out.println("App Version: " + version + " matched successfully");
			return true;
		} else {
			System.out.println("App Version: " + version + " not matched");
		}
		return false;
	}

	@Override
	public void close() {
		driver.close();

	}

	@Override
	public void quit() {
		driver.quit();

	}

	@Override
	public void maximizeWindow() {
		driver.manage().window().maximize();
	}

	@Override
	public void waitImplicitlyFor(long seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);

	}

	@Override
	public void waitAndSwitchToNewWindow(long seconds) throws InterruptedException {
		Thread.sleep(seconds*1000);
		List<String> allhandles = new ArrayList<String>(driver.getWindowHandles());
		int index = allhandles.size() - 1;
		switchToWindow(index);
	}

	/**
	 * ----------------------------------------------------------------------------------------------------------------------------
	 * Implementing WebFxElement Methods
	 * ----------------------------------------------------------------------------------------------------------------------------
	 */
	@Override
	public void append(WebElement ele, String data) {
		ele.sendKeys(data);
	}

	@Override
	public void clear(WebElement ele) {
		try {
			ele.clear();
			reportStep("The field is cleared Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The field is not Interactable", "fail");
			throw new RuntimeException();
		}
	}

	@Override
	public void clearAndType(WebElement ele, String data) {
		try {
			ele.clear();
			ele.sendKeys(data);
			reportStep("The Data :" + data + " entered Successfully", "pass");
		} catch (ElementNotInteractableException e) {
			reportStep("The Element " + ele + " is not Interactable", "fail");
			throw new RuntimeException();
		}

	}

	@Override
	public String getElementText(WebElement ele) {
		String text = ele.getText();
		return text;
	}

	@Override
	public String getBackgroundColor(WebElement ele) {
		String cssValue = ele.getCssValue("color");
		return cssValue;
	}

	@Override
	public String getTypedText(WebElement ele) {
		String attributeValue = ele.getAttribute("value");
		return attributeValue;
	}

	@Override
	public boolean verifyExactText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().equals(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}

		return false;
	}

	@Override
	public boolean verifyPartialText(WebElement ele, String expectedText) {
		try {
			if (ele.getText().contains(expectedText)) {
				reportStep("The expected text contains the actual " + expectedText, "pass");
				return true;
			} else {
				reportStep("The expected text doesn't contain the actual " + expectedText, "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Text");
		}

		return false;
	}

	@Override
	public boolean verifyExactAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).equals(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
				return true;
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}
		return false;
	}

	@Override
	public void verifyPartialAttribute(WebElement ele, String attribute, String value) {
		try {
			if (ele.getAttribute(attribute).contains(value)) {
				reportStep("The expected attribute :" + attribute + " value contains the actual " + value, "pass");
			} else {
				reportStep("The expected attribute :" + attribute + " value does not contains the actual " + value,
						"fail");
			}
		} catch (WebDriverException e) {
			System.out.println("Unknown exception occured while verifying the Attribute Text");
		}

	}

	@Override
	public boolean verifyDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				reportStep("The element " + ele + " is visible", "pass");
				return true;
			} else {
				reportStep("The element " + ele + " is not visible", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;

	}

	@Override
	public boolean verifyDisappeared(WebElement ele) {
		return false;

	}

	@Override
	public boolean verifyEnabled(WebElement ele) {
		try {
			if (ele.isEnabled()) {
				reportStep("The element " + ele + " is Enabled", "pass");
				return true;
			} else {
				reportStep("The element " + ele + " is not Enabled", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		return false;
	}

	@Override
	public void verifySelected(WebElement ele) {
		try {
			if (ele.isSelected()) {
				reportStep("The element " + ele + " is selected", "pass");
				// return true;
			} else {
				reportStep("The element " + ele + " is not selected", "fail");
			}
		} catch (WebDriverException e) {
			System.out.println("WebDriverException : " + e.getMessage());
		}
		// return false;

	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * Other non-inherited methods
	 * -------------------------------------------------------------------------------------------
	 */

	public long takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L;
		try {
			FileUtils.copyFile(driver.getScreenshotAs(OutputType.FILE),
					new File("./reports/images/" + number + ".jpg"));
		} catch (WebDriverException e) {
			System.out.println("The browser has been closed.");
		} catch (IOException e) {
			System.out.println("The snapshot could not be taken");
		}
		return number;
	}

	/**
	 * creates a properties Object and loads the specified file from the resouce
	 * folder into the Object.
	 * 
	 * @throws IOException
	 */
	public Properties loadFromResource(String filePath) throws IOException {
		InputStream input = null;
		Properties prop = new Properties();
		input = ClassLoader.getSystemClassLoader().getResourceAsStream(filePath);
		prop.load(input);
		return prop;
	}

//	public  maximizeCurrentWindow(JavaDriver driver) {
//	}

}
