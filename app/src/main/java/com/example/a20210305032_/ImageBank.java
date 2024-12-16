package com.example.a20210305032_;

import java.util.Collections;
import java.util.List;

public class ImageBank {

    private List<ImageQuestion> mImageQuestionList;
    private int mNextImageQuestionIndex;

    public ImageBank(List<ImageQuestion> imageQuestionList) {
        mImageQuestionList = imageQuestionList;

        Collections.shuffle(mImageQuestionList);

        mNextImageQuestionIndex = 0;
    }

    public ImageQuestion getImageQuestion() {
        if (mNextImageQuestionIndex == mImageQuestionList.size()) {
            mNextImageQuestionIndex = 0;
        }

        return mImageQuestionList.get(mNextImageQuestionIndex++);
    }

}