package cheatchki.SKPermissionsEx.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;
import ru.tehkode.permissions.PermissionGroup;

public class ExprRank extends SimplePropertyExpression<PermissionGroup, Number>{

	static {
		register(ExprRank.class, Number.class, "rank", "permissiongroup");
	}
	
	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	@Nullable
	public Number convert(PermissionGroup arg0) {
		return arg0.getRank();
	}

	@Override
	protected String getPropertyName() {
		return "rank";
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch (mode) {
		case SET:
			return CollectionUtils.array(Number.class);
		case DELETE:
			return CollectionUtils.array();
		default:
			return null;
		}
		
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		Number rank = 0;
		if (mode == ChangeMode.SET) 
			rank = (Number) delta[0];
		getExpr().getSingle(e).setRank(rank.intValue());
	}

	
}
