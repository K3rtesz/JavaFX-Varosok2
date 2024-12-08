package com.example.javafxvarosok;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PopulationDAO {
    public List<Population> getAllPopulations() throws SQLException {
        String query = "SELECT * FROM lelekszam";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<Population> populations = new ArrayList<>();
            while (rs.next()) {
                populations.add(new Population(
                        rs.getInt("varosid"),
                        rs.getInt("ev"),
                        rs.getInt("no"),
                        rs.getInt("osszesen")
                ));
            }
            return populations;
        }
    }

    public List<Population> filterPopulations(String cityId, String year) throws SQLException {
        String query = "SELECT * FROM lelekszam WHERE (? = '' OR varosid = ?) AND (? = '' OR ev = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cityId);
            pstmt.setString(2, cityId);
            pstmt.setString(3, year);
            pstmt.setString(4, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Population> populations = new ArrayList<>();
                while (rs.next()) {
                    populations.add(new Population(
                            rs.getInt("varosid"),
                            rs.getInt("ev"),
                            rs.getInt("no"),
                            rs.getInt("osszesen")
                    ));
                }
                return populations;
            }
        }
    }


    public void insertPopulation(int cityId, int year, int females, int total) throws SQLException {
        String query = "INSERT INTO lelekszam (varosid, ev, no, osszesen) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cityId);
            pstmt.setInt(2, year);
            pstmt.setInt(3, females);
            pstmt.setInt(4, total);
            pstmt.executeUpdate();
        }
    }

    public void updatePopulation(int cityId, int year, int females, int total) throws SQLException {
        String query = "UPDATE lelekszam SET no = ?, osszesen = ? WHERE varosid = ? AND ev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, females);
            pstmt.setInt(2, total);
            pstmt.setInt(3, cityId);
            pstmt.setInt(4, year);
            pstmt.executeUpdate();
        }
    }

    public void deletePopulation(int cityId, int year) throws SQLException {
        String query = "DELETE FROM lelekszam WHERE varosid = ? AND ev = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cityId);
            pstmt.setInt(2, year);
            pstmt.executeUpdate();
        }
    }



}
