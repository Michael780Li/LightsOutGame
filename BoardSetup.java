import java.io.*;
public class BoardSetup {

    public static void main() throws IOException {
        int moves[][]=new int [25][25];
        int board[]=new int[25];
        String[] boardAndAnswer=new String[26];
        moves=posMoves();

        boardAndAnswer=genBoard(moves);
        
        for (int i=0;i<(boardAndAnswer.length-1)/5;i++) {
            for(int j=0; j<(boardAndAnswer.length-1)/5;j++) {
            System.out.print(boardAndAnswer[i*5+j]);
            }
            System.out.println();
        }
        
        writeBoard(boardAndAnswer);
        System.out.print("Solution: ");
        
        System.out.println(boardAndAnswer[25]);
        
        
    }
    
    public static void writeBoard(String[] boardAndAnswer) throws IOException {
    	PrintWriter output = new PrintWriter (new FileWriter ("Board.txt"));
		for (int c = 0 ; c <boardAndAnswer.length ; c++)	{
			output.println (boardAndAnswer[c]);
		}
		
	    output.close ( );
    }
    
    
    public static int[][] posMoves() {
        int moves[][]=new int [25][25];
        for (int row=0;row<moves.length;row++) {
            for (int column=0; column< moves[0].length;column++) {
                moves[row][column]=0;
            }
        }
                
                
        for (int r=0;r<moves.length;r++) {
            for (int c=0; c< moves[0].length;c++) {
                //central diagonal
                if(c==r) {
                    moves[r][c]=1;
                    
                }
                
                //lines beside central diagonal
                moves[0][1]=1;
                if(!(r%5==0)){
                        moves[r-1][r]=1;
                }
                if(!(r%5==0)){
                    moves[r][r-1]=1;
            }
                
                //diagonal to right of central
                if (c==(5+r)) {
                    moves[r][c]=1;
                }
                //diagonal to left of central
                if(c==(r-5)) {
                    moves[r][c]=1;
                }
            }
        }
        
        return moves;
    }
    //(
    public static String[] genBoard(int moves[][]) {
        int solution[]=new int [20];
        int numMoves=0;
        
        
        int board[]=new int [25];
        String boardAndAnswer[]=new String [26];
        int count=0;
        int move;
        String AppliedMoves="";
        
        do{
            move=(int) (Math.round(Math.random()*24));

            AppliedMoves=((move+1)+" "+AppliedMoves);
            
            
            board=applyMove(moves,board, move);
            count=countLights(board);
        }while( !(count==10));
        
        AppliedMoves=sortAppliedMoves(AppliedMoves);
        for (int i=0;i<board.length;i++) {
            boardAndAnswer[i]=(""+board[i]);
        }
        boardAndAnswer[25]=AppliedMoves;
        
        return boardAndAnswer;
    }
    
    public static String sortAppliedMoves(String AppliedMoves) {
    	String Answer = "";
    	String[] numbers = AppliedMoves.split(" ");
    	int[] moves = new int[numbers.length];
    	for (int i = 0; i < numbers.length; i++) {
        	moves[i] = Integer.parseInt(numbers[i]);
    	}
    	
    	int temp;
    	for (int j=0; j<numbers.length-1; j++){
        	for (int k=0; k<numbers.length-1-j; k++){
            	if (moves[k+1]<moves[k]){
                	temp=moves[k];
                	moves[k]=moves[k+1];
                	moves[k+1]=temp;
            	}
            	
        	}
    	}
    	
    	for (int m=0; m<numbers.length-1; m++){
    		if (moves[m]==moves[m+1]){	
            	moves[m]=0;
            	moves[m+1]=0;
        	}
    	}

    	for(int n=0; n< numbers.length;n++) {
    		if (moves[n]!=0) {
    			Answer=Answer+moves[n]+" ";
    		}
    	}
    	
    	return Answer;
    	
    }
    
    
    
    
    
    
    public static int[] applyMove(int moves[][], int board[], int move) {
        for (int i=0;i<board.length;i++) {
            board[i]=board[i]+moves[move][i];
        }
        board=modulo2(board);
        
        return board;
    }
    public static int[] modulo2(int [] board) {
        for (int i=0;i<board.length;i++) {
            if(board[i]%2==0) {
                board[i]=0;
            }else {
                board[i]=1;
            }
        }
        return board;
    }
    public static int countLights(int [] board) {
        int count=0;
        for(int i=0; i<board.length;i++) {
            if (board[i]==1) {
                count++;
            }
        }
        return count;
    }
    
    //)
}




