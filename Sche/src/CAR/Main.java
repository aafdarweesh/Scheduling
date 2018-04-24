package CAR;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		
		GeneralInfo g = new GeneralInfo();
		
		Server server = new Server(g);
		Generator generator = new Generator(g,server);

		
		generator.Run();
		int counter = 0;
		
		
		for(int i = 0; i < g.allNodes.size(); ++i){
			
			for(int j= 0; j < g.allNodes.size(); ++j){
				
				System.out.print(" "+ g.nodesConnections[i][j]);
				
			}
			System.out.print("\n");
			
		}
		
		while(counter++ < 100){
			
			if(g.delivered.size() == generator.totalNumOfPackets) break;
					
			System.out.println("=======================");
			
			
			for(int i = 0; i < generator.totalNumOfPackets; ++i){
				Packet currentPacket = generator.genaratedPackets.get(i);
				if(currentPacket.arrivelTime == g.timeCounter){
					
					int source = currentPacket.source;
					
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
			
			g.timeCounter++; //change the current time
			
			
			//g.printToSystem();
		}
		g.LastInfo();
		
		
	}
}
