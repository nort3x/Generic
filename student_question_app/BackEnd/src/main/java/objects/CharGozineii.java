package objects;

import basics.Question;
import exceptions.MalformedQuestion;
import interfaces.QuestionVisitor;

import java.util.List;

public class CharGozineii extends Question {
    List<String> answers;String question;Integer correctChoice;

    public CharGozineii(List<String> answers, String question, Integer correctChoice) throws MalformedQuestion {
        this.answers = answers;
        this.question = question;
        this.correctChoice = correctChoice;
        if(answers.size()!=4)
            throw new MalformedQuestion("should have exactly 4 answers");
    }

    public CharGozineii(){}

    @Override
    public void getVisitedBy(QuestionVisitor qv) {
        qv.visit(this);
    }

    public Integer getCorrectChoice() {
        return correctChoice;
    }

    @Override
    public String provideQuestionDescription() {
        return question;
    }

    @Override
    public List<String> providedAnswers() {
        return answers;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }
}
