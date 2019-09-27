package sudoku;

import java.lang.reflect.Array;
import java.util.*;


public class Solver 
{
	private Grid						problem;
	private ArrayList<Grid>				solutions;
	
	
	public Solver(Grid problem)
	{
		this.problem = problem;
	}
	
	
	public void solve()
	{
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}
	
		
	//if the grid is not legal, solver abandons the grid.
	//if the grid is legal and full, solver accepts the current grid.
	//and if the solver is legal but not full, solver continues to solve the grid using recursion
	private void solveRecurse(Grid grid)
	{		
		Evaluation eval = evaluate(grid);
		
		if (eval == Evaluation.ABANDON)
		{
			return;
		}
		else if (eval == Evaluation.ACCEPT)
		{
			solutions.add(grid);
		}
		else
		{
			if(eval == Evaluation.CONTINUE) {
				ArrayList<Grid> nextGrids = grid.next9Grids();
				for(Grid g: nextGrids) {
					solveRecurse(g);
				}
			}
		}
	}
	
	//depending on the state of the grid, returns an enum
	//ABANDON if not legal
	//ACCEPT if legal and complete
	//CONTINUE if legal and incomplete
	public Evaluation evaluate(Grid grid)
	{
		if(!grid.isLegal()) {
			return Evaluation.ABANDON;
		}
		else if(grid.isLegal() && grid.isFull()) {
			return Evaluation.ACCEPT;
		}
		else {
			return Evaluation.CONTINUE;
		}
	}

	
	public ArrayList<Grid> getSolutions()
	{
		return solutions;
	}
	
	
	public static void main(String[] args)
	{
		Grid g = TestGridSupplier.getPuzzle3();		// or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Will solve");
		System.out.println(g);
		solver.solve();
		System.out.println(solver.getSolutions().get(0));
		
		
		if(solver.getSolutions().get(0).equals(TestGridSupplier.getSolution3())) {
			System.out.println("Solved");
		}
	}
}
