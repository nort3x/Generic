package objects;

import basics.Question;
import facades.QuestionFacadeAdaptor;

import java.util.List;

public class Exam {

    final private List<QuestionFacadeAdaptor> questionList;

    public Exam(List<QuestionFacadeAdaptor> questionList){
        this.questionList = questionList;
    }

    public List<QuestionFacadeAdaptor> getQuestionList() {
        return questionList;
    }
}
