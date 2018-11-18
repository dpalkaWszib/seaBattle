package pl.dawidpalka;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Ship> ships = new ArrayList<>();
    private int points = 0;
    private String name = "Player";

    public Player() {
        ships.add(new Ship(4, Ship.Position.horizontal));
        ships.add(new Ship(3, Ship.Position.horizontal));
        ships.add(new Ship(3, Ship.Position.horizontal));
        ships.add(new Ship(2, Ship.Position.horizontal));
        ships.add(new Ship(2, Ship.Position.horizontal));
        ships.add(new Ship(2, Ship.Position.horizontal));
        ships.add(new Ship(1, Ship.Position.horizontal));
        ships.add(new Ship(1, Ship.Position.horizontal));
        ships.add(new Ship(1, Ship.Position.horizontal));
        ships.add(new Ship(1, Ship.Position.horizontal));
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
