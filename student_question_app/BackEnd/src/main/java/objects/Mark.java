package objects;

public class Mark {

  private final int mark;
  private final long date;

  public Mark(int mark, long date) {
    this.mark = mark;
    this.date = date;
  }


  public int getMark() {
    return mark;
  }

  public long getDate() {
    return date;
  }
}
