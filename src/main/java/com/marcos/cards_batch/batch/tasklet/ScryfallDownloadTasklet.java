package com.marcos.cards_batch.batch.tasklet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ScryfallDownloadTasklet implements Tasklet {
    private static final String URL = "https://api.scryfall.com/bulk-data/all-cards";
    private final HttpClient client = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper;

    public ScryfallDownloadTasklet(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public @Nullable RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) 
        throws Exception {
            HttpResponse<String> response = requestGetHttp(URI.create(URL), HttpResponse.BodyHandlers.ofString());
            JsonNode root = objectMapper.readTree(response.body());
            String downloadUri = root.get("download_uri").asText();

            HttpResponse<InputStream> downloadResponse = requestGetHttp(URI.create(downloadUri), 
                HttpResponse.BodyHandlers.ofInputStream());

            Path target = Paths.get("data", "cards.json");
            Files.createDirectories(target.getParent());

            try (InputStream in = downloadResponse.body()) {
                Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
            }

            return RepeatStatus.FINISHED;
    }

    private <T> HttpResponse<T> requestGetHttp(
        URI uri, HttpResponse.BodyHandler<T> bodyHandler) throws IOException, InterruptedException{
            HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("User-Agent", "SpellTrade-Batch/1.0")
                .header("Accept", "application/json")
                .GET()
                .build();
            
            log.info("Requesting  {}", uri);
            HttpResponse<T> response = client.send(request, bodyHandler);

            if(response.statusCode() == 404) {
                log.atError()
                    .setMessage("Not Found - {} - {}")
                    .addArgument(response.statusCode())
                    .addArgument(response.body())
                    .log();;

                throw new IOException("Not found - " + response.statusCode());
            } else if (response.statusCode() != 200) {
                log.atError()
                    .setMessage("Error - {} - {}")
                    .addArgument(response.statusCode())
                    .addArgument(response.body())
                    .log();;
                throw new IOException("Error: " + response.statusCode());
            }

            return response;
    }
}
    
