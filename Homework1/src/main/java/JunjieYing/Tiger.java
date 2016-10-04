package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public class Tiger extends Creature {
    public Tiger(String name) {
        super(name);
    }

    @Override
    public void move() {
        System.out.println(this + " has just pounced.");
    }


}
