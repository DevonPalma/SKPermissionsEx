package cheatchki.SKPermissionsEx.Effects;

import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EffPromotionDemotion extends Effect{

	static {
		Skript.registerEffect(EffPromotionDemotion.class, 
				"[pex] promote %permissionuser% [on] ladder %string%",
				"[pex] demote %permissionuser% [on] ladder %string%");
	}
	
	private Expression<PermissionUser> user;
	private Expression<String> ladder;
	private boolean promote;
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] arg0, int arg1, Kleenean arg2, ParseResult arg3) {
		user = (Expression<PermissionUser>) arg0[0];
		ladder = (Expression<String>) arg0[1];
		promote = arg1 == 0;
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "promote/demote user";
	}

	@Override
	protected void execute(Event arg0) {
		PermissionUser user = this.user.getSingle(arg0);
		String ladder = this.ladder.getSingle(arg0);

		int userRank = user.getRank(ladder);
		
		
		PermissionGroup sourceGroup = user.getRankLadders().get(ladder);
		PermissionGroup targetGroup = null;
		
		if (promote) {
			for (Map.Entry<Integer, PermissionGroup> entry : PermissionsEx.getPermissionManager().getRankLadder(ladder).entrySet()) {
				int groupRank = entry.getValue().getRank();
				if (groupRank >= userRank) 
					continue;
				if (targetGroup != null && groupRank <= targetGroup.getRank())
					continue;
				targetGroup = entry.getValue();
				
			}
		} else {
			for (Map.Entry<Integer, PermissionGroup> entry : PermissionsEx.getPermissionManager().getRankLadder(ladder).entrySet()) {
				int groupRank = entry.getValue().getRank();
				if (groupRank <= userRank) 
					continue;
				if (targetGroup != null && groupRank >= targetGroup.getRank())
					continue;
				targetGroup = entry.getValue();
			}
			
		}
		if (targetGroup != null) {
			user.removeGroup(sourceGroup);
			user.addGroup(targetGroup);
		}
		
	}
	
}
