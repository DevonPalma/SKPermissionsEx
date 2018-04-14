package cheatchki.SKPermissionsEx.Expressions;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import cheatchki.SKPermissionsEx.Utils.CheatsUtils;
import ru.tehkode.permissions.PermissionEntity;

public class ExprOptions extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprOptions.class, String.class, ExpressionType.PROPERTY, 
				"[pex] %permissionentity% option %string% [in [world] %-world%]",
				"[pex] option %string% of %permissionentity% [in [world] %-world%]");
	}
	
	private Expression<PermissionEntity> entity;
	private Expression<String> option;
	private Expression<World> world;
	
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		if (arg1 == 0) {
			entity = (Expression<PermissionEntity>) arg0[0];
			option = (Expression<String>) arg0[1];
		} else {
			entity = (Expression<PermissionEntity>) arg0[1];
			option = (Expression<String>) arg0[0];
		}
		world = (Expression<World>) arg0[2];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "option of entity";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		PermissionEntity entity = this.entity.getSingle(arg0);
		String worldName = CheatsUtils.getName(world != null ? world.getSingle(arg0) : null);
		String option = world != null ? this.option.getSingle(arg0) : null;
		
		return new String[] { entity.getOption(option, worldName) };
	}
	
}
