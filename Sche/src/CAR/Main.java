package CAR;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		
		GeneralInfo g = new GeneralInfo();
		
		Server server = new Server(g);
		Generator generator = new Generator(g,server);
		/*
		int n = 5;
		int source = 3;
		int destination = 0;
		int[][] conn = {{0,0,1,0,0},{1,0,1,1,1}, {1,1,0,1,0}, {0,1,1,0,1}, {0,1,0,1,0}};
		//System.out.println(conn[0].length);
		int[] path = server.GetPath(n, source, destination, conn);
		for(int i = 0; i < path.length; ++i){
			System.out.println(path[i]);
		}
		*/
		
		generator.Run();
		int counter = 0;
		while(counter++ < 1000){
					
			//genaratedPackets
			g.timeCounter++; //change the current time
			
			
			
			for(int i = 0; i < generator.totalNumOfPackets; ++i){
				Packet currentPacket = generator.genaratedPackets.get(i);
				if(currentPacket.arrivelTime == g.timeCounter){
					
					int source = currentPacket.source;
					//int destination = currentPacket.destination;
					
					boolean checkIfFullBuffer = g.allNodes.get(source).buffer.full();
					if(checkIfFullBuffer == false){
						g.allNodes.get(source).buffer.Push(currentPacket, g);
						g.addToNodeExpected(currentPacket);
					}else{
						currentPacket.arrivelTime++;
					}
				}
			}
			
			
			for(int i = 0; i < g.allNodes.size(); ++i){
				g.allNodes.get(i).Run();
			}
			
			
			//g.printToSystem();
		}
		g.LastInfo();
		
		
	}
}
