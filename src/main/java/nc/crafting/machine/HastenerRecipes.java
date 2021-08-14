package nc.crafting.machine;

import nc.crafting.NCRecipeHelper;
import nc.item.NCItems;
import net.minecraft.item.ItemStack;

public class HastenerRecipes extends NCRecipeHelper {

	private static final HastenerRecipes recipes = new HastenerRecipes();

	public HastenerRecipes(){
		super(1, 1);
	}
	public static final NCRecipeHelper instance() {
		return recipes;
	}

	public void addRecipes() {
		addRecipe("fuelU238Base", "fuelTh230Base");
		addRecipe("fuelU235Base", "fueldustLead");
		addRecipe("fuelU233Base", "fueldustLead");
		addRecipe("fuelPu238Base", "fuelTh230Base");
		addRecipe("fuelPu239Base", "fuelU235Base");
		addRecipe("fuelPu242Base", "fuelU238Base");
		addRecipe("fuelPu241Base", "fuelNp237Base");
		addRecipe("fuelTh232Base", "fueldustLead");
		addRecipe("fuelTh230Base", "fueldustLead");
		
		addRecipe("fueltinyU238Base", "fueltinyTh230Base");
		addRecipe("fueltinyU235Base", "fueldustTinyLead");
		addRecipe("fueltinyU233Base", "fueldustTinyLead");
		addRecipe("fueltinyPu238Base", "fueltinyTh230Base");
		addRecipe("fueltinyPu239Base", "fueltinyU235Base");
		addRecipe("fueltinyPu242Base", "fueltinyU238Base");
		addRecipe("fueltinyPu241Base", "fueltinyNp237Base");
		addRecipe("fueltinyTh232Base", "fueldustTinyLead");
		addRecipe("fueltinyTh230Base", "fueldustTinyLead");
		
		addRecipe("fuelU238Oxide", "fuelTh230Oxide");
		addRecipe("fuelU235Oxide", "fueldustLead");
		addRecipe("fuelU233Oxide", "fueldustLead");
		addRecipe("fuelPu238Oxide", "fuelTh230Oxide");
		addRecipe("fuelPu239Oxide", "fuelU235Oxide");
		addRecipe("fuelPu242Oxide", "fuelU238Oxide");
		addRecipe("fuelPu241Oxide", "fuelNp237Oxide");
		addRecipe("fuelTh232Oxide", "fueldustLead");
		addRecipe("fuelTh230Oxide", "fueldustLead");
		
		addRecipe("fueltinyU238Oxide", "fueltinyTh230Oxide");
		addRecipe("fueltinyU235Oxide", "fueldustTinyLead");
		addRecipe("fueltinyU233Oxide", "fueldustTinyLead");
		addRecipe("fueltinyPu238Oxide", "fueltinyTh230Oxide");
		addRecipe("fueltinyPu239Oxide", "fueltinyU235Oxide");
		addRecipe("fueltinyPu242Oxide", "fueltinyU238Oxide");
		addRecipe("fueltinyPu241Oxide", "fueltinyNp237Oxide");
		addRecipe("fueltinyTh232Oxide", "fueldustTinyLead");
		addRecipe("fueltinyTh230Oxide", "fueldustTinyLead");
    	
		addRecipe("fuelNp236Base", "fueldustLead");
		addRecipe("fuelNp237Base", "fuelU233Base");
		addRecipe("fuelAm241Base", "fuelNp237Base");
		addRecipe("fuelAm242Base", "fuelTh230Base");
		addRecipe("fuelAm243Base", "fuelPu239Base");
		addRecipe("fuelCm243Base", "fuelPu239Base");
		addRecipe("fuelCm245Base", "fuelPu241Base");
		addRecipe("fuelCm246Base", "fuelPu242Base");
		addRecipe("fuelCm247Base", "fuelAm243Base");
		addRecipe("fuelCf250Base", "fuelCm246Base");
		
		addRecipe("fueltinyNp236Base", "fueldustTinyLead");
		addRecipe("fueltinyNp237Base", "fueltinyU233Base");
		addRecipe("fueltinyAm241Base", "fueltinyNp237Base");
		addRecipe("fueltinyAm242Base", "fueltinyTh230Base");
		addRecipe("fueltinyAm243Base", "fueltinyPu239Base");
		addRecipe("fueltinyCm243Base", "fueltinyPu239Base");
		addRecipe("fueltinyCm245Base", "fueltinyPu241Base");
		addRecipe("fueltinyCm246Base", "fueltinyPu242Base");
		addRecipe("fueltinyCm247Base", "fueltinyAm243Base");
		addRecipe("fueltinyCf250Base", "fueltinyCm246Base");
		
		addRecipe("fuelNp236Oxide", "fueldustLead");
		addRecipe("fuelNp237Oxide", "fuelU233Oxide");
		addRecipe("fuelAm241Oxide", "fuelNp237Oxide");
		addRecipe("fuelAm242Oxide", "fuelTh230Oxide");
		addRecipe("fuelAm243Oxide", "fuelPu239Oxide");
		addRecipe("fuelCm243Oxide", "fuelPu239Oxide");
		addRecipe("fuelCm245Oxide", "fuelPu241Oxide");
		addRecipe("fuelCm246Oxide", "fuelPu242Oxide");
		addRecipe("fuelCm247Oxide", "fuelAm243Oxide");
		addRecipe("fuelCf250Oxide", "fuelCm246Oxide");
		
		addRecipe("fueltinyNp236Oxide", "fueldustTinyLead");
		addRecipe("fueltinyNp237Oxide", "fueltinyU233Oxide");
		addRecipe("fueltinyAm241Oxide", "fueltinyNp237Oxide");
		addRecipe("fueltinyAm242Oxide", "fueltinyTh230Oxide");
		addRecipe("fueltinyAm243Oxide", "fueltinyPu239Oxide");
		addRecipe("fueltinyCm243Oxide", "fueltinyPu239Oxide");
		addRecipe("fueltinyCm245Oxide", "fueltinyPu241Oxide");
		addRecipe("fueltinyCm246Oxide", "fueltinyPu242Oxide");
		addRecipe("fueltinyCm247Oxide", "fueltinyAm243Oxide");
		addRecipe("fueltinyCf250Oxide", "fueltinyCm246Oxide");
    	
    	addRecipe(new ItemStack(NCItems.fuel, 1, 38), new ItemStack(NCItems.fuel, 1, 39));
    	addRecipe(new ItemStack(NCItems.fuel, 1, 47), new ItemStack(NCItems.fuel, 1, 49));
	}
}