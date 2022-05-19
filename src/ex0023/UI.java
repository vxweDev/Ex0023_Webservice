package ex0023;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UI {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        HttpClient client1 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://orf.at"))
                .GET()
                .build();

        System.out.println("--> client1.send(...)");
        HttpResponse<String> response1 = client1.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(""+response1);
        System.out.println(""+response1.body());
        System.out.println("---");

        ExecutorService executor = Executors.newCachedThreadPool();
        HttpClient client2 = HttpClient.newBuilder().executor(executor).build();
        client2.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenAccept(response2 -> {
            System.out.println("response2");
            System.out.println(""+response2);
            System.out.println(""+response2.body());
        });
        System.out.println("---");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
}
