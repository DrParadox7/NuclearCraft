package nc.crafting.machine;

import nc.crafting.NCRecipeHelper;

public class SeparatorRecipes extends NCRecipeHelper {

	private static final SeparatorRecipes recipes = new SeparatorRecipes();

	public SeparatorRecipes(){
		super(1, 2);
	}
	public static final NCRecipeHelper instance() {
		return recipes;
	}

	public void addRecipes() {
		fuel("fuelU235", "fuelU238", "fuelU235");
		fuel("fuelP239", "fuelPu242", "fuelPu239");
		fuel("fuelU233", "fuelU238", "fuelU233");
		fuel("fuelP241", "fuelPu242", "fuelPu241");
		
		recipe("fuelMOX239", "fuelU238Oxide", 8, "fuelPu239Oxide", 1);
		recipe("fuelMOX239Cell", "fuelU238Oxide", 8, "fuelPu239Oxide", 1);
		recipe("fuelMOX241", "fuelU238Oxide", 8, "fuelPu241Oxide", 1);
		recipe("fuelMOX241Cell", "fuelU238Oxide", 8, "fuelPu241Oxide", 1);
		recipe("fuelTBU", "fuelTh232Base", 5, "fuelTh232Base", 4);
		recipe("fuelTBUCell", "fuelTh232Base", 5, "fuelTh232Base", 4);
		recipe("fuelTBUOxide", "fuelTh232Oxide", 5, "fuelTh232Oxide", 4);
		recipe("fuelTBUCellOxide", "fuelTh232Oxide", 5, "fuelTh232Oxide", 4);
		
		fuel("fuelN236", "fuelNp237", "fuelNp236");
		fuel("fuelA242", "fuelAm243", "fuelAm242");
		fuel("fuelC243", "fuelCm246", "fuelCm243");
		fuel("fuelC245", "fuelCm246", "fuelCm245");
		fuel("fuelC247", "fuelCm246", "fuelCm247");
		
		recipe("ingotUranium", "U238Base", 2, "tinyU235Base", 2);
		//recipe("dustUranium", "U238Base", 2, "tinyU235Base", 2);
		
		recipe("ingotYellorium", "U238Base", "tinyU235Base");
		//recipe("dustYellorium", "U238Base", "tinyU235Base");
		recipe("ingotYellorite", "U238Base", "tinyU235Base");
		//recipe("dustYellorite", "U238Base", "tinyU235Base");
		
		recipe("ingotThorium", "Th232Base", 2, "tinyTh230Base", 2);
		//recipe("dustThorium", "Th232Base", 2, "tinyTh230Base", 2);
		
		recipe("ingotUraniumOxide", "U238Oxide", 2, "tinyU235Oxide", 2);
		//recipe("dustUraniumOxide", "U238Oxide", 2, "tinyU235Oxide", 2);
		
		recipe("ingotThoriumOxide", "Th232Oxide", 2, "tinyTh230Oxide", 2);
		//recipe("dustThoriumOxide", "Th232Oxide", 2, "tinyTh230Oxide", 2);
		
		recipe("ingotLithium", "Li7", "tinyLi6", 2);
		//recipe("dustLithium", "Li7", "tinyLi6", 2);
		
		recipe("ingotBoron", "B11", "tinyB10", 3);
		//recipe("dustBoron", "B11", "tinyB10", 3);
	}
	
	public void recipe(String in, String out1, int num1, String out2, int num2) {
		addRecipe(in, oreStack(out1, num1), oreStack(out2, num2));
	}
	
	public void recipe(String in, String out1, String out2, int num2) {
		addRecipe(in, out1, oreStack(out2, num2));
	}
	
	public void recipe(String in, String out1, int num1, String out2) {
		addRecipe(in, oreStack(out1, num1), out2);
	}
	
	public void recipe(String in, String out1, String out2) {
		addRecipe(in, out1, out2);
	}
	
	public void fuel(String fuel, String fertile, String fissile) {
		recipe("fuelLE" + fuel, fertile + "Base", 8, fissile + "Base", 1);
		recipe("fuelHE" + fuel, fertile + "Base", 5, fissile + "Base", 4);
		recipe("fuelLE" + fuel + "Cell", fertile + "Base", 8, fissile + "Base", 1);
		recipe("fuelHE" + fuel + "Cell", fertile + "Base", 5, fissile + "Base", 4);
		
		recipe("fuelLE" + fuel + "Oxide", fertile + "Oxide", 8, fissile + "Oxide", 1);
		recipe("fuelHE" + fuel + "Oxide", fertile + "Oxide", 5, fissile + "Oxide", 4);
		recipe("fuelLE" + fuel + "CellOxide", fertile + "Oxide", 8, fissile + "Oxide", 1);
		recipe("fuelHE" + fuel + "CellOxide", fertile + "Oxide", 5, fissile + "Oxide", 4);
	}
}
