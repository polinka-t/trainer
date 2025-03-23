package ru.polinka_t.dao;

import ru.polinka_t.domain.model.OpenQuestionCard;

import java.sql.PreparedStatement;
import org.springframework.stereotype.Repository;

import ru.polinka_t.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class QuestionJdbcDao implements QuestionRepository {
    private static final String DDL_QUERY = """
          CREATE TABLE questions (id bigint PRIMARY KEY, question VARCHAR(64), expected_answer VARCHAR(256))
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, question, expected_answer FROM questions
          """;
    private static final String FIND_BY_ID_QUERY = """
          SELECT id, question, expected_answer FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUESTION_QUERY = """
          INSERT INTO questions(id, question, expected_answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_QUESTION_QUERY = """
          UPDATE questions SET id=?, question=?, expected_answer=?
          """;
    private static final String DELETE_QUESTION_QUERY = """
          DELETE FROM questions WHERE id=?
          """;
    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initDataBase();
    }

    public void initDataBase() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DDL_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OpenQuestionCard> findAll() {
        List<OpenQuestionCard> tasks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                OpenQuestionCard task = new OpenQuestionCard(
                        resultSet.getString("question"),
                        resultSet.getString("expected_answer"),
                        resultSet.getLong("id")
                );
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    @Override
    public Optional<OpenQuestionCard> findById(Long id) {
        List<OpenQuestionCard> tasks = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY);) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OpenQuestionCard task = new OpenQuestionCard(
                        resultSet.getString("question"),
                        resultSet.getString("expected_answer")  ,
                        resultSet.getLong("id")
                );
                tasks.add(task);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks.isEmpty() ? Optional.empty() : Optional.of(tasks.getFirst());
    }

    @Override
    public void add(OpenQuestionCard task) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION_QUERY);){
            statement.setLong(1, task.getId());
            statement.setString(2, task.getQuestion());
            statement.setString(3, task.getExpectedAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpenQuestionCard task) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUESTION_QUERY);){
            statement.setLong(1, task.getId());
            statement.setString(2, task.getQuestion());
            statement.setString(3, task.getExpectedAnswer());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUESTION_QUERY);){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

