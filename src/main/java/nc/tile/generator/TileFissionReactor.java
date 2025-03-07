package nc.tile.generator;

import cofh.api.energy.IEnergyReceiver;
import nc.NuclearCraft;
import nc.block.NCBlocks;
import nc.block.generator.BlockFissionReactor;
import nc.handler.BombType;
import nc.handler.EntityBomb;
import nc.handler.NCExplosion;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileFissionReactor extends TileGeneratorInventory {

//    block meta data:
//          north:2 -> 3
//          easth:3 -> 4
//          south:0 -> 2
//           west:1 -> 5

    private int tickCount = 0;
    public int complete;

    public int x0 = 0;
    public int y0 = 0;
    public int z0 = 0;
    public int x1 = 0;
    public int y1 = 0;
    public int z1 = 0;
    public int lx = 0;
    public int ly = 0;
    public int lz = 0;

    public int off = 0;

    public int E;
    public int H;
    public int MinusH;
    public int EReal;
    public int HReal;
    public int HCooling;
    public int FReal;
    public int fueltime;
    public int fueltype;
    public int heat;
    public int efficiency;
    public int numberOfCells;
    private static double pMult = NuclearCraft.fissionRF;
    private static double hMult = NuclearCraft.fissionHeat;
    public String typeoffuel = StatCollector.translateToLocal("gui.noFuel");
    public int MBNumber;
    public String problem = StatCollector.translateToLocal("gui.casingIncomplete");
    public String extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete");

    private List<TileFissionReactorProxy> proxies = new ArrayList<>();

    private final Block[] validCasing = {NCBlocks.reactorBlock, NCBlocks.fissionReactorGraphiteActive, NCBlocks.fissionReactorGraphiteIdle, NCBlocks.fissonReactorProxy};

    public TileFissionReactor() {
        super("fissionReactor", 25000000, 2);
    }

    public static String[] cellTypes = {
            "LEU235Cell", "HEU235Cell",
            "LEP239Cell", "HEP239Cell",
            "MOX239Cell",
            "TBUCell",
            "LEU233Cell", "HEU233Cell",
            "LEP241Cell", "HEP241Cell",
            "MOX241Cell",

            "LEU235CellOxide", "HEU235CellOxide",
            "LEP239CellOxide", "HEP239CellOxide",
            "LEU233CellOxide", "HEU233CellOxide",
            "LEP241CellOxide", "HEP241CellOxide",

            "LEN236Cell", "HEN236Cell",
            "LEA242Cell", "HEA242Cell",
            "LEC243Cell", "HEC243Cell",
            "LEC245Cell", "HEC245Cell",
            "LEC247Cell", "HEC247Cell",

            "LEN236CellOxide", "HEN236CellOxide",
            "LEA242CellOxide", "HEA242CellOxide",
            "LEC243CellOxide", "HEC243CellOxide",
            "LEC245CellOxide", "HEC245CellOxide",
            "LEC247CellOxide", "HEC247CellOxide",
            "TBUCellOxide"
    };

    public static String[] fuelTypes = {
            "LEU", "HEU",
            "LEP", "HEP",
            "MOX",
            "TBU",
            "LEU", "HEU",
            "LEP", "HEP",
            "MOX",

            "LEU-Ox", "HEU-Ox",
            "LEP-Ox", "HEP-Ox",
            "LEU-Ox", "HEU-Ox",
            "LEP-Ox", "HEP-Ox",

            "LEN", "HEN",
            "LEA", "HEA",
            "LEC", "HEC",
            "LEC", "HEC",
            "LEC", "HEC",

            "LEN-Ox", "HEN-Ox",
            "LEA-Ox", "HEA-Ox",
            "LEC-Ox", "HEC-Ox",
            "LEC-Ox", "HEC-Ox",
            "LEC-Ox", "HEC-Ox",
            "TBU-Ox"
    };

    public void updateEntity() {
        super.updateEntity();
        checkStructure();
        if (!worldObj.isRemote) {
            product();
            fuel();
            energy();
            overheat();
            addEnergy();
            addEnergyFromProxies();
        }
        typeoffuelx();
        if (flag != flag1) {
            flag1 = flag;
            BlockFissionReactor.updateBlockState(flag, worldObj, xCoord, yCoord, zCoord);
        }
        markDirty();
        if (fueltime < 0) fueltime = 0;
    }

    private void addEnergyFromProxies() {
        for (TileFissionReactorProxy proxy: proxies) {
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {

                int tileCoordX = proxy.xCoord + side.offsetX;
                int tileCoordY = proxy.yCoord + side.offsetY;
                int tileCoordZ = proxy.zCoord + side.offsetZ;

                if (worldObj.blockExists(tileCoordX, tileCoordY, tileCoordZ)) {
                    TileEntity tile = worldObj.getTileEntity(tileCoordX, tileCoordY, tileCoordZ);
                    if (tile instanceof IEnergyReceiver) {
                        storage.extractEnergy(((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(), storage.extractEnergy(storage.getMaxEnergyStored(), true), false), false);
                    }
                }
            }
        }
    }

    public void overheat() {
        if (heat >= 1000000) {

            if (NuclearCraft.nuclearMeltdowns) {
                if (getBlockMetadata() == 4)
                    NCExplosion.createExplosion(new EntityBomb(worldObj).setType(BombType.BOMB_STANDARD), worldObj, xCoord + ((x0 + x1) / 2), yCoord + ((y0 + y1) / 2), zCoord + ((z0 + z1) / 2), 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, true);
                else if (getBlockMetadata() == 2)
                    NCExplosion.createExplosion(new EntityBomb(worldObj).setType(BombType.BOMB_STANDARD), worldObj, xCoord - ((z0 + z1) / 2), yCoord + ((y0 + y1) / 2), zCoord + ((x0 + x1) / 2), 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, true);
                else if (getBlockMetadata() == 5)
                    NCExplosion.createExplosion(new EntityBomb(worldObj).setType(BombType.BOMB_STANDARD), worldObj, xCoord - ((x0 + x1) / 2), yCoord + ((y0 + y1) / 2), zCoord - ((z0 + z1) / 2), 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, true);
                else if (getBlockMetadata() == 3)
                    NCExplosion.createExplosion(new EntityBomb(worldObj).setType(BombType.BOMB_STANDARD), worldObj, xCoord + ((z0 + z1) / 2), yCoord + ((y0 + y1) / 2), zCoord - ((x0 + x1) / 2), 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, 0.01F * (lx + ly + lz) * NuclearCraft.explosionRadius, true);
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);
            } else heat = 1000000;
        }
    }

    public void typeoffuelx() {
        for (int i = 0; i < fuelTypes.length; i++) {
            if (fueltype == i + 1) {
                typeoffuel = StatCollector.translateToLocal("gui." + fuelTypes[i]);
                return;
            }
        }
        typeoffuel = StatCollector.translateToLocal("gui.noFuel");
    }

    private void energy() {
        double energyThisTick = 0;
        double fuelThisTick = 0;
        double heatThisTick = 0;
        double coolerHeatThisTick = 0;
        double fakeEnergyThisTick = 0;
        double fakeHeatThisTick = 0;
        double numberOfCells = 0;
        double extraCells = 0;
        double adj1 = 0;
        double adj2 = 0;
        double adj3 = 0;
        double adj4 = 0;
        double adj5 = 0;
        double adj6 = 0;
        double baseRF = 0;
        double baseFuel = 0;
        double baseHeat = 0;

        if (doesFuelMatchDepleted() && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && fueltime > 0 && fueltype != 0 && complete == 1) {
            off = 0;
            flag = true;
        } else if (doesFuelMatchDepleted() && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && fueltime > 0 && fueltype != 0 && complete == 1) {
            off = 1;
            flag = false;
        } else {
            off = 0;
            flag = false;
        }

        if (tickCount >= NuclearCraft.fissionUpdateRate) {
            if (complete == 1) {
                for (int z = z0 + 1; z <= z1 - 1; z++) {
                    for (int x = x0 + 1; x <= x1 - 1; x++) {
                        for (int y = y0 + 1; y <= y1 - 1; y++) {
                            if (find(NCBlocks.cellBlock, x, y, z)) {
                                extraCells = 0;
                                if (find(NCBlocks.cellBlock, x + 1, y, z) || (find(NCBlocks.graphiteBlock, x + 1, y, z) && find(NCBlocks.cellBlock, x + 2, y, z)))
                                    extraCells += 1;
                                if (find(NCBlocks.cellBlock, x - 1, y, z) || (find(NCBlocks.graphiteBlock, x - 1, y, z) && find(NCBlocks.cellBlock, x - 2, y, z)))
                                    extraCells += 1;
                                if (find(NCBlocks.cellBlock, x, y + 1, z) || (find(NCBlocks.graphiteBlock, x, y + 1, z) && find(NCBlocks.cellBlock, x, y + 2, z)))
                                    extraCells += 1;
                                if (find(NCBlocks.cellBlock, x, y - 1, z) || (find(NCBlocks.graphiteBlock, x, y - 1, z) && find(NCBlocks.cellBlock, x, y - 2, z)))
                                    extraCells += 1;
                                if (find(NCBlocks.cellBlock, x, y, z + 1) || (find(NCBlocks.graphiteBlock, x, y, z + 1) && find(NCBlocks.cellBlock, x, y, z + 2)))
                                    extraCells += 1;
                                if (find(NCBlocks.cellBlock, x, y, z - 1) || (find(NCBlocks.graphiteBlock, x, y, z - 1) && find(NCBlocks.cellBlock, x, y, z - 2)))
                                    extraCells += 1;

                                if (extraCells == 0) numberOfCells += 1;
                                else if (extraCells == 1) adj1 += 1;
                                else if (extraCells == 2) adj2 += 1;
                                else if (extraCells == 3) adj3 += 1;
                                else if (extraCells == 4) adj4 += 1;
                                else if (extraCells == 5) adj5 += 1;
                                else if (extraCells == 6) adj6 += 1;
                            }
                        }
                    }
                }
            }

            if (doesFuelMatchDepleted() && worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && fueltime > 0 && fueltype != 0 && complete == 1) {

                flag = true;
                off = 0;

                //LEU
                if (fueltype == 1 || fueltype == 7) {
                    baseRF = NuclearCraft.baseRFLEU;
                    baseFuel = NuclearCraft.baseFuelLEU;
                    baseHeat = NuclearCraft.baseHeatLEU;
                }

                //HEU
                if (fueltype == 2 || fueltype == 8) {
                    baseRF = NuclearCraft.baseRFHEU;
                    baseFuel = NuclearCraft.baseFuelHEU;
                    baseHeat = NuclearCraft.baseHeatHEU;
                }

                //LEP
                if (fueltype == 3 || fueltype == 9) {
                    baseRF = NuclearCraft.baseRFLEP;
                    baseFuel = NuclearCraft.baseFuelLEP;
                    baseHeat = NuclearCraft.baseHeatLEP;
                }

                //HEP
                if (fueltype == 4 || fueltype == 10) {
                    baseRF = NuclearCraft.baseRFHEP;
                    baseFuel = NuclearCraft.baseFuelHEP;
                    baseHeat = NuclearCraft.baseHeatHEP;
                }

                //MOX
                if (fueltype == 5 || fueltype == 11) {
                    baseRF = NuclearCraft.baseRFMOX;
                    baseFuel = NuclearCraft.baseFuelMOX;
                    baseHeat = NuclearCraft.baseHeatMOX;
                }

                //TBU
                if (fueltype == 6) {
                    baseRF = NuclearCraft.baseRFTBU;
                    baseFuel = NuclearCraft.baseFuelTBU;
                    baseHeat = NuclearCraft.baseHeatTBU;
                }

                //LEU-Ox
                if (fueltype == 12 || fueltype == 16) {
                    baseRF = NuclearCraft.baseRFLEUOx;
                    baseFuel = NuclearCraft.baseFuelLEUOx;
                    baseHeat = NuclearCraft.baseHeatLEUOx;
                }

                //HEU-Ox
                if (fueltype == 13 || fueltype == 17) {
                    baseRF = NuclearCraft.baseRFHEUOx;
                    baseFuel = NuclearCraft.baseFuelHEUOx;
                    baseHeat = NuclearCraft.baseHeatHEUOx;
                }

                //LEP-Ox
                if (fueltype == 14 || fueltype == 18) {
                    baseRF = NuclearCraft.baseRFLEPOx;
                    baseFuel = NuclearCraft.baseFuelLEPOx;
                    baseHeat = NuclearCraft.baseHeatLEPOx;
                }

                //HEP-Ox
                if (fueltype == 15 || fueltype == 19) {
                    baseRF = NuclearCraft.baseRFHEPOx;
                    baseFuel = NuclearCraft.baseFuelHEPOx;
                    baseHeat = NuclearCraft.baseHeatHEPOx;
                }

                //LEN
                if (fueltype == 20) {
                    baseRF = NuclearCraft.baseRFLEN;
                    baseFuel = NuclearCraft.baseFuelLEN;
                    baseHeat = NuclearCraft.baseHeatLEN;
                }

                //HEN
                if (fueltype == 21) {
                    baseRF = NuclearCraft.baseRFHEN;
                    baseFuel = NuclearCraft.baseFuelHEN;
                    baseHeat = NuclearCraft.baseHeatHEN;
                }

                //LEA
                if (fueltype == 22) {
                    baseRF = NuclearCraft.baseRFLEA;
                    baseFuel = NuclearCraft.baseFuelLEA;
                    baseHeat = NuclearCraft.baseHeatLEA;
                }

                //HEA
                if (fueltype == 23) {
                    baseRF = NuclearCraft.baseRFHEA;
                    baseFuel = NuclearCraft.baseFuelHEA;
                    baseHeat = NuclearCraft.baseHeatHEA;
                }

                //LEC
                if (fueltype == 24 || fueltype == 26 || fueltype == 28) {
                    baseRF = NuclearCraft.baseRFLEC;
                    baseFuel = NuclearCraft.baseFuelLEC;
                    baseHeat = NuclearCraft.baseHeatLEC;
                }

                //HEC
                if (fueltype == 25 || fueltype == 27 || fueltype == 29) {
                    baseRF = NuclearCraft.baseRFHEC;
                    baseFuel = NuclearCraft.baseFuelHEC;
                    baseHeat = NuclearCraft.baseHeatHEC;
                }

                //LEN-Ox
                if (fueltype == 30) {
                    baseRF = NuclearCraft.baseRFLENOx;
                    baseFuel = NuclearCraft.baseFuelLENOx;
                    baseHeat = NuclearCraft.baseHeatLENOx;
                }

                //HEN-Ox
                if (fueltype == 31) {
                    baseRF = NuclearCraft.baseRFHENOx;
                    baseFuel = NuclearCraft.baseFuelHENOx;
                    baseHeat = NuclearCraft.baseHeatHENOx;
                }

                //LEA-Ox
                if (fueltype == 32) {
                    baseRF = NuclearCraft.baseRFLEAOx;
                    baseFuel = NuclearCraft.baseFuelLEAOx;
                    baseHeat = NuclearCraft.baseHeatLEAOx;
                }

                //HEA-Ox
                if (fueltype == 33) {
                    baseRF = NuclearCraft.baseRFHEAOx;
                    baseFuel = NuclearCraft.baseFuelHEAOx;
                    baseHeat = NuclearCraft.baseHeatHEAOx;
                }

                //LEC-Ox
                if (fueltype == 34 || fueltype == 36 || fueltype == 38) {
                    baseRF = NuclearCraft.baseRFLECOx;
                    baseFuel = NuclearCraft.baseFuelLECOx;
                    baseHeat = NuclearCraft.baseHeatLECOx;
                }

                //HEC-Ox
                if (fueltype == 35 || fueltype == 37 || fueltype == 39) {
                    baseRF = NuclearCraft.baseRFHECOx;
                    baseFuel = NuclearCraft.baseFuelHECOx;
                    baseHeat = NuclearCraft.baseHeatHECOx;
                }

                //TBU-Ox
                if (fueltype == 40) {
                    baseRF = NuclearCraft.baseRFTBUOx;
                    baseFuel = NuclearCraft.baseFuelTBUOx;
                    baseHeat = NuclearCraft.baseHeatTBUOx;
                }

                energyThisTick += baseRF * (10000 * pMult + heat) * (numberOfCells + 2 * adj1 + 3 * adj2 + 4 * adj3 + 5 * adj4 + 6 * adj5 + 7 * adj6) /* *Math.cbrt((lx - 2)*(ly - 2)*(lz - 2)) */ / 1000000;
                heatThisTick += baseHeat * (numberOfCells + 3 * adj1 + 6 * adj2 + 10 * adj3 + 15 * adj4 + 21 * adj5 + 28 * adj6);
                fuelThisTick += (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) * baseFuel / NuclearCraft.fissionEfficiency;

                for (int z = z0 + 1; z <= z1 - 1; z++) {
                    for (int x = x0 + 1; x <= x1 - 1; x++) {
                        for (int y = y0 + 1; y <= y1 - 1; y++) {
                            if (find(NCBlocks.graphiteBlock, x, y, z)) {
                                heatThisTick += (hMult / 100) * baseRF * (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) / 5;
                                if (surroundOr(NCBlocks.cellBlock, x, y, z))
                                    energyThisTick += (10000 * pMult + heat) * baseRF * (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) / 10000000;
                            }
                            if (find(NCBlocks.speedBlock, x, y, z) && (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) > 0) {
                                if (lx - 2 + ly - 2 + lz - 2 > 0)
                                    fuelThisTick += (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) * baseFuel / (NuclearCraft.fissionEfficiency * (lx - 2 + ly - 2 + lz - 2));
                            }
                        }
                    }
                }

            } else if (doesFuelMatchDepleted() && !worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord) && fueltime > 0 && fueltype != 0 && complete == 1) {

                off = 1;

                //LEU
                if (fueltype == 1 || fueltype == 7) {
                    baseRF = NuclearCraft.baseRFLEU;
                    baseHeat = NuclearCraft.baseHeatLEU;
                }

                //HEU
                if (fueltype == 2 || fueltype == 8) {
                    baseRF = NuclearCraft.baseRFHEU;
                    baseHeat = NuclearCraft.baseHeatHEU;
                }

                //LEP
                if (fueltype == 3 || fueltype == 9) {
                    baseRF = NuclearCraft.baseRFLEP;
                    baseHeat = NuclearCraft.baseHeatLEP;
                }

                //HEP
                if (fueltype == 4 || fueltype == 10) {
                    baseRF = NuclearCraft.baseRFHEP;
                    baseHeat = NuclearCraft.baseHeatHEP;
                }

                //MOX
                if (fueltype == 5 || fueltype == 11) {
                    baseRF = NuclearCraft.baseRFMOX;
                    baseHeat = NuclearCraft.baseHeatMOX;
                }

                //TBU
                if (fueltype == 6) {
                    baseRF = NuclearCraft.baseRFTBU;
                    baseHeat = NuclearCraft.baseHeatTBU;
                }

                //LEU-Ox
                if (fueltype == 12 || fueltype == 16) {
                    baseRF = NuclearCraft.baseRFLEUOx;
                    baseHeat = NuclearCraft.baseHeatLEUOx;
                }

                //HEU-Ox
                if (fueltype == 13 || fueltype == 17) {
                    baseRF = NuclearCraft.baseRFHEUOx;
                    baseHeat = NuclearCraft.baseHeatHEUOx;
                }

                //LEP-Ox
                if (fueltype == 14 || fueltype == 18) {
                    baseRF = NuclearCraft.baseRFLEPOx;
                    baseHeat = NuclearCraft.baseHeatLEPOx;
                }

                //HEP-Ox
                if (fueltype == 15 || fueltype == 19) {
                    baseRF = NuclearCraft.baseRFHEPOx;
                    baseHeat = NuclearCraft.baseHeatHEPOx;
                }

                //LEN
                if (fueltype == 20) {
                    baseRF = NuclearCraft.baseRFLEN;
                    baseHeat = NuclearCraft.baseHeatLEN;
                }

                //HEN
                if (fueltype == 21) {
                    baseRF = NuclearCraft.baseRFHEN;
                    baseHeat = NuclearCraft.baseHeatHEN;
                }

                //LEA
                if (fueltype == 22) {
                    baseRF = NuclearCraft.baseRFLEA;
                    baseHeat = NuclearCraft.baseHeatLEA;
                }

                //HEA
                if (fueltype == 23) {
                    baseRF = NuclearCraft.baseRFHEA;
                    baseHeat = NuclearCraft.baseHeatHEA;
                }

                //LEC
                if (fueltype == 24 || fueltype == 26 || fueltype == 28) {
                    baseRF = NuclearCraft.baseRFLEC;
                    baseHeat = NuclearCraft.baseHeatLEC;
                }

                //HEC
                if (fueltype == 25 || fueltype == 27 || fueltype == 29) {
                    baseRF = NuclearCraft.baseRFHEC;
                    baseHeat = NuclearCraft.baseHeatHEC;
                }

                //LEN-Ox
                if (fueltype == 30) {
                    baseRF = NuclearCraft.baseRFLENOx;
                    baseHeat = NuclearCraft.baseHeatLENOx;
                }

                //HEN-Ox
                if (fueltype == 31) {
                    baseRF = NuclearCraft.baseRFHENOx;
                    baseHeat = NuclearCraft.baseHeatHENOx;
                }

                //LEA-Ox
                if (fueltype == 32) {
                    baseRF = NuclearCraft.baseRFLEAOx;
                    baseHeat = NuclearCraft.baseHeatLEAOx;
                }

                //HEA-Ox
                if (fueltype == 33) {
                    baseRF = NuclearCraft.baseRFHEAOx;
                    baseHeat = NuclearCraft.baseHeatHEAOx;
                }

                //LEC-Ox
                if (fueltype == 34 || fueltype == 36 || fueltype == 38) {
                    baseRF = NuclearCraft.baseRFLECOx;
                    baseHeat = NuclearCraft.baseHeatLECOx;
                }

                //HEC-Ox
                if (fueltype == 35 || fueltype == 37 || fueltype == 39) {
                    baseRF = NuclearCraft.baseRFHECOx;
                    baseHeat = NuclearCraft.baseHeatHECOx;
                }

                //TBU-Ox
                if (fueltype == 40) {
                    baseRF = NuclearCraft.baseRFTBUOx;
                    baseHeat = NuclearCraft.baseHeatTBUOx;
                }

                fakeEnergyThisTick += baseRF * (10000 * pMult + heat) * (numberOfCells + 2 * adj1 + 3 * adj2 + 4 * adj3 + 5 * adj4 + 6 * adj5 + 7 * adj6) /* *Math.cbrt((lx - 2)*(ly - 2)*(lz - 2))*/ / 1000000;
                fakeHeatThisTick += baseHeat * (hMult / 100) * (numberOfCells + 3 * adj1 + 6 * adj2 + 10 * adj3 + 15 * adj4 + 21 * adj5 + 28 * adj6);

                for (int z = z0 + 1; z <= z1 - 1; z++) {
                    for (int x = x0 + 1; x <= x1 - 1; x++) {
                        for (int y = y0 + 1; y <= y1 - 1; y++) {
                            if (find(NCBlocks.graphiteBlock, x, y, z)) {
                                fakeHeatThisTick += (hMult / 100) * baseRF * (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) / 5;
                                if (surroundOr(NCBlocks.cellBlock, x, y, z))
                                    fakeEnergyThisTick += (10000 * pMult + heat) * baseRF * (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) / 10000000;
                            }
                        }
                    }
                }
                flag = false;
            } else {
                flag = false;
                off = 0;
            }

            if (complete == 1) {
                for (int z = z0 + 1; z <= z1 - 1; z++) {
                    for (int x = x0 + 1; x <= x1 - 1; x++) {
                        for (int y = y0 + 1; y <= y1 - 1; y++) {
                            if (find(NCBlocks.coolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.standardCool;
                                if (surroundOr(NCBlocks.coolerBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.standardCool;
                            }
                            if (find(NCBlocks.waterCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.waterCool;
                                if (surroundOr(NCBlocks.reactorBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.waterCool;
                            }
                            if (find(NCBlocks.cryotheumCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.cryotheumCool;
                                if (surroundNAnd(NCBlocks.cryotheumCoolerBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.cryotheumCool;
                            }
                            if (find(NCBlocks.redstoneCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.redstoneCool;
                                if (surroundOr(NCBlocks.cellBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.redstoneCool;
                            }
                            if (find(NCBlocks.enderiumCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.enderiumCool;
                                if (surroundOr(NCBlocks.graphiteBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.enderiumCool;
                            }
                            if (find(NCBlocks.glowstoneCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.glowstoneCool;
                                if (surroundAnd(NCBlocks.graphiteBlock, x, y, z))
                                    coolerHeatThisTick -= 3 * NuclearCraft.glowstoneCool;
                            }
                            if (find(NCBlocks.heliumCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.heliumCool;
                            }
                            if (find(NCBlocks.coolantCoolerBlock, x, y, z)) {
                                coolerHeatThisTick -= NuclearCraft.coolantCool;
                                if (surroundOr(NCBlocks.waterCoolerBlock, x, y, z))
                                    coolerHeatThisTick -= NuclearCraft.coolantCool;
                            }
                            if (find(Blocks.water, x, y, z)) coolerHeatThisTick -= 1;
                        }
                    }
                }
                if (lx - 2 + ly - 2 + lz - 2 <= 3) coolerHeatThisTick -= NuclearCraft.baseHeatTBU;
            }
            E = (int) (energyThisTick + fakeEnergyThisTick);
            EReal = (int) energyThisTick;

            FReal = (int) fuelThisTick;

            H = (int) (heatThisTick + fakeHeatThisTick + coolerHeatThisTick);
            MinusH = -H;
            HReal = (int) (heatThisTick + coolerHeatThisTick);
            HCooling = (int) coolerHeatThisTick;

            if (complete == 1)
                efficiency = (int) (100 * (energyThisTick + fakeEnergyThisTick) / (baseRF * (pMult / 100) * (numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6) /* *Math.cbrt((lx - 2)*(ly - 2)*(lz - 2)) */));
            else efficiency = 0;

            if (complete == 1) this.numberOfCells = (int) ((numberOfCells + adj1 + adj2 + adj3 + adj4 + adj5 + adj6));
            else this.numberOfCells = 0;

            tickCount = 0;
        } else tickCount++;

        if (EReal <= 0) flag = false;

        if (off == 0 && flag) storage.receiveEnergy((int) EReal, false);

        if (off == 0 && flag) fueltime -= FReal;

        if (off == 0 && flag) {
            if (heat + (int) HReal >= 0) {
                heat += (int) HReal;
            } else {
                heat = 0;
            }
        } else if (off == 1 && !flag) {
            if (heat + (int) HCooling >= 0) {
                heat += (int) HCooling;
            } else {
                heat = 0;
            }
        }

        if (fueltime < 0) fueltime = 0;
        if (fueltime == 0) E = 0;
    }

    public boolean findBasic(Block block, int x, int y, int z) {
        return find(block, x, y, z);
    }

    public boolean between(Block block, int x, int y, int z) {
        return ((findBasic(block, x + 1, y, z) && findBasic(block, x - 1, y, z)) || (findBasic(block, x, y + 1, z) && findBasic(block, x, y - 1, z)) || (findBasic(block, x, y, z + 1) && findBasic(block, x, y, z - 1)));
    }

    public boolean surroundOr(Block block, int x, int y, int z) {
        return (findBasic(block, x + 1, y, z) || findBasic(block, x - 1, y, z) || findBasic(block, x, y + 1, z) || findBasic(block, x, y - 1, z) || findBasic(block, x, y, z + 1) || findBasic(block, x, y, z - 1));
    }

    public boolean surroundOr(Block block, Block block2, int x, int y, int z) {
        return ((findBasic(block, x + 1, y, z) && findBasic(block2, x + 2, y, z)) || (findBasic(block, x - 1, y, z) && findBasic(block2, x - 2, y, z)) || (findBasic(block, x, y + 1, z) && findBasic(block2, x, y + 2, z)) || (findBasic(block, x, y - 1, z) && findBasic(block2, x, y - 2, z)) || (findBasic(block, x, y, z + 1) && findBasic(block2, x, y, z + 2)) || (findBasic(block, x, y, z - 1) && findBasic(block2, x, y, z - 2)));
    }

    public boolean surroundAnd(Block block, int x, int y, int z) {
        return (findBasic(block, x + 1, y, z) && findBasic(block, x - 1, y, z) && findBasic(block, x, y + 1, z) && findBasic(block, x, y - 1, z) && findBasic(block, x, y, z + 1) && findBasic(block, x, y, z - 1));
    }

    public boolean surroundNAnd(Block block, int x, int y, int z) {
        return (!findBasic(block, x + 1, y, z) && !findBasic(block, x - 1, y, z) && !findBasic(block, x, y + 1, z) && !findBasic(block, x, y - 1, z) && !findBasic(block, x, y, z + 1) && !findBasic(block, x, y, z - 1));
    }

    public boolean multiblockstring() {
        if (complete == 1) {
            return true;
        }
        return false;
    }

    private boolean doesFuelMatchDepleted() {
        if (slots[0] == null) return true;
        if (slots[1] != null) {
            int type = TileFissionReactor.setfueltype(slots[0]);
            if (OreDictionary.getOreIDs(slots[1]).length == 0 || OreDictionary.getOreIDs(slots[1]) == null)
                for (int id : OreDictionary.getOreIDs(slots[1])) {
                    if (OreDictionary.getOres("d" + cellTypes[type - 1]).get(0).getItem() == OreDictionary.getOres(OreDictionary.getOreName(id)).get(0).getItem() && OreDictionary.getOres("d" + cellTypes[type - 1]).get(0).getItemDamage() == OreDictionary.getOres(OreDictionary.getOreName(id)).get(0).getItemDamage() && slots[1].stackSize < 64) {
                        return true;
                    }
                }
            return false;
        }
        return true;
    }

    private void fuel() {
        ItemStack fuel = getStackInSlot(0);
        if (fuel != null && doesFuelMatchDepleted() && isFuel(fuel) && fueltime == 0 && fueltype == 0) {
            fueltime += fuelValue(fuel);
            fueltype = TileFissionReactor.setfueltype(fuel);
            slots[0].stackSize--;

            if (slots[0].stackSize <= 0) {
                slots[0] = null;
            }
            off = 1;
        }
    }

    private void product() {
        if (slots[1] == null && fueltime <= 0 && fueltype != 0) {
            for (int i = 0; i < cellTypes.length; i++) {
                if (fueltype == i + 1) {
                    slots[1] = new ItemStack(OreDictionary.getOres("d" + cellTypes[i]).get(0).getItem(), 1, OreDictionary.getOres("d" + cellTypes[i]).get(0).getItemDamage());
                    fueltype = 0;
                }
            }
            fueltime = 0;
        } else if (slots[1] != null && fueltime <= 0 && fueltype != 0) {
            for (int i = 0; i < cellTypes.length; i++) {
                for (int id : OreDictionary.getOreIDs(slots[1])) {
                    if (OreDictionary.getOres("d" + cellTypes[i]).get(0).getItem() == OreDictionary.getOres(OreDictionary.getOreName(id)).get(0).getItem() && OreDictionary.getOres("d" + cellTypes[i]).get(0).getItemDamage() == OreDictionary.getOres(OreDictionary.getOreName(id)).get(0).getItemDamage() && slots[1].stackSize < 64) {
                        if (fueltype == i + 1) {
                            slots[1].stackSize++;
                            fueltype = 0;
                        }
                    }
                }
            }
            fueltime = 0;
        }
    }

    public static boolean isFuel(ItemStack stack) {
        return fuelValue(stack) > 0 && setfueltype(stack) != 0;
    }

    public static int fuelValue(ItemStack stack) {
        if (stack == null) return 0;
        else {
            for (int i = 0; i < cellTypes.length; i++) {
                for (int id : OreDictionary.getOreIDs(stack)) {
                    if (OreDictionary.getOreID(cellTypes[i]) == id) return 10000000;
                }
            }
            return 0;
        }
    }

    public static int setfueltype(ItemStack stack) {
        if (stack == null) return 0;
        else {
            for (int i = 0; i < cellTypes.length; i++) {
                for (int id : OreDictionary.getOreIDs(stack)) {
                    if (OreDictionary.getOreID(cellTypes[i]) == id) return i + 1;
                }
            }
            return 0;
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        typeoffuel = nbt.getString("Typeoffuel");
        problem = nbt.getString("problem");
        extendedproblem = nbt.getString("extendedproblem");
        fueltime = nbt.getInteger("Fueltime");
        fueltype = nbt.getInteger("Fueltype");

        x0 = nbt.getInteger("x0");
        y0 = nbt.getInteger("y0");
        z0 = nbt.getInteger("z0");
        x1 = nbt.getInteger("x1");
        y1 = nbt.getInteger("y1");
        z1 = nbt.getInteger("z1");
        lx = nbt.getInteger("lx");
        ly = nbt.getInteger("ly");
        lz = nbt.getInteger("lz");

        E = nbt.getInteger("E");
        H = nbt.getInteger("H");
        MinusH = nbt.getInteger("MinusH");
        off = nbt.getInteger("off");
        MBNumber = nbt.getInteger("MBN");
        heat = nbt.getInteger("Heat");
        efficiency = nbt.getInteger("efficiency");
        numberOfCells = nbt.getInteger("numberOfCells");
        EReal = nbt.getInteger("EReal");
        HReal = nbt.getInteger("HReal");
        HCooling = nbt.getInteger("HCooling");
        FReal = nbt.getInteger("FReal");
        complete = nbt.getInteger("complete");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Fueltime", fueltime);
        nbt.setInteger("Fueltype", fueltype);

        nbt.setInteger("x0", x0);
        nbt.setInteger("y0", y0);
        nbt.setInteger("z0", z0);
        nbt.setInteger("x1", x1);
        nbt.setInteger("y1", y1);
        nbt.setInteger("z1", z1);
        nbt.setInteger("lx", lx);
        nbt.setInteger("ly", ly);
        nbt.setInteger("lz", lz);

        nbt.setInteger("E", E);
        nbt.setInteger("H", H);
        nbt.setInteger("MinusH", MinusH);
        nbt.setInteger("off", off);
        nbt.setInteger("MBN", MBNumber);
        nbt.setInteger("Heat", heat);
        nbt.setInteger("efficiency", efficiency);
        nbt.setInteger("numberOfCells", numberOfCells);
        nbt.setString("Typeoffuel", typeoffuel);
        nbt.setString("problem", problem);
        nbt.setString("extendedproblem", extendedproblem);
        nbt.setInteger("EReal", EReal);
        nbt.setInteger("HReal", HReal);
        nbt.setInteger("HCooling", HCooling);
        nbt.setInteger("FReal", FReal);
        nbt.setInteger("complete", complete);
    }

    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == 0) return isFuel(stack);
        else return false;
    }

    public boolean canExtractItem(int slot, ItemStack stack, int slots) {
        return slot == 1;
    }


    private int[] getRealBlock(int relativeX, int relativeY, int relativeZ){
        int xc = xCoord;
        int yc = yCoord + relativeY;
        int zc = zCoord;

        if (getBlockMetadata() == 4) return new int[]{xc + relativeX, yc, zc + relativeZ};
        else if (getBlockMetadata() == 2) return new int[]{xc - relativeZ, yc, zc + relativeX};
        else if (getBlockMetadata() == 5) return new int[]{xc - relativeX, yc, zc - relativeZ};
        else if (getBlockMetadata() == 3) return new int[]{xc + relativeZ, yc, zc - relativeX};
        return new int[]{0,0,0};
    }

    private String getRealBlockString(int relativeX, int relativeY, int relativeZ){
        int[] realcoords = getRealBlock(relativeX, relativeY, relativeZ);
        return "(" + realcoords[0] + "," + realcoords[1] + "," + realcoords[2] + ")";
    }




    private boolean find(Block[] blocks, int x, int y, int z){

        int xc = xCoord;
        int yc = yCoord + y;
        int zc = zCoord;
        Block checkBlock;

        if (getBlockMetadata() == 4) checkBlock = worldObj.getBlock(xc + x, yc, zc + z);
        else if (getBlockMetadata() == 2) checkBlock = worldObj.getBlock(xc - z, yc, zc + x);
        else if (getBlockMetadata() == 5) checkBlock = worldObj.getBlock(xc - x, yc, zc - z);
        else if (getBlockMetadata() == 3) checkBlock = worldObj.getBlock(xc + z, yc, zc - x);
        else return false;

        return Arrays.asList(blocks).contains(checkBlock);
    }


    /**
     * get block at coordinate offset from controller
     * positive x is behind
     * positive z  is to the right
     *
     * @param block
     * @param x
     * @param y
     * @param z
     * @return
     */
    private boolean find(Block block, int x, int y, int z) {
        int xc = xCoord;
        int yc = yCoord + y;
        int zc = zCoord;

        if (getBlockMetadata() == 4) return (worldObj.getBlock(xc + x, yc, zc + z) == block);
        else if (getBlockMetadata() == 2) return (worldObj.getBlock(xc - z, yc, zc + x) == block);
        else if (getBlockMetadata() == 5) return (worldObj.getBlock(xc - x, yc, zc - z) == block);
        else if (getBlockMetadata() == 3) return (worldObj.getBlock(xc + z, yc, zc - x) == block);
        else return false;
    }

    private boolean find(Block block, Block block2, Block block3, Block block4, int x, int y, int z) {
        int xc = xCoord;
        int yc = yCoord + y;
        int zc = zCoord;

        if (getBlockMetadata() == 4)
            return (worldObj.getBlock(xc + x, yc, zc + z) == block || worldObj.getBlock(xc + x, yc, zc + z) == block2 || worldObj.getBlock(xc + x, yc, zc + z) == block3 || worldObj.getBlock(xc + x, yc, zc + z) == block4);
        else if (getBlockMetadata() == 2)
            return (worldObj.getBlock(xc - z, yc, zc + x) == block || worldObj.getBlock(xc - z, yc, zc + x) == block2 || worldObj.getBlock(xc - z, yc, zc + x) == block3 || worldObj.getBlock(xc - z, yc, zc + x) == block4);
        else if (getBlockMetadata() == 5)
            return (worldObj.getBlock(xc - x, yc, zc - z) == block || worldObj.getBlock(xc - x, yc, zc - z) == block2 || worldObj.getBlock(xc - x, yc, zc - z) == block3 || worldObj.getBlock(xc - x, yc, zc - z) == block4);
        else if (getBlockMetadata() == 3)
            return (worldObj.getBlock(xc + z, yc, zc - x) == block || worldObj.getBlock(xc + z, yc, zc - x) == block2 || worldObj.getBlock(xc + z, yc, zc - x) == block3 || worldObj.getBlock(xc + z, yc, zc - x) == block4);
        else return false;
    }

    private boolean find(Block block, Block block2, Block block3, Block block4, Block block5, int x, int y, int z) {
        int xc = xCoord;
        int yc = yCoord + y;
        int zc = zCoord;

        if (getBlockMetadata() == 4)
            return (worldObj.getBlock(xc + x, yc, zc + z) == block || worldObj.getBlock(xc + x, yc, zc + z) == block2 || worldObj.getBlock(xc + x, yc, zc + z) == block3 || worldObj.getBlock(xc + x, yc, zc + z) == block4 || worldObj.getBlock(xc + x, yc, zc + z) == block5);
        else if (getBlockMetadata() == 2)
            return (worldObj.getBlock(xc - z, yc, zc + x) == block || worldObj.getBlock(xc - z, yc, zc + x) == block2 || worldObj.getBlock(xc - z, yc, zc + x) == block3 || worldObj.getBlock(xc - z, yc, zc + x) == block4 || worldObj.getBlock(xc - z, yc, zc + x) == block5);
        else if (getBlockMetadata() == 5)
            return (worldObj.getBlock(xc - x, yc, zc - z) == block || worldObj.getBlock(xc - x, yc, zc - z) == block2 || worldObj.getBlock(xc - x, yc, zc - z) == block3 || worldObj.getBlock(xc - x, yc, zc - z) == block4 || worldObj.getBlock(xc - x, yc, zc - z) == block5);
        else if (getBlockMetadata() == 3)
            return (worldObj.getBlock(xc + z, yc, zc - x) == block || worldObj.getBlock(xc + z, yc, zc - x) == block2 || worldObj.getBlock(xc + z, yc, zc - x) == block3 || worldObj.getBlock(xc + z, yc, zc - x) == block4 || worldObj.getBlock(xc + z, yc, zc - x) == block5);
        else return false;
    }

    public void resetProxies(){
        for (TileFissionReactorProxy proxy: proxies) {
            proxy.setRealTileEntity(null);
        }
    }

    private void addProxy(int x, int y, int z){
        int[] realCoords = this.getRealBlock(x,y,z);
        TileEntity tileEntity  = worldObj.getTileEntity(realCoords[0], realCoords[1], realCoords[2]);
        if(tileEntity instanceof TileFissionReactorProxy){
            TileFissionReactorProxy tileFissionReactorProxy =  (TileFissionReactorProxy) tileEntity;
            tileFissionReactorProxy.setRealTileEntity(this);
            proxies.add(tileFissionReactorProxy);
        }
    }

    private boolean checkStructure() {
        if (tickCount >= NuclearCraft.fissionUpdateRate) {
            int max_length = NuclearCraft.fissionMaxLength + 2;
            Block reactorBlock = NCBlocks.reactorBlock;
            Block graphiteIdle = NCBlocks.fissionReactorGraphiteIdle;
            Block graphiteActive = NCBlocks.fissionReactorGraphiteActive;
            Block steamIdle = NCBlocks.fissionReactorSteamIdle;
            Block reactorSteamActive = NCBlocks.fissionReactorSteamActive;
            Block energyProxy = NCBlocks.fissonReactorProxy;
            boolean casintComplete = false;
            int rz = 0;
            int z0 = 0;
            int x0 = 0;
            int y0 = 0;
            int z1 = 0;
            int x1 = 0;
            int y1 = 0;
//
            for (int z = 0; z <= max_length; z++) {
                //check if controller in valid locaiton
                if (
                        (
                                find(reactorBlock, 0, 1, 0) ||
                                        find(reactorBlock, 0, -1, 0)                            // controller has reactor block above or below (on a face)
                        )
                                ||                                                                     // or
                                (
                                        (find(reactorBlock, 1, 1, 0) ||
                                                find(reactorBlock, 1, -1, 0)) &&               // controller has reactor block in other position (not clear)
                                                find(reactorBlock, 1, 0, 0)
                                )
                                ||                                                                      // or
                                (
                                        (find(reactorBlock, 1, 1, 0) &&
                                                !find(reactorBlock, 1, -1, 0))                  // maby in the corner
                                                && !find(reactorBlock, 1, 0, 0))
                                ||                                                                       // or
                                (
                                        (!find(reactorBlock, 1, 1, 0) &&
                                                find(reactorBlock, 1, -1, 0))
                                                && !find(reactorBlock, 1, 0, 0)
                                )
                ) {
                    if (!find(reactorBlock, 0, 1, -z) && !find(reactorBlock, 0, -1, -z) && (find(reactorBlock, graphiteIdle, graphiteActive, steamIdle, reactorSteamActive, 0, 0, -z + 1) || find(reactorBlock, graphiteIdle, graphiteActive, steamIdle, reactorSteamActive, 0, 1, -z + 1) || find(reactorBlock, graphiteIdle, graphiteActive, steamIdle, reactorSteamActive, 0, -1, -z + 1))) {
                        rz = max_length - z;
                        z0 = -z + 1;
                        casintComplete = true;
                        break;
                    }
                } else if (!find(reactorBlock, 0, 0, -z) && !find(reactorBlock, 1, 1, -z) && !find(reactorBlock, 1, -1, -z) && find(reactorBlock, graphiteIdle, graphiteActive, steamIdle, reactorSteamActive, 0, 0, -z + 1) && find(reactorBlock, 1, 0, -z) && find(reactorBlock, 1, 1, -z + 1) && find(reactorBlock, 1, -1, -z + 1)) {
                    rz = max_length - z;
                    z0 = -z + 1;
                    casintComplete = true;
                    break;
                }
            }
            if (!casintComplete) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_invalid_controller_location");
                return false;
            }
            casintComplete = false;

            //bottom left front corner based on left front
            for (int y = 0; y <= max_length; y++) {
                if (
                        !find(validCasing, x0, -y , z0) && // no casing at current check location, if true this should be the bottom block for this corner
                                !find(validCasing, x0 -1, -y + 1, z0) && //check for blocks in front of the  corner
                                !find(validCasing, x0, -y + 1, z0 - 1) && //check for blocks on the left of the corner

                                find(validCasing, x0, -y + 1, z0 ) && //check if block above is casing
                                find(validCasing, x0, -y + 2, z0 ) && //check if block above is casing
                                find(validCasing, x0 + 1, -y + 1, z0 ) && //check if block above is casing
                                find(validCasing, x0, -y + 1, z0 + 1 ) && //check if block above is casing

                                find(validCasing, x0 + 1, -y + 1, z0 + 1) &&
                                find(validCasing, x0, -y + 2, z0 + 1) &&
                                find(validCasing, x0 + 1, -y + 2, z0)) {
                    y0 = -y + 1;
                    casintComplete = true;
                    break;
                }
            }
            if (!casintComplete) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_bottom_left_front_corner");
                return false;
            }
            casintComplete = false;

            //bottom front right corner check
            for (int z = 0; z <= rz; z++) {
                if (
                        !find(validCasing, x0, y0, z) &&
                                !find(validCasing, x0, y0 -1, z -1) &&
                                !find(validCasing, x0 -1, y0, z - 1) &&

                                find(validCasing, x0, y0, z-1) &&
                                find(validCasing, x0, y0, z-2) &&
                                find(validCasing, x0, y0 + 1, z-1) &&
                                find(validCasing, x0 + 1, y0, z-1) &&

                                find(validCasing, x0 + 1, y0 + 1, z - 1) &&
                                find(validCasing, x0, y0 + 1, z - 2) &&
                                find(validCasing, x0 + 1, y0 + 1, z -1)) {
                    z1 = z - 1;
                    casintComplete = true;
                    break;
                }
            }
            if (!casintComplete) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_bottom_right_front_corner");
                return false;
            }
            casintComplete = false;

            // back bottom left corner check
            for (int x = 0; x <= max_length; x++) {
                if (
                        !find(validCasing, x0 + x, y0, z0) &&
                                !find(validCasing, x0 + x - 1, y0, z0 - 1) &&
                                !find(validCasing, x0 + x - 1, y0 - 1, z0) &&

                                find(validCasing, x0+x-1,y0, z0 ) &&
                                find(validCasing, x0+x-1,y0 + 1, z0 ) &&
                                find(validCasing, x0+x-1,y0, z0 + 1 ) &&
                                find(validCasing, x0+x-2,y0, z0 ) &&

                                find(validCasing, x0 + x - 1, y0 + 1, z0 + 1) &&
                                find(validCasing, x0 + x -2, y0 + 1, z0) &&
                                find(validCasing, x0 + x - 2, y0, z0 + 1)) {
                    x1 = x0 + x -1;
                    casintComplete = true;
                    break;
                }
            }
            if (!casintComplete) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_bottom_left_back_corner");
                return false;
            }

//            check bottom right back corner
            if (
                    find(reactorBlock, x1+1, y0, z1) ||
                            find(reactorBlock, x1, y0, z1 + 1) ||
                            find(reactorBlock, x1 , y0 - 1, z1) ||

                            !find(validCasing, x1,y0, z1 ) ||
                            !find(validCasing, x1-1,y0, z1 ) ||
                            !find(validCasing, x1,y0, z1-1 ) ||
                            !find(validCasing, x1,y0+1, z1 ) ||

                            !find(validCasing, x1 - 1, y0 + 1, z1) ||
                            !find(validCasing, x1 , y0 + 1, z1 -1) ||
                            !find(validCasing, x1 -1, y0, z1 -1)) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_bottom_right_back_corner");
                return false;
            }



            casintComplete = false;

            //left front top corner check
            for (int y = 0; y <= max_length; y++) {
                if (
                        !find(validCasing, x0, y0 + y, z0) &&
                                !find(validCasing, x0 - 1, y0 + y -1, z0) &&
                                !find(validCasing, x0, y0 + y - 1, z0 - 1) &&

                                find(validCasing, x0, y0 + y - 1, z0) &&
                                find(validCasing, x0 + 1, y0 + y - 1, z0) &&
                                find(validCasing, x0, y0 + y - 1, z0 + 1) &&
                                find(validCasing, x0, y0 + y - 2, z0) &&

                                find(validCasing, x0 + 1, y0 + y - 1 , z0 + 1) &&
                                find(validCasing, x0, y0 + y - 2, z0 + 1) &&
                                find(validCasing, x0 + 1, y0 + y - 2, z0)) {
                    y1 = y0 + y -1;
                    casintComplete = true;
                    break;
                }
            }
            if (!casintComplete) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_top_left_front_corner");
                return false;
            }


            //            check right front top corner
            if (
                    find(reactorBlock, x0-1, y1, z1) ||
                            find(reactorBlock, x0, y1, z1 + 1) ||
                            find(reactorBlock, x0 , y1 + 1, z1) ||

                            !find(validCasing, x0, y1, z1 ) ||
                            !find(validCasing, x0+1, y1, z1 ) ||
                            !find(validCasing, x0, y1-1, z1 ) ||
                            !find(validCasing, x0, y1, z1-1 ) ||


                            !find(validCasing, x0, y1 - 1, z1 - 1) ||
                            !find(validCasing, x0 + 1 , y1 - 1, z1) ||
                            !find(validCasing, x0 + 1, y1, z1 -1)) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_top_right_front_corner");
                return false;
            }

            //            check right back top corner
            if (
                    find(reactorBlock, x1+1, y1, z1) ||
                            find(reactorBlock, x1, y1, z1 + 1) ||
                            find(reactorBlock, x0 , y1 + 1, z1) ||

                            !find(validCasing, x1, y1, z1 ) ||
                            !find(validCasing, x1-1, y1, z1 ) ||
                            !find(validCasing, x1, y1-1, z1 ) ||
                            !find(validCasing, x1, y1, z1-1 ) ||


                            !find(validCasing, x1, y1 - 1, z1 - 1) ||
                            !find(validCasing, x1 - 1 , y1 - 1, z1) ||
                            !find(validCasing, x1 - 1, y1, z1 -1)) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_top_right_back_corner");
                return false;
            }


            //            check left back top corner
            if (
                    find(reactorBlock, x1+1, y1, z0) ||
                            find(reactorBlock, x1, y1, z0 - 1) ||
                            find(reactorBlock, x0 , y1 + 1, z0) ||

                            !find(validCasing, x1, y1, z0 ) ||
                            !find(validCasing, x1-1, y1, z0 ) ||
                            !find(validCasing, x1, y1-1, z0 ) ||
                            !find(validCasing, x1, y1, z0+1 ) ||


                            !find(validCasing, x1, y1 - 1, z0 + 1) ||
                            !find(validCasing, x1 - 1 , y1 - 1, z0) ||
                            !find(validCasing, x1 - 1, y1, z0 + 1)) {
                complete = 0;
                problem = StatCollector.translateToLocal("gui.casingIncomplete");
                extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_top_left_back_corner");
                return false;
            }




            casintComplete = false;

            //check to see if coordinates make sense
            if ((x0 > 0 || x1 < 0) || (y0 > 0 || y1 < 0) || (z0 > 0 || z1 < 0) || x1 - x0 < 1 || y1 - y0 < 1 || z1 - z0 < 1) {
                problem = StatCollector.translateToLocal("gui.invalidStructure");
                extendedproblem = StatCollector.translateToLocal("gui.invalidStructure");
                complete = 0;
                return false;
            }

            //check entire multiblock for multiple controllers
            for (int z = z0; z <= z1; z++) {
                for (int x = x0; x <= x1; x++) {
                    for (int y = y0; y <= y1; y++) {
                        if (find(graphiteIdle, graphiteActive, steamIdle, reactorSteamActive, x, y, z)) {
                            if (x == 0 && y == 0 && z == 0) {
                            } else {
                                problem = StatCollector.translateToLocal("gui.multipleControllers");
                                extendedproblem = StatCollector.translateToLocal("gui.multipleControllers") + ": " + getRealBlockString(x,y,z);
                                complete = 0;
                                return false;
                            }
                        }
                    }
                }
            }

            //check top and bottom are completly filled with reactor casing
            for (int z = z0 ; z <= z1; z++) {
                for (int x = x0; x <= x1; x++) {
                    if (!find(reactorBlock, x, y0, z) && !(x == 0 && y0 == 0 && z == 0)) {
                        if(find(energyProxy, x, y0, z)){
                            addProxy(x, y0, z);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_bottom") + ": " + getRealBlockString(x, y0, z);
                            complete = 0;
                            return false;
                        }
                    }
                    if (!find(reactorBlock, x, y1, z) && !(x == 0 && y1 == 0 && z == 0)) {
                        if(find(energyProxy, x, y1, z)){
                            addProxy(x, y1, z);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_top") + ": " + getRealBlockString(x, y1, z);
                            complete = 0;
                            return false;
                        }
                    }
                }
            }

            //check if sides are completly filled
            for (int y = y0; y <= y1; y++) {

                //check left and right side
                for (int x = x0; x <= x1; x++) {
                    if (!find(reactorBlock, x, y, z0) && !(x == 0 && y == 0 && z0 == 0)) {
                        if(find(energyProxy, x, y, z0)){
                            addProxy(x, y, z0);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_left") + ": " + getRealBlockString(x, y, z0);
                            complete = 0;
                            return false;
                        }
                    }
                    if (!find(reactorBlock, x, y, z1) && !(x == 0 && y == 0 && z1 == 0)) {
                        if(find(energyProxy, x, y, z1)){
                            addProxy(x, y, z1);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_right") + ": " + getRealBlockString(x, y, z1);
                            complete = 0;
                            return false;
                        }
                    }
                }

                //check front and back
                for (int z = z0 + 1; z <= z1 - 1; z++) {
                    if (!find(reactorBlock, x0, y, z) && !(x0 == 0 && y == 0 && z == 0)) {
                        if(find(energyProxy, x0, y, z)){
                            addProxy(x0, y, z);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_front") + ": " + getRealBlockString(x0, y, z);
                            complete = 0;
                            return false;
                        }
                    }
                    if (!find(reactorBlock, x1, y, z) && !(x1 == 0 && y == 0 && z == 0)) {
                        if(find(energyProxy, x1, y, z)){
                            addProxy(x1, y, z);
                        }else {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingIncomplete_back") + ": " + getRealBlockString(x1, y, z);
                            complete = 0;
                            return false;
                        }
                    }
                }
            }
            //check interior
            for (int z = z0 + 1; z <= z1 - 1; z++) {
                for (int x = x0 + 1; x <= x1 - 1; x++) {
                    for (int y = y0 + 1; y <= y1 - 1; y++) {
                        if (find(validCasing, x, y, z)) {
                            problem = StatCollector.translateToLocal("gui.casingIncomplete");
                            extendedproblem = StatCollector.translateToLocal("gui.casingInInterior")  + ": " + getRealBlockString(x,y,z);
                            complete = 0;
                            return false;
                        }
                    }
                }
            }
            //problem = StatCollector.translateToLocal("gui.casingIncomplete");
            complete = 1;
            tickCount = 0;
            this.x0 = x0;
            this.y0 = y0;
            this.z0 = z0;
            this.x1 = x1;
            this.y1 = y1;
            this.z1 = z1;
            lx = x1 + 1 - x0;
            ly = y1 + 1 - y0;
            lz = z1 + 1 - z0;
            return true;
        } else {
            tickCount++;
            return complete == 1;
        }
    }
}
