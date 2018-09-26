package no.hakon.serversandsockets;

import java.io.UnsupportedEncodingException;

public class HttpPath {
	
	// Test-Input: /myapp/echo?status=402&body=vi%20plukker%20bl%C3%A5b%C3%A6r
	String input;

	public HttpPath(String string) {
		try {
			input = java.net.URLDecoder.decode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// Return the path, split before first ? 
	
	public String getPath() {
		return input.split("\\?", 2)[0];
	}

	// Return the path split into an array of Strings each representing a part of the path
	
	public String [] getPathParts() {
		return input.split("\\?", 2)[0].replaceFirst("/", "").split("/");
	}
	
	// Return a query with the path excluded
	public HttpQuery getQuery(){
		if(input.contains("?"))
		{
		HttpQuery httpQuery = new HttpQuery(input.split("\\?", 2)[1]);
		return httpQuery; 
		}
		else return null;
	}

}
