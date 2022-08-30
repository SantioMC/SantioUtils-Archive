package me.santio.test.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
public enum InventoryItemList {
    
    TEST_1(Material.DIRT, "&7"),
    TEST_2(Material.DIAMOND, "&7"),
    TEST_3(Material.POTATO, "&7"),
    TEST_4(Material.GRANITE, "&7"),
    TEST_5(Material.LAPIS_BLOCK, "&7"),
    TEST_6(Material.RED_WOOL, "&7"),
    TEST_7(Material.BLUE_WOOL, "&7"),
    TEST_8(Material.GREEN_WOOL, "&7"),
    TEST_9(Material.YELLOW_WOOL, "&7"),
    TEST_10(Material.WHITE_WOOL, "&7"),
    TEST_11(Material.BLACK_WOOL, "&7"),
    TEST_12(Material.BROWN_WOOL, "&7"),
    TEST_13(Material.PINK_WOOL, "&7");
    
    private final Material material;
    private final String name;
}
