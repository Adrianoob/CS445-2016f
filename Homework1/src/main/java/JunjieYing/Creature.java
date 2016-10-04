package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public abstract class Creature extends Thing{
    private Thing last = null;
    public Creature(String name) {
        super(name);
    }

    public void eat(Thing aThing) {
        last = aThing;
        System.out.println(this + " has just eaten a " + aThing + ".");
    }

    abstract void move();

    public void whatDidYouEat() {
        if (last == null)
            System.out.println(this + " has had nothing to eat!");
        else
            System.out.println(this + " has eaten a " + last + ".");
    }
}
