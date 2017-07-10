package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/kviz")
public class KvizoviKlijentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KvizoviKlijentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("kvizId") == null) {
			request.getRequestDispatcher("/kvizoviKlijent.html").forward(request, response);
			return;
		}
		String kvizId = request.getParameter("kvizId");
		request.setAttribute("kvizId", kvizId);
		request.getRequestDispatcher("/kviz.jsp").forward(request, response);
	}
	
}