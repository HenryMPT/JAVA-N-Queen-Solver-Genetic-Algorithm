import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class BoardGraphics extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int nQueens;
	public int width;
	public int height;
	public int cellSize = 30;
	
	/**
	 * 
	 * @param n The number of queens
	 * @param wWidth The width of the window
	 * @param wHeight The height of the window
	 */
	public BoardGraphics(int n, int wWidth, int wHeight)
	{
		nQueens = n;
		width = wWidth;
		height = wHeight;
	}
	

	 
	 /**
	  * 
	  * Places Queens using images. Not being used atm
	  */
	 public void placeQueensImage(Graphics g, Individual queens)
	 {
		 BufferedImage img = null;
		 try {
		     img = ImageIO.read(new File("queen.jpg"));
		 } catch (IOException e) {
		 }
		 g.setColor(Color.green);
		 for(int i = 1; i <= nQueens; i++)
			 for(int j = 2; j <= nQueens; j++)
	         {
				 g.drawOval(i*cellSize + 5, j*cellSize + 5, 10, 20);
				 g.drawImage(img, i*cellSize + 5, j*cellSize + 5,  null);
	         }
		 
	 }
	 
	 /**
	  *  Places Queens on the board as green circles, unless they have a conflict, in which case
	  *  they are represented as red circles. 
	  * @param g The graphics object
	  * @param ind The Individual representing the board
	  */
	 public void placeQueens(Graphics g, Individual ind)
	 {
		 g.setColor(Color.green);
		 ArrayList<Integer> queens = ind.Chromo;
		 for(int i = 0; i < queens.size(); i ++)
		 {
			 
			 if(ind.checkLeftDiagonal(i, queens.get(i)) > 0 || ind.checkRightDiagonal(i, queens.get(i)) > 0  )
			 { 
			   g.setColor(Color.red);
			   g.drawOval(queens.get(i)*cellSize + cellSize + 10, i*cellSize + 2*cellSize + 2, 10, 20);
			   g.setColor(Color.green);
			 }
			 else
			 {
				 g.drawOval(queens.get(i)*cellSize + cellSize + 10, i*cellSize + 2*cellSize + 2, 10, 20);
			 }
		 }
		 
		 
	 }
	 
	 
	 
	 /**
	  * Draws a matrix representing a Chess Board.
	  * @param g
	  */
	 public void drawBoard(Graphics g) 
	 {
		 g.setColor(Color.black);
		 for(int i = 1; i < nQueens+3; i++)
         {
			 if(i < nQueens+2)
        	 g.drawLine(i*cellSize, 2*cellSize, i*cellSize, (nQueens+2) *cellSize );  
        	
        	 g.drawLine(cellSize, i*cellSize , (nQueens+1) * cellSize , i*cellSize);

         }
		
	 }
	 
	 /*
	  * Displays stats on the Window
	  */
	 public void showStats(Graphics g, int gen, int conf, double avgConf, int width, int height)
	 {
		 g.setColor(Color.blue);
		 g.drawString("Gen " + gen, width-200, height-500);
		 g.setColor(Color.red);
		 g.drawString("Board Conflicts " + conf,width-200, height-450);
		 g.drawString("Average Conflicts " + avgConf,width-200, height-400);
	 }
	 
	 /*
	  * Resets the window
	  *  Adds 500 to width and height to account for possible window resizing. 
	  */
	 public void clearBoard(Graphics g)
	 {
		 g.clearRect(0, 0, width+500, height+500);  
		 
	 }
	
	
}
