package nc.crafting.machine;

import nc.crafting.NCRecipeHelper;

public class FissionRecipes extends NCRecipeHelper {

	private static final FissionRecipes recipes = new FissionRecipes();

	public FissionRecipes(){
		super(1, 1);
	}
	public static final NCRecipeHelper instance() {
		return recipes;
	}

	public void addRecipes() {
		deplete2("fuelLEU235");
		deplete2("fuelHEU235");
		deplete2("fuelLEP239");
		deplete2("fuelHEP239");
		deplete("fuelMOX239");
		deplete2("fuelTBU");
		deplete2("fuelLEU233");
		deplete2("fuelHEU233");
		deplete2("fuelLEP241");
		deplete2("fuelHEP241");
		deplete("fuelMOX241");
		
		deplete2("fuelLEN236");
		deplete2("fuelHEN236");
		deplete2("fuelLEA242");
		deplete2("fuelHEA242");
		deplete2("fuelLEC243");
		deplete2("fuelHEC243");
		deplete2("fuelLEC245");
		deplete2("fuelHEC245");
		deplete2("fuelLEC247");
		deplete2("fuelHEC247");
	}
	
	public void deplete(String fuel) {
		addRecipe(fuel + "Cell", "d" + fuel + "Cell");
	}
	
	public void deplete2(String fuel) {
		addRecipe(fuel + "Cell", "d" + fuel + "Cell");
		addRecipe(fuel + "CellOxide", "d" + fuel + "CellOxide");
	}
}