package ru.ads.set;

import java.util.ArrayList;

public class PowerSet
{
    public ArrayList<String> values;

    public PowerSet()
    {
        values = new ArrayList<>();
    }

    public int size()
    {
        return values.size();
    }

    public void put(String value)
    {
        if (!get(value)) {
            values.add(value);
        }
    }

    public boolean get(String value)
    {
        return values.contains(value);
    }

    public boolean remove(String value)
    {
        return values.remove(value);
    }

    public PowerSet intersection(PowerSet set2)
    {
        PowerSet intersectedSet = new PowerSet();
        for (String curr : values) {
            if (set2.get(curr)) {
                intersectedSet.put(curr);
            }
        }
        return intersectedSet;
    }

    public PowerSet union(PowerSet set2)
    {
        PowerSet unionSet = new PowerSet();
        for (String curr : values) {
            unionSet.put(curr);
        }

        for (String curr : set2.values) {
            unionSet.put(curr);
        }
        return unionSet;
    }

    public PowerSet difference(PowerSet set2)
    {
        PowerSet differenceSet = new PowerSet();
        for (String curr : values) {
            if (!set2.get(curr)) {
                differenceSet.put(curr);
            }
        }
        return differenceSet;
    }

    public boolean isSubset(PowerSet set2)
    {
        for (String curr : set2.values) {
            if (!this.get(curr)) {
                return false;
            }
        }
        return true;
    }

}


