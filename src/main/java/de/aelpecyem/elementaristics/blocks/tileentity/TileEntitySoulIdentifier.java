package de.aelpecyem.elementaristics.blocks.tileentity;

import de.aelpecyem.elementaristics.Elementaristics;
import de.aelpecyem.elementaristics.capability.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.Sys;

public class TileEntitySoulIdentifier extends TileEntity implements ITickable {

    public boolean playerStandingOn;
    public int tickCount;
    public EntityPlayer playerOn;
    public boolean isReadyForUse;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        playerStandingOn = compound.getBoolean("playerStandingOn");
        tickCount = compound.getInteger("tickCount");
        isReadyForUse = compound.getBoolean("isReadyForUse");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("playerStandingOn", playerStandingOn);
        compound.setInteger("tickCount", tickCount);
        compound.setBoolean("isReadyForUse", isReadyForUse);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if (world.isRemote) {
            if (isReadyForUse) {

                if (world.getClosestPlayer(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 10000, false) != null) {
                    playerOn = world.getClosestPlayer(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 10000, false);
                    playerStandingOn = true;
                } else {
                    playerOn = null;
                    playerStandingOn = false;
                }//TODO might actually do Soul Identification with drugs
            }
            if (tickCount >= 4) {
                if (playerOn != null) {
                    if (tickCount <= 280) {
                        playerOn.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 2, 4, false, false));
                        float centerX = pos.getX() + 0.5F;
                        float centerZ = pos.getZ() + 0.5F;
                        float circleRadius = 1;
                        float secondsPerRevolution = 3;

                        float posX = (float) (centerX + circleRadius * Math.cos(2 * 3.141 * (tickCount / 20.0) * secondsPerRevolution));
                        float posZ = (float) (centerZ + circleRadius * Math.sin(2 * 3.141 * (tickCount / 20.0) * secondsPerRevolution));

                        Elementaristics.proxy.generateGenericParticles(world, posX, pos.getY() + 0.2, posZ, 0, 0.25, 0, 14474460, 2, 100, 0.0F, false, true);

                        //  world.spawnParticle(EnumParticleTypes.END_ROD, posX, pos.getY() + 0.2, posZ, 0, 0.25, 0);
                    }
                } else {
                    isReadyForUse = false;
                }
            }
            if (playerOn != null) {

                if (!world.isRemote) {
                    switch (tickCount) {

                        case 10:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_1.name") + " " + playerOn.getName() + "... (0 %)"));
                            break;
                        case 30:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_2.name") + "... (15 %)"));
                            break;
                        case 60:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_3.name") + ""));
                            break;
                        case 90:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_4.name") + "... (35 %)"));
                            break;
                        case 120:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_5.name") + ""));
                            break;
                        case 150:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_6.name") + "... (50 %)"));
                            break;
                        case 180:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_7.name") + "... (65 %)"));
                            break;
                        case 210:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_8.name") + ""));
                            break;
                        case 240:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_9.name") + "... (85 %)"));
                            playerOn.attackEntityFrom(DamageSource.MAGIC, 7);
                            playerOn.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 3, 5));//damage and nausea
                            break;
                        case 270:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_10.name") + "... (100 %)"));
                            break;
                        case 290:
                            playerOn.sendMessage(new TextComponentString(I18n.format("message.soul_progress_done.name") + ""));
                            break;
                        default:
                            break;

                    }
                }
            }
            if (isReadyForUse) {
                if (playerStandingOn) {
                    if (playerOn != null) {
                        tickCount++;
                    }
                } else {
                    tickCount = 0;
                }
            } else {
                tickCount = 0;
            }

            if (tickCount == 300) {
                if (playerOn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                        IPlayerCapabilities cap = playerOn.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
                        playerOn.sendMessage(new TextComponentString(I18n.format("message.soul.name") + " " +
                                SoulInit.getSoulFromId(cap.getSoulId()).getLocalizedName() + "!"));
                        if (playerOn.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
                            cap.setKnowsSoul(true);
                            //SoulCapabilities.getCapFromSoul(cap.getSoulId()).reapplyBuffsOnRespawn(playerOn, cap);
                        }
                    tickCount = 0;
                    isReadyForUse = false;
                /*playerStandingOn = false;
                playerOn = null;*/
                }
            }

            if (tickCount < 0) {
                tickCount = 0;
            }

        }
    }
}
