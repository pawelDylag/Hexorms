package com.fais.hexorms.data;

import java.util.*;

public class MrWorm extends Worm {
    private int[] genes = new int[6];
    private HashMap<Integer, Double> probs = new HashMap<Integer, Double>();
    private HashMap<Integer, Double> sortedprobs = new HashMap<Integer, Double>();
    private double probsum = 0.0;
    private boolean inheritGenes = false;
    private int myid;
    private int previous_dir = 1;

    public MrWorm(int id, boolean isChild)
    {
        super(id);
        myid = id;
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
        Iterator<Map.Entry<Integer, Double>> i = sortedprobs.entrySet().iterator();
        while(i.hasNext())
        {
            int key = i.next().getKey();
            System.out.println("PROB FOR DIR="+key+" IS "+sortedprobs.get(key));
        }

    }

    @Override
    public int rotate(boolean [] occupied)
    {
        double maxProb = 0.0;
        double floor = 0;
        double ceiling;
        int direction = -1;
        ArrayList<Integer> available_keys = new ArrayList<>();
        for(int i=0; i<occupied.length; i++)
        {
            if(occupied[i] == true)
            {
                available_keys.add(i+1);
            }
        }
        for(int k=0; k<available_keys.size(); k++)
        {
            maxProb = maxProb + sortedprobs.get(available_keys.get(k));
        }
        System.out.println("MAXPROB: "+maxProb);
        Random ran = new Random();
        double randomdouble = ran.nextDouble();
        randomdouble = randomdouble * maxProb;
        ceiling = maxProb;
        System.out.println("RANDOM: "+randomdouble);
        Iterator<Map.Entry<Integer, Double>> i = sortedprobs.entrySet().iterator();
        while(i.hasNext())
        {
            int key = i.next().getKey();
            if(occupied[key-1]==false)
            {
                System.out.println("OMMITING KEY: "+key);
                // Ommit this key
            }
            else
            {
                ceiling = sortedprobs.get(key)+floor;
                System.out.println("FLOOR: "+floor+" , "+"CEILING: "+ceiling+" , "+"KEY: "+key);
                if (randomdouble > floor && randomdouble <= ceiling)
                {
                    direction = key;
                    previous_dir = direction;
                    System.out.println("ROBAK " + myid + " idzie w kierunku " + direction);
                    break;
                }
                else
                {
                    floor = floor + sortedprobs.get(key);
                }
            }
        }
        if(direction==-1)
        {
            direction = previous_dir;
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
            int gene = ran.nextInt(6);
            genes[gene] = ran.nextInt(6)+1;
            calcProb();
        }
    }

    private void codeMyGenes() {
        Random random = new Random();
        for (int i = 0; i < genes.length; i++) {
            genes[i] = random.nextInt(6)+1;
            System.out.println("MY GENE "+(i+1)+" : "+genes[i]);
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
