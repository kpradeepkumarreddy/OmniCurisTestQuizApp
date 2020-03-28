package com.omnicuris.quizapp.models;


import java.io.Serializable;
import java.util.List;

public class QuestionTO implements Serializable {
    private String question = null;
    private List<String> options = null;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
