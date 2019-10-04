package com.dice.repositories;

import com.dice.models.Game;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
public interface GameRepository extends CrudRepository<Game, UUID>
{
    Double countByIsWinner(boolean isWinner); // count all win games

    @Query(value = "SELECT COUNT(idGame) from Game where winner=true", nativeQuery = true)
    double countAllWinGames();

    @Query(value = "SELECT COUNT(idGame) from Game where winner=true and idplayer=?1")
    double countPlayerWinGames(UUID idPlayer);

    @Query(value = "SELECT COUNT(idGame) from Game where idplayer=?1")
    double countPlayerGames(UUID Player);
}
