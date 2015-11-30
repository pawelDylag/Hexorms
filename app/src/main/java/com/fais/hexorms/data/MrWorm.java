package com.fais.hexorms.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MrWorm extends Worm {
    private int[] genes = new int[6];
    private HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> sortedprobs = new HashMap<Integer, Double>();
    private double probsum = 0.0;
    private boolean inheritGenes = false;

    public MrWorm(int id, boolean isChild)
    {
        super(id);
        this.health = 5;
        this.maxHealth = 12;
        if(isChild)
        {
            inheritGenes = true;
        }
        else
        {
            System.out.println("WORM NR "+id+" koduje sobie Geny");
            codeMyGenes();
            calcProb();
        }

    }

    @Override
    public int rotate()
    {
        double floor = 0;
        int direction = 0;
        Random ran = new Random();
        double randomdouble = ran.nextDouble();
        Iterator<Map.Entry<Integer, Double>> i = sortedprobs.entrySet().iterator();
        while(i.hasNext())
        {
            int key = i.next().getKey();
            if(randomdouble>floor && randomdouble<=sortedprobs.get(key))
            {
                direction = key;
            }
            else
            {
                floor = sortedprobs.get(key);
            }
        }
        return direction;
    }

    @Override
    public Worm makeChild(int childId)
    {
        System.out.println("ROBIE DZIECKO!");
        MrWorm mychild = new MrWorm(childId,true);
        mychild.passGenes(genes);
        return mychild;
    }

    @Override
    public void loseSomeHealth() {
        {
            this.health--;
        }
    }

    @Override
    public void passGenes(int [] genes)
    {
        Random ran = new Random();
        if(inheritGenes)
        {
            this.genes = genes;
            int gene = ran.nextInt(5);
            genes[gene] = ran.nextInt(10);
            calcProb();
        }
    }

    private void codeMyGenes() {
        Random random = new Random();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextInt(10);
        }
    }

    private void calcProb()
    {
        for (int k = 0; k < genes.length; k++) {
            probsum = probsum + Math.exp(genes[k]);
        }
        for (int i = 0; i < genes.length; i++) {
            double value = Math.exp(genes[i]) / probsum;
            probs.put(i + 1, value);
        }
        sortedprobs = sortProbs();
    }

    private HashMap sortProbs()
    {
        List list = new LinkedList(probs.entrySet());
        Collections.sort(list, new Comparator()
        {
            public int compare(Object o1, Object o2)
            {
                return ((Comparable) ((Map.Entry) (o1)).getValue()).compareTo(((Map.Entry) (o2)).getValue());
            }
        });

        HashMap result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();)
        {
            Map.Entry entry = (Map.Entry)it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
