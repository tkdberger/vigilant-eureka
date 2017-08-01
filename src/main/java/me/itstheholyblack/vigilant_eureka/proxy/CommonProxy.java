package me.itstheholyblack.vigilant_eureka.proxy;

import me.itstheholyblack.vigilant_eureka.Reference;
import me.itstheholyblack.vigilant_eureka.blocks.*;
import me.itstheholyblack.vigilant_eureka.blocks.tiles.LeyLineTile;
import me.itstheholyblack.vigilant_eureka.blocks.tiles.MovingCastleDoorTile;
import me.itstheholyblack.vigilant_eureka.client.renderer.CustomBipedArmor;
import me.itstheholyblack.vigilant_eureka.core.EventHandler;
import me.itstheholyblack.vigilant_eureka.items.DimKey;
import me.itstheholyblack.vigilant_eureka.items.ItemBismite;
import me.itstheholyblack.vigilant_eureka.items.ItemLeyKey;
import me.itstheholyblack.vigilant_eureka.items.armor.InvisCap;
import me.itstheholyblack.vigilant_eureka.items.armor.WarpBoots;
import me.itstheholyblack.vigilant_eureka.network.PacketHandler;
import me.itstheholyblack.vigilant_eureka.world.WorldGenBismuth;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.ListIterator;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        FMLLog.log.info("Registering Vigilant Eureka event handler.");
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        FMLLog.log.info("Registering Vigilant Eureka networking.");
        PacketHandler.registerMessages(Reference.MOD_ID);
    }

    /**
     * Init method.
     * Crazy one-liner courtesy of Paul Fulham.
     *
     * @author Paul Fulham
     */
    public void init(FMLInitializationEvent e) {
        Minecraft.getMinecraft().getRenderManager().getSkinMap().values().forEach(r -> {
            List<LayerRenderer<EntityLivingBase>> layers =
                    ReflectionHelper.getPrivateValue(RenderLivingBase.class, r, "field_177097_h", "layerRenderers");
            ListIterator<LayerRenderer<EntityLivingBase>> iter = layers.listIterator();
            while (iter.hasNext()) {
                LayerRenderer<EntityLivingBase> layer = iter.next();
                if (layer instanceof LayerBipedArmor) {
                    iter.set(new CustomBipedArmor((LayerBipedArmor) layer));
                }
            }
        });
        WorldGenBismuth worldGenBismuth = new WorldGenBismuth();
        GameRegistry.registerWorldGenerator(worldGenBismuth, 100);
    }

    public void postInit(FMLPostInitializationEvent e) {
        OreDictionary.registerOre("oreBismuth", ModBlocks.bismuthOre);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BasicBlock());
        event.getRegistry().register(new MovingCastleDoor());
        event.getRegistry().register(new BlockBismuthOre());
        event.getRegistry().register(new BlockLeyLine());
        GameRegistry.registerTileEntity(MovingCastleDoorTile.class, Reference.MOD_ID + ":castledoor");
        GameRegistry.registerTileEntity(LeyLineTile.class, Reference.MOD_KEY + "leytile");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry()
                .register(new ItemBlock(ModBlocks.firstBlock).setRegistryName(ModBlocks.firstBlock.getRegistryName()));
        event.getRegistry()
                .register(new ItemBlock(ModBlocks.movingdoor).setRegistryName(ModBlocks.movingdoor.getRegistryName()).setMaxStackSize(16)); // shoot me
        event.getRegistry().register(new ItemBlock(ModBlocks.bismuthOre).setRegistryName(ModBlocks.bismuthOre.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.leyLine).setRegistryName(ModBlocks.leyLine.getRegistryName()));
        event.getRegistry().register(new DimKey());
        event.getRegistry().register(new WarpBoots());
        event.getRegistry().register(new InvisCap());
        event.getRegistry().register(new ItemBismite());
        event.getRegistry().register(new ItemLeyKey());
        Items.FIREWORKS.setCreativeTab(CreativeTabs.MISC); // please and thank you
    }
}
