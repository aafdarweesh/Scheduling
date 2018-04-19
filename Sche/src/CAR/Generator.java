package CAR;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Generator {
	int totalNumOfPackets;
	int numOfpacketType;
	ArrayList<Packet> genaratedPackets;
	ArrayList<Integer> numOfPacketList;
	ArrayList<Integer> serviceTimeForEachPacket;
	ArrayList<Integer> arrivalRate;
	ArrayList<Integer> arrivalTime;
	int numberOfNodes;
	GeneralInfo general;
	Server server;

	Generator(GeneralInfo g, Server s) {
		general = g;
		genaratedPackets = new ArrayList<Packet>();
		numOfPacketList = new ArrayList<Integer>();
		serviceTimeForEachPacket = new ArrayList<Integer>();
		arrivalRate = new ArrayList<Integer>();
		arrivalTime = new ArrayList<Integer>();
		totalNumOfPackets = 0;
		numOfpacketType = 0;
		numberOfNodes = 0;
		server = s;
	}

	public static int getPoissonRandom(double mean) {
		Random r = new Random();
		double L = Math.exp(-mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * r.nextDouble();
			k++;
		} while (p > L);
		return k - 1;
	}

	public void storeNumberofPacket() {
		int numOfpacket;

		System.out
				.print("Enter the number of different type of packet you need to simulate : ");

		Scanner in = new Scanner(System.in);
		numOfpacketType = in.nextInt();

		for (int i = 0; i < numOfpacketType; i++) {
			System.out.print("Enter the number of package of type %d   :" + " "
					+ (i + 1));
			in = new Scanner(System.in);
			numOfpacket = in.nextInt();
			numOfPacketList.add(numOfpacket); // storing the number of packets
												// arriving to the system
			totalNumOfPackets = totalNumOfPackets + numOfpacket;

		}
	}

	public void storeServiceTimeForPacket() {
		int packageTime;
		for (int i = 0; i < numOfpacketType; i++) // storing the service time
													// for each package
		{
			System.out.print("\nEnter the service time for package %d  :"
					+ (i + 1));
			Scanner in = new Scanner(System.in);
			packageTime = in.nextInt();
			serviceTimeForEachPacket.add(packageTime);

		}

	}

	public void storeArrivalRate() {
		int rate;

		for (int i = 0; i < totalNumOfPackets; i++) {
			rate = getPoissonRandom(2);

			arrivalRate.add(rate);

		}

	}

	public void storeArrivalTime() {
		int time = 0;

		for (int i = 0; i < totalNumOfPackets; i++) {
			time = time + arrivalRate.get(i);
			// System.out.println("time "+time);
			arrivalTime.add(time);
		}
	}

	public void storeGenaratedPackets() {
		int destination;
		int source;

		int j = 0;
		int i = 0;
		int count = 0;

		int idcounter = 0;

		/* saving the vauls inside the array */
		while (j < numOfpacketType) {
			while (count < numOfPacketList.get(j)) {

				Random rand = new Random();
				Random rand2 = new Random();

				int randomNum = rand.nextInt((general.allNodes.size() - 1) + 1) + 1;
				int randomNum2 = rand2
						.nextInt((general.allNodes.size() - 1) + 1) + 1;

				destination = randomNum;
				source = randomNum2;
				while (source == destination) {
					randomNum = rand.nextInt((general.allNodes.size() - 1) + 1) + 1;
					destination = randomNum;
				}

				Packet p = new Packet(j + 1, destination, source, 0,
						serviceTimeForEachPacket.get(j), 2 * j, idcounter++);

				/*
				 * p.destination = destination; p.source = source; p.serviceTime
				 * = serviceTimeForEachPacket.get(j); p.type = j + 1;
				 */
				genaratedPackets.add(p);

				count++;
				// i++;
			}

			j++;
			count = 0;

		}
		for (i = 0; i < totalNumOfPackets; i++) // reorganizing the events
												// randomly
		{
			for (j = 0; j < totalNumOfPackets; j++) {

				Random rand = new Random();

				int randIndex = rand.nextInt(totalNumOfPackets);

				Packet temp = genaratedPackets.get(i);
				Packet temp2 = genaratedPackets.get(randIndex);

				genaratedPackets.remove(i);
				genaratedPackets.add(i, temp2);

				genaratedPackets.remove(randIndex);
				genaratedPackets.add(randIndex, temp);

			}

			// adding arrival time
			for (i = 0; i < totalNumOfPackets; i++) {

				genaratedPackets.get(i).arrivelTime = arrivalTime.get(i);

			}

		}
	}

	public void GenerateNodes() {

		System.out.print("\nEnter the number of nodes :");
		Scanner in = new Scanner(System.in);

		numberOfNodes = in.nextInt();

		for (int i = 0; i < numberOfNodes; ++i) {
			Node node = new Node(i, server, general);
			general.allNodes.add(node);
		}

	}

	// this function will define the connection between the nodes
	public void GenerateConnectionBetweenNodes() {
		int n = general.allNodes.size();
		general.nodesConnections = new int[n][n];
		Random rand = new Random();
		int rando;
		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {

				if (i == j || general.nodesConnections[i][j] == 1)
					continue;
				general.nodesConnections[i][j] = general.nodesConnections[j][i] = 0;
				rando = rand.nextInt(50) + 1;
				if (rando > 35)
					general.nodesConnections[i][j] = general.nodesConnections[j][i] = 1;
			}
		}
	}

	void Run() {

		storeNumberofPacket();
		storeServiceTimeForPacket();
		storeArrivalRate();
		storeGenaratedPackets();
		GenerateConnectionBetweenNodes();
		GenerateNodes();

	}

}
