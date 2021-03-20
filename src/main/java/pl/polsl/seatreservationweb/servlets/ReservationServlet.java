package pl.polsl.seatreservationweb.servlets;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.seatreservationweb.model.CinemaHall;
import pl.polsl.seatreservationweb.model.CombinationOfPlace;
import pl.polsl.seatreservationweb.model.CombinationOfPlaceRepository;
import pl.polsl.seatreservationweb.model.Prospector;
import pl.polsl.seatreservationweb.model.SeatReservationException;
import pl.polsl.seatreservationweb.view.HtmlPageRender;

/**
 *
 * @author Piotr KUźnik
 * @version 2.0
 */
@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {

    /**
     * Obeject EntityManager
     */
    private EntityManager entityManager;

    /**
     * Initial Servlet
     *
     * @throws ServletException throw when server is error
     */
    @Override
    public void init() throws ServletException {
        super.init();

        System.out.print("init w reserwacjach");
        ServletContext context = this.getServletContext();
        this.entityManager = (EntityManager) context.getAttribute("entityManager");
        if (this.entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl_SeatReservationWeb_war_5.0-SNAPSHOTPU");
            this.entityManager = emf.createEntityManager();

            context.setAttribute("entityManager", this.entityManager);
            System.out.print("utworzenie em w reserwacjach");
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF8");
        HtmlPageRender page = new HtmlPageRender(response.getWriter());
        page.operationCounter(request, response);
        page.renderFormReservation();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        HtmlPageRender page = new HtmlPageRender(response.getWriter());
        int quantityOfChairs;
        CombinationOfPlace combinateOfPlace;

        page.operationCounter(request, response);

        String quantityOfChairsString = request.getParameter("quantityOfChairs");
        if (quantityOfChairsString == null) {
            response.addHeader("Location", "/");
            return;
        }

        try {
            quantityOfChairs = Integer.parseInt(quantityOfChairsString);
        } catch (NumberFormatException e) {
            page.renderException("Wprowadzony parametr nie jest liczbą!");

            return;
        }

        CombinationOfPlaceRepository repository;
        Prospector prospector;
        CinemaHall hall = new CinemaHall(10, 10);
        try {
            repository = new CombinationOfPlaceRepository(this.entityManager);
        } catch (SeatReservationException e) {
            page.renderException("Wystąpił błąd: " + e.getMessage());

            return;
        }

        repository.getAll().forEach(entity -> {
            hall.reserveCombinateOfPlace(entity);
        });

        try {
            prospector = new Prospector(hall, 0, 0);
        } catch (SeatReservationException e) {
            page.renderException("Wystąpił błąd: " + e.getMessage());

            return;
        }

        try {
            combinateOfPlace = prospector.findNextToTheChairNextToYou(quantityOfChairs);
            repository.add(combinateOfPlace);
            hall.reserveCombinateOfPlace(combinateOfPlace);
        } catch (SeatReservationException e) {
            page.renderException("Wystąpił błąd podczas szukania miejsca: " + e.getMessage());

            return;
        }

        page.renderReservation(combinateOfPlace);
    }

}
