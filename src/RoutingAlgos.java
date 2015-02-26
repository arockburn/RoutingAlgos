import java.util.Arrays;
import java.util.Random;

/**
 * Created by Aaron on 2/24/2015.
 */
public class RoutingAlgos {
    public int[] stopCounts = new int[RoutingConstants.days];

    public int[] daysChoices(int freq){
        int[] choices = new int[RoutingConstants.days];

        if(freq >= RoutingConstants.days)
            Arrays.fill(choices, 1);

        /*
        else{
            int gaps = RoutingConstants.days - freq;
            if(gaps > 1){
                int daysLeft = freq;
                Random rand = new Random();

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
                    else if(gaps > freq){
                        boolean prevDayScheduled = false;
                        int extraGaps = gaps - freq;
                        for(int i = 0; i < RoutingConstants.days; i++){
                            if(prevDayScheduled) {
                                choices[i] = 0;
                                prevDayScheduled = false;
                                gaps--;
                            }
                            else{
                                if(extraGaps > 0){
                                    int r = rand.nextInt();
                                    if(r == 1 && daysLeft != 0){
                                        choices[i] = 1;
                                        daysLeft--;
                                        prevDayScheduled = true;
                                    }
                                    else{
                                        choices[i] = 0;
                                        extraGaps--;
                                        gaps--;
                                        prevDayScheduled = false;
                                    }
                                }
                                else{

                                }
                            }
                        }
                    }
                    else{

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
        */

        else {
            int period = RoutingConstants.days / freq;
            int extraDays = RoutingConstants.days % freq;
            int daysScheduled = 0;
            boolean doneScheduling = false;
            Random random = new Random();

            while (!doneScheduling) {
                if (daysScheduled == RoutingConstants.days)
                    doneScheduling = true;

                else if (period == 1) {
                    if (extraDays >= 1) {
                        int[] gapDays = new int[extraDays];
                        for (int i = 0; i < extraDays; i++) {
                            int rand = random.nextInt(RoutingConstants.days);
                            if (i == 0)
                                gapDays[0] = rand;
                            else {
                                if (gapDays[0] == rand)
                                    gapDays[1] = (rand + 2) % (RoutingConstants.days - 1);
                                else
                                    gapDays[1] = rand;
                            }
                        }

                        if (extraDays == 2) {
                            if (gapDays[0] > gapDays[1]) {
                                int temp = gapDays[0];
                                gapDays[0] = gapDays[1];
                                gapDays[1] = temp;
                            }
                        }

                        int gapDayCnt = 0;
                        boolean gapsUsed = false;
                        for (int i = 0; i < RoutingConstants.days; i++) {
                            boolean gap = false;
                            if(!gapsUsed){
                                if (i == gapDays[gapDayCnt]) {
                                    choices[i] = 0;
                                    gapDayCnt++;
                                    gap = true;
                                    if(gapDayCnt == extraDays)
                                        gapsUsed = true;
                                }
                            }
                            if(!gap)
                                choices[i] = 1;

                            daysScheduled++;
                        }
                    }

                }
                else{
                    int dayToSchedule = random.nextInt(period);
                    for (int i = 0; i < period; i++) {
                        if (daysScheduled > 0) {
                            if (choices[daysScheduled - 1] == 1 && dayToSchedule == i)
                                dayToSchedule++;
                        }
                        if (i != dayToSchedule)
                            choices[daysScheduled] = 0;
                        else
                            choices[daysScheduled] = 1;

                        daysScheduled++;
                    }
                    if (extraDays != 0) {
                        choices[daysScheduled] = 0;
                        daysScheduled++;
                        extraDays--;
                    }
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
            case 0:
                currStopFreq = 1;
                numStops = RoutingConstants.oneDayStops;
                break;
            case 1:
                currStopFreq = 2;
                numStops = RoutingConstants.twoDayStops;
                break;
            case 2:
                currStopFreq = 3;
                numStops = RoutingConstants.threeDayStops;
                break;
            case 3:
                currStopFreq = 4;
                numStops = RoutingConstants.fourDayStops;
                break;
            case 4:
                currStopFreq = 5;
                numStops = RoutingConstants.fiveDayStops;
                break;
            case 5:
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
        for(int i = RoutingConstants.days - 1; i >= 0; i--){
            finalRoutes[i] = algos.routes(i);
            System.out.print(Arrays.toString(algos.stopCounts) + "\n");
//            System.out.println(i + ",");
        }

        System.out.print(Arrays.toString(algos.stopCounts));
    }
}
