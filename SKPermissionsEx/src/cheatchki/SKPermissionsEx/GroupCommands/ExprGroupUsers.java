package cheatchki.SKPermissionsEx.GroupCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@Name("Group's users")
@Description("Gets the users in a PermissionsEx group")
@Examples({"message \"%group \"\"admin\"\" users%\"", 
			"add player's name to group \"admin\" users in world player's world"})
@Since("0.4.0")

public class ExprGroupUsers extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprGroupUsers.class, String.class, ExpressionType.SIMPLE,
				"[pex] group %string% user[s] [in [world] %-world%]");
	}
	
	private Expression<String> group;
	private Expression<World> world;
	
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
		group = (Expression<String>) arg0[0];
		world = (Expression<World>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% user[s] [in [world] %-world%]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		String groupName = this.group.getSingle(arg0);
		String worldName = world != null ? world.getSingle(arg0).getName() : null;
		
		Set<PermissionUser> users = PermissionsEx.getPermissionManager().getUsers(groupName, worldName);
		
		if (users == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return null;
		}
		
		if (users.isEmpty()) {
			SKPermissionsEx.getInstance().getLogger().warning("Group " + groupName + " has no users.");
			return null;
		}
		
		List<String> userNames = new ArrayList<String>();
		
		for (PermissionUser user : users) {
			userNames.add(user.getName());
		}
		
		return userNames.toArray(new String[userNames.size()]);
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
		String groupName = this.group.getSingle(e);
		String worldName = world != null ? world.getSingle(e).getName() : null;
		
		if (mode == ChangeMode.ADD) {
			String userName = (String) delta[0];
			PermissionUser user = PermissionsEx.getPermissionManager().getUser(userName);
			if (user == null) {
				SKPermissionsEx.getInstance().getLogger().severe("User " + userName + " doesn't exist.");
				return;
			}
			user.addGroup(groupName, worldName);
		} else if (mode == ChangeMode.REMOVE) {
			String userName = (String) delta[0];
			PermissionUser user = PermissionsEx.getPermissionManager().getUser(userName);
			if (user == null) {
				SKPermissionsEx.getInstance().getLogger().severe("User " + userName + " doesn't exist.");
				return;
			}
			user.removeGroup(groupName, worldName);
			
		} else if (mode == ChangeMode.DELETE) {
			Set<PermissionUser> users = PermissionsEx.getPermissionManager().getUsers(groupName, worldName);
			
			if (users == null) {
				SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
				return;
			}
			
			if (users.isEmpty()) {
				SKPermissionsEx.getInstance().getLogger().warning("Group " + groupName + " has no users.");
				return;
			}
			
			for (PermissionUser user : users) {
				user.removeGroup(groupName, worldName);
			}
		}
	}
}
