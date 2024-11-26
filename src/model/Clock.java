package model;

public class Clock {
    private static final String[] weekDay= {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    int currentDay;
    int time;

    public int getTime() {
        return time;
    }

    public void getDay(){
        return weekDay[currentDay];
    }

    public void incrementTime(){
        time++;
        //to be continued..
    }

    public void incrementDay(){
        currentDay++;
        if(currentDay>=7)
            currentDay=0;
    }
}