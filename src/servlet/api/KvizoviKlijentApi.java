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
import helpers.Helper;
import model.Korisnik;
import model.Kviz;
import service.KorisnikService;
import service.KvizService;

@WebServlet(description = "Kvizovi api", urlPatterns = { "/api/kvizovi" })
public class KvizoviKlijentApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KvizService kvizService;

	public KvizoviKlijentApi() {
		super();
		kvizService = new KvizService(new KvizDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();	   
	    List<Kviz> sviKvizovi = kvizService.findAllActive();
	    List<HashMap<String, String> > sviKvizoviMap = new ArrayList<HashMap<String, String> >();
	    for(Kviz k : sviKvizovi) {
	    		sviKvizoviMap.add(k.toMap());
	    }
	    out.print(new Gson().toJson(sviKvizoviMap));
	}
	
}