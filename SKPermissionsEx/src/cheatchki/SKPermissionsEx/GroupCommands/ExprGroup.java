package cheatchki.SKPermissionsEx.GroupCommands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

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

@Name("All Groups")
@Description("Gets all groups currently registered by PermissionsEx")
@Examples({"message \"%pex groups\"",
			"add \"testGroup\" to groups",
			"delete pex groups"})
@Since("0.4.0")

public class ExprGroup extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprGroup.class, String.class, ExpressionType.SIMPLE, 
				"[pex] group[s]");
	}
	
	
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
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group[s]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		List<String> groups = new ArrayList<String>();
		for (PermissionGroup g : PermissionsEx.getPermissionManager().getGroupList()) {
			groups.add(g.getName());
		}
		return groups.toArray(new String[groups.size()]);
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
		if (mode == ChangeMode.ADD) {
			addGroup((String) delta[0]);
		} else if (mode == ChangeMode.REMOVE) {
			removeGroup((String) delta[0]);
		} else if (mode == ChangeMode.DELETE) {
			for (PermissionGroup g : PermissionsEx.getPermissionManager().getGroupList()) {
				removeGroup(g.getName());
			}
			
		}
	}
	
	private void addGroup(String groupName) {
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		if (!group.isVirtual()) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " already exist");
			return;
		}
		group.save();
	}
	
	private void removeGroup(String groupName) {
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		group.remove();
		PermissionsEx.getPermissionManager().resetGroup(group.getIdentifier());
	}
	

}
