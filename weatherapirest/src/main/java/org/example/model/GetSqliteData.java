package org.example.model;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GetSqliteData implements GetDatamartData {

    public Statement setConnect() throws SQLException {
        String dbPath = "database/datamart.db";
        Connection connection = connect(dbPath);
        return connection.createStatement();
    }

    private Connection connect(String dbPath) {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:" + dbPath;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public List<String> getLocations(String tableName, Statement statement, LocalDate from, LocalDate to) throws SQLException {
        List<String> places = new ArrayList<>();
        ResultSet rs = statement.executeQuery("SELECT place, date FROM " + tableName + ";");
        while (rs.next()) {
            String place = rs.getString("place");
            String date = rs.getString("date");
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = LocalDate.parse(date, format);
            if (newDate.isBefore(to) && newDate.isAfter(from)) {
                places.add(place);
            }
        }
        return places;
    }
}
