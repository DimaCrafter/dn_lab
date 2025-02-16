package ru.csit.shiryaev_lab.gateway.remote;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CoreClient {
    private final WebClient core;
    public CoreClient (Environment env) {
        var baseUrl = env.getProperty("remote.core");
        assert baseUrl != null;

        core = WebClient.create(baseUrl);
    }

    public Mono<String> request (HttpMethod method, String path) {
        return core.method(method).uri(path).retrieve().bodyToMono(String.class);
    }

    public <P> Mono<String> request (HttpMethod method, String path, P payload) {
        return core.method(method).uri(path).bodyValue(payload).retrieve().bodyToMono(String.class);
    }
}
