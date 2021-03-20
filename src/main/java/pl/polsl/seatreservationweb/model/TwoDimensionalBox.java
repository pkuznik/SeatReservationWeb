package pl.polsl.seatreservationweb.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Two-dimensional list
 * 
 * @author Piotr Kuźnik
 * @version 1.1
 * @param <T> prefer Bool
 *
 */
public class TwoDimensionalBox<T> {

    /**
     * Representation array in array type T
     *
     * @var List<List<T>>
     */
    private final List<List<T>> contents;

    /**
     * @var int array boundary in a row
     */
    private final int sizeX;

    /**
     * @var int array boundary in a column
     */
    private final int sizeY;

    /**
     * Constructor
     * @param sizeX int array boundary in a row
     * @param sizeY int array boundary in a column
     * @param defaultValue T value to initialized array
     */
    public TwoDimensionalBox(int sizeX, int sizeY, T defaultValue) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.contents = new ArrayList<>();
        
        sizeX++;
        sizeY++;
        for (int i = 1; i <= sizeY; i++) {
            ArrayList<T> rows = new ArrayList<>();
            for (int j = 1; j <= sizeX; j++) {
                rows.add(defaultValue);
            }
            this.contents.add(rows);
        }
    }

    /**
     * set value in cell
     *
     * @param x int number of row
     * @param y int number of column
     * @param value T default value to set all cell
     */
    public void add(int x, int y, T value) {
        List<T> rows = this.contents.get(y);
        rows.set(x, value);

        this.contents.set(y, rows);
    }

    /**
     * get value from cell
     *
     * @param x int number of row
     * @param y int number of column
     * @return Return value about coordinate (x,y)
     */
    public T get(int x, int y) {
        return this.contents.get(y).get(x);
    }

    /**
     * Get size in row
     *
     * @return int
     */
    public int getRowSize() {
        return this.sizeX;
    }

    /**
     * Get size in column
     *
     * @return int
     */
    public int getColumnSize() {
        return this.sizeY;
    }
    
    /**
     * 
     * @return Object two demensional type T
     */
    public T[][] toArray() {
        T[][] data = (T[][]) new Object[this.sizeY][this.sizeX];
        
        int x =0, y = 0;
        for (int i=1; i<=this.sizeY; i++) {
            for (int j=1; j<=this.sizeX; j++) {
                data[x][y++] = this.get(j, i);
            }
            x++;
            y=0;
        }
        
        return data;
    }
    
    /**
     * 
     * @param y int number of column
     * @return array object T 
     */
    public T[] rowToArray(int y) {
         List<T> rows = this.contents.get(y);
         
         return (T[]) rows.toArray();
    } 
}
