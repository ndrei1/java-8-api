package com.andrei.racer.racer_calculation;

import static java.util.Comparator.comparing;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class TopRacersFormatter {

    private static final String FORMAT_STRING_FROM_RACER = "%-2d. %-20s| %-30s|";
    private static final String FORMAT_STRING_FROM_TIME_LAP = "%d:%d.%d\n";

    public String format(List<Racer> racers, int quantityOfRacer) {
        AtomicInteger place = new AtomicInteger();
        String result = "";
        return racers.stream()
                .sorted(comparing(Racer::getBestTime))
                .map(racer -> formatTemplate(racer, place.getAndIncrement(), quantityOfRacer)).collect(Collectors.joining(result));
    }

    private String formatTemplate(Racer racer, int place, int maxPlace) {
        place++;
        String formatRacer = "";
        if (place == maxPlace - 3) {
            formatRacer += "-----------------------------------------------------------------\n";
        }

        String lapTime = "";
        long minute = racer.getBestTime().getSeconds() / 60;
        long second = racer.getBestTime().getSeconds() - 60;
        long nano = racer.getBestTime().getNano() / 1000000;
        lapTime = String.format(FORMAT_STRING_FROM_TIME_LAP, minute, second, nano);

        formatRacer += String.format(FORMAT_STRING_FROM_RACER, place, racer.getName(),
                racer.getTeam()) + lapTime;
        return formatRacer;
    }
}

