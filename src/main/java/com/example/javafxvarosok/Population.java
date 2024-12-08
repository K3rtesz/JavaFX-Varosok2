package com.example.javafxvarosok;

public class Population {
    private int cityId;
    private int year;
    private int females;
    private int total;

    public Population(int cityId, int year, int females, int total) {
        this.cityId = cityId;
        this.year = year;
        this.females = females;
        this.total = total;
    }

    public int getCityId() {
        return cityId;
    }

    public int getYear() {
        return year;
    }

    public int getFemales() {
        return females;
    }

    public int getTotal() {
        return total;
    }
}
