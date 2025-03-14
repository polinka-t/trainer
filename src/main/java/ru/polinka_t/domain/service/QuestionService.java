package ru.polinka_t.domain.service;

import org.springframework.stereotype.Service;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<OpenQuestionCard> getAll() {
        return repository.findAll();
    }

    public Optional<OpenQuestionCard> getById(Long id) {
        return repository.findById(id);
    }

    public boolean contains(OpenQuestionCard task) {
        return repository.findById(task.getId()).isPresent();
    }

    public void save(OpenQuestionCard task) {
        if (isTaskInvalid(task)) {
            throw new IllegalArgumentException("Вопрос не может быть пустым");
        }
        repository.add(task);
    }

    public void delete(Long id) {
        repository.remove(id);
    }

    private boolean isTaskInvalid(OpenQuestionCard task) {
        return task.getQuestion().isBlank() || task.getExpectedAnswer().isBlank();
    }
}

