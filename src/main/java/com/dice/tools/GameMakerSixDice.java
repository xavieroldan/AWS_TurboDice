package com.dice.tools;

import com.dice.models.DiceResult;
import com.dice.models.Game;
import com.dice.models.Player;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
public class GameMakerSixDice
{

    private static final int ROLL_MAX = 6;
    private static final int WIN_RESULTS[] =
    {
        5, 6
    };

    public Player playGame(Player player)
    {
        //create the dice game
        Game game = new Game();
        game.setIdGame(UUID.randomUUID());
        game.setPlayer(player);
        game.setListDiceResult(new ArrayList<>());
        //lets play!
        boolean isWinner = true;
        for (int i = 1; i < ROLL_MAX + 1; i++)
        {
            //launch the dice
            int result = RandomRollGen.getRandomRoll();
            DiceResult diceResult = new DiceResult(UUID.randomUUID(), result);
            diceResult.setGame(game);
            game.getListDiceResult().add(diceResult);
            //verify winner game for diferents results
            boolean winDiceRoll = false;
            for (int j : WIN_RESULTS)
            {
                if (result == j)
                {
                    //Is one of the result is true : finaly winner
                    winDiceRoll = true;
                }
                //Else is false: do nothing -> is loser
            }
            if (!winDiceRoll)
            {
                //If one of the results is false: finaly loser
                isWinner = false;
            }
            //Else is winner : do nothing -> is winner
        }
        //getting the result
        if (isWinner)
        {
            game.setIsWinner(true);
        }
        else
        {
            game.setIsWinner(false);
        }
        if (player.getListGame() == null)
        {
            player.setListGame(new ArrayList<>());
        }
        player.getListGame().add(game);
        return player;
    }
}
