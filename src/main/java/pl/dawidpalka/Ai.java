package pl.dawidpalka;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ai extends Player{

    private List<Ship> ships = new ArrayList<>();
    private List<int[]> fields = new ArrayList<>();
    private int points = 0;
    private String name = "Ai";

    public void init(Sea sea) {
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


        this.addShips(sea);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                int temp[] = {i, j};
                fields.add(temp);
            }
        }
    }

    public void addShips(Sea sea) {

        Random generator = new Random();
        boolean added = false;
        for (Ship ship : ships) {
            int pick = generator.nextInt(Ship.Position.values().length);
            ship.setPosition(Ship.Position.values()[pick]);
            while (!sea.addShip(ship, generator.nextInt(10), generator.nextInt(10)));
        }

    }

    public List<int[]> getFields() {
        return fields;
    }

    public void setFields(List<int[]> fields) {
        this.fields = fields;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
