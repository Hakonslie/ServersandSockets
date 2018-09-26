package no.hakon.serversandsockets;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class HttpServerTest {

    private static HttpEchoServer server;

    @BeforeClass
    public static void startServer() throws IOException, InterruptedException {
        server = new HttpEchoServer(0);
    }

    @Test
    public void shouldHandleRequest() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=200");
        HttpResponse response = request.execute();
        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void shouldEchoStatusCode() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=404");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    public void shouldEchoResponseHeaders() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
    public void shouldEchoResponseBody() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }
}
