package net.dreamerzero.paperplus.expansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import java.math.RoundingMode;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import io.papermc.paper.datapack.Datapack;

public class PaperPlus extends PlaceholderExpansion {

    @Override
    public boolean canRegister() {
        try{
            Class.forName("io.papermc.paper.datapack.Datapack");
            return true;
        } catch(ClassNotFoundException e){
            return false;
        }
    }

    @Override
    public String getAuthor() {
        return "4drian3d";
    }

    @Override
    public String getIdentifier() {
        return "paperplus";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setRoundingMode(RoundingMode.DOWN);
        numberFormat.setMaximumFractionDigits(2);
        switch (identifier) {
            case "server_mspt": return String.valueOf(numberFormat.format(Bukkit.getServer().getAverageTickTime()));
            case "player_lastplayed": case "player-lastjoin": return String.valueOf(player.getLastLogin());
            case "datapack_list": 
                StringBuilder builder = new StringBuilder();
                for (Datapack datapack : Bukkit.getServer().getDatapackManager().getEnabledPacks()) {
                    builder = builder.append("["+datapack.getName()+"]");
                    builder = builder.append(" ");
                }
                return builder.toString();
            case "datapack_count": return String.valueOf(Bukkit.getServer().getDatapackManager().getEnabledPacks().size());
            default: break;
        }

        if (identifier.startsWith("world_maxheight_")){
            String[] string = identifier.split("_");
            var world = Bukkit.getWorld(string[2]);
            return String.valueOf(world.getMaxHeight());
            
        } else if(identifier.startsWith("world_minheight_")){
            String[] string = identifier.split("_");
            var world = Bukkit.getWorld(string[2]);
            return String.valueOf(world.getMinHeight());
        }
        
        /**
         * Online Player placeholders
         * 
         * They can only return a value if the player is online, 
         * so they are in a separate section.
         */
        Player playerOnline;
        if (player.isOnline()) {
             playerOnline = (Player) player;
        } else {
            return null;
        }

        switch (identifier){
            case "player_viewdistance": return String.valueOf(playerOnline.getClientViewDistance());
            case "player_notickviewdistance": return String.valueOf(playerOnline.getClientViewDistance());
            case "player_client": return playerOnline.getClientBrandName();
            default: break; 
        }
        return null;
    }
}