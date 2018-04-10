package cheatchki.SKPermissionsEx.GroupCommands;

import java.util.LinkedList;
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

@Name("parents of group")
@Description("Gets the parents of a PermissionsEx group")
@Examples({"message \"%group \"\"admin\"\" parents in world player's world%\"",
			"add \"default\" to group \"admin\" parents in world player's world"})
@Since("0.4.0")

public class ExprParents extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprParents.class, String.class, ExpressionType.SIMPLE,
				"[pex] group %string% parent[s] [list] [in [world] %-world%]");
	}
	
	private Expression<String> groupName;
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
		groupName = (Expression<String>) arg0[0];
		world = (Expression<World>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% parent[s] [list] [in [world] %-world%]";
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
		
		List<String> parentNames = group.getOwnParentIdentifiers(worldName);
		if (parentNames.isEmpty()) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + "has no parents.");
			return null;
		}
		
		return parentNames.toArray(new String[parentNames.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.ADD || mode == ChangeMode.REMOVE) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {

		String worldName = world != null ? world.getSingle(e).getName() : null;

		String groupName = this.groupName.getSingle(e);
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		
		String parentName = (String) delta[0];
		PermissionGroup parentGroup = PermissionsEx.getPermissionManager().getGroup(parentName);
		if (parentGroup == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + parentName + " doesn't exist");
			return;
		}
		
		List<PermissionGroup> groups = new LinkedList<>(group.getOwnParents(worldName));
		if (mode == ChangeMode.REMOVE) {
			groups.remove(parentGroup);
		} else if (mode == ChangeMode.ADD) {
			if (!groups.contains(parentGroup)) {
				groups.add(parentGroup);
			}
		}	
		
		group.setParents(groups, worldName);
		
		group.save();
	}

}
