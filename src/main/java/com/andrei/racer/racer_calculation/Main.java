package com.andrei.racer.racer_calculation;

import com.andrei.racer.repository.RacerRepository;

import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        TopRacersFormatter topRacersFormatter = new TopRacersFormatter();
        RacerRepository racerRepository = new RacerRepository(
                "start.log",
                "end.log",
                "abbreviations.txt");
        List<Racer> racers = racerRepository.getRacers();
        String result = topRacersFormatter.format(racers, racers.size());
        System.out.println(result);

    }
}
