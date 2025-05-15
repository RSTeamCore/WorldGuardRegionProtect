package net.ritasister.wgrp.api.model.location;

/**
 * Represents a location in a 3D space, including coordinates, orientation, and world information.
 */
public interface Location {

    /**
     * Gets the X coordinate of the location.
     *
     * @return the X coordinate
     */
    double getX();

    /**
     * Gets the Y coordinate of the location.
     *
     * @return the Y coordinate
     */
    double getY();

    /**
     * Gets the Z coordinate of the location.
     *
     * @return the Z coordinate
     */
    double getZ();

    /**
     * Gets the yaw (horizontal rotation) of the location.
     *
     * @return the yaw in degrees
     */
    float getYaw();

    /**
     * Gets the pitch (vertical rotation) of the location.
     *
     * @return the pitch in degrees
     */
    float getPitch();

    /**
     * Gets the world where the location is situated.
     *
     * @return the world
     */
    World getWorld();

    /**
     * Gets the name of the location.
     *
     * @return the name of the location
     */
    String getName();

    /**
     * Adds the specified values to the current coordinates.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new Location with updated coordinates
     */
    Location add(double x, double y, double z);

    /**
     * Subtracts the specified values from the current coordinates.
     *
     * @param x the X offset
     * @param y the Y offset
     * @param z the Z offset
     * @return a new Location with updated coordinates
     */
    Location subtract(double x, double y, double z);

    /**
     * Multiplies the coordinates by the specified values.
     *
     * @param multiplier the factor to multiply each coordinate
     * @return a new Location with updated coordinates
     */
    Location multiply(double multiplier);

    /**
     * Divides the coordinates by the specified values.
     *
     * @param divisor the factor to divide each coordinate
     * @return a new Location with updated coordinates
     */
    Location divide(double divisor);

    /**
     * Converts the location to a string representation.
     *
     * @return a string representation of the location
     */
    String toString();

    /**
     * Creates a copy of this location.
     *
     * @return a new Location object with identical values
     */
    Location clone();

}
