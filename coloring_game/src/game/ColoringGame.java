package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * One of the naive solutions to this game http://www.flashbynight.com/drench/
 * @author user
 *
 */
public class ColoringGame {

	/**  
	 * The Size of the color point square Matrix which will have random colors from 1 to 6 filled in
	 */
	public static int MATRIX_SIZE = 14;

	/**
	 * The list color points at the boundary of the block of color points  starting from (0,0) which have the same color as (0,0)
	 */
	protected static List<ColorPoint> boundary = null;

	/**
	 * List of adjacent block of squares starting from (0,0) which have the same color as (0,0)
	 */
	protected static List<ColorPoint> sameColorBrothers = null;

	/**
	 * The randomly generated matrix with colors from 1 to 6
	 */
	protected static ColorPoint[][] colorGraph = new ColorPoint[MATRIX_SIZE][MATRIX_SIZE];

	/**
	 * List of points already checked by the calculateBoundaryAndSameColorBrothers method
	 */
	protected static List<ColorPoint> pointsAlreadyChecked = null;
	/**
	 * If the getRandomColor method should generate a test input instead of random input
	 * Look at the implementation of getRandomColor to see how test input works
	 */
	protected static boolean testMatrixGeneration = false;
	/**
	 * Index to the test input matrix
	 */
	protected static int testMatrixIndex = 0;
	
	/**
	 * Main method
	 * @param args
	 */
	public static void main(String[] args) {

		populateColorGraph();
		int numOfTries = 0;
		do{
			//TODO extremely inefficient to discard these lists every time and create them afresh
			// this  is just a basic version of the program to test the idea, will fix it when I get time
			boundary = new ArrayList<ColorPoint>();
			sameColorBrothers = new ArrayList<ColorPoint>();
			pointsAlreadyChecked = new ArrayList<ColorPoint>();
			//of course the same color brothers starts with the main brother :)
			sameColorBrothers.add(colorGraph[0][0]);
			calculateBoundaryAndSameColorBrothers(colorGraph[0][0]);
			printColorGraph(sameColorBrothers,boundary);
			colorTheGraph();
			numOfTries++;
		}while(boundary.size() != 0);
		System.out.println("Finished in "+numOfTries+" number of colorings");
	}

	/**
	 * This method finds the most frequent color in the list of boundary color points and uses that to color the whole
	 * list of sameColorBrothers
	 * TODO : The better way to do is to find the count of brothers of same color for each of the boundary color points and use the max in that list
	 * to color the sameColorBrothers list
	 * @param colorGraph
	 */
	protected static void colorTheGraph() {
		//we have six colors so we have int array of 6 to keep track of count of boundary colors
		int[] colorFrequency = {0,0,0,0,0,0};
		Iterator<ColorPoint>  iter= null;
		
		if(boundary.size() > 0){
			//count the colors in the boundary
			iter= boundary.iterator();
			while(iter.hasNext()){
				ColorPoint p = iter.next();
				colorFrequency[p.getColor()-1]++;
			}
			
			//find the most frequent color in the boundary
			int maxFreqColor = 0;
			for(int i = 0 ; i < colorFrequency.length;i++){
				if(colorFrequency[i] > maxFreqColor){
					maxFreqColor = i+1;
				}
			}
			
			System.out.println("Selecting the Maximum Frequency Color = "+maxFreqColor);
			//use the color found above to color all the sameColorBrothers
			iter = sameColorBrothers.iterator();
			while(iter.hasNext()){
				ColorPoint p = iter.next();
				p.setColor(maxFreqColor);
			}

		} 

	}

	/**
	 * For a given color point we find all the adjacent color points of same color and add it to  sameColorBrothers global list
	 * and all adjacent color points of different color to boundary global list
	 * @param colorPoint
	 */
	protected static void calculateBoundaryAndSameColorBrothers(ColorPoint colorPoint) {
		List<ColorPoint> adjacentPointsForColorPoint = null;

		adjacentPointsForColorPoint = getAdjacentColoringPoints(colorPoint
				.getX(), colorPoint.getY());

		//need to make sure we don't go in a infinite loop checking the same thing we have checked before
		//obviously every checked point is either in the sameColorBrothers list or boundary list, so we check only
		//points we have not checked before
		List<ColorPoint> pointsToCheckFurther = getInAnotInB(getInAnotInB(adjacentPointsForColorPoint, sameColorBrothers),boundary);

		Iterator<ColorPoint> iter = pointsToCheckFurther.iterator();
		while(iter.hasNext()){
			ColorPoint p = iter.next();
			if(p.getColor() == colorGraph[0][0].getColor()){
				sameColorBrothers.add(p);
				//since if it is same color we need to go further and find the boundary,we recursively call the calculateBoundaryAndSameColorBrothers
				calculateBoundaryAndSameColorBrothers(p);
			}else{
				boundary.add(p);
			}
		}
	
	}

	/**
	 * As the name says list of items in A but not in B
	 * 
	 * @param adjacentPointsForColorPoint
	 * @param adjacentPointsForComingFromPoint
	 * @return
	 */
	protected static List<ColorPoint> getInAnotInB(
			List<ColorPoint> adjacentPointsForColorPoint,
			List<ColorPoint> adjacentPointsForComingFromPoint) {

		List<ColorPoint> inAnotInB = new ArrayList<ColorPoint>();
		Iterator<ColorPoint> iter = adjacentPointsForColorPoint.iterator();
		ColorPoint p = null;
		while (iter.hasNext()) {
			p = iter.next();
			if (!adjacentPointsForComingFromPoint.contains(p)) {
				inAnotInB.add(p);
			}

		}

		return inAnotInB;
	}

	/**
	 * Gets the adjacent points which are valid in the matrix
	 * 
	 * @param xPosition
	 * @param yPosition
	 * @return
	 */
	protected static List<ColorPoint> getAdjacentColoringPoints(int xPosition,
			int yPosition) {
		List<ColorPoint> adjacentColorPoints = new ArrayList<ColorPoint>();
		int x, y;

		x = xPosition - 1;
		y = yPosition;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition + 1;
		y = yPosition;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition;
		y = yPosition - 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition;
		y = yPosition + 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition - 1;
		y = yPosition - 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition + 1;
		y = yPosition + 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition + 1;
		y = yPosition - 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		x = xPosition - 1;
		y = yPosition + 1;
		if (x >= 0 && x < MATRIX_SIZE && y >= 0 && y < MATRIX_SIZE) {
			adjacentColorPoints.add(colorGraph[x][y]);
		}

		return adjacentColorPoints;

	}

	/**
	 * Prints the content of the ColorGraph
	 * 
	 * @param colorGraph
	 */
	protected static void printColorGraph() {
		for (int i = 0; i < colorGraph.length; i++) {
			for (int j = 0; j < colorGraph.length; j++) {
				System.out.print(" " + colorGraph[i][j].getColor() + " " + " ");
			}
			System.out.println();
		}

	}

	/**
	 * Prints the content of the matrix with elements in list surrounded by []
	 * 
	 * @param list
	 */
	protected static void printColorGraph(List<ColorPoint> list) {
		System.out.println();
		for (int i = 0; i < colorGraph.length; i++) {
			for (int j = 0; j < colorGraph.length; j++) {
				if (list.contains(new ColorPoint(i, j))) {
					System.out.print("[" + colorGraph[i][j].getColor() + "]"
							+ " ");
				} else {
					System.out.print(" " + colorGraph[i][j].getColor() + " "
							+ " ");
				}
			}
			System.out.println();
		}

	}

	/**
	 * Prints the content of the matrix with {} around elements in list1 and [] around items in list2
	 * 
	 * @param list1
	 * @param list2
	 */
	protected static void printColorGraph(List<ColorPoint> list1, List<ColorPoint> list2 ) {
		System.out.println();
		for (int i = 0; i < colorGraph.length; i++) {
			for (int j = 0; j < colorGraph.length; j++) {
				if (list1.contains(colorGraph[i][j])) {
					System.out.print("{" + colorGraph[i][j].getColor() + "}"
							+ " ");
				}else if (list2.contains(colorGraph[i][j])) {
					System.out.print("[" + colorGraph[i][j].getColor() + "]"
							+ " ");
				} else {
					System.out.print(" " + colorGraph[i][j].getColor() + " "
							+ " ");
				}
			}
			System.out.println();
		}

	}

	/**
	 * Populates the ColorGraph with random colors
	 * 
	 * @param colorGraph
	 */
	protected static void populateColorGraph() {
		ColorPoint p = null;
		for (int i = 0; i < colorGraph.length; i++) {
			for (int j = 0; j < colorGraph.length; j++) {
				p = new ColorPoint(i, j);
				p.setColor(getRandomColor());
				colorGraph[i][j] = p;
			}
		}

	}

	/**
	 * Returns a random color between 1 to 6
	 * 
	 * @return
	 */
	protected static int getRandomColor() {
		int[] testMatrix = {1,3,5,6};
		
		if(testMatrixGeneration){
			return testMatrix[testMatrixIndex++];
			
			
		}
		double rand = Math.random();
		int color = 1;
		if (rand > 0 && rand < .15) {
			color = 1;
		} else if (rand >= .15 && rand < .3) {
			color = 2;
		} else if (rand >= .3 && rand < .45) {
			color = 3;
		} else if (rand >= .45 && rand < .6) {
			color = 4;
		} else if (rand >= .6 && rand < .75) {
			color = 5;
		} else if (rand >= .75 && rand < 1) {
			color = 6;
		}
		return color;
	}

}
