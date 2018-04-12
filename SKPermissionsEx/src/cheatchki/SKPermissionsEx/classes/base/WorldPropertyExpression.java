package cheatchki.SKPermissionsEx.classes.base;


import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.PropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import cheatchki.SKPermissionsEx.Utils.WorldConverter;
import cheatchki.SKPermissionsEx.Utils.WorldConverters;

/**
 * The ideas and methods used in here stem from the work of Peter Güttinger and Bensku
 * this is simply an extension of his original work under the Skript plugin
 * to better suit my needs, for more info please read under 
 * the skript source code 
 * 
 * from file: skript/expressions/base/PropertyExpression.java
 * 
 * @author cheatchki
 *
 */

public abstract class WorldPropertyExpression<F, T> extends PropertyExpression<F, T> {
	
	public static <T> void register(final Class<? extends Expression<T>> c, final Class<T> type, final String property, final String fromType) {
		Skript.registerExpression(c, type, ExpressionType.PROPERTY, 
				"[the] " + property + " of %" + fromType + "%" + " [in [world] %-world%]",
				"%" + fromType + "%'[s] " + property + " [in [world] %-world%]");
	}

	@SuppressWarnings("null")
	private Expression<World> world;
	
	protected final void setWorld(final Expression<World> world) {
		this.world = world;
	}
	
	public final Expression<World> getWorld() {
		return world;
	}
	
	public final World getWorld(Event e) {
		if (getWorld() == null) 
			return null;
		return getWorld().getSingle(e);
	}
	
	@Override
	public T[] get(Event e, F[] source) {
		return get(e, source, world != null ? world.getSingle(e) : null);
	}
	
	// Converts the given source object and the world to the correct type
	protected abstract T[] get(Event e, F[] source, World world);
	
	public T[] get(final World world, final F[] source, final WorldConverter<? super F, ? extends T> converter) {
		assert source != null;
		assert converter != null;
		return WorldConverters.convertUnsafe(world, source, getReturnType(), converter);
	}
	
}
