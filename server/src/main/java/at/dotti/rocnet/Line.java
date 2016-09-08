package at.dotti.rocnet;

/**
 * Created by stefan on 08.09.2016.
 */
public class Line {

    private String type;
    private int number;

    public Line(String type, int number) {
        this.type = type;
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
