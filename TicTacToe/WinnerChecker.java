
public class WinnerChecker {

    BoardGenerator boardGenerator=new BoardGenerator();

    public boolean isWinner(char a) {
        if(!isRaw(a))
            return false;
        else if(!isDiagonal(a))
            return false;
        else if(!isColumn(a))
            return false;
        else
            return true;
    }

    private boolean isRaw(char a) {
        boolean flag=true;
        char[][] board= boardGenerator.getBoard();
        for(int i=0; i<board.length; ++i){
            if((board[i][0]==a) && (board[i][1]==a) && (board[i][2]==a)){
                flag=false;
            }
        }
        return flag;
    }

    private boolean isDiagonal(char a){
        boolean flag=true;
        char[][] board= boardGenerator.getBoard();
        if((board[0][0]==a) && (board[1][1]==a) && (board[2][2]==a)){
            return false;
        }
        else if((board[0][2]==a) && (board[1][1]==a) && (board[2][0]==a))
            return false;
        return flag;
    }

    private boolean isColumn(char a){
        boolean flag=true;
        char[][] board= boardGenerator.getBoard();
        for(int i=0; i<board.length; ++i){
            if((board[0][i]==a) && (board[1][i]==a) && (board[2][i])==a){
                flag=false;
            }
        }
        return flag;
    }
}
