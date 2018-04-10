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

@Name("option of group")
@Description("Gets the option of a PermissionsEx group")
@Examples({"message \"%group \"\"admin\"\" option \"\"rank-ladder\"\" in world player's world%\"", 
			"set group \"admin\" option \"rank-ladder\" to \"default\""})
@Since("0.4.0")

public class ExprOption extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprOption.class, String.class, ExpressionType.SIMPLE,
				"[pex] group %string% option %string% [in [world] %-world%]");
	}
	
	private Expression<String> groupName;
	private Expression<String> optionName;
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
		optionName = (Expression<String>) arg0[1];
		world = (Expression<World>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% option %string% [in [world] %-world%]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		String groupName = this.groupName.getSingle(arg0);
		String optionName = this.optionName.getSingle(arg0);
		String worldName = world != null ? world.getSingle(arg0).getName() : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return null;
		}
		
		return new String[] { group.getOption(optionName) };
		
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		String groupName = this.groupName.getSingle(e);
		String optionName = this.optionName.getSingle(e);
		String worldName = world != null ? world.getSingle(e).getName() : null;
		
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist");
			return;
		}
		
		String optionValue = (String) delta[0];
		
		group.setOption(optionName, optionValue, worldName);
		
	}

}
