package net.goose.lifesteal.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ComponentUtil {
    public static MutableComponent addComponents(MutableComponent... components){
        MutableComponent currentComponent = components[0];
        for(int index = 1; index < components.length; index++) {
            if(index == components.length - 1)
                currentComponent.append(components[index]);
        }
        return currentComponent;
    }
}
