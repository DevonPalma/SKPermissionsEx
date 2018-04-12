package cheatchki.SKPermissionsEx.classes;


import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import ru.tehkode.permissions.PermissionEntity;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class types {
	static {
		Classes.registerClass(new ClassInfo<>(PermissionEntity.class, "permissionentity")
				.user("(pex|perm(ission)?)?entit(y|ies)")
				.name("Permission Entity")
				.description("A Permission Entity is the base class for a the PermissionsEx types")
				.usage("to be done")
				.examples("to be done")
				.since("0.4.3")
				.parser(new Parser<PermissionEntity>() {

					@Override
					public String getVariableNamePattern() {
						return "pexentity:.*";
					}

					@Override
					public String toString(PermissionEntity arg0, int arg1) {
						return arg0.getName();
					}

					@Override
					public String toVariableNameString(PermissionEntity arg0) {
						return "pexentity:" + arg0.getIdentifier();
					}

					@Override
					public boolean canParse(ParseContext context) {
						return false;
					}

					@Override
					public PermissionEntity parse(String s, ParseContext context) {
						return null;
					}
				})
				.serializer(new Serializer<PermissionEntity>() {

					@Override
					protected boolean canBeInstantiated() {
						return true;
					}

					@Override
					public void deserialize(PermissionEntity arg0, Fields arg1)
							throws StreamCorruptedException, NotSerializableException {
						assert arg0.getType() != null;
						switch(arg0.getType()) {
						case GROUP:
							arg0 = PermissionsEx.getPermissionManager().getGroup((String) arg1.getPrimitive("Identifier"));
							break;
						case USER:
							arg0 = PermissionsEx.getPermissionManager().getUser((String) arg1.getPrimitive("Identifier"));
							break;
						}
						
					}

					@Override
					public boolean mustSyncDeserialization() {
						return false;
					}

					@Override
					public Fields serialize(PermissionEntity arg0) throws NotSerializableException {
						Fields f = new Fields();
						f.putPrimitive("Identifier", arg0.getIdentifier());
						return f;
					}
					
				})
				);
		Classes.registerClass(new ClassInfo<>(PermissionGroup.class, "permissiongroup")
				.user("(pex|perm(ission)?)?groups?")
				.name("Permission Group")
				.description("A Permission Group is a PermissionsEx entity")
				.usage("to be done")
				.examples("to be done")
				.since("0.4.3")
				.serializeAs(PermissionEntity.class)
				);
		
		Classes.registerClass(new ClassInfo<>(PermissionUser.class, "permissionuser")
				.user("(pex|perm(ission)?)?users?")
				.name("Permission User")
				.description("A Permission User is a PermissionsEx entity which refers to a player")
				.usage("to be done")
				.examples("to be done")
				.since("0.4.3")
				.serializeAs(PermissionEntity.class)
				);
	}
}
