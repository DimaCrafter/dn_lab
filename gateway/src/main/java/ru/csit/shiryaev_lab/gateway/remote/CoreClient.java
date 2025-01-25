package ru.csit.shiryaev_lab.gateway.remote;

import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class CoreClient {
    private static final WebClient core = WebClient.create("http://127.0.0.1:8081");

    public static Mono<String> request (HttpMethod method, String path) {
        return core.method(method).uri(path).retrieve().bodyToMono(String.class);
    }

    public static <P> Mono<String> request (HttpMethod method, String path, P payload) {
        return core.method(method).uri(path).bodyValue(payload).retrieve().bodyToMono(String.class);
    }
}
