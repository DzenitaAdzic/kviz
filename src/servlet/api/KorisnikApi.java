package servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet(description = "Korisnici api", urlPatterns = { "/admin/api/korisnici" })
public class KorisnikApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KorisnikService korisnikService;

	public KorisnikApi() {
		super();
		korisnikService = new KorisnikService(new KorisnikDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();	    
	    out.print(new Gson().toJson(korisnikService.findAll()));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> requestBody = Helper.parseRequest(request);        
        String action = requestBody.get("action");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		JsonObject jo = new JsonObject();
		if(action == null) {
			jo.addProperty("status", 0);			
			jo.addProperty("message", "Polje action je obavezno.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;
		}
		
		switch(action) {
		case "DELETE":
			String userId = requestBody.get("userId");
			if(userId == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje userId je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			int status = 0;
			if (korisnikService.remove(Integer.parseInt(userId))) {
				status = 1;
			}
			jo.addProperty("status", status);			
			jo.addProperty("message", status == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(status == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;			
		case "CREATE":
			String username = requestBody.get("username");
			String password = requestBody.get("password");
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
			Korisnik k = new Korisnik();
			k.setUsername(username);
			k.setPassword(password);
			int statusCreate = 0;
			if(korisnikService.create(k)) {
				statusCreate = 1;
			}
			jo.addProperty("status", statusCreate);			
			jo.addProperty("message", statusCreate == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(statusCreate == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;
		default:
			jo.addProperty("status", 0);			
			jo.addProperty("message", "Nepoznata akcija.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;		
		}
	}
}