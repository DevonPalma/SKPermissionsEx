package cheatchki.SKPermissionsEx.Expressions;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ExprAllGroups extends SimpleExpression<PermissionGroup> {

	static {
		Skript.registerExpression(ExprAllGroups.class, PermissionGroup.class, ExpressionType.SIMPLE, "all [pex] groups");
	}
	
	@Override
	public Class<? extends PermissionGroup> getReturnType() {
		return PermissionGroup.class;
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
		return "groups";
	}

	@Override
	@Nullable
	protected PermissionGroup[] get(Event arg0) {
		List<PermissionGroup> groups = PermissionsEx.getPermissionManager().getGroupList();
		return groups.toArray(new PermissionGroup[groups.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.DELETE) 
			return CollectionUtils.array();
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		for (PermissionGroup g : PermissionsEx.getPermissionManager().getGroupList()) {
			g.remove();
			PermissionsEx.getPermissionManager().resetGroup(g.getIdentifier());
		}
	}
}
