package me.santio.test.utils;

import me.santio.utils.inventory.Slots;
import org.junit.Assert;
import org.junit.Test;

public class InventoryTest {
    
    @Test
    public void slotsTest() {
        Slots slots = Slots.column(1).add(Slots.row(1));
        Assert.assertEquals(17, slots.size());
    }
    
}
