package cheatchki.SKPermissionsEx.unused;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class EffTimedPerm extends Effect {

	static {
		Skript.registerEffect(EffTimedPerm.class, 
				"add %string% to %permissionentity%'s permission"
				);
	}
	
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		return false;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void execute(Event arg0) {
		// TODO Auto-generated method stub
	}
 
}