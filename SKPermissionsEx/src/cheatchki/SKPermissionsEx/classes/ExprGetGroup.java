package cheatchki.SKPermissionsEx.classes;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ExprGetGroup extends SimpleExpression<PermissionGroup>{

	static {
		Skript.registerExpression(ExprGetGroup.class, PermissionGroup.class, ExpressionType.SIMPLE,
				"pex group %string%");
	}
	
	private Expression<String> name;
	
	@Override
	public Class<? extends PermissionGroup> getReturnType() {
		return PermissionGroup.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		name = (Expression<String>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "pex group %string%";
	}

	@Override
	@Nullable
	protected PermissionGroup[] get(Event arg0) {
		return new PermissionGroup[] { PermissionsEx.getPermissionManager().getGroup(name.getSingle(arg0)) };
	}

}
