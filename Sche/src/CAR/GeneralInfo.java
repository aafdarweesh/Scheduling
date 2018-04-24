package CAR;

import java.util.ArrayList;
import java.util.Random;

public class GeneralInfo {
	int timeCounter;
	int numberOfDroppedPackets; 
	int rerequestedPackets; 
	int limitedTimeForPacket; 
	int packetDeliveredMoreThanOneTime; 

	ArrayList<Node> allNodes;
	ArrayList<Packet> delivered; 
	ArrayList<Packet> dropped;
	int[][] nodesConnections; 
	GeneralInfo() {
		this.timeCounter = 0;
		this.numberOfDroppedPackets = 0;
		this.rerequestedPackets = 0;
		this.limitedTimeForPacket = 20; 
		this.packetDeliveredMoreThanOneTime = 0;

		this.allNodes = new ArrayList<Node>();
		this.delivered = new ArrayList<Packet>();
		this.dropped = new ArrayList<Packet>();

	}

	public boolean CheckNodeStatus(int nodeId) {
		return this.allNodes.get(nodeId).notfailure;
	}

	public void addToNodeExpected(Packet newPacket) {
		int destination;
		destination = newPacket.destination;
		allNodes.get(destination).expectedPackets.add(newPacket);
	}
	

	public void printToSystem()
	{
		System.out.println("//////////////////////////////////////");
		System.out.println("The time counter   "+timeCounter);
		System.out.println("//////////////////////////////////////");
		for (int i=0;i<allNodes.size();++i)
		{
			System.out.println("Node : "+ allNodes.get(i).id + ", Not Failure  :" + allNodes.get(i).notfailure + ", The status : "+ allNodes.get(i).status);
		
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
		
		System.out.println("List of Delievered packets : " + this.delivered.size());
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
