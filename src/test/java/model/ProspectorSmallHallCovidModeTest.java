
package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.seatreservationweb.model.Prospector;
import pl.polsl.seatreservationweb.model.SeatReservationException;
import pl.polsl.seatreservationweb.model.CinemaHall;

/**
 * Test reserved chairs in cinema hall in covid mode where free chairs 
 * between reserved is 2 chairs in row and 1 chair in column
 * 
 * @author Piotr Kuźnik
 * @version 1.0
 */
public class ProspectorSmallHallCovidModeTest {

    /**
     * @var Prospector algorithm searching for free seats in the cinema room 
     */
    private Prospector prospector;

    @BeforeEach
    public void setUp() {
        try {
            this.prospector = new Prospector( new CinemaHall(10, 10), 2, 1);
        } catch (SeatReservationException e) {}
    }

    /**
     * Test check reserved all from source array
     * 
     * @param numberOfchairs Order number of chairs to reserved
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 1, 2, 3, 4, 5, 6})
    public void testSmallHallInCovidMode(int numberOfchairs) {
        Boolean error = false;

        try {
            this.prospector.findNextToTheChairNextToYou(numberOfchairs);
        } catch (SeatReservationException e) {
            error = true;
        }
        assertFalse(error, "Błędny algorytm nie można zarezerwować " + numberOfchairs + " miejsc w sali kinowej z odstępami 2 wolnych miejsc!");
    }
}
