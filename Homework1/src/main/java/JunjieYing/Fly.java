package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public class Fly extends Creature implements Flyer{
    public Fly(String name) {
        super(name);
    }

    @Override
    public void eat(Thing aThing) {
        if (aThing instanceof Creature)
            System.out.println(this + " won't eat a " + aThing + ".");
        else
            super.eat(aThing);
    }

    @Override
    public void move() {
        fly();
    }

    public void fly() {
        System.out.println(this + " is buzzing around in flight.");
    }
}
