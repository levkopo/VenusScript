package com.levkopo.vs.fpm.lib;

import com.levkopo.vs.fpm.lib.methods.GetQuery;
import com.levkopo.vs.fpm.lib.methods.SetHeader;
import com.levkopo.vs.fpm.lib.methods.VSInfo;
import com.levkopo.vs.library.VenusLibrary;

public class FPMLibrary extends VenusLibrary {

    public FPMLibrary(){
        add(SetHeader.class);
        add(GetQuery.class);
        add(VSInfo.class);
    }
}
