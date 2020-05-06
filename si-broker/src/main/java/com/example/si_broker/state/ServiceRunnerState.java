package com.example.si_broker.state;

import com.example.si_broker.controllers.v1.ServiceController;
import com.example.si_broker.domain.ServiceDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
@RequestScope
public class ServiceRunnerState implements State {

    @Override
    public void execute(Context context) {
        try {
            URL url = new URL("http://localhost:8080/api/v1/services/complexService");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            Map<String, String> parameters = new HashMap<>();
            parameters.put("aggregationFunction", context.getComplexServiceParameters());

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes("?aggregationFunction=" + context.getComplexServiceParameters());
            out.flush();
            out.close();

        } catch (Exception e) {

        }
    }
}
