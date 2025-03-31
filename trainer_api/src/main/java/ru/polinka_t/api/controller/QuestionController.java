package ru.polinka_t.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import ru.polinka_t.api.dto.OpenQuestionCardDto;
import ru.polinka_t.api.mapper.QuestionDtoMapper;
import ru.polinka_t.domain.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private final QuestionService service;

    private final QuestionDtoMapper mapper;

    public QuestionController(QuestionService service, QuestionDtoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Operation(
            summary = "List all questions",
            description = "Get list of all stored questions without filtering"
    )
    @GetMapping("")
    List<OpenQuestionCardDto> getAll() {
        return mapper.mapToDto(service.getAll());
    }

    @Operation(
            summary = "Get question by id",
            description = "Get a specific question using passed id"
    )
    @GetMapping("/{id}")
    Optional<OpenQuestionCardDto> getById(@PathVariable Long id) {
        return service.getById(id).map(mapper::mapToDto);
    }

    @Operation(
            summary = "Add question",
            description = "Store a question passed as JSON object"
    )
    @PostMapping("")
    void save(@RequestBody OpenQuestionCardDto question) {
        service.save(mapper.mapToModel(question));
    }

    @Operation(
            summary = "Remove question",
            description = "Remove a specific question using passed id"
    )
    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        service.delete(id);
    }
}