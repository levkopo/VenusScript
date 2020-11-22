package com.levkopo.vs.library.request;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodArgs;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@MethodName("r_get")
@MethodArgs({StringValue.class})
public class GETRequest extends Method {

    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        try {
            URL urlObject = new URL((String) descriptor.get(0).value());
            URLConnection conn = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder output = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                output.append(inputLine);
            }
            in.close();

            return new StringValue(output.toString());
        }catch (Exception ignored){}

        return new BoolValue(false);
    }
}
