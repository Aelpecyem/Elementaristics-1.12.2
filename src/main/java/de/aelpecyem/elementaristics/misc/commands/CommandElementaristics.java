package de.aelpecyem.elementaristics.misc.commands;

import de.aelpecyem.elementaristics.capability.player.IPlayerCapabilities;
import de.aelpecyem.elementaristics.capability.player.PlayerCapProvider;
import de.aelpecyem.elementaristics.init.SoulInit;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import thaumcraft.common.lib.CommandThaumcraft;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CommandElementaristics extends CommandBase {

    @Override
    public String getName() {
        return "elementaristics";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "elementaristics <player> <knowsSoul:setMagan:setAscensionStage:setSoulId> <value:count>";
    }


    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }


    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 3){
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Usage: /" + getUsage(sender)));
            return;
        }

        String target = args[0];
        EntityPlayer player;
        boolean boolValue;
        int intValue;
        try {
            player = server.getPlayerList().getPlayerByUsername(target);
        } catch (NullPointerException e){
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Player " + target + " not found"));
            return;
        }
        String mode = args[1];
        String value = args[2];
        if (mode.equalsIgnoreCase("knowsSoul")){
            if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
                boolValue = Boolean.parseBoolean(value);
                if (!changeKnowsSoul(boolValue, player, sender)) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Something went wrong"));
                    return;
                }
            }else{
                sender.sendMessage(new TextComponentString(TextFormatting.RED + "Value must be " + TextFormatting.BOLD + "true" + TextFormatting.RESET + TextFormatting.RED + " or " + TextFormatting.BOLD +  "false"));
                return;
            }
        } else if (mode.equalsIgnoreCase("setMagan") || mode.equalsIgnoreCase("setAscensionStage")) {
            try{
                intValue = Integer.parseInt(value);
                if (mode.equalsIgnoreCase("setMagan")){
                    if (!changeSetMagan(intValue, player, sender)) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "Something went wrong"));
                        return;
                    }
                }else if (mode.equalsIgnoreCase("setAscensionStage")){
                    if (!changeAscensionStage(intValue, player, sender)) {
                        sender.sendMessage(new TextComponentString(TextFormatting.RED + "Something went wrong"));
                        return;
                    }
                }
            }catch (NumberFormatException e){
                sender.sendMessage(new TextComponentString(TextFormatting.RED + value +" is not a valid number"));
                return;
            }
        } else if (mode.equalsIgnoreCase("setSoulId")){
            try{
                intValue = Integer.parseInt(value) > -1 ? Math.min(Integer.parseInt(value), SoulInit.souls.size()) : 0;
                if (!changeSoulId(intValue, player, sender)) {
                    sender.sendMessage(new TextComponentString(TextFormatting.RED + "Something went wrong"));
                    return;
                }
            }catch (NumberFormatException e){
                sender.sendMessage(new TextComponentString(TextFormatting.RED + value +" is not a valid number"));
                return;
            }
        }else{
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Mode must be either knowsSoul, setMagan, setAscensionStage or setSoulId"));
            return;
        }
    }

    public boolean changeKnowsSoul(boolean boolValue, EntityPlayer player, ICommandSender sender){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setKnowsSoul(boolValue);
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Successfully changed  state of knowing of "  + player.getDisplayNameString() + " to " + boolValue));
            SoulInit.updateSoulInformation(player, cap);
            return true;
        }
        return false;
    }

    public boolean changeSetMagan(int intValue, EntityPlayer player, ICommandSender sender){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setMagan(intValue);
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Successfully changed the magan of "  + player.getDisplayNameString() + " to " + intValue));
            SoulInit.updateSoulInformation(player, cap);
            return true;
        }
        return false;
    }

    public boolean changeAscensionStage(int intValue, EntityPlayer player, ICommandSender sender){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setPlayerAscensionStage(intValue);
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Successfully changed the ascension stage of "  + player.getDisplayNameString() + " to " + intValue));
            SoulInit.updateSoulInformation(player, cap);
            return true;
        }
        return false;
    }
    public boolean changeSoulId(int intValue, EntityPlayer player, ICommandSender sender){
        if (player.hasCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null)) {
            IPlayerCapabilities cap = player.getCapability(PlayerCapProvider.ELEMENTARISTICS_CAP, null);
            cap.setSoulId(intValue);
            sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Successfully changed the soul of "  + player.getDisplayNameString() + " to " + SoulInit.getSoulFromId(intValue).getName()));
            SoulInit.updateSoulInformation(player, cap);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        List<String> completitions = new ArrayList<>();
        if(args.length == 1) {
            completitions.add(sender.getName());
        }else if (args.length == 2) {
            completitions.add("knowsSoul");
            completitions.add("setMagan");
            completitions.add("setAscensionStage");
            completitions.add("setSoulId");
        }else if (args.length == 3) {
            if (args[1].equalsIgnoreCase("knowsSoul")){
                completitions.add("true");
                completitions.add("false");
            } else if (args[1].equalsIgnoreCase("setMagan")){
                completitions.add("100");
            } else {
                completitions.add("0");
            }
        }
        return completitions;
    }
}
