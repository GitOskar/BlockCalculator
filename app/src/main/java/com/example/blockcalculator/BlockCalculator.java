package com.example.blockcalculator;

public class BlockCalculator
{
    public static int block(float maxPr, float currOnLvl, float prOnPlace)
    {
        return (int) Math.ceil((maxPr - currOnLvl + prOnPlace) / 2.0);
    }

    public static int profit(float blockCost, float arcRefund, float refund)
    {
        return (int) (Math.ceil(arcRefund * refund - blockCost));
    }
}
