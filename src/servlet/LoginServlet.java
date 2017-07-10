package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.KorisnikDao;
import helpers.Helper;
import model.Korisnik;
import service.KorisnikService;

@WebServlet(description = "Admin entry point", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KorisnikService korisnikService;

	public LoginServlet() {
		super();
		korisnikService = new KorisnikService(new KorisnikDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("logout") != null) {
			request.getSession().invalidate();
		}

		request.getRequestDispatcher("/login.html").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> requestBody = Helper.parseRequest(request);
        String username = requestBody.get("username");
        String password = requestBody.get("password");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		JsonObject jo = new JsonObject();
		if(username == null) {
			jo.addProperty("status", 0);			
			jo.addProperty("message", "Polje username je obavezno.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;
		}
		if(password == null) {
			jo.addProperty("status", 0);			
			jo.addProperty("message", "Polje password je obavezno.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;
		}
		
		Korisnik korisnik = korisnikService.authenticate(username, password);
		if (korisnik != null) {
			request.getSession().setAttribute("user", korisnik);
			jo.addProperty("status", 1);			
			jo.addProperty("message", "OK");
			response.setStatus(200);
		    out.print(new Gson().toJson(jo));
		    return;
		} else {
			jo.addProperty("status", 0);			
			jo.addProperty("message", "Pogresni login podaci.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;
		}
	}

}