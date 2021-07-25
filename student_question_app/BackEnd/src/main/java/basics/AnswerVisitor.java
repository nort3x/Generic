package basics;

public abstract class AnswerVisitor<T extends Question> {
    public abstract boolean checkAnswer(T q);
}
