package nc.crafting.nei;

import nc.NuclearCraft;
import nc.block.NCBlocks;
import nc.gui.crafting.GuiNuclearWorkspace;
import nc.gui.generator.GuiFissionReactor;
import nc.gui.generator.GuiFissionReactorSteam;
import nc.gui.generator.GuiFusionReactor;
import nc.gui.generator.GuiFusionReactorSteam;
import nc.gui.machine.GuiCollector;
import nc.gui.machine.GuiCooler;
import nc.gui.machine.GuiHastener;
import nc.gui.machine.GuiHeliumExtractor;
import nc.gui.machine.GuiIoniser;
import nc.gui.machine.GuiIrradiator;
import nc.gui.machine.GuiNuclearFurnace;
import nc.gui.machine.GuiRecycler;
import nc.gui.machine.GuiSeparator;
import nc.item.NCItems;
import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.DefaultOverlayHandler;

public class NEINCConfig implements IConfigureNEI {

	public void loadConfig() {
		API.registerRecipeHandler(new NuclearFurnaceRecipeHandler());
		API.registerUsageHandler(new NuclearFurnaceRecipeHandler());
		API.registerRecipeHandler(new NuclearFurnaceFuelRecipeHandler());
		API.registerUsageHandler(new NuclearFurnaceFuelRecipeHandler());
		API.registerRecipeHandler(new RecyclerRecipeHandler());
		API.registerUsageHandler(new RecyclerRecipeHandler());
		API.registerRecipeHandler(new HastenerRecipeHandler());
		API.registerUsageHandler(new HastenerRecipeHandler());
		API.registerRecipeHandler(new SeparatorRecipeHandler());
		API.registerUsageHandler(new SeparatorRecipeHandler());
		API.registerRecipeHandler(new NuclearWorkspaceRecipeHandler());
		API.registerUsageHandler(new NuclearWorkspaceRecipeHandler());
		API.registerRecipeHandler(new FissionRecipeHandler());
		API.registerUsageHandler(new FissionRecipeHandler());
		API.registerRecipeHandler(new FissionSteamRecipeHandler());
		API.registerUsageHandler(new FissionSteamRecipeHandler());
		API.registerRecipeHandler(new CollectorRecipeHandler());
		API.registerUsageHandler(new CollectorRecipeHandler());
		API.registerRecipeHandler(new IoniserRecipeHandler());
		API.registerUsageHandler(new IoniserRecipeHandler());
		API.registerRecipeHandler(new IrradiatorRecipeHandler());
		API.registerUsageHandler(new IrradiatorRecipeHandler());
		API.registerRecipeHandler(new CoolerRecipeHandler());
		API.registerUsageHandler(new CoolerRecipeHandler());
		API.registerRecipeHandler(new HeliumExtractorRecipeHandler());
		API.registerUsageHandler(new HeliumExtractorRecipeHandler());
		API.registerRecipeHandler(new FusionRecipeHandler());
		API.registerUsageHandler(new FusionRecipeHandler());
		API.registerRecipeHandler(new FusionSteamRecipeHandler());
		API.registerUsageHandler(new FusionSteamRecipeHandler());
		API.registerRecipeHandler(new InfoUsageHandler());
		API.registerUsageHandler(new InfoUsageHandler());
		
		API.registerGuiOverlayHandler(GuiNuclearWorkspace.class, new DefaultOverlayHandler(), "nwcrafting");
		
		API.registerGuiOverlay(GuiNuclearFurnace.class, "nuclearsmelting");
		API.registerGuiOverlay(GuiNuclearFurnace.class, "nuclearfuel");
		
		API.registerGuiOverlay(GuiHastener.class, "hastening");
		API.registerGuiOverlay(GuiSeparator.class, "separating");
		API.registerGuiOverlay(GuiNuclearWorkspace.class, "nwcrafting");
		API.registerGuiOverlay(GuiFissionReactor.class, "fission");
		API.registerGuiOverlay(GuiFissionReactorSteam.class, "fission");
		API.registerGuiOverlay(GuiCollector.class, "collecting");
		API.registerGuiOverlay(GuiIoniser.class, "ionising");
		API.registerGuiOverlay(GuiIrradiator.class, "irradiating");
		API.registerGuiOverlay(GuiCooler.class, "cooling");
		API.registerGuiOverlay(GuiHeliumExtractor.class, "heliumExtracting");
		API.registerGuiOverlay(GuiRecycler.class, "recycling");
		API.registerGuiOverlay(GuiFusionReactor.class, "fusing");
		API.registerGuiOverlay(GuiFusionReactorSteam.class, "fusing");
		
		API.hideItem(new ItemStack(NCBlocks.fissionReactorGraphiteActive));
		API.hideItem(new ItemStack(NCBlocks.fissionReactorSteamActive));
		API.hideItem(new ItemStack(NCBlocks.furnaceActive));
		API.hideItem(new ItemStack(NCBlocks.hastenerActive));
		API.hideItem(new ItemStack(NCBlocks.nuclearFurnaceActive));
		API.hideItem(new ItemStack(NCBlocks.reactionGeneratorActive));
		API.hideItem(new ItemStack(NCBlocks.separatorActive));
		API.hideItem(new ItemStack(NCBlocks.collectorActive));
		API.hideItem(new ItemStack(NCBlocks.oxidiserIdle));
		API.hideItem(new ItemStack(NCBlocks.oxidiserActive));
		API.hideItem(new ItemStack(NCBlocks.ioniserActive));
		API.hideItem(new ItemStack(NCBlocks.irradiatorActive));
		API.hideItem(new ItemStack(NCBlocks.coolerActive));
		API.hideItem(new ItemStack(NCBlocks.heliumExtractorActive));
		API.hideItem(new ItemStack(NCBlocks.electromagnetActive));
		API.hideItem(new ItemStack(NCBlocks.superElectromagnetActive));
		API.hideItem(new ItemStack(NCBlocks.supercoolerActive));
		API.hideItem(new ItemStack(NCBlocks.synchrotronActive));
		API.hideItem(new ItemStack(NCBlocks.recyclerActive));
		API.hideItem(new ItemStack(NCItems.blank));
		API.hideItem(new ItemStack(NCBlocks.fusionReactorBlock));
		API.hideItem(new ItemStack(NCBlocks.fusionReactorBlockTop));
		API.hideItem(new ItemStack(NCBlocks.fusionReactorSteamBlock));
		API.hideItem(new ItemStack(NCBlocks.fusionReactorSteamBlockTop));
		API.hideItem(new ItemStack(NCBlocks.nukeE));
		API.hideItem(new ItemStack(NCBlocks.antimatterBombE));
		API.hideItem(new ItemStack(NCBlocks.EMPE));
	}

	public String getName() {
		return "NuclearCraft NEI Plugin";
	}

	public String getVersion() {
		return NuclearCraft.version;
	}
}