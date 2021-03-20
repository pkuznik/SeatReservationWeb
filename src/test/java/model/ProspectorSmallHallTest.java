
package model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.polsl.seatreservationweb.model.Prospector;
import pl.polsl.seatreservationweb.model.SeatReservationException;
import pl.polsl.seatreservationweb.model.CinemaHall;

/**
 *
 * @author Piotr Kuźnik
 * @version 1.0
 */
public class ProspectorSmallHallTest {
    
     /**
     * @var Prospector algorithm searching for free seats in the cinema room 
     */
    private Prospector prospector;

    public ProspectorSmallHallTest() {
        try {
            this.prospector = new Prospector( new CinemaHall(10, 10), 0, 0);
        } catch (SeatReservationException e) {
        }
    } 
        
    /**
     * Test check reserved all from source array
     * 
     * @param numberOfchairs Order number of chairs to reserved
     */
    @ParameterizedTest
    @ValueSource(ints = {2, 1, 2, 3, 4, 5, 6})
    public void testSmallHall(int numberOfchairs) {
        boolean error = false;
        
        try {
            this.prospector.findNextToTheChairNextToYou(numberOfchairs);
        } catch (SeatReservationException e) {
            error = true;
        }
        assertFalse(error, "Błędny algorytm nie można zarezerwować " + numberOfchairs + " miejsc w sali kinowej!");
    }
}
