package com.andrei.racer.racer_calculation;

import java.time.Duration;

public class Racer {

    private String abbreviation;
    private Duration bestTime;
    private String name;
    private String team;


    public Racer(String abbreviation, String name, String team, Duration bestTime) {
        this.abbreviation = abbreviation;
        this.bestTime = bestTime;
        this.team = team;
        this.name = name;

    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public Duration getBestTime() {
        return bestTime;
    }
}
