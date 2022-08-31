package com.javafxui.test.marathon.webFxElements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.javafxui.test.marathon.webFxElements.enums.WebFxElementAttribute;



public class CheckComboBox {

	RemoteWebElement comboBox;

	List<WebElement> items = new ArrayList<WebElement>();

	List<String> listOfOptions = new ArrayList<String>();

	public CheckComboBox(WebElement element) {
		if (!element.getAttribute("StyleClass").toLowerCase().contains("combo-box")) {
			System.out.println(element.getAttribute("StyleClass"));
			throw new UnexpectedTagNameException("combo-box", element.getTagName());
		}

		this.comboBox = (RemoteWebElement) element;

		collectItems();

	}

	public List<String> getListOfVisibleOptions() {
		List<String> optionsAsString = new ArrayList<String>();

		for (int i = 1; i <= items.size(); i++) {
			selectOptionByIndex(i);
			WebElement listCell = comboBox.findElement(By.cssSelector(":instance-of('javafx.scene.control.ListCell')"));
			optionsAsString.add(listCell.getAttribute("text"));
		}
		return optionsAsString;

	}

	public void selectOptionByValue(String value) {
		for (int i = 1; i <= items.size(); i++) {
			selectOptionByIndex(i);
			WebElement listCell = comboBox.findElement(By.cssSelector(":instance-of('javafx.scene.control.ListCell')"));
			System.out.println(listCell.getAttribute("text") + " against " + value);
			if (listCell.getAttribute("text").toLowerCase().contains(value.toLowerCase()))
				break;
			if (i == items.size())
				throw new NoSuchElementException("Value :" + value + " not listed in the combo box");
		}

	}

	public int numberOfOptions() {
		return items.size();
	}

	public void collectItems() {
		items = comboBox.findElements(By.cssSelector(".::all-options"));

	}

	public List<WebElement> getItems() {
		return items;
	}

	public List<String> getListOfOptions() {
		String content = comboBox.getAttribute("items");
		content = content.replace("\",\"", "split");
		content = content.replace("[", "");
		content = content.replace("]", "");
		content = content.replace(" ", "");
		content.trim();
		String[] split = content.split("split");
		listOfOptions = Arrays.asList(split);
		return listOfOptions;
	}

	/**
	 * Selects the element from combo box with its index value. Starts as 1 - n
	 */
	public void selectOptionByIndex(int k) {

		WebElement optionElement = fetchOptionByIndex(k);
		optionElement.click();

	}

	public WebElement fetchOptionByIndex(int k) {
		WebElement optionElement = comboBox.findElement(By.cssSelector(".::nth-option(" + k + ")"));
		return optionElement;
	}

	public void clickOnNthElement(Actions actions, int option) throws InterruptedException {

		for (int i = 1; i <= option; i++) {

			actions.sendKeys(Keys.DOWN).build().perform();// press down arrow key
			Thread.sleep(1000);
		}
		actions.sendKeys(Keys.ENTER).build().perform();// press enter
	}

	public String getSelectedOption() {
		return comboBox.getAttribute(WebFxElementAttribute.TEXT.getAttribute());
	}

//	public List<WebElement> getAllOptionElements() {
//
//		// parseContents(content);
//		collectOptionElements();
//		return optionElements;
//	}

//	private void collectOptionElements() {
//		int k = 1;
//		WebElement optionElement = comboBox.findElement(By.cssSelector(".::nth-option(" + k + ")"));
//
//		while (optionElement != null) {
//			try {
//				/* Using getText, to catch exception at which index the list ends. */
//				if (optionElement.getText() != null)
//					optionElements.add(optionElement);
//				k++;
//				optionElement = comboBox.findElement(By.cssSelector(".::nth-option(" + k + ")"));
//			} catch (ClassCastException e) {
//				break;
//			}
//		}
//
//	}

//	public void collectElementsByListCell() {
//		comboBox.findElements(By.cssSelector("*[styleClass*='list-cell']"));
//
//	}

//	public void parseContents() {
//
//		String content = comboBox.getAttribute(WebFxElementAttribute.CONTENT.getAttribute());
//		content = content.replace("\",\"", "split");
//		content = content.replace("[[\"", "");
//		content = content.replace("\"]]", "");
//		content = content.replace(" ", "");
//		content = content.replace("IdValue", "");
//		content.trim();
//		String[] split = content.split("split");
//		for (String con : split) {
//			int startIndex = con.indexOf(",value") + 6; // 6 -> indicates the value to be adjusted for "value" keyword
//														// in the content
//			CharSequence value = con.subSequence(startIndex + 1, con.length() - 1);
//			listOfOptions.add((String) value);
//		}
//
//	}

}
