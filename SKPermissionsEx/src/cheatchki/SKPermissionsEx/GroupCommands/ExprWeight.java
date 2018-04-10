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

@Name("weight of group")
@Description("Gets the weight of a PermissionsEx group")
@Examples({"message \"%pex group \"\"beta\"\" weight%\"",
			"set pex group \"admin\" weight to 15",
			"delete pex group \"mod\" weight"})
@Since("0.4.0")

public class ExprWeight extends SimpleExpression<Integer> {

	static {
		Skript.registerExpression(ExprWeight.class, Integer.class, ExpressionType.COMBINED, 
				"[pex] group %string% weight");
	}
	
	private Expression<String> groupName;
	
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		groupName = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[pex] group %string% weight";
	}

	@Override
	@Nullable
	protected Integer[] get(Event arg0) {
		String groupName = this.groupName.getSingle(arg0);
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		
		if (group == null) {
			SKPermissionsEx.getInstance().getLogger().severe("Group " + groupName + " doesn't exist.");
			return null;
		}
		return new Integer[] { group.getWeight() };
	}
	
	@Override
	public void change(Event arg0, Object[] arg1, ChangeMode arg2) {
		String groupName = this.groupName.getSingle(arg0);
		PermissionGroup group = PermissionsEx.getPermissionManager().getGroup(groupName);
		
		if (arg2 == ChangeMode.SET) {
			int weight = ((Number) arg1[0]).intValue();
			group.setWeight(weight);
		} else if (arg2 == ChangeMode.DELETE) {
			group.setWeight(0);
		}
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.DELETE || mode == ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}
	
}
