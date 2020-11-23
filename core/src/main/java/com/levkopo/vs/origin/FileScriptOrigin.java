package com.levkopo.vs.origin;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileScriptOrigin implements ScriptOrigin {
	private final File file;

	public FileScriptOrigin(File file) {
		this.file = file;
	}

	@Override
	public ScriptOrigin findRelative(String includePath) {
		File file = new File(getFile().getParent(), includePath);

		if (file.exists()) {
			return new FileScriptOrigin(file);
		}

		return ScriptOrigin.super.findRelative(includePath);
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getScriptName() {
		return getFile().getName();
	}

	@Override
	public String read() throws IOException {
		Scanner reader = new Scanner(getFile());

		StringBuilder value = new StringBuilder();
		while (reader.hasNextLine()) {
			value.append(reader.nextLine()).append("\n");
		}
		reader.close();


		//Filter
		value = new StringBuilder(value.toString().replaceAll("\\r", ""));
		if (value.toString().startsWith("\uFEFF")) {
			value = new StringBuilder(value.substring(1));
		}

		return value.toString();
	}

	@Override
	public String toString() {
		return "fileorigin(" + getScriptName() + ')';
	}
}
