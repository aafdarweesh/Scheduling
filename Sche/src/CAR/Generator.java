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
			System.out.print("Enter the number of package of type "+ (i + 1)  + " :");
			in = new Scanner(System.in);
			numOfpacket = in.nextInt();
			numOfPacketList.add(numOfpacket); 
			totalNumOfPackets = totalNumOfPackets + numOfpacket;

		}
	}

	public void storeServiceTimeForPacket() {
		int packageTime;
		for (int i = 0; i < numOfpacketType; i++) 
		{
			System.out.print("\nEnter the service time for package"
					+ (i + 1) + " :");
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

		while (j < numOfpacketType) {
			while (count < numOfPacketList.get(j)) {

				Random rand = new Random();
				Random rand2 = new Random();

				int randomNum = rand.nextInt((numberOfNodes - 1) + 1);
				int randomNum2 = rand2
						.nextInt((numberOfNodes - 1) + 1);

				destination = randomNum;
				source = randomNum2;
				while (source == destination) {
					randomNum = rand.nextInt((numberOfNodes - 1) + 1);
					destination = randomNum;
				}

				Packet p = new Packet(j + 1, destination, source, 0,
						serviceTimeForEachPacket.get(j), 2 * j, idcounter++);
				
				
				System.out.println("Packet id" + (idcounter - 1) + ", source : " + source + " destination : "+ destination);
				
			
				genaratedPackets.add(p);

				count++;
			}

			j++;
			count = 0;

		}
		for (i = 0; i < totalNumOfPackets; i++) 
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

			for (i = 0; i < totalNumOfPackets; i++) {

				genaratedPackets.get(i).arrivelTime = arrivalTime.get(i);

			}

		}
		System.out.println("List of Generated");
		for( i = 0; i < totalNumOfPackets; ++i){
			System.out.print("ID "+genaratedPackets.get(i).id);
			System.out.print(", Type "+genaratedPackets.get(i).type);
			System.out.print(", arrival time "+genaratedPackets.get(i).arrivelTime);
			System.out.print(", service time "+genaratedPackets.get(i).serviceTime);
			System.out.print(", destination "+genaratedPackets.get(i).destination);
			System.out.println(", source "+genaratedPackets.get(i).source);
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
				if (rando > 40)
					general.nodesConnections[i][j] = general.nodesConnections[j][i] = 1;
			}
		}
	}


	void Run() {
		
		GenerateNodes();

		storeNumberofPacket();
		storeServiceTimeForPacket();
		
		storeArrivalRate();
		storeArrivalTime();
		storeGenaratedPackets();
		GenerateConnectionBetweenNodes();
		

	}

}
