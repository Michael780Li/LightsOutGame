import java.io.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class SwingV9 implements ActionListener {
	JButton[][] board = new JButton[5][5];
	JButton newGame=new JButton("New Game");
	JButton solve=new JButton("Solve");
	JButton reset=new JButton("Reset");
	
	JButton howToPlay=new JButton("How To Play");
	static JLabel username=new JLabel();
	JFrame f=new JFrame("Lights Out");	
	JLabel solution=new JLabel();
	JLabel turn=new JLabel();
	JLabel win=new JLabel();
	JLabel winNumTurns=new JLabel();
	JLabel highScore=new JLabel();
	JLabel scores=new JLabel();
	JLabel rankings=new JLabel();
	JPanel rectScores=new JPanel();
	JLabel clickNewGame=new JLabel();
	
	static String Name="";


	int countTurn=0;
	int score;
	String boardLabels[]=new String[26];
	
	//answer before user makes andy moves
	String answer;

	public SwingV9() throws IOException{
		
		
		
		BoardSetup.main();
		boardLabels= readBoardText(boardLabels);
		answer=boardLabels[25];

		f.setSize(555,550);
		f.setLayout(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		
		
		
		
		
		//Title
		JLabel l1=new JLabel("LIGHTS OUT");  
		l1.setFont(new Font("Cooper Black", Font.BOLD, 20));
		l1.setBounds(163,15, 150,30);  
		f.add(l1);

		//Buttons
		howToPlay.setBounds(410,415,105,30);
		f.add(howToPlay);
		howToPlay.setForeground(Color.blue);
		howToPlay.addActionListener(this);
		
		newGame.setBounds(90,50,95,30);
		f.add(newGame);
		newGame.addActionListener(this);

		reset.setBounds(185,50,95,30);
		f.add(reset);
		reset.addActionListener(this);

		solve.setBounds(280,50,95,30);
		f.add(solve);
		solve.addActionListener(this);




		//turn number
		turn.setText("Turn "+countTurn);
		turn.setBounds(212,80, 400,30); 
		f.add(turn);
		f.repaint();

		//Board

		for (int i = 0 ; i < board.length ; i++){
			for (int j = 0 ; j < board[i].length ; j++){

				board [j] [i] = new JButton ("");
				board[j][i].setText (boardLabels[i*5+j]);
				board [j] [i].setBounds (j * 60+85,	i * 60+150, 60, 60);
				board[j][i].addActionListener(this);
				f.add (board [j] [i]);	

			}
		}


		//High Scores


		rectScores.setLayout(null);
		rectScores.setBackground(Color.pink);
		rectScores.setBorder(BorderFactory.createLineBorder(Color.black));
		rectScores.setBounds(405, 50,110,170);
		f.add(rectScores);

		highScore.setText("High Scores");
		highScore.setBounds(20,-60, 100,150);
		rectScores.add(highScore);


		//turn off all buttons besides new game
		reset.setEnabled(false);
		solve.setEnabled(false);
		for (int i = 0 ; i < board.length ; i++){
			for (int j = 0 ; j < board[i].length ; j++){
				board[i][j].setEnabled(false);	

			}
		}


		for (int i = 0 ; i < board.length ; i++){
			for (int j = 0 ; j < board[i].length ; j++){

				board[i][j].setBackground(Color.gray);
				f.repaint();
			}
		}

		//direction to click newGame
		clickNewGame.setText("CLICK NEW GAME TO BEGIN");
		clickNewGame.setFont(new Font("Cooper Black", Font.PLAIN, 12));
		clickNewGame.setForeground(Color.red);
		clickNewGame.setBounds(140,100,400,30); 
		f.add(clickNewGame);
	}

	public String[] readBoardText(String [] boardLabels) throws IOException{
		BufferedReader input = new BufferedReader (new FileReader ("Board.txt")); 
		String line;
		int i=0;
		line = input.readLine ();  //Read a String.
		while (line!=null){  //File is terminated by a null.
			boardLabels[i]=line;
			line = input.readLine ( );  //Read next line.
			i=i+1;
		}
		input.close();

		return boardLabels;
	}

	public void buttonLightColour(int i, int j, String onOff) {

		if(onOff.equals("1")) {
			board[i][j].setBackground(Color.green);
		}else {
			board[i][j].setBackground(Color.black);
		}

	}


	public void win(JButton board[][]) throws IOException {
		int count=0;
		for (int a = 0 ; a < board.length ; a++){
			for (int b = 0 ; b < board[a].length ; b++){
				if (board[b][a].getText().equals("0")) {
					count++;
				}
			}
		}

		//Win notification/ disable buttons
		if (count==25) {    
			win.setText("YOU WON");	   
			win.setForeground(Color.red);
			win.setBounds(205,120, 400,30); 
			f.add(win);
			f.repaint();
			for (int w = 0 ; w < board.length ; w++){
				for (int q = 0 ; q < board[w].length ; q++){
					board[q][w].setEnabled(false);

				}
			}
			
			score=countTurn;
			
			winNumTurns.setText("It took "+countTurn+" moves to win");	   
			winNumTurns.setBounds(160,450, 400,30); 
			f.add(winNumTurns);
			f.repaint();
		
			highScore(score);
		}
	}

	//update score board
	public void highScore (int score) throws IOException {
		ScoreBoard.main(score, "update");	//update Score Board
		score=0;				//reset score to zero

		paintScores();
	}

	//paint score board

	public void paintScores() throws IOException {
		BufferedReader input = new BufferedReader (new FileReader ("ScoreBoard.txt")); 

		String line;
		int number;
		int[]rank=new int [10];
		int i=0;
		line = input.readLine ( );  //Read a String.
		while (line!=null){  //File is terminated by a null.
			number = Integer.parseInt (line); 
			rank[i]=number;
			line = input.readLine ( );  //Read next line.
			i=i+1;
		}
		input.close();


		String list="<html>";
		for (int j=0; j<rank.length;j++) {
			if (rank[j]!=0) {
				list= list+rank[j]+"<p>";
			}
		}
		scores.setText(list);
		scores.setVerticalAlignment(JLabel.TOP);
		scores.setBounds(20,30, 100,150);
		rectScores.add(scores);






	}

	public static void main(String[] args) throws IOException {
		SwingV9 obj1 = new SwingV9();
	
		Name=JOptionPane.showInputDialog(null, "Please enter a username:");
		if (Name==null) {
			obj1.f.setVisible(false);
		}else {
			obj1.f.setVisible(true);
			username.setText("Username:   "+Name);
			username.setBounds(5,1, 150,30);  
			obj1.f.add(username);
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		JButton action = (JButton)e.getSource();
		
		
		
		if (action==howToPlay) {
			JOptionPane.showMessageDialog(null,
				    "Objective: Turn off all the lights <on=red> <off=black>\nThe square you click on will turn trigger itself and bordering buttons in a 't' formation",
				    "INSTRUCTIONS:",
				    JOptionPane.INFORMATION_MESSAGE);
			
		}
		
		
		
		if (action==newGame) {
			reset.setEnabled(true);
			solve.setEnabled(true);
			try {
				BoardSetup.main();
				boardLabels= readBoardText(boardLabels);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			for (int i = 0 ; i < board.length ; i++){
				for (int j = 0 ; j < board[i].length ; j++){
					board[j][i].setText (boardLabels[i*5+j]);
					board[j][i].setEnabled(true);
				}
			}
			for (int i = 0 ; i < board.length ; i++){
				for (int j = 0 ; j < board[i].length ; j++){

					buttonLightColour(i,j,board[i][j].getText());
					f.repaint();
				}
			}
			clickNewGame.setText("");
			solution.setText("");
			win.setText("");
			winNumTurns.setText("");
			
			countTurn=0;
			turn.setText("Turn "+countTurn);

			//clear Score Board
			scores.setText("");
			try {
				ScoreBoard.main(0, "clear");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//take answer for future reference
			answer=boardLabels[25];
			resetBorders(); //discard solution from previous games

		}
		if (action==reset) {
			//reset
			win.setText("");
			solution.setText("");
			winNumTurns.setText("");
			for (int i = 0 ; i < board.length ; i++){
				for (int j = 0 ; j < board[i].length ; j++){
					board[j][i].setText (boardLabels[i*5+j]);
					board[j][i].setEnabled(true);

				}
			}	

			countTurn=0;
			turn.setText("Turn "+countTurn);
			for (int i = 0 ; i < board.length ; i++){
				for (int j = 0 ; j < board[i].length ; j++){

					buttonLightColour(i,j,board[i][j].getText());
					f.repaint();
				}
			}
			
			//reset answers to what it was before user made any moves
			boardLabels[25]=answer;
			
			resetBorders(); //discard solution from previous games
		}
		if (action==solve) {
			//reorder solution (taking into account new moves made by user)
			boardLabels[25]=BoardSetup.sortAppliedMoves(boardLabels[25]);
			
			String answerLightUp[]=boardLabels[25].split(" ");
			
			for (int i=0; i<answerLightUp.length; i++) {
				int square=Integer.parseInt(answerLightUp[i]);
				
				for (int q = 0 ; q < board.length ; q++){
					for (int w = 0 ; w < board[1].length ; w++){
						if ((q+1+5*w)==square) {
							board[q][w].setBorder(BorderFactory.createLineBorder(Color.red, 2));
						}
					}
				}
			}


		}

		for(int a = 0; a<board.length;a++) {
			for(int b = 0; b<board[a].length;b++) {

				if (action == board[a][b]) {
					board[a][b].setBorder(BorderFactory.createLineBorder(Color.gray, 1)); //get rid of yellow border from solution
					boardLabels[25]=boardLabels[25]+(a+1+5*b)+" ";
					board[a][b].setText(switchNums(board[a][b].getText()));
					if (!(a==0)) {
						board[a-1][b].setText(""+switchNums(board[a-1][b].getText()));
						//account for clicks at border of board:
					}
					if (!(a==board.length-1)) {
						board[a+1][b].setText(switchNums(board[a+1][b].getText()));
					}
					if (!(b==0)) {
						board[a][b-1].setText(switchNums(board[a][b-1].getText()));
					}
					if (!(b==board[0].length-1)) {
						board[a][b+1].setText(switchNums(board[a][b+1].getText()));
					}
					countTurn++;

					turn.setText("Turn "+countTurn);	   


					try {
						win(board);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}


					for (int i = 0 ; i < board.length ; i++){
						for (int j = 0 ; j < board[i].length ; j++){

							buttonLightColour(i,j,board[i][j].getText());
							f.repaint();
						}
					}
				} 
			}
		}

	}

	public void resetBorders() {
		for (int q = 0 ; q < board.length ; q++){
			for (int w = 0 ; w < board[1].length ; w++){
					board[q][w].setBorder(BorderFactory.createLineBorder(Color.gray));
			}
		}
	}
	public static String switchNums(String num) {
		if (num.equals("0")) {
			return "1";
		}else {
			return "0";
		}
	}
}




