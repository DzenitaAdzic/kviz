package servlet.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
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
import helpers.Helper.RezultatJson;
import helpers.Helper.RezultatObject;
import model.Korisnik;
import model.Kviz;
import model.Odgovor;
import model.Pitanje;
import model.Rezultat;
import service.KorisnikService;
import service.KvizService;
import service.RezultatService;

@WebServlet(description = "Kvizovi api", urlPatterns = { "/api/pitanja" })
public class PitanjaKlijentApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KvizService kvizService;
	private RezultatService rezultatService;
	
	public PitanjaKlijentApi() {
		super();
		kvizService = new KvizService(new KvizDao());
		rezultatService = new RezultatService(new RezultatDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String kvizId = request.getParameter("kvizId");
	    PrintWriter out = response.getWriter();	   
	    Kviz kviz = kvizService.findById(Integer.parseInt(kvizId));
	    List<HashMap<String, Object> > svaPitanja = new ArrayList<HashMap<String, Object> >();
	    for(Pitanje p : kviz.getPitanja()) {
	    		svaPitanja.add(p.toMap());
	    }
	    out.print(new Gson().toJson(svaPitanja));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RezultatJson rezultati = Helper.parseRezultatiRequest(request);
		int bodovi = 0;
		for (RezultatObject rez : rezultati.odgovori) {
			Pitanje p = kvizService.findPitanjeById(rez.pitanjeId);
			boolean tacno = true;
			int brojTacnih = 0;
			for (Odgovor o : p.getOdgovori()) {
				if(o.getTacan() == 0) { continue; }
				brojTacnih++;
				if(!rez.odgovori.contains(o.getId())) {
					tacno = false;
					break;
				}
			}
			if (tacno && brojTacnih == rez.odgovori.size()) {
				bodovi+=p.getBodovi();
			}
		}
		Rezultat r = new Rezultat();
		r.setBodovi(new Integer(bodovi).toString());
		r.setDatum((int) (System.currentTimeMillis() / 1000L));
		r.setEmail(rezultati.email);
		r.setKviz(kvizService.findById(rezultati.kvizId));
		
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
		JsonObject jo = new JsonObject();
		int statusCreate = 0;
		if(rezultatService.create(r)) {
			statusCreate = 1;
		}
		jo.addProperty("status", statusCreate);			
		jo.addProperty("message", statusCreate == 0 ? "Dogodila se greska" : "OK");
		response.setStatus(statusCreate == 0 ? 400 : 200);
	    out.print(new Gson().toJson(jo));		    
	    return;
	}
}