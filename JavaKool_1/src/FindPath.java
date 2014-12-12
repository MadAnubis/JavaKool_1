import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FindPath {
	
	private int size;
	private char[][] in_matrix;
	private Container[][] cont_matrix;
	private List<Container> cont_list;
	
	public FindPath() {}
	
	private char[][] parseInput(String in) {
		char[][] t_matrix = new char[size][size];
		int x, y, counter;
		
		counter = 0;
		
		for( x = 0; x < size; ++x) {
			for(y = 0; y < size; ++y) {
				t_matrix[x][y] = in.charAt(counter);
				counter++;
			}
			counter++;
		}
		
		return t_matrix;
	}
	
	private Container[][] matrixToContainer() {
		Container[][] matrix = new Container[size][size];
		
		for(int i = 0; i < size; ++i){
			for(int j = 0; j < size; ++j){
				matrix[i][j] = new Container();
				matrix[i][j].value_ = (int)in_matrix[i][j];
				matrix[i][j].x_ = i;
				matrix[i][j].y_ = j;
				matrix[i][j].used_ = false;
				
				if(in_matrix[i][j] == 'B' || in_matrix[i][j] == 'F') {
					continue;
				}else if(in_matrix[i][j] < '0' || in_matrix[i][j] > '9') {
					matrix[i][j].value_ = -1;
				}else {
					matrix[i][j].value_ -= '0';
				}
			}
		}
		
		return matrix;
	}
	
	private Container findValue(char value) {
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				if(in_matrix[i][j] == value)
					return cont_matrix[i][j];
			}
		}
		return null;
	}
	
	private void addElement(Container in, List<Container> list) {
		if(in.used_ || in.value_ < 0 || in.value_ > 9)
			return;
		
		in.used_ = true;
		list.add(in);
	}
	
	private List<Container> createToplogicalOrder() {
		List<Container> temp = new ArrayList<Container>();
		int counter = 0;
		
		temp.add(findValue('F'));
		
		while(counter < temp.size()) {
			
			int x = temp.get(counter).x_;
			int y = temp.get(counter).y_;
			
			if(x - 1 >= 0)
				addElement(cont_matrix[x - 1][y], temp);
			if(x + 1 < size)
				addElement(cont_matrix[x + 1][y], temp);
			if(y - 1 >= 0)
				addElement(cont_matrix[x][y - 1], temp); 
			if(y + 1 < size)
				addElement(cont_matrix[x][y + 1], temp);
			
			counter++;
		}
		temp.add(findValue('B'));
		return temp;
	}
	
	private void calcSum(Container old_cont, Container new_cont) {
		if((new_cont.value_ < 0 || new_cont.value_ > 9) && new_cont.value_ != 'B')
			return;
		
		int temp_sum = old_cont.last_sum_ + new_cont.value_;
		
		if(new_cont.last_sum_ == 0 || new_cont.last_sum_ > temp_sum) {
			new_cont.last_sum_ = temp_sum;
		}
	}

	private void calcDijkstra(List<Container> in) {
		int x, y;
		
		for(int c = 0; c < in.size() - 1; ++c) {
			x = in.get(c).x_;
			y = in.get(c).y_;
			
			if(x - 1 >= 0)
				calcSum(cont_matrix[x][y], cont_matrix[x - 1][y]);
			if(x + 1 < size)
				calcSum(cont_matrix[x][y], cont_matrix[x + 1][y]);
			if(y - 1 >= 0)
				calcSum(cont_matrix[x][y], cont_matrix[x][y - 1]);
			if(y + 1 < size)
				calcSum(cont_matrix[x][y], cont_matrix[x][y + 1]);
		}
	}

	private boolean drawPath(Container in) {
		int x = in.x_;
		int y = in.y_;
		Container temp = in;
			
		while(true) {
			if(x - 1 >= 0)
				if(temp.last_sum_ > cont_matrix[x - 1][y].last_sum_ 
						&& cont_matrix[x - 1][y].value_ > 0)
					temp = cont_matrix[x - 1][y];
			if(x + 1 < size)
				if(temp.last_sum_ > cont_matrix[x + 1][y].last_sum_
						&& cont_matrix[x + 1][y].value_ > 0)
					temp = cont_matrix[x + 1][y];
			if(y - 1 >= 0)
				if(temp.last_sum_ > cont_matrix[x][y - 1].last_sum_
						&& cont_matrix[x][y - 1].value_ > 0)
					temp = cont_matrix[x][y - 1];
			if(y + 1 < size)
				if(temp.last_sum_ > cont_matrix[x][y + 1].last_sum_
						&& cont_matrix[x][y + 1].value_ > 0)
					temp = cont_matrix[x][y + 1];
			
			if(temp.value_ == 'F')
				break;
			
			x = temp.x_;
			y = temp.y_;
			in_matrix[x][y] = '*';
		}
		

		return false;
	}

	private String genOutput() {
		String temp = new String();
		temp = "";
		
		for(int i = 0; i < size; ++i) {
			for(int j = 0; j < size; ++j) {
				temp += in_matrix[i][j];
			}
			temp+= "\n";
		}
		
		return temp;
	}

	public String calculatePath(String in_string, int m_size) {

		size = m_size;
		in_matrix = parseInput(in_string);
		cont_matrix = matrixToContainer();
		cont_list = createToplogicalOrder();
		calcDijkstra(cont_list);
		drawPath(cont_list.get(cont_list.size() - 1));		

		return genOutput();
	}



}
