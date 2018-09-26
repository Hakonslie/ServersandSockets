package no.hakon.serversandsockets;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public class HttpQuery {

	private HashMap<String, String> parameters;
	
	// Constructor splits the query into &'s and then by ='s to seperate key and value, puts them into a HashMap parameters
	
	public HttpQuery(String query)
	{
		
		parameters = new HashMap<String, String>();
		for(String q : query.split("&")) {
			if(q.split("=")[0].equals("body")) {
					parameters.put("body", q.split("=")[1]);
			}
			else {
			parameters.put(q.split("=")[0].toLowerCase(), q.split("=")[1]);
			}
		}
		if(getParameter("status").equals(" ")){
			parameters.put("status", "200");
		}
	}
	
	// method for getting parameters that match the String.
	
	
	public String getParameter(String parameter) {
		
		if(parameter == null || parameter == "") return "";		
		for(HashMap.Entry<String, String> par : parameters.entrySet()) {
			
			if(par.getKey().equals(parameter)) return par.getValue();
		}		
		return " ";
		
	}

}
