package no.hakon.serversandsockets;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class httpPathTest {
	@Test
	public void shouldParseUrl() {
	    HttpPath path = new HttpPath("/myapp/echo?status=402&body=vi%20plukker%20bl%C3%A5b%C3%A6r&location=www.lol.no");
	    assertThat(path.getPath()).isEqualTo("/myapp/echo");
	    assertThat(path.getPathParts()).containsExactly("myapp", "echo");
	    assertThat(path.getQuery().getParameter("status")).isEqualTo("402");
	    assertThat(path.getQuery().getParameter("body")).isEqualTo("vi plukker blåbær");
	    assertThat(path.getQuery().getParameter("location")).isEqualTo("www.lol.no");
	}
	
	@Test
	public void shouldParseUrlWithOnlyOneFolder() {
	    HttpPath path = new HttpPath("/echo?body=Hello+Kristiania!");
	    assertThat(path.getPath()).isEqualTo("/echo");
	    assertThat(path.getPathParts()).containsExactly("echo");
	    assertThat(path.getQuery().getParameter("status")).isEqualTo("200");
	    assertThat(path.getQuery().getParameter("body")).isEqualTo("Hello Kristiania!");
	}

}
