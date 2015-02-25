import java.util.Arrays;
import java.util.Random;

/**
 * Created by Aaron on 2/24/2015.
 */
public class RoutingAlgos {
    public int[] stopCounts = new int[RoutingConstants.days];

    public int[] daysChoices(int freq){
        int[] choices = new int[freq];

        if(freq >= RoutingConstants.days)
            Arrays.fill(choices, 1);

        else{
            int gaps = RoutingConstants.days - freq;
            if(gaps > 1){
                int daysLeft = freq;
                Random rand = new Random();
                boolean prevDayScheduled = false;

                if(gaps >= freq) {
                    if(gaps == freq){
                        int r = rand.nextInt();
                        if(r == 0)
                            for(int i = 0; i < RoutingConstants.days; i++){
                                if(i % 2 == 0)
                                    choices[i] = 0;
                                else
                                    choices[i] = 1;
                            }
                        else{
                            for(int i = 0; i < RoutingConstants.days; i++) {
                                if (i % 2 == 0)
                                    choices[i] = 1;
                                else
                                    choices[i] = 0;
                            }
                        }
                    }
                    else if(freq == 1){
                        int r = rand.nextInt() * (RoutingConstants.days - 1)+ 1;
                        for(int i = 0; i < RoutingConstants.days; i++){
                            if(i != r)
                                choices[i] = 0;
                            else
                                choices[i] = 1;
                        }
                    }
                    else{
                        int extraGaps = gaps - freq;

                        for(int i = 0; i < RoutingConstants.days; i++){
                            double useGap = rand.nextDouble() * (gaps/RoutingConstants.days);

                        }

                    }

                }

                else{
                    for(int i = 0; i < RoutingConstants.days; i++){

                    }

                }

            }
            else{
                int max = 0;
                for(int j = 0; j < stopCounts.length; j++){
                    if(stopCounts[j] > max)
                        max = j;
                }
                for(int j = 0; j < RoutingConstants.days; j++){
                    if(j == max)
                        stopCounts[j] = 0;
                    else
                        stopCounts[j] = 1;
                }
            }

        }


        return choices;
    }

    public int[] routes(int day){
        int[] routes = new int[RoutingConstants.days];
        int currStopFreq;
        int numStops;
        switch(day){
            case 1:
                currStopFreq = 1;
                numStops = RoutingConstants.oneDayStops;
                break;
            case 2:
                currStopFreq = 2;
                numStops = RoutingConstants.twoDayStops;
                break;
            case 3:
                currStopFreq = 3;
                numStops = RoutingConstants.threeDayStops;
                break;
            case 4:
                currStopFreq = 4;
                numStops = RoutingConstants.fourDayStops;
                break;
            case 5:
                currStopFreq = 5;
                numStops = RoutingConstants.fiveDayStops;
                break;
            case 6:
                currStopFreq = 6;
                numStops = RoutingConstants.sixDayStops;
                break;
            default:
                currStopFreq = 0;
                numStops = 0;
        }

        for(int i = 0; i < numStops; i++){
            routes = daysChoices(currStopFreq);
            for(int j = 0; j < RoutingConstants.days; j++){
                if(routes[j] == 1)
                    stopCounts[j]++;
            }
        }

        return routes;
    }

    public static void main(String[] args){
        int[][] finalRoutes = new int[RoutingConstants.days][];
        RoutingAlgos algos = new RoutingAlgos();
        for(int i = RoutingConstants.days; i > 0; i--){
//            finalRoutes[i] = algos.routes(i);
            System.out.println(i + ",");
        }

    }
}
