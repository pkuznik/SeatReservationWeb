package pl.polsl.seatreservationweb.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * This class is representation simple reservation on N chairs
 *
 * @author Piotr Kuźnik
 * @version 5.1
 */
@Entity
public class CombinationOfPlace implements Serializable {

    /**
     * serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Indetificator in db
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * beginning of the reservation range in the row
     */
    private int startX;

    /**
     * beginning of the reservation range in the column
     */
    private int startY;

    /**
     * end of reservation range in a row
     */
    private int endX;

    /**
     * end of reservation range in a column
     */
    private int endY;

    /**
     * Constructor
     */
    public CombinationOfPlace() {
    }

    /**
     * Constructor
     *
     * @param startX Number chair start to reserved in row
     * @param startY Number chair start to reserved in column
     * @param endX Number chair end to reserved in row
     * @param endY Number chair end to reserved in column
     */
    public CombinationOfPlace(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    /**
     * Get indetificator of entity in DB
     *
     * @return number
     */
    public Long getId() {
        return id;
    }

    /**
     * Set indetificator of entity in DB
     *
     * @param id number
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method return beginning of the reservation range in the row
     *
     * @return int
     */
    public int getStartX() {
        return startX;
    }

    /**
     *
     * @param startX Number chair start to reserved in row
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Method return beginning of the reservation range in the column
     *
     * @return int
     */
    public int getStartY() {
        return startY;
    }

    /**
     *
     * @param startY Number chair start to reserved in column
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Method return end of reservation range in a row
     *
     * @return int
     */
    public int getEndX() {
        return endX;
    }

    /**
     *
     * @param endX Number chair end to reserved in row
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * Method return end of reservation range in a column
     *
     * @return int
     */
    public int getEndY() {
        return endY;
    }

    /**
     *
     * @param endY Number chair end to reserved in column
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CombinationOfPlace)) {
            return false;
        }
        CombinationOfPlace other = (CombinationOfPlace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CombinationOfPlace(" + this.startX + ", " + this.startY + "," + this.endX + ", " + this.endY + ")";
    }
}
