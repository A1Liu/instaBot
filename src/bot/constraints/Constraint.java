package bot.constraints;

import instagram.Post;

/**
 * This functional interface represents a constraint for the Instagram bot to consider when liking a post.
 * Constraint objects affect the behavior of the bot when liking posts:
 * 
 * shouldLike() will only return true if all constraints are met, or if an overriding constraint is met.
 * canLike() will only return true if all required constraints are met, or if an overriding constraint is met.
 * canUnlike() will only return true if no overriding constraints are met.
 * 
 * Note:
 * All the methods can be overriden. All constraints are checked at runtime.
 * Also, it is highly discouraged to create a constraint that is both required and overriding, as such a constraint would essentially have final say. In the case of two or more of such constraints,
 * without a consensus, it would be up to random chance (whichever constraint is seen first at runtime)
 * @author aliu
 *
 */
public interface Constraint {

	/**
	 * Holds logic that determines whether this constraint is met or not.
	 * @param post the post to judge
	 * @return true if the post meets this constraint (based off of only this constraint, we should like this post).
	 */
	public boolean like(Post post);

	/**
	 * Determines whether or not this constraint is an absolute requirement. If this is true, then a result of false from the canLike() method prevents liking.
	 * If this is false, this constraint object will be ignored by the canLike() method
	 * @return true if this constraint is an absolute requirement to liking
	 */
	default boolean isRequired(Post post) {
		return false;
	}
	
	/**
	 * Determines whether or not this constraint can force a like. If this is true, then a result of true from the this object's canLike() method forces liking, and overrides required constraints
	 * @return true if this constraint overrides others.
	 */
	default boolean isOverriding(Post post) {
		return false;
	}
}
