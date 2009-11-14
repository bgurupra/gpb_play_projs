package game;

public class ColoringGame {
	
	public static int MATRIX_SIZE = 8;
	
	public static void main(String[] args){
		
		int[][] colorGraph = new int[MATRIX_SIZE][MATRIX_SIZE];
		populateColorGraph(colorGraph);
		printColorGraph(colorGraph);
		colorTheGraph(colorGraph);
		
	}

	/**
	 * 
	 * @param colorGraph
	 */
	protected static void colorTheGraph(int[][] colorGraph) {
		//find list of adjacent ColorPoints to the block from (0,0) whose color is different from (0,0) color
		//find the color which has the maximum number of ColorPoints
		//color the (0,0) block with that color
		//repeat till all ColorPoints have the same color
		
	}

	/**
	 * Prints the content of the ColorGraph
	 * @param colorGraph
	 */
	protected static void printColorGraph(int[][] colorGraph) {
		for (int i = 0; i < colorGraph.length;i++){
			for (int j = 0; j < colorGraph.length;j++ ){
				System.out.print(colorGraph[i][j]+" ") ;
			}
			System.out.println();
		}
		
	}

	/**
	 * Populates the ColorGraph with random colors
	 * @param colorGraph
	 */
	protected static void populateColorGraph(int[][] colorGraph) {
		for (int i = 0; i < colorGraph.length;i++){
			for (int j = 0; j < colorGraph.length;j++ ){
				colorGraph[i][j] = getRandomColor();
			}
		}
		
	}
	
	/**
	 * Returns a random color between 1 to 6
	 * @return
	 */
	protected static int getRandomColor(){
		double rand = Math.random();
		int color = 0;
		if(rand > 0 && rand < .15){
			color = 1;
		}else if(rand >=.15 && rand < .3){
			color = 2;
		}else if(rand >=.3 && rand < .45){
			color = 3;
		}else if(rand >=.45 && rand < .6){
			color = 4;
		}else if(rand >=.6 && rand < .75){
			color = 5;
		}else if(rand >=.75 && rand < 1){
			color = 6;
		}
		
		return color;
	}

}
