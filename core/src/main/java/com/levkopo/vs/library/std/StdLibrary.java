package com.levkopo.vs.library.std;

import com.levkopo.vs.library.VenusLibrary;

public class StdLibrary extends VenusLibrary {
	public StdLibrary() {
		// Basic I/O
		addAll(HasScan.class, Print.class, Println.class, Scan.class);

		// Collections
		addAll(NewArray.class, Size.class);

		// Desktop
		addAll(Beep.class, Browse.class, Shell.class);

		// Synchronous
		addAll(Consume.class, Produce.class, WaitAttribution.class, WaitDefinition.class);

		// Utilities
		addAll(Assert.class, Exit.class, ExitWithCode.class, Millis.class, Sleep.class);

		//Strings
		addAll(StrExplode.class, StrImplode.class, StrReplace.class);

		//Time
		addAll(UnixTime.class);
	}
}