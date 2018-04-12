package cheatchki.SKPermissionsEx.Expressions;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.util.coll.CollectionUtils;
import cheatchki.SKPermissionsEx.Utils.CheatsUtils;
import cheatchki.SKPermissionsEx.classes.base.SimpleWorldPropertyExpression;
import ru.tehkode.permissions.PermissionEntity;

public class ExprPrefix extends SimpleWorldPropertyExpression<PermissionEntity, String> {

	static {
		register(ExprPrefix.class, String.class, "[(vault|pex)] prefix", "permissionentity");
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String getPropertyName() {
		return "prefix";
	}

	@Override
	public String convert(World world, PermissionEntity f) {
		return f.getPrefix(CheatsUtils.getName(world));
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch(mode) {
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
		String prefix = null;
		if (mode == ChangeMode.SET)
			prefix = (String) delta[0];
		getExpr().getSingle(e).setPrefix(prefix, CheatsUtils.getName(getWorld(e)));
		
	}
	
	


}
