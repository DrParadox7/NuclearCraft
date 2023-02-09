package nc.block.machine;

import java.util.Random;

import nc.NuclearCraft;
import nc.block.NCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlockOxidiser extends Block {

	public BlockOxidiser(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setCreativeTab(NuclearCraft.tabNC);
		this.setHardness(3.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeMetal);
		this.setHarvestLevel("pickaxe", 0);
		this.setBlockTextureName("nc:machine/" + unlocalizedName);
	}

	public Item getItemDropped(int par1, Random random, int par3) {
		return Item.getItemFromBlock(NCBlocks.oxidiserIdle);
	}
	
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return new ItemStack(NCBlocks.oxidiserIdle);
	}
	

}