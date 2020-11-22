package com.levkopo.vs.fpm;

import com.fastcgi.FCGIInterface;
import com.github.bloodshura.ignitium.io.File;
import com.levkopo.vs.component.Script;
import com.levkopo.vs.executor.ApplicationContext;
import com.levkopo.vs.executor.OutputReference;
import com.levkopo.vs.executor.VenusExecutor;
import com.levkopo.vs.fpm.lib.FPMLibrary;
import com.levkopo.vs.origin.FileScriptOrigin;
import com.levkopo.vs.origin.ScriptMode;
import com.levkopo.vs.origin.ScriptOrigin;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.*;

public class FastCGIApplication {

    public static void main(String[] args) {
        int count = 0;

        VenusExecutor executor = new VenusExecutor();
        FCGIInterface fcgiinterface = new FCGIInterface();
        while(fcgiinterface.FCGIaccept()>=0) {
            count++;
            ScriptOrigin origin = new FileScriptOrigin(new File(getProperty("SCRIPT_FILENAME")));

            StringBuilder output = new StringBuilder();
            Map<String, String> headers = new HashMap<>();
            try {
                ApplicationContext ctx = new ApplicationContext();
                ctx.getLibrarySuppliers().add("fpm", FPMLibrary::new);
                ctx.setUserData("in", null);
                ctx.setUserData("out", (OutputReference) output::append);
                ctx.setUserData("query", parseQuery(getProperty("QUERY_STRING")));
                ctx.setUserData("headers", (OutputReference) (header)->{
                    String[] header_data = header.split(":", 2);
                    headers.put(header_data[0].toLowerCase(), header_data[1]);
                });

                Script script = origin.compile(ctx);
                executor.run(script, ScriptMode.NORMAL);
            } catch (Exception e) {
                StringWriter errors = new StringWriter();
                e.printStackTrace(new PrintWriter(errors));
                output.append(errors.toString());
            }

            out.println("HTTP/1.1 200 OK");
            if(!headers.containsKey("content-type"))
                headers.put("content-type", "text/html; charset=UTF-8");

            headers.put("Content-Length", String.valueOf(output.length()));
            headers.put("transfer-encoding", "chunked");
            for (String header_key: headers.keySet()){
                String header_value = headers.get(header_key);
                out.println(header_key+": "+header_value);
            }

            out.println("\n");
            out.println(output.toString());
        }
    }

    private static Map<String, String> parseQuery(String string_query){
        Map<String, String> result = new HashMap<>();

        if(string_query.isEmpty())
            return result;

        String[] query_list = string_query.split("&");
        for(String query: query_list){
            if(!query.isEmpty()) {
                String[] query_data = query.split("=");
                if (query_data.length == 1)
                    result.put(query_data[0], "");
                else result.put(query_data[0], query_data[1]);
            }
        }

        return result;
    }
}
