package cheatchki.SKPermissionsEx.classes;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
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

public class ExprGetUser extends SimpleExpression<PermissionUser>{

	static {
		Skript.registerExpression(ExprGetUser.class, PermissionUser.class, ExpressionType.SIMPLE,
				"pex user %string%", "pex user %player%");
	}
	
	private Expression<String> name = null;
	private Expression<Player> player = null;
	
	@Override
	public Class<? extends PermissionUser> getReturnType() {
		return PermissionUser.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (arg1 == 0)
			name = (Expression<String>) arg0[0];
		else
			player = (Expression<Player>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "pex group (%string%|%player%)";
	}

	@Override
	@Nullable
	protected PermissionUser[] get(Event arg0) {
		if (name != null)
			return new PermissionUser[] { PermissionsEx.getPermissionManager().getUser(name.getSingle(arg0)) };
		else
			return new PermissionUser[] { PermissionsEx.getPermissionManager().getUser(player.getSingle(arg0)) };
	}
	
	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.DELETE) {
			return CollectionUtils.array();
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		get(e)[0].remove();
	}
}
