package cheatchki.SKPermissionsEx.Expressions;

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
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ExprDefault extends SimpleExpression<PermissionGroup> {

	static {
		Skript.registerExpression(ExprDefault.class, PermissionGroup.class, ExpressionType.SIMPLE,
				"[pex] default group[s] [in [world] %-world%]");
	}
	
	private Expression<World> world;
	
	
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
		world = (Expression<World>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "default group";
	}

	@Override
	@Nullable
	protected PermissionGroup[] get(Event arg0) {
		String worldName = CheatsUtils.getName(world != null ? world.getSingle(arg0) : null);
		List<PermissionGroup> g = PermissionsEx.getPermissionManager().getDefaultGroups(worldName);
		return g.toArray(new PermissionGroup[g.size()]);
	}

	@Override
	public Class<?>[] acceptChange(ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(PermissionGroup.class);
		}
		return null;
		
	}

	@Override
	public void change(Event e, Object[] delta, ChangeMode mode) {
		String worldName = CheatsUtils.getName(world != null ? world.getSingle(e) : null);
		PermissionGroup defaultGroup = (PermissionGroup) delta[0];
		
		for (PermissionGroup g : PermissionsEx.getPermissionManager().getDefaultGroups(worldName)) {
			g.setDefault(false, worldName);
		}
		
		if (mode == ChangeMode.SET) {
			defaultGroup.setDefault(true, worldName);
		} 
	}

}