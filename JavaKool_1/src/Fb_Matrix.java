
public class Fb_Matrix<T> {
	
	public int x_;
	public int y_;
	public T[][] matrix_;
	
	public Fb_Matrix(int size){
		x_ = size;
		y_ = size;
		
		matrix_ = (T[][])new Object[x_][y_];
	}

}
