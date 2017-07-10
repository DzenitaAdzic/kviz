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

@WebServlet(description = "Kvizovi api", urlPatterns = { "/admin/api/kvizovi" })
public class KvizoviApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KvizService kvizService;

	public KvizoviApi() {
		super();
		kvizService = new KvizService(new KvizDao());
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

	    List<Kviz> sviKvizovi = kvizService.findByKorisnikId(korisnik.getId());
	    System.out.println("SVI KVIZOVI: " + sviKvizovi.size());
	    List<HashMap<String, String> > sviKvizoviMap = new ArrayList<HashMap<String, String> >();
	    for(Kviz k : sviKvizovi) {
	    		sviKvizoviMap.add(k.toMap());
	    }
	    out.print(new Gson().toJson(sviKvizoviMap));
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
			String kvizId = requestBody.get("kvizId");
			if(kvizId == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje kvizId je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			int status = 0;
			if (kvizService.remove(Integer.parseInt(kvizId))) {
				status = 1;
			}
			jo.addProperty("status", status);			
			jo.addProperty("message", status == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(status == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;	
		case "SETACTIVE":
			String kvizId1 = requestBody.get("kvizId");
			if(kvizId1 == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje kvizId je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			int status1 = 0;
			Kviz kvizEdit = kvizService.findById(Integer.parseInt(kvizId1));
			kvizEdit.setIsActive(1);
			if (kvizService.save(kvizEdit)) {
				status1 = 1;
			}
			jo.addProperty("status", status1);			
			jo.addProperty("message", status1 == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(status1 == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;
		case "SETINACTIVE":
			String kvizId2 = requestBody.get("kvizId");
			if(kvizId2 == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje kvizId je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			int status2 = 0;
			Kviz kvizEdit2 = kvizService.findById(Integer.parseInt(kvizId2));
			kvizEdit2.setIsActive(0);
			if (kvizService.save(kvizEdit2)) {
				status1 = 1;
			}
			jo.addProperty("status", status2);			
			jo.addProperty("message", status2 == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(status2 == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;	
		case "CREATE":
			String naziv = requestBody.get("naziv");
			String active = requestBody.get("active");
			if(naziv == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje naziv je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			if(active == null) {
				jo.addProperty("status", 0);			
				jo.addProperty("message", "Polje aktivan je obavezno.");
				response.setStatus(400);
			    out.print(new Gson().toJson(jo));
			    return;
			}
			Kviz k = new Kviz();
			k.setNaziv(naziv);
			k.setIsActive(Boolean.parseBoolean(active) ? 1 : 0);
			if(request.getSession().getAttribute("user")!=null) {
				Korisnik korisnik = (Korisnik) request.getSession().getAttribute("user");
				k.setKorisnik(korisnik);
			}
			System.out.println("KVIZ: " + k);
			int statusCreate = 0;
			if(kvizService.create(k)) {
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