package basics;

import exceptions.AlreadyExist;
import interfaces.QuestionVisitor;

import java.util.List;

public abstract class Question {
    Boolean  answeredCorrectly = null;
    public Question() {
    }
    public boolean isAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public boolean setAnswer(AnswerVisitor a) throws AlreadyExist {
        if(answeredCorrectly!=null)
            throw new AlreadyExist("already answered");
        answeredCorrectly = a.checkAnswer(this);
        return answeredCorrectly;
    }
    public abstract void getVisitedBy(QuestionVisitor qv);
    public abstract String provideQuestionDescription();
    public abstract List<String> providedAnswers();

}
