import java.io.File;
import java.util.List;
import de.hsrm.mi.prog2.TextIO;

public class Sudokuloeser {

	int[][] grit;
	int size = 0;
	int squareSize = 0;
	final int EMPTY = 0;
	
	public Sudokuloeser() {
		this.grit = laden();
	}

	// Liest aus einer Datei unser Sudoku aus und setzt andere Parameter wie groesse
	// usw.
	// War zu faul um das in den Konstruktor zu packen 

	int[][] laden() {

		int[][] loadedGrit = null;
		try {
			List<String> sudoku = TextIO.read(new File("sudoku3.txt"));
			this.size = sudoku.size();
			this.squareSize = (int) Math.sqrt(size);
			loadedGrit = grit(sudoku);
		} catch (Exception e) {
			System.out.println("Kein Sudoku vorhanden");
		}
		return loadedGrit;
	}
	// Erstellt das grit auf dem wir arbeiten.
	int[][] grit(List<String> sudoku) {

		int i = 0;
		int j = 0;
		int[][] grit = new int[size][size];
		for (String line : sudoku) {
			String[] splitLine = line.split(",");
			for (String element : splitLine) {
				grit[i][j] = Integer.parseInt(element);
				j++;
			}
			j = 0;
			i++;
		}
		return grit;
	}
	// Methoden zum checken von Zeilen, Spalten und Kaestchen
	boolean overAllCheck(int row, int column, int number) {
		if (checkRow(row, number) && checkColumn(column, number) && checkSquare(row, column, number))
			return true;
		return false;
	}
	boolean checkRow(int row, int number) {
		for (int i = 0; i < size; i++) {
			if (grit[row][i] == number)
				return false;
		}
		return true;
	}
	boolean checkColumn(int column, int number) {
		for (int i = 0; i < size; i++) {
			if (grit[i][column] == number)
				return false;
		}
		return true;
	}
	boolean checkSquare(int row, int column, int number) {
		int rowBig = row / squareSize;
		int bigStartRow = rowBig*squareSize;
		int columnBig = column / squareSize;
		int bigStartCol = columnBig*squareSize;

		for (int i = bigStartRow; i < bigStartRow + squareSize; i++) {
			for (int j = bigStartCol; j < bigStartCol + squareSize; j++) {
				if (grit[i][j] == number)
					return false;
			}
		}
		return true;
	}
	public boolean solve() {
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				if (grit[row][column] == EMPTY) {
					for (int num = 1; num <= size; num++) {
						if (overAllCheck(row, column, num)) {
							grit[row][column] = num;
							if (solve()) {
								return true;
							} else {
								grit[row][column] = EMPTY;
							}
						}
					}
					return false;
				}				
			}			
		}
		return true;
	}
	public void print() {
		for (int i = 0; i < grit.length; i++) {
			for (int j = 0; j < grit[i].length; j++) {				
				if ((j + 1) % squareSize == 0) {					
					System.out.print(grit[i][j] + " :: ");					
				} else {					
					System.out.print(grit[i][j] + " | ");
				}	
			}			
			if ((i + 1) % squareSize == 0) {				
				System.out.println();				
				for (int k = 0; k < grit[i].length; k++) {					
					System.out.print("----");					
				}
			}			
			System.out.println();
		}
	}
}
