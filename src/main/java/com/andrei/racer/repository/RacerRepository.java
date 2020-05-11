package com.andrei.racer.repository;

import com.andrei.racer.racer_calculation.Racer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RacerRepository {

    private static final String FORMAT = "yyyy-MM-dd_HH:mm:ss.SSS";
    private String filePathWithAbbreviations;
    private String filePathWithStart;
    private String filePathWithEnd;


    public RacerRepository(String filePathWithStart, String filePathWithEnd, String filePathWithAbbreviations) {
        this.filePathWithStart = filePathWithStart;
        this.filePathWithEnd = filePathWithEnd;
        this.filePathWithAbbreviations = filePathWithAbbreviations;
    }

    public List<Racer> getRacers() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        File fileStart = new File((classLoader.getResource(filePathWithStart)).getFile());
        File fileEnd = new File((classLoader.getResource(filePathWithEnd)).getFile());

        Stream<String> streamFromStart = Files.lines(Paths.get(fileStart.getPath()));
        Map<String, LocalDateTime> startTime = streamFromStart.collect(Collectors.toMap(p -> p.substring(0, 3), t -> createResult(t)));
        Stream<String> streamFromEnd = Files.lines(Paths.get(fileEnd.getPath()));
        Map<String, LocalDateTime> endTime = streamFromEnd.collect(Collectors.toMap(p -> p.substring(0, 3), t -> createResult(t)));
        return creatRacers(startTime, endTime);
    }

    private LocalDateTime createResult(String time) {
        time = time.substring(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
        return LocalDateTime.parse(time, formatter);
    }

    private List<Racer> creatRacers(Map<String, LocalDateTime> startTime, Map<String, LocalDateTime> endTime) throws IOException {

        ClassLoader classLoader = this.getClass().getClassLoader();
        File fileAbbreviations = new File(classLoader.getResource(filePathWithAbbreviations).getFile());
        Stream<String> streamFromAbbreviations = Files.lines(Paths.get(fileAbbreviations.getPath())).sorted();
        List<Racer> racers = streamFromAbbreviations.map(s -> createRacer(endTime.get(s.substring(0, 3)), startTime.get(s.substring(0, 3)), s)).collect(Collectors.toList());

        return racers;
    }

    private Racer createRacer(LocalDateTime dateTimeEnd, LocalDateTime dateTimeStart, String abbreviations) {
        Duration lapTime = Duration.between(dateTimeStart, dateTimeEnd);
        String[] splitFile = abbreviations.split("_");
        return new Racer(splitFile[0], splitFile[1], splitFile[2], lapTime);
    }


}
