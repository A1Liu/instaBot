package bot.extras;

import bot.constraints.Constraint;
import instagram.Post;

/**
 * This constraint is overriding and not required: any user on the whitelist is immediately absolved from other constraints
 * @author aliu
 *
 */
public class WhiteList implements Constraint {

	String[] list;
	
	public WhiteList(String... list) {
		this.list = list;
	}

	@Override
	public final boolean like(Post post) {
		return false;
	}

	@Override
	public final boolean isRequired(Post post) {
		return false;
	}
	
	@Override
	public final boolean isOverriding(Post post) {
		return true;
	}
}
