package com.dice.repositories;

import com.dice.models.Player;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
public interface PlayerRepository extends CrudRepository<Player, UUID>
{
    Player findByName(String name); // find player by name
}
