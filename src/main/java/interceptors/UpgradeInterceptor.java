package interceptors;

import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.util.concurrent.Callable;

import necesse.engine.registries.ObjectRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.level.gameObject.CraftingStationUpgrade;

public class UpgradeInterceptor {
    public static class Interceptor {
        public static CraftingStationUpgrade intercept(@SuperCall Callable<CraftingStationUpgrade> original) {
            System.out.println("Intercepted getStationUpgrade call! Big success!"
            		+ "");
            return new CraftingStationUpgrade(ObjectRegistry.getObject("fallenworkstation"),
    				new Ingredient[]{new Ingredient("upgradeshard", 15), new Ingredient("alchemyshard", 15)});
        }
    }
}