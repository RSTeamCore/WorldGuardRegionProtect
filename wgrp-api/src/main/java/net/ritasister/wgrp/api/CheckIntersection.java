package net.ritasister.wgrp.api;

/**
 * Methods by which it is checked which selection method the player has chosen
 *
 * @param <Player>
 */
public interface CheckIntersection<Player> {

    boolean checkIntersection(Player player);

    boolean checkCIntersection(Player player, String... args);

    boolean checkPIntersection(Player player, String... args);

    boolean checkSIntersection(Player player, String... args);

    boolean checkUIntersection(Player player, String... args);

    boolean checkCPIntersection(Player player, String... args);

}
