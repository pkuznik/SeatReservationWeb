package pl.polsl.seatreservationweb.model;

/**
 * Class implements algoright to reserved chairs in cinema hall.
 *
 * @author Piotr Kuźnik
 * @version 1.13
 */
public class Prospector {

    /**
     * #var CinemaHall object of Cinema Hall
     */
    private final CinemaHall hall;

    /**
     * @var int Number of free chairs between reserved in row
     */
    private final int spaceX;

    /**
     * @var int Number of free chairs between reserved in column
     */
    private final int spaceY;

    /**
     * Constructor
     *
     * @param hall Object CinemaHall
     * @param spaceX Number of free chairs between reserved in row
     * @param spaceY Number of free chairs between reserved in column
     * 
     * @throws SeatReservationException throw when param hall is null
     */
    public Prospector(CinemaHall hall, int spaceX, int spaceY) throws SeatReservationException {
        this.hall = hall;
        this.spaceX = spaceX;
        this.spaceY = spaceY;
        
        if (hall == null) {
            throw new SeatReservationException("First argument must be instance of CinemaHall");
        }
    }
    
    /**
     * 
     * @return  number of space chair in row
     */
    public int getSpaceInRowChairBetweenReservation() {
        return this.spaceX;
    }

    /**
     * 
     * @return number of space chair in column
     */
    public int getSpaceInColumnChairBetweenReservation() {
        return this.spaceY;
    }
    /**
     * method find next free chairs to reserved
     *
     * @param quantityChairs number
     * @return On success object CombinationOfPlace of coords in hall
     * @throws SeatReservationException on errror when cannot reserwation
     */
    public CombinationOfPlace findNextToTheChairNextToYou(int quantityChairs) throws SeatReservationException {
        int freeChairsInRow = 0;
        int startX = 1, endX, startY = 1;
        for (int numberRow = 1; numberRow <= this.hall.getQuantityOfChairsInColumn(); numberRow++) {
            if (quantityChairs <= this.hall.getQuntityOfchairsInRow()) {
                freeChairsInRow = 0;
                startX = 1;
               
            }
            if (freeChairsInRow == 0) {
                startY = numberRow;
                startX = 1;
            }

            for (int numberChair = 1; numberChair <= this.hall.getQuntityOfchairsInRow(); numberChair++) {
                endX = numberChair;
                if (this.hall.isChairReserved(numberRow, numberChair)) {
                    freeChairsInRow = 0;
                    numberChair += this.spaceX;
                    startX = numberChair + 1;
                    startY = numberRow;
                    
                    continue;
                } else {
                    freeChairsInRow++;
                }

                //System.out.println("Find::" + quantityChairs + " coordinate(" + numberChair + ", " + numberRow + ") free=" + freeChairsInRow + " prepare (" + startX + ", " + startY + ", " + endX + ", " + numberRow + ")" );
                if (freeChairsInRow >= quantityChairs) {
                    return new CombinationOfPlace(startX, startY, endX, numberRow);
                }
            }

            numberRow += this.spaceY;
        }

        throw new SeatReservationException("Brak możliwości zarezerowania " + quantityChairs + " miejsc!");
    }

}
