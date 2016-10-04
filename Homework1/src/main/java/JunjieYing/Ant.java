package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public class Ant extends Creature{
    public Ant(String name) {
        super(name);
    }

    @Override
    void move() {
        System.out.println(this + " is crawling around.");
    }
}
