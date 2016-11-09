package priv.jj.lf2u.role;

import org.junit.Test;

/**
 * Farmer Test
 */
public class FarmerTest {
    @Test
    public void createAFarmer() {
        Farmer farmer1 = new Farmer("John Smith", "john.smith@example.com", "123-456-7890",
                "SafeHouse Farm Alpacas", "25550 W Cuba Rd, Barrington, IL 60010",
                "847-651-2140", "http://www.openherd.com/",
                new String[]{"60010", "60011", "60067"});

    }
}