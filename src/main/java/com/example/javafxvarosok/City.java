package com.example.javafxvarosok;

public class City {
    private int id;
    private String name;
    private int countyId;
    private boolean isCountySeat;
    private boolean isCountyAuthority;

    public City(int id, String name, int countyId, boolean isCountySeat, boolean isCountyAuthority) {
        this.id = id;
        this.name = name;
        this.countyId = countyId;
        this.isCountySeat = isCountySeat;
        this.isCountyAuthority = isCountyAuthority;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCountyId() {
        return countyId;
    }

    public boolean isCountySeat() {
        return isCountySeat;
    }

    public boolean isCountyAuthority() {
        return isCountyAuthority;
    }
}
