package com.dice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
@Entity
@Table(name = "diceresult")
public class DiceResult
{
    @Id
    @Column(name = "idresult", length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idDiceResult;

    @Column(name = "result", nullable = false)
    private int result;

    @ManyToOne(
            targetEntity = Game.class,
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "idgame",
            nullable = true)
    @JsonIgnore
    private Game game;

    public DiceResult()
    {
    }

    public DiceResult(UUID idDiceResult, int result)
    {
        this.idDiceResult = idDiceResult;
        this.result = result;
    }

    public UUID getIdDiceResult()
    {
        return idDiceResult;
    }

    public void setIdDiceResult(UUID idDiceResult)
    {
        this.idDiceResult = idDiceResult;
    }

    public int getResult()
    {
        return result;
    }

    public void setResult(int result)
    {
        this.result = result;
    }

    public Game getGame()
    {
        return game;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    @Override
    public String toString()
    {
        return "DiceResult{" + "idDiceResult=" + idDiceResult + ", result=" + result + ", game=" + game + '}';
    }
}
