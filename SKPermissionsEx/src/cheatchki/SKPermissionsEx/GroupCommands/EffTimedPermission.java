package cheatchki.SKPermissionsEx.GroupCommands;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import cheatchki.SKPermissionsEx.SKPermissionsEx;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import ru.tehkode.utils.DateUtils;

@Name("Adding/removing timed permissions")
@Description("Adds or removes a timed permission to a PermissionsEx group")
@Examples({"group \"admin\" timed add \"test.timedPerm\" 50", 
			"group \"mod\" timed remove \"coolperm.lamest\" in player's world"})
@Since("0.4.0")

public class EffTimedPermission extends Effect {

	static {
		Skript.registerEffect(EffTimedPermission.class, 
				"[pex] group %string% timed add %string% %number% [in [world] %-world%]",
				"[pex] group %string% timed remove %string% [in [world] %-world%]");
	}
	
	private Expression<String> groupName;
	private Expression<String> permissionName;
	private Expression<Number> time;
	private Expression<World> world;
	
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		groupName = (Expression<String>) arg0[0];
		permissionName = (Expression<String>) arg0[1];
		time = (arg1 == 0) ? (Expression<Number>) arg0[2] : null;
		world = (Expression<World>) arg0[(arg1 == 0) ? 3 : 2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% timed (add|removed) %string% [%number%] [in [world] %world%]";
	}

	@Override
	protected void execute(Event arg0) {
		String groupName = this.groupName.getSingle(arg0);
		String permissionName = this.permissionName.getSingle(arg0);
		Integer time = this.time != null ? this.time.getSingle(arg0).intValue() : null;
		String worldName = this.world != null ? this.world.getSingle(arg0).getName() : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist.");
			return;
		}
		
		if (time != null) {
			group.addTimedPermission(permissionName, worldName, time);
//			SKPermissionsEx.getInstance().getLogger().severe("Adding time permission " + permissionName + " to " + groupName + " for " + time);
		} else {
			group.removeTimedPermission(permissionName, worldName);
//			SKPermissionsEx.getInstance().getLogger().severe("removing time permission " + permissionName + " to " + groupName);
		}
		
	}

}
