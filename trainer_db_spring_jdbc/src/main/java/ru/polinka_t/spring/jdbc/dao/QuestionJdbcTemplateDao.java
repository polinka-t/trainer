package ru.polinka_t.spring.jdbc.dao;

import org.springframework.stereotype.Repository;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.repo.QuestionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class QuestionJdbcTemplateDao implements QuestionRepository {
    private final Map<Long, OpenQuestionCard> cards = new HashMap<>();
    @Override
    public List<OpenQuestionCard> findAll() {
        return cards.values().stream().toList();
    }
    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        return Optional.ofNullable(cards.get(id));
    }
    @Override
    public void add(OpenQuestionCard openQuestionCard) {
        cards.put(openQuestionCard.getId(), openQuestionCard);
    }
    @Override
    public void update(OpenQuestionCard openQuestionCard) {
        cards.put(openQuestionCard.getId(), openQuestionCard);
    }
    @Override
    public void remove(Long id) {
        cards.remove(id);
    }
}

