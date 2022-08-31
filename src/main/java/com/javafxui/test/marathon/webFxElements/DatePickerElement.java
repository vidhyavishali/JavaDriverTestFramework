package com.javafxui.test.marathon.webFxElements;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

public class DatePickerElement {

	RemoteWebElement datePicker;

	DateTimeFormatter formatter;

	public DatePickerElement(WebElement element) {
		if (!element.getTagName().toString().equalsIgnoreCase("date-picker")
				|| !element.getAttribute("StyleClass").toLowerCase().contains("date-picker"))
			throw new UnexpectedTagNameException("date-picker", element.getTagName());

		this.datePicker = (RemoteWebElement) element;
		formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		formatter.withLocale(Locale.GERMAN);

	}

	public LocalDate getValue() throws ParseException {

		String dateText = datePicker.getAttribute("Text");

		LocalDate asDate = parseToLocalDate(dateText);

		return asDate;

	}

	public LocalDate parseToLocalDate(String dateText) {

		return LocalDate.parse(dateText, formatter);
	}

	public void setValue(String date) throws InterruptedException {

		WebElement dateTextField = datePicker.findElement(By.cssSelector("*[styleClass*='jfx-text-field']"));
		dateTextField.clear();
		Thread.sleep(2000);
		System.out.println("setting date:" +date);
		dateTextField.sendKeys(date, Keys.ENTER);

	}

	public void setValue(LocalDate date) throws InterruptedException {

		String dateText = toString(date);
		setValue(dateText);

	}

	public String toString(LocalDate object) {
		if (object == null)
			return null;
		return formatter.format(object);
	}

	/**
	 * Provide the plus days in negative to go back to past dates
	 * @throws InterruptedException 
	 */
	public void setDateTo(LocalDate givenDate, int nDays, int nMonths, int nYears) throws InterruptedException {

		if (nYears >= 0) {
			givenDate = givenDate.plusYears(nDays);
		} else
			givenDate = givenDate.minusYears(nDays);
		if (nMonths >= 0) {
			givenDate = givenDate.plusMonths(nDays);
		} else
			givenDate = givenDate.minusMonths(nDays);
		if (nDays >= 0) {
			givenDate = givenDate.plusDays(nDays);
		} else
			givenDate = givenDate.minusDays(nDays);

		setValue(givenDate);
	}

	public void setDateTo(String dateText, int nDays, int nMonths, int nYears) throws InterruptedException {
		setDateTo(parseToLocalDate(dateText), nDays, nMonths, nYears);
	}

	// Get The Current Day
	public static String getCurrentDay() {
		// Create a Calendar Object
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		// Get Current Day as a number
		int todayInt = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println("Today Int: " + todayInt + "\n");
		// Integer to String Conversion
		String todayStr = Integer.toString(todayInt);
		System.out.println("Today Str: " + todayStr + "\n");
		return todayStr;
	}

	// Click to given day
	public static void clickGivenDay(List<WebElement> elementList, String day) {
		// DatePicker is a table. Thus we can navigate to each cell
		// and if a cell matches with the current date then we will click it.
		/** Functional JAVA version of this method. */
		elementList.stream().filter(element -> element.getText().contains(day)).findFirst()
				.ifPresent(WebElement::click);
		/** Non-functional JAVA version of this method. */
		// for (
		// WebElement cell : elementList) {
		// String cellText = cell.getText();
		// if (cellText.contains(day)) {
		// cell.click();
		// break;
		// }
		// }
	}
}
