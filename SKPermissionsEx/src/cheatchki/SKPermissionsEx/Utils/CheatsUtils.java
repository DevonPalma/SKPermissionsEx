package cheatchki.SKPermissionsEx.Utils;

import org.bukkit.World;

public class CheatsUtils {
	public static String getName(World world) {
		if (world == null) 
			return null;
		return world.getName();
	}
}
