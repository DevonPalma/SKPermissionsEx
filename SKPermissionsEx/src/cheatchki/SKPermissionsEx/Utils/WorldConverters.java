package cheatchki.SKPermissionsEx.Utils;

import java.lang.reflect.Array;
import java.util.Arrays;

import org.bukkit.World;

/**
 * I do not take credit for this work, this belongs to Peter Güttinger and Bensku
 * this is simply a minor rewrite of his original work under the Skript plugin
 * to better suit my needs, for more info please read under 
 * the skript source code 
 * 
 * from file: skript/registrations/Converters.java
 * 
 * @author cheatchki
 *
 */

public class WorldConverters {
	@SuppressWarnings("unchecked")
	public final static <F, T> T[] convertUnsafe(final World world, final F[] from, final Class<?> to, final WorldConverter<? super F, ? extends T> conv) {
		return convert(world, from, (Class<T>) to, conv);
	}
	
	public final static <F, T> T[] convert(final World world, final F[] from, final Class<T> to, final WorldConverter<? super F, ? extends T> conv) {
		@SuppressWarnings("unchecked")
		T[] ts = (T[]) Array.newInstance(to, from.length);
		int j = 0;
		for (int i = 0; i < from.length; i++) {
			final F f = from[i];
			final T t = f == null ? null : conv.convert(world, f);
			if (t != null)
				ts[j++] = t;
		}
		if (j != ts.length)
			ts = Arrays.copyOf(ts, j);
		assert ts != null;
		return ts;
	}
}
