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
	int[][] nodesConnections;

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

	// this function will define the connection between the nodes
	public void GenerateConnectionBetweenNodes() {
		int n = this.allNodes.size();
		this.nodesConnections = new int[n][n];
		Random rand = new Random();
		int rando;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {

				if (i == j || this.nodesConnections[i][j] == 1)
					continue;
				this.nodesConnections[i][j] = this.nodesConnections[j][i] = 0;
				rando = rand.nextInt(50) + 1;
				if (rando > 35)
					this.nodesConnections[i][j] = this.nodesConnections[j][i] = 1;
			}
		}
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
}
