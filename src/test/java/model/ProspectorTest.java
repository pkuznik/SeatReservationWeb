/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import pl.polsl.seatreservationweb.model.Prospector;
import pl.polsl.seatreservationweb.model.SeatReservationException;
import pl.polsl.seatreservationweb.model.CinemaHall;
import pl.polsl.seatreservationweb.model.CombinationOfPlace;

/**
 *
 * @author Piotr Kuźnik
 * @version 1.1
 */
public class ProspectorTest {

    @DisplayName("Próba rezerwacji ")
    @Test
    public void testEmptyCienemaHallForProspector() {
        Boolean error = true;
        try {
            Prospector prospector = new Prospector(null, 0, 0);
        } catch (SeatReservationException e) {
            error = false;
        }
        assertFalse(error, "Błędny algorytm nie można używać algorytmu bez zdefiniowaia sali kinowej!");
    }

    @DisplayName("Najprostszy test algorytmu ;-)")
    @Test
    public void testReserveChairsAlgorithIsReadyToUse() {
        Boolean error = false;

        try {
            Prospector prospector = new Prospector(new CinemaHall(10, 10), 0, 0);
            prospector.findNextToTheChairNextToYou(1);
        } catch (SeatReservationException e) {
            error = true;
        }
        assertFalse(error, "Błędny algorytm nie można nic zarezerwować!");
    }

    @DisplayName("Test sytuacji wyjątkowej - rezerwacja dwóch rzędów")
    @Test
    public void testTwoRowsReservedInCienameHall() {
        Boolean error = false;

        try {
            Prospector prospector = new Prospector(new CinemaHall(10, 10), 0, 0);
            prospector.findNextToTheChairNextToYou(20);
        } catch (SeatReservationException e) {
            error = true;
        }
        assertFalse(error, "Nie obsłużono rezerwacji kilku rzędów sali kinowej!");
    }

    @DisplayName("Test sytuacji wyjątkowej - przepełnienie")
    @Test
    public void testOwerflowCienameHall() {
        CinemaHall hall = new CinemaHall(10, 10);
        Boolean error = false;
        CombinationOfPlace coordinate = null;
        try {
            Prospector prospector = new Prospector(hall, 0, 0);
            coordinate = prospector.findNextToTheChairNextToYou(10);
            hall.reserveCombinateOfPlace(coordinate);

            coordinate = prospector.findNextToTheChairNextToYou(20);
            hall.reserveCombinateOfPlace(coordinate);

            coordinate = prospector.findNextToTheChairNextToYou(30);
            hall.reserveCombinateOfPlace(coordinate);

            coordinate = prospector.findNextToTheChairNextToYou(40);
            hall.reserveCombinateOfPlace(coordinate);

            coordinate = prospector.findNextToTheChairNextToYou(1); //overflow
        } catch (SeatReservationException e) {
            error = true;
        }

        assertTrue(error, "Nie obsłużono przepełnienia sali kinowej! - " + coordinate);
    }
}
