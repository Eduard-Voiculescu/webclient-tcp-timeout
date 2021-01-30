package com.evoicule.webclienttcpexecutor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ExecutorController {

    public ExecutorController () { }

    @GetMapping("/execute")
    public ResponseEntity<String> tcpExecutor (@RequestParam String ms) throws InterruptedException {
        log.info("Starting execution ...");
        log.info("Sleeping for {} milliseconds ...", ms);

        Thread.sleep(Long.parseLong(ms));
        log.info("Execution Successful");

        return ResponseEntity.ok("Execution Successful");
    }

}
