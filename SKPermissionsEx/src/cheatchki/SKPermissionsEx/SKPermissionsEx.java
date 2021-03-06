package cheatchki.SKPermissionsEx;

import java.io.IOException;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;

public class SKPermissionsEx extends JavaPlugin implements Listener {
	
	private static SKPermissionsEx instance;
	private static SkriptAddon addonInstance;
	
	
	public void onEnable() {
		try {
			getAddonInstance().loadClasses("cheatchki.SKPermissionsEx", "classes", "Expressions", "Utils", "Effects");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onDisable() {
		
	}
	
	public static SKPermissionsEx getInstance() {
		if (instance == null) {
			throw new IllegalStateException();
		}
		return instance;
	}
	
	public static SkriptAddon getAddonInstance() {
		if (addonInstance == null) {
			addonInstance = Skript.registerAddon(getInstance());
		}
		return addonInstance;
	}
	
	
	public SKPermissionsEx() {
		if (instance == null) {
			instance = this;
		} else {
			throw new IllegalStateException();
		}
	}
	
}
