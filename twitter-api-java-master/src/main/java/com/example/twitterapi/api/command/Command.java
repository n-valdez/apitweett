package com.example.twitterapi.api.command;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class Command {

    private final String bearerToken;

    @Autowired
    public Command(@Value("${bearerToken}") String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String executeHttpMethod(HttpUriRequest httpRequest) throws IOException {

        try {
            CloseableHttpResponse response = executeHttpMethodRequest(httpRequest);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            return responseHandler.handleResponse(response);
        } catch (IOException e) {
            String usrMsg = "Error al obtener la respuesta de Twitter API 2";
            String devMsg = usrMsg + ": " + e.getMessage();
            System.out.println(devMsg);
            throw e;
        }
    }

    private CloseableHttpResponse executeHttpMethodRequest(HttpUriRequest httpRequest) throws IOException {
        httpRequest.setHeader("Authorization", String.format("Bearer %s", bearerToken));
        httpRequest.setHeader("Content-Type", "application/json");
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();
        try {
            System.out.println("Ejecutando request... ");
            CloseableHttpResponse response = httpClient.execute(httpRequest);
            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                InputStream inputStream = response.getEntity().getContent();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int read;
                while ((read = inputStream.read()) != -1) {
                    baos.write(read);
                }
                String stringResponse = IOUtils.toString(baos.toByteArray(), "UTF-8");
                baos.close();
                inputStream.close();
                response.close();
                String msg = "Twitter 2 devolvió código de estado HTTP " + status;
                System.out.println(msg);
                System.out.println("El error es: " + stringResponse);
                throw new RuntimeException();
            }
            return response;
        } catch (IOException e) {
            String usrMsg = "Error al obtener la respuesta de Twitter 2";
            String devMsg = usrMsg + ": " + e.getMessage();
            System.out.println(devMsg);
            throw e;
        }
    }
}
