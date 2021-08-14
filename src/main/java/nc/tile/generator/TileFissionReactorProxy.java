package nc.tile.generator;

import cofh.api.energy.IEnergyHandler;
import nc.block.NCBlocks;
import nc.tile.machine.TileInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Arrays;

public class TileFissionReactorProxy extends TileEntity implements IEnergyHandler, ISidedInventory, IFluidHandler {

//    block meta data:
//          north:2 -> 3
//          easth:3 -> 4
//          south:0 -> 2
//           west:1 -> 5


    public TileInventory getRealTileEntity() {
        return realTileEntity;
    }

    private TileInventory realTileEntity;
    private TileFissionReactor realTileEntityFissionReactor;
    private TileFissionReactorSteam realTileEntityFissionReactorSteam;

    public void setRealTileEntity(TileInventory realTileEntity) {
        if (!this.worldObj.isRemote){
            this.worldObj.notifyBlocksOfNeighborChange(this.xCoord, this.yCoord,this.zCoord, this.getBlockType());
        }

        this.realTileEntity = realTileEntity;
        if(this.realTileEntity instanceof TileFissionReactor){
            this.realTileEntityFissionReactor = (TileFissionReactor) this.realTileEntity;
        }else if(this.realTileEntity instanceof TileFissionReactorSteam){
            System.out.println("setting up steam");
            this.realTileEntityFissionReactorSteam = (TileFissionReactorSteam) this.realTileEntity;
        }
    }


    public TileFissionReactorProxy() {

    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        if (realTileEntityFissionReactor != null) {
            return realTileEntityFissionReactor.extractEnergy(from, maxExtract, simulate);
        }
        return 0;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        if (realTileEntityFissionReactor != null) {
            return realTileEntityFissionReactor.getEnergyStored(from);
        }
        return 0;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        if (realTileEntityFissionReactor != null) {
            return realTileEntityFissionReactor.getEnergyStored(from);
        }
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        if (realTileEntityFissionReactor != null) {
            return realTileEntityFissionReactor.canConnectEnergy(from);
        }
        return false;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        if (realTileEntity != null) {
            return realTileEntity.getAccessibleSlotsFromSide(p_94128_1_);
        }
        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        if (realTileEntity != null) {
            return realTileEntity.canInsertItem(p_102007_1_, p_102007_2_, p_102007_3_);
        }
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        if (realTileEntity != null) {
            return realTileEntity.canExtractItem(p_102008_1_, p_102008_2_, p_102008_3_);
        }
        return false;
    }

    @Override
    public int getSizeInventory() {
        if (realTileEntity != null) {
            return realTileEntity.getSizeInventory();
        }
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        if (realTileEntity != null) {
            return realTileEntity.getStackInSlot(p_70301_1_);
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        if (realTileEntity != null) {
            return realTileEntity.decrStackSize(p_70298_1_, p_70298_2_);
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        if (realTileEntity != null) {
            return realTileEntity.getStackInSlotOnClosing(p_70304_1_);
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        if (realTileEntity != null) {
            realTileEntity.setInventorySlotContents(p_70299_1_, p_70299_2_);
        }
    }

    @Override
    public String getInventoryName() {
        if (realTileEntity != null) {
            return realTileEntity.getInventoryName();
        }
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        if (realTileEntity != null) {
            return realTileEntity.hasCustomInventoryName();
        }
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        if (realTileEntity != null) {
            return realTileEntity.getInventoryStackLimit();
        }
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        if (realTileEntity != null) {
            return realTileEntity.isUseableByPlayer(p_70300_1_);
        }
        return false;
    }

    @Override
    public void openInventory() {
        if (realTileEntity != null) {
            realTileEntity.openInventory();
        }
    }

    @Override
    public void closeInventory() {
        if (realTileEntity != null) {
            realTileEntity.closeInventory();
        }
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        if (realTileEntity != null) {
            return realTileEntity.isItemValidForSlot(p_94041_1_, p_94041_2_);
        }
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.fill(from, resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.drain(from, resource, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.drain(from, maxDrain, doDrain);
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.canFill(from, fluid);
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.canDrain(from, fluid);
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (realTileEntityFissionReactorSteam != null){
            return realTileEntityFissionReactorSteam.getTankInfo(from);
        }
        return new FluidTankInfo[0];
    }
}
