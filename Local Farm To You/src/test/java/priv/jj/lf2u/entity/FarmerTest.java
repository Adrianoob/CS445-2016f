package priv.jj.lf2u.entity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/23/16.
 */
public class FarmerTest {
    @Test
    public void testGetDeliversTo() {
        Farmer f = new Farmer("", "", "", "" ,"", "", "", new String[] {"60616", "60010"});

        assertArrayEquals("correct zip zone", new String[] {"60616", "60010"}, f.getDeliversTo());
    }

    public void testDeliveryChargeMethods() {
        Farmer f = new Farmer("", "", "", "" ,"", "", "", new String[] {"60616", "60010"});
        assertEquals("delivery charge is 0", 0, f.getDeliveryCharge(), 0);

        f.setDeliveryCharge(9.95);
        assertEquals("delivery charge has changed", 9.95, f.getDeliveryCharge(), 0);
    }
}