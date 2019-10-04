/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dice.models;

import com.dice.models.Player;
import java.util.Objects;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
public class RateDTO implements Comparable<RateDTO>
{

    private Player player;
    private double rate;

    public RateDTO()
    {
    }

    public RateDTO(Player player, double rate)
    {
        this.setPlayer(player);
        this.setRate(rate);
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public double getRate()
    {
        return rate;
    }

    public void setRate(double rate)
    {
        this.rate = rate;
    }

    @Override
    public int compareTo(RateDTO p2)
    {
        RateDTO p1 = this;
        // Assume no nulls, and simple ordinal comparisons

        // First by rate - stop if this gives a result.
        int rateResult = Double.valueOf(p2.getRate()).compareTo(p1.getRate());
        if (rateResult != 0)
        {
            return rateResult;
        }
        // Next by games played
        int p1NumGames = p1.getPlayer().getListGame().size();
        int p2NumGames = p2.getPlayer().getListGame().size();
        int facultyResult = Integer.valueOf(p1NumGames).compareTo(p2NumGames);
        if (facultyResult != 0)
        {
            return facultyResult;
        }
        // Finally by register date
        return p2.getPlayer().getRegDate().compareTo(p1.getPlayer().getRegDate());
    }

    @Override
    public String toString()
    {
        return "RateDTO{" + "player=" + player + ", rate=" + rate + '}';
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.player);
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.rate) ^ (Double.doubleToLongBits(this.rate) >>> 32));
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
        final RateDTO other = (RateDTO) obj;
        if (Double.doubleToLongBits(this.rate) != Double.doubleToLongBits(other.rate))
        {
            return false;
        }
        if (!Objects.equals(this.player, other.player))
        {
            return false;
        }
        return true;
    }

}
