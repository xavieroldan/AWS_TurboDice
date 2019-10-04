package com.dice.tools;

import java.util.Random;

/**
 *
 * @author Xavier Roldán <info@xavierroldan.com>
 */
public class RandomRollGen
{
    public static int getRandomRoll()
    {
        int output = 0;
        int min = 1;
        int max = 6;

        Random r = new Random();
        output = r.nextInt((max - min) + 1) + min;

        return output;
    }
}
