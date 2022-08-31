package com.javafxui.test.appUtils.enums;

public enum RoleType {

	ROLETYPE1(1, "Rolename1"),
	ROLETYPE2(2, "Rolename2"),
	ROLETYPE3(3, "Rolename3");

	String value;
	int index;

	RoleType(int index, String value) {

		this.index = index;
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public int getIndex() {
		return index;
	}


}
