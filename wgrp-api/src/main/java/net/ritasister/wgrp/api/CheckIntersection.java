package net.ritasister.wgrp.api;

/**
 * Methods by which it is checked which selection method the player has chosen
 */
public interface CheckIntersection<P> {

    /**
     *
     * @param player
     * @return
     */
    boolean checkIntersection(P player);

    /**
     *
     * @param player
     * @return
     */
    boolean checkCIntersection(P player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkPIntersection(P player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkSIntersection(P player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkUIntersection(P player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkCPIntersection(P player, String... args);

}
