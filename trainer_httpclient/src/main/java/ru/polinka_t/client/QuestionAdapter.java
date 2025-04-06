package main.java.ru.polinka_t.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.polinka_t.api.dto.OpenQuestionCardDto;
import ru.polinka_t.api.mapper.QuestionDtoMapper;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.repo.QuestionRepository;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.net.HttpURLConnection.HTTP_OK;

@Repository
public class QuestionAdapter implements QuestionRepository {
    private static final Logger logger = LogManager.getLogger(QuestionAdapter.class);

    private final HttpClient httpClient;
    private final QuestionDtoMapper questionDtoMapper;
    private final String serverUrl;
    private final Gson gson;

    public QuestionAdapter(QuestionDtoMapper questionDtoMapper, @Value("${trainer.interactions.server_url}") String serverUrl) {
        httpClient = HttpClient.newBuilder().build();
        this.questionDtoMapper = questionDtoMapper;
        this.serverUrl = serverUrl;
        gson = new GsonBuilder().create();
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCard> questions = Collections.emptyList();
        try {
            String url = getRootUrl();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            if (validateResponse(response)) {
                List<OpenQuestionCardDto> questionDtos =
                        gson.fromJson(
                                response.body(),
                                new TypeToken<List<OpenQuestionCardDto>>() {}.getType()
                        );
                questions = questionDtoMapper.mapToModel(questionDtos);
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return questions;
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        Optional<OpenQuestionCard> question = Optional.empty();
        try {
            String url = getRootUrl() + '/' + id.toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (validateResponse(response)) {
                var dto = gson.fromJson(response.body(), OpenQuestionCardDto.class);
                question = Optional.of(questionDtoMapper.mapToModel(dto));
            }
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
        return question;
    }

    @Override
    public void add(OpenQuestionCard question) {
        OpenQuestionCardDto questionDto = questionDtoMapper.mapToDto(question);
        String body = gson.toJson(question);
        try {
            String url = getRootUrl();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void update(OpenQuestionCard task) {
        OpenQuestionCardDto questionDto = questionDtoMapper.mapToDto(task);
        String body = gson.toJson(questionDto);
        try {
            String url = getRootUrl();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .headers("Content-Type", "application/json;charset=UTF-8")
                    .PUT(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void remove(Long id) {
        try {
            String url = getRootUrl() + '/' + id.toString();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .DELETE()
                    .build();
            httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private String getRootUrl() {
        return serverUrl + "/question";
    }

    private boolean validateResponse(HttpResponse<String> response) {
        return response != null
                && response.statusCode() == HTTP_OK
                && response.body() != null
                && !response.body().isEmpty();
    }
}