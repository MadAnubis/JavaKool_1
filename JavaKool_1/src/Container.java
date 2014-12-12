/*
 * Container to hold matrix values for easier management.
 */
public class Container {
	public int value_;
	public int x_;
	public int y_;
	public int last_sum_;
	public Container last_sum_link;
	public char link_;
	public boolean used_;
	
	public Container() {
		value_ = 0;
		x_ = 0;
		y_ = 0;
		last_sum_ = 0;
		used_ = false;		
	}
}
