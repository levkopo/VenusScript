package com.levkopo.vs.library.request;

import com.levkopo.vs.library.VenusLibrary;

public class RequestLibrary extends VenusLibrary {

    public RequestLibrary(){
        addAll(GETRequest.class);
    }
}
