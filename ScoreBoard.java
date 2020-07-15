import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ScoreBoard {

	public static void main(int score, String updateOrClear) throws IOException {
		BufferedReader input = new BufferedReader (new FileReader ("ScoreBoard.txt")); 
		final int NUMRANKS=11;
		
		if (updateOrClear.equals("update")){
			//read current scoreboard
			String line;
			int number;
			int[]rank=new int [11];
			int i=0;
			line = input.readLine ( );  //Read a String.
			while (line!=null){  //File is terminated by a null.
		    		number = Integer.parseInt (line); 
		    		rank[i]=number;
		    		line = input.readLine ( );  //Read next line.
		    		i=i+1;
			}
			input.close();
			//add in new score
			rank[i]=score;
			
			//sort scores for lowest to greatest
			int temp;
			for (int a=0; a<NUMRANKS-1;a++) {	
				for (int b=0; b<NUMRANKS-a-1; b++) {
					if (rank[b+1]<rank[b]) {
						temp=rank[b];
						rank[b]=rank[b+1];
						rank[b+1]=temp;
					}

				}
			}
	for (int a=0;a<NUMRANKS;a++) {
		System.out.println(rank[a]);
	}
	
		PrintWriter output = new PrintWriter (new FileWriter ("ScoreBoard.txt"));
		int sc;
		for (int c = 0 ; c <10 ; c++)	{
		    	sc = rank[c];
		    if (sc!=0) {
		    	output.println (sc);
		    }
	  	}
		//count numbers on board
		int countNum=0;
		for (int d=0; d<10;d++) {
			if (rank[d]!=0) {
				countNum++;
			}
		}
		if (countNum!=10) {
			sc=rank[10];
			output.println (sc);
		}
		
		
	    output.close ( );



		}else if(updateOrClear.equals("clear")){
			PrintWriter output = new PrintWriter (new FileWriter ("ScoreBoard.txt"));		
			output.println();
		}
		
		
		
	}

}




