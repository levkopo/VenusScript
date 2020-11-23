package com.levkopo.vs.fpm.lib.methods;

import com.levkopo.vs.exception.runtime.UndefinedVariableException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.function.Function;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.VoidMethod;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.library.VenusLibrary;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@MethodName("vs_info")
public class VSInfo extends VoidMethod {

    @Override
    public void callVoid(Context context, FunctionCallDescriptor descriptor) throws UndefinedVariableException {
        Map<String, Supplier<VenusLibrary>> libs = context.getApplicationContext().getLibrarySuppliers();
        OutputReference reference = context.getApplicationContext().getUserData("out", OutputReference.class);

        reference.output("<table border=\"1\">\n" +
                "   <caption>Libraries VenusScript</caption>\n" +
                "   <tr>\n" +
                "    <th>Name</th>\n" +
                "    <th>Functions</th>\n" +
                "   </tr>");
        for(String libName : libs.keySet()){
            VenusLibrary lib = Objects.requireNonNull(libs.get(libName)).get();
            reference.output("<tr>");
            reference.output("<td>"+libName+"</td>");
            reference.output("<td>");
            for (Function function:lib){
                reference.output(function.getName()+";");
            }
            reference.output("</td>");
            reference.output("</tr>");
        }

        reference.output("</table>");
    }
}
