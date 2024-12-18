/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bhaashik.align.sequence;

/**
 *
 * @author anil
 */
public abstract class DynamicProgramming {

   protected String sequence1;
   protected String sequence2;
   protected SequenceCell[][] scoreTable;
   protected boolean tableIsFilledIn;
   protected boolean isInitialized;

   public DynamicProgramming(String sequence1, String sequence2) {
      this.sequence1 = sequence1;
      this.sequence2 = sequence2;
      scoreTable = new SequenceCell[sequence2.length() + 1][sequence1.length() + 1];
   }

   public int[][] getScoreTable() {
      ensureTableIsFilledIn();

      int[][] matrix = new int[scoreTable.length][scoreTable[0].length];
      for (int i = 0; i < matrix.length; i++) {
         for (int j = 0; j < matrix[i].length; j++) {
            matrix[i][j] = scoreTable[i][j].getScore();
         }
      }

      return matrix;
   }

   protected void initializeScores() {
      for (int i = 0; i < scoreTable.length; i++) {
         for (int j = 0; j < scoreTable[i].length; j++) {
            scoreTable[i][j].setScore(getInitialScore(i, j));
         }
      }
   }

   protected void initializePointers() {
      for (int i = 0; i < scoreTable.length; i++) {
         for (int j = 0; j < scoreTable[i].length; j++) {
            scoreTable[i][j].setPrevCell(getInitialPointer(i, j));
         }
      }
   }

   protected void initialize() {
      for (int i = 0; i < scoreTable.length; i++) {
         for (int j = 0; j < scoreTable[i].length; j++) {
            scoreTable[i][j] = new SequenceCell(i, j);
         }
      }
      initializeScores();
      initializePointers();

      isInitialized = true;
   }

   protected abstract SequenceCell getInitialPointer(int row, int col);

   protected abstract int getInitialScore(int row, int col);

   protected abstract void fillInCell(SequenceCell currentCell, SequenceCell cellAbove,
         SequenceCell cellToLeft, SequenceCell cellAboveLeft);

   protected void fillIn() {
      for (int row = 1; row < scoreTable.length; row++) {
         for (int col = 1; col < scoreTable[row].length; col++) {
            SequenceCell currentCell = scoreTable[row][col];
            SequenceCell cellAbove = scoreTable[row - 1][col];
            SequenceCell cellToLeft = scoreTable[row][col - 1];
            SequenceCell cellAboveLeft = scoreTable[row - 1][col - 1];
            fillInCell(currentCell, cellAbove, cellToLeft, cellAboveLeft);
         }
      }

      tableIsFilledIn = true;
   }

   abstract protected Object getTraceback();

   public void printScoreTable() {
      ensureTableIsFilledIn();
      for (int i = 0; i < sequence2.length() + 2; i++) {
         for (int j = 0; j < sequence1.length() + 2; j++) {
            if (i == 0) {
               if (j == 0 || j == 1) {
                  System.out.print("  ");
               } else {
                  if (j == 2) {
                     System.out.print("     ");
                  } else {
                     System.out.print("   ");
                  }
                  System.out.print(sequence1.charAt(j - 2));
               }
            } else if (j == 0) {
               if (i == 1) {
                  System.out.print("  ");
               } else {
                  System.out.print(" " + sequence2.charAt(i - 2));
               }
            } else {
               String toPrint;
               SequenceCell currentCell = scoreTable[i - 1][j - 1];
               SequenceCell prevCell = currentCell.getPrevCell();
               if (prevCell != null) {
                  if (currentCell.getCol() == prevCell.getCol() + 1
                        && currentCell.getRow() == prevCell.getRow() + 1) {
                     toPrint = "\\";
                  } else if (currentCell.getCol() == prevCell.getCol() + 1) {
                     toPrint = "-";
                  } else {
                     toPrint = "|";
                  }
               } else {
                  toPrint = " ";
               }
               int score = currentCell.getScore();
               String s = String.format("%1$3d", score);
               toPrint += s;
               System.out.print(toPrint);
            }

            System.out.print(' ');
         }
         System.out.println();
      }
   }

   protected void ensureTableIsFilledIn() {
      if (!isInitialized) {
         initialize();
      }
      if (!tableIsFilledIn) {
         fillIn();
      }
   }
}
