package me.itstheholyblack.vigilant_eureka.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class BlockTileEntity<TE extends TileEntity> extends Block {
    protected String name;

    public BlockTileEntity(Material material, String name) {
        super(material);
    }

    /**
     * Returns the {@link Class} of this block's tile entity
     */
    public abstract Class<TE> getTileEntityClass();

    /**
     * Returns the {@link TE} instance for this specific block
     */
    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE) world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}