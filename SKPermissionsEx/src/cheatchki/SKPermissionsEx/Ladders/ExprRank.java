package cheatchki.SKPermissionsEx.Ladders;


import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.List;
import java.util.Map;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.skript.registrations.Converters;
import ch.njol.yggdrasil.Fields;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ExprRank {
	static {
		Classes.registerClass(new ClassInfo<>(PermissionGroup.class, "permissiongroup")
				.user("permissiongroup")
				.name("PermissionGroup")
				.description("A PermissionGroup is a permission based entity from PermissionsEx")
				.usage("")
				.examples("")
				.since("0.5")
				.defaultExpression(new EventValueExpression<>(PermissionGroup.class))
				.parser(new Parser<PermissionGroup>() {

					
					@Override
					public String getVariableNamePattern() {
						return ".+";
					}

					@Override
					public String toString(PermissionGroup arg0, int arg1) {
						return arg0.getName();
					}

					@Override
					public String toVariableNameString(PermissionGroup arg0) {
						return arg0.getIdentifier().toUpperCase();
					}

					@Override
					public PermissionGroup parse(String s, ParseContext context) {
						PermissionGroup g = PermissionsEx.getPermissionManager().getGroup(s);
						return g;
					}

					@Override
					public boolean canParse(ParseContext context) {
						return true;
					}
					
				}).serializer(new Serializer<PermissionGroup>() {

					@Override
					protected boolean canBeInstantiated() {
						return true;
					}

					@Override
					public void deserialize(PermissionGroup arg0, Fields arg1)
							throws StreamCorruptedException, NotSerializableException {
						
						arg0.setPermissions((List<String>) arg1.getObject("permissions"));
						arg0.setPrefix((String) arg1.getPrimitive("prefix"), null);
						arg0.setSuffix((String) arg1.getPrimitive("suffix"), null);
						arg0.setWeight((int) arg1.getPrimitive("weight"));
						Map<String, String> options = (Map<String, String>) arg1.getObject("options");
						for (String key : options.keySet()) {
							arg0.setOption(key, options.get(key));
						}
						arg0.setParents((List<PermissionGroup>) arg1.getObject("parents"));
					}
					
					

					@Override
					public boolean mustSyncDeserialization() {
						return true;
					}

					@Override
					public Fields serialize(PermissionGroup arg0) throws NotSerializableException {
						// TODO Auto-generated method stub
						Fields f = new Fields();
						f.putPrimitive("name", arg0.getName());
						f.putObject("permissions", arg0.getPermissions(null));
						f.putPrimitive("prefix", arg0.getPrefix());
						f.putPrimitive("suffix", arg0.getSuffix());
						f.putPrimitive("weight", arg0.getWeight());
						f.putObject("options", arg0.getOptions(null));
						f.putObject("parents", arg0.getOwnParents());
						return f;
					}

					@SuppressWarnings("unchecked")
					@Override
					protected PermissionGroup deserialize(Fields fields)
							throws StreamCorruptedException, NotSerializableException {
						PermissionGroup g = PermissionsEx.getPermissionManager().getGroup((String) fields.getPrimitive("name"));
						deserialize(g, fields);
						return g;
					}
					
				}));
	}
}
