package cheatchki.SKPermissionsEx.Expressions;

import java.util.List;

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

public class ExprPermissions extends SimpleExpression<String>{

	static {
		Skript.registerExpression(ExprPermissions.class, String.class, ExpressionType.PROPERTY, 
				"%permissionentity%'[s] permissions [in [world] %-world%]",
				"permissions of %permissionentity% [in [world] %-world%]");
	}
	
	private Expression<PermissionEntity> entity;
	private Expression<World> world;
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<PermissionEntity>) arg0[0];
		world = (Expression<World>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "permissions of entity [in world]";
	}

	@Override
	@Nullable
	protected String[] get(Event arg0) {
		World world = this.world != null ? this.world.getSingle(arg0) : null;
		List<String> perms = entity.getSingle(arg0).getPermissions(CheatsUtils.getName(world));
		return perms.toArray(new String[perms.size()]);
	}
//
//	@Override
//	public Class<?>[] acceptChange(ChangeMode mode) {
//		switch(mode) {
//		case ADD:
//		case REMOVE:
//			return CollectionUtils.array(String.class);
//		default:
//			return null;
//		}
//		
//	}
//
//	@Override
//	public void change(Event e, Object[] delta, ChangeMode mode) {
//		World world = this.world != null ? this.world.getSingle(e) : null;
//		String permission = (String) delta[0];
//		
//		if (mode == ChangeMode.ADD)
//			entity.getSingle(e).addPermission(permission, CheatsUtils.getName(world));
//		else 
//			entity.getSingle(e).removePermission(permission, CheatsUtils.getName(world));
//	}
	
}
