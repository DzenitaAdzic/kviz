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
import model.Pitanje;
import service.KorisnikService;
import service.KvizService;

@WebServlet(description = "Kvizovi api", urlPatterns = { "/admin/api/pitanja" })
public class PitanjaApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private KvizService kvizService;

	public PitanjaApi() {
		super();
		kvizService = new KvizService(new KvizDao());
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String kvizId = request.getParameter("kvizId");
		Kviz kviz = kvizService.findById(Integer.parseInt(kvizId));
		
		response.setContentType("application/json");
	    PrintWriter out = response.getWriter();	   
	    List<Pitanje> svaPitanja = kviz.getPitanja();
	    List<HashMap<String, Object> > svaPitanjaMap = new ArrayList<HashMap<String, Object> >();
	    for(Pitanje p : svaPitanja) {
	    	svaPitanjaMap.add(p.toMap());
	    }
	    out.print(new Gson().toJson(svaPitanjaMap));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");		
		Pitanje p = Helper.parsePitanjeFromRequest(request);		
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
			int status = 0;
			if (kvizService.removePitanje(p.getId())) {
				status = 1;
			}
			jo.addProperty("status", status);			
			jo.addProperty("message", status == 0 ? "Dogodila se greska" : "OK");
			response.setStatus(status == 0 ? 400 : 200);
		    out.print(new Gson().toJson(jo));
		    return;			
		case "CREATE":
			String kvizId = request.getParameter("kvizId");
			Kviz k = kvizService.findById(Integer.parseInt(kvizId));
			k.getPitanja().add(p);			
			int statusCreate = 0;
			if(kvizService.save(k)) {
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