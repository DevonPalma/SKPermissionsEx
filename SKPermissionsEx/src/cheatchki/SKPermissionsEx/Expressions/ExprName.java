package cheatchki.SKPermissionsEx.Expressions;


import javax.annotation.Nullable;

import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ru.tehkode.permissions.PermissionEntity;

public class ExprName extends SimplePropertyExpression<PermissionEntity, String>{

	static {
		register(ExprName.class, String.class, "name", "permissionentity");
	}
	
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	@Nullable
	public String convert(PermissionEntity arg0) {
		return arg0.getName();
	}

	@Override
	protected String getPropertyName() {
		return "name";
	}
	
}
