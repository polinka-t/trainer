package ru.polinka_t.domain.model;

import com.sun.jdi.LongValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpenQuestionCardTest {
    @Test
    @DisplayName("checkAnswer выводит True, если ответ совпадает с ожидаемым")
    void when_Answer_looks_like_expected_isTrue() {
        String question = "What is your favourite bank?";
        String expectedAnswer = "T-bank";
        OpenQuestionCard card = new OpenQuestionCard(question, expectedAnswer, 1L);
        assertTrue(card.checkAnswer("T-bank"));
    }

    @Test
    @DisplayName("checkAnswer выводит False, если ответ не совпадает с ожидаемым")
    void when_Answer_does_not_look_like_expected_isFalse() {
        String question = "How are you?";
        String expectedAnswer = "Awesome!";
        OpenQuestionCard card = new OpenQuestionCard(question, expectedAnswer, 2L);
        assertFalse(card.checkAnswer("so-so"));
    }

    @Test
    @DisplayName("checkAnswer выводит ошибку, если на вход передан null")
    void when_Answer_is_null_isException() {
        String question = "Ask something";
        String expectedAnswer = "Hello! How are you?";
        OpenQuestionCard card = new OpenQuestionCard(question, expectedAnswer, 3L);
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->  card.checkAnswer(null));
    }

    @Test
    @DisplayName("Создание класса выводит ошибку, если на вход передан question = null")
    void when_Question_is_null_isException() {
        String expectedAnswer = "Hello! How are you?";
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->  new OpenQuestionCard(null, expectedAnswer, 4L));
    }
    @Test
    @DisplayName("Создание класса выводит ошибку, если на вход передан expectedAnswer = null")
    void when_expectedAnswer_is_null_isException() {
        String question = "Ask something";
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->  new OpenQuestionCard(question, null, 5L));
    }
    @Test
    @DisplayName("Создание класса выводит ошибку, если на вход переданы null")
    void when_expectedAnswer_and_question_is_null_isException() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () ->  new OpenQuestionCard(null, null, 6L));
    }


}
