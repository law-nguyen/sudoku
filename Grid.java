package sudoku;

import java.util.*;


public class Grid 
{
	private int[][]						values;
	

	//
	// DON'T CHANGE THIS.
	//
	// Constructs a Grid instance from a string[] as provided by TestGridSupplier.
	// See TestGridSupplier for examples of input.
	// Dots in input strings represent 0s in values[][].
	//
	public Grid(String[] rows)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
		{
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i=0; i<9; i++)
			{
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}
	
	
	//
	// DON'T CHANGE THIS.
	//
	public String toString()
	{
		String s = "";
		for (int j=0; j<9; j++)
		{
			for (int i=0; i<9; i++)
			{
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char)('0' + n);
			}
			s += "\n";
		}
		return s;
	}


	//copies the source grid
	Grid(Grid src)
	{
		values = new int[9][9];
		for (int j=0; j<9; j++)
			for (int i=0; i<9; i++)
				values[j][i] = src.values[j][i];
	}
	
	
	//finds an empty cell, then returns an array of grids containing all nine possibilities of what that empty cell could be
	public ArrayList<Grid> next9Grids()
	{		
		int xOfNextEmptyCell =0;
		int yOfNextEmptyCell =0;

		if(this.isFull()) {
			return null;
		}
		// Find x,y of an empty cell.
		else {
			forLoop:
				for(int i = 0; i < values.length; i++) {
					for(int j = 0; j < values[i].length; j++) {
						if(values[i][j] == 0) {
							xOfNextEmptyCell = i;
							yOfNextEmptyCell = j;
							break forLoop;
						}
					}
				}
			
			// Construct array list to contain 9 new grids.
			ArrayList<Grid> grids = new ArrayList<Grid>();
			for(int n = 1; n <= values.length; n++) {
				Grid newGrid = new Grid(this);
				newGrid.values[xOfNextEmptyCell][yOfNextEmptyCell] = n;
				grids.add(newGrid);
			}
		
		

		// Create 9 new grids as described in the comments above. Add them to grids.

			return grids;
		}
	}
	
	
	//
	// COMPLETE THIS
	//
	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// 3x3 block contains a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal()
	{
		// Check every row. If you find an illegal row, return false.
		
		//using sets to find duplicates.
		Set<Integer> row = new HashSet<Integer>();
		row.add(0);
		int count = 0;
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				//if the set  cannot add a value, the value is a duplicate. returns false if duplicate is a non-zero number
				if(row.add(values[i][j]) == false && values[i][j] != 0) {
					return false;
				}
				//increments counter. once counter reaches 9, clears the set so program can check next row
				count++;
				if(count % 9 == 0) {
					row.clear();
				}
			}
		}
		
		
		// Check every column. If you find an illegal column, return false.
		
		//set checks for duplicates
		Set<Integer> column = new HashSet<Integer>();
		column.add(0);
		for(int j = 0; j < values[0].length; j++) {
			for(int i = 0; i < values.length; i++) {
				//again, if the set cannot add a vlue, the value is a duplicate. returns false if duplicate is a non-zero number
				if(column.add(values[i][j]) == false && values[i][j] != 0) {
					return false;
				}
				//increments counter. once counter reaches 9, clears the set so program can check next column.
				count++;
				if(count % 9 == 0) {
					column.clear();
				}
			}
		}
		
		// Check every block. If you find an illegal block, return false.
		
		//using a set again to check for duplicates
		Set<Integer> block = new HashSet<Integer>();
		block.add(0);
		for(int i = 0; i < values.length; i++){
			for(int j = 0; j < values[i].length; j++){
				//breaks grid up into nine 3x3 blocks, then checks if the contents of each block is a non-zero duplicate
				//if so, returns false
				if(block.add(values[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3]) ==  false 
						&& values[(i / 3) * 3 + j / 3][i * 3 % 9 + j % 3] != 0) {
					return false;
				}
				count++;
				if(count % 9 == 0) {
					block.clear();
				}
			}
		}
		
		// All rows/cols/blocks are legal.
		return true;
	}

	
	//returns true if the board is full and all elements are legal integers
	public boolean isFull()
	{
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if(values[i][j] < 1 || values[i][j] > 9) {
					return false;
				}
			}
		}
		return true;
	}
	

	//returns true if all elements in each grid are equal to each other
	public boolean equals(Object x)
	{
		Grid that = (Grid)x;
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				if(values[i][j] != that.values[i][j]) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		TestGridSupplier testGrid = new TestGridSupplier();
		Grid test1 = new Grid(testGrid.getPuzzle1());
		Grid test2 = new Grid(testGrid.getSolution1());
		System.out.println(test1.equals(test2));
		System.out.println(test1.isFull());
		System.out.println(test2.isFull());
		System.out.println(test1.isLegal());
		
	}
}
