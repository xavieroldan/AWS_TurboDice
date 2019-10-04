package com.dice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
@Entity
@Table(name = "game")
public class Game
{
    @Id
    @Column(name = "idgame", length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idGame;

    @Column(
            name = "anonim", length = 1,
            columnDefinition = "boolean default false",
            nullable = false
    )
    private boolean isAnonim;

    @Column(
            name = "winner", length = 1,
            columnDefinition = "boolean default false",
            nullable = false
    )
    private boolean isWinner;

    @ManyToOne(
            targetEntity = Player.class,
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "idplayer",
            nullable = true)
    @JsonIgnore
    private Player player;

    @OneToMany(
            targetEntity = DiceResult.class,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @Column(nullable = true)
    private List<DiceResult> listDiceResult;

    public Game()
    {
    }

    public Game(UUID idGame, boolean isAnonim, boolean isWinner)
    {
        this.idGame = idGame;
        this.isAnonim = isAnonim;
        this.isWinner = isWinner;
    }

    public UUID getIdGame()
    {
        return idGame;
    }

    public void setIdGame(UUID idGame)
    {
        this.idGame = idGame;
    }

    public boolean getIsAnonim()
    {
        return isAnonim;
    }

    public void setIsAnonim(boolean isAnonim)
    {
        this.isAnonim = isAnonim;
    }

    public boolean getIsWinner()
    {
        return isWinner;
    }

    public void setIsWinner(boolean isWinner)
    {
        this.isWinner = isWinner;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public List<DiceResult> getListDiceResult()
    {
        return listDiceResult;
    }

    public void setListDiceResult(List<DiceResult> listDiceResult)
    {
        this.listDiceResult = listDiceResult;
    }

    @Override
    public String toString()
    {
        return "Game{" + "idGame=" + idGame + ", isAnonim=" + isAnonim + ", isWinner=" + isWinner + ",  player=" + player + ", listDiceResult=" + listDiceResult + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.idGame);
        hash = 79 * hash + (this.isAnonim ? 1 : 0);
        hash = 79 * hash + (this.isWinner ? 1 : 0);
        hash = 79 * hash + Objects.hashCode(this.player);
        hash = 79 * hash + Objects.hashCode(this.listDiceResult);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Game other = (Game) obj;
        if (this.isAnonim != other.isAnonim)
        {
            return false;
        }
        if (this.isWinner != other.isWinner)
        {
            return false;
        }
        if (!Objects.equals(this.idGame, other.idGame))
        {
            return false;
        }
        if (!Objects.equals(this.player, other.player))
        {
            return false;
        }
        if (!Objects.equals(this.listDiceResult, other.listDiceResult))
        {
            return false;
        }
        return true;
    }
}
