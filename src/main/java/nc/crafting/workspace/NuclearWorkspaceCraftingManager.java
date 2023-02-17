package nc.crafting.workspace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cpw.mods.fml.common.registry.GameRegistry;
import nc.NuclearCraft;
import nc.block.NCBlocks;
import nc.item.NCItems;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class NuclearWorkspaceCraftingManager {
    /** The static instance of this class */
    public static final NuclearWorkspaceCraftingManager instance = new NuclearWorkspaceCraftingManager();
    /** A list of all the recipes added */
    @SuppressWarnings("rawtypes")
	public List recipes;

    /**
     * Returns the static instance of this class
     */
    public static final NuclearWorkspaceCraftingManager getInstance() {
        /** The static instance of this class */
        return instance;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public NuclearWorkspaceCraftingManager() {
        ItemStack circuitBasic = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);

        ItemStack circuitAdv = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);
        circuitAdv.setItemDamage(1);

        ItemStack circuitElite = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);
        circuitElite.setItemDamage(2);

        ItemStack circuitUltimate = GameRegistry.findItemStack("Mekanism", "ControlCircuit", 1);
        circuitUltimate.setItemDamage(3);

        ItemStack electroCoil = GameRegistry.findItemStack("MekanismGenerators", "Generator", 1);
        electroCoil.setItemDamage(9);

        ItemStack teleportCore = GameRegistry.findItemStack("Mekanism", "TeleportationCore", 1);

        ItemStack casingTurbine = GameRegistry.findItemStack("MekanismGenerators", "Generator", 1);
        casingTurbine.setItemDamage(10);

        ItemStack turbineValve = GameRegistry.findItemStack("MekanismGenerators", "Generator", 1);
        turbineValve.setItemDamage(11);

        ItemStack turbineBlade = GameRegistry.findItemStack("MekanismGenerators", "TurbineBlade", 1);

        ItemStack Reactor = GameRegistry.findItemStack("MekanismGenerators", "Reactor", 1);

        recipes = new ArrayList();

        //Reactor Casing
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.reactorBlock, 16), true, new Object[]{"IBBBI", "BCCCB", "BCCCB", "BCCCB", "IBBBI", 'B', "plateBasic", 'I', "ingotSteel", 'C', OreDictionary.doesOreNameExist("blockConcrete") ? "blockConcrete" : new ItemStack(Blocks.clay)}));
        //Fuel Cell Container
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.cellBlock, 4), true, new Object[]{"IBGBI", "BG GB", "G   G", "BG GB", "IBGBI", 'B', "plateBasic", 'I', "ingotSteel", 'G', new ItemStack(NCBlocks.reactorBlock, 1, 1)}));

        //Empty Reactor Cooler
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.emptyCoolerBlock, 8), true, new Object[]{"IBBBI", "BXXXB", "BXXXB", "BXXXB", "IBBBI", 'B', "plateBasic", 'I', "ingotSteel", 'X', new ItemStack(Blocks.iron_bars, 1)}));
        //Speed Reactor Block
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.speedBlock, 6), true, new Object[] {" NNN ", "NURUN", "NRURN", "NURUN", " NNN ", 'N', "plateBasic", 'R', Items.blaze_powder, 'U', Items.redstone}));

        //Fission Controller Graphite
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.fissionReactorGraphiteIdle, 1), true, new Object[]{"RBGBR", "BECEB", "GCNCG", "BECEB", "RBGBR", 'R', "plateReinforced", 'B', "plateBasic", 'G', "blockGraphite", 'E', "alloyElite", 'C', new ItemStack(NCItems.parts, 1, 18), 'N', new ItemStack(NCBlocks.nuclearFurnaceIdle, 1)}));
        //Fission Controller Steam
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.fissionReactorSteamIdle, 1), true, new Object[]{" CVC ", "CTRTC", "VRFRV", "CTRTC", " CVC ", 'C', casingTurbine, 'T', turbineBlade,'V', turbineValve, 'R', new ItemStack(NCBlocks.emptyCoolerBlock, 8), 'F', new ItemStack(NCBlocks.fissionReactorGraphiteIdle, 1)}));

        //Tubing (Aesthetic)
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.tubing1, 8), true, new Object[] {" NNN ", "NIIIN", " NNN ", 'N', "plateLead", 'I', "plateIron"}));
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.tubing2, 8), true, new Object[] {" N ", "NIN", "NIN", "NIN", " N ", 'N', "plateLead", 'I', "plateIron"}));

        //Electromagnet
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.electromagnetIdle, 8), true, new Object[]{"RBCBR", "BICIB", "CCACC", "BICIB", "RBCBR", 'R', "plateReinforced", 'B', "plateBasic", 'C', new ItemStack(NCItems.parts, 1, 12), 'A', "alloyAdvanced", 'I', "ingotRedAlloy"}));
        //Fusion Connector
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.fusionConnector, 4), true, new Object[]{"ARIRA", "RSCSR", "ICTCI", "RSCSR", "ARIRA", 'A', "alloyElite", 'R', "plateReinforced", 'I', "ingotElectrumFlux", 'S', "itemSilicon", 'C', new ItemStack(NCItems.parts, 1, 12), 'T', "coilSilver"}));
        //Fusion Controller
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.fusionReactor, 1), true, new Object[]{"DRFRD", "RUCUR", "FRXRF", "RUCUR", "DRFRD", 'D', "plateDU", 'R', "plateReinforced", 'F', new ItemStack(NCBlocks.fusionConnector, 1), 'U', "alloyUltimate", 'C', new ItemStack(NCItems.parts, 1, 18), 'X', Reactor}));

        //Steam Fusion Controller
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.fusionReactorSteam, 1), true, new Object[]{" CVC ", "CTRTC", "VRFRV", "CTRTC", " CVC ", 'C', casingTurbine, 'T', turbineBlade,'V', turbineValve, 'R', new ItemStack(NCBlocks.emptyCoolerBlock, 8), 'F', new ItemStack(NCBlocks.fusionReactor, 1)}));

        //Synchroton Controller
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.synchrotronIdle, 1), true, new Object[]{"EUUUE", "UACAU", "UCTCU", "UACAU", "EUUUE", 'E', new ItemStack(NCBlocks.superElectromagnetIdle, 1, 3), 'A', "plateAdvanced", 'U', "plateDU", 'C', circuitUltimate, 'T', teleportCore}));
        //Superconducting Electromagnetic
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.superElectromagnetIdle, 16), true, new Object[]{"ADEDA", "DGEGD", "EEUEE", "DGEGD", "ADEDA", 'A', "plateAdvanced", 'D', "plateDU", 'E', new ItemStack(NCItems.parts, 1, 17), 'G', "ingotRefinedGlowstone", 'U', "alloyUltimate"}));
        //Electromagnetic Cooler
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.supercoolerIdle, 16), true, new Object[]{"ARTRA", "RLTLR", "TTUTT", "RLTLR", "ARTRA", 'A', "plateAdvanced", 'R', "plateReinforced", 'T', new ItemStack(NCBlocks.reactorBlock, 1, 1), 'L', "ingotRefinedLapis", 'U', "alloyUltimate"}));

        //RTG Uranium
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.WRTG, 1), true, new Object[]{"CPPPC", "PEGEP", "PGXGP", "PEGEP", "CPPPC", 'C', circuitBasic, 'P', "plateBasic", 'E', electroCoil, 'G', "blockGraphite", 'X', new ItemStack(NCItems.fuel, 1, 141)}));
        //RTG Americium
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.AmRTG, 1), true, new Object[]{"CPPPC", "PEGEP", "PGXGP", "PEGEP", "CPPPC", 'C', circuitAdv, 'P', "plateReinforced", 'E', electroCoil, 'G', "blockGraphite", 'X', new ItemStack(NCItems.fuel, 1, 139)}));
        //RTG Plutonium
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.RTG, 1), true, new Object[]{"CPPPC", "PEGEP", "PGXGP", "PEGEP", "CPPPC", 'C', circuitElite, 'P', "plateDU", 'E', electroCoil, 'G', "blockGraphite", 'X', new ItemStack(NCItems.fuel, 1, 46)}));
        //Californium Plutonium
        this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.CfRTG, 1), true, new Object[]{"CPPPC", "PEGEP", "PGXGP", "PEGEP", "CPPPC", 'C', circuitUltimate, 'P', "plateAdvanced", 'E', electroCoil, 'G', "blockGraphite", 'X', new ItemStack(NCItems.fuel, 1, 140)}));

        if (NuclearCraft.enableNukes)
            this.addRecipe(new NuclearWorkspaceShapedOreRecipe(new ItemStack(NCBlocks.antimatterBomb, 1), true, new Object[] {"PPPPP", "PAAAP", "PAEAP", "PAAAP", "PPPPP", 'A', NCItems.antimatter, 'P', "plateAdvanced", 'E', NCBlocks.superElectromagnetIdle}));

        Collections.sort(this.recipes, new NuclearWorkspaceRecipeSorter());
    }

    @SuppressWarnings("unchecked")
    public void addRecipe(IRecipe recipe) {
        //NuclearWorkspaceCraftingManager.getInstance().getRecipeList().add(recipe);
        this.recipes.add(recipe);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public NuclearWorkspaceShapedRecipes addRecipe(ItemStack p_92103_1_, Object ... p_92103_2_) {
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if (p_92103_2_[i] instanceof String[]) {
            String[] astring = (String[])((String[])p_92103_2_[i++]);

            for (int l = 0; l < astring.length; ++l) {
                String s1 = astring[l];
                ++k;
                j = s1.length();
                s = s + s1;
            }
        } else {
            while (p_92103_2_[i] instanceof String) {
                String s2 = (String)p_92103_2_[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap hashmap;

        for (hashmap = new HashMap(); i < p_92103_2_.length; i += 2) {
            Character character = (Character)p_92103_2_[i];
            ItemStack itemstack1 = null;

            if (p_92103_2_[i + 1] instanceof Item) {
                itemstack1 = new ItemStack((Item)p_92103_2_[i + 1]);
            } else if (p_92103_2_[i + 1] instanceof Block) {
                itemstack1 = new ItemStack((Block)p_92103_2_[i + 1], 1, 32767);
            } else if (p_92103_2_[i + 1] instanceof ItemStack) {
                itemstack1 = (ItemStack)p_92103_2_[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for (int i1 = 0; i1 < j * k; ++i1) {
            char c0 = s.charAt(i1);

            if (hashmap.containsKey(Character.valueOf(c0))) {
                aitemstack[i1] = ((ItemStack)hashmap.get(Character.valueOf(c0))).copy();
            } else {
                aitemstack[i1] = null;
            }
        }

        NuclearWorkspaceShapedRecipes shapedrecipes = new NuclearWorkspaceShapedRecipes(j, k, aitemstack, p_92103_1_);
        this.recipes.add(shapedrecipes);
        return shapedrecipes;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public NuclearWorkspaceShapelessRecipes addShapelessRecipe(ItemStack p_77596_1_, Object ... p_77596_2_) {
        ArrayList arraylist = new ArrayList();
        Object[] aobject = p_77596_2_;
        int i = p_77596_2_.length;

        for (int j = 0; j < i; ++j) {
            Object object1 = aobject[j];

            if (object1 instanceof ItemStack) {
                arraylist.add(((ItemStack)object1).copy());
            } else if (object1 instanceof Item) {
                arraylist.add(new ItemStack((Item)object1));
            } else {
                if (!(object1 instanceof Block)) {
                    throw new RuntimeException("Invalid shapeless recipe!");
                }

                arraylist.add(new ItemStack((Block)object1));
            }
        }

        NuclearWorkspaceShapelessRecipes shapelessrecipes = new NuclearWorkspaceShapelessRecipes(p_77596_1_, arraylist);
        this.recipes.add(shapelessrecipes);
        return shapelessrecipes;
    }

    public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, World p_82787_2_) {
        int i = 0;
        ItemStack itemstack = null;
        ItemStack itemstack1 = null;
        int j;

        for (j = 0; j < p_82787_1_.getSizeInventory(); ++j) {
            ItemStack itemstack2 = p_82787_1_.getStackInSlot(j);

            if (itemstack2 != null) {
                if (i == 0) {
                    itemstack = itemstack2;
                }
                if (i == 1) {
                    itemstack1 = itemstack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemstack.getItem() == itemstack1.getItem() && itemstack.stackSize == 1 && itemstack1.stackSize == 1 && itemstack.getItem().isRepairable()) {
            Item item = itemstack.getItem();
            int j1 = item.getMaxDamage() - itemstack.getItemDamageForDisplay();
            int k = item.getMaxDamage() - itemstack1.getItemDamageForDisplay();
            int l = j1 + k + item.getMaxDamage() * 5 / 100;
            int i1 = item.getMaxDamage() - l;

            if (i1 < 0) {
                i1 = 0;
            }

            return new ItemStack(itemstack.getItem(), 1, i1);
        } else {
            for (j = 0; j < this.recipes.size(); ++j) {
                IRecipe irecipe = (IRecipe)this.recipes.get(j);

                if (irecipe.matches(p_82787_1_, p_82787_2_)) {
                    return irecipe.getCraftingResult(p_82787_1_);
                }
            }

            return null;
        }
    }

    /**
     * returns the List<> of all recipes
     */
    @SuppressWarnings("rawtypes")
    public List getRecipeList() {
        return this.recipes;
    }
}