package core;


import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.recipe.Ingredient;
import net.bytebuddy.asm.Advice;
import necesse.level.gameObject.CraftingStationObject;
import necesse.level.gameObject.CraftingStationUpgrade;
import necesse.level.gameObject.FallenWorkstationObject;

import net.bytebuddy.ByteBuddy;

import net.bytebuddy.matcher.ElementMatchers;

import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;


// This mod is not a real mod. It's an example showing how to force the fallen workstation to have an upgrade.

@ModEntry
public class ExampleMod {

    public void postInit() {
        System.out.println("IT'S THE WORKSTATION WORKSTATION WORKSTATION WORKSTATION");
        
        // Install Byte Buddy agent (required for runtime class modification)
        ByteBuddyAgent.install();

        try {
            new ByteBuddy()
                .rebase(CraftingStationObject.class) // Rebase preseves the original class.
                .visit(Advice.to(GetStationUpgradeInterceptor.class)
                    .on(ElementMatchers.named("getStationUpgrade"))) // Intercept this method in every instance of CraftingStationObject
                .make()
                .load(CraftingStationObject.class.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());

            System.out.println("Successfully intercepted getStationUpgrade()!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
      
    }

    public void initResources() {

    }

    
    public static class GetStationUpgradeInterceptor {
        @Advice.OnMethodExit
        static void onExit(@Advice.This Object thisObject, @Advice.Return(readOnly = false) CraftingStationUpgrade returnValue) {
            System.out.println("Intercepted getStationUpgrade call!");

    
            if (thisObject instanceof FallenWorkstationObject) {
            	
                // Modify the return value only for FallenWorkstationObject.
                returnValue = new CraftingStationUpgrade(ObjectRegistry.getObject("tungstenworkstation"),
                        new Ingredient[]{new Ingredient("tungstenbar", 8), new Ingredient("quartz", 4)});
                System.out.println("Modified return value for FallenWorkstationObject!");
            }
        }
    }

}
