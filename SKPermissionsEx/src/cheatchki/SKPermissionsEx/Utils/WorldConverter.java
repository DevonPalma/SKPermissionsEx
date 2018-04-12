package cheatchki.SKPermissionsEx.Utils;

import javax.annotation.Nullable;

import org.bukkit.World;

import ch.njol.skript.classes.Converter;

public interface WorldConverter<F, T> extends Converter<F, T> {
	@Nullable
	public T convert(World world, F f);
	
	public default T convert(F f) {
		return convert(null, f);
	}
}
