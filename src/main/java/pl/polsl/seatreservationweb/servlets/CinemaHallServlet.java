/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.seatreservationweb.servlets;

import java.io.IOException;
import java.util.List;
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
import pl.polsl.seatreservationweb.model.SeatReservationException;
import pl.polsl.seatreservationweb.view.HtmlPageRender;

/**
 *
 * @author Piotr KUźnik
 * @version 2.0
 */
@WebServlet("/cinemaHall")
public class CinemaHallServlet extends HttpServlet {

    /**
     * Obeject EntityManager
     */
    private EntityManager entityManager;

    /**
     * Initials Servlet
     *
     * @throws ServletException throw when server is error
     */
    @Override
    public void init() throws ServletException {
        super.init();

        System.out.print("init  w hall");
        ServletContext context = this.getServletContext();
        this.entityManager = (EntityManager) context.getAttribute("entityManager");
        if (this.entityManager == null) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("pl.polsl_SeatReservationWeb_war_5.0-SNAPSHOTPU");
            this.entityManager = emf.createEntityManager();

            context.setAttribute("entityManager", this.entityManager);
            System.out.print("utworzenie em w hall");
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
        CombinationOfPlaceRepository repository;
        CinemaHall hall = new CinemaHall(10, 10);
        try {
            repository = new CombinationOfPlaceRepository(this.entityManager);
        } catch (SeatReservationException e) {
            page.renderException("Wystąpił błąd: " + e.getMessage());

            return;
        }

        List<CombinationOfPlace> entities = repository.getAll();

        entities.forEach(entity -> {
            hall.reserveCombinateOfPlace(entity);
        });

        page.operationCounter(request, response);
        page.renderingHtmlTable(hall, entities);
    }
}
