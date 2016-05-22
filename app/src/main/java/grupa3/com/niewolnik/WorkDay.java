package grupa3.com.niewolnik;

/**
 * Created by student on 22/05/16.
 */


class WorkDay {

    private int lp;
    private String arriveTime;
    private String leavingTime;
    private String date;
    private int freeDay;

    public WorkDay(String arriveTime, String leavingTime, String date,int freeDay) {
        this.arriveTime = arriveTime;
        this.leavingTime = leavingTime;
        this.date = date;
        this.freeDay=freeDay;
    }

    public WorkDay() {

    }

    public void setLP(int lp) {
        this.lp = lp;
    }

    public int getLP() {
        return lp;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }



    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFreeDay(int freeDay) {
        this.freeDay = freeDay;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public String getLeavingTime() {
        return leavingTime;
    }

    public String getDate() {
        return date;
    }

    public int getFreeDay() {
        return freeDay;
    }

    public String toString() {
        return arriveTime + " " + leavingTime + " " + date;
    }

}
