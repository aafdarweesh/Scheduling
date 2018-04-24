package CAR;

import java.util.ArrayList;

public class Server {
	
	GeneralInfo general;
	
	
	Server(GeneralInfo g){
		this.general = g;
	}
	
	public ArrayList<Integer> GetPath(int n, int source, int destination, int[][] connections, int size){
		int[] prev = new int[n];
		int[] dist = new int[n];
		boolean[] check = new boolean[n];
		for(int i = 0; i < n; ++i){
			dist[i] = 2000000000;
			prev[i] = -1; 
			if(general.allNodes.get(i).notfailure == false) check[i] = true;
			else check[i] = false;
		}

		dist[source] = 0;
		
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(source);
		while(arr.size() > 0){
			int u = arr.get(0);
			arr.remove(0);
			
			for(int j = 0; j < n; ++j){
				if(check[j] == false && connections[u][j] == 1 && dist[j] > dist[u] + 1) {
					prev[j] = u;
					dist[j] = dist[u] + 1;
					arr.add(j);
				}
			}
		}
		

		ArrayList<Integer> path = new ArrayList<Integer>();
		int cnt = destination;
		
		System.out.print("path from  "+ source + " to "+destination);
	
		for(int i = 0; i < n; ++i){
			System.out.print(" "+ cnt);
			
			path.add(cnt);
			if(cnt == source || cnt > n) break;
			cnt = prev[cnt];
		}
		
		System.out.print("\n");
		
		return path;
	}
	

}
