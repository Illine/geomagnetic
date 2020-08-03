package com.illine.weather.geomagnetic.test.helper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FileHelper {

    private static final String TODAY_PATTERN = "today";
    private static final String AFTER_TOMORROW_PATTERN = "afterTomorrow";

    private static final Locale TESTED_DATE_LOCALE = Locale.ENGLISH;
    private static final String TESTED_DATE_PATTERN = "dd MMM";
    private static final DateTimeFormatter TESTED_DATE_FORMATTER = DateTimeFormatter.ofPattern(TESTED_DATE_PATTERN, TESTED_DATE_LOCALE);

    public static Path getPath(String pathArg, Class<?> currentClass) {
        var path = Optional.ofNullable(currentClass.getClassLoader().getResource(pathArg)).orElseThrow().getPath();
        return Paths.get(path);
    }

    public static String getFileContent(Path path) throws IOException {
        var fileBytes = Files.readAllBytes(path);
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    public static String setDate(String fileContent, LocalDate from, LocalDate to) {
        var testedFrom = from.format(TESTED_DATE_FORMATTER);
        var testedTo = to.format(TESTED_DATE_FORMATTER);
        var tmp = fileContent.replaceAll(TODAY_PATTERN, testedFrom);
        return tmp.replaceAll(AFTER_TOMORROW_PATTERN, testedTo);
    }
}