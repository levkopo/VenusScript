package com.levkopo.vs.origin;

import com.github.bloodshura.ignitium.charset.Encoding;
import com.github.bloodshura.ignitium.io.File;
import com.github.bloodshura.ignitium.io.FileException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.Charset;

public class FileScriptOrigin implements ScriptOrigin {
	private final File file;

	public FileScriptOrigin(File file) {
		this.file = file;
	}

	@Override
	public ScriptOrigin findRelative(String includePath) {
		try {
			File file = new File(getFile().getParent(), includePath);

			if (file.exists()) {
				return new FileScriptOrigin(file);
			}
		} catch (FileException ignored) {
		}

		return ScriptOrigin.super.findRelative(includePath);
	}

	public File getFile() {
		return file;
	}

	@Override
	public String getScriptName() {
		return getFile().getFullName();
	}

	@Override
	public String read() throws IOException {
		/*try {
			System.setProperty("file.encoding","UTF-8");
			Field charset = Charset.class.getDeclaredField("defaultCharset");
			charset.setAccessible(true);
			charset.set(null,null);
		}catch (Exception ignored){}*/

		String value = getFile().readString(Encoding.UTF_8);
		value = value.replaceAll("\\r", "");


		if (value.startsWith("\uFEFF")) {
			value = value.substring(1);
		}

		return value;
	}

	@Override
	public String toString() {
		return "fileorigin(" + getScriptName() + ')';
	}
}
