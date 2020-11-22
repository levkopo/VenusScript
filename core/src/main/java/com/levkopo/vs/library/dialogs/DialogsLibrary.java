package com.levkopo.vs.library.dialogs;

import com.levkopo.vs.library.VenusLibrary;

public class DialogsLibrary extends VenusLibrary {
	public DialogsLibrary() {
		addAll(AskDialog.class, Dialog.class, ErrorDialog.class, InfoDialog.class, InputDialog.class, SetTheme.class, WarnDialog.class);
	}
}