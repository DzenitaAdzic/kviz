package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Korisnik;

@WebServlet("/admin/home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("user")==null) {
			request.getRequestDispatcher("/login.html").forward(request, response);
    			return;			
		}
    
		Korisnik korisnik = (Korisnik) request.getSession().getAttribute("user");
		String home = "/home.html";
		if(korisnik.getSuperadmin() == 0) {
			home = "/homeEditor.html";
		}
		request.getRequestDispatcher(home).forward(request, response);
	}

}