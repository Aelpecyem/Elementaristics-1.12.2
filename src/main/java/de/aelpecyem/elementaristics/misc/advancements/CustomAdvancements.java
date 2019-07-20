package de.aelpecyem.elementaristics.misc.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CustomAdvancements {
    private static Method CriterionRegister;
    public static class Advancements {
        /**
         * Usually fired via code, when the player ascends
         * Code usages: when the Mirror of the Soul is used, the Silver Thread is slain, ascension rites being performed...
         */
        public static AscensionTrigger ASCEND;
    }

    public static void init(){
        CustomAdvancements.Advancements.ASCEND = (AscensionTrigger) registerAdvancementTrigger(new AscensionTrigger());
    }

    public static <T extends ICriterionInstance> ICriterionTrigger<T> registerAdvancementTrigger(ICriterionTrigger<T> trigger) {
        if(CriterionRegister == null) {
            CriterionRegister = ReflectionHelper.findMethod(CriteriaTriggers.class, "register", "func_192118_a", ICriterionTrigger.class);
            CriterionRegister.setAccessible(true);
        }
        try {
            trigger = (ICriterionTrigger<T>) CriterionRegister.invoke(null, trigger);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            System.out.println("Failed to register trigger " + trigger.getId() + "!");
            e.printStackTrace();
        }
        return trigger;
    }

}
