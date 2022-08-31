package com.javafxui.test.appUtils.enums;

public enum Environment {
	
	DEVELOPMENT("develop"), STAGING("staging"), TEST("test");
	
	private String environment;

	private Environment(String environment) {
		this.environment = environment;
	}

	public String getAsString() {
		return environment;
	}

}
