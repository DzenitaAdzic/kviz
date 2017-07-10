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

@WebServlet(description = "Kvizovi api", urlPatterns = { "/admin/api/rezultati" })
public class RezultatiKlijentApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RezultatService rezultatService;

	public RezultatiKlijentApi() {
		super();
		rezultatService = new RezultatService(new RezultatDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();	   
	    List<Rezultat> sviRezultati = rezultatService.findAll();
	    List<HashMap<String, String> > sviRezultatiMap = new ArrayList<HashMap<String, String> >();
	    for(Rezultat r : sviRezultati) {
	    		sviRezultatiMap.add(r.toMap());
	    }
	    out.print(new Gson().toJson(sviRezultatiMap));
	}
	
}