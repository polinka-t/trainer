package ru.polinka_t.domain.model;

public class OpenQuestionCard {
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(String question, String expectedAnswer) {
        if (question == null || expectedAnswer == null) {
            throw new IllegalArgumentException("На вход поданы пустые значения");
        }

        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion(){
        return question;
    }
    public boolean checkAnswer(String answer){
        if (answer == null) {
            throw new IllegalArgumentException("Ответ не должен быть пустым");
        }
        return answer.equals(expectedAnswer);
    }

}
