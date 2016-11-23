package priv.jj.lf2u.system;

import org.junit.Test;
import priv.jj.lf2u.entity.Manager;

import static org.junit.Assert.*;

/**
 * Created by adrianoob on 11/21/16.
 */
public class ManagerSystemTest {
    private ManagerSystem ms = ManagerSystem.INSTANCE;

    @Test
    public void viewAllAccountTest() {
        Manager[] mngrs = ms.getManagers();
        assertEquals("verify managers step 1", 1, mngrs.length);
        assertEquals("verify managers step 2", "0", mngrs[0].getMid());
    }

    @Test
    public void getAccountByMidTest() {
        assertEquals("manager with correct name", "Admin", ms.managerOfMid("0").getName());
    }
}