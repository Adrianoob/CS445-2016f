package JunjieYing;
/**
 * Created by adrianoob on 9/13/16.
 */
public class Thing {
    private String name;
    public Thing(String name) {
        this.name = name;
    }


    public String toString() {
        String res = "";
        if (this.getClass().getSimpleName().equals("Thing"))
            res = name;
        else
            res = name + " " + this.getClass().getSimpleName();
        return res;
    }
}
