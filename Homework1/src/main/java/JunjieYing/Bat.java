package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public class Bat extends Creature implements Flyer {
    public Bat(String name) {
        super(name);
    }

    @Override
    public void eat(Thing aThing) {
        if (aThing instanceof Creature)
            super.eat(aThing);
        else if (aThing instanceof Thing)
            System.out.println(this + " won't eat a " + aThing + ".");
    }

    @Override
    public void move() {
        fly();
    }

    @Override
    public void fly() {
        System.out.println(this + " is swooping through the dark.");
    }
}
