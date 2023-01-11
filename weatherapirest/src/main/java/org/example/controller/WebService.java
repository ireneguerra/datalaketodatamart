package org.example.controller;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static spark.Spark.get;

public class WebService {
    public void webService() {
        WebService webService = new WebService();
        get("/v1/places/with-max-temperature", webService::getMax);
        get("/v1/places/with-min-temperature", webService::getMin);
    }

    private String getMax(Request request, Response response) throws SQLException {
        response.header("content-type", "application/json");
        String from = request.queryParams("from");
        String to = request.queryParams("to");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, format);
        LocalDate toDate = LocalDate.parse(to, format);
        List<String> places = new Controller().getMaxLoc(fromDate, toDate);
        return toJson(places);
    }

    private String getMin(Request request, Response response) throws SQLException {
        response.header("content-type", "application/json");
        String from = request.queryParams("from");
        String to = request.queryParams("to");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(from, format);
        LocalDate toDate = LocalDate.parse(to, format);
        List<String> places = new Controller().getMinLoc(fromDate, toDate);
        return toJson(places);
    }

    private static String toJson(List<String> places) {
        return new Gson().toJson(places);
    }

}
