package ru.csit.shiryaev_lab.gateway.controllers;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.csit.shiryaev_lab.gateway.payloads.ItemEditPayload;
import ru.csit.shiryaev_lab.gateway.remote.CoreClient;


@RestController
public class ItemsController {
    @GetMapping(path = "/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Mono<String> getItems () {
        return CoreClient.request(HttpMethod.GET, "/items");
    }

    @PutMapping("/items")
    public final Mono<String> createItem (@RequestBody ItemEditPayload payload) {
        return CoreClient.request(HttpMethod.PUT, "/items", payload);
    }

    @GetMapping(path = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Mono<String> getItem (@PathVariable String id) {
        return CoreClient.request(HttpMethod.GET, "/items/" + id);
    }


    @PatchMapping("/items/{id}")
    public final Mono<String> updateItem (@PathVariable String id, @RequestBody ItemEditPayload payload) {
        return CoreClient.request(HttpMethod.PATCH, "/items/" + id, payload);
    }

    @DeleteMapping("/items/{id}")
    public final Mono<String> deleteItem (@PathVariable String id) {
        return CoreClient.request(HttpMethod.DELETE, "/items/" + id);
    }
}
