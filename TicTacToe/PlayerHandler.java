import java.util.HashMap;

/**
 * Created by Bartłomiej Janik on 5/22/2017.
 */
public class PlayerHandler {

    private HashMap<Character, Integer> pointsSummary;

    PlayerHandler() {
        pointsSummary = new HashMap<>();
        pointsSummary.put('o',0);
        pointsSummary.put('x',0);
    }
    public char changePlayer(char firstPlayerChar) {
        return (firstPlayerChar=='o') ? 'x':'o';
    }

    public void addPointsForPlayer(char player){
        pointsSummary.put(player,pointsSummary.get(player)+1);

    }

    public HashMap<Character, Integer> getPointsSummary() {
        return pointsSummary;
    }


    public char checkSign(String firstPlayer) {
        while(firstPlayer.charAt(0)!='x' && firstPlayer.charAt('o')!='o'){
            System.out.println("Woops I don't know such sign :/ pick o or x");
            //firstPlayer=sc.next();
        }

        return firstPlayer.charAt(0);
    }
}
