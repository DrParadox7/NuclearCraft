package nc.crafting.machine;

import nc.crafting.NCRecipeHelper;

public class RecyclerRecipes extends NCRecipeHelper {

	private static final RecyclerRecipes recipes = new RecyclerRecipes();

	public RecyclerRecipes(){
		super(1, 4);
	}
	public static final NCRecipeHelper instance() {
		return recipes;
	}

	public void addRecipes() {
		recipe("fueldLEU235Cell", "fueltinyU238Base", 24, "fueltinyNp236Base", 4, "fueltinyNp237Base", 4, "fueltinyPu239Base", 8);
		recipe("fueldHEU235Cell", "fueltinyU238Base", 16, "fueltinyNp237Base", 8, "fueltinyPu239Base", 8, "fueltinyPu242Base", 8);
		recipe("fueldLEP239Cell", "fueltinyPu242Base", 24, "fueltinyPu241Base", 4, "fueltinyAm242Base", 4, "fueltinyAm243Base", 8);
		recipe("fueldHEP239Cell", "fueltinyPu242Base", 16, "fueltinyPu241Base", 8, "fueltinyAm242Base", 8, "fueltinyAm243Base", 8);
		recipe("fueldMOX239Cell", "fueltinyU238Oxide", 24, "fueltinyPu239Oxide", 4, "fueltinyPu241Oxide", 4, "fueltinyPu242Oxide", 8);
		recipe("fueldTBUCell", "fueltinyU233Base", 24, "fueltinyU235Base", 4, "fueltinyNp236Base", 4, "fueltinyNp237Base", 8);
		recipe("fueldLEU233Cell", "fueltinyU238Base", 24, "fueltinyU235Base", 4, "fueltinyNp236Base", 4, "fueltinyNp237Base", 8);
		recipe("fueldHEU233Cell", "fueltinyU238Base", 16, "fueltinyNp236Base", 8, "fueltinyNp237Base", 8, "fueltinyPu239Base", 8);
		recipe("fueldLEP241Cell", "fueltinyPu242Base", 24, "fueltinyAm241Base", 4, "fueltinyAm242Base", 4, "fueltinyCm243Base", 8);
		recipe("fueldHEP241Cell", "fueltinyPu242Base", 16, "fueltinyAm243Base", 8, "fueltinyCm243Base", 8, "fueltinyCm246Base", 8);
		recipe("fueldMOX241Cell", "fueltinyU238Oxide", 24, "fueltinyPu239Oxide", 4, "fueltinyPu241Oxide", 4, "fueltinyAm243Oxide", 8);
		
		recipe("fueldLEU235CellOxide", "fueltinyU238Oxide", 24, "fueltinyNp236Oxide", 4, "fueltinyNp237Oxide", 4, "fueltinyPu239Oxide", 8);
		recipe("fueldHEU235CellOxide", "fueltinyU238Oxide", 16, "fueltinyNp237Oxide", 8, "fueltinyPu239Oxide", 8, "fueltinyPu242Oxide", 8);
		recipe("fueldLEP239CellOxide", "fueltinyPu242Oxide", 24, "fueltinyPu241Oxide", 4, "fueltinyAm242Oxide", 4, "fueltinyAm243Oxide", 8);
		recipe("fueldHEP239CellOxide", "fueltinyPu242Oxide", 16, "fueltinyPu241Oxide", 8, "fueltinyAm242Oxide", 8, "fueltinyAm243Oxide", 8);
		recipe("fueldTBUCellOxide", "fueltinyU233Oxide", 24, "fueltinyU235Oxide", 4, "fueltinyNp236Oxide", 4, "fueltinyNp237Oxide", 8);
		recipe("fueldLEU233CellOxide", "fueltinyU238Oxide", 24, "fueltinyU235Oxide", 4, "fueltinyNp236Oxide", 4, "fueltinyNp237Oxide", 8);
		recipe("fueldHEU233CellOxide", "fueltinyU238Oxide", 16, "fueltinyNp236Oxide", 8, "fueltinyNp237Oxide", 8, "fueltinyPu239Oxide", 8);
		recipe("fueldLEP241CellOxide", "fueltinyPu242Oxide", 24, "fueltinyAm241Oxide", 4, "fueltinyAm242Oxide", 4, "fueltinyCm243Oxide", 8);
		recipe("fueldHEP241CellOxide", "fueltinyPu242Oxide", 16, "fueltinyAm243Oxide", 8, "fueltinyCm243Oxide", 8, "fueltinyCm246Oxide", 8);

		recipe("fueldLEN236Cell", "fueltinyNp237Base", 24, "fueltinyU238Base", 4, "fueltinyPu239Base", 4, "fueltinyPu241Base", 8);
		recipe("fueldHEN236Cell", "fueltinyNp237Base", 16, "fueltinyPu238Base", 8, "fueltinyPu239Base", 8, "fueltinyPu241Base", 8);
		recipe("fueldLEA242Cell", "fueltinyAm243Base", 24, "fueltinyCm243Base", 4, "fueltinyCm246Base", 4, "fueltinyCm247Base", 8);
		recipe("fueldHEA242Cell", "fueltinyAm243Base", 16, "fueltinyCm243Base", 8, "fueltinyCm245Base", 8, "fueltinyCm247Base", 8);
		recipe("fueldLEC243Cell", "fueltinyCm246Base", 24, "fueltinyAm243Base", 4, "fueltinyCm243Base", 4, "fueltinyCm245Base", 8);
		recipe("fueldHEC243Cell", "fueltinyCm246Base", 16, "fueltinyAm243Base", 8, "fueltinyCm243Base", 8, "fueltinyCm245Base", 8);
		recipe("fueldLEC245Cell", "fueltinyCm246Base", 24, "fueltinyCm245Base", 4, "fueltinyCm247Base", 4, "fueltinyCf250Base", 8);
		recipe("fueldHEC245Cell", "fueltinyCm246Base", 16, "fueltinyCm245Base", 8, "fueltinyCm247Base", 8, "fueltinyCf250Base", 8);
		recipe("fueldLEC247Cell", "fueltinyCm246Base", 24, "fueltinyCm247Base", 4, "fueltinyCm247Base", 4, "fueltinyCf250Base", 8);
		recipe("fueldHEC247Cell", "fueltinyCm246Base", 16, "fueltinyCm247Base", 8, "fueltinyCm247Base", 8, "fueltinyCf250Base", 8);
		
		recipe("fueldLEN236CellOxide", "fueltinyNp237Oxide", 24, "fueltinyU238Oxide", 4, "fueltinyPu239Oxide", 4, "fueltinyPu241Oxide", 8);
		recipe("fueldHEN236CellOxide", "fueltinyNp237Oxide", 16, "fueltinyPu238Oxide", 8, "fueltinyPu239Oxide", 8, "fueltinyPu241Oxide", 8);
		recipe("fueldLEA242CellOxide", "fueltinyAm243Oxide", 24, "fueltinyCm243Oxide", 4, "fueltinyCm246Oxide", 4, "fueltinyCm247Oxide", 8);
		recipe("fueldHEA242CellOxide", "fueltinyAm243Oxide", 16, "fueltinyCm243Oxide", 8, "fueltinyCm245Oxide", 8, "fueltinyCm247Oxide", 8);
		recipe("fueldLEC243CellOxide", "fueltinyCm246Oxide", 24, "fueltinyAm243Oxide", 4, "fueltinyCm243Oxide", 4, "fueltinyCm245Oxide", 8);
		recipe("fueldHEC243CellOxide", "fueltinyCm246Oxide", 16, "fueltinyAm243Oxide", 8, "fueltinyCm243Oxide", 8, "fueltinyCm245Oxide", 8);
		recipe("fueldLEC245CellOxide", "fueltinyCm246Oxide", 24, "fueltinyCm245Oxide", 4, "fueltinyCm247Oxide", 4, "fueltinyCf250Oxide", 8);
		recipe("fueldHEC245CellOxide", "fueltinyCm246Oxide", 16, "fueltinyCm245Oxide", 8, "fueltinyCm247Oxide", 8, "fueltinyCf250Oxide", 8);
		recipe("fueldLEC247CellOxide", "fueltinyCm246Oxide", 24, "fueltinyCm247Oxide", 4, "fueltinyCm247Oxide", 4, "fueltinyCf250Oxide", 8);
		recipe("fueldHEC247CellOxide", "fueltinyCm246Oxide", 16, "fueltinyCm247Oxide", 8, "fueltinyCm247Oxide", 8, "fueltinyCf250Oxide", 8);
	}
	
	public void recipe(String in, String out1, int num1, String out2, int num2, String out3, int num3, String out4, int num4) {
		addRecipe(in, oreStack(out1, num1), oreStack(out2, num2), oreStack(out3, num3), oreStack(out4, num4));
	}
}
