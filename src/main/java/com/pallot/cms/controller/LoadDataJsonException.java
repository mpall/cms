package com.pallot.cms.controller;

import org.codehaus.jackson.JsonParseException;

public class LoadDataJsonException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String url;
	private String location;
	private String content;

	public LoadDataJsonException(String url, String location, String content, JsonParseException e) {
		super(e);
		this.url = url;
		this.location = location;
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public String getLocation() {
		return location;
	}

	public String getContent() {
		return content;
	}

}
