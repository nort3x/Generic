package basics;

import exceptions.AlreadyExist;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public abstract class AnswerNode extends VBox {
    private Consumer<Boolean> c;
    private Boolean b = null;
    public void consumeAnswerSensor(Consumer<Boolean> c){
        this.c = c;
    }
    public boolean finalizeAnswer() throws AlreadyExist {
        if(b == null) {
            b = onFinalAnswerCalled();
            c.accept(b);
            return b;
        }else
            throw new AlreadyExist("already answered");

    }

    public abstract boolean onFinalAnswerCalled();
}
