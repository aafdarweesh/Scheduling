package CAR;

import java.util.ArrayList;

public class Server {
	
	GeneralInfo general;
	
	
	Server(GeneralInfo g){
		this.general = g;
	}
	
	//this function checks the shortest path between two nodes and return the path starting from the following node as a start not from the source node 
	//so the path pointer should start from 0
	public int[] GetPath(int n, int source, int destination, int[][] connections, int size){
		int[] prev = new int[n];
		int[] dist = new int[n];
		boolean[] check = new boolean[n];
		for(int i = 0; i < n; ++i){
			dist[i] = 2000000000;
			prev[i] = -1; 
			check[i] = false;
		}
		//System.out.println(connections[0].length);
		dist[source] = 0;
		
		//Dijkstra’s shortest path algorithm
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
		
		//System.out.println(dist[destination]);
		//System.out.println("\n\n\n");
		int[] path = new int[dist[destination]];
		int cnt = destination;
		//path[0] = cnt;
		for(int i = dist[destination] - 1; i > 0 ; --i){
			cnt = prev[cnt];
			path[i-1] = cnt;
		}
		path[dist[destination] - 1] = destination;
		
		return path;
	}
	

}
