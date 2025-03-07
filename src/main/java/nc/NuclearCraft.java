package nc;

import java.io.File;

import mekanism.api.gas.Gas;
import mekanism.api.gas.GasRegistry;
import mekanism.api.gas.GasifyableItems;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.util.StackUtils;
import nc.block.NCBlocks;
import nc.block.accelerator.BlockSuperElectromagnet;
import nc.block.accelerator.BlockSupercooler;
import nc.block.accelerator.BlockSynchrotron;
import nc.block.basic.BlockBlock;
import nc.block.basic.BlockMachineBlock;
import nc.block.basic.BlockOre;
import nc.block.crafting.BlockNuclearWorkspace;
import nc.block.fluid.BlockDenseSteam;
import nc.block.fluid.BlockHelium;
import nc.block.fluid.BlockPlasma;
import nc.block.fluid.BlockSteam;
import nc.block.fluid.BlockSuperdenseSteam;
import nc.block.fluid.FluidDenseSteam;
import nc.block.fluid.FluidHelium;
import nc.block.fluid.FluidPlasma;
import nc.block.fluid.FluidSteam;
import nc.block.fluid.FluidSuperdenseSteam;
import nc.block.generator.*;
import nc.block.machine.BlockCollector;
import nc.block.machine.BlockCooler;
import nc.block.machine.BlockFurnace;
import nc.block.machine.BlockHastener;
import nc.block.machine.BlockHeliumExtractor;
import nc.block.machine.BlockIoniser;
import nc.block.machine.BlockIrradiator;
import nc.block.machine.BlockNuclearFurnace;
import nc.block.machine.BlockOxidiser;
import nc.block.machine.BlockRecycler;
import nc.block.machine.BlockSeparator;
import nc.block.nuke.BlockAntimatterBomb;
import nc.block.nuke.BlockAntimatterBombExploding;
import nc.block.nuke.BlockEMP;
import nc.block.nuke.BlockEMPExploding;
import nc.block.nuke.BlockNuke;
import nc.block.nuke.BlockNukeExploding;
import nc.block.quantum.BlockSimpleQuantum;
import nc.block.reactor.BlockBlastBlock;
import nc.block.reactor.BlockCellBlock;
import nc.block.reactor.BlockCoolerBlock;
import nc.block.reactor.BlockFusionConnector;
import nc.block.reactor.BlockGraphiteBlock;
import nc.block.reactor.BlockReactorBlock;
import nc.block.reactor.BlockSpeedBlock;
import nc.block.reactor.BlockTubing1;
import nc.block.reactor.BlockTubing2;
import nc.block.storage.BlockLithiumIonBattery;
import nc.block.storage.BlockVoltaicPile;
import nc.entity.EntityAntimatterBombPrimed;
import nc.entity.EntityEMPPrimed;
import nc.entity.EntityNuclearMonster;
import nc.entity.EntityNukePrimed;
import nc.gui.GuiHandler;
import nc.handler.BlockDropHandler;
import nc.handler.EntityDropHandler;
import nc.handler.EntityHandler;
import nc.handler.FuelHandler;
import nc.item.ItemAntimatter;
import nc.item.ItemBattery;
import nc.item.ItemFuel;
import nc.item.ItemMaterial;
import nc.item.ItemNC;
import nc.item.ItemPart;
import nc.item.ItemUpgrade;
import nc.item.NCItems;
import nc.item.NCRecord;
import nc.itemblock.accelerator.ItemBlockSuperElectromagnet;
import nc.itemblock.accelerator.ItemBlockSupercooler;
import nc.itemblock.accelerator.ItemBlockSynchrotron;
import nc.itemblock.basic.ItemBlockBlock;
import nc.itemblock.basic.ItemBlockOre;
import nc.itemblock.crafting.ItemBlockNuclearWorkspace;
import nc.itemblock.generator.*;
import nc.itemblock.machine.ItemBlockCollector;
import nc.itemblock.machine.ItemBlockCooler;
import nc.itemblock.machine.ItemBlockFurnace;
import nc.itemblock.machine.ItemBlockHastener;
import nc.itemblock.machine.ItemBlockHeliumExtractor;
import nc.itemblock.machine.ItemBlockIoniser;
import nc.itemblock.machine.ItemBlockIrradiator;
import nc.itemblock.machine.ItemBlockNuclearFurnace;
import nc.itemblock.machine.ItemBlockOxidiser;
import nc.itemblock.machine.ItemBlockRecycler;
import nc.itemblock.machine.ItemBlockSeparator;
import nc.itemblock.nuke.ItemBlockAntimatterBomb;
import nc.itemblock.nuke.ItemBlockEMP;
import nc.itemblock.nuke.ItemBlockNuke;
import nc.itemblock.quantum.ItemBlockSimpleQuantum;
import nc.itemblock.reactor.ItemBlockBlastBlock;
import nc.itemblock.reactor.ItemBlockCellBlock;
import nc.itemblock.reactor.ItemBlockCoolerBlock;
import nc.itemblock.reactor.ItemBlockFusionConnector;
import nc.itemblock.reactor.ItemBlockGraphiteBlock;
import nc.itemblock.reactor.ItemBlockReactorBlock;
import nc.itemblock.reactor.ItemBlockSpeedBlock;
import nc.itemblock.storage.ItemBlockLithiumIonBattery;
import nc.itemblock.storage.ItemBlockVoltaicPile;
import nc.packet.PacketHandler;
import nc.proxy.CommonProxy;
import nc.tile.accelerator.TileSuperElectromagnet;
import nc.tile.accelerator.TileSupercooler;
import nc.tile.accelerator.TileSynchrotron;
import nc.tile.crafting.TileNuclearWorkspace;
import nc.tile.generator.*;
import nc.tile.machine.TileCollector;
import nc.tile.machine.TileCooler;
import nc.tile.machine.TileFurnace;
import nc.tile.machine.TileHastener;
import nc.tile.machine.TileHeliumExtractor;
import nc.tile.machine.TileIoniser;
import nc.tile.machine.TileIrradiator;
import nc.tile.machine.TileNuclearFurnace;
import nc.tile.machine.TileRecycler;
import nc.tile.machine.TileSeparator;
import nc.tile.other.TileTubing1;
import nc.tile.other.TileTubing2;
import nc.tile.quantum.TileSimpleQuantum;
import nc.tile.storage.TileLithiumIonBattery;
import nc.tile.storage.TileVoltaicPile;
import nc.util.Achievements;
import nc.worldgen.OreGen;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.DamageSource;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = NuclearCraft.modid, version = NuclearCraft.version, dependencies = "required-after:Mekanism;")

public class NuclearCraft {
	public static int connectedRenderID;
	public static final String modid = "NuclearCraft";
	public static final String textureid = "NC";
	public static final String version = "1.9f";
	
	public static final CreativeTabs tabNC = new CreativeTabs("tabNC") {
		// Creative Tab Shown Item
		public Item getTabIconItem() {
			return Item.getItemFromBlock(NCBlocks.fusionReactor);
		}
	};

	// Mod Hooks
	public static IC2Recipes IC2Hook;
	public static TERecipes TEHook;

	@Instance(modid)
	public static NuclearCraft instance;
	
	public static final int guiIdNuclearFurnace = 0;
	public static final int guiIdFurnace = 1;
	public static final int guiIdReactionGenerator = 5;
	public static final int guiIdSeparator = 6;
	public static final int guiIdHastener = 7;
	public static final int guiIdFissionReactorGraphite = 8;
	public static final int guiIdNuclearWorkspace = 9;
	public static final int guiIdCollector = 10;
	public static final int guiIdFusionReactor = 11;
	public static final int guiIdIoniser = 14;
	public static final int guiIdIrradiator = 15;
	public static final int guiIdCooler = 16;
	public static final int guiIdHeliumExtractor = 18;
	public static final int guiIdSynchrotron = 19;
	public static final int guiIdFissionReactorSteam = 21;
	public static final int guiIdFusionReactorSteam = 22;
	public static final int guiIdRecycler = 23;
	
	// Config File
	public static boolean workspaceShiftClick;
	public static boolean nonTECoolerRecipes;
	
	public static int EMUpdateRate;
	public static int fissionUpdateRate;
	public static int fissionComparatorHeat;
	public static int fusionUpdateRate;
	public static int acceleratorUpdateRate;
	
	public static boolean oreGenCopper;
	public static int oreSizeCopper;
	public static int oreRarityCopper;
	public static int oreMaxHeightCopper;
	public static boolean oreGenTin;
	public static int oreSizeTin;
	public static int oreRarityTin;
	public static int oreMaxHeightTin;
	public static boolean oreGenLead;
	public static int oreSizeLead;
	public static int oreRarityLead;
	public static int oreMaxHeightLead;
	public static boolean oreGenSilver;
	public static int oreSizeSilver;
	public static int oreRaritySilver;
	public static int oreMaxHeightSilver;
	public static boolean oreGenUranium;
	public static int oreSizeUranium;
	public static int oreRarityUranium;
	public static int oreMaxHeightUranium;
	public static boolean oreGenThorium;
	public static int oreSizeThorium;
	public static int oreRarityThorium;
	public static int oreMaxHeightThorium;
	public static boolean oreGenLithium;
	public static int oreSizeLithium;
	public static int oreRarityLithium;
	public static int oreMaxHeightLithium;
	public static boolean oreGenBoron;
	public static int oreSizeBoron;
	public static int oreRarityBoron;
	public static int oreMaxHeightBoron;
	public static boolean oreGenMagnesium;
	public static int oreSizeMagnesium;
	public static int oreRarityMagnesium;
	public static int oreMaxHeightMagnesium;
	public static boolean oreGenPlutonium;
	public static int oreSizePlutonium;
	public static int oreRarityPlutonium;
	public static int oreMaxHeightPlutonium;
	
	public static int liquidHeliumLakeGen;
	
	public static int nuclearFurnaceCookSpeed;
	public static int nuclearFurnaceCookEfficiency;
	public static int metalFurnaceCookSpeed;
	public static int metalFurnaceCookEfficiency;
	public static int separatorSpeed;
	public static int separatorEfficiency;
	public static int hastenerSpeed;
	public static int hastenerEfficiency;
	public static int collectorSpeed;
	public static int ioniserSpeed;
	public static int ioniserEfficiency;
	public static int irradiatorSpeed;
	public static int irradiatorEfficiency;
	public static int coolerSpeed;
	public static int coolerEfficiency;
	public static int heliumExtractorSpeed;
	public static int heliumExtractorEfficiency;
	public static int recyclerSpeed;
	public static int recyclerEfficiency;
	public static int reactionGeneratorRF;
	public static int reactionGeneratorEfficiency;
	public static int RTGRF;
	public static int AmRTGRF;
	public static int CfRTGRF;
	public static int WRTGRF;
	public static int solarRF;
	public static int lithiumIonRF;
	public static int voltaicPileRF;
	public static int steamDecompressRate;
	public static int steamRFUsageRate;
	public static int explosionRadius;
	
	public static boolean enablePaul;
	public static boolean enableBrian;
	public static boolean enableNuclearMonster;
	public static boolean enableNukes;
	public static boolean enableEMP;
	public static boolean enableLoot;
	public static int lootModifier;
	public static boolean extraDrops;
	
	public static int fissionMaxLength;
	public static int fissionRF;
	public static int fissionSteam;
	public static int fissionEfficiency;
	public static int fissionHeat;
	public static boolean nuclearMeltdowns;
	public static boolean alternateCasing;
	
	public static int baseRFLEU;
	public static int baseRFHEU;
	public static int baseRFLEP;
	public static int baseRFHEP;
	public static int baseRFMOX;
	public static int baseRFTBU;
	public static int baseRFLEUOx;
	public static int baseRFHEUOx;
	public static int baseRFLEPOx;
	public static int baseRFHEPOx;
	public static int baseRFTBUOx;
	public static int baseRFLEN;
	public static int baseRFHEN;
	public static int baseRFLEA;
	public static int baseRFHEA;
	public static int baseRFLEC;
	public static int baseRFHEC;
	public static int baseRFLENOx;
	public static int baseRFHENOx;
	public static int baseRFLEAOx;
	public static int baseRFHEAOx;
	public static int baseRFLECOx;
	public static int baseRFHECOx;
	
	public static int baseFuelLEU;
	public static int baseFuelHEU;
	public static int baseFuelLEP;
	public static int baseFuelHEP;
	public static int baseFuelMOX;
	public static int baseFuelTBU;
	public static int baseFuelLEUOx;
	public static int baseFuelHEUOx;
	public static int baseFuelLEPOx;
	public static int baseFuelHEPOx;
	public static int baseFuelTBUOx;
	public static int baseFuelLEN;
	public static int baseFuelHEN;
	public static int baseFuelLEA;
	public static int baseFuelHEA;
	public static int baseFuelLEC;
	public static int baseFuelHEC;
	public static int baseFuelLENOx;
	public static int baseFuelHENOx;
	public static int baseFuelLEAOx;
	public static int baseFuelHEAOx;
	public static int baseFuelLECOx;
	public static int baseFuelHECOx;
	
	public static int baseHeatLEU;
	public static int baseHeatHEU;
	public static int baseHeatLEP;
	public static int baseHeatHEP;
	public static int baseHeatMOX;
	public static int baseHeatTBU;
	public static int baseHeatLEUOx;
	public static int baseHeatHEUOx;
	public static int baseHeatLEPOx;
	public static int baseHeatHEPOx;
	public static int baseHeatTBUOx;
	public static int baseHeatLEN;
	public static int baseHeatHEN;
	public static int baseHeatLEA;
	public static int baseHeatHEA;
	public static int baseHeatLEC;
	public static int baseHeatHEC;
	public static int baseHeatLENOx;
	public static int baseHeatHENOx;
	public static int baseHeatLEAOx;
	public static int baseHeatHEAOx;
	public static int baseHeatLECOx;
	public static int baseHeatHECOx;
	
	public static int standardCool;
	public static int waterCool;
	public static int cryotheumCool;
	public static int redstoneCool;
	public static int enderiumCool;
	public static int glowstoneCool;
	public static int heliumCool;
	public static int coolantCool;
	
	public static int fusionMaxRadius;
	public static int fusionRF;
	public static int fusionSteam;
	public static int fusionEfficiency;
	public static int fusionHeat;
	public static int electromagnetRF;
	public static boolean fusionMeltdowns;
	public static boolean fusionEfficiencyConverge;
	public static int fusionComparatorEfficiency;
	public static boolean fusionSounds;
	
	public static int baseRFHH;
	public static int baseRFHD;
	public static int baseRFHT;
	public static int baseRFHHe;
	public static int baseRFHB;
	public static int baseRFHLi6;
	public static int baseRFHLi7;
	public static int baseRFDD;
	public static int baseRFDT;
	public static int baseRFDHe;
	public static int baseRFDB;
	public static int baseRFDLi6;
	public static int baseRFDLi7;
	public static int baseRFTT;
	public static int baseRFTHe;
	public static int baseRFTB;
	public static int baseRFTLi6;
	public static int baseRFTLi7;
	public static int baseRFHeHe;
	public static int baseRFHeB;
	public static int baseRFHeLi6;
	public static int baseRFHeLi7;	
	public static int baseRFBB;
	public static int baseRFBLi6;
	public static int baseRFBLi7;	
	public static int baseRFLi6Li6;
	public static int baseRFLi6Li7;
	public static int baseRFLi7Li7;
	
	public static int baseFuelHH;
	public static int baseFuelHD;
	public static int baseFuelHT;
	public static int baseFuelHHe;
	public static int baseFuelHB;
	public static int baseFuelHLi6;
	public static int baseFuelHLi7;
	public static int baseFuelDD;
	public static int baseFuelDT;
	public static int baseFuelDHe;
	public static int baseFuelDB;
	public static int baseFuelDLi6;
	public static int baseFuelDLi7;
	public static int baseFuelTT;
	public static int baseFuelTHe;
	public static int baseFuelTB;
	public static int baseFuelTLi6;
	public static int baseFuelTLi7;
	public static int baseFuelHeHe;
	public static int baseFuelHeB;
	public static int baseFuelHeLi6;
	public static int baseFuelHeLi7;	
	public static int baseFuelBB;
	public static int baseFuelBLi6;
	public static int baseFuelBLi7;	
	public static int baseFuelLi6Li6;
	public static int baseFuelLi6Li7;
	public static int baseFuelLi7Li7;
	
	public static int heatHH;
	public static int heatHD;
	public static int heatHT;
	public static int heatHHe;
	public static int heatHB;
	public static int heatHLi6;
	public static int heatHLi7;
	public static int heatDD;
	public static int heatDT;
	public static int heatDHe;
	public static int heatDB;
	public static int heatDLi6;
	public static int heatDLi7;
	public static int heatTT;
	public static int heatTHe;
	public static int heatTB;
	public static int heatTLi6;
	public static int heatTLi7;
	public static int heatHeHe;
	public static int heatHeB;
	public static int heatHeLi6;
	public static int heatHeLi7;	
	public static int heatBB;
	public static int heatBLi6;
	public static int heatBLi7;	
	public static int heatLi6Li6;
	public static int heatLi6Li7;
	public static int heatLi7Li7;
	
	public static int ringMaxSize;
	public static int colliderRF;
	public static int colliderProduction;
	public static int synchrotronRF;
	public static int synchrotronProduction;
	public static int superElectromagnetRF;
	public static int electromagnetHe;
	public static int acceleratorProduction;
	public static boolean acceleratorSounds;

	public static Fluid liquidHelium;
	public static Fluid fusionPlasma;
	public static Fluid steam;
	public static Fluid denseSteam;
	public static Fluid superdenseSteam;
	
	@SidedProxy(clientSide = "nc.proxy.ClientProxy", serverSide = "nc.proxy.CommonProxy")
	public static CommonProxy NCProxy;
	
	public static final Material liquidhelium = (new MaterialLiquid(MapColor.redColor));
	public static DamageSource heliumfreeze = (new DamageSource("heliumfreeze")).setDamageBypassesArmor();
	public static final Material fusionplasma = (new MaterialLiquid(MapColor.purpleColor));
	public static DamageSource plasmaburn = (new DamageSource("plasmaburn")).setDamageBypassesArmor();
	public static final Material steamMaterial = (new MaterialLiquid(MapColor.grayColor));
	public static DamageSource steamburn = (new DamageSource("steamburn")).setDamageBypassesArmor();
	
	public static Achievements achievements;
	
	public static Achievement nuclearFurnaceAchievement;
	public static Achievement heavyDutyWorkspaceAchievement;
	public static Achievement nukeAchievement;
	public static Achievement reactionGeneratorAchievement;
	public static Achievement fissionControllerAchievement;
	public static Achievement RTGAchievement;
	public static Achievement fusionReactorAchievement;
	public static Achievement separatorAchievement;
	public static Achievement synchrotronAchievement;

	public static SimpleNetworkWrapper network;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {	
		//config
		Configuration config = new Configuration(new File("config/NuclearCraft/NCConfig.cfg"));
		Configuration fissionConfig = new Configuration(new File("config/NuclearCraft/FissionConfig.cfg"));
		Configuration fusionConfig = new Configuration(new File("config/NuclearCraft/FusionConfig.cfg"));
		Configuration acceleratorConfig = new Configuration(new File("config/NuclearCraft/AcceleratorConfig.cfg"));
		Configuration toolConfig = new Configuration(new File("config/NuclearCraft/ToolConfig.cfg"));
		config.load();
		fissionConfig.load();
		fusionConfig.load();
		acceleratorConfig.load();
		toolConfig.load();


		workspaceShiftClick = config.getBoolean("If enabled, shift clicking items in the Heavy Duty Workspace will move items into the crafting grid", "!: Enable Shift Click into Workspace Grid", false, "");
		nonTECoolerRecipes = config.getBoolean("Enable the vanilla crafting recipes for the advanced reactor coolers EVEN IF Thermal Expansion is not installed", "!: Force Vanilla Crafting of Coolers", false, "");
		
		oreGenCopper = config.getBoolean("Generation", "0.0: Copper Ore", true, "");
		oreSizeCopper = config.getInt("Chunk Size", "0.0: Copper Ore", 8, 1, 100, "");
		oreRarityCopper = config.getInt("Gen Rate", "0.0: Copper Ore", 12, 1, 100, "");
		oreMaxHeightCopper = config.getInt("Max Height", "0.0: Copper Ore", 60, 1, 255, "");
		oreGenTin = config.getBoolean("Generation", "0.1: Tin Ore", true, "");
		oreSizeTin = config.getInt("Chunk Size", "0.1: Tin Ore", 8, 1, 100, "");
		oreRarityTin = config.getInt("Gen Rate", "0.1: Tin Ore", 11, 1, 100, "");
		oreMaxHeightTin = config.getInt("Max Height", "0.1: Tin Ore", 60, 1, 255, "");
		oreGenLead = config.getBoolean("Generation", "0.2: Lead Ore", true, "");
		oreSizeLead = config.getInt("Chunk Size", "0.2: Lead Ore", 7, 1, 100, "");
		oreRarityLead = config.getInt("Gen Rate", "0.2: Lead Ore", 11, 1, 100, "");
		oreMaxHeightLead = config.getInt("Max Height", "0.2: Lead Ore", 40, 1, 255, "");
		oreGenSilver = config.getBoolean("Generation", "0.3: Silver Ore", true, "");
		oreSizeSilver = config.getInt("Chunk Size", "0.3: Silver Ore", 7, 1, 100, "");
		oreRaritySilver = config.getInt("Gen Rate", "0.3: Silver Ore", 10, 1, 100, "");
		oreMaxHeightSilver = config.getInt("Max Height", "0.3: Silver Ore", 40, 1, 255, "");
		oreGenUranium = config.getBoolean("Generation", "0.4: Uranium Ore", true, "");
		oreSizeUranium = config.getInt("Chunk Size", "0.4: Uranium Ore", 10, 1, 100, "");
		oreRarityUranium = config.getInt("Gen Rate", "0.4: Uranium Ore", 2, 1, 100, "");
		oreMaxHeightUranium = config.getInt("Max Height", "0.4: Uranium Ore", 36, 1, 255, "");
		oreGenThorium = config.getBoolean("Generation", "0.5: Thorium Ore", true, "");
		oreSizeThorium = config.getInt("Chunk Size", "0.5: Thorium Ore", 7, 1, 100, "");
		oreRarityThorium = config.getInt("Gen Rate", "0.5: Thorium Ore", 2, 1, 100, "");
		oreMaxHeightThorium = config.getInt("Max Height", "0.5: Thorium Ore", 36, 1, 255, "");
		oreGenLithium = config.getBoolean("Generation", "0.6: Lithium Ore", true, "");
		oreSizeLithium = config.getInt("Chunk Size", "0.6: Lithium Ore", 7, 1, 100, "");
		oreRarityLithium = config.getInt("Gen Rate", "0.6: Lithium Ore", 7, 1, 100, "");
		oreMaxHeightLithium = config.getInt("Max Height", "0.6: Lithium Ore", 32, 1, 255, "");
		oreGenBoron = config.getBoolean("Generation", "0.7: Boron Ore", true, "");
		oreSizeBoron = config.getInt("Chunk Size", "0.7: Boron Ore", 7, 1, 100, "");
		oreRarityBoron = config.getInt("Gen Rate", "0.7: Boron Ore", 7, 1, 100, "");
		oreMaxHeightBoron = config.getInt("Max Height", "0.7: Boron Ore", 24, 1, 255, "");
		oreGenMagnesium = config.getBoolean("Generation", "0.8: Magnesium Ore", true, "");
		oreSizeMagnesium = config.getInt("Chunk Size", "0.8: Magnesium Ore", 7, 1, 100, "");
		oreRarityMagnesium = config.getInt("Gen Rate", "0.8: Magnesium Ore", 7, 1, 100, "");
		oreMaxHeightMagnesium = config.getInt("Max Height", "0.8: Magnesium Ore", 24, 1, 255, "");
		oreGenPlutonium = config.getBoolean("Generation", "0.9: Plutonium Ore", true, "");
		oreSizePlutonium = config.getInt("Chunk Size", "0.9: Plutonium Ore", 17, 1, 100, "");
		oreRarityPlutonium = config.getInt("Gen Rate", "0.9: Plutonium Ore", 1, 1, 100, "");
		oreMaxHeightPlutonium = config.getInt("Max Height", "0.9: Plutonium Ore", 255, 1, 255, "");
		
		liquidHeliumLakeGen = config.getInt("Liquid Helium Gen Rate in End", "0.10: World Gen", 0, 0, 10, "");

		separatorSpeed = config.getInt("Isotope Separator Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		separatorEfficiency = config.getInt("Isotope Separator Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		hastenerSpeed = config.getInt("Decay Hastener Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		hastenerEfficiency = config.getInt("Decay Hastener Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		ioniserSpeed = config.getInt("Ioniser Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		ioniserEfficiency = config.getInt("Ioniser Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		irradiatorSpeed = config.getInt("Neutron Irradiator Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		irradiatorEfficiency = config.getInt("Neutron Irradiator Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		coolerSpeed = config.getInt("Supercooler Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		coolerEfficiency = config.getInt("Supercooler Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		heliumExtractorSpeed = config.getInt("Helium Extractor Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		heliumExtractorEfficiency = config.getInt("Helium Extractor Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		recyclerSpeed = config.getInt("Fuel Recycler Speed Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		recyclerEfficiency = config.getInt("Fuel Recycler Efficiency Multiplier", "1.0: RF Machines", 100, 10, 1000, "");
		reactionGeneratorRF = config.getInt("Reaction Generator RF/t", "1.1: RF Generators", 100, 10, 1000, "");
		reactionGeneratorEfficiency = config.getInt("Reaction Generator Efficiency Multiplier", "1.1: RF Generators", 100, 10, 1000, "");
		RTGRF = config.getInt("Plutonium RTG RF/t", "1.1: RF Generators", 100, 1, 1000, "");
		AmRTGRF = config.getInt("Americium RTG RF/t", "1.1: RF Generators", 40, 1, 1000, "");
		CfRTGRF = config.getInt("Californium RTG RF/t", "1.1: RF Generators", 500, 1, 1000, "");
		WRTGRF = config.getInt("WRTG RF/t", "1.1: RF Generators", 5, 1, 1000, "");
		solarRF = config.getInt("Solar Panel RF/t", "1.1: RF Generators", 10, 1, 1000, "");
		nuclearFurnaceCookSpeed = config.getInt("Nuclear Furnace Speed Multiplier", "1.2: Non-RF Machines", 100, 10, 1000, "");
		nuclearFurnaceCookEfficiency = config.getInt("Nuclear Furnace Fuel Usage Multiplier", "1.2: Non-RF Machines", 100, 10, 1000, "");
		metalFurnaceCookSpeed = config.getInt("Metal Furnace Speed Multiplier", "1.2: Non-RF Machines", 100, 10, 1000, "");
		metalFurnaceCookEfficiency = config.getInt("Metal Furnace Fuel Usage Multiplier", "1.2: Non-RF Machines", 100, 10, 1000, "");
		collectorSpeed = config.getInt("Helium Collector Speed Multiplier", "1.2: Non-RF Machines", 100, 10, 1000, "");
		lithiumIonRF = config.getInt("Lithium Ion Battery RF", "1.3: RF Storage", 10000000, 1, 100000000, "");
		voltaicPileRF = config.getInt("Voltaic Pile RF", "1.3: RF Storage", 1000000, 1, 100000000, "");
		
		steamDecompressRate = config.getInt("Steam Decompressor Rate", "1.4: Steam Manipulators", 2, 1, 100, "");
		steamRFUsageRate = config.getInt("Steam Generator Usage Rate", "1.4: Steam Manipulators", 2000, 1, 100000, "");
		
		explosionRadius = config.getInt("General Explosion Radius Modifier", "1.5: Explosions", 100, 10, 1000, "");
		
		enableNuclearMonster = config.getBoolean("Enable Nuclear Monsters Spawning", "2.0: Mobs", true, "");
		enablePaul = config.getBoolean("Enable Paul", "2.0: Mobs", true, "");
		enableBrian = config.getBoolean("Enable Brian", "2.0: Mobs", true, "");
		enableNukes = config.getBoolean("Enable Nuclear and Antimatter Weapons", "2.1: Other", true, "");
		enableEMP = config.getBoolean("Enable EMP Weapon", "2.1: Other", true, "");
		enableLoot = config.getBoolean("Enable Loot in Generated Chests", "2.1: Other", true, "");
		lootModifier = config.getInt("Dungeon Loot Frequency", "2.1: Other", 2, 0, 10, "");
		extraDrops = config.getBoolean("Enable Extra Mob and Ore Drops", "2.1: Other", true, "");
		
		fissionUpdateRate = fissionConfig.getInt("Number of ticks per update of Fission Reactors", "!: Update Rate", 20, 1, 1000, "");
		fissionComparatorHeat = fissionConfig.getInt("The heat at which the comparator will emit a full redstone signal", "!: Comparator Heat", 250000, 1, 1000000, "");
		fissionMaxLength = fissionConfig.getInt("Fission Reactor Maximum Interior Length", "0: General", 25, 1, 100, "");
		fissionRF = fissionConfig.getInt("Fission Reactor RF Production Multiplier", "0: General", 100, 10, 1000, "");
		fissionSteam = fissionConfig.getInt("Fission Reactor Steam Production Multiplier", "0: General", 100, 10, 1000, "");
		fissionEfficiency = fissionConfig.getInt("Fission Reactor Fuel Efficiency Multiplier", "0: General", 100, 10, 1000, "");
		fissionHeat = fissionConfig.getInt("Fission Reactor Heat Production Multiplier", "0: General", 100, 10, 1000, "");
		nuclearMeltdowns = fissionConfig.getBoolean("Enable Fission Reactor Meltdowns", "0: General", true, "");
		alternateCasing = fissionConfig.getBoolean("Use Alternate Casing Texture", "0: General", false, "");
		
		baseRFLEU = fissionConfig.getInt("LEU Base Power", "1: Fission Fuel Base Power/Steam Production", 200, 20, 2000, "");
		baseRFHEU = fissionConfig.getInt("HEU Base Power", "1: Fission Fuel Base Power/Steam Production", 800, 80, 8000, "");
		baseRFLEP = fissionConfig.getInt("LEP Base Power", "1: Fission Fuel Base Power/Steam Production", 400, 40, 4000, "");
		baseRFHEP = fissionConfig.getInt("HEP Base Power", "1: Fission Fuel Base Power/Steam Production", 1600, 160, 16000, "");
		baseRFMOX = fissionConfig.getInt("MOX Base Power", "1: Fission Fuel Base Power/Steam Production", 500, 50, 5000, "");
		baseRFTBU = fissionConfig.getInt("TBU Base Power", "1: Fission Fuel Base Power/Steam Production", 100, 10, 1000, "");
		baseRFLEUOx = fissionConfig.getInt("LEU-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 300, 30, 3000, "");
		baseRFHEUOx = fissionConfig.getInt("HEU-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 1200, 120, 12000, "");
		baseRFLEPOx = fissionConfig.getInt("LEP-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 600, 60, 6000, "");
		baseRFHEPOx = fissionConfig.getInt("HEP-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 2400, 240, 24000, "");
		baseRFTBUOx = fissionConfig.getInt("TBU-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 150, 15, 1500, "");
		
		baseRFLEN = fissionConfig.getInt("LEN Base Power", "1: Fission Fuel Base Power/Steam Production", 150, 15, 1500, "");
		baseRFHEN = fissionConfig.getInt("HEN Base Power", "1: Fission Fuel Base Power/Steam Production", 600, 60, 6000, "");
		baseRFLEA = fissionConfig.getInt("LEA Base Power", "1: Fission Fuel Base Power/Steam Production", 300, 30, 3000, "");
		baseRFHEA = fissionConfig.getInt("HEA Base Power", "1: Fission Fuel Base Power/Steam Production", 1200, 120, 12000, "");
		baseRFLEC = fissionConfig.getInt("LEC Base Power", "1: Fission Fuel Base Power/Steam Production", 500, 50, 5000, "");
		baseRFHEC = fissionConfig.getInt("HEC Base Power", "1: Fission Fuel Base Power/Steam Production", 2000, 200, 20000, "");
		baseRFLENOx = fissionConfig.getInt("LEN-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 225, 22, 2250, "");
		baseRFHENOx = fissionConfig.getInt("HEN-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 900, 90, 9000, "");
		baseRFLEAOx = fissionConfig.getInt("LEA-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 450, 45, 4500, "");
		baseRFHEAOx = fissionConfig.getInt("HEA-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 1800, 180, 18000, "");
		baseRFLECOx = fissionConfig.getInt("LEC-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 750, 75, 7500, "");
		baseRFHECOx = fissionConfig.getInt("HEC-Ox Base Power", "1: Fission Fuel Base Power/Steam Production", 3000, 300, 30000, "");
		
		baseFuelLEU = fissionConfig.getInt("LEU Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelHEU = fissionConfig.getInt("HEU Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelLEP = fissionConfig.getInt("LEP Usage Rate", "2: Fission Fuel Usage Rate", 50000, 5000, 500000, "");
		baseFuelHEP = fissionConfig.getInt("HEP Usage Rate", "2: Fission Fuel Usage Rate", 50000, 5000, 500000, "");
		baseFuelMOX = fissionConfig.getInt("MOX Usage Rate", "2: Fission Fuel Usage Rate", 37500, 3750, 375000, "");
		baseFuelTBU = fissionConfig.getInt("TBU Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		baseFuelLEUOx = fissionConfig.getInt("LEU-Ox Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelHEUOx = fissionConfig.getInt("HEU-Ox Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelLEPOx = fissionConfig.getInt("LEP-Ox Usage Rate", "2: Fission Fuel Usage Rate", 50000, 5000, 500000, "");
		baseFuelHEPOx = fissionConfig.getInt("HEP-Ox Usage Rate", "2: Fission Fuel Usage Rate", 50000, 5000, 500000, "");
		baseFuelTBUOx = fissionConfig.getInt("TBU-Ox Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		
		baseFuelLEN = fissionConfig.getInt("LEN Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		baseFuelHEN = fissionConfig.getInt("HEN Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		baseFuelLEA = fissionConfig.getInt("LEA Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelHEA = fissionConfig.getInt("HEA Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelLEC = fissionConfig.getInt("LEC Usage Rate", "2: Fission Fuel Usage Rate", 37500, 3750, 375000, "");
		baseFuelHEC = fissionConfig.getInt("HEC Usage Rate", "2: Fission Fuel Usage Rate", 37500, 3750, 375000, "");
		baseFuelLENOx = fissionConfig.getInt("LEN-Ox Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		baseFuelHENOx = fissionConfig.getInt("HEN-Ox Usage Rate", "2: Fission Fuel Usage Rate", 12500, 1250, 125000, "");
		baseFuelLEAOx = fissionConfig.getInt("LEA-Ox Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelHEAOx = fissionConfig.getInt("HEA-Ox Usage Rate", "2: Fission Fuel Usage Rate", 25000, 2500, 250000, "");
		baseFuelLECOx = fissionConfig.getInt("LEC-Ox Usage Rate", "2: Fission Fuel Usage Rate", 37500, 3750, 375000, "");
		baseFuelHECOx = fissionConfig.getInt("HEC-Ox Usage Rate", "2: Fission Fuel Usage Rate", 37500, 3750, 375000, "");
		
		baseHeatLEU = fissionConfig.getInt("LEU Base Heat", "3: Fission Fuel Base Heat", 80, 8, 800, "");
		baseHeatHEU = fissionConfig.getInt("HEU Base Heat", "3: Fission Fuel Base Heat", 640, 64, 6400, "");
		baseHeatLEP = fissionConfig.getInt("LEP Base Heat", "3: Fission Fuel Base Heat", 200, 20, 2000, "");
		baseHeatHEP = fissionConfig.getInt("HEP Base Heat", "3: Fission Fuel Base Heat", 1600, 160, 16000, "");
		baseHeatMOX = fissionConfig.getInt("MOX Base Heat", "3: Fission Fuel Base Heat", 240, 24, 2400, "");
		baseHeatTBU = fissionConfig.getInt("TBU Base Heat", "3: Fission Fuel Base Heat", 20, 2, 200, "");
		baseHeatLEUOx = fissionConfig.getInt("LEU-Ox Base Heat", "3: Fission Fuel Base Heat", 100, 10, 1000, "");
		baseHeatHEUOx = fissionConfig.getInt("HEU-Ox Base Heat", "3: Fission Fuel Base Heat", 800, 80, 8000, "");
		baseHeatLEPOx = fissionConfig.getInt("LEP-Ox Base Heat", "3: Fission Fuel Base Heat", 250, 25, 2500, "");
		baseHeatHEPOx = fissionConfig.getInt("HEP-Ox Base Heat", "3: Fission Fuel Base Heat", 2000, 200, 20000, "");
		baseHeatTBUOx = fissionConfig.getInt("TBU-Ox Base Heat", "3: Fission Fuel Base Heat", 25, 2, 250, "");
		
		baseHeatLEN = fissionConfig.getInt("LEN Base Heat", "3: Fission Fuel Base Heat", 40, 4, 400, "");
		baseHeatHEN = fissionConfig.getInt("HEN Base Heat", "3: Fission Fuel Base Heat", 320, 32, 3200, "");
		baseHeatLEA = fissionConfig.getInt("LEA Base Heat", "3: Fission Fuel Base Heat", 120, 12, 1200, "");
		baseHeatHEA = fissionConfig.getInt("HEA Base Heat", "3: Fission Fuel Base Heat", 960, 96, 9600, "");
		baseHeatLEC = fissionConfig.getInt("LEC Base Heat", "3: Fission Fuel Base Heat", 300, 30, 3000, "");
		baseHeatHEC = fissionConfig.getInt("HEC Base Heat", "3: Fission Fuel Base Heat", 2400, 240, 24000, "");
		baseHeatLENOx = fissionConfig.getInt("LEN-Ox Base Heat", "3: Fission Fuel Base Heat", 50, 5, 500, "");
		baseHeatHENOx = fissionConfig.getInt("HEN-Ox Base Heat", "3: Fission Fuel Base Heat", 400, 40, 4000, "");
		baseHeatLEAOx = fissionConfig.getInt("LEA-Ox Base Heat", "3: Fission Fuel Base Heat", 150, 15, 1500, "");
		baseHeatHEAOx = fissionConfig.getInt("HEA-Ox Base Heat", "3: Fission Fuel Base Heat", 1200, 120, 12000, "");
		baseHeatLECOx = fissionConfig.getInt("LEC-Ox Base Heat", "3: Fission Fuel Base Heat", 375, 37, 3750, "");
		baseHeatHECOx = fissionConfig.getInt("HEC-Ox Base Heat", "3: Fission Fuel Base Heat", 3000, 300, 30000, "");
		
		standardCool = fissionConfig.getInt("Standard Cooler", "5: Cooler Base Cooling Rates (H/t)", 30, 1, 250, "");
		waterCool = fissionConfig.getInt("Water Cooler", "5: Cooler Base Cooling Rates (H/t)", 30, 1, 250, "");
		cryotheumCool = fissionConfig.getInt("Cryotheum Cooler", "5: Cooler Base Cooling Rates (H/t)", 80, 1, 250, "");
		redstoneCool = fissionConfig.getInt("Redstone Cooler", "5: Cooler Base Cooling Rates (H/t)", 80, 1, 250, "");
		enderiumCool = fissionConfig.getInt("Enderium Cooler", "5: Cooler Base Cooling Rates (H/t)", 80, 1, 250, "");
		glowstoneCool = fissionConfig.getInt("Glowstone Cooler", "5: Cooler Base Cooling Rates (H/t)", 80, 1, 250, "");
		heliumCool = fissionConfig.getInt("Liquid Helium Cooler", "5: Cooler Base Cooling Rates (H/t)", 125, 1, 250, "");
		coolantCool = fissionConfig.getInt("IC2 Coolant Cooler", "5: Cooler Base Cooling Rates (H/t)", 80, 1, 250, "");
		
		fusionUpdateRate = fusionConfig.getInt("Number of ticks per update of Fusion Reactors", "!: Update Rate", 20, 1, 1000, "");
		fusionSounds = fusionConfig.getBoolean("Enable Fusion Reactor Sound Effects", "!: Sounds", true, "");
		fusionEfficiencyConverge = fusionConfig.getBoolean("Make Fusion Reactors' efficiencies converge towards 100% - this would mean comparators would not be needed", "!: Efficiency Converge", false, "");
		fusionComparatorEfficiency = fusionConfig.getInt("The efficiency at which the comparator will emit a full redstone signal", "!: Comparator Efficiency", 90, 1, 100, "");
		fusionMaxRadius = fusionConfig.getInt("Fusion Reactor Maximum Radius - Defined as Number of Fusion Connectors Between the Control Chunk and a Central Inner Electromagnet", "0: General", 25, 1, 100, "");
		fusionRF = fusionConfig.getInt("Fusion Reactor RF Production Multiplier", "0: General", 100, 10, 1000, "");
		fusionSteam = fusionConfig.getInt("Fusion Reactor Steam Production Multiplier", "0: General", 100, 10, 1000, "");
		fusionEfficiency = fusionConfig.getInt("Fusion Reactor Fuel Efficiency Multiplier", "0: General", 100, 10, 1000, "");
		fusionHeat = fusionConfig.getInt("Fusion Reactor Heat Production Multiplier", "0: General", 100, 10, 1000, "");
		electromagnetRF = fusionConfig.getInt("Electromagnet RF/t Requirement", "0: General", 50, 0, 1000, "");
		fusionMeltdowns = fusionConfig.getBoolean("Enable Fusion Reactor Overheating", "0: General", true, "");
		
		baseRFHH = fusionConfig.getInt("HH Base Power", "1: Fusion Combo Base Power", 320, 32, 3200, "");
		baseRFHD = fusionConfig.getInt("HD Base Power", "1: Fusion Combo Base Power", 240, 24, 2400, "");
		baseRFHT = fusionConfig.getInt("HT Base Power", "1: Fusion Combo Base Power", 80, 8, 800, "");
		baseRFHHe = fusionConfig.getInt("HHe Base Power", "1: Fusion Combo Base Power", 80, 8, 800, "");
		baseRFHB = fusionConfig.getInt("HB Base Power", "1: Fusion Combo Base Power", 320, 32, 3200, "");
		baseRFHLi6 = fusionConfig.getInt("HLi6 Base Power", "1: Fusion Combo Base Power", 120, 12, 1200, "");
		baseRFHLi7 = fusionConfig.getInt("HLi7 Base Power", "1: Fusion Combo Base Power", 480, 48, 4800, "");
		baseRFDD = fusionConfig.getInt("DD Base Power", "1: Fusion Combo Base Power", 560, 56, 5600, "");
		baseRFDT = fusionConfig.getInt("DT Base Power", "1: Fusion Combo Base Power", 800, 80, 8000, "");
		baseRFDHe = fusionConfig.getInt("DHe Base Power", "1: Fusion Combo Base Power", 640, 64, 6400, "");
		baseRFDB = fusionConfig.getInt("DB Base Power", "1: Fusion Combo Base Power", 80, 8, 800, "");
		baseRFDLi6 = fusionConfig.getInt("DLi6 Base Power", "1: Fusion Combo Base Power", 600, 60, 6000, "");
		baseRFDLi7 = fusionConfig.getInt("DLi7 Base Power", "1: Fusion Combo Base Power", 40, 4, 400, "");
		baseRFTT = fusionConfig.getInt("TT Base Power", "1: Fusion Combo Base Power", 240, 24, 2400, "");
		baseRFTHe = fusionConfig.getInt("THe Base Power", "1: Fusion Combo Base Power", 160, 16, 1600, "");
		baseRFTB = fusionConfig.getInt("TB Base Power", "1: Fusion Combo Base Power", 40, 4, 400, "");
		baseRFTLi6 = fusionConfig.getInt("TLi6 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFTLi7 = fusionConfig.getInt("TLi7 Base Power", "1: Fusion Combo Base Power", 40, 4, 400, "");
		baseRFHeHe = fusionConfig.getInt("HeHe Base Power", "1: Fusion Combo Base Power", 480, 48, 4800, "");
		baseRFHeB = fusionConfig.getInt("HeB Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFHeLi6 = fusionConfig.getInt("HeLi6 Base Power", "1: Fusion Combo Base Power", 560, 56, 5600, "");
		baseRFHeLi7 = fusionConfig.getInt("HeLi7 Base Power", "1: Fusion Combo Base Power", 120, 12, 1200, "");
		baseRFBB = fusionConfig.getInt("BB Base Power", "1: Fusion Combo Base Power", 40, 4, 400, "");
		baseRFBLi6 = fusionConfig.getInt("BLi6 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFBLi7 = fusionConfig.getInt("BLi7 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFLi6Li6 = fusionConfig.getInt("Li6Li6 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFLi6Li7 = fusionConfig.getInt("Li6Li7 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		baseRFLi7Li7 = fusionConfig.getInt("Li7Li7 Base Power", "1: Fusion Combo Base Power", 20, 2, 200, "");
		
		baseFuelHH = fusionConfig.getInt("HH Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 800, 80, 8000, "");
		baseFuelHD = fusionConfig.getInt("HD Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 480, 48, 4800, "");
		baseFuelHT = fusionConfig.getInt("HT Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 320, 32, 3200, "");
		baseFuelHHe = fusionConfig.getInt("HHe Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 320, 32, 3200, "");
		baseFuelHB = fusionConfig.getInt("HB Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelHLi6 = fusionConfig.getInt("HLi6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelHLi7 = fusionConfig.getInt("HLi7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 160, 16, 1600, "");
		baseFuelDD = fusionConfig.getInt("DD Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 320, 32, 3200, "");
		baseFuelDT = fusionConfig.getInt("DT Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 640, 64, 6400, "");
		baseFuelDHe = fusionConfig.getInt("DHe Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 200, 20, 2000, "");
		baseFuelDB = fusionConfig.getInt("DB Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelDLi6 = fusionConfig.getInt("DLi6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 200, 20, 2000, "");
		baseFuelDLi7 = fusionConfig.getInt("DLi7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 40, 4, 400, "");
		baseFuelTT = fusionConfig.getInt("TT Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 120, 12, 1200, "");
		baseFuelTHe = fusionConfig.getInt("THe Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelTB = fusionConfig.getInt("TB Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelTLi6 = fusionConfig.getInt("TLi6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 16, 1, 160, "");
		baseFuelTLi7 = fusionConfig.getInt("TLi7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 32, 3, 320, "");
		baseFuelHeHe = fusionConfig.getInt("HeHe Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 120, 12, 1200, "");
		baseFuelHeB = fusionConfig.getInt("HeB Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 40, 4, 400, "");
		baseFuelHeLi6 = fusionConfig.getInt("HeLi6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 160, 16, 1600, "");
		baseFuelHeLi7 = fusionConfig.getInt("HeLi7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 80, 8, 800, "");
		baseFuelBB = fusionConfig.getInt("BB Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 40, 4, 400, "");
		baseFuelBLi6 = fusionConfig.getInt("BLi6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 40, 4, 400, "");
		baseFuelBLi7 = fusionConfig.getInt("BLi7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 20, 2, 200, "");
		baseFuelLi6Li6 = fusionConfig.getInt("Li6Li6 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 20, 2, 200, "");
		baseFuelLi6Li7 = fusionConfig.getInt("Li6Li7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 40, 4, 400, "");
		baseFuelLi7Li7 = fusionConfig.getInt("Li7Li7 Base Fuel Usage Rate", "2: Fusion Combo Base Fuel Usage Rate", 20, 2, 200, "");
		
		heatHH = fusionConfig.getInt("HH Heat Variable", "3: Fusion Combo Heat Variable", 2140, 500, 20000, "");
		heatHD = fusionConfig.getInt("HD Heat Variable", "3: Fusion Combo Heat Variable", 1380, 500, 20000, "");
		heatHT = fusionConfig.getInt("HT Heat Variable", "3: Fusion Combo Heat Variable", 4700, 500, 20000, "");
		heatHHe = fusionConfig.getInt("HHe Heat Variable", "3: Fusion Combo Heat Variable", 4820, 500, 20000, "");
		heatHB = fusionConfig.getInt("HB Heat Variable", "3: Fusion Combo Heat Variable", 5660, 500, 20000, "");
		heatHLi6 = fusionConfig.getInt("HLi6 Heat Variable", "3: Fusion Combo Heat Variable", 4550, 500, 20000, "");
		heatHLi7 = fusionConfig.getInt("HLi7 Heat Variable", "3: Fusion Combo Heat Variable", 4640, 500, 20000, "");
		heatDD = fusionConfig.getInt("DD Heat Variable", "3: Fusion Combo Heat Variable", 4780, 500, 20000, "");
		heatDT = fusionConfig.getInt("DT Heat Variable", "3: Fusion Combo Heat Variable", 670, 500, 20000, "");
		heatDHe = fusionConfig.getInt("DHe Heat Variable", "3: Fusion Combo Heat Variable", 2370, 500, 20000, "");
		heatDB = fusionConfig.getInt("DB Heat Variable", "3: Fusion Combo Heat Variable", 5955, 500, 20000, "");
		heatDLi6 = fusionConfig.getInt("DLi6 Heat Variable", "3: Fusion Combo Heat Variable", 5335, 500, 20000, "");
		heatDLi7 = fusionConfig.getInt("DLi7 Heat Variable", "3: Fusion Combo Heat Variable", 7345, 500, 20000, "");
		heatTT = fusionConfig.getInt("TT Heat Variable", "3: Fusion Combo Heat Variable", 3875, 500, 20000, "");
		heatTHe = fusionConfig.getInt("THe Heat Variable", "3: Fusion Combo Heat Variable", 5070, 500, 20000, "");
		heatTB = fusionConfig.getInt("TB Heat Variable", "3: Fusion Combo Heat Variable", 7810, 500, 20000, "");
		heatTLi6 = fusionConfig.getInt("TLi6 Heat Variable", "3: Fusion Combo Heat Variable", 7510, 500, 20000, "");
		heatTLi7 = fusionConfig.getInt("TLi7 Heat Variable", "3: Fusion Combo Heat Variable", 8060, 500, 20000, "");
		heatHeHe = fusionConfig.getInt("HeHe Heat Variable", "3: Fusion Combo Heat Variable", 6800, 500, 20000, "");
		heatHeB = fusionConfig.getInt("HeB Heat Variable", "3: Fusion Combo Heat Variable", 8060, 500, 20000, "");
		heatHeLi6 = fusionConfig.getInt("HeLi6 Heat Variable", "3: Fusion Combo Heat Variable", 8800, 500, 20000, "");
		heatHeLi7 = fusionConfig.getInt("HeLi7 Heat Variable", "3: Fusion Combo Heat Variable", 12500, 500, 20000, "");
		heatBB = fusionConfig.getInt("BB Heat Variable", "3: Fusion Combo Heat Variable", 8500, 500, 20000, "");
		heatBLi6 = fusionConfig.getInt("BLi6 Heat Variable", "3: Fusion Combo Heat Variable", 9200, 500, 20000, "");
		heatBLi7 = fusionConfig.getInt("BLi7 Heat Variable", "3: Fusion Combo Heat Variable", 13000, 500, 20000, "");
		heatLi6Li6 = fusionConfig.getInt("Li6Li6 Heat Variable", "3: Fusion Combo Heat Variable", 12000, 500, 20000, "");
		heatLi6Li7 = fusionConfig.getInt("Li6Li7 Heat Variable", "3: Fusion Combo Heat Variable", 11000, 500, 20000, "");
		heatLi7Li7 = fusionConfig.getInt("Li7Li7 Heat Variable", "3: Fusion Combo Heat Variable", 14000, 500, 20000, "");
		
		acceleratorUpdateRate = acceleratorConfig.getInt("Number of ticks per update of Particle Accelerators", "!: Update Rate", 20, 1, 1000, "");
		EMUpdateRate = acceleratorConfig.getInt("Number of ticks per update of Electromagnet and Supercooler Tile Entities", "!: Update Rate", 100, 1, 1000, "");
		acceleratorSounds = acceleratorConfig.getBoolean("Enable Particle Accelerator Sound Effects", "!: Sounds", true, "");
		ringMaxSize = acceleratorConfig.getInt("Maximum Ring Size", "0: General", 200, 20, 2000, "");
		colliderRF = acceleratorConfig.getInt("Collider RF Requirement Multiplier", "0: General", 100, 10, 1000, "");
		colliderProduction = acceleratorConfig.getInt("Collider Production Multiplier", "0: General", 100, 10, 1000, "");
		synchrotronRF = acceleratorConfig.getInt("Synchrotron RF Requirement Multiplier", "0: General", 100, 10, 1000, "");
		synchrotronProduction = acceleratorConfig.getInt("Synchrotron Production Multiplier", "0: General", 100, 10, 1000, "");
		superElectromagnetRF = acceleratorConfig.getInt("Superconducting Electromagnet RF/t Requirement", "0: General", 500, 0, 10000, "");
		electromagnetHe = acceleratorConfig.getInt("Electromagnet Supercooler Liquid Helium mB/s Requirement", "0: General", 1, 0, 100, "");
		acceleratorProduction = acceleratorConfig.getInt("Synchrotron Production Multiplier", "0: General", 100, 10, 1000, "");

		config.save();
		fissionConfig.save();
		fusionConfig.save();
		acceleratorConfig.save();
		toolConfig.save();
		
		// Recipes
		/*RecipeSorter.register("nuclearcraft:workspaceshaped", NuclearWorkspaceShapedOreRecipe.class, Category.SHAPED, "after:minecraft:shaped");
		RecipeSorter.register("nuclearcraft:workspaceshapeless", NuclearWorkspaceShapelessOreRecipe.class, Category.SHAPELESS, "after:minecraft:shapeless");*/

		// Fluid Registry
		liquidHelium = new FluidHelium("liquidHelium");
		FluidRegistry.registerFluid(liquidHelium);
		NCBlocks.blockHelium = new BlockHelium(liquidHelium, liquidhelium.setReplaceable(), NuclearCraft.heliumfreeze).setCreativeTab(tabNC).setBlockName("liquidHeliumBlock");
		GameRegistry.registerBlock(NCBlocks.blockHelium, "liquidHeliumBlock");
		fusionPlasma = new FluidPlasma("fusionPlasma");
		FluidRegistry.registerFluid(fusionPlasma);
		NCBlocks.blockFusionPlasma = new BlockPlasma(fusionPlasma, fusionplasma.setReplaceable(), NuclearCraft.plasmaburn).setCreativeTab(tabNC).setBlockName("fusionPlasmaBlock");
		GameRegistry.registerBlock(NCBlocks.blockFusionPlasma, "fusionPlasmaBlock");
		
		steam = new FluidSteam("steam");
		FluidRegistry.registerFluid(steam);
		NCBlocks.blockSteam = new BlockSteam(steam, steamMaterial.setReplaceable(), NuclearCraft.steamburn).setCreativeTab(tabNC).setBlockName("steamBlock");
		GameRegistry.registerBlock(NCBlocks.blockSteam, "steamBlock");
		
		denseSteam = new FluidDenseSteam("denseSteam");
		FluidRegistry.registerFluid(denseSteam);
		NCBlocks.blockDenseSteam = new BlockDenseSteam(denseSteam, steamMaterial.setReplaceable(), NuclearCraft.steamburn).setCreativeTab(tabNC).setBlockName("denseSteamBlock");
		GameRegistry.registerBlock(NCBlocks.blockDenseSteam, "denseSteamBlock");
		
		superdenseSteam = new FluidSuperdenseSteam("superdenseSteam");
		FluidRegistry.registerFluid(superdenseSteam);
		NCBlocks.blockSuperdenseSteam = new BlockSuperdenseSteam(superdenseSteam, steamMaterial.setReplaceable(), NuclearCraft.steamburn).setCreativeTab(tabNC).setBlockName("superdenseSteamBlock");
		GameRegistry.registerBlock(NCBlocks.blockSuperdenseSteam, "superdenseSteamBlock");
		
		// Ore Registry
		GameRegistry.registerBlock(NCBlocks.blockOre = new BlockOre("blockOre", Material.rock), ItemBlockOre.class, "blockOre");
		
		// Block Registry
		GameRegistry.registerBlock(NCBlocks.blockBlock = new BlockBlock("blockBlock", Material.iron), ItemBlockBlock.class, "blockBlock");
		
		NCBlocks.simpleQuantumUp = new BlockSimpleQuantum(true).setBlockName("simpleQuantumUp").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.simpleQuantumUp, ItemBlockSimpleQuantum.class, "simpleQuantumUp");
		NCBlocks.simpleQuantumDown = new BlockSimpleQuantum(false).setBlockName("simpleQuantumDown").setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.simpleQuantumDown, ItemBlockSimpleQuantum.class, "simpleQuantumDown");
		
		NCBlocks.graphiteBlock = new BlockGraphiteBlock().setBlockName("graphiteBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeStone).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.graphiteBlock, ItemBlockGraphiteBlock.class, "graphiteBlock");
		NCBlocks.cellBlock = new BlockCellBlock().setBlockName("cellBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.cellBlock, ItemBlockCellBlock.class, "cellBlock");
		NCBlocks.reactorBlock = new BlockReactorBlock().setBlockName("reactorBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.reactorBlock, ItemBlockReactorBlock.class, "reactorBlock");
		NCBlocks.fusionConnector = new BlockFusionConnector().setBlockName("fusionConnector").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.fusionConnector, ItemBlockFusionConnector.class, "fusionConnector");
		
		NCBlocks.coolerBlock = new BlockCoolerBlock().setBlockName("coolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.coolerBlock, ItemBlockCoolerBlock.class, "coolerBlock");
		NCBlocks.emptyCoolerBlock = new BlockCoolerBlock().setBlockName("emptyCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.emptyCoolerBlock, "emptyCoolerBlock");
		NCBlocks.waterCoolerBlock = new BlockCoolerBlock().setBlockName("waterCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.waterCoolerBlock, ItemBlockCoolerBlock.class, "waterCoolerBlock");
		NCBlocks.cryotheumCoolerBlock = new BlockCoolerBlock().setBlockName("cryotheumCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F).setLightLevel(0.25F);
		GameRegistry.registerBlock(NCBlocks.cryotheumCoolerBlock, ItemBlockCoolerBlock.class, "cryotheumCoolerBlock");
		NCBlocks.redstoneCoolerBlock = new BlockCoolerBlock().setBlockName("redstoneCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F).setLightLevel(0.25F);
		GameRegistry.registerBlock(NCBlocks.redstoneCoolerBlock, ItemBlockCoolerBlock.class, "redstoneCoolerBlock");
		NCBlocks.enderiumCoolerBlock = new BlockCoolerBlock().setBlockName("enderiumCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F).setLightLevel(0.25F);
		GameRegistry.registerBlock(NCBlocks.enderiumCoolerBlock, ItemBlockCoolerBlock.class, "enderiumCoolerBlock");
		NCBlocks.glowstoneCoolerBlock = new BlockCoolerBlock().setBlockName("glowstoneCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F).setLightLevel(1F);
		GameRegistry.registerBlock(NCBlocks.glowstoneCoolerBlock, ItemBlockCoolerBlock.class, "glowstoneCoolerBlock");
		NCBlocks.coolantCoolerBlock = new BlockCoolerBlock().setBlockName("coolantCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.coolantCoolerBlock, ItemBlockCoolerBlock.class, "coolantCoolerBlock");
		NCBlocks.heliumCoolerBlock = new BlockCoolerBlock().setBlockName("heliumCoolerBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.heliumCoolerBlock, ItemBlockCoolerBlock.class, "heliumCoolerBlock");
		
		NCBlocks.speedBlock = new BlockSpeedBlock().setBlockName("speedBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.speedBlock, ItemBlockSpeedBlock.class, "speedBlock");
		NCBlocks.blastBlock = new BlockBlastBlock().setBlockName("blastBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(6000.0F).setHardness(10.0F);
		GameRegistry.registerBlock(NCBlocks.blastBlock, ItemBlockBlastBlock.class, "blastBlock");
		NCBlocks.machineBlock = new BlockMachineBlock().setBlockName("machineBlockNC").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.machineBlock, "machineBlockNC");
		
		NCBlocks.tubing1 = new BlockTubing1().setBlockName("tubing1").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.tubing1, "tubing1");
		NCBlocks.tubing2 = new BlockTubing2().setBlockName("tubing2").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(2.0F);
		GameRegistry.registerBlock(NCBlocks.tubing2, "tubing2");
	
		// Machine Registry
			// Block
		NCBlocks.electromagnetIdle = new BlockElectromagnet(false).setBlockName("electromagnetIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.electromagnetIdle, ItemBlockElectromagnet.class, "electromagnetIdle");
		NCBlocks.electromagnetActive = new BlockElectromagnet(true).setBlockName("electromagnetActive").setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.electromagnetActive, ItemBlockElectromagnet.class, "electromagnetActive");
		NCBlocks.superElectromagnetIdle = new BlockSuperElectromagnet(false).setBlockName("superElectromagnetIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.superElectromagnetIdle, ItemBlockSuperElectromagnet.class, "superElectromagnetIdle");
		NCBlocks.superElectromagnetActive = new BlockSuperElectromagnet(true).setBlockName("superElectromagnetActive").setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.superElectromagnetActive, ItemBlockSuperElectromagnet.class, "superElectromagnetActive");
		NCBlocks.supercoolerIdle = new BlockSupercooler(false).setBlockName("supercoolerIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.supercoolerIdle, ItemBlockSupercooler.class, "supercoolerIdle");
		NCBlocks.supercoolerActive = new BlockSupercooler(true).setBlockName("supercoolerActive").setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.supercoolerActive, ItemBlockSupercooler.class, "supercoolerActive");
		NCBlocks.synchrotronIdle = new BlockSynchrotron(false).setBlockName("synchrotronIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.synchrotronIdle, ItemBlockSynchrotron.class, "synchrotronIdle");
		NCBlocks.synchrotronActive = new BlockSynchrotron(true).setBlockName("synchrotronActive").setStepSound(Block.soundTypeMetal).setResistance(8.0F).setHardness(3.0F);
		GameRegistry.registerBlock(NCBlocks.synchrotronActive, ItemBlockSynchrotron.class, "synchrotronActive");
		
		NCBlocks.nuclearWorkspace = new BlockNuclearWorkspace(Material.iron).setBlockName("nuclearWorkspace").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.nuclearWorkspace, ItemBlockNuclearWorkspace.class, "nuclearWorkspace");
		
		NCBlocks.fusionReactor = new BlockFusionReactor(Material.iron).setBlockName("fusionReactor").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactor, ItemBlockFusionReactor.class, "fusionReactor");
		NCBlocks.fusionReactorBlock = new BlockFusionReactorBlock().setBlockName("fusionReactorBlock").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactorBlock, "fusionReactorBlock");
		NCBlocks.fusionReactorBlockTop = new BlockFusionReactorBlockTop().setBlockName("fusionReactorBlockTop").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactorBlockTop, "fusionReactorBlockTop");
		
		NCBlocks.fusionReactorSteam = new BlockFusionReactorSteam(Material.iron).setBlockName("fusionReactorSteam").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactorSteam, ItemBlockFusionReactorSteam.class, "fusionReactorSteam");
		NCBlocks.fusionReactorSteamBlock = new BlockFusionReactorSteamBlock().setBlockName("fusionReactorSteamBlock").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactorSteamBlock, "fusionReactorSteamBlock");
		NCBlocks.fusionReactorSteamBlockTop = new BlockFusionReactorSteamBlockTop().setBlockName("fusionReactorSteamBlockTop").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fusionReactorSteamBlockTop, "fusionReactorSteamBlockTop");
		
		NCBlocks.nuclearFurnaceIdle = new BlockNuclearFurnace(false).setBlockName("nuclearFurnaceIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.nuclearFurnaceIdle, ItemBlockNuclearFurnace.class, "nuclearFurnaceIdle");
		NCBlocks.nuclearFurnaceActive = new BlockNuclearFurnace(true).setBlockName("nuclearFurnaceActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.nuclearFurnaceActive, ItemBlockNuclearFurnace.class, "nuclearFurnaceActive");
		NCBlocks.furnaceIdle = new BlockFurnace(false).setBlockName("furnaceIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.furnaceIdle, ItemBlockFurnace.class, "furnaceIdle");
		NCBlocks.furnaceActive = new BlockFurnace(true).setBlockName("furnaceActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.furnaceActive, ItemBlockFurnace.class, "furnaceActive");
		
		NCBlocks.reactionGeneratorIdle = new BlockReactionGenerator(false).setBlockName("reactionGeneratorIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.reactionGeneratorIdle, ItemBlockReactionGenerator.class, "reactionGeneratorIdle");
		NCBlocks.reactionGeneratorActive = new BlockReactionGenerator(true).setBlockName("reactionGeneratorActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.reactionGeneratorActive, ItemBlockReactionGenerator.class, "reactionGeneratorActive");
		
		NCBlocks.fissionReactorGraphiteIdle = new BlockFissionReactor(false).setBlockName("fissionReactorGraphiteIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fissionReactorGraphiteIdle, ItemBlockFissionReactor.class, "fissionReactorGraphiteIdle");
		NCBlocks.fissionReactorGraphiteActive = new BlockFissionReactor(true).setBlockName("fissionReactorGraphiteActive").setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fissionReactorGraphiteActive, ItemBlockFissionReactor.class, "fissionReactorGraphiteActive");
		
		NCBlocks.fissionReactorSteamIdle = new BlockFissionReactorSteam(false).setBlockName("fissionReactorSteamIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fissionReactorSteamIdle, ItemBlockFissionReactorSteam.class, "fissionReactorSteamIdle");
		NCBlocks.fissionReactorSteamActive = new BlockFissionReactorSteam(true).setBlockName("fissionReactorSteamActive").setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fissionReactorSteamActive, ItemBlockFissionReactorSteam.class, "fissionReactorSteamActive");

		NCBlocks.fissonReactorProxy = new BlockFissonReactorProxy().setBlockName("fissionReactorProxy").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.fissonReactorProxy, ItemBlockFissionReactorProxy.class, "fissionReactorProxy");

		NCBlocks.RTG = new BlockRTG().setBlockName("RTG").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.RTG, ItemBlockRTG.class, "RTG");
		NCBlocks.AmRTG = new BlockAmRTG().setBlockName("AmRTG").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.AmRTG, ItemBlockAmRTG.class, "AmRTG");
		NCBlocks.CfRTG = new BlockCfRTG().setBlockName("CfRTG").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.CfRTG, ItemBlockCfRTG.class, "CfRTG");
		NCBlocks.WRTG = new BlockWRTG().setBlockName("WRTG").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.WRTG, ItemBlockWRTG.class, "WRTG");
		NCBlocks.solarPanel = new BlockSolarPanel().setBlockName("solarPanel").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.solarPanel, ItemBlockSolarPanel.class, "solarPanel");
		
		NCBlocks.lithiumIonBatteryBlock = new BlockLithiumIonBattery().setBlockName("lithiumIonBatteryBlock").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.lithiumIonBatteryBlock, ItemBlockLithiumIonBattery.class, "lithiumIonBatteryBlock");
		NCBlocks.voltaicPile = new BlockVoltaicPile().setBlockName("voltaicPile").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.voltaicPile, ItemBlockVoltaicPile.class, "voltaicPile");
		
		NCBlocks.separatorIdle = new BlockSeparator(false).setBlockName("separatorIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.separatorIdle, ItemBlockSeparator.class, "separatorIdle");
		NCBlocks.separatorActive = new BlockSeparator(true).setBlockName("separatorActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.separatorActive, ItemBlockSeparator.class, "separatorActive");
		NCBlocks.hastenerIdle = new BlockHastener(false).setBlockName("hastenerIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.hastenerIdle, ItemBlockHastener.class, "hastenerIdle");
		NCBlocks.hastenerActive = new BlockHastener(true).setBlockName("hastenerActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.hastenerActive, ItemBlockHastener.class, "hastenerActive");
		
		NCBlocks.collectorIdle = new BlockCollector(false).setBlockName("collectorIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.collectorIdle, ItemBlockCollector.class, "collectorIdle");
		NCBlocks.collectorActive = new BlockCollector(true).setBlockName("collectorActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.collectorActive, ItemBlockCollector.class, "collectorActive");
		NCBlocks.oxidiserIdle = new BlockOxidiser("oxidiserIdle", Material.iron).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.oxidiserIdle, ItemBlockOxidiser.class, "oxidiserIdle");
		NCBlocks.oxidiserActive = new BlockOxidiser("oxidiserIdle", Material.iron).setBlockName("oxidiserActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.oxidiserActive, ItemBlockOxidiser.class, "oxidiserActive");
		NCBlocks.ioniserIdle = new BlockIoniser(false).setBlockName("ioniserIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.ioniserIdle, ItemBlockIoniser.class, "ioniserIdle");
		NCBlocks.ioniserActive = new BlockIoniser(true).setBlockName("ioniserActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.ioniserActive, ItemBlockIoniser.class, "ioniserActive");
		NCBlocks.irradiatorIdle = new BlockIrradiator(false).setBlockName("irradiatorIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.irradiatorIdle, ItemBlockIrradiator.class, "irradiatorIdle");
		NCBlocks.irradiatorActive = new BlockIrradiator(true).setBlockName("irradiatorActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.irradiatorActive, ItemBlockIrradiator.class, "irradiatorActive");
		NCBlocks.coolerIdle = new BlockCooler(false).setBlockName("coolerIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.coolerIdle, ItemBlockCooler.class, "coolerIdle");
		NCBlocks.coolerActive = new BlockCooler(true).setBlockName("coolerActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.coolerActive, ItemBlockCooler.class, "coolerActive");
		NCBlocks.heliumExtractorIdle = new BlockHeliumExtractor(false).setBlockName("heliumExtractorIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.heliumExtractorIdle, ItemBlockHeliumExtractor.class, "heliumExtractorIdle");
		NCBlocks.heliumExtractorActive = new BlockHeliumExtractor(true).setBlockName("heliumExtractorActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.heliumExtractorActive, ItemBlockHeliumExtractor.class, "heliumExtractorActive");
		NCBlocks.recyclerIdle = new BlockRecycler(false).setBlockName("recyclerIdle").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.recyclerIdle, ItemBlockRecycler.class, "recyclerIdle");
		NCBlocks.recyclerActive = new BlockRecycler(true).setBlockName("recyclerActive").setStepSound(Block.soundTypeMetal).setResistance(20.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.recyclerActive, ItemBlockRecycler.class, "recyclerActive");
		
		NCBlocks.steamGenerator = new BlockSteamGenerator().setBlockName("steamGenerator").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.steamGenerator, ItemBlockSteamGenerator.class, "steamGenerator");
		NCBlocks.steamDecompressor = new BlockSteamDecompressor().setBlockName("steamDecompressor").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.steamDecompressor, ItemBlockSteamDecompressor.class, "steamDecompressor");
		NCBlocks.denseSteamDecompressor = new BlockDenseSteamDecompressor().setBlockName("denseSteamDecompressor").setCreativeTab(tabNC).setStepSound(Block.soundTypeMetal).setResistance(5.0F).setHardness(5.0F);
		GameRegistry.registerBlock(NCBlocks.denseSteamDecompressor, ItemBlockDenseSteamDecompressor.class, "denseSteamDecompressor");
		
		NCBlocks.nuke = new BlockNuke().setBlockName("nuke").setCreativeTab(tabNC).setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.nuke, ItemBlockNuke.class, "nuke");
		NCBlocks.nukeE = new BlockNukeExploding().setBlockName("nukeE").setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.nukeE, ItemBlockNuke.class, "nukeE");
		
		NCBlocks.antimatterBomb = new BlockAntimatterBomb().setBlockName("antimatterBomb").setCreativeTab(tabNC).setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.antimatterBomb, ItemBlockAntimatterBomb.class, "antimatterBomb");
		NCBlocks.antimatterBombE = new BlockAntimatterBombExploding().setBlockName("antimatterBombE").setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.antimatterBombE, ItemBlockAntimatterBomb.class, "antimatterBombE");
		
		NCBlocks.EMP = new BlockEMP().setBlockName("EMP").setCreativeTab(tabNC).setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.EMP, ItemBlockEMP.class, "EMP");
		NCBlocks.EMPE = new BlockEMPExploding().setBlockName("EMPE").setStepSound(Block.soundTypeCloth).setHardness(0.0F);
		GameRegistry.registerBlock(NCBlocks.EMPE, ItemBlockEMP.class, "EMPE");
			
			// Tile Entity
		GameRegistry.registerTileEntity(TileNuclearFurnace.class, "nuclearFurnaceNC");
		GameRegistry.registerTileEntity(TileFurnace.class, "furnaceNC");
		GameRegistry.registerTileEntity(TileReactionGenerator.class, "reactionGeneratorNC");
		GameRegistry.registerTileEntity(TileSeparator.class, "separatorNC");
		GameRegistry.registerTileEntity(TileHastener.class, "hastenerNC");
		GameRegistry.registerTileEntity(TileCollector.class, "collectorNC");
		GameRegistry.registerTileEntity(TileFissionReactor.class, "fissionReactorGraphiteNC");
		GameRegistry.registerTileEntity(TileFissionReactorProxy.class, "fissionReactorEnergyProxyNC");
		GameRegistry.registerTileEntity(TileFissionReactorSteam.class, "fissionReactorSteamNC");
		GameRegistry.registerTileEntity(TileNuclearWorkspace.class, "nuclearWorkspaceNC");
		GameRegistry.registerTileEntity(TileFusionReactor.class, "fusionReactorNC");
		GameRegistry.registerTileEntity(TileFusionReactorSteam.class, "fusionReactorSteamNC");
		GameRegistry.registerTileEntity(TileTubing1.class, "TEtubing1NC");
		GameRegistry.registerTileEntity(TileTubing2.class, "TEtubing2NC");
		GameRegistry.registerTileEntity(TileRTG.class, "RTGNC");
		GameRegistry.registerTileEntity(TileAmRTG.class, "AmRTGNC");
		GameRegistry.registerTileEntity(TileCfRTG.class, "CfRTGNC");
		GameRegistry.registerTileEntity(TileWRTG.class, "WRTGNC");
		GameRegistry.registerTileEntity(TileSteamGenerator.class, "steamGeneratorNC");
		GameRegistry.registerTileEntity(TileSteamDecompressor.class, "steamDecompressorNC");
		GameRegistry.registerTileEntity(TileDenseSteamDecompressor.class, "denseSteamDecompressorNC");
		GameRegistry.registerTileEntity(TileFusionReactorBlock.class, "fusionReactorBlockNC");
		GameRegistry.registerTileEntity(TileFusionReactorBlockTop.class, "fusionReactorBlockTopNC");
		GameRegistry.registerTileEntity(TileFusionReactorSteamBlock.class, "fusionReactorSteamBlockNC");
		GameRegistry.registerTileEntity(TileFusionReactorSteamBlockTop.class, "fusionReactorSteamBlockTopNC");
		GameRegistry.registerTileEntity(TileIoniser.class, "ioniserNC");
		GameRegistry.registerTileEntity(TileIrradiator.class, "irradiatorNC");
		GameRegistry.registerTileEntity(TileCooler.class, "coolerNC");
		GameRegistry.registerTileEntity(TileHeliumExtractor.class, "heliumExtractorNC");
		GameRegistry.registerTileEntity(TileRecycler.class, "recyclerNC");
		GameRegistry.registerTileEntity(TileSolarPanel.class, "solarPanelNC");
		
		GameRegistry.registerTileEntity(TileElectromagnet.class, "electromagnetNC");
		GameRegistry.registerTileEntity(TileSuperElectromagnet.class, "superElectromagnetNC");
		GameRegistry.registerTileEntity(TileSupercooler.class, "supercoolerNC");
		GameRegistry.registerTileEntity(TileSynchrotron.class, "synchrotronNC");
		
		GameRegistry.registerTileEntity(TileLithiumIonBattery.class, "lithiumIonBatteryBlockNC");
		GameRegistry.registerTileEntity(TileVoltaicPile.class, "voltaicPileNC");
		
		GameRegistry.registerTileEntity(TileSimpleQuantum.class, "simpleQuantumNC");
	
		// Item Registry
		GameRegistry.registerItem(NCItems.fuel = new ItemFuel(), "fuel");
		GameRegistry.registerItem(NCItems.material = new ItemMaterial(), "material");
		GameRegistry.registerItem(NCItems.parts = new ItemPart(), "parts");

		NCItems.upgradeSpeed = new ItemUpgrade("upgradeSpeed", "Used to increase the speed of machines.", "Stacked upgrades increase the speed exponentially.").setMaxStackSize(8);
		GameRegistry.registerItem(NCItems.upgradeSpeed, "upgradeSpeed");
		NCItems.upgradeEnergy = new ItemUpgrade("upgradeEnergy", "Used to increase the energy efficiency of machines.", "Stacked upgrades increase the efficiency exponentially.").setMaxStackSize(8);
		GameRegistry.registerItem(NCItems.upgradeEnergy, "upgradeEnergy");

		//Records
		NCItems.recordPractice = new NCRecord(0, "Practice", "recordPractice", "Whenever Jimmy has some new discoveries", "to test out, his virtual practice labs are", "the best places to see what's possible...");
		GameRegistry.registerItem(NCItems.recordPractice, "recordPractice");
		NCItems.recordArea51 = new NCRecord(0, "Area51", "recordArea51", "Jimmy, with his newly aquired map, must", "make his way to the mines of Area 51", "to recover his invisibility technology...");
		GameRegistry.registerItem(NCItems.recordArea51, "recordArea51");
		NCItems.recordNeighborhood = new NCRecord(0, "Neighborhood", "recordNeighborhood", "Jimmy's hometown - a quiet and green place", "with roads to many great places such as", "Retroland and Downtown...");
		GameRegistry.registerItem(NCItems.recordNeighborhood, "recordNeighborhood");
		
		//Blank
		NCItems.blank = new ItemNC("", "blank", false);
		GameRegistry.registerItem(NCItems.blank, "blank");
		
		//Antimatter
		NCItems.antimatter = new ItemAntimatter("antimatter", "This is a product of the Synchrotron.", "DO NOT drop this on the floor, or your base...", "...may go sadface...");
		GameRegistry.registerItem(NCItems.antimatter, "antimatter");
		
		//Batteries
		NCItems.lithiumIonBattery = new ItemBattery(lithiumIonRF, (int) Math.ceil(lithiumIonRF/20), "lithiumIonBattery", "A battery capable of storing " + (NuclearCraft.lithiumIonRF >= 1000000 ? NuclearCraft.lithiumIonRF/1000000 + " M" : (NuclearCraft.lithiumIonRF >= 1000 ? NuclearCraft.lithiumIonRF/1000 + " k" : NuclearCraft.lithiumIonRF + " ")) + "RF.");
		GameRegistry.registerItem(NCItems.lithiumIonBattery, "lithiumIonBattery");
		
		// Block Crafting Recipes
		b(0, "ingotCopper");
		b(1, "ingotTin");
		b(2, "ingotLead");
		b(3, "ingotSilver");
		b(4, "ingotUranium");
		b(5, "ingotThorium");
		b(6, "ingotBronze");
		b(8, "ingotLithium");
		b(9, "ingotBoron");
		b(10, "ingotMagnesium");
		b(NCBlocks.graphiteBlock, "ingotGraphite");
		
		// Tiny Dust to Full Dust
		m(17, "dustTinyLead");
		
		// Isotope Lump Recipes
		m(24, "tinyU238Base");
		m(26, "tinyU235Base");
		m(28, "tinyU233Base");
		m(30, "tinyPu238Base");
		m(32, "tinyPu239Base");
		m(34, "tinyPu242Base");
		m(36, "tinyPu241Base");
		m(38, "tinyTh232Base");
		m(40, "tinyTh230Base");
		
		m(55, "tinyU238Oxide");
		m(57, "tinyU235Oxide");
		m(59, "tinyU233Oxide");
		m(61, "tinyPu238Oxide");
		m(63, "tinyPu239Oxide");
		m(65, "tinyPu242Oxide");
		m(67, "tinyPu241Oxide");
		m(82, "tinyTh232Oxide");
		m(84, "tinyTh230Oxide");
		
		m(46, "tinyLi6");
		m(48, "tinyB10");
		
		m(86, "tinyNp236Base");
		m(88, "tinyNp237Base");
		m(90, "tinyAm241Base");
		m(92, "tinyAm242Base");
		m(94, "tinyAm243Base");
		m(96, "tinyCm243Base");
		m(98, "tinyCm245Base");
		m(100, "tinyCm246Base");
		m(102, "tinyCm247Base");
		m(122, "tinyCf250Base");
		
		m(104, "tinyNp236Oxide");
		m(106, "tinyNp237Oxide");
		m(108, "tinyAm241Oxide");
		m(110, "tinyAm242Oxide");
		m(112, "tinyAm243Oxide");
		m(114, "tinyCm243Oxide");
		m(116, "tinyCm245Oxide");
		m(118, "tinyCm246Oxide");
		m(120, "tinyCm247Oxide");
		m(124, "tinyCf250Oxide");

		// Simple Shapeless Crafting Recipes
			//Copper Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 0), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 0)});
			//Tin Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 1), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 1)});
			//Lead Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 2), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 2)});
			//Silver Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 3), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 3)});
			//Uranium Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 4), new ItemStack(NCBlocks.blockBlock, 1, 4));
			//Thorium Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 5), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 5)});
			//Bronze Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 6), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 6)});
			//Tough Alloy Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 25, 7), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 7)});
			//Lithium Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 42), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 8)});
			//Boron Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 43), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 9)});
			//Magnesium Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 50), new Object[] {new ItemStack(NCBlocks.blockBlock, 1, 10)});
			//Graphite Ingot
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.material, 9, 76), new Object[] {new ItemStack(NCBlocks.graphiteBlock, 1)});

			//Reinforced Tubings - Horizontal & Vertical
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.tubing1, 1), new Object[] {new ItemStack(NCBlocks.tubing2)});
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.tubing2, 1), new Object[] {new ItemStack(NCBlocks.tubing1)});
		
		// Complex Shapeless Crafting Recipes
			//Bronze - Ingot + Dust
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 4, 6), new Object[] {"ingotCopper", "ingotCopper", "ingotCopper", "ingotTin"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 4, 21), new Object[] {"dustCopper", "dustCopper", "dustCopper", "dustTin"}));

			//Magnesium Diboride - Ingot + Dust
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 3, 71), new Object[] {"ingotMagnesium", "ingotBoron", "ingotBoron"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 3, 72), new Object[] {"dustMagnesium", "dustBoron", "dustBoron"}));

			//Uranium RTG Capsule
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, 141), new Object[] {new ItemStack(NCItems.fuel, 1, 48), "U238"}));
			//Americium RTG Capsule
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, 139), new Object[] {new ItemStack(NCItems.fuel, 1, 48), "Am241"}));
			//Plutonium RTG Capsule
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, 46), new Object[] {new ItemStack(NCItems.fuel, 1, 48), "Pu238"}));
			//Californium RTG Capsule
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, 140), new Object[] {new ItemStack(NCItems.fuel, 1, 48), "Cf250"}));

		// Fission Fuel Shapeless Recipes
		l(0, "U238", "U235Base");
		h(1, "U238", "U235Base");
		l(2, "Pu242", "Pu239Base");
		h(3, "Pu242", "Pu239Base");
		l(4, "U238", "Pu239Oxide");
		f(5, "Th232Base");
		l(6, "U238", "U233Base");
		h(7, "U238", "U233Base");
		l(8, "Pu242", "Pu241Base");
		h(9, "Pu242", "Pu241Base");
		l(10, "U238", "Pu241Oxide");
		f(76, "Th232Oxide");
		
		l(51, "U238", "U235Oxide");
		h(52, "U238", "U235Oxide");
		l(53, "Pu242", "Pu239Oxide");
		h(54, "Pu242", "Pu239Oxide");
		l(55, "U238", "U233Oxide");
		h(56, "U238", "U233Oxide");
		l(57, "Pu242", "Pu241Oxide");
		h(58, "Pu242", "Pu241Oxide");
		
		l(79, "Np237", "Np236Base");
		h(80, "Np237", "Np236Base");
		l(81, "Am243", "Am242Base");
		h(82, "Am243", "Am242Base");
		l(83, "Cm246", "Cm243Base");
		h(84, "Cm246", "Cm243Base");
		l(85, "Cm246", "Cm245Base");
		h(86, "Cm246", "Cm245Base");
		l(87, "Cm246", "Cm247Base");
		h(88, "Cm246", "Cm247Base");
		
		l(89, "Np237", "Np236Oxide");
		h(90, "Np237", "Np236Oxide");
		l(91, "Am243", "Am242Oxide");
		h(92, "Am243", "Am242Oxide");
		l(93, "Cm246", "Cm243Oxide");
		h(94, "Cm246", "Cm243Oxide");
		l(95, "Cm246", "Cm245Oxide");
		h(96, "Cm246", "Cm245Oxide");
		l(97, "Cm246", "Cm247Oxide");
		h(98, "Cm246", "Cm247Oxide");
		
		// Fuel Cell Shapeless Recipes
		c(11, "LEU235");
		c(12, "HEU235");
		c(13, "LEP239");
		c(14, "HEP239");
		c(15, "MOX239");
		c(16, "TBU");
		c(17, "LEU233");
		c(18, "HEU233");
		c(19, "LEP241");
		c(20, "HEP241");
		c(21, "MOX241");
		c(77, "TBUOxide");
		
		c(59, "LEU235Oxide");
		c(60, "HEU235Oxide");
		c(61, "LEP239Oxide");
		c(62, "HEP239Oxide");
		c(63, "LEU233Oxide");
		c(64, "HEU233Oxide");
		c(65, "LEP241Oxide");
		c(66, "HEP241Oxide");
		
		c(99, "LEN236");
		c(100, "HEN236");
		c(101, "LEA242");
		c(102, "HEA242");
		c(103, "LEC243");
		c(104, "HEC243");
		c(105, "LEC245");
		c(106, "HEC245");
		c(107, "LEC247");
		c(108, "HEC247");
		
		c(109, "LEN236Oxide");
		c(110, "HEN236Oxide");
		c(111, "LEA242Oxide");
		c(112, "HEA242Oxide");
		c(113, "LEC243Oxide");
		c(114, "HEC243Oxide");
		c(115, "LEC245Oxide");
		c(116, "HEC245Oxide");
		c(117, "LEC247Oxide");
		c(118, "HEC247Oxide");
		
		c(41, "Li6");
		c(42, "Li7");
		c(43, "B10");
		c(44, "B11");

		// Simple Shaped Crafting Recipes
			//Empty Fission Cell
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.fuel, 4, 33),  new Object[] {" I ", "IGI", " I ", 'I', "ingotSteel", 'G', "blockGlassColorless"}));
			//Empty Fluid Cell
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.fuel, 4, 45),  new Object[] {" I ", "IGI", " I ", 'I', "ingotIron", 'G', "blockGlassColorless"}));
			//Empty Capsule
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.fuel, 4, 48),  new Object[] {"IPI", "P P", "IPI", 'I', "ingotSteel", 'P', "plateBasic"}));
			//Nuclear Furnace
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.nuclearFurnaceIdle,  new Object[] {"XIX", "IFI", "XIX", 'X', "plateBasic", 'I', "ingotRefinedObsidian", 'F', Blocks.furnace}));

		//Mekanism Shaped Recipes
		ItemStack atomicManipulator = GameRegistry.findItemStack("minechem", "minechemAtomicManipulator", 1);
		ItemStack rotationalComplex = GameRegistry.findItemStack("MekanismGenerators", "Generator", 1);
		rotationalComplex.setItemDamage(8);

		ItemStack machineCasing = GameRegistry.findItemStack("Mekanism", "BasicBlock", 1);
		machineCasing.setItemDamage(8);

		ItemStack structuralGlass = GameRegistry.findItemStack("Mekanism", "BasicBlock", 1);
		structuralGlass.setItemDamage(10);

		ItemStack elecroCore = GameRegistry.findItemStack("Mekanism", "ElectrolyticCore", 1);

		ItemStack polyetheneBlock = GameRegistry.findItemStack("Mekanism", "PlasticBlock", 1);
		polyetheneBlock.setItemDamage(15);

		ItemStack pressureDispenser = GameRegistry.findItemStack("Mekanism", "BasicBlock2", 1);
		pressureDispenser.setItemDamage(6);

		ItemStack enrichedAlloy = GameRegistry.findItemStack("Mekanism", "EnrichedAlloy", 1);
		ItemStack reinforcedAlloy = GameRegistry.findItemStack("Mekanism", "ReinforcedAlloy", 1);

		ItemStack reinforedPlastic = GameRegistry.findItemStack("Mekanism", "ReinforcedPlasticBlock", 1);
		reinforedPlastic.setItemDamage(15);

		ItemStack polyethenePlate = GameRegistry.findItemStack("Mekanism", "Polyethene", 1);
		polyethenePlate.setItemDamage(2);

		ItemStack advControlCircuit = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);
		advControlCircuit.setItemDamage(1);

		ItemStack eliteControlCircuit = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);
		eliteControlCircuit.setItemDamage(2);


			//Basic Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.parts, 8, 0), new Object[] {"PLP", "LGL", "PLP", 'P', polyethenePlate, 'L', "ingotLead", 'G', "dustGraphite"}));
			//Computer Plating
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.parts, 2, 18), new Object[] {"SCS", "IRI", "SCS", 'S', "itemSilicon", 'C', advControlCircuit, 'R', "plateReinforced", 'I', "ingotGold"}));

			//Isotope Separator
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.separatorIdle, new Object[] {"BRB", "GMG", "BCB", 'B', "plateBasic", 'G', "gearGold", 'R', atomicManipulator != null ? atomicManipulator : rotationalComplex, 'M', machineCasing, 'C', "coilGold"}));
			//Decay Hastener
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.hastenerIdle, new Object[] {"BUB", "GMG", "BCB",'B', "plateBasic",'U', "plateDU", 'G', "gearLead", 'M', machineCasing, 'C', "coilGold"}));
			//Ioniser
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.ioniserIdle, new Object[] {"BDB", "GMG", "BCB",'B', "plateBasic",'D', new ItemStack(NCItems.parts, 1, 17), 'G', "gearSignalum", 'M', machineCasing, 'C', "coilGold"}));
			//Helium Liquifier
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.coolerIdle, new Object[] {"BKB", "GMG", "BCB",'B', "plateBasic",'K', new ItemStack(NCBlocks.cryotheumCoolerBlock, 1, 0), 'G', "gearBronze", 'M', machineCasing, 'C', "coilGold"}));
			//Helium Collector
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.collectorIdle, new Object[] {"BTB", "TXT", "BTB",'B', "plateAdvanced",'T', new ItemStack(NCBlocks.blockBlock, 1, 5), 'X', new ItemStack(Blocks.iron_bars)}));
			//Recycler
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.recyclerIdle, new Object[] {"BEB", "GMG", "BCB",'B', "plateBasic",'E', elecroCore, 'G', "gearEnderium", 'M', machineCasing, 'C', "coilGold"}));
			//Steam Decompressor
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.steamDecompressor, new Object[] {"PDP", "DAD", "PDP", 'P', polyetheneBlock, 'D', pressureDispenser, 'A', enrichedAlloy}));
			//Dense Steam Decompressor
		GameRegistry.addRecipe(new ShapedOreRecipe(NCBlocks.denseSteamDecompressor, new Object[] {"RDR", "DAD", "RDR", 'R', reinforedPlastic, 'D', new ItemStack(NCBlocks.steamDecompressor, 1, 0), 'A', reinforcedAlloy}));

			//Speed Upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(NCItems.upgradeSpeed, new Object[] {"IBI", "ESE", "ICI", 'I', "ingotRefinedLapis", 'B', "plateBasic", 'E', new ItemStack(NCItems.parts, 1, 17), 'S', "coilSilver", 'C', eliteControlCircuit}));
			//Energy Upgrade
		GameRegistry.addRecipe(new ShapedOreRecipe(NCItems.upgradeEnergy, new Object[] {"IBI", "ESE", "ICI", 'I', "ingotRefinedGlowstone", 'B', "plateBasic", 'E', new ItemStack(NCItems.parts, 1, 12), 'S', "coilGold", 'C', eliteControlCircuit}));

			//Reactor Glass
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCBlocks.reactorBlock,4, 1),  new Object[] {" R ", "RGR", " R ", 'R', new ItemStack(NCBlocks.reactorBlock), 'G', structuralGlass}));

			//Reactor Port
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCBlocks.fissonReactorProxy, 1), new Object[] {"ARA", "RCR", "ARA", 'A', enrichedAlloy, 'R', new ItemStack(NCBlocks.reactorBlock,1, 0), 'C', new ItemStack(NCItems.parts, 1, 18)}));

			//Heavy Duty Workspace
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCBlocks.nuclearWorkspace, 1), new Object[] {"III", " R ", "AMA", 'I', "ingotSteel", 'R', reinforcedAlloy, 'A', enrichedAlloy, 'M', machineCasing}));

			//Copper Coil
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.parts, 4, 12), new Object[] {"III", "AAA", "III", 'I', "ingotCopper", 'A', enrichedAlloy}));
			//Magnesium Coil
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.parts, 4, 17), new Object[] {"III", "AAA", "III", 'I', "ingotMagnesiumDiboride", 'A', reinforcedAlloy}));

		// Other Shapeless Recipes
			//Universal Reactant
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.parts, 3, 4), new Object[] {Items.sugar, "dustLapis", Items.redstone}));

			//Records
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.recordPractice, 1), new Object[] {"record", "ingotBoron"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.recordArea51, 1), new Object[] {"record", "ingotTough"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.recordNeighborhood, 1), new Object[] {"record", "universalReactant"}));

			//Tough Alloy - Ingot + Dust
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 4, 7), new Object[] {new ItemStack(NCItems.parts, 1, 4), "dustCoal", "dustCoal", "ingotLead", "ingotLead", "ingotSilver", "ingotSilver", "ingotIron", "ingotIron"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 4, 22), new Object[] {new ItemStack(NCItems.parts, 1, 4), "dustCoal", "dustCoal", "dustLead", "dustLead", "dustSilver", "dustSilver", "dustIron", "dustIron"}));

			//Empty Fluid Cell
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, 45), new Object[] {"filledNCGasCell"}));
			//Water Fluid Cell
		GameRegistry.addShapelessRecipe(new ItemStack(NCItems.fuel, 1, 34), (new ItemStack (Items.water_bucket, 1)), (new ItemStack (NCItems.fuel, 1, 45)));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.coolerBlock, 1), (new ItemStack (Items.redstone, 1)), (new ItemStack(NCItems.parts, 1, 4)), (new ItemStack (NCBlocks.emptyCoolerBlock, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.waterCoolerBlock, 1), (new ItemStack (NCItems.fuel, 1, 34)), (new ItemStack (NCBlocks.emptyCoolerBlock, 1)));
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.waterCoolerBlock, 1), (new ItemStack (Items.water_bucket, 1)), (new ItemStack (NCBlocks.emptyCoolerBlock, 1)));
		
		GameRegistry.addShapelessRecipe(new ItemStack(NCBlocks.heliumCoolerBlock, 1), (new ItemStack (NCBlocks.emptyCoolerBlock, 1)), (new ItemStack (NCItems.fuel, 1, 75)), (new ItemStack (NCItems.fuel, 1, 75)), (new ItemStack (NCItems.fuel, 1, 75)), (new ItemStack (NCItems.fuel, 1, 75)));

			//Hard Crushed Carbon Dust
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 2, 79), new Object[] {"dustGraphite", "dustDiamond"}));

			//Lithium Manganese - Ingot + Dust
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 2, 80), new Object[] {"ingotLithium", "ingotManganeseDioxide"}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.material, 2, 81), new Object[] {"dustLithium", "dustManganeseDioxide"}));
		
		// Smelting Recipes
			//Copper Ore -> Copper Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 0), new ItemStack(NCItems.material, 1, 0), 0.6F);
			//Tin Ore -> Tin Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 1), new ItemStack(NCItems.material, 1, 1), 0.6F);
			//Lead Ore -> Lead Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 2), new ItemStack(NCItems.material, 1, 2), 0.8F);
			//Silver Ore -> Silver Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 3), new ItemStack(NCItems.material, 1, 3), 0.8F);
			//Uranium Ore -> Uranium Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 4), new ItemStack (NCItems.material, 1, 4), 1.2F);
			//Thorium Ore -> Thorium Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 5), new ItemStack(NCItems.material, 1, 5), 1.2F);
			//Plutonium Ore -> Plutonium Lump
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 6), new ItemStack(NCItems.material, 1, 33), 1.2F);
			//Lithium Ore ->Lithium Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 7), new ItemStack(NCItems.material, 1, 42), 0.8F);
			//Boron Ore -> Boron Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 8), new ItemStack(NCItems.material, 1, 43), 0.8F);
			//Magnesium Ore -> Magnesium Ingot
		GameRegistry.addSmelting(new ItemStack(NCBlocks.blockOre, 1, 9), new ItemStack(NCItems.material, 1, 50), 0.8F);

			//Iron Dust -> Iron Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 8), new ItemStack(Items.iron_ingot), 0.0F);
			//Gold Dust -> Gold Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 9), new ItemStack(Items.gold_ingot), 0.0F);
			//Copper Dust -> Copper Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 15), new ItemStack(NCItems.material, 1, 0), 0.0F);
			//Tin Dust -> Tin Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 16), new ItemStack(NCItems.material, 1, 1), 0.0F);
			//Lead Dust -> Lead Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 17), new ItemStack(NCItems.material, 1, 2), 0.0F);
			//Silver Dust -> Silver Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 18), new ItemStack(NCItems.material, 1, 3), 0.0F);
			//Uranium Dust -> Uranium Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 19), new ItemStack(NCItems.material, 1, 4), 0.0F);
			//Thorium Dust -> Thorium Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 20), new ItemStack(NCItems.material, 1, 5), 0.0F);
			//Bronze Dust -> Bronze Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 21), new ItemStack(NCItems.material, 1, 6), 0.0F);
			//Tough Alloy Dust -> Tough Alloy Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 22), new ItemStack(NCItems.material, 1, 7), 0.0F);
			//Lithium Dust -> Lithium Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 44), new ItemStack(NCItems.material, 1, 42), 0.0F);
			//Boron Dust -> Boron Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 45), new ItemStack(NCItems.material, 1, 43), 0.0F);
			//Magnesium Dust -> Magnesium Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 51), new ItemStack(NCItems.material, 1, 50), 0.0F);
			//Uranium Oxide Dust -> Uranium Oxide Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 54), new ItemStack(NCItems.material, 1, 53), 0.0F);
			//Magnesium Diboride Dust -> Magnesium Diboride Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 72), new ItemStack(NCItems.material, 1, 71), 0.0F);
			//Graphite Dust -> Graphite Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 77), new ItemStack(NCItems.material, 1, 76), 0.0F);
			//Hard Carbon Dust -> Hard Carbon Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 79), new ItemStack(NCItems.material, 1, 78), 0.0F);
			//Lithium Manganese Dust -> Lithium Manganese Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 81), new ItemStack(NCItems.material, 1, 80), 0.0F);
			//Thorium Oxide Dust -> Thorium Oxide Ingot
		GameRegistry.addSmelting(new ItemStack(NCItems.material, 1, 127), new ItemStack(NCItems.material, 1, 126), 0.0F);

		// Gui Handler
		@SuppressWarnings("unused")
		GuiHandler guiHandler = new GuiHandler();
		
		// Proxy
		NCProxy.registerRenderThings();
		NCProxy.registerSounds();
		NCProxy.registerTileEntitySpecialRenderer();
		
		// Packets
		PacketHandler.init();
		
		// Entities
		EntityHandler.registerMonsters(EntityNuclearMonster.class, "NuclearMonster");
		EntityHandler.registerNuke(EntityNukePrimed.class, "NukePrimed");
		EntityHandler.registerEMP(EntityEMPPrimed.class, "EMPPrimed");
		EntityHandler.registerAntimatterBomb(EntityAntimatterBombPrimed.class, "AntimatterBombPrimed");

		// Fuel Handler	
		GameRegistry.registerFuelHandler(new FuelHandler());

		// Ores Ore Dictionary
		OreDictionary.registerOre("oreUranium", new ItemStack(NCBlocks.blockOre, 1, 4));
		OreDictionary.registerOre("oreCopper", new ItemStack(NCBlocks.blockOre, 1, 0));
		OreDictionary.registerOre("oreTin", new ItemStack(NCBlocks.blockOre, 1, 1));
		OreDictionary.registerOre("oreLead", new ItemStack(NCBlocks.blockOre, 1, 2));
		OreDictionary.registerOre("oreSilver", new ItemStack(NCBlocks.blockOre, 1, 3));
		OreDictionary.registerOre("oreThorium", new ItemStack(NCBlocks.blockOre, 1, 5));
		OreDictionary.registerOre("orePlutonium", new ItemStack(NCBlocks.blockOre, 1, 6));
		OreDictionary.registerOre("oreLithium", new ItemStack(NCBlocks.blockOre, 1, 7));
		OreDictionary.registerOre("oreBoron", new ItemStack(NCBlocks.blockOre, 1, 8));
		OreDictionary.registerOre("oreMagnesium", new ItemStack(NCBlocks.blockOre, 1, 9));

		// Items Ore Dictionary
		OreDictionary.registerOre("ingotUranium", new ItemStack(NCItems.material, 1, 4));
		OreDictionary.registerOre("ingotCopper", new ItemStack(NCItems.material, 1, 0));
		OreDictionary.registerOre("ingotTin", new ItemStack(NCItems.material, 1, 1));
		OreDictionary.registerOre("ingotLead", new ItemStack(NCItems.material, 1, 2));
		OreDictionary.registerOre("ingotSilver", new ItemStack(NCItems.material, 1, 3));
		OreDictionary.registerOre("ingotBronze", new ItemStack(NCItems.material, 1, 6));
		OreDictionary.registerOre("ingotThorium", new ItemStack(NCItems.material, 1, 5));
		OreDictionary.registerOre("ingotLithium", new ItemStack(NCItems.material, 1, 42));
		OreDictionary.registerOre("ingotBoron", new ItemStack(NCItems.material, 1, 43));
		OreDictionary.registerOre("ingotTough", new ItemStack(NCItems.material, 1, 7));
		OreDictionary.registerOre("ingotMagnesium", new ItemStack(NCItems.material, 1, 50));
		OreDictionary.registerOre("ingotUraniumOxide", new ItemStack(NCItems.material, 1, 53));
		OreDictionary.registerOre("ingotThoriumOxide", new ItemStack(NCItems.material, 1, 126));
		OreDictionary.registerOre("ingotMagnesiumDiboride", new ItemStack(NCItems.material, 1, 71));
		OreDictionary.registerOre("gemRhodochrosite", new ItemStack(NCItems.material, 1, 73));
		OreDictionary.registerOre("ingotGraphite", new ItemStack(NCItems.material, 1, 76));
		OreDictionary.registerOre("ingotHardCarbon", new ItemStack(NCItems.material, 1, 78));
		OreDictionary.registerOre("ingotLithiumManganeseDioxide", new ItemStack(NCItems.material, 1, 80));

		// Dusts Ore Dictionary
		OreDictionary.registerOre("dustIron", new ItemStack(NCItems.material, 1, 8));
		OreDictionary.registerOre("dustGold", new ItemStack(NCItems.material, 1, 9));
		OreDictionary.registerOre("dustLapis", new ItemStack(NCItems.material, 1, 10));
		OreDictionary.registerOre("dustDiamond", new ItemStack(NCItems.material, 1, 11));
		OreDictionary.registerOre("dustEmerald", new ItemStack(NCItems.material, 1, 12));
		OreDictionary.registerOre("dustQuartz", new ItemStack(NCItems.material, 1, 13));
		OreDictionary.registerOre("dustCoal", new ItemStack(NCItems.material, 1, 14));
		OreDictionary.registerOre("dustCopper", new ItemStack(NCItems.material, 1, 15));
		OreDictionary.registerOre("dustLead", new ItemStack(NCItems.material, 1, 17));
		OreDictionary.registerOre("dustTinyLead", new ItemStack(NCItems.material, 1, 23));
		OreDictionary.registerOre("dustTin", new ItemStack(NCItems.material, 1, 16));
		OreDictionary.registerOre("dustSilver", new ItemStack(NCItems.material, 1, 18));
		OreDictionary.registerOre("dustUranium", new ItemStack(NCItems.material, 1, 19));
		OreDictionary.registerOre("dustThorium", new ItemStack(NCItems.material, 1, 20));
		OreDictionary.registerOre("dustBronze", new ItemStack(NCItems.material, 1, 21));
		OreDictionary.registerOre("dustLithium", new ItemStack(NCItems.material, 1, 44));
		OreDictionary.registerOre("dustBoron", new ItemStack(NCItems.material, 1, 45));
		OreDictionary.registerOre("dustTough", new ItemStack(NCItems.material, 1, 22));
		OreDictionary.registerOre("dustMagnesium", new ItemStack(NCItems.material, 1, 51));
		OreDictionary.registerOre("dustObsidian", new ItemStack(NCItems.material, 1, 52));
		OreDictionary.registerOre("dustUraniumOxide", new ItemStack(NCItems.material, 1, 54));
		OreDictionary.registerOre("dustThoriumOxide", new ItemStack(NCItems.material, 1, 127));
		OreDictionary.registerOre("dustMagnesiumDiboride", new ItemStack(NCItems.material, 1, 72));
		OreDictionary.registerOre("dustManganeseOxide", new ItemStack(NCItems.material, 1, 74));
		OreDictionary.registerOre("dustManganeseDioxide", new ItemStack(NCItems.material, 1, 75));
		OreDictionary.registerOre("dustGraphite", new ItemStack(NCItems.material, 1, 77));
		OreDictionary.registerOre("dustHardCarbon", new ItemStack(NCItems.material, 1, 79));
		OreDictionary.registerOre("dustLithiumManganeseDioxide", new ItemStack(NCItems.material, 1, 81));

		// Blocks Ore Dictionary
		OreDictionary.registerOre("blockUranium", new ItemStack(NCBlocks.blockBlock, 1, 4));
		OreDictionary.registerOre("blockCopper", new ItemStack(NCBlocks.blockBlock, 1, 0));
		OreDictionary.registerOre("blockTin", new ItemStack(NCBlocks.blockBlock, 1, 1));
		OreDictionary.registerOre("blockLead", new ItemStack(NCBlocks.blockBlock, 1, 2));
		OreDictionary.registerOre("blockSilver", new ItemStack(NCBlocks.blockBlock, 1, 3));
		OreDictionary.registerOre("blockBronze", new ItemStack(NCBlocks.blockBlock, 1, 6));
		OreDictionary.registerOre("blockThorium", new ItemStack(NCBlocks.blockBlock, 1, 5));
		OreDictionary.registerOre("blockTough", new ItemStack(NCBlocks.blockBlock, 1, 7));
		OreDictionary.registerOre("blockLithium", new ItemStack(NCBlocks.blockBlock, 1, 8));
		OreDictionary.registerOre("blockBoron", new ItemStack(NCBlocks.blockBlock, 1, 9));
		OreDictionary.registerOre("blockMagnesium", new ItemStack(NCBlocks.blockBlock, 1, 10));
		OreDictionary.registerOre("blockGraphite", new ItemStack(NCBlocks.graphiteBlock));

		// Parts Ore Dictionary
		OreDictionary.registerOre("plateBasic", new ItemStack(NCItems.parts, 1, 0));
		OreDictionary.registerOre("plateIron", new ItemStack(NCItems.parts, 1, 1));
		OreDictionary.registerOre("plateReinforced", new ItemStack(NCItems.parts, 1, 3));
		OreDictionary.registerOre("universalReactant", new ItemStack(NCItems.parts, 1, 4));
		OreDictionary.registerOre("plateTin", new ItemStack(NCItems.parts, 1, 6));
		OreDictionary.registerOre("plateDU", new ItemStack(NCItems.parts, 1, 8));
		OreDictionary.registerOre("plateAdvanced", new ItemStack(NCItems.parts, 1, 9));
		OreDictionary.registerOre("plateLead", new ItemStack(NCItems.parts, 1, 14));

		// Fission Fuel Materials Ore Dictionary
		OreDictionary.registerOre("U238", new ItemStack(NCItems.material, 1, 24));
		OreDictionary.registerOre("U238Base", new ItemStack(NCItems.material, 1, 24));
		OreDictionary.registerOre("U238", new ItemStack(NCItems.material, 1, 55));
		OreDictionary.registerOre("U238Oxide", new ItemStack(NCItems.material, 1, 55));
		OreDictionary.registerOre("tinyU238", new ItemStack(NCItems.material, 1, 25));
		OreDictionary.registerOre("tinyU238Base", new ItemStack(NCItems.material, 1, 25));
		OreDictionary.registerOre("tinyU238", new ItemStack(NCItems.material, 1, 56));
		OreDictionary.registerOre("tinyU238Oxide", new ItemStack(NCItems.material, 1, 56));

		OreDictionary.registerOre("U235", new ItemStack(NCItems.material, 1, 26));
		OreDictionary.registerOre("U235Base", new ItemStack(NCItems.material, 1, 26));
		OreDictionary.registerOre("U235", new ItemStack(NCItems.material, 1, 57));
		OreDictionary.registerOre("U235Oxide", new ItemStack(NCItems.material, 1, 57));
		OreDictionary.registerOre("tinyU235", new ItemStack(NCItems.material, 1, 27));
		OreDictionary.registerOre("tinyU235Base", new ItemStack(NCItems.material, 1, 27));
		OreDictionary.registerOre("tinyU235", new ItemStack(NCItems.material, 1, 58));
		OreDictionary.registerOre("tinyU235Oxide", new ItemStack(NCItems.material, 1, 58));

		OreDictionary.registerOre("U233", new ItemStack(NCItems.material, 1, 28));
		OreDictionary.registerOre("U233Base", new ItemStack(NCItems.material, 1, 28));
		OreDictionary.registerOre("U233", new ItemStack(NCItems.material, 1, 59));
		OreDictionary.registerOre("U233Oxide", new ItemStack(NCItems.material, 1, 59));
		OreDictionary.registerOre("tinyU233", new ItemStack(NCItems.material, 1, 29));
		OreDictionary.registerOre("tinyU233Base", new ItemStack(NCItems.material, 1, 29));
		OreDictionary.registerOre("tinyU233", new ItemStack(NCItems.material, 1, 60));
		OreDictionary.registerOre("tinyU233Oxide", new ItemStack(NCItems.material, 1, 60));

		OreDictionary.registerOre("Pu238", new ItemStack(NCItems.material, 1, 30));
		OreDictionary.registerOre("Pu238Base", new ItemStack(NCItems.material, 1, 30));
		OreDictionary.registerOre("Pu238", new ItemStack(NCItems.material, 1, 61));
		OreDictionary.registerOre("Pu238Oxide", new ItemStack(NCItems.material, 1, 61));
		OreDictionary.registerOre("tinyPu238", new ItemStack(NCItems.material, 1, 31));
		OreDictionary.registerOre("tinyPu238Base", new ItemStack(NCItems.material, 1, 31));
		OreDictionary.registerOre("tinyPu238", new ItemStack(NCItems.material, 1, 62));
		OreDictionary.registerOre("tinyPu238Oxide", new ItemStack(NCItems.material, 1, 62));

		OreDictionary.registerOre("Pu239", new ItemStack(NCItems.material, 1, 32));
		OreDictionary.registerOre("Pu239Base", new ItemStack(NCItems.material, 1, 32));
		OreDictionary.registerOre("Pu239", new ItemStack(NCItems.material, 1, 63));
		OreDictionary.registerOre("Pu239Oxide", new ItemStack(NCItems.material, 1, 63));
		OreDictionary.registerOre("tinyPu239", new ItemStack(NCItems.material, 1, 33));
		OreDictionary.registerOre("tinyPu239Base", new ItemStack(NCItems.material, 1, 33));
		OreDictionary.registerOre("tinyPu239", new ItemStack(NCItems.material, 1, 64));
		OreDictionary.registerOre("tinyPu239Oxide", new ItemStack(NCItems.material, 1, 64));

		OreDictionary.registerOre("Pu242", new ItemStack(NCItems.material, 1, 34));
		OreDictionary.registerOre("Pu242Base", new ItemStack(NCItems.material, 1, 34));
		OreDictionary.registerOre("Pu242", new ItemStack(NCItems.material, 1, 65));
		OreDictionary.registerOre("Pu242Oxide", new ItemStack(NCItems.material, 1, 65));
		OreDictionary.registerOre("tinyPu242", new ItemStack(NCItems.material, 1, 35));
		OreDictionary.registerOre("tinyPu242Base", new ItemStack(NCItems.material, 1, 35));
		OreDictionary.registerOre("tinyPu242", new ItemStack(NCItems.material, 1, 66));
		OreDictionary.registerOre("tinyPu242Oxide", new ItemStack(NCItems.material, 1, 66));

		OreDictionary.registerOre("Pu241", new ItemStack(NCItems.material, 1, 36));
		OreDictionary.registerOre("Pu241Base", new ItemStack(NCItems.material, 1, 36));
		OreDictionary.registerOre("Pu241", new ItemStack(NCItems.material, 1, 67));
		OreDictionary.registerOre("Pu241Oxide", new ItemStack(NCItems.material, 1, 67));
		OreDictionary.registerOre("tinyPu241", new ItemStack(NCItems.material, 1, 37));
		OreDictionary.registerOre("tinyPu241Base", new ItemStack(NCItems.material, 1, 37));
		OreDictionary.registerOre("tinyPu241", new ItemStack(NCItems.material, 1, 68));
		OreDictionary.registerOre("tinyPu241Oxide", new ItemStack(NCItems.material, 1, 68));

		OreDictionary.registerOre("Th232", new ItemStack(NCItems.material, 1, 38));
		OreDictionary.registerOre("Th232Base", new ItemStack(NCItems.material, 1, 38));
		OreDictionary.registerOre("Th232", new ItemStack(NCItems.material, 1, 82));
		OreDictionary.registerOre("Th232Oxide", new ItemStack(NCItems.material, 1, 82));
		OreDictionary.registerOre("tinyTh232", new ItemStack(NCItems.material, 1, 39));
		OreDictionary.registerOre("tinyTh232Base", new ItemStack(NCItems.material, 1, 39));
		OreDictionary.registerOre("tinyTh232", new ItemStack(NCItems.material, 1, 83));
		OreDictionary.registerOre("tinyTh232Oxide", new ItemStack(NCItems.material, 1, 83));

		OreDictionary.registerOre("Th230", new ItemStack(NCItems.material, 1, 40));
		OreDictionary.registerOre("Th230Base", new ItemStack(NCItems.material, 1, 40));
		OreDictionary.registerOre("Th230", new ItemStack(NCItems.material, 1, 84));
		OreDictionary.registerOre("Th230Oxide", new ItemStack(NCItems.material, 1, 84));
		OreDictionary.registerOre("tinyTh230", new ItemStack(NCItems.material, 1, 41));
		OreDictionary.registerOre("tinyTh230Base", new ItemStack(NCItems.material, 1, 41));
		OreDictionary.registerOre("tinyTh230", new ItemStack(NCItems.material, 1, 85));
		OreDictionary.registerOre("tinyTh230Oxide", new ItemStack(NCItems.material, 1, 85));

		OreDictionary.registerOre("Np236", new ItemStack(NCItems.material, 1, 86));
		OreDictionary.registerOre("Np236Base", new ItemStack(NCItems.material, 1, 86));
		OreDictionary.registerOre("Np236", new ItemStack(NCItems.material, 1, 104));
		OreDictionary.registerOre("Np236Oxide", new ItemStack(NCItems.material, 1, 104));
		OreDictionary.registerOre("tinyNp236", new ItemStack(NCItems.material, 1, 87));
		OreDictionary.registerOre("tinyNp236Base", new ItemStack(NCItems.material, 1, 87));
		OreDictionary.registerOre("tinyNp236", new ItemStack(NCItems.material, 1, 105));
		OreDictionary.registerOre("tinyNp236Oxide", new ItemStack(NCItems.material, 1, 105));

		OreDictionary.registerOre("Np237", new ItemStack(NCItems.material, 1, 88));
		OreDictionary.registerOre("Np237Base", new ItemStack(NCItems.material, 1, 88));
		OreDictionary.registerOre("Np237", new ItemStack(NCItems.material, 1, 106));
		OreDictionary.registerOre("Np237Oxide", new ItemStack(NCItems.material, 1, 106));
		OreDictionary.registerOre("tinyNp237", new ItemStack(NCItems.material, 1, 89));
		OreDictionary.registerOre("tinyNp237Base", new ItemStack(NCItems.material, 1, 89));
		OreDictionary.registerOre("tinyNp237", new ItemStack(NCItems.material, 1, 107));
		OreDictionary.registerOre("tinyNp237Oxide", new ItemStack(NCItems.material, 1, 107));

		OreDictionary.registerOre("Am241", new ItemStack(NCItems.material, 1, 90));
		OreDictionary.registerOre("Am241Base", new ItemStack(NCItems.material, 1, 90));
		OreDictionary.registerOre("Am241", new ItemStack(NCItems.material, 1, 108));
		OreDictionary.registerOre("Am241Oxide", new ItemStack(NCItems.material, 1, 108));
		OreDictionary.registerOre("tinyAm241", new ItemStack(NCItems.material, 1, 91));
		OreDictionary.registerOre("tinyAm241Base", new ItemStack(NCItems.material, 1, 91));
		OreDictionary.registerOre("tinyAm241", new ItemStack(NCItems.material, 1, 109));
		OreDictionary.registerOre("tinyAm241Oxide", new ItemStack(NCItems.material, 1, 109));

		OreDictionary.registerOre("Am242", new ItemStack(NCItems.material, 1, 92));
		OreDictionary.registerOre("Am242Base", new ItemStack(NCItems.material, 1, 92));
		OreDictionary.registerOre("Am242", new ItemStack(NCItems.material, 1, 110));
		OreDictionary.registerOre("Am242Oxide", new ItemStack(NCItems.material, 1, 110));
		OreDictionary.registerOre("tinyAm242", new ItemStack(NCItems.material, 1, 93));
		OreDictionary.registerOre("tinyAm242Base", new ItemStack(NCItems.material, 1, 93));
		OreDictionary.registerOre("tinyAm242", new ItemStack(NCItems.material, 1, 111));
		OreDictionary.registerOre("tinyAm242Oxide", new ItemStack(NCItems.material, 1, 111));

		OreDictionary.registerOre("Am243", new ItemStack(NCItems.material, 1, 94));
		OreDictionary.registerOre("Am243Base", new ItemStack(NCItems.material, 1, 94));
		OreDictionary.registerOre("Am243", new ItemStack(NCItems.material, 1, 112));
		OreDictionary.registerOre("Am243Oxide", new ItemStack(NCItems.material, 1, 112));
		OreDictionary.registerOre("tinyAm243", new ItemStack(NCItems.material, 1, 95));
		OreDictionary.registerOre("tinyAm243Base", new ItemStack(NCItems.material, 1, 95));
		OreDictionary.registerOre("tinyAm243", new ItemStack(NCItems.material, 1, 113));
		OreDictionary.registerOre("tinyAm243Oxide", new ItemStack(NCItems.material, 1, 113));

		OreDictionary.registerOre("Cm243", new ItemStack(NCItems.material, 1, 96));
		OreDictionary.registerOre("Cm243Base", new ItemStack(NCItems.material, 1, 96));
		OreDictionary.registerOre("Cm243", new ItemStack(NCItems.material, 1, 114));
		OreDictionary.registerOre("Cm243Oxide", new ItemStack(NCItems.material, 1, 114));
		OreDictionary.registerOre("tinyCm243", new ItemStack(NCItems.material, 1, 97));
		OreDictionary.registerOre("tinyCm243Base", new ItemStack(NCItems.material, 1, 97));
		OreDictionary.registerOre("tinyCm243", new ItemStack(NCItems.material, 1, 115));
		OreDictionary.registerOre("tinyCm243Oxide", new ItemStack(NCItems.material, 1, 115));

		OreDictionary.registerOre("Cm245", new ItemStack(NCItems.material, 1, 98));
		OreDictionary.registerOre("Cm245Base", new ItemStack(NCItems.material, 1, 98));
		OreDictionary.registerOre("Cm245", new ItemStack(NCItems.material, 1, 116));
		OreDictionary.registerOre("Cm245Oxide", new ItemStack(NCItems.material, 1, 116));
		OreDictionary.registerOre("tinyCm245", new ItemStack(NCItems.material, 1, 99));
		OreDictionary.registerOre("tinyCm245Base", new ItemStack(NCItems.material, 1, 99));
		OreDictionary.registerOre("tinyCm245", new ItemStack(NCItems.material, 1, 117));
		OreDictionary.registerOre("tinyCm245Oxide", new ItemStack(NCItems.material, 1, 117));

		OreDictionary.registerOre("Cm246", new ItemStack(NCItems.material, 1, 100));
		OreDictionary.registerOre("Cm246Base", new ItemStack(NCItems.material, 1, 100));
		OreDictionary.registerOre("Cm246", new ItemStack(NCItems.material, 1, 118));
		OreDictionary.registerOre("Cm246Oxide", new ItemStack(NCItems.material, 1, 118));
		OreDictionary.registerOre("tinyCm246", new ItemStack(NCItems.material, 1, 101));
		OreDictionary.registerOre("tinyCm246Base", new ItemStack(NCItems.material, 1, 101));
		OreDictionary.registerOre("tinyCm246", new ItemStack(NCItems.material, 1, 119));
		OreDictionary.registerOre("tinyCm246Oxide", new ItemStack(NCItems.material, 1, 119));

		OreDictionary.registerOre("Cm247", new ItemStack(NCItems.material, 1, 102));
		OreDictionary.registerOre("Cm247Base", new ItemStack(NCItems.material, 1, 102));
		OreDictionary.registerOre("Cm247", new ItemStack(NCItems.material, 1, 120));
		OreDictionary.registerOre("Cm247Oxide", new ItemStack(NCItems.material, 1, 120));
		OreDictionary.registerOre("tinyCm247", new ItemStack(NCItems.material, 1, 103));
		OreDictionary.registerOre("tinyCm247Base", new ItemStack(NCItems.material, 1, 103));
		OreDictionary.registerOre("tinyCm247", new ItemStack(NCItems.material, 1, 121));
		OreDictionary.registerOre("tinyCm247Oxide", new ItemStack(NCItems.material, 1, 121));

		OreDictionary.registerOre("Cf250", new ItemStack(NCItems.material, 1, 122));
		OreDictionary.registerOre("Cf250Base", new ItemStack(NCItems.material, 1, 122));
		OreDictionary.registerOre("Cf250", new ItemStack(NCItems.material, 1, 124));
		OreDictionary.registerOre("Cf250Oxide", new ItemStack(NCItems.material, 1, 124));
		OreDictionary.registerOre("tinyCf250", new ItemStack(NCItems.material, 1, 123));
		OreDictionary.registerOre("tinyCf250Base", new ItemStack(NCItems.material, 1, 123));
		OreDictionary.registerOre("tinyCf250", new ItemStack(NCItems.material, 1, 125));
		OreDictionary.registerOre("tinyCf250Oxide", new ItemStack(NCItems.material, 1, 125));

		// Lithium and Boron Isotopes
		OreDictionary.registerOre("Li6", new ItemStack(NCItems.material, 1, 46));
		OreDictionary.registerOre("tinyLi6", new ItemStack(NCItems.material, 1, 69));
		OreDictionary.registerOre("Li7", new ItemStack(NCItems.material, 1, 47));
		OreDictionary.registerOre("B10", new ItemStack(NCItems.material, 1, 48));
		OreDictionary.registerOre("tinyB10", new ItemStack(NCItems.material, 1, 70));
		OreDictionary.registerOre("B11", new ItemStack(NCItems.material, 1, 49));

		// Fission Fuels Ore Dictionary
		OreDictionary.registerOre("LEU235", new ItemStack(NCItems.fuel, 1, 0));
		OreDictionary.registerOre("LEU235Oxide", new ItemStack(NCItems.fuel, 1, 51));
		OreDictionary.registerOre("LEU235Cell", new ItemStack(NCItems.fuel, 1, 11));
		OreDictionary.registerOre("LEU235CellOxide", new ItemStack(NCItems.fuel, 1, 59));
		OreDictionary.registerOre("dLEU235Cell", new ItemStack(NCItems.fuel, 1, 22));
		OreDictionary.registerOre("dLEU235CellOxide", new ItemStack(NCItems.fuel, 1, 67));

		OreDictionary.registerOre("HEU235", new ItemStack(NCItems.fuel, 1, 1));
		OreDictionary.registerOre("HEU235Oxide", new ItemStack(NCItems.fuel, 1, 52));
		OreDictionary.registerOre("HEU235Cell", new ItemStack(NCItems.fuel, 1, 12));
		OreDictionary.registerOre("HEU235CellOxide", new ItemStack(NCItems.fuel, 1, 60));
		OreDictionary.registerOre("dHEU235Cell", new ItemStack(NCItems.fuel, 1, 23));
		OreDictionary.registerOre("dHEU235CellOxide", new ItemStack(NCItems.fuel, 1, 68));

		OreDictionary.registerOre("LEP239", new ItemStack(NCItems.fuel, 1, 2));
		OreDictionary.registerOre("LEP239Oxide", new ItemStack(NCItems.fuel, 1, 53));
		OreDictionary.registerOre("LEP239Cell", new ItemStack(NCItems.fuel, 1, 13));
		OreDictionary.registerOre("LEP239CellOxide", new ItemStack(NCItems.fuel, 1, 61));
		OreDictionary.registerOre("dLEP239Cell", new ItemStack(NCItems.fuel, 1, 24));
		OreDictionary.registerOre("dLEP239CellOxide", new ItemStack(NCItems.fuel, 1, 69));

		OreDictionary.registerOre("HEP239", new ItemStack(NCItems.fuel, 1, 3));
		OreDictionary.registerOre("HEP239Oxide", new ItemStack(NCItems.fuel, 1, 54));
		OreDictionary.registerOre("HEP239Cell", new ItemStack(NCItems.fuel, 1, 14));
		OreDictionary.registerOre("HEP239CellOxide", new ItemStack(NCItems.fuel, 1, 62));
		OreDictionary.registerOre("dHEP239Cell", new ItemStack(NCItems.fuel, 1, 25));
		OreDictionary.registerOre("dHEP239CellOxide", new ItemStack(NCItems.fuel, 1, 70));

		OreDictionary.registerOre("MOX239", new ItemStack(NCItems.fuel, 1, 4));
		OreDictionary.registerOre("MOX239Cell", new ItemStack(NCItems.fuel, 1, 15));
		OreDictionary.registerOre("dMOX239Cell", new ItemStack(NCItems.fuel, 1, 26));

		OreDictionary.registerOre("TBU", new ItemStack(NCItems.fuel, 1, 5));
		OreDictionary.registerOre("TBUOxide", new ItemStack(NCItems.fuel, 1, 76));
		OreDictionary.registerOre("TBUCell", new ItemStack(NCItems.fuel, 1, 16));
		OreDictionary.registerOre("TBUCellOxide", new ItemStack(NCItems.fuel, 1, 77));
		OreDictionary.registerOre("dTBUCell", new ItemStack(NCItems.fuel, 1, 27));
		OreDictionary.registerOre("dTBUCellOxide", new ItemStack(NCItems.fuel, 1, 78));

		OreDictionary.registerOre("LEU233", new ItemStack(NCItems.fuel, 1, 6));
		OreDictionary.registerOre("LEU233Oxide", new ItemStack(NCItems.fuel, 1, 55));
		OreDictionary.registerOre("LEU233Cell", new ItemStack(NCItems.fuel, 1, 17));
		OreDictionary.registerOre("LEU233CellOxide", new ItemStack(NCItems.fuel, 1, 63));
		OreDictionary.registerOre("dLEU233Cell", new ItemStack(NCItems.fuel, 1, 28));
		OreDictionary.registerOre("dLEU233CellOxide", new ItemStack(NCItems.fuel, 1, 71));

		OreDictionary.registerOre("HEU233", new ItemStack(NCItems.fuel, 1, 7));
		OreDictionary.registerOre("HEU233Oxide", new ItemStack(NCItems.fuel, 1, 56));
		OreDictionary.registerOre("HEU233Cell", new ItemStack(NCItems.fuel, 1, 18));
		OreDictionary.registerOre("HEU233CellOxide", new ItemStack(NCItems.fuel, 1, 64));
		OreDictionary.registerOre("dHEU233Cell", new ItemStack(NCItems.fuel, 1, 29));
		OreDictionary.registerOre("dHEU233CellOxide", new ItemStack(NCItems.fuel, 1, 72));

		OreDictionary.registerOre("LEP241", new ItemStack(NCItems.fuel, 1, 8));
		OreDictionary.registerOre("LEP241Oxide", new ItemStack(NCItems.fuel, 1, 57));
		OreDictionary.registerOre("LEP241Cell", new ItemStack(NCItems.fuel, 1, 19));
		OreDictionary.registerOre("LEP241CellOxide", new ItemStack(NCItems.fuel, 1, 65));
		OreDictionary.registerOre("dLEP241Cell", new ItemStack(NCItems.fuel, 1, 30));
		OreDictionary.registerOre("dLEP241CellOxide", new ItemStack(NCItems.fuel, 1, 73));

		OreDictionary.registerOre("HEP241", new ItemStack(NCItems.fuel, 1, 9));
		OreDictionary.registerOre("HEP241Oxide", new ItemStack(NCItems.fuel, 1, 58));
		OreDictionary.registerOre("HEP241Cell", new ItemStack(NCItems.fuel, 1, 20));
		OreDictionary.registerOre("HEP241CellOxide", new ItemStack(NCItems.fuel, 1, 66));
		OreDictionary.registerOre("dHEP241Cell", new ItemStack(NCItems.fuel, 1, 31));
		OreDictionary.registerOre("dHEP241CellOxide", new ItemStack(NCItems.fuel, 1, 74));

		OreDictionary.registerOre("MOX241", new ItemStack(NCItems.fuel, 1, 10));
		OreDictionary.registerOre("MOX241Cell", new ItemStack(NCItems.fuel, 1, 21));
		OreDictionary.registerOre("dMOX241Cell", new ItemStack(NCItems.fuel, 1, 32));

		OreDictionary.registerOre("LEN236", new ItemStack(NCItems.fuel, 1, 79));
		OreDictionary.registerOre("LEN236Oxide", new ItemStack(NCItems.fuel, 1, 89));
		OreDictionary.registerOre("LEN236Cell", new ItemStack(NCItems.fuel, 1, 99));
		OreDictionary.registerOre("LEN236CellOxide", new ItemStack(NCItems.fuel, 1, 109));
		OreDictionary.registerOre("dLEN236Cell", new ItemStack(NCItems.fuel, 1, 119));
		OreDictionary.registerOre("dLEN236CellOxide", new ItemStack(NCItems.fuel, 1, 129));

		OreDictionary.registerOre("HEN236", new ItemStack(NCItems.fuel, 1, 80));
		OreDictionary.registerOre("HEN236Oxide", new ItemStack(NCItems.fuel, 1, 90));
		OreDictionary.registerOre("HEN236Cell", new ItemStack(NCItems.fuel, 1, 100));
		OreDictionary.registerOre("HEN236CellOxide", new ItemStack(NCItems.fuel, 1, 110));
		OreDictionary.registerOre("dHEN236Cell", new ItemStack(NCItems.fuel, 1, 120));
		OreDictionary.registerOre("dHEN236CellOxide", new ItemStack(NCItems.fuel, 1, 130));

		OreDictionary.registerOre("LEA242", new ItemStack(NCItems.fuel, 1, 81));
		OreDictionary.registerOre("LEA242Oxide", new ItemStack(NCItems.fuel, 1, 91));
		OreDictionary.registerOre("LEA242Cell", new ItemStack(NCItems.fuel, 1, 101));
		OreDictionary.registerOre("LEA242CellOxide", new ItemStack(NCItems.fuel, 1, 111));
		OreDictionary.registerOre("dLEA242Cell", new ItemStack(NCItems.fuel, 1, 121));
		OreDictionary.registerOre("dLEA242CellOxide", new ItemStack(NCItems.fuel, 1, 131));

		OreDictionary.registerOre("HEA242", new ItemStack(NCItems.fuel, 1, 82));
		OreDictionary.registerOre("HEA242Oxide", new ItemStack(NCItems.fuel, 1, 92));
		OreDictionary.registerOre("HEA242Cell", new ItemStack(NCItems.fuel, 1, 102));
		OreDictionary.registerOre("HEA242CellOxide", new ItemStack(NCItems.fuel, 1, 112));
		OreDictionary.registerOre("dHEA242Cell", new ItemStack(NCItems.fuel, 1, 122));
		OreDictionary.registerOre("dHEA242CellOxide", new ItemStack(NCItems.fuel, 1, 132));

		OreDictionary.registerOre("LEC243", new ItemStack(NCItems.fuel, 1, 83));
		OreDictionary.registerOre("LEC243Oxide", new ItemStack(NCItems.fuel, 1, 93));
		OreDictionary.registerOre("LEC243Cell", new ItemStack(NCItems.fuel, 1, 103));
		OreDictionary.registerOre("LEC243CellOxide", new ItemStack(NCItems.fuel, 1, 113));
		OreDictionary.registerOre("dLEC243Cell", new ItemStack(NCItems.fuel, 1, 123));
		OreDictionary.registerOre("dLEC243CellOxide", new ItemStack(NCItems.fuel, 1, 133));

		OreDictionary.registerOre("HEC243", new ItemStack(NCItems.fuel, 1, 84));
		OreDictionary.registerOre("HEC243Oxide", new ItemStack(NCItems.fuel, 1, 94));
		OreDictionary.registerOre("HEC243Cell", new ItemStack(NCItems.fuel, 1, 104));
		OreDictionary.registerOre("HEC243CellOxide", new ItemStack(NCItems.fuel, 1, 114));
		OreDictionary.registerOre("dHEC243Cell", new ItemStack(NCItems.fuel, 1, 124));
		OreDictionary.registerOre("dHEC243CellOxide", new ItemStack(NCItems.fuel, 1, 134));

		OreDictionary.registerOre("LEC245", new ItemStack(NCItems.fuel, 1, 85));
		OreDictionary.registerOre("LEC245Oxide", new ItemStack(NCItems.fuel, 1, 95));
		OreDictionary.registerOre("LEC245Cell", new ItemStack(NCItems.fuel, 1, 105));
		OreDictionary.registerOre("LEC245CellOxide", new ItemStack(NCItems.fuel, 1, 115));
		OreDictionary.registerOre("dLEC245Cell", new ItemStack(NCItems.fuel, 1, 125));
		OreDictionary.registerOre("dLEC245CellOxide", new ItemStack(NCItems.fuel, 1, 135));

		OreDictionary.registerOre("HEC245", new ItemStack(NCItems.fuel, 1, 86));
		OreDictionary.registerOre("HEC245Oxide", new ItemStack(NCItems.fuel, 1, 96));
		OreDictionary.registerOre("HEC245Cell", new ItemStack(NCItems.fuel, 1, 106));
		OreDictionary.registerOre("HEC245CellOxide", new ItemStack(NCItems.fuel, 1, 116));
		OreDictionary.registerOre("dHEC245Cell", new ItemStack(NCItems.fuel, 1, 126));
		OreDictionary.registerOre("dHEC245CellOxide", new ItemStack(NCItems.fuel, 1, 136));

		OreDictionary.registerOre("LEC247", new ItemStack(NCItems.fuel, 1, 87));
		OreDictionary.registerOre("LEC247Oxide", new ItemStack(NCItems.fuel, 1, 97));
		OreDictionary.registerOre("LEC247Cell", new ItemStack(NCItems.fuel, 1, 107));
		OreDictionary.registerOre("LEC247CellOxide", new ItemStack(NCItems.fuel, 1, 117));
		OreDictionary.registerOre("dLEC247Cell", new ItemStack(NCItems.fuel, 1, 127));
		OreDictionary.registerOre("dLEC247CellOxide", new ItemStack(NCItems.fuel, 1, 137));

		OreDictionary.registerOre("HEC247", new ItemStack(NCItems.fuel, 1, 88));
		OreDictionary.registerOre("HEC247Oxide", new ItemStack(NCItems.fuel, 1, 98));
		OreDictionary.registerOre("HEC247Cell", new ItemStack(NCItems.fuel, 1, 108));
		OreDictionary.registerOre("HEC247CellOxide", new ItemStack(NCItems.fuel, 1, 118));
		OreDictionary.registerOre("dHEC247Cell", new ItemStack(NCItems.fuel, 1, 128));
		OreDictionary.registerOre("dHEC247CellOxide", new ItemStack(NCItems.fuel, 1, 138));

		// Vanilla Ore Dictionary
		OreDictionary.registerOre("gemCoal", new ItemStack(Items.coal, 1));
		OreDictionary.registerOre("oreObsidian", new ItemStack(Blocks.obsidian, 1));

		// Filled Fluid Cell Dictionary
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 34));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 35));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 36));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 37));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 38));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 39));
		OreDictionary.registerOre("filledNCGasCell", new ItemStack(NCItems.fuel, 1, 40));
		OreDictionary.registerOre("deuteriumCell", new ItemStack(NCItems.fuel, 1, 37));

		// Record Ore Dictionary
		OreDictionary.registerOre("record", new ItemStack(NCItems.recordPractice, 1));
		OreDictionary.registerOre("record", new ItemStack(NCItems.recordArea51, 1));
		OreDictionary.registerOre("record", new ItemStack(NCItems.recordNeighborhood, 1));

		//Railcraft Integration
		ItemStack railcraftConcrete = GameRegistry.findItemStack("Railcraft", "cube", 1);

		if (railcraftConcrete != null) {
			railcraftConcrete.setItemDamage(1);
			OreDictionary.registerOre("blockConcrete", railcraftConcrete);
		}

		//Gas Registry
		GasRegistry.register(new Gas("uranium238")).registerFluid();
		GasRegistry.register(new Gas("boron10")).registerFluid();

		//Register Gasifyables Items
		GasifyableItems.registerGasifyables("deuteriumCell", GasRegistry.getGas("deuterium"), 1000);
		GasifyableItems.registerGasifyables("U238", GasRegistry.getGas("uranium238"), 200);
		GasifyableItems.registerGasifyables("B10", GasRegistry.getGas("boron10"), 200);


		// Random Chest Loot
		if (enableLoot) {
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordPractice, 1), 1, 1, 2*lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordArea51, 1), 1, 1, 2*lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.DUNGEON_CHEST).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordNeighborhood, 1), 1, 1, 2*lootModifier));

			ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordPractice, 1), 1, 1, lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordArea51, 1), 1, 1, lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.MINESHAFT_CORRIDOR).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordNeighborhood, 1), 1, 1, lootModifier));

			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordPractice, 1), 1, 1, 2*lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordArea51, 1), 1, 1, 2*lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_CROSSING).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordNeighborhood, 1), 1, 1, 2*lootModifier));

			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordPractice, 1), 1, 1, lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordArea51, 1), 1, 1, lootModifier));
			ChestGenHooks.getInfo(ChestGenHooks.STRONGHOLD_LIBRARY).addItem(new WeightedRandomChestContent(new ItemStack(NCItems.recordNeighborhood, 1), 1, 1, lootModifier));
		}
		
		// World Generation Registry
		GameRegistry.registerWorldGenerator(new OreGen(), 1);

		// Inter Mod Comms - Mekanism
			//Oxide Processing
		addOxideRecipes();

			//Advanced Plating
		NBTTagCompound deuteriumFilling = new NBTTagCompound();
		deuteriumFilling.setTag("input", new ItemStack(NCItems.parts, 1, 0).writeToNBT(new NBTTagCompound()));
		deuteriumFilling.setTag("gasType", GasRegistry.getGas("deuterium").write(new NBTTagCompound()));
		deuteriumFilling.setTag("output", new ItemStack(NCItems.parts, 1, 3).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", deuteriumFilling);

			//Uranium Plating
		NBTTagCompound uraniumFilling = new NBTTagCompound();
		uraniumFilling.setTag("input", new ItemStack(NCItems.parts, 1, 0).writeToNBT(new NBTTagCompound()));
		uraniumFilling.setTag("gasType", GasRegistry.getGas("uranium238").write(new NBTTagCompound()));
		uraniumFilling.setTag("output", new ItemStack(NCItems.parts, 1, 8).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", uraniumFilling);

			//Boron Plating
		NBTTagCompound boronFilling = new NBTTagCompound();
		boronFilling.setTag("input", new ItemStack(NCItems.parts, 1, 0).writeToNBT(new NBTTagCompound()));
		boronFilling.setTag("gasType", GasRegistry.getGas("boron10").write(new NBTTagCompound()));
		boronFilling.setTag("output", new ItemStack(NCItems.parts, 1, 9).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", boronFilling);

		NBTTagCompound copperOreEnrichment = new NBTTagCompound();
		copperOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 0).writeToNBT(new NBTTagCompound()));
		copperOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 15).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", copperOreEnrichment);
		
		NBTTagCompound tinOreEnrichment = new NBTTagCompound();
		tinOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 1).writeToNBT(new NBTTagCompound()));
		tinOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 16).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", tinOreEnrichment);
		
		NBTTagCompound leadOreEnrichment = new NBTTagCompound();
		leadOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 2).writeToNBT(new NBTTagCompound()));
		leadOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 17).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", leadOreEnrichment);
		
		NBTTagCompound silverOreEnrichment = new NBTTagCompound();
		silverOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 3).writeToNBT(new NBTTagCompound()));
		silverOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 18).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", silverOreEnrichment);
		
		NBTTagCompound uraniumOreEnrichment = new NBTTagCompound();
		uraniumOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 4).writeToNBT(new NBTTagCompound()));
		uraniumOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 19).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", uraniumOreEnrichment);
		
		NBTTagCompound thoriumOreEnrichment = new NBTTagCompound();
		thoriumOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 5).writeToNBT(new NBTTagCompound()));
		thoriumOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 20).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", thoriumOreEnrichment);
		
		NBTTagCompound plutoniumOreEnrichment = new NBTTagCompound();
		plutoniumOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 6).writeToNBT(new NBTTagCompound()));
		plutoniumOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 33).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", plutoniumOreEnrichment);
		
		NBTTagCompound lithiumOreEnrichment = new NBTTagCompound();
		lithiumOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 7).writeToNBT(new NBTTagCompound()));
		lithiumOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 44).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", lithiumOreEnrichment);
		
		NBTTagCompound boronOreEnrichment = new NBTTagCompound();
		boronOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 8).writeToNBT(new NBTTagCompound()));
		boronOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 45).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", boronOreEnrichment);
		
		NBTTagCompound magnesiumOreEnrichment = new NBTTagCompound();
		magnesiumOreEnrichment.setTag("input", new ItemStack(NCBlocks.blockOre, 1, 9).writeToNBT(new NBTTagCompound()));
		magnesiumOreEnrichment.setTag("output", new ItemStack(NCItems.material, 2, 51).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "EnrichmentChamberRecipe", magnesiumOreEnrichment);

		NBTTagCompound uraniumIngotCrushing = new NBTTagCompound();
		uraniumIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 4).writeToNBT(new NBTTagCompound()));
		uraniumIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 19).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", uraniumIngotCrushing);
		
		NBTTagCompound thoriumIngotCrushing = new NBTTagCompound();
		thoriumIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 5).writeToNBT(new NBTTagCompound()));
		thoriumIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 20).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", thoriumIngotCrushing);
		
		NBTTagCompound uraniumIngotOxideCrushing = new NBTTagCompound();
		uraniumIngotOxideCrushing.setTag("input", new ItemStack(NCItems.material, 1, 53).writeToNBT(new NBTTagCompound()));
		uraniumIngotOxideCrushing.setTag("output", new ItemStack(NCItems.material, 1, 54).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", uraniumIngotOxideCrushing);
		
		NBTTagCompound thoriumIngotOxideCrushing = new NBTTagCompound();
		thoriumIngotOxideCrushing.setTag("input", new ItemStack(NCItems.material, 1, 126).writeToNBT(new NBTTagCompound()));
		thoriumIngotOxideCrushing.setTag("output", new ItemStack(NCItems.material, 1, 127).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", thoriumIngotOxideCrushing);
		
		NBTTagCompound bronzeIngotCrushing = new NBTTagCompound();
		bronzeIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 6).writeToNBT(new NBTTagCompound()));
		bronzeIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 21).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", bronzeIngotCrushing);
		
		NBTTagCompound toughIngotCrushing = new NBTTagCompound();
		toughIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 7).writeToNBT(new NBTTagCompound()));
		toughIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 22).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", toughIngotCrushing);
		
		NBTTagCompound lithiumIngotCrushing = new NBTTagCompound();
		lithiumIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 42).writeToNBT(new NBTTagCompound()));
		lithiumIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 44).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", lithiumIngotCrushing);
		
		NBTTagCompound boronIngotCrushing = new NBTTagCompound();
		boronIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 43).writeToNBT(new NBTTagCompound()));
		boronIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 45).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", boronIngotCrushing);
		
		NBTTagCompound magnesiumIngotCrushing = new NBTTagCompound();
		magnesiumIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 50).writeToNBT(new NBTTagCompound()));
		magnesiumIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 51).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", magnesiumIngotCrushing);
		
		NBTTagCompound mgbIngotCrushing = new NBTTagCompound();
		mgbIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 71).writeToNBT(new NBTTagCompound()));
		mgbIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 72).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", mgbIngotCrushing);
		
		NBTTagCompound rCrushing = new NBTTagCompound();
		rCrushing.setTag("input", new ItemStack(NCItems.material, 1, 73).writeToNBT(new NBTTagCompound()));
		rCrushing.setTag("output", new ItemStack(NCItems.material, 1, 74).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", rCrushing);
		
		NBTTagCompound graphiteIngotCrushing = new NBTTagCompound();
		graphiteIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 76).writeToNBT(new NBTTagCompound()));
		graphiteIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 77).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", graphiteIngotCrushing);
		
		NBTTagCompound hardCarbonIngotCrushing = new NBTTagCompound();
		hardCarbonIngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 78).writeToNBT(new NBTTagCompound()));
		hardCarbonIngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 79).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", hardCarbonIngotCrushing);
		
		NBTTagCompound LiMnO2IngotCrushing = new NBTTagCompound();
		LiMnO2IngotCrushing.setTag("input", new ItemStack(NCItems.material, 1, 80).writeToNBT(new NBTTagCompound()));
		LiMnO2IngotCrushing.setTag("output", new ItemStack(NCItems.material, 1, 81).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "CrusherRecipe", LiMnO2IngotCrushing);

		for (ItemStack ore : OreDictionary.getOres("dustCoal")) {
			mekanism.api.recipe.RecipeHelper.addMetallurgicInfuserRecipe(InfuseRegistry.get("CARBON"), 20, StackUtils.size(ore, 1), new ItemStack(NCItems.material, 1, 77));
		}

		/*NBTTagCompound oxygenFilling = new NBTTagCompound();
		oxygenFilling.setTag("input", new ItemStack(NCItems.fuel, 1, 45).writeToNBT(new NBTTagCompound()));
		oxygenFilling.setTag("gasType", GasRegistry.getGas("oxygen").write(new NBTTagCompound()));
		oxygenFilling.setTag("output", new ItemStack(NCItems.fuel, 1, 35).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", oxygenFilling);
		
		NBTTagCompound hydrogenFilling = new NBTTagCompound();
		hydrogenFilling.setTag("input", new ItemStack(NCItems.fuel, 1, 45).writeToNBT(new NBTTagCompound()));
		hydrogenFilling.setTag("gasType", GasRegistry.getGas("hydrogen").write(new NBTTagCompound()));
		hydrogenFilling.setTag("output", new ItemStack(NCItems.fuel, 1, 36).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", hydrogenFilling);
		
		NBTTagCompound deuteriumFilling = new NBTTagCompound();
		deuteriumFilling.setTag("input", new ItemStack(NCItems.fuel, 1, 45).writeToNBT(new NBTTagCompound()));
		deuteriumFilling.setTag("gasType", GasRegistry.getGas("deuterium").write(new NBTTagCompound()));
		deuteriumFilling.setTag("output", new ItemStack(NCItems.fuel, 1, 37).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", deuteriumFilling);
		
		NBTTagCompound tritiumFilling = new NBTTagCompound();
		tritiumFilling.setTag("input", new ItemStack(NCItems.fuel, 1, 45).writeToNBT(new NBTTagCompound()));
		tritiumFilling.setTag("gasType", GasRegistry.getGas("tritium").write(new NBTTagCompound()));
		tritiumFilling.setTag("output", new ItemStack(NCItems.fuel, 1, 38).writeToNBT(new NBTTagCompound()));
		FMLInterModComms.sendMessage("Mekanism", "ChemicalInjectionChamberRecipe", tritiumFilling);*/
		
		// Inter Mod Comms - AE2
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileNuclearFurnace.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFurnace.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileReactionGenerator.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSeparator.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileHastener.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileCollector.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFissionReactor.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFissionReactorSteam.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileNuclearWorkspace.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactor.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactorSteam.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileTubing1.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileTubing2.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileRTG.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileAmRTG.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileCfRTG.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileWRTG.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSteamGenerator.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSteamDecompressor.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileDenseSteamDecompressor.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactorBlock.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactorBlockTop.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactorSteamBlock.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileFusionReactorSteamBlockTop.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileIoniser.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileIrradiator.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileCooler.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileHeliumExtractor.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileRecycler.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSolarPanel.class.getCanonicalName());
		
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileElectromagnet.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSuperElectromagnet.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSupercooler.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSynchrotron.class.getCanonicalName());
		
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileLithiumIonBattery.class.getCanonicalName());
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileVoltaicPile.class.getCanonicalName());
		
		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial", TileSimpleQuantum.class.getCanonicalName());
	}
	public static void addOxideFuels(String oredict) {
		ItemStack fuel = OreDictionary.getOres(oredict).get(0);
		ItemStack fuelOxide = StackUtils.size(OreDictionary.getOres(oredict + "Oxide").get(0), 1);

// 		# Faulty #
//		NBTTagCompound oxideEnrichment = new NBTTagCompound();
//		oxideEnrichment.setTag("input", fuel.writeToNBT(new NBTTagCompound()));
//		oxideEnrichment.setTag("output", fuelOxide.writeToNBT(new NBTTagCompound()));
//		FMLInterModComms.sendMessage("Mekanism", "PurificationChamberRecipe", oxideEnrichment);

		mekanism.api.recipe.RecipeHelper.addPurificationChamberRecipe(fuel, fuelOxide);
	}

	public static void addOxideRecipes(){
		addOxideFuels("LEU235");
		addOxideFuels("HEU235");
		addOxideFuels("LEP239");
		addOxideFuels("HEP239");
		addOxideFuels("LEU233");
		addOxideFuels("HEU233");
		addOxideFuels("LEP241");
		addOxideFuels("HEP241");
		addOxideFuels("TBU");
		addOxideFuels("LEN236");
		addOxideFuels("HEN236");
		addOxideFuels("LEA242");
		addOxideFuels("HEA242");
		addOxideFuels("LEC243");
		addOxideFuels("HEC243");
		addOxideFuels("LEC245");
		addOxideFuels("HEC245");
		addOxideFuels("LEC247");
		addOxideFuels("HEC247");
	}

	public void b(int meta, String item) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCBlocks.blockBlock, 1, meta), true, new Object[] {"XXX", "XXX", "XXX", 'X', item}));
	}
	
	public void b(Block block, String item) {
		GameRegistry.addRecipe(new ShapedOreRecipe(block, true, new Object[] {"XXX", "XXX", "XXX", 'X', item}));
	}
	
	public void l(int meta, String fertile, String fissile) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, meta), new Object[] {fertile, fertile, fertile, fertile, fertile, fertile, fertile, fertile, fissile}));
	}
	
	public void h(int meta, String fertile, String fissile) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, meta), new Object[] {fertile, fertile, fertile, fertile, fertile, fissile, fissile, fissile, fissile}));
	}
	
	public void f(int meta, String fertile) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, meta), new Object[] {fertile, fertile, fertile, fertile, fertile, fertile, fertile, fertile, fertile}));
	}
	
	public void m(int meta, String item) {
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(NCItems.material, 1, meta), true, new Object[] {"XXX", "XXX", "XXX", 'X', item}));
	}
	
	public void c(int meta, String fuel) {
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(NCItems.fuel, 1, meta), new Object[] {fuel, new ItemStack (NCItems.fuel, 1, 33)}));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		// Seeds
		MinecraftForge.addGrassSeed(extraDrops ? new ItemStack(Items.pumpkin_seeds) : new ItemStack(Items.wheat_seeds), 1);
			
		// Extra Block Drops
		MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
		
		// Extra Mob Drops
		MinecraftForge.EVENT_BUS.register(new EntityDropHandler());

		// Achievements
		achievements = new Achievements("NuclearCraft");
		FMLCommonHandler.instance().bus().register(achievements);
		
		nuclearFurnaceAchievement = a("nuclearFurnace", 4, -2, NCBlocks.nuclearFurnaceIdle, null);
		heavyDutyWorkspaceAchievement = a("heavyDutyWorkspace", 0, 0, NCBlocks.nuclearWorkspace, null);
		nukeAchievement = a("nuke", -2, -2, NCBlocks.nukeE, heavyDutyWorkspaceAchievement);
		reactionGeneratorAchievement = a("reactionGenerator", -2, 0, NCBlocks.reactionGeneratorIdle, heavyDutyWorkspaceAchievement);
		fissionControllerAchievement = a("fissionController", 2, 2, NCBlocks.fissionReactorGraphiteIdle, null);
		RTGAchievement = a("RTG", 2, 0, NCBlocks.RTG, fissionControllerAchievement);
		fusionReactorAchievement = a("fusionReactor", 4, 2, NCBlocks.fusionReactor, fissionControllerAchievement);
		separatorAchievement = a("separator", -2, 2, NCBlocks.separatorIdle, null);
		synchrotronAchievement = a("synchrotron", 4, 6, NCBlocks.synchrotronIdle, null);
		synchrotronAchievement = a("antimatterBomb", 4, 8, NCBlocks.antimatterBombE, synchrotronAchievement);
	}

	public Achievement a(String name, int x, int y, Block req, Achievement pre) {
		return achievements.registerAchievement(new Achievement("achievement." + name, name, x, y, req, pre));
	}
	
	public Achievement a(String name, int x, int y, Item req, Achievement pre) {
		return achievements.registerAchievement(new Achievement("achievement." + name, name, x, y, req, pre));
	}
	
	public Achievement a(String name, int x, int y, ItemStack req, Achievement pre) {
		return achievements.registerAchievement(new Achievement("achievement." + name, name, x, y, req, pre));
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		//IC2 Integration
		IC2Hook = new IC2Recipes();
		IC2Hook.hook();

		//Thermal Expansion Integration
		TEHook = new TERecipes();
		TEHook.hook();
	}
}