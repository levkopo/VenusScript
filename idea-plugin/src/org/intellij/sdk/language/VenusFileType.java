package org.intellij.sdk.language;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class VenusFileType extends LanguageFileType {

    public static final VenusFileType INSTANCE = new VenusFileType();

    protected VenusFileType() {
        super(VenusScriptLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "VenusScript";
    }

    @NotNull
    @Override
    public @NlsContexts.Label String getDescription() {
        return "=)";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "vs";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return null;
    }
}
