package bot.constraints;

import instagram.Post;

/**
 * This is a required, non-overriding constraint. The constructor accepts lambda arguments.
 * @author aliu
 *
 */
public abstract class RequiredConstraint implements Constraint {

	private Constraint constraint;
	
	public RequiredConstraint(Constraint constraint) {
		this.constraint = constraint;
	}
	
	@Override
	public boolean like(Post post) {
		return constraint.like(post);
	}
	
	@Override
	public final boolean isRequired(Post post) {
		return true;
	}
	
	@Override
	public final boolean isOverriding(Post post) {
		return false;
	}
}
