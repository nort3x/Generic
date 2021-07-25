package objects;

import java.util.List;
import java.util.stream.Collectors;

import basics.AnswerVisitor;

public class EntekhabiAnswer extends AnswerVisitor<EntekhabiQuestion> {

  List<Integer> selectedAnswers;
  public EntekhabiAnswer(List<Integer> selectedAnswers){
    this.selectedAnswers = selectedAnswers.stream().sorted().collect(Collectors.toList());
  }

  @Override
  public boolean checkAnswer(EntekhabiQuestion q) {
    return q.correctItems.equals(selectedAnswers);
  }

}
