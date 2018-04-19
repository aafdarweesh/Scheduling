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
/*
	public void PushToNodeBuffer(int nodeId, Packet newPacket) {
		allNodes.get(nodeId).buffer.Push(this,newPacket);
	}
*/
	public void addToNodeExpected(Packet newPacket) {
		int destination;
		destination = newPacket.destination;
		allNodes.get(destination).expectedPackets.add(newPacket);
	}
	
	/*
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
					this.allNodes.get(source).buffer.packetQueue.add(currentPacket);
					addToNodeExpected(currentPacket);
				}else{
					currentPacket.arrivelTime++;
				}
			}
		}
		
		
		for(int i = 0; i < allNodes.size(); ++i){
			allNodes.get(i).Run();
		}
		
		
	}
	*/
	public void printToSystem()
	{
		System.out.println("//////////////////////////////////////");
		System.out.println("The time counter   "+timeCounter);
		System.out.println("//////////////////////////////////////");
		for (int i=0;i<allNodes.size();++i)
		{
			System.out.println("Node : "+ allNodes.get(i).id + ", Not Failure  :" + allNodes.get(i).failure + ", The status : "+ allNodes.get(i).status);
		
			 System.out.println("the buffer content   ");
			 allNodes.get(i).buffer.print();
			 System.out.println("the expected Packets content   ");
			 allNodes.get(i).printExpectedPacketsContent();
			 System.out.println();
			 System.out.println("=========================================");
			
		}
		
		 
	}
	
	
	public void LastInfo(){
		System.out.println("numberOfDroppedPackets : " + numberOfDroppedPackets);
		System.out.println("rerequestedPackets : " + rerequestedPackets);
		System.out.println("limitedTimeForPacket : " + limitedTimeForPacket);
		System.out.println("packetDeliveredMoreThanOneTime : " + packetDeliveredMoreThanOneTime);
		
		System.out.println("List of Delievered packets : ");
		for(int i = 0 ; i < delivered.size(); ++i){
			System.out.print("ID "+delivered.get(i).id);
			System.out.print(", Type "+delivered.get(i).type);
			System.out.print(", arrival time "+delivered.get(i).arrivelTime);
			System.out.print(", service time "+delivered.get(i).serviceTime);
			System.out.print(", destination "+delivered.get(i).destination);
			System.out.println(", source "+delivered.get(i).source);
		}
		
		
		System.out.println("List of Dropped packets : ");
		for(int i = 0 ; i < dropped.size(); ++i){
			System.out.print("ID "+dropped.get(i).id);
			System.out.print(", Type "+dropped.get(i).type);
			System.out.print(", arrival time "+dropped.get(i).arrivelTime);
			System.out.print(", service time "+dropped.get(i).serviceTime);
			System.out.print(", destination "+dropped.get(i).destination);
			System.out.println(", source "+dropped.get(i).source);
		}
		
		
	}
	
	
}
