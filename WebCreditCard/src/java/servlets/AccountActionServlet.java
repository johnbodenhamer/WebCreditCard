package servlets;

import business.CreditCard;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ryan
 */
public class AccountActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String URL = "/CardTrans.jsp";
        String message = "";
        CreditCard card;
        String path
                = getServletContext().getRealPath("/WEB-INF/") + "\\";

        String action = request.getParameter("actiontype");
        card
                = (CreditCard) request.getSession().getAttribute("card");
        if (card == null && !action.equalsIgnoreCase("NEW")
                && !action.equalsIgnoreCase("EXISTING")) {
            message = "Action attempt when no account is open.<br>";
        } else {
            if (action.equalsIgnoreCase("NEW")) {
                //message = "New account requestetd<br>";
                card = new CreditCard(path);
                if (card.getErrorStatus()) {
                    message = "Account Error"
                            + card.getErrorMessage() + "<br>";
                } else {
                    message = card.getActionMsg() + "<br>";
                }
            }
            if (action.equalsIgnoreCase("EXISTING")) {

                String account = request.getParameter("account");
                card = new CreditCard(Integer.parseInt(account), path);
                //request.getParameter(action)
            }
            if (action.equalsIgnoreCase("CHARGE")) {
                try {
                    Double dAmt = Double.parseDouble(request.getParameter("cAmt"));
                    card.setCharge(dAmt, request.getParameter("cDesc"));
                } catch (NumberFormatException e) {
                    message = "Invalid input.<br>";
                }
                //request.getParameter(action)
            }
            if (action.equalsIgnoreCase("PAYMENT")) {
                try {
                    card.setPayment(Double.parseDouble(request.getParameter("pAmt")));
                } catch (NumberFormatException e) {
                    message = "Invalid input.<br>";
                }
            }
            if (action.equalsIgnoreCase("INCREASE")) {
                try {
                    card.setCreditIncrease(Double.parseDouble(request.getParameter("cIncrease")));
                } catch (NumberFormatException e) {
                    message = "Invalid input.<br>";
                }
            }
            if (action.equalsIgnoreCase("INTEREST")) {
                try {
                    card.setInterestCharge(Double.parseDouble(request.getParameter("iRate")));
                } catch (NumberFormatException e) {
                    message = "Invalid input.<br>";
                }
            }

        }// other actions processed here
        if (action.equalsIgnoreCase("HISTORY")) {
            URL = "/History.jsp";
        }
        request.getSession().setAttribute("card", card);
        Cookie acctcookie = new Cookie("acct",
                String.valueOf(card.getAccountId()));
        acctcookie.setMaxAge(60 * 2);
        acctcookie.setPath("/");
        response.addCookie(acctcookie);

//possible issue here
        if (!message.isEmpty()) {
            request.setAttribute("message", message);
        }

        RequestDispatcher disp
                = getServletContext().getRequestDispatcher(URL);

        disp.forward(request, response);

    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
