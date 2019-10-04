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
public class GameMaker
{

    private static final int ROLL_MAX = 2;
    private static final int WIN_RESULTS[] =
    {
        7
    };

    public Player playGame(Player player)
    {
        //create the dice game
        Game game = new Game();
        game.setIdGame(UUID.randomUUID());
        game.setPlayer(player);
        game.setListDiceResult(new ArrayList<>());
        //lets play!
        int totalResult = 0;
        for (int i = 1; i < ROLL_MAX + 1; i++)
        {
            //launch the dice
            int result = RandomRollGen.getRandomRoll();
            DiceResult diceResult = new DiceResult(UUID.randomUUID(), result);
            diceResult.setGame(game);
            game.getListDiceResult().add(diceResult);//save the result
            totalResult += result;
        }
        //getting the result
        for (int i : WIN_RESULTS)
        {
            if (totalResult == i)
            {
                game.setIsWinner(true);
            }
            else
            {
                game.setIsWinner(false);
            }
        }
        if (player.getListGame() == null)
        {
            player.setListGame(new ArrayList<>());
        }
        player.getListGame().add(game);
        return player;
    }
}
