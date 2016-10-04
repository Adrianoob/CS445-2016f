package JunjieYing;

/**
 *
 */

public class TestCreature {
    private static final int CREATURE_COUNT = 6;
    private static final int THING_COUNT = 10;

    public TestCreature() {

    }

    public static void main(String [] args) {
        // test1
        System.out.println("Things:\n");
        Thing [] array = new Thing[10];
        for (int i = 0; i < 10; i++) {
            array[i] = new Thing("something" + i);
        }

        for (Thing t : array) {
            System.out.println(t);
        }

        System.out.println("\nCreatures:\n");
        // test2
        Creature [] ca = new Creature[6];
        ca[0] = new Tiger("Tggr");
        ca[1] = new Ant("Aant");
        ca[2] = new Bat("Baat");
        ca[3] = new Fly("Flyy");
        ca[4] = new Fly("Flll");
        ca[5] = new Bat("Bttt");
        for (Creature c : ca) {
            System.out.println(c);
        }
    }
}
