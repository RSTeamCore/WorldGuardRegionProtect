package net.ritasister.wgrp.api;

/**
 * Methods by which it is checked which selection method the player has chosen
 */
public interface CheckIntersection<Player> {

    /**
     *
     * @param player
     * @return
     */
    boolean checkIntersection(Player player);

    /**
     *
     * @param player
     * @return
     */
    boolean checkCIntersection(Player player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkPIntersection(Player player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkSIntersection(Player player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkUIntersection(Player player, String... args);

    /**
     *
     * @param player
     * @return
     */
    boolean checkCPIntersection(Player player, String... args);

}
