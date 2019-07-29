package me.perotin.rustified.events;

import me.perotin.rustified.Rustified;
import me.perotin.rustified.objects.BluePrint;
import me.perotin.rustified.objects.BluePrintData;
import me.perotin.rustified.objects.RustifiedPlayer;
import me.perotin.rustified.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Recipe;

/* Created by Perotin on 2/12/19 */
public class CraftEvent implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(event.getWhoClicked() instanceof Player){
            Player clicker = (Player) event.getWhoClicked();
            if(!clicker.hasPermission("rustified.blueprint.exempt")){
                Recipe recipe = event.getRecipe();
                RustifiedPlayer rustifiedPlayer = Rustified.getPlayerObjectFor(clicker);
                BluePrintData data = BluePrintData.getSingleton();
                if(data.isMaterialBluePrintable(recipe.getResult().getType()) && !rustifiedPlayer.isAbleToCraft(recipe.getResult().getType())){
                    // cannot craft it!


                    event.setCancelled(true);
                  //  clicker.getInventory().remove(event.getCurrentItem());

                    String msg = Messages.getMessage("crafting-denied");
                    int levelForItem = data.getLevelFor(new BluePrint(recipe.getResult().getType()));;
                    String required = Messages.getMessage("workbench-level-required").replace("$number$", levelForItem+"");
                    if(msg.contains("/")){
                        String first = msg.split("/")[0];
                        String second = msg.split("/")[1];
                        clicker.sendMessage(first);
                        clicker.sendMessage(second);
                        clicker.sendMessage(required);
                        return;
                    }
                    clicker.sendMessage(Messages.getMessage("crafting-denied"));
                    clicker.sendMessage(required);

                }

            }
        }
    }
}
