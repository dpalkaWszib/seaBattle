package pl.dawidpalka;

public class Ship {

    private int id;
    private int length;
    private int numberOfHits;
    public enum Position {
        horizontal, vertical;
    };
    private Position position;
    private boolean sunken = false;
    private boolean added = false;

    private int longitude;
    private int latitude;

    public Ship(int length, Position position) {
        this.id = (int) Counter.getNextNumber();
        this.length = length;
        this.position = position;
    }

    public boolean isSunken() {
        return this.sunken;
    }

    public void hit() {
        this.numberOfHits += 1;
        if(this.numberOfHits >= this.length){
            this.sunken = true;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }

    public void setNumberOfHits(int hit) {
        this.numberOfHits = hit;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setSunken(boolean sunken) {
        this.sunken = sunken;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public String toString() {
        return "x";
    }
}
