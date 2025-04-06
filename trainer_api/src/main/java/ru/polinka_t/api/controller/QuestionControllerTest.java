package ru.polinka_t.api.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.polinka_t.api.controller.QuestionController;
import ru.polinka_t.api.dto.OpenQuestionCardDto;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.repo.QuestionRepository;

import java.util.List;

@SpringBootTest
class QuestionControllerTest {
    @MockitoBean
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionController questionController;

    @Test
    @DisplayName("Question is created and added to repo")
    void testQuestionController() {
        Mockito.when(questionRepository.findAll())
                .thenReturn(List.of(new OpenQuestionCard( "What time is it?", "You know", 10L)));
        List<OpenQuestionCardDto> list = questionController.getAll();
        Assertions.assertEquals(1, list.size());
        Assertions.assertEquals(12L, list.getFirst().getId());
        Assertions.assertEquals("What time is it?", list.getFirst().getQuestion());
        Assertions.assertEquals("You know", list.getFirst().getExpectedAnswer());
    }
}