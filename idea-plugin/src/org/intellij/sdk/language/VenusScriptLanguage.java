package org.intellij.sdk.language;

import com.intellij.lang.Language;

public class VenusScriptLanguage extends Language {
    public static final VenusScriptLanguage INSTANCE = new VenusScriptLanguage();

    protected VenusScriptLanguage() {
        super("VenusScript");
    }
}
