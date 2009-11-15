package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ColoringGame {

	public static int MATRIX_SIZE = 3;

	protected static List<ColorPoint> boundary = null;

	protected static List<ColorPoint> sameColorBrothers = null;

	protected static ColorPoint[][] colorGraph = new ColorPoint[MATRIX_SIZE][MATRIX_SIZE];

	public static void main(String[] args) {

		populateColorGraph();
		//printColorGraph();
		int k = 0 ;
		do{
			boundary = new ArrayList<ColorPoint>();
			sameColorBrothers = new ArrayList<ColorPoint>();
			sameColorBrothers.add(colorGraph[0][0]);
			calculateBoundaryAndSameColorBrothers(null,colorGraph[0][0]);
			printColorGraph(sameColorBrothers,boundary);
			colorTheGraph();
			//printColorGraph();
			k++;
		}while(boundary.size() != 0 && k < 4);
	}

	/**
	 * 
	 * @param colorGraph
	 */
	protected static void colorTheGraph() {
		int[] colorFrequency = {0,0,0,0,0,0};
		Iterator<ColorPoint>  iter= null;
		if(boundary.size() > 0){
			iter= boundary.iterator();
			while(iter.hasNext()){
				ColorPoint p = iter.next();
				colorFrequency[p.getColor()-1]++;
			}
		}
		int maxFreqColor = -1;
		for(int i = 0 ; i < colorFrequency.length;i++){
			//System.out.println("Color = "+(i+1)+"Color Frequency = "+ colorFrequency[i]);
			if(colorFrequency[i] > maxFreqColor){
				maxFreqColor = i+1;
			}
		}
		
		System.out.println("Maximum Frequency Color = "+maxFreqColor);
		
		iter = sameColorBrothers.iterator();
		while(iter.hasNext()){
			ColorPoint p = iter.next();
			p.setColor(maxFreqColor);
		}
	}


	protected static void calculateBoundaryAndSameColorBrothers(
			ColorPoint comingFromPoint, ColorPoint colorPoint) {
		List<ColorPoint> adjacentPointsForColorPoint = null;
		List<ColorPoint> adjacentPointsForComingFromPoint = null;
		printColorGraph(sameColorBrothers,boundary);
		if (comingFromPoint != null) {
			adjacentPointsForComingFromPoint = getAdjacentColoringPoints(
					comingFromPoint.getX(), comingFromPoint.getY());
			//printColorGraph(adjacentPointsForComingFromPoint);
			adjacentPointsForComingFromPoint.add(comingFromPoint);
		}

		adjacentPointsForColorPoint = getAdjacentColoringPoints(colorPoint
				.getX(), colorPoint.getY());
		//printColorGraph(adjacentPointsForColorPoint);

		List<ColorPoint> pointsToCheckFurther = comingFromPoint == null ? adjacentPointsForColorPoint
				: getInAnotInB(adjacentPointsForColorPoint,
						adjacentPointsForComingFromPoint);
		//printColorGraph(pointsToCheckFurther);

		Iterator<ColorPoint> iter = pointsToCheckFurther.iterator();
		while(iter.hasNext()){
			ColorPoint p = iter.next();
			if(p.getColor() == colorGraph[0][0].getColor()){
				sameColorBrothers.add(p);
				calculateBoundaryAndSameColorBrothers(colorPoint,p);
			}else{
				boundary.add(p);
			}
		}
	
	}

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
		double rand = Math.random();
		int color = 1;
/*		if (rand > 0 && rand < .15) {
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
*/
		return color;
	}

}
