package CAR;

import java.util.ArrayList;

public class PacketQueue {
	ArrayList<Packet> packetQueue;
	int num = 10;
	
	
	PacketQueue() {
		this.packetQueue = new ArrayList<Packet>();
	}

	public Packet Pop(int id) {
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

	public void Push(Packet newPacket, GeneralInfo g) {
		//System.out.println("ID "+newPacket.id);
		if(this.full() == true) {
		
			System.out.println("meeee"+this.packetQueue.size());
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
