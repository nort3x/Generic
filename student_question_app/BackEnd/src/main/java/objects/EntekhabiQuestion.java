package objects;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import basics.Question;
import interfaces.QuestionVisitor;

public class EntekhabiQuestion extends Question {

  List<String> answers;
  String question;
  List<Integer> correctItems;
  public EntekhabiQuestion(List<String> answers, String question,List<Integer> correctItems) {
    this.answers = answers;
    this.question = question;
    this.correctItems = correctItems.stream().sorted().collect(Collectors.toList());
  }

  @Override
  public void getVisitedBy(QuestionVisitor qv) {
    qv.visit(this);
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

  public List<Integer> getCorrectItems() {
    return correctItems;
  }
}
