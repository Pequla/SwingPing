package com.pequla.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pequla.model.CachedData;
import com.pequla.model.PagedData;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class DataService {

    private static DataService instance;

    private final HttpClient client;
    private final ObjectMapper mapper;

    private DataService() {
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(3))
                .build();

        // Register json mapper
        this.mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static DataService getInstance() {
        if (instance == null) {
            instance = new DataService();
        }
        return instance;
    }

    public CachedData getCachedDataForId(Integer id) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://cache.samifying.com/api/data/" + id))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> rsp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (rsp.statusCode() != 200)
            throw new RuntimeException("Response code " + rsp.statusCode());
        return mapper.readValue(rsp.body(), CachedData.class);
    }

    public PagedData getCachedData(Integer page, Integer size) throws IOException, InterruptedException {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://cache.samifying.com/api/data?page=" + page +"&size="+size))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> rsp = client.send(req, HttpResponse.BodyHandlers.ofString());
        if (rsp.statusCode() != 200)
            throw new RuntimeException("Response code " + rsp.statusCode());
        return mapper.readValue(rsp.body(), PagedData.class);
    }
}
