package com.levkopo.vs.library.time;

import com.levkopo.vs.library.VenusLibrary;

public class TimeLibrary extends VenusLibrary {
	public TimeLibrary() {
		addAll(GetDay.class, GetHour.class, GetMinute.class, GetMonth.class, GetSecond.class, GetYear.class);
	}
}