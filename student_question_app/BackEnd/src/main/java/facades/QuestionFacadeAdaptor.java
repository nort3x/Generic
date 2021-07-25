package facades;

import basics.Question;
import exceptions.MalformedQuestion;
import objects.CharGozineii;
import objects.EntekhabiQuestion;

import java.util.List;

public class QuestionFacadeAdaptor {

    private enum Type {
        CharGozine,
        Entekhabi
    }

    Integer correctAns4Gozine = null;
    List<Integer> correctAnsEntekhabi = null;
    List<String> answers = null;
    String question = null;
    Type k = null;


    public QuestionFacadeAdaptor(CharGozineii charGozineii){
        correctAns4Gozine =  charGozineii.getCorrectChoice();
        question = charGozineii.getQuestion();
        answers = charGozineii.getAnswers();
        k = Type.CharGozine;
    }
    public QuestionFacadeAdaptor(EntekhabiQuestion entekhabiQuestion){
        answers = entekhabiQuestion.getAnswers();
        correctAnsEntekhabi = entekhabiQuestion.getCorrectItems();
        question = entekhabiQuestion.getQuestion();
        k = Type.Entekhabi;
    }


    public Question getQuestion(){
        return switch (k){
            case Entekhabi -> new EntekhabiQuestion(answers,question, correctAnsEntekhabi);
            case CharGozine -> {
                try {
                    yield new CharGozineii(answers,question, correctAns4Gozine);
                } catch (MalformedQuestion malformedQuestion) {
                    malformedQuestion.printStackTrace();
                   yield null;
                }
            }
        };
    }
    
    public QuestionFacadeAdaptor(){
    }
}
