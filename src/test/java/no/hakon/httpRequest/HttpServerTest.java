package no.kristiania.prg200.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class HttpServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        try(ServerSocket serverSocket = new ServerSocket(10080)) {
            Thread thread = new Thread(() -> runServer(serverSocket));
            thread.start();
            thread.join();
        }
    }

    public static void runServer(ServerSocket serverSocket) {
        while (true) {
            try {
                Socket socket = serverSocket.accept();

                StringBuilder requestLine = new StringBuilder();

                // Reads the first line
                int c;
                while ((c = socket.getInputStream().read()) != -1) {
                    if (c == '\r') {
                        break;
                    }
                    requestLine.append((char)c);
                }
                System.out.println(requestLine.toString());

                String statusCode = "200";
                String body = "Hello world!";
                String location = "http://www.google.com";

                // Writes the response
                socket.getOutputStream().write(("HTTP/1.1 " + statusCode + " OK\r\n").getBytes());
                socket.getOutputStream().write("Content-Type: text/html; charset=utf-8\r\n".getBytes());
                if (location != null) {
                    socket.getOutputStream().write(("Location: " + location + "\r\n").getBytes());
                }
                socket.getOutputStream().write("Server: Kristiania Java Server!!\r\n".getBytes());
                socket.getOutputStream().write(("Content-Length: " + body.length() + "\r\n").getBytes());
                socket.getOutputStream().write("\r\n".getBytes());
                socket.getOutputStream().write((body + "\r\n").getBytes());
                socket.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static HttpEchoServer server;

    @BeforeClass
    public static void startServer() throws IOException {
        server = new HttpEchoServer(0);
    }

    @Test
    @Ignore("Fjern denne @Ignore-linja når du er klar for å gå videre")
    public void shouldHandleRequest() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=200");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    @Ignore("Fjern denne @Ignore-linja når du er klar for å gå videre")
    public void shouldEchoStatusCode() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(), "/echo?status=404");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(404);
    }

    @Test
    @Ignore("Fjern denne @Ignore-linja når du er klar for å gå videre")
    public void shouldEchoResponseHeaders() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?status=307&Location=http%3A%2F%2Fwww.kristiania.no");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(307);
        assertThat(response.getHeader("Location")).isEqualTo("http://www.kristiania.no");
    }

    @Test
    @Ignore("Fjern denne @Ignore-linja når du er klar for å gå videre")
    public void shouldEchoResponseBody() throws IOException {
        HttpRequest request = new HttpRequest("localhost", server.getPort(),
                "/echo?body=Hello+Kristiania!");
        HttpResponse response = request.execute();

        assertThat(response.getStatusCode()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Hello Kristiania!");
    }
}
