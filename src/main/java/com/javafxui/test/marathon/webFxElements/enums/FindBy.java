package com.javafxui.test.marathon.webFxElements.enums;

public enum FindBy {
	
	ID("id"), NAME("name"), STYLECLASS("StyleClass"), TEXT("text"), TAG("Tag");
	
	private String locator;

	private FindBy(String locator) {
		this.locator = locator;
	}

	public String getAsString() {
		return locator;
	}

}
