/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dice.apis;

import com.dice.models.Game;
import com.dice.models.Player;
import com.dice.repositories.GameRepository;
import com.dice.repositories.PlayerRepository;
import com.dice.tools.ErrorTransactionException;
import com.dice.tools.ErrorValueException;
import com.dice.models.RateDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
@Component
public class HelperRestController
{
    @Autowired
    PlayerRepository playerRepo;
    @Autowired
    GameRepository gameRepo;

    //Check a List of Players is OK
    public List<Player> verifyListPlayers(List<Player> listPlayer) throws ErrorTransactionException
    {
        try
        {
            if (listPlayer.isEmpty())
            {
                throw new ErrorTransactionException("No hay jugadores en el sistema.");
            }

            return listPlayer;
        }
        catch (Exception e)
        {
            throw new ErrorTransactionException("No hay jugadores en el sistema.");
        }

    }

    //Transform a List of Players in an sort List by the Rate (worst the first)
    protected List<RateDTO> sortPlayersByRate() throws ErrorTransactionException
    {
        List<Player> listPlayer = verifyListPlayers((List<Player>) playerRepo.findAll());
        List<RateDTO> listRateDTO = new ArrayList<>();
        for (Player player : listPlayer)
        {
            //Count the games
            double games = player.getListGame().size();
            if (games != 0)
            {
                List<Game> listGame = player.getListGame();
                double wins = 0;
                //Count the wins
                for (Game game : listGame)
                {
                    if (game.getIsWinner())
                    {
                        wins++;
                    }
                }
                //Calculate and output the results
                double result = (wins / games) * 100;
                RateDTO resultDTO = new RateDTO(player, result);
                listRateDTO.add(resultDTO);
            }
            else
            {
                //No games rate 0%
                RateDTO resultDTO = new RateDTO(player, 0);
                listRateDTO.add(resultDTO);
            }
        }
        //Order the list
        Collections.sort(listRateDTO);
        return listRateDTO;
    }

    protected Player verifyName(Player player) throws ErrorValueException
    {
        if (player.getName().trim().isEmpty())
        {
            throw new ErrorValueException("Nombre de jugador vacío.");
        }
        //Trim the name
        String name = player.getName();
        player.setName(name.trim());
        return player;
    }

    protected List<RateDTO> getListPlayersRateDTO(List<Player> listPlayers)
    {
        List<RateDTO> output = new ArrayList<>(); //TODO: Put here the sortRanking method
        //Get player Ratio
        for (Player player : listPlayers)
        {
            UUID idPlayer = player.getIdPlayer();
            double ratio = 0;
            if (!player.getListGame().isEmpty())
            {
                Double ratioFull = (gameRepo.countPlayerWinGames(idPlayer)
                        / gameRepo.countPlayerGames(idPlayer)) * 100;
                BigDecimal bd = new BigDecimal(ratioFull).setScale(2, RoundingMode.HALF_UP);
                ratio = bd.doubleValue();
            }
            RateDTO rateDTO = new RateDTO(player, ratio);
            output.add(rateDTO);
        }
        Collections.sort(output);
        return output;
    }

}
