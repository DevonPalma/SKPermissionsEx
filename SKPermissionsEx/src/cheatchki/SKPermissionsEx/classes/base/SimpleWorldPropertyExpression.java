package cheatchki.SKPermissionsEx.classes.base;

import javax.annotation.Nullable;

import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import cheatchki.SKPermissionsEx.Utils.WorldConverter;

/**
 * The ideas and methods used in here stem from the work of Peter Güttinger and Bensku
 * this is simply an extension of his original work under the Skript plugin
 * to better suit my needs, for more info please read under 
 * the skript source code 
 * 
 * from file: skript/expressions/base/SimplePropertyExpression.java
 * 
 * @author cheatchki
 *
 */

public abstract class SimpleWorldPropertyExpression<F, T> extends WorldPropertyExpression<F, T> implements WorldConverter<F, T> {
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(final Expression<?>[] exprs, final int matchedPattern, final Kleenean isDelay, final ParseResult parseResult) {
		setExpr((Expression<? extends F>) exprs[0]);
		setWorld((Expression<World>) exprs[1]);
		return true;
	}
	
	public abstract String getPropertyName();
	
	@Override
	@Nullable
	public abstract T convert(World world, F f);
	
	@Override
	protected T[] get(final Event e, final F[] source, final World world) {
		return super.get(world, source, this);
	}
	
	@Override
	public String toString(final @Nullable Event e, final boolean debug) {
		return "the " + getPropertyName() 
			+ " of " +  getExpr().toString(e, debug); 
//			+ " in world " + CheatsUtils.getName(getWorld(e));
	}
}
