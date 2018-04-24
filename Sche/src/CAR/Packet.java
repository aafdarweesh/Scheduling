package CAR;

import java.util.ArrayList;

public class Packet {
	int type;
	int destination;
	int source;
	ArrayList<Integer> path;
	int arrivelTime;
	int serviceTime;
	int startServiceTime; 
	int distanceTraveled; 
	int size; 
	int id; 
	
	
	Packet(int ty, int des, int so, int arrT, int sT, int size, int id){
		this.type = ty;
		this.destination = des;
		this.source = so;
		this.arrivelTime = arrT;
		this.serviceTime = sT;
		this.startServiceTime = 0;
		this.distanceTraveled = 0;
		this.size = size;
		this.id = id;
		path = new ArrayList<Integer>();
	}
	
	
}


