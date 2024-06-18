package net.ritasister.wgrp.util;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Utility class for a version of plugin.
 */
public final class Version implements Comparable<Version> {
    private final int[] parts;
    private final String version;
    private final String tag;

    /**
     * Creates a new version object to be compared.
     * The version format is numbers separated by '.', possibly followed by a '-' to include an extra tag after it.
     *
     * @param version string representation of the version
     * @throws IllegalArgumentException if the given version string is null or empty
     * @throws NumberFormatException    if there are non-number characters in the version (before the tag)
     */
    public Version(final String version) {
        Preconditions.checkArgument(version != null && !version.isEmpty());

        final int index = version.indexOf('-');
        final String[] split = (index == -1 ? version : version.substring(0, index)).split("\\.");
        parts = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            // Let this throw an exception if parsing fails
            parts[i] = Integer.parseInt(split[i]);
        }

        this.version = version;
        tag = index != -1 ? version.substring(index + 1) : "";
    }

    /**
     * Compare two versions.
     *
     * @param version version to compare to
     * @return 0 if they are the same, 1 if this instance is newer, -1 if older
     */
    public int compareTo(@Nullable final Version version) {
        if (version == null) return 0;

        final int max = Math.max(this.parts.length, version.parts.length);
        for (int i = 0; i < max; i++) {
            final int partA = i < this.parts.length ? this.parts[i] : 0;
            final int partB = i < version.parts.length ? version.parts[i] : 0;
            if (partA < partB) {
                return -1;
            } else if (partA > partB) {
                return 1;
            }
        }

        if (this.tag.isEmpty() && !version.tag.isEmpty()) {
            return 1;
        } else if (!this.tag.isEmpty() && version.tag.isEmpty()) {
            return -1;
        }
        return 0;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, tag);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Version other)) {
            return false;
        }

        return other.version.equals(version) && other.version == version;
    }
}
