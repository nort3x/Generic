package interfaces;

import objects.CharGozineii;
import objects.EntekhabiQuestion;

public interface QuestionVisitor {
  void visit(EntekhabiQuestion entekhabiQuestion);
  void visit(CharGozineii charGozineii);
}