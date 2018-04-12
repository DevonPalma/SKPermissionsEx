package cheatchki.SKPermissionsEx.Effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ru.tehkode.permissions.PermissionEntity;

public class EffCreateEntity extends Effect{

	static {
		Skript.registerEffect(EffCreateEntity.class, "[pex] create %permissionentity%");
	}
	
	private Expression<PermissionEntity> entity;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		entity = (Expression<PermissionEntity>) arg0[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "create player/group";
	}

	@Override
	protected void execute(Event arg0) {
		entity.getSingle(arg0).save();
	}

}
