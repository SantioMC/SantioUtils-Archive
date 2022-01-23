package me.santio.utils.inventories.buttons;

import lombok.experimental.Accessors;
import me.santio.utils.CustomItem;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Accessors(chain = true)
public class EnumButton extends ListButton {
    
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public <T extends Enum<T>> EnumButton(CustomItem item, T current, Class<T> aEnum, Consumer<T> updated) {
        super(item, Arrays.stream(aEnum.getEnumConstants()).map(Enum::name).collect(Collectors.toList()), current.name(), (val) -> updated.accept(Arrays.stream(aEnum.getEnumConstants()).filter(e -> e.name().equals(val)).findFirst().get()));
    }
    
}
