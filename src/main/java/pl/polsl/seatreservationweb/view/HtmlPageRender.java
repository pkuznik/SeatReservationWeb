package pl.polsl.seatreservationweb.view;

import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pl.polsl.seatreservationweb.model.CinemaHall;
import pl.polsl.seatreservationweb.model.CombinationOfPlace;

/**
 * Class to render html on web page
 *
 * @author Piotr Kuźnik
 * @version 1.0
 */
public class HtmlPageRender {

    /**
     * @var PrintWriter obejct printerWriter can write on outstream
     */
    private final PrintWriter out;

    /**
     * @var int
     */
    private int operationCounter = 0;

    /**
     * Constructor
     *
     * @param output obejct printerWriter can write on outstream
     */
    public HtmlPageRender(PrintWriter output) {
        this.out = output;
    }

    /**
     *
     * @param request object instance of HttpServletRequest
     * @param response object instance of HttpServletResponse
     */
    public void operationCounter(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("operationCounter")) {
                    try {
                        this.operationCounter = Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException exception) {
                        this.operationCounter = 0;
                    }
                    break;
                }
            }
        }

        this.operationCounter++;

        Cookie cookie = new Cookie("operationCounter", "" + this.operationCounter);
        response.addCookie(cookie);
    }

    /**
     *
     * @param message Message for send to view
     */
    public void renderException(String message) {
        this.renderHeader("Błąd!", "<div class=\"alert alert-danger\" role=\"alert\">\n"
                + "  Error! " + message + " \n"
                + "</div>");

        this.renderFooter();
    }

    /**
     * Render form to reserve
     */
    public void renderFormReservation() {
        this.renderHeader("Dokonaj rezerwacji", "Wypełnij poniższy formularz, aby dokonać rezerwacji.");

        this.out.println("<div class=\"container\"><div class=\"row\"><div class=\"col-md-12\">");
        this.out.println("<form action=\"reservation\" method=\"POST\">\n"
                + "<div class=\"form-group\">"
                + "<label for=\"quantityOfChairs\">Wprowadź ile miejsc chcesz zarezerwować: </label>\n"
                + "<input id=\"quantityOfChairs\" type=\"number\" name=\"quantityOfChairs\" min=\"1\" max=\"100\"/>\n"
                + "</div><button type=\"submit\" class=\"btn btn-primary\">Rezerwuj</button>\n"
                + "</form>");
        this.out.println("</div></div></div>");

        this.renderFooter();
    }

    /**
     * Render html with reservation details
     *
     * @param cooradinate object instance of CombinationOfPlace
     */
    public void renderReservation(CombinationOfPlace cooradinate) {
        this.renderHeader("Twoja rezerwacja", "Twoja rezerwacja");
        this.out.println("<div class=\"container\"><div class=\"row\"><div class=\"col-md-12\">");
        int i = 1;
        for (int numberRow = cooradinate.getStartY(); numberRow <= cooradinate.getEndY(); numberRow++) {
            for (int numberChair = cooradinate.getStartX(); numberChair <= cooradinate.getEndX(); numberChair++) {
                this.out.println("<p>Bilet " + (i++) + ": Rząd: " + numberRow + " Miejsce: " + numberChair + "</p>");
            }
        }

        this.out.println("</div></div></div>");
        this.renderFooter();
    }

    /**
     * render cinema room in html table
     *
     * @param cinemaHall object instance of CinemaHall
     * @param listEntities list of CombinationOfPlace
     */
    public void renderingHtmlTable(CinemaHall cinemaHall, List<CombinationOfPlace> listEntities) {
        this.renderHeader("Podgląd sali kinowej", "Aktualny stan sali kinowej");
        this.out.println("<div class=\"container\"><div class=\"row\"><div class=\"col-md-6\">");
        this.out.println("<h3>Sala kinowa o rozmiarze " + cinemaHall.getQuntityOfchairsInRow() + "x" + cinemaHall.getQuantityOfChairsInColumn() + "</h3>");

        this.out.print("<table><tr><td></td>");
        for (int x = 1; x < cinemaHall.getQuntityOfchairsInRow(); x++) {
            this.out.print("<td>" + x + "</td>");
        }
        this.out.println("</tr>");

        for (int y = 1; y <= cinemaHall.getQuantityOfChairsInColumn(); y++) {
            this.out.print("<tr><td>" + y + "</td>");
            for (int x = 1; x <= cinemaHall.getQuntityOfchairsInRow(); x++) {
                this.out.print("<td>");
                if (cinemaHall.isChairReserved(y, x)) {
                    this.out.print("[x]");
                } else {
                    this.out.print("[ ]");
                }

                this.out.print("</td>");
            }

            this.out.println("</tr>");
        }

        this.out.println("</table>");
        this.out.println("</div>");

        this.out.println("<div class=\"col-md-6\">");
        this.out.println("<h3>Lista zapisanych rezerwacji</h3>");

        this.out.print("<style>"
                + "#ListEntities td:nth-child(n) {"
                + " text-align:right;"
                + "}"
                + "</style>");
        this.out.print("<table id=\"ListEntities\" class=\"table\"><thead><tr><td>Id rezerwacji</td><td>Rezerwacja od</td><td>Rezerwacja do</td></tr></thead>");
        this.out.println("<tbody>");

        listEntities.forEach(entity -> {
            this.out.print("<tr>");
            this.out.print("<td>" + entity.getId() + "</td>");
            this.out.print("<td>Rząd: " + entity.getStartY() + ", Miejsce: " + entity.getStartX() + "</td>");
            this.out.print("<td>Rząd: " + entity.getEndY() + ", Miejsce: " + entity.getEndX() + "</td>");
            this.out.print("</tr>");
        });

        this.out.println("</tbody></table>");
        this.out.println("</div></div>");
        this.renderFooter();
    }

    /**
     *
     * @param titlePage title page visible on bar windows
     * @param subTitle description page
     */
    private void renderHeader(String titlePage, String subTitle) {
        this.out.println("<!doctype html>\n"
                + "<html lang=\"en\">\n"
                + "  <head>\n"
                + "    <!-- Required meta tags -->\n"
                + "    <meta charset=\"utf-8\">\n"
                + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n"
                + "    <!-- Bootstrap CSS -->\n"
                + "    <link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\">\n"
                + "\n"
                + "    <title>" + titlePage + "</title>\n"
                + "  </head>\n<body>\n");

        this.out.println("<div class=\"jumbotron\">\n"
                + "  <h1 class=\"display-4\">SeatReservation Web Edition!</h1>\n"
                + "  <p class=\"lead\">Serwis do zarządzania rezerwacjami miejsc w sali kinowej.</p>\n"
                + "  <hr class=\"my-4\">\n"
                + "  <p>" + subTitle + "</p>\n"
                + "  <a class=\"btn btn-primary btn-lg\" href=\"/SeatReservationWeb/reservation\" role=\"button\">Zarezerwuj miejsce</a>\n"
                + "  <a class=\"btn btn-secondary btn-lg\" href=\"/SeatReservationWeb/cinemaHall\" role=\"button\">Pokaż salę kinową</a>\n"
                + " <div class=\"alert alert-info\" role=\"alert\">\n  Liczba operacji: " + this.operationCounter + "</div>"
                + "</div>");
    }

    /**
     * Render footer page
     */
    private void renderFooter() {
        this.out.println("\n<body></html>");
    }
}
