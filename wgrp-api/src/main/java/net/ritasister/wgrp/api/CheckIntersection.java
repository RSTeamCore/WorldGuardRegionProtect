package net.ritasister.wgrp.api;

public interface CheckIntersection<P> {

    boolean checkIntersection(P player);

    boolean checkCIntersection(P player, String... args);

    boolean checkPIntersection(P player, String... args);

    boolean checkSIntersection(P player, String... args);

    boolean checkUIntersection(P player, String... args);

    boolean checkCPIntersection(P player, String... args);

}
