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

@Name("default group(s)")
@Description("Gets the default PermissionsEx group(s)")
@Examples({"message \"%default group in world player's world%\"", 
			"set default group \"admin\" to true"})
@Since("0.4.0")

public class ExprDefault extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprDefault.class, String.class, ExpressionType.SIMPLE,
				"[pex] default group[s] [%-string%] [in [world] %-world%]");
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
		world = (Expression<World>) arg0[1];
		groupName = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] default group[s] [%-string%] [in [world] %-world%]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		String worldName = world != null ? world.getSingle(arg0).getName() : null;
		
		List<String> groups = new ArrayList<String>();
		
		for (PermissionGroup defaultGroup : PermissionsEx.getPermissionManager().getDefaultGroups(worldName)) {
			groups.add(defaultGroup.getName());
		}
		
		return groups.toArray(new String[groups.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Boolean.class);
		}
		return null;
		
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		String worldName = world != null ? world.getSingle(e).getName() : null;
		String groupName = this.groupName != null ? this.groupName.getSingle(e) : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		
		if (mode == ChangeMode.SET) {
			boolean val = (boolean) delta[0];
			group.setDefault(val, worldName);
		}
	}

}
