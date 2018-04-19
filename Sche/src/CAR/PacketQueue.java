package CAR;

import java.util.ArrayList;

public class PacketQueue {
	ArrayList<Packet> packetQueue;
	int num = 1; // the number of items the queue should hold
	//GeneralInfo generalInfo;
	
	
	PacketQueue() {
		this.packetQueue = new ArrayList<Packet>();
	}

	public Packet Pop() {
		Packet temp;
		temp = this.packetQueue.get(0);
		this.packetQueue.remove(0);
		return temp;
	}

	public boolean full() {
		int length;
		length = packetQueue.size();
		if (length == num)
			return true;

		return false;
	}

	public void print() {
		for (int i = 0; i < packetQueue.size(); i++) {
			System.out.print("ID "+packetQueue.get(i).id);
			System.out.print(", Type "+packetQueue.get(i).type);
			System.out.print(", arrival time "+packetQueue.get(i).arrivelTime);
			System.out.print(", service time "+packetQueue.get(i).serviceTime);
			System.out.print(", destination "+packetQueue.get(i).destination);
			System.out.println(", source "+packetQueue.get(i).source);
			
		}
	}

	public boolean empty() {

		int length;
		length = packetQueue.size();
		if (length == 0)
			return true;

		return false;
	}

	// here we should check if it reaches the capacity will drop the packet and
	// not add it to the queue
	public void Push(Packet newPacket, GeneralInfo g) {
		//System.out.println("ID "+newPacket.id);
		if(this.full() == true) {
			g.dropped.add(newPacket);
			g.numberOfDroppedPackets++;
			//System.out.println("Dropped");
		}
		else {
			//System.out.println("NOT Dropped");
			this.packetQueue.add(newPacket);
		}
	}
}
