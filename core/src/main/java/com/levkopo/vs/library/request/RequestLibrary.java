package com.levkopo.vs.library.request;

import com.levkopo.vs.library.VSLibrary;

public class RequestLibrary extends VSLibrary {

    public RequestLibrary(){
        addObject(RequestObject.class);
        addFunction(GETRequest.class);
        addFunction(BuildHTTPParameters.class);
    }
}
