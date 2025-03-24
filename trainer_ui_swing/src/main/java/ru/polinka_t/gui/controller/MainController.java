package ru.polinka_t.gui.controller;

import ru.polinka_t.gui.model.QuestionTableModel;
import org.springframework.stereotype.Controller;
import ru.polinka_t.domain.model.OpenQuestionCard;
import ru.polinka_t.domain.service.QuestionService;


import javax.swing.*;
import java.awt.*;
import java.util.Optional;

@Controller
public class MainController {
    private final QuestionService service;
    private JFrame mainFrame;
    private JPanel mainPanel;

    public MainController(QuestionService service) {
        this.service = service;
    }

    public void interactWithUser() {
        mainFrame = new JFrame("Time Management Tool");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(prepareMenu());
        prepareMainPanelForListTask();
        mainFrame.setVisible(true);
    }

    private void prepareMainPanelForAddQuestion(Optional<OpenQuestionCard> taskForEdit) {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }

        mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("Id"), layoutConstraints);
        JTextField idField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        mainPanel.add(idField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Вопрос"), layoutConstraints);
        JTextField questionField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        mainPanel.add(questionField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        mainPanel.add(new JLabel("Ожидаемый ответ"), layoutConstraints);
        JTextField expectedAnswerField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        mainPanel.add(expectedAnswerField, layoutConstraints);

        JButton addButton = new JButton("Добавить");
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 2;
        taskForEdit.ifPresent( t -> {
            idField.setText(t.getId().toString());
            questionField.setText(t.getQuestion());
            expectedAnswerField.setText(t.getExpectedAnswer());
        });
        addButton.addActionListener(event -> {
            OpenQuestionCard task = new OpenQuestionCard(
                    questionField.getText(),
                    expectedAnswerField.getText(),
                    Long.parseLong(idField.getText())
            );
            service.save(task);
            prepareMainPanelForListTask();
        });
        mainPanel.add(addButton, layoutConstraints);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private void prepareMainPanelForListTask() {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new BorderLayout());
        JTable table = new JTable(new QuestionTableModel(service.getAll()));
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private JMenuBar prepareMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu taskMenu = new JMenu("Task");
        JMenuItem addTask = new JMenuItem("Добавить вопрос");
        addTask.addActionListener((event) -> {
            prepareMainPanelForAddQuestion(Optional.empty());
        });
        taskMenu.add(addTask);
        menuBar.add(taskMenu);
        JMenuItem listTask = new JMenuItem("Просмотреть вопросы");
        listTask.addActionListener((event) -> {
            prepareMainPanelForListTask();
        });
        taskMenu.add(listTask);
        JMenuItem removeTask = new JMenuItem("Удалить вопрос");
        removeTask.addActionListener((event) -> {
            OpenQuestionCard taskToDelete = (OpenQuestionCard)JOptionPane.showInputDialog(
                    mainFrame,
                    "Какую задачу хотите удалить?",
                    "Удаление задачи",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            service.delete(taskToDelete.getId());
            prepareMainPanelForListTask();
        });
        taskMenu.add(removeTask);
        JMenuItem editTask = new JMenuItem("Обновить задачу");
        editTask.addActionListener((event) -> {
            OpenQuestionCard taskToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос хотите изменить?",
                    "Удаление вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            prepareMainPanelForAddQuestion(Optional.of(taskToDelete));
        });
        taskMenu.add(editTask);
        menuBar.add(taskMenu);
        return menuBar;
    }
}

