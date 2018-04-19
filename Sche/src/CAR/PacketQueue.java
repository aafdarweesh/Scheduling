package CAR;

import java.util.ArrayList;

public class PacketQueue {
	ArrayList<Packet> packetQueue;
	int num = 10; // the number of items the queue should hold
	GeneralInfo generalInfo;
	
	
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
			System.out.println(packetQueue.get(i).type);
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
	public void Push(Packet newPacket) {
		
		if(this.full() == true) {
			generalInfo.dropped.add(newPacket);
			generalInfo.numberOfDroppedPackets++;
		}
		else this.packetQueue.add(newPacket);
	}
}
