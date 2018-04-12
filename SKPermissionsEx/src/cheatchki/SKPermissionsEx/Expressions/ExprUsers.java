package cheatchki.SKPermissionsEx.Expressions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import cheatchki.SKPermissionsEx.Utils.CheatsUtils;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;

public class ExprUsers extends SimpleExpression<PermissionUser>{

	static {
		Skript.registerExpression(ExprUsers.class, PermissionUser.class, ExpressionType.PROPERTY, 
				"%permissiongroup%'[s] users [in [world] %-world%]",
				"users of %permissiongroup% [in [world] %-world%]");
	}
	
	private Expression<PermissionGroup> group;
	private Expression<World> world;
	
	@Override
	public Class<? extends PermissionUser> getReturnType() {
		return PermissionUser.class;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		group = (Expression<PermissionGroup>) arg0[0];
		world = (Expression<World>) arg0[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "parents of entity [in world]";
	}

	@Override
	@Nullable
	protected PermissionUser[] get(Event arg0) {
		World world = this.world != null ? this.world.getSingle(arg0) : null;
		
		Set<PermissionUser> users = group.getSingle(arg0).getUsers(CheatsUtils.getName(world));
		
		return users.toArray(new PermissionUser[users.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch(mode) {
		case ADD:
		case REMOVE:
			return CollectionUtils.array(PermissionUser.class);
		default:
			return null;
		}
		
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		World world = this.world != null ? this.world.getSingle(e) : null;
		PermissionGroup group = this.group.getSingle(e);
		PermissionUser user = (PermissionUser) delta[0];
		
		if (mode == ChangeMode.ADD)
			user.addGroup(group, CheatsUtils.getName(world));
		else 
			user.removeGroup(group, CheatsUtils.getName(world));
		
//		entity.save();
		
	}
	
}
