package com.javafxui.test.marathon.webFxElements;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import com.javafxui.test.marathon.webFxElements.enums.WebFxElementAttribute;
import com.javafxui.test.marathon.webFxElements.enums.WebFxTag;

public class MultiSelectComboBoxElement {

	RemoteWebElement multiElementSelector;

	RemoteWebElement comboBox;

	List<WebElement> selectedOptions = new ArrayList<WebElement>();

	List<String> availableOptions = new ArrayList<String>();

	public MultiSelectComboBoxElement(WebElement element) {
		String styleClassName = element.getAttribute(WebFxElementAttribute.STYLECLASS.getAttribute());
		if (styleClassName == null || !styleClassName.contains("multiple-element-selector")) {

			throw new UnexpectedTagNameException("mutiple-element-selector", styleClassName);

		}

		WebElement comboBoxElem = multiElementSelector.findElementByTagName(WebFxTag.COMBO_BOX.getTagName());
		comboBox = (RemoteWebElement) comboBoxElem;

		collectSelectedOptions();

	}

	private void collectSelectedOptions() {
		List<String> selectedOptions = new ArrayList<String>();
		String styleClass1 = "selected-element-container";
		RemoteWebElement flowpane = (RemoteWebElement) multiElementSelector
				.findElementByCssSelector("*[styleClass*='" + styleClass1 + "']");

		List<WebElement> hboxes = flowpane.findElementsByTagName(WebFxTag.HBOX.getTagName());

		for (WebElement hbox : hboxes) {
			// printAllDetails(hbox);
			String styleClass2 = "label";
			WebElement label = ((RemoteWebElement) hbox)
					.findElementByCssSelector("*[styleClass*='" + styleClass2 + "']");
			selectedOptions.add(label.getAttribute("text"));
		}

	}

	public List<String> getAllAvailableOptions() {
		String content = comboBox.getAttribute(WebFxElementAttribute.CONTENT.getAttribute());
		parseContents(content);
		return availableOptions;
	}

	private void parseContents(String content) {
		content = content.replace("\",\"", "split");
		content = content.replace("[[\"", "");
		content = content.replace("\"]]", "");
		content = content.replace(" ", "");
		content = content.replace("IdValue", "");
		content.trim();
		String[] split = content.split("split");
		for (String con : split) {
			int startIndex = con.indexOf(",value") + 6; // 6 -> indicates the value to be adjusted for "value" keyword
														// in the content
			CharSequence value = con.subSequence(startIndex + 1, con.length() - 1);
			availableOptions.add((String) value);
		}

	}

//	public WebFxMultiElementSelector(WebElement element) {
//
//		String styleClassName = element.getAttribute("StyleClass");
//		if (null == styleClassName || !styleClassName.contains("multiple-element-selector")) {
//			throw new UnexpectedTagNameException("mutiple-element-selector", styleClassName);
//		} else
//			this.multiElementSelector = element;
//	}
//
//	public List<String> getAllAvailableOptions() {
//
//		List<String> availableOptions = new ArrayList<String>();
//		WebElement comboBox = (WebElement) multiElementSelector.findElementByTagName(WebFxTag.COMBO_BOX);
////		List<WebElement> findElements = ele.findElements(By.tagName("combo-box"));
////		WebElement comboBox = findElements.get(0);
//
//		String content = comboBox.getElementAttribute(WebElementAttribute.CONTENT);
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
//			availableOptions.add((String) value);
//		}
//		return availableOptions;
//	}
//
//	public List<String> getAllSelectedOptions() {
//		List<String> selectedOptions = new ArrayList<String>();
//		WebElement flowpane = multiElementSelector
//				.findElementByCssSelector("*[styleClass*='selected-element-container']");
//
//		List<WebElement> hboxes = flowpane.findAllElementsByTagName(WebFxTag.HBOX);
//
//		for (WebElement hbox : hboxes) {
//			// printAllDetails(hbox);
//			WebElement label = hbox.findElement(By.cssSelector("*[styleClass*='label']"));
//			selectedOptions.add(label.getAttribute("text"));
//		}
//		return selectedOptions;
//	}
//
//	private static void selectOptionsByIndex(List<Integer> optionsList) {
//		List<WebElement> findElements = layerSelect.findElements(By.tagName("combo-box"));
//		WebElement comboBox = findElements.get(0);
//		comboBox.click();
//
//		for (Integer k : optionsList) {
//			comboBox.findElement(By.cssSelector(".::nth-option(" + k + ")")).click();
//		}
//		comboBox.click();
//	}

}
