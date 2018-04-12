package cheatchki.SKPermissionsEx.Expressions;

import java.util.LinkedList;
import java.util.List;

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

public class ExprParents extends SimpleExpression<PermissionGroup>{

	static {
		Skript.registerExpression(ExprParents.class, PermissionGroup.class, ExpressionType.PROPERTY, 
				"%permissionentity%'[s] parents [in [world] %-world%]",
				"parents of %permissionentity% [in [world] %-world%]");
	}
	
	private Expression<PermissionEntity> entity;
	private Expression<World> world;
	
	@Override
	public Class<? extends PermissionGroup> getReturnType() {
		return PermissionGroup.class;
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
		return "parents of entity [in world]";
	}

	@Override
	@Nullable
	protected PermissionGroup[] get(Event arg0) {
		World world = this.world != null ? this.world.getSingle(arg0) : null;
		List<PermissionGroup> parents = entity.getSingle(arg0).getParents(CheatsUtils.getName(world));
		return parents.toArray(new PermissionGroup[parents.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		switch(mode) {
		case ADD:
		case REMOVE:
			return CollectionUtils.array(PermissionGroup.class);
		default:
			return null;
		}
		
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		World world = this.world != null ? this.world.getSingle(e) : null;
		PermissionEntity entity = this.entity.getSingle(e);
		PermissionGroup parent = (PermissionGroup) delta[0];
		List<PermissionGroup> parents = new LinkedList<>(entity.getOwnParents(CheatsUtils.getName(world)));
		
		if (mode == ChangeMode.ADD)
			parents.add(parent);
		else 
			parents.remove(parent);
		
		entity.setParents(parents, CheatsUtils.getName(world));
//		entity.save();
		
	}
	
}
