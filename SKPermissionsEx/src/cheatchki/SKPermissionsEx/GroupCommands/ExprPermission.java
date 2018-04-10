package cheatchki.SKPermissionsEx.GroupCommands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import cheatchki.SKPermissionsEx.SKPermissionsEx;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("permissions of group")
@Description("Gets the permissions of a PermissionsEx group")
@Examples({"message \"%group \"\"admin\"\" permissions in world player's world with parents%\"", 
			"add \"test.perm\" to group \"admin\" permissions"})
@Since("0.4.0")

public class ExprPermission extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprPermission.class, String.class, ExpressionType.SIMPLE, 
				"[pex] group %string% permission[s] [in [world] %-world%]",
				"[pex] group %string% permission[s] [in [world] %-world%] (with|includ(e|ing)) parents");
	}
	
	
	private Expression<String> groupName;
	private Expression<World> world;
	private boolean withParents;
	private Expression<Number> time;
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		groupName = (Expression<String>) arg0[0];
		world = (Expression<World>) arg0[1];
		withParents = arg1 == 1;
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% permission[s] [in %-world%] [(with|includ(e|ing)) parents]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		String groupName = this.groupName.getSingle(arg0);
		String worldName = world != null ? world.getSingle(arg0).getName() : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return null;
		}
		
		List<String> permissions = withParents ? 
					group.getPermissions(worldName) : 
					group.getOwnPermissions(worldName);
		
		return permissions.toArray(new String[permissions.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE || mode == ChangeMode.DELETE) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		String groupName = this.groupName.getSingle(e);
		String worldName = world != null ? world.getSingle(e).getName() : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		
		if (mode == ChangeMode.ADD) {
			String permission = (String) delta[0];
			group.addPermission(permission, worldName);
		} else if (mode == ChangeMode.REMOVE) {
			String permission = (String) delta[0];
			group.removePermission(permission, worldName);
		} else if (mode == ChangeMode.DELETE) {
			List<String> permissions = group.getOwnPermissions(worldName);
			for (int i = permissions.size() - 1; i >= 0; i--) {
				group.removePermission(permissions.get(i), worldName);
			}
		}
	}

}
