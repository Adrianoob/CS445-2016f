package priv.jj.lf2u.entity;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/23/16.
 */
public class FarmStoreProductTest {
    @Test
    public void testDateMethods() {
        FarmStoreProduct pro = new FarmStoreProduct("1", "1", "1", "01-01", "10-10", 1, "1", "");

        pro.setStart_date("02-02");
        pro.setEnd_date("12-12");
        assertEquals("start date correct", "02-02", pro.getStart_date());
        assertEquals("end date correct", "12-12", pro.getEnd_date());
    }
}