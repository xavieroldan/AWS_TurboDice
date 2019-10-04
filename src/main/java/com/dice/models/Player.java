package com.dice.models;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.Id;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
@Entity
@Table(name = "player")
public class Player
{
    @Id
    @Column(name = "idplayer", length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idPlayer;

    @Column(name = "name",
            length = 50,
            nullable = false)
    private String name;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "regdate")
    private Date regDate;

    @OneToMany(
            targetEntity = Game.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Column(nullable = true)
    private List<Game> listGame;

    public Player()
    {
    }

    public Player(UUID idPlayer, String name, Date regDate, List<Game> listGame)
    {
        this.setIdPlayer(idPlayer);
        this.setName(name);
        this.setRegDate(regDate);
        this.setListGame(listGame);
    }

    public UUID getIdPlayer()
    {
        return idPlayer;
    }

    public void setIdPlayer(UUID idPlayer)
    {
        this.idPlayer = idPlayer;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getRegDate()
    {
        return regDate;
    }

    public void setRegDate(Date date)
    {
        this.regDate = date;
    }

    public List<Game> getListGame()
    {
        return listGame;
    }

    public void setListGame(List<Game> listGame)
    {
        this.listGame = listGame;
    }

    @Override
    public String toString()
    {
        return "Player{" + "idPlayer=" + idPlayer + ", name=" + name + ", regDate=" + regDate + ", listGame=" + listGame + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.idPlayer);
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.regDate);
        hash = 11 * hash + Objects.hashCode(this.listGame);
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
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name))
        {
            return false;
        }
        if (!Objects.equals(this.idPlayer, other.idPlayer))
        {
            return false;
        }
        if (!Objects.equals(this.regDate, other.regDate))
        {
            return false;
        }
        if (!Objects.equals(this.listGame, other.listGame))
        {
            return false;
        }
        return true;
    }
}
