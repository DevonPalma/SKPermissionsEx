package cheatchki.SKPermissionsEx.GroupCommands;

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

@Name("suffix of group")
@Description("Gets the suffix of a PermissionsEx group")
@Examples({"message \"%group \"\"admin\"\" suffix in world player's world%\"", 
			"set group \"admin\" suffix to \"[admin]\"",
			"delete group \"admin\" suffix"})
@Since("0.4.0")

public class ExprSuffix extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprSuffix.class, String.class, ExpressionType.SIMPLE,
				"[pex] group %string% Suffix [in [world] %-world%]");
	}
	
	private Expression<String> groupName;
	private Expression<World> world;
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		groupName = (Expression<String>) arg0[0];
		world = (Expression<World>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% Suffix [in [world] %-world%]";
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
		return new String[] { group.getSuffix(worldName) };
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE) {
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
		
		if (mode == ChangeMode.SET) {
			String suffix = (String) delta[0];
			group.setSuffix(suffix, worldName);
		} else if (mode == ChangeMode.DELETE) {
			group.setSuffix(null, worldName);
			
		}
	}

}
