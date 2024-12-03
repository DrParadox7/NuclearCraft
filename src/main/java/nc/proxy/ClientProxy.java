package nc.proxy;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import nc.NuclearCraft;
import nc.block.NCBlocks;
import nc.entity.EntityAntimatterBombPrimed;
import nc.entity.EntityEMPPrimed;
import nc.entity.EntityNuclearMonster;
import nc.entity.EntityNukePrimed;
import nc.item.NCItems;
import nc.model.ModelNuclearMonster;
import nc.render.*;
import nc.tile.crafting.TileNuclearWorkspace;
import nc.tile.generator.TileFusionReactor;
import nc.tile.generator.TileFusionReactorSteam;
import nc.tile.other.TileTubing1;
import nc.tile.other.TileTubing2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.common.MinecraftForge;
import team.chisel.ctmlib.CTMRenderer;

public class ClientProxy extends CommonProxy {
	
public void registerRenderThings() {

	//connected textures handler
	ISimpleBlockRenderingHandler connectedTextureHandler = new CTMRenderer(RenderingRegistry.getNextAvailableRenderId());
	NuclearCraft.connectedRenderID = connectedTextureHandler.getRenderId();
	RenderingRegistry.registerBlockHandler(connectedTextureHandler);

	//Nuclear Workspace
	TileEntitySpecialRenderer render = new RenderNuclearWorkspace();
	ClientRegistry.bindTileEntitySpecialRenderer(TileNuclearWorkspace.class, render);
	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NCBlocks.nuclearWorkspace), new ItemRenderNuclearWorkspace(render, new TileNuclearWorkspace()));

	//Fusion Reactor
	TileEntitySpecialRenderer renderfusion = new RenderFusionReactor();
	ClientRegistry.bindTileEntitySpecialRenderer(TileFusionReactor.class, renderfusion);
	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NCBlocks.fusionReactor), new ItemRenderFusionReactor(renderfusion, new TileFusionReactor()));

	//Fusion Reactor Steam
	TileEntitySpecialRenderer renderfusionSteam = new RenderFusionReactorSteam();
	ClientRegistry.bindTileEntitySpecialRenderer(TileFusionReactorSteam.class, renderfusionSteam);
	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NCBlocks.fusionReactorSteam), new ItemRenderFusionReactorSteam(renderfusionSteam, new TileFusionReactorSteam()));

	//Tubing 1
	TileEntitySpecialRenderer render1 = new RenderTubing1();
	ClientRegistry.bindTileEntitySpecialRenderer(TileTubing1.class, render1);
	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NCBlocks.tubing1), new ItemRenderTubing1(render1, new TileTubing1()));

	//Tubing 2
	TileEntitySpecialRenderer render2 = new RenderTubing2();
	ClientRegistry.bindTileEntitySpecialRenderer(TileTubing2.class, render2);
	MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(NCBlocks.tubing2), new ItemRenderTubing2(render2, new TileTubing2()));

	//Nuke Primed
	RenderingRegistry.registerEntityRenderingHandler(EntityNukePrimed.class, new RenderNukePrimed());

	//EMP Primed
	RenderingRegistry.registerEntityRenderingHandler(EntityEMPPrimed.class, new RenderEMPPrimed());

	//Antimatter Bomb Primed
	RenderingRegistry.registerEntityRenderingHandler(EntityAntimatterBombPrimed.class, new RenderAntimatterBombPrimed());

	//Entities
	RenderingRegistry.registerEntityRenderingHandler(EntityNuclearMonster.class, new RenderNuclearMonster(new ModelNuclearMonster(), 0.3F));

	//Gases
	NuclearcraftRenderer.init();
}
	@Override
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return (ctx.side.isClient() ? Minecraft.getMinecraft().thePlayer : super.getPlayerEntity(ctx));
	}

	public void registerSounds() {}

	public void registerTileEntitySpecialRenderer() {}
}
