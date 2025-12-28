package net.ritasister.wgrp.util.file.config.provider;

import net.ritasister.wgrp.api.config.version.DateProvider;
import org.jetbrains.annotations.NotNull;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateProvider implements DateProvider {

    @Override
    public @NotNull String getCurrentDate() {
        final ZonedDateTime now = ZonedDateTime.now();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
        return formatter.format(now);
    }
}
