package CAR;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		int[] arr = {};
		ArrayList<Integer> arrayList = null;
		System.out.println(arr.length);
		
		GeneralInfo g = new GeneralInfo();
		Server s = new Server(g);
		
		int n = 5;
		int source = 3;
		int destination = 0;
		int[][] conn = {{0,0,1,0,0},{1,0,1,1,1}, {1,1,0,1,0}, {0,1,1,0,1}, {0,1,0,1,0}};
		//System.out.println(conn[0].length);
		int[] path = s.GetPath(n, source, destination, conn);
		for(int i = 0; i < path.length; ++i){
			System.out.println(path[i]);
		}
		
		
	}
}
