package net.goose.lifesteal.util;

import net.minecraft.network.chat.Component;

public class ComponentUtil {
    public static Component addComponents(Component component, Component component1){
        return Component.literal(component.getString() + "; " + component1.getString());
    }
}
