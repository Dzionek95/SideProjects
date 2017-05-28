import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class TicTacToe {

    private static Scanner sc=new Scanner(System.in);
    private static PlayerHandler playerHandler=new PlayerHandler();

    public static void main(String... args) throws WrongCharacterException{
        System.out.println("Which player first O or X?");
        String firstPlayer=sc.next();
      //  playerHandler.checkSign(firstPlayer);
        char currentPlayerChar=firstPlayer.charAt(0);

            startGame(currentPlayerChar);
            startGameAgain(currentPlayerChar);
            getOverallScores();


    }

    private static void getOverallScores() {
        System.out.println("It's overall score: ");
        HashMap<Character,Integer> map=playerHandler.getPointsSummary();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove();
        }
    }

    private static void startGameAgain(char sign){
        System.out.println("Wanna play again? y/n");
        String choice= sc.next();
        while (choice.charAt(0)=='y'){
            startGame(sign);
            System.out.println("Wanna play again? y/n");
            clearScreen();
        }


    }

    private static void startGame(char sign){
        WinnerChecker winnerChecker=new WinnerChecker();
        BoardGenerator boardGenerator=new BoardGenerator();
        boardGenerator.resetArray();

        while (winnerChecker.isWinner(sign)) {
            System.out.println("Which position on x axe Mr " + sign+ " ?");
            int x = sc.nextInt();
            System.out.println("Which position on y axe Mr " + sign+ " ?");
            int y = sc.nextInt();
            clearScreen();
            boardGenerator.printBoardWithNewSign(x, y, sign);
            sign = playerHandler.changePlayer(sign);
        }

        playerHandler.addPointsForPlayer(sign);
        System.out.println("Well done Mr " + sign + " you won =)");
    }

    private static void clearScreen() {
        for (int i = 0; i < 50; ++i)
            System.out.println();
    }
}
