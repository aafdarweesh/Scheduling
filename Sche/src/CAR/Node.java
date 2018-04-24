package CAR;

import java.util.ArrayList;

public class Node {
	int id; 
	PacketQueue buffer; 
	Packet currentPacket; 
	boolean notfailure; 
	boolean status; 
	Server server; 
	GeneralInfo generalInfo; 
	ArrayList<Packet> expectedPackets;

	Node(int newid, Server s, GeneralInfo g) {
		this.id = newid;
		this.buffer = new PacketQueue();
		this.notfailure = true;
		this.status = true;
		this.server = s; 
		this.generalInfo = g;
		this.expectedPackets = new ArrayList<Packet>();
	}

	void Run() {

		
		System.out.println("id : " + this.id + " status : "+ this.status + " bufferSize : " + this.buffer.packetQueue.size() + " expected : " + this.expectedPackets.size());
		
		if (status == true) {
			
			if (buffer.empty() == false) {
				this.currentPacket = buffer.Pop(this.id);
				
				
				if (this.currentPacket.destination == this.id) {

					this.status = false; 
					this.currentPacket.startServiceTime = generalInfo.timeCounter; 
					boolean checkDeliveredBefore = true;
					for (int i = 0; i < this.expectedPackets.size(); ++i) {
						if (this.currentPacket.id == expectedPackets.get(i).id) {
							expectedPackets.remove(i);
							checkDeliveredBefore = false;
						}
					}
					if (checkDeliveredBefore == true) {
						generalInfo.packetDeliveredMoreThanOneTime++; 
						this.status = true; 
					}

					
				} else if (this.currentPacket.path.size() > 0 && this.currentPacket.path.get(this.currentPacket.path.size()-1) == this.id) {
					
					this.currentPacket.path.remove(this.currentPacket.path.size()-1);
					
					
					this.status = true;
					int nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size()-1);
					
					if (generalInfo.allNodes.get(nextNodeId).notfailure == true) {
						currentPacket.distanceTraveled++;
						generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);
						
					} else {


						ArrayList<Integer> newPath = server.GetPath(
								generalInfo.allNodes.size(), this.id,
								this.currentPacket.destination,
								generalInfo.nodesConnections,
								this.currentPacket.size);
						if (newPath.size() > 0 && newPath.get(newPath.size()-1) == this.id) {
							
							newPath.remove(newPath.size()-1);
							
							this.currentPacket.path = newPath;
							nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size()-1);
							currentPacket.distanceTraveled++;
							generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);

						} else {

							/*if (generalInfo.timeCounter - currentPacket.arrivelTime >= generalInfo.limitedTimeForPacket) {
								System.out.println("Buffer time exceeded at Node : "+ this.id);
								System.out.println("Packet id" + (currentPacket.id)
										+ ", source : " + currentPacket.source
										+ " destination : "
										+ currentPacket.destination);
								
								generalInfo.dropped.add(currentPacket);
								generalInfo.numberOfDroppedPackets++;
							} else {*/
								currentPacket.distanceTraveled++;
								this.buffer.Push(currentPacket, generalInfo);
							//}
						}

					}
					
				} else {
					this.status = true;
					
					ArrayList<Integer> newPath = server.GetPath(
							generalInfo.allNodes.size(), this.id,
							this.currentPacket.destination,
							generalInfo.nodesConnections,
							this.currentPacket.size);
					if (newPath.size() > 0 && newPath.get(newPath.size()-1) == this.id) {
						this.currentPacket.path = newPath; 
						int nextNodeId = this.currentPacket.path.get(this.currentPacket.path.size()-1);
						
						currentPacket.distanceTraveled++;
						generalInfo.allNodes.get(nextNodeId).buffer.Push(currentPacket, generalInfo);
						
						
						
					} else {

						/*if (generalInfo.timeCounter - currentPacket.arrivelTime >= generalInfo.limitedTimeForPacket) {
							System.out
									.println("Buffer time exceeded at Node : "
											+ this.id);
							System.out.println("Packet id" + (currentPacket.id)
									+ ", source : " + currentPacket.source
									+ " destination : "
									+ currentPacket.destination);
							
							generalInfo.dropped.add(currentPacket);
							generalInfo.numberOfDroppedPackets++;
						} else {*/
							currentPacket.distanceTraveled++;
							this.buffer.Push(currentPacket, generalInfo);
						//}
					}

				}
			}
		} 
		
		else {
			
			int remainingTime = generalInfo.timeCounter
					- this.currentPacket.startServiceTime;
			
			if (remainingTime >= this.currentPacket.serviceTime - 1) {

				generalInfo.delivered.add(this.currentPacket); 

				this.status = true;
			}

		}

		for (int i = 0; i < expectedPackets.size(); ++i) {
			if (generalInfo.timeCounter - expectedPackets.get(i).arrivelTime >= generalInfo.limitedTimeForPacket) {
				expectedPackets.get(i).arrivelTime = generalInfo.timeCounter;

				generalInfo.rerequestedPackets++;

				generalInfo.allNodes.get(expectedPackets.get(i).source)
						.RequestedPacket(expectedPackets.get(i));
			}
		}

	}


	void RequestedPacket(Packet newPacket) {
		Packet p = new Packet(newPacket.type, newPacket.destination,
				newPacket.source, generalInfo.timeCounter,
				newPacket.serviceTime, newPacket.size, newPacket.id);
		
		this.buffer.Push(p, generalInfo);
	}

	public void printExpectedPacketsContent() {
		for (int i = 0; i < expectedPackets.size(); ++i) {
			System.out.print(" Packets ID " + expectedPackets.get(i).id);
			System.out.print(", Packets arrivel Time "
					+ expectedPackets.get(i).arrivelTime);
			System.out.print(", Packets service Time "
					+ expectedPackets.get(i).serviceTime);
			System.out.print(", Packets destination "
					+ expectedPackets.get(i).destination);
			System.out.print(", Packets source  "
					+ expectedPackets.get(i).source);

		}
	}
}
