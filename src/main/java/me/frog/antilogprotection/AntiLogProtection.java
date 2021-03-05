package me.frog.antilogprotection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class AntiLogProtection extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onLonIn(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        String v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        if (!p.getClass().getName().equals("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer")) {
            p = Bukkit.getPlayer(p.getUniqueId());
        }
        try {
            Class<?> CraftPlayerClass = Class.forName("org.bukkit.craftbukkit." + v + ".entity.CraftPlayer");
            Object CraftPlayer = CraftPlayerClass.cast((Player) p);
            Method getHandle = CraftPlayer.getClass().getMethod("getHandle");
            Object EntityPlayer = getHandle.invoke(CraftPlayer);
            Field invt = EntityPlayer.getClass().getDeclaredField("invulnerableTicks");
            invt.set(EntityPlayer, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
