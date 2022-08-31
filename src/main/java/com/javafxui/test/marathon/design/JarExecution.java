package com.javafxui.test.marathon.design;

import java.net.MalformedURLException;
import java.util.List;

import org.openqa.selenium.WebElement;

import com.javafxui.test.appUtils.enums.Environment;
import com.javafxui.test.marathon.webFxElements.enums.FindBy;

import net.sourceforge.marathon.javadriver.JavaDriver;
import net.sourceforge.marathon.javafxagent.NoSuchElementException;
import net.sourceforge.marathon.javafxagent.NoSuchWindowException;

public interface JarExecution {
	
	/**
	 * This method will launch the specified jar file and 
	 * maximise the screen and set the wait for 30 seconds 
	 * @param path - path to the jar file 
	 * @author Vidhya PI
	 * @return 
	 * @throws MalformedURLException 
	 */	
	public JavaDriver startApp(String url);
	
	/**
	 * This method will locate the element using any given locator
	 * @param locatorType  - The locator by which the element to be found
	 * @param locValue - The locator value by which the element to be found
		 * @author vv.karumanassey
	 * @throws NoSuchElementException
	 * @return The first matching element on the current context.
	 */
	public WebElement locateElement(FindBy locatorType, String value);	
	
	/**
	 * This method will locate the element using id
	 * @param locValue - The locator value by which the element to be found
		 * @author vv.karumanassey
	 * @throws NoSuchElementException
	 * @return The first matching element on the current context.
	 */
	public WebElement locateElement(String value);
	
	/**
	 * This method will locates all matching element using any given locator
	 * @param locatorType  - The locator by which the element to be found
	 * @param locValue - The locator value by which the element to be found
		 * @author vv.karumanassey
	 * @return A list of all WebElements, or an empty list if nothing matches.
	 */
	public List<WebElement> locateElements(FindBy locatorType, String value);	
	
	
	/**
	 * This method will switch to the Window of interest
	 * @param index The window index to be switched to. 0 -> first window 
		 * @author vv.karumanassey
	 * @throws NoSuchWindowException
	 */
	public void switchToWindow(int index);
	
	/**
	 * This method will switch to the Window of interest using its title
	 * @param title The window title to be switched to first window 
		 * @author vv.karumanassey
	 * @throws NoSuchWindowException
	 */
	public void switchToWindow(String title);

	
	/**
	 * This method will verify weather the application is running is expected environment
	 * @param environment   - The environment to be checked
		 * @author vv.karumanassey
	 * @return true if the given object represents a String equivalent to this environment, false otherwise
	 */
	public boolean verifyEnvironment(Environment environment);
	
	/**
	 * This method will verify the version of the software from the window title bar with expected version
	 * @param title - The expected title of the window
		 * @author vv.karumanassey
	 * @return true if the given object represents a String equivalent to this title, false otherwise
	 */
	public boolean verifyVersion(String version);
	
	
	/**
	 * This method will close the active browser
		 * @author vv.karumanassey
	 */
	public void close();
	
	/**
	 * This method will close all the browsers
		 * @author vv.karumanassey
	 */
	public void quit();
	
	/**
	 * This method will maximize the app window
	 * @author vv.karumanassey
	 */
	public void maximizeWindow();
	
	/**
	 * This method wait implicitly for specified seconds
	 * @author vv.karumanassey
	 */
	public void waitImplicitlyFor(long seconds);
	
	/**
	 * This method provides a wait of 5 seconds before switching to newly opened window
	 * @author vv.karumanassery
	 * */
	public void waitAndSwitchToNewWindow(long seconds) throws InterruptedException;
	

}
