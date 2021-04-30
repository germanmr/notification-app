package com.javainuse.controller;

import com.javainuse.dto.User;
import com.javainuse.services.GitHubLookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tests")
public class TestAsyncController {

    private static final Logger logger = LoggerFactory.getLogger(TestAsyncController.class);

    private final GitHubLookupService gitHubLookupService;

    @Autowired
    public TestAsyncController(GitHubLookupService gitHubLookupService) {
        this.gitHubLookupService = gitHubLookupService;
    }


    static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> com) {
        return CompletableFuture.allOf(com.toArray(new CompletableFuture<?>[0]))
                .thenApply(v -> com.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
                );
    }

    @GetMapping("/run")
    public void run() throws InterruptedException, ExecutionException {
        // Start the clock
        long start = System.currentTimeMillis();

        boolean few = false;

        if (few) {
            logger.info("Starting with few");

            // Kick of multiple, asynchronous lookups
            CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
            CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
            CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

            // Wait until they are all done
            CompletableFuture.allOf(page1, page2, page3).join();

            // Print results, including elapsed time
            logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
            logger.info("--> " + page1.get());
            logger.info("--> " + page2.get());
            logger.info("--> " + page3.get());
        } else {

            logger.info("Starting with A LOT");

            List<CompletableFuture<User>> calls = new ArrayList<>();

            for (int i = 0; i < 18; i++) {
                calls.add(gitHubLookupService.findUser("Spring-Projects"));
            }

            try {
                CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(sequence(calls));
                System.out.println("combinedFuture.get(): " + combinedFuture.get());

                calls.forEach(userCompletableFuture -> {
                    try {
                        System.out.println("userCompletableFuture.get(): " + userCompletableFuture.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                });

                // Print results, including elapsed time
                logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
                logger.info("Result: " + combinedFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Exception occurred while running in parallel", e);
            } catch (Exception e) {
                throw new RuntimeException("Exception occurred while running in parallel", e);
            }

        }


    }

}
