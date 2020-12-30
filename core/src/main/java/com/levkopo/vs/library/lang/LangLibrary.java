package com.levkopo.vs.library.lang;

import com.levkopo.vs.library.VSLibrary;
import com.levkopo.vs.library.lang.functions.*;
import com.levkopo.vs.library.lang.number.NumberClass;
import com.levkopo.vs.library.lang.string.StringObject;

public class LangLibrary extends VSLibrary {

    public LangLibrary(){
        addObject(StringObject.class);
        addObject(NumberClass.class);
        addFunction(Print.class);
        addFunction(Println.class);
        addFunction(Exit.class);
        addFunction(RunOnThread.class);
        addFunction(UnixTime.class);
        addFunction(NewArray.class);
        addFunction(Replace.class);
        addFunction(HasScan.class);
        addFunction(Scan.class);
        addFunction(Size.class);
        addFunction(Sleep.class);
        addFunction(StrExplode.class);
        addFunction(StrImplode.class);
        addFunction(Date.class);
        addFunction(SetTimezone.class);
        addFunction(FunctionHasAnnotation.class);
        addFunction(FunctionGetAnnotationData.class);
    }
}
