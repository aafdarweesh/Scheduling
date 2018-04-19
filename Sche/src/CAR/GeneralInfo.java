package CAR;

import java.util.ArrayList;
import java.util.Random;

public class GeneralInfo {
	int timeCounter;
	int numberOfDroppedPackets; // number of packets that have been dropped
	int rerequestedPackets; // number of re-requested packets (should equal the
							// number of dropped packets
	int limitedTimeForPacket; // the maximum time the packet can take in the
								// system before requesting sending the packet
								// again
	int packetDeliveredMoreThanOneTime; // the number of packets delivered more
										// than one time

	ArrayList<Node> allNodes;
	ArrayList<Packet> delivered; // list of delivered packets
	ArrayList<Packet> dropped;
	int[][] nodesConnections; //the links between the nodes

	GeneralInfo() {
		this.timeCounter = 0;
		this.numberOfDroppedPackets = 0;
		this.rerequestedPackets = 0;
		this.limitedTimeForPacket = 20; // the maximum time the packet can take
										// in the system before requesting
										// another packet
		this.packetDeliveredMoreThanOneTime = 0;

		this.allNodes = new ArrayList<Node>();
		this.delivered = new ArrayList<Packet>();
		this.dropped = new ArrayList<Packet>();

	}

	public boolean CheckNodeStatus(int nodeId) {
		return this.allNodes.get(nodeId).failure;
	}

	public void PushToNodeBuffer(int nodeId, Packet newPacket) {
		allNodes.get(nodeId).buffer.Push(newPacket);
	}

	public void addToNodeExpected(Packet newPacket) {
		int destination;
		destination = newPacket.destination;
		allNodes.get(destination).expectedPackets.add(newPacket);
	}
	
	
	public void Run(Generator generator){
		//genaratedPackets
		this.timeCounter++; //change the current time
		
		
		
		for(int i = 0; i < generator.totalNumOfPackets; ++i){
			Packet currentPacket = generator.genaratedPackets.get(i);
			if(currentPacket.arrivelTime == this.timeCounter){
				
				int source = currentPacket.source;
				//int destination = currentPacket.destination;
				
				boolean checkIfFullBuffer = this.allNodes.get(source).buffer.full();
				if(checkIfFullBuffer == false){
					this.allNodes.get(source).buffer.Push(currentPacket);
					addToNodeExpected(currentPacket);
				}else{
					currentPacket.arrivelTime++;
				}
			}
		}
		
		
	}
	
	
}
