public class BoardGenerator {

    private static char[][] board= new char[3][3];


    BoardGenerator(){
        printBoard();
    }

    public char[][] getBoard() {
        return board;
    }

    public void resetArray() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = ' ';
            }
        }
    }
    private void printBoard(){
            for (int i = 0; i < board.length; ++i) {
                System.out.print("| ");
                for (int j = 0; j < board.length; ++j) {
                    System.out.print(board[i][j] + " | ");
                }
                System.out.println();
            }
        }

    public void printBoardWithNewSign(int x, int y, char player){
        board[x-1][y-1]=player;
        printBoard();
    }




}
