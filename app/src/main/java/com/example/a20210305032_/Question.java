package com.example.a20210305032_;

import java.util.List;

public class Question {
    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
        if (choiceList == null || choiceList.isEmpty()) {
            throw new IllegalArgumentException("Choice list cannot be null or empty");
        }
        if (answerIndex < 0 || answerIndex >= choiceList.size()) {
            throw new IllegalArgumentException("Answer index out of bounds");
        }

        this.mQuestion = question;
        this.mChoiceList = choiceList;
        this.mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
        this.mQuestion = question;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public void setChoiceList(List<String> choiceList) {
        if (choiceList == null || choiceList.isEmpty()) {
            throw new IllegalArgumentException("Choice list cannot be null or empty");
        }
        this.mChoiceList = choiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public void setAnswerIndex(int answerIndex) {
        if (answerIndex < 0 || answerIndex >= mChoiceList.size()) {
            throw new IllegalArgumentException("Answer index out of bounds");
        }
        this.mAnswerIndex = answerIndex;
    }
}