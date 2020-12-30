package com.levkopo.vs.fpm.lib;

import com.levkopo.vs.fpm.lib.methods.GetQuery;
import com.levkopo.vs.fpm.lib.methods.SetHeader;
import com.levkopo.vs.fpm.lib.methods.VSInfo;
import com.levkopo.vs.library.VSLibrary;

public class FPMLibrary extends VSLibrary {

    public FPMLibrary(){
        addFunction(SetHeader.class);
        addFunction(GetQuery.class);
        addFunction(VSInfo.class);
    }
}
