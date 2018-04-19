package CAR;

import java.util.ArrayList;

public class Node {
	int id; // id of the node
	PacketQueue buffer; // it is a FIFO queue that holds the packets that this
						// node has received
	Packet currentPacket; // current Packet that is being executed
	boolean failure; // check if there is a failure in this node or not (case1:
						// node out of the range, case2: node has failed)
						// 1 : no failure, 0 : there is a failure (cannot do
						// anything)
	boolean status; // if false means that the node is executing the current
					// node
	Server server; // all nodes will be pointing to (communicating with) the
					// same server
	GeneralInfo generalInfo; // here we store general information about the
								// system (the timer, the current status of the
								// nodes...etc)
	ArrayList<Packet> expectedPackets;

	Node(int newid, Server s, GeneralInfo g) {
		this.id = newid; // the id of the node given in the initialization
		this.buffer = new PacketQueue();
		this.failure = true;
		this.status = true;
		this.server = s; // the main server
		this.generalInfo = g; // the main generalInformation class
		this.expectedPackets = new ArrayList<Packet>();
	}

	void Run() {

		// in case the node is free (means that this node is not executing any
		// packet)
		if (status == true) {
			// check if the buffer is not empty
			if (buffer.empty() == false) {
				this.currentPacket = buffer.Pop();
				// check if the packet that is on the top is mine or not (for
				// this node or not)
				if (this.currentPacket.destination == this.id) {

					this.status = false; // change the status to blocked (CPU
											// status)
					this.currentPacket.startServiceTime = generalInfo.timeCounter; // start
																					// executing
																					// the
																					// packet

					boolean checkDeliveredBefore = true;
					// remove the packet from the expected list of events
					for (int i = 0; i < this.expectedPackets.size(); ++i) {
						if (this.currentPacket.id == expectedPackets.get(i).id) {
							expectedPackets.remove(i);
							checkDeliveredBefore = false;
						}
					}
					// check if I have received the packet before or not
					if (checkDeliveredBefore == true) {
						generalInfo.packetDeliveredMoreThanOneTime++; // Increment
																		// the
																		// value
						this.status = true; // change the status to get the
											// following packet
					}

					// check if there is a path for the packet, then send to the
					// following node in the path
				} else if (this.currentPacket.hasPath == true) {
					int nextNodeId = this.currentPacket.path[this.currentPacket.pointInPath];
					// check of the following node in the path of the packet is
					// available or not
					if (generalInfo.CheckNodeStatus(nextNodeId) == true) {
						generalInfo.PushToNodeBuffer(nextNodeId, currentPacket);
						// means that the following node has a failure
					} else {
						// here we request a new path for the node from the
						// current node to the destination of the packet
						// change the pointer in the path to zero, change the
						// content of the path for that packet
						int[] newPath = server.GetPath(generalInfo.allNodes.size(), this.id, this.currentPacket.destination, generalInfo.nodesConnections, this.currentPacket.size);
						this.currentPacket.path = newPath;
						this.currentPacket.pointInPath = 0; // assuming that the
															// new path has the
															// following node in
															// the first element
						nextNodeId = this.currentPacket.path[0];
						// the check for the status of the following node will
						// take place in the server path check
						generalInfo.PushToNodeBuffer(nextNodeId, currentPacket);

					}
					// in case that this packet is not mine and has no path I
					// will request one from the server and send it
				} else {
					// here we request a new path for the node from the
					// current node to the destination of the packet
					// change the pointer in the path to zero, change the
					// content of the path for that packet
					int[] newPath = server.GetPath(generalInfo.allNodes.size(), this.id, this.currentPacket.destination, generalInfo.nodesConnections, this.currentPacket.size);
					this.currentPacket.path = newPath;
					this.currentPacket.pointInPath = 0; // assuming that the new
														// path has the
														// following node in the
														// first spot
					int nextNodeId = this.currentPacket.path[0];
					// the check for the status of the following node will take
					// place in the server path check
					generalInfo.PushToNodeBuffer(nextNodeId, currentPacket);
				}
			}// if the buffer is empty and the status is free do nothing (no
				// transmitting or executing)
			// now if the status of the CPU is busy (means it is executing
			// another packet
		} else {
			// check if the current packet has finished or not (if yes then
			// change the status to free and do nothing)
			int remainingTime = generalInfo.timeCounter
					- this.currentPacket.startServiceTime;
			// here check if the packet will be finished in the following
			// iteration (change the status, so that it gets the newPacket in
			// the buffer)
			if (remainingTime >= this.currentPacket.serviceTime - 1) {

				generalInfo.delivered.add(this.currentPacket); // adding the
																// current
																// packet to the
																// delivered
																// packets

				this.status = true;
			}

		}

		// check the list of expected packets if they are arrived or not
		for (int i = 0; i < expectedPackets.size(); ++i) {
			if (generalInfo.timeCounter - expectedPackets.get(i).arrivelTime >= generalInfo.limitedTimeForPacket) {
				expectedPackets.get(i).arrivelTime = generalInfo.timeCounter;

				// increment the re-requested packets
				generalInfo.rerequestedPackets++;

				// copy to the buffer of the source node
				generalInfo.allNodes.get(expectedPackets.get(i).source)
						.RequestedPacket(expectedPackets.get(i));
			}
		}

	}

	// request the packet again (reinitialize the values)
	void RequestedPacket(Packet newPacket) {
		Packet p = new Packet(newPacket.type, newPacket.destination,
				newPacket.source, generalInfo.timeCounter,
				newPacket.serviceTime, newPacket.size, newPacket.id);

		this.buffer.Push(p);
	}
}
