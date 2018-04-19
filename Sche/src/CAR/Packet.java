package CAR;

public class Packet {
	int type;
	int destination;
	int source;
	int[] path = {};
	int pointInPath;
	int arrivelTime;
	int serviceTime;
	//added
	int startServiceTime; //the starting time of execution for this packet
	int distanceTraveled; //the total distance that the packet has traveled so far, it is needed to calculate the queuing time
	int size; //the size of the Packet
	boolean hasPath; //check if the packet has path or not
	int id; //packet id to track the packet 
	
	
	Packet(int ty, int des, int so, int arrT, int sT, int size, int id){
		this.type = ty;
		this.destination = des;
		this.source = so;
		this.pointInPath = 0;
		this.arrivelTime = arrT;
		this.serviceTime = sT;
		this.startServiceTime = 0;
		this.distanceTraveled = 0;
		this.hasPath = false;
		this.size = size;
		this.id = id;
	}
	
	
}


