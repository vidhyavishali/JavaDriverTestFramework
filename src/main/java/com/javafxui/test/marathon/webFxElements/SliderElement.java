package com.javafxui.test.marathon.webFxElements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class SliderElement {

	RemoteWebElement slider;

	public SliderElement(WebElement element) {
		if (!element.getTagName().toString().equalsIgnoreCase("Slider"))
			throw new UnexpectedTagNameException("Slider", element.getTagName());

		this.slider = (RemoteWebElement) element;

	}

	/**
	 * Takes the size of the slider track and moves the pointer to the coordinate as
	 * per the given percentage [0,25,75,100]
	 * 
	 */
	public void moveSliderPointTo(int percentage, Actions actions) {
		List<WebElement> findElements2 = slider.findElements(By.cssSelector("*[styleClass*='track']"));
		WebElement sliderTrack = findElements2.get(0);
		Dimension size = sliderTrack.getSize();
		int width = size.getWidth();
		int xOffset = (int) (width * percentage) / 100;
		actions.moveToElement(sliderTrack, xOffset, 0).click().perform();

	}

}
