package bot.extras;

import bot.constraints.RequiredConstraint;
import instagram.Post;

/**
 * This is a required constraint: Post must not be by a user on the blacklist
 * @author aliu
 *
 */
public class Blacklist extends RequiredConstraint {

	String[] list;
	
	Blacklist(String...list) {
		super(null);
		this.list = list;
	}
	
	public final boolean like(Post post) {
		return false;
		
	}



}
