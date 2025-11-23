package main.java.com.qrcode.matrix;

public class QRMatrix {
    

    private boolean[][] modules; //(true = black false = white)
    private static final int SIZE = 21; //21x21 grid

    private boolean[][] functionModules;

    public QRMatrix() 
    {
        modules = new boolean[SIZE][SIZE];
        functionModules = new boolean[SIZE][SIZE];
        drawFinderPattern(); 
        drawSeparators();//not needed?
        drawTimingPatterns();
        drawDarkModule();

        
    
    }


    private void drawFinderPattern() 
    {

        /*
              0 1 2 3 4 5 6       
            0 X X X X X X X  
            1 X . . . . . X  
            2 X . X X X . X  
            3 X . X X X . X  
            4 X . X X X . X  
            5 X . . . . . X  
            6 X X X X X X X  
        */

        int[][] starts = new int[][] { {0, 0}, {0, 14}, {14, 0} };
        for (int[] start : starts)
        {
            int size = 7; //7x7 square
            int r = start[0]; //rows offset
            int c = start[1]; //cols offset
            for (int row = 0; row < size; row++)
            {
                for(int col = 0; col < size; col++)
                {
                    if (row == 0 || row == size - 1) //top (0) and bottom row (6)
                        modules[row + r][col + c] = true;
                    
                    else if (row == 1 || row == size - 2) //rows 1 & 5
                        modules[row + r][col + c] = (col == 0 || col == size - 1);
                    
                    else //rows 2 3 & 4
                        modules[row + r][col + c] = (col != 1 && col != size - 2); 
                }
            }
        }
    }

    private void drawSeparators()
    {
        //they are already made?
    }

    private void drawTimingPatterns()
    {
        int row = 8;
        int col = 6;

        for (; row <= 12; row++)
            modules[row][col] = (row % 2 == 0);
        

        row = 6;
        col = 8;

        for (; col <= 12; col++)
            modules[row][col] = (col % 2 == 0);
    }

    private void drawDarkModule() 
    {
        modules[13][8] = true;  //always black at this position
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                sb.append(modules[row][col] ? "X" : ".");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) 
    {
        QRMatrix matrix = new QRMatrix();
        System.out.println(matrix.toString());
    }

}
