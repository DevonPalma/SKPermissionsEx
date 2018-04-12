package cheatchki.SKPermissionsEx.Expressions;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import cheatchki.SKPermissionsEx.classes.base.SimpleWorldPropertyExpression;
import cheatchki.SKPermissionsEx.classes.base.WorldPropertyExpression;
import ru.tehkode.permissions.PermissionEntity;

public class ExprPrefix extends SimpleWorldPropertyExpression<PermissionEntity, String> {

	static {
		register(ExprPrefix.class, String.class, "prefix", "permissionentity");
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
		return f.getPrefix(world != null ? world.getName() : null);
	}



}
