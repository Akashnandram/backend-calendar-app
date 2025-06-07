package com.example.calendar.service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HttpClientService {
    private static final List<Integer> RETRYABLE_HTTP_CODES = List.of(408, 500, 502, 503, 504, 522, 524);
    private static final String POST_CALL = "postCall";
    private static final String PUT_CALL = "putCall";
    private static final String GET_CALL = "getCall";
    private static final String HEAD_CALL = "headCall";
    private static final String HTTP_LOG_RESPONSE = "Service gave {} for uri {}";
    private static final String HTTP_EXCEPTION_FORMAT = "Got exception for uri {}, exception cause={}, exception message={}";

    @Autowired
    private OkHttpClient client;

    public Response getCall(String url, Map<String, String> headersMap) throws Exception {

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headersMap != null && !headersMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headersMap.entrySet()) {
                headerBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        Request request = new Request.Builder()
                .url(url)
                .get()
                .headers(headerBuilder.build())
                .build();
        try {
            String responseBody = "";
            Response response = client.newCall(request).execute();
            if (RETRYABLE_HTTP_CODES.contains(response.code())) {
                if (response.body() != null) {
                    responseBody = response.body().string();
                    response.body().close();
                }

                throw new Exception(String.valueOf(HttpStatus.valueOf(response.code())));
            } else if (400 <= response.code()) {
                if (response.body() != null) {
                    responseBody = response.body().string();
                    response.body().close();
                }

                throw new Exception(String.valueOf(HttpStatus.valueOf(response.code())));
            }
            return response;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
