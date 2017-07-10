package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Pitanje;

public class Helper {
	
	public static Map<String, String> parseRequest(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader;
		try {
			reader = request.getReader();
			String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		    System.out.println("Request data: " + data);
		    if (data == null || data.length() == 0) {
		    		return new HashMap<String, String>();
		    }
		    java.lang.reflect.Type type = new TypeToken<Map<String, String>>(){}.getType();
		    Map<String, String> map = new Gson().fromJson(data, type);
		    return map;
		} catch (IOException e) {
			System.out.println("Parsing error: " + e.getMessage());
			return null;
		}	    
	}
	
	public static Pitanje parsePitanjeFromRequest(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader;
		try {
			reader = request.getReader();
			String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		    System.out.println("Request data: " + data);
		    Gson g = new Gson();
			Pitanje p = g.fromJson(data, Pitanje.class);
			return p;
		} catch (IOException e) {
			System.out.println("Parsing error: " + e.getMessage());
			return null;
		}	    
	}	
	
	public static RezultatJson parseRezultatiRequest(HttpServletRequest request) {
		StringBuilder buffer = new StringBuilder();
	    BufferedReader reader;
		try {
			reader = request.getReader();
			String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		    System.out.println("Request data: " + data);
		    Gson g = new Gson();
		    RezultatJson p = g.fromJson(data, RezultatJson.class);
			return p;
		} catch (IOException e) {
			System.out.println("Parsing error: " + e.getMessage());
			return null;
		}	    
	}
	
	public class RezultatJson {
		public String email;
		public int kvizId;
		public List<RezultatObject> odgovori;
	};
	
	public class RezultatObject {
		public int pitanjeId;
		public List<Integer> odgovori;
	};
}
