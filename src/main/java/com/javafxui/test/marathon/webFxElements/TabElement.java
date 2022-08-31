package com.javafxui.test.marathon.webFxElements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.javafxui.test.marathon.webFxElements.enums.WebFxElementAttribute;

public class TabElement {

	RemoteWebElement tabContainer;

	Actions actions;

	List<String> availableOptions = new ArrayList<String>();

	public TabElement(WebElement element, Actions actions) {
		if (!element.getTagName().toString().equalsIgnoreCase("tab-pane")
				|| !element.getAttribute("StyleClass").toLowerCase().contains("jfx-tab-pane"))
			throw new UnexpectedTagNameException("tab-pane", element.getTagName());

		this.tabContainer = (RemoteWebElement) element;
		String content = tabContainer.getAttribute(WebFxElementAttribute.CONTENT.getAttribute());
		parseContents(content);
		this.actions = actions;
	}

	public void selectTab(int k) {

		for (int i = 1; i <= k; i++)
			actions.click(tabContainer).keyDown(Keys.CONTROL).sendKeys(Keys.TAB).build().perform();

	}

	public void selectTab(String name) {

		int tabIndex = availableOptions.indexOf(name);
		selectTab(tabIndex + 1);
	}

	public List<String> getAllTabs() {

		return availableOptions;
	}

	private void parseContents(String content) {
		System.out.println(content);
		content = content.replace("\",\"", "split");
		content = content.replace("[[\"", "");
		content = content.replace("\"]]", "");
		content.trim();
		String[] split = content.split("split");
		availableOptions.addAll(Arrays.asList(split));
	}

}
