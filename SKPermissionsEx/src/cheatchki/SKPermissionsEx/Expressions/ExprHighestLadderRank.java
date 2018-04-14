package cheatchki.SKPermissionsEx.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

public class ExprHighestLadderRank extends SimpleExpression<PermissionGroup> {

	static {
		Skript.registerExpression(ExprHighestLadderRank.class, PermissionGroup.class, ExpressionType.SIMPLE, 
				"[pex] %permissionuser% rank [in] ladder %string%");
	}
	
	private Expression<PermissionUser> user;
	private Expression<String> ladder;
	
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
		user = (Expression<PermissionUser>) arg0[0];
		ladder = (Expression<String>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "highest group of a ladder";
	}

	@Override
	@Nullable
	protected PermissionGroup[] get(Event arg0) {
		PermissionUser user = this.user != null ? this.user.getSingle(arg0) : null;
		String ladder = this.ladder != null ? this.ladder.getSingle(arg0) : null;
		
		
		if (user != null) {
			PermissionGroup parent = user.getRankLadderGroup(ladder);
			if (parent != null)
				return new PermissionGroup[] { parent };
		}
		return null;
		
		
	}
	
	
}
