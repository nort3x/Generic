import basics.Question;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.QuestionFacadeAdaptor;
import objects.EntekhabiQuestion;
import objects.Exam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import exceptions.AlreadyExist;
import exceptions.MalformedQuestion;
import objects.CharGozineii;
import objects.CharGozineiiAnswer;

public class SerializationTest {

  @Test void SerializeExam() throws MalformedQuestion {
    CharGozineii c = new CharGozineii(List.of("1","2","3","4"),"which",1);
    EntekhabiQuestion entekhabiQuestion = new EntekhabiQuestion(List.of("1","2","3","4","5"),"which",List.of(1,2,3));
    Exam m = new Exam(List.of(new QuestionFacadeAdaptor(c),new QuestionFacadeAdaptor(entekhabiQuestion)));
    Gson g = new GsonBuilder().setPrettyPrinting().create();
    System.out.println(g.toJson(m));
  }
}
