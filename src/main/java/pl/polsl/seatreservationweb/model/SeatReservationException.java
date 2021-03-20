
package pl.polsl.seatreservationweb.model;

/**
 * This class is domain exception model
 * 
 * @author Piotr Kuźnik
 * @version 1.12
 */
public class SeatReservationException extends Exception{
    
    /**
     * Constructor
     * 
     * @param message Message of error 
     */
    public SeatReservationException(String message) {
        super(message);
    }

}