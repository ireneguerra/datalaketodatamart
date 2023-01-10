package org.example.view;

import com.google.gson.Gson;
import org.example.controller.Controller;
import spark.Request;
import spark.Response;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static spark.Spark.get;

public class WebService {
    public static void webService() {
        get("/v1/places/with-max-temperature", WebService::getMax);
        get("/v1/places/with-min-temperature", WebService::getMin);
    }

    private static String getMax(Request request, Response response) throws SQLException {
        response.header("content-type", "application/json");
        String from = request.queryParams("from");
        String to = request.queryParams("to");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, format);
        LocalDate toDate = LocalDate.parse(to, format);
        List<String> places = new Controller().getMaxLoc(fromDate, toDate);
        return toJson(places);
    }

    private static String getMin(Request request, Response response) throws SQLException {
        response.header("content-type", "application/json");
        String from = request.queryParams("from");
        String to = request.queryParams("to");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate fromDate = LocalDate.parse(from, format);
        LocalDate toDate = LocalDate.parse(to, format);
        List<String> places = new Controller().getMinLoc(fromDate, toDate);
        return toJson(places);
    }

    private static String toJson(List<String> places) {
        return new Gson().toJson(places);
    }

}
