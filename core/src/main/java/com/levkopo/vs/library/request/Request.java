package com.levkopo.vs.library.request;

import com.levkopo.vs.exception.runtime.ScriptRuntimeException;
import com.levkopo.vs.executor.Context;
import com.levkopo.vs.function.FunctionCallDescriptor;
import com.levkopo.vs.function.Method;
import com.levkopo.vs.function.annotation.MethodName;
import com.levkopo.vs.value.BoolValue;
import com.levkopo.vs.value.MapValue;
import com.levkopo.vs.value.StringValue;
import com.levkopo.vs.value.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@MethodName("execute")
public class Request extends Method {

    @SuppressWarnings("unchecked")
    @Override
    public Value call(Context context, FunctionCallDescriptor descriptor) throws ScriptRuntimeException {
        MapValue mapValue = (MapValue) context.getVar("parameters").getValue();
        Map<Object, Value> headers = (Map<Object, Value>) context.getVar("headers").getValue().value();
        String method = (String) context.getVar("method").getValue().value();
        String parameters = URLBuilder.httpBuildQuery(mapValue.getMap());

        try {
            URL urlObject = new URL(context.getVar("url").getValue().value()+"?"+parameters);
            HttpURLConnection conn = (HttpURLConnection) urlObject.openConnection();
            conn.setRequestMethod(method);
            for(Map.Entry<Object, Value> header: headers.entrySet()){
                conn.addRequestProperty((String) header.getKey(), header.getValue().value().toString());
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
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
