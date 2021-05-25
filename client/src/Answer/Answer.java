package Answer;

import Control.Outputer;

import java.io.Serializable;

public class Answer implements Serializable {

    private String stringAnswer;
    private final AnswerStatus answerStatus;
    private Object objectAnswer;


    public Answer(String stringAnswer, AnswerStatus answerStatus) {
        this.stringAnswer = stringAnswer;
        this.answerStatus = answerStatus;

    }

    public Answer(Object objectAnswer, AnswerStatus answerStatus) {
        this.answerStatus = answerStatus;
        this.objectAnswer = objectAnswer;
    }

    public void printStringAnswer(){
        if (stringAnswer!=null) Outputer.println(stringAnswer);
    }

    public AnswerStatus getAnswerStatus() {
        return answerStatus;
    }

    public Object getObjectAnswer() {
        return objectAnswer;
    }

}
