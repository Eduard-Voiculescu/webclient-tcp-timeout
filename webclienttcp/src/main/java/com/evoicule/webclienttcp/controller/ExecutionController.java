package com.evoicule.webclienttcp.controller;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ExecutionController {

    private final WebClient webClient;

    public ExecutionController() {
        int timeout = 2100000;
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .responseTimeout(Duration.ofMillis(timeout))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS)));

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    @GetMapping("/tcpExecute")
    public ResponseEntity<String> tcpExecution(@RequestParam String ms) {
        log.info("Starting call to tcp executor ...");

        String tcpExecutorUrl = "http://localhost:9001/execute";

        try {
            log.info("Will sleep for {} ms", ms);
            Mono<ResponseEntity<String>> response =
                    webClient
                            .get()
                            .uri(new URI(tcpExecutorUrl + "?ms=" + ms))
                            .exchangeToMono(clientResponse -> clientResponse.toEntity(String.class));
            return response.block();
        } catch (Exception e) {
            log.info("Timeout...");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
