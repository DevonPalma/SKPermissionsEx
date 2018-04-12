package cheatchki.SKPermissionsEx.Expressions;

import java.util.Set;

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
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ExprAllUsers extends SimpleExpression<PermissionUser> {

	static {
		Skript.registerExpression(ExprAllUsers.class, PermissionUser.class, ExpressionType.SIMPLE, "all [pex] users");
	}
	
	@Override
	public Class<? extends PermissionUser> getReturnType() {
		return PermissionUser.class;
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
	protected PermissionUser[] get(Event arg0) {
		Set<PermissionUser> users = PermissionsEx.getPermissionManager().getUsers();
		return users.toArray(new PermissionUser[users.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.DELETE) 
			return CollectionUtils.array();
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		for (PermissionUser g : PermissionsEx.getPermissionManager().getUsers()) {
			g.remove();
			PermissionsEx.getPermissionManager().resetUser(g.getName());;
		}
	}
}
