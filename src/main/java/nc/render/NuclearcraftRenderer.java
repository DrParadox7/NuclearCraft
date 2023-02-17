package nc.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import mekanism.api.gas.GasRegistry;
import mekanism.client.render.MekanismRenderer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class NuclearcraftRenderer {

    public static void init()
    {
        MinecraftForge.EVENT_BUS.register(new NuclearcraftRenderer());
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event)
    {
        if(event.map.getTextureType() == 0) {
            GasRegistry.getGas("uranium238").setIcon(event.map.registerIcon("nc:fluid/gases/LiquidUranium238"));
            GasRegistry.getGas("boron10").setIcon(event.map.registerIcon("nc:fluid/gases/LiquidBoron10"));
        }
    }
}
