package cheatchki.SKPermissionsEx.Expressions;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import ru.tehkode.permissions.PermissionGroup;

public class ExprRankLadder extends SimplePropertyExpression<PermissionGroup, String>{

	static {
		register(ExprRankLadder.class, String.class, "rankladder", "permissiongroup");
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String getPropertyName() {
		return "rank ladder";
	}

	@Override
	public String convert(PermissionGroup f) {
		return f.getRankLadder();
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
		case SET:
			return CollectionUtils.array(String.class);
		case DELETE:
			return CollectionUtils.array();
		default:
			return null;
		}
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		PermissionGroup g = getExpr() != null ? getExpr().getSingle(e) : null;
		String rankLadder = null;
		if (mode == ChangeMode.SET) 
			rankLadder = (String) delta[0];
		if (g != null)
			g.setRankLadder(rankLadder);
	}
	
	
	
}
