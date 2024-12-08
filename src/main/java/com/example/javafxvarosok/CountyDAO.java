package com.example.javafxvarosok;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountyDAO {
    public List<County> getAllCounties() throws SQLException {
        String query = "SELECT * FROM megye";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<County> counties = new ArrayList<>();
            while (rs.next()) {
                counties.add(new County(
                        rs.getInt("id"),
                        rs.getString("nev")
                ));
            }
            return counties;
        }
    }

    public List<County> filterCounties(String name) throws SQLException {
        String query = "SELECT * FROM megye WHERE nev LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                List<County> counties = new ArrayList<>();
                while (rs.next()) {
                    counties.add(new County(
                            rs.getInt("id"),
                            rs.getString("nev")
                    ));
                }
                return counties;
            }
        }
    }


    public void insertCounty(String name) throws SQLException {
        String query = "INSERT INTO megye (nev) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.executeUpdate();
        }
    }

    public void updateCounty(int id, String name) throws SQLException {
        String query = "UPDATE megye SET nev = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteCounty(int id) throws SQLException {
        String query = "DELETE FROM megye WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }



}
