package net.goose.lifesteal.util;

import net.minecraft.network.chat.Component;

public class ComponentUtil {
    public static Component addComponents(Component component, Component component1, boolean includeSemi){
        if(includeSemi){
            return Component.nullToEmpty(component.getString() + " " + component1.getString());
        }else {
            return Component.nullToEmpty(component.getString() + "; " + component1.getString());
        }
    }

    public static Component addComponents(Component component, Component component1){
        return addComponents(component, component1, true);
    }
}
