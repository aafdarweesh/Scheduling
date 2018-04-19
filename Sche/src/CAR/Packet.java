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
	
	
	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDestination() {
		return destination;
	}
	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int[] getPath() {
		return path;
	}
	public void setPath(int[] path) {
		this.path = path;
	}
	public int getPointInPath() {
		return pointInPath;
	}
	public void setPointInPath(int pointInPath) {
		this.pointInPath = pointInPath;
	}
	public int getArrivelTime() {
		return arrivelTime;
	}
	public void setArrivelTime(int arrivelTime) {
		this.arrivelTime = arrivelTime;
	}
	public int getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	
}


