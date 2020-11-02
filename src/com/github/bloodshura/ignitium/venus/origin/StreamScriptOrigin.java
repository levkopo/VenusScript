package com.github.bloodshura.ignitium.venus.origin;

import com.github.bloodshura.ignitium.resource.Resource;

import java.io.IOException;

public class StreamScriptOrigin implements ScriptOrigin {
	private final String name;
	private final Resource resource;

	public StreamScriptOrigin(String name, Resource resource) {
		this.name = name;
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	@Override
	public String getScriptName() {
		return name;
	}

	@Override
	public String read() throws IOException {
		String value = getResource().readString();
		value = value.replaceAll("\\r", "");

		if (value.startsWith("\uFEFF")) {
			value = value.substring(1);
		}

		return value;
	}

	@Override
	public String toString() {
		return "streamorigin(" + getScriptName() + ')';
	}
}
