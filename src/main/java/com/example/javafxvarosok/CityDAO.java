package com.example.javafxvarosok;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityDAO {
    public List<City> getAllCities() throws SQLException {
        String query = "SELECT * FROM varos";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            List<City> cities = new ArrayList<>();
            while (rs.next()) {
                cities.add(new City(
                        rs.getInt("id"),
                        rs.getString("nev"),
                        rs.getInt("megyeid"),
                        rs.getBoolean("megyeszekhely"),
                        rs.getBoolean("megyeijogu")
                ));
            }
            return cities;
        }
    }

    public List<City> filterCities(String name, String countyId) throws SQLException {
        String query = "SELECT * FROM varos WHERE nev LIKE ? AND (? = '' OR megyeid = ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, countyId);
            pstmt.setString(3, countyId);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<City> cities = new ArrayList<>();
                while (rs.next()) {
                    cities.add(new City(
                            rs.getInt("id"),
                            rs.getString("nev"),
                            rs.getInt("megyeid"),
                            rs.getBoolean("megyeszekhely"),
                            rs.getBoolean("megyeijogu")
                    ));
                }
                return cities;
            }
        }
    }



    public void insertCity(String name, int countyId, boolean isCountySeat, boolean isCountyAuthority) throws SQLException {
        String query = "INSERT INTO varos (nev, megyeid, megyeszekhely, megyeijogu) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countyId);
            pstmt.setBoolean(3, isCountySeat);
            pstmt.setBoolean(4, isCountyAuthority);
            pstmt.executeUpdate();
        }
    }

    public void updateCity(int id, String name, int countyId, boolean isCountySeat, boolean isCountyAuthority) throws SQLException {
        String query = "UPDATE varos SET nev = ?, megyeid = ?, megyeszekhely = ?, megyeijogu = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, countyId);
            pstmt.setBoolean(3, isCountySeat);
            pstmt.setBoolean(4, isCountyAuthority);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        }
    }


    public void deleteCity(int id) throws SQLException {
        String query = "DELETE FROM varos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

}

