package me.itstheholyblack.vigilant_eureka.client.renderer;

import me.itstheholyblack.vigilant_eureka.items.ModItems;
import me.itstheholyblack.vigilant_eureka.util.NBTUtil;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * Class to conditionally render armor, allowing full invisibility. Code concept by Paul Fulham, modified by Edwan Vi into a working state.
 *
 * @author Paul Fulham
 * @author Edwan Vi
 */
@SideOnly(Side.CLIENT)
public class CustomBipedArmor implements LayerRenderer<EntityLivingBase> {

    private LayerBipedArmor layer;

    public CustomBipedArmor(LayerBipedArmor l) {
        this.layer = l;
    }

    @Override
    public void doRenderLayer(@Nonnull EntityLivingBase entity, float limbSwing, float limbSwingAmount, float delta, float age, float yaw, float pitch, float scale) {
        boolean visible = !entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem().equals(ModItems.invisCap);
        if (entity instanceof EntityPlayer) {
            visible = visible && !NBTUtil.getPlayerPersist((EntityPlayer) entity).getBoolean("metaphysical_high_ground");
        }
        if (visible) {
            layer.doRenderLayer(entity, limbSwing, limbSwingAmount, delta, age, yaw, pitch, scale);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return layer.shouldCombineTextures();
    }
}
