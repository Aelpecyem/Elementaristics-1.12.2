package de.aelpecyem.elementaristics.config;

import de.aelpecyem.elementaristics.Elementaristics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.DummyConfigElement.DummyCategoryElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ElementaristicsConfigFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ElementaristicsConfigGUI(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return runtimeGuiCategories();
    }

    public static class ElementaristicsConfigGUI extends GuiConfig {

        public ElementaristicsConfigGUI(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), Elementaristics.MODID, false, false, I18n.format("gui.config.title"));
        }

        private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<>();
            //add general config once convenient
            list.add(new DummyCategoryElement("gui.config.category.compat", "gui.config.category.compat", CategoryEntryGeneral.class));
            list.add(new DummyCategoryElement("gui.config.category.dimensions", "gui.config.category.dimensions", CategoryEntryGeneral.class));
            list.add(new DummyCategoryElement("gui.config.category.misc", "gui.config.category.misc", CategoryEntryGeneral.class));
            list.add(new DummyCategoryElement("gui.config.category.client", "gui.config.category.client", CategoryEntryGeneral.class));
            return list;
        }

        public static class CategoryEntryGeneral extends GuiConfigEntries.CategoryEntry {
            public CategoryEntryGeneral(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement configElement) {
                super(owningScreen, owningEntryList, configElement);
            }

            @Override
            protected GuiScreen buildChildScreen() {
                Configuration config = Elementaristics.config;
                ConfigElement categoryGeneral = new ConfigElement(config.getCategory(Config.CATEGORY_COMPAT));
                List<IConfigElement> propertiesOnScreen = categoryGeneral.getChildElements();
                String windowTitle = I18n.format("gui.config.category.compat");
                return new GuiConfig(owningScreen, propertiesOnScreen, owningScreen.modID,
                        this.configElement.requiresWorldRestart() ||
                                this.owningScreen.allRequireWorldRestart, this.configElement.requiresMcRestart() ||
                        this.owningScreen.allRequireMcRestart, windowTitle);
            }
        }
    }
}
