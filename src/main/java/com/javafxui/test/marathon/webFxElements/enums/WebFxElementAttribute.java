package com.javafxui.test.marathon.webFxElements.enums;

public enum WebFxElementAttribute {

	NAME("name"), VALUE("value"), CONTENT("content"), STYLECLASS("StyleClass"), ID_VALUE("IdValue"),
	CHILDREN("children"), TEXT("text");

	private String attribute;

	private WebFxElementAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

}
