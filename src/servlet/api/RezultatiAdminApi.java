package servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.KorisnikDao;
import dao.KvizDao;
import dao.RezultatDao;
import helpers.Helper;
import model.Korisnik;
import model.Kviz;
import model.Rezultat;
import service.KorisnikService;
import service.KvizService;
import service.RezultatService;

@WebServlet(description = "Kvizovi api", urlPatterns = { "/api/rezultati" })
public class RezultatiAdminApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RezultatService rezultatService;

	public RezultatiAdminApi() {
		super();
		rezultatService = new RezultatService(new RezultatDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();
	    JsonObject jo = new JsonObject();
	    if(request.getSession().getAttribute("user")==null) {
	    		jo.addProperty("status", 0);			
			jo.addProperty("message", "Nepoznat korisnik.");
			response.setStatus(400);
		    out.print(new Gson().toJson(jo));
		    return;			
		}
	    
	    Korisnik korisnik = (Korisnik) request.getSession().getAttribute("user");

	    ArrayList<Rezultat> sviRezultati = new ArrayList();
	    for(Kviz k : korisnik.getKvizovi()) {
	    		sviRezultati.addAll(k.getRezultati());
	    }
	    List<HashMap<String, String> > sviRezultatiMap = new ArrayList<HashMap<String, String> >();
	    for(Rezultat r : sviRezultati) {
	    		sviRezultatiMap.add(r.toMap());
	    }
	    out.print(new Gson().toJson(sviRezultatiMap));
	}
	
}