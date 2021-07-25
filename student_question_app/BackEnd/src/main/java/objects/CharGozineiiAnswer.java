package objects;

import basics.AnswerVisitor;
import basics.Question;

public class CharGozineiiAnswer extends AnswerVisitor<CharGozineii> {
    private final Integer answer;
    public CharGozineiiAnswer(Integer answer){
        this.answer = answer;
    }
    @Override
    public boolean checkAnswer(CharGozineii q) {
        return q.getCorrectChoice().equals(answer);
    }
}
