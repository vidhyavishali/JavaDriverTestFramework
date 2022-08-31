package com.javafxui.test.marathon.webFxElements.enums;

public enum WebFxTag {

	COMBO_BOX("combo-box"), HBOX("h-box");

	private String tagName;

	private WebFxTag(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}
}
