package net.ritasister.wgrp.util.utility.version;

import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public final class Version implements Comparable<Version> {

    private final int[] parts;

    public Version(@NotNull String versionStr) {
        final String clean = versionStr.replaceAll("[^0-9.]", "");
        this.parts = Arrays.stream(clean.split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @Override
    public int compareTo(@NotNull Version o) {
        final int maxLength = Math.max(this.parts.length, o.parts.length);
        for (int i = 0; i < maxLength; i++) {
            final int thisPart = i < this.parts.length ? this.parts[i] : 0;
            final int thatPart = i < o.parts.length ? o.parts[i] : 0;
            if (thisPart < thatPart) {
                return -1;
            }
            if (thisPart > thatPart) {
                return 1;
            }
        }
        return 0;
    }
}
