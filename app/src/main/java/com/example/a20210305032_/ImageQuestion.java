// Updating multiple Java files from a project.

// ImageQuestion.java
package com.example.a20210305032_;

import java.util.List;

public class ImageQuestion {
    private String mImageQuestion;
    private int mAnswerIndex;
    private int mImage;
    private List<String> mChoiceList;

    public ImageQuestion(String imageQuestion, int image, List<String> choiceList, int answerIndex) {
        if (imageQuestion == null || imageQuestion.isEmpty()) {
            throw new IllegalArgumentException("Image question cannot be null or empty");
        }
        if (choiceList == null || choiceList.isEmpty()) {
            throw new IllegalArgumentException("Choice list cannot be null or empty");
        }
        if (answerIndex < 0 || answerIndex >= choiceList.size()) {
            throw new IllegalArgumentException("Answer index out of bounds");
        }

        mAnswerIndex = answerIndex;
        this.mImageQuestion = imageQuestion;
        this.mImage = image;
        this.mChoiceList = choiceList;
    }

    public String getImageQuestion() {
        return mImageQuestion;
    }

    public void setImageQuestion(String imageQuestion) {
        if (imageQuestion == null || imageQuestion.isEmpty()) {
            throw new IllegalArgumentException("Image question cannot be null or empty");
        }
        this.mImageQuestion = imageQuestion;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
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