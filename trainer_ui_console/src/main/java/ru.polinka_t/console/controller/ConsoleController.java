package ru.polinka_t.console.controller;


import org.springframework.stereotype.Controller;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.service.QuestionService;

import java.util.Scanner;
import java.util.Optional;

@Controller
public class ConsoleController {
    private static final String MENU = """
        Введите [1], чтобы показать все вопросы
        Введите [2], чтобы добавить вопрос
        Введите [3], чтобы  удалить вопрос
        Введите [4], чтобы найти нужный вопрос
        Введите [5], чтобы выйти
        """;

    private final QuestionService service;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleController(QuestionService service) {
        this.service = service;
    }

    public void interactWithUser() {
        while(true) {
            printMenu();
            String operationCode = scanner.nextLine();
            switch (operationCode) {
                case "1" -> printAllTasks();
                case "2" -> addTask();
                case "3" -> removeTask();
                case "4" -> findTask();
                case "5" -> { return; }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu() {
        System.out.println(MENU);
    }

    private void printAllTasks() {
        System.out.println(service.getAll());
    }

    private void addTask() {
        System.out.println("Введите id");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println("Введите текст задания");
        String question = scanner.nextLine();
        System.out.println("Введите ожидаемый ответ");
        String expectedAnswer = scanner.nextLine();
        OpenQuestionCard task = new OpenQuestionCard(question, expectedAnswer, id);
        service.save(task);
    }

    private void removeTask() {
        System.out.println("Введите id задания, которое хотите удалить");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> task = service.getById(id);
        if (task.isPresent()) {
            System.out.println("Введите [Y], если точно хотите удалить задание " + task.get());
            String confirmation = scanner.nextLine();
            if ("Y".equals(confirmation)) {
                service.delete(task.get().getId());
            }
        } else {
            System.out.println("Не удалось найти задание с таким id");
        }
    }

    private void findTask() {
        System.out.println("Введите id задания, которое хотите найти");
        Long id = Long.parseLong(scanner.nextLine());
        Optional<OpenQuestionCard> task = service.getById(id);
        if (task.isPresent()) {
            System.out.println(task.get());
        } else {
            System.out.println("Не удалось найти задание с таким id");
        }
    }
}


