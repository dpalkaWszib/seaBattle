package pl.dawidpalka;

import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class MainApp extends Application {

    public int gameStatus = 1;

    @Override
    public void start(Stage stage) throws Exception {

        Sea aiSea = new Sea();
        Sea playerSea = new Sea();

        Player player = new Player();

        GridPane page = new GridPane();

        Scene scene = new Scene(page, 800, 400);
        page.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        Text scenetitle = new Text("Welcome");
        scenetitle.setId("welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        page.add(scenetitle, 0, 0, 2, 1);
        page.add(addPlayerSea(playerSea, player), 0, 1);
        page.add(addAiSea(aiSea, playerSea, page, player), 1, 1);

        page.getColumnConstraints().add(new ColumnConstraints(400)); // column 0 is 100 wide
        page.getColumnConstraints().add(new ColumnConstraints(400)); // column 1 is 200 wide

        stage.setTitle("Seabattle");
        stage.setScene(scene);
        stage.show();

    }


    private GridPane addPlayerSea(Sea playerSea, Player player) {

        GridPane grid = new GridPane();
        grid.setId("playerGrid");


        for(int i=0; i< playerSea.getSea().length; i++){
            for(int j=0; j< playerSea.getSea()[i].length; j++){

                int longitude = i;
                int latitude = j;

                Label label = new Label("");
                    label.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center;");
                    label.setId(longitude+"-"+latitude);
                    label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    label.setOnMouseEntered(e -> {
                        if(gameStatus == 1){
                            this.drawShip(longitude, latitude, grid, player);
                        }

                    });
                    label.setOnMouseExited(e -> {
                        if(gameStatus == 1) {
                            this.clearShip(longitude, latitude, grid);
                        }
                    });
                    label.setOnMouseClicked(e -> {
                        if(gameStatus == 1) {

                            if (e.getButton() == MouseButton.PRIMARY) {

                                this.addShip(longitude, latitude, grid, player, playerSea);

                            } else if (e.getButton() == MouseButton.SECONDARY) {
                                this.shipRoration(player);
                                this.clearShip(longitude, latitude, grid);
                                this.drawShip(longitude, latitude, grid, player);
                            }
                        }
                    });
                    grid.add(label, j, i);
            }

        }

        grid.setStyle("-fx-background-color: black; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
        grid.setSnapToPixel(false);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(10);
        column.setHalignment(HPos.CENTER);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(10);
        row.setValignment(VPos.CENTER);
        for (int i = 0; i < 10; i++) {
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }

        return grid;
    }

    private GridPane addAiSea(Sea aiSea, Sea playerSea, GridPane page, Player player) {

        GridPane grid = new GridPane();

        Ai ai = new Ai();
        ai.init(aiSea);

        for (int i = 0; i < aiSea.getSea().length; i++) {
            for (int j = 0; j < aiSea.getSea()[i].length; j++) {
                String sTemp = "o";

                if (aiSea.getSea()[i][j] != null) {
                    sTemp = aiSea.getSea()[i][j].toString();
                }
                System.out.print("[" + sTemp + "]");
            }

            System.out.println();
        }


        for (int i = 0; i < aiSea.getSea().length; i++) {
            for (int j = 0; j < aiSea.getSea()[i].length; j++) {

                int longitude = i;
                int latitude = j;


                Label label = new Label("");
                label.setId(i + "-" + j);
                label.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center;");
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

                label.setOnMouseEntered(e -> {
                    if(gameStatus == 2) {
                        this.drawShot(longitude, latitude, grid);
                    }

                });
                label.setOnMouseExited(e -> {
                    if(gameStatus == 2) {
                        this.clearShot(longitude, latitude, grid);
                    }
                });

                label.setOnMouseClicked(e -> {
                    if(gameStatus == 2) {
                        if (e.getButton() == MouseButton.PRIMARY) {
                            this.shot(longitude, latitude, grid, aiSea, player);
                            GridPane playerGrid = (GridPane) page.lookup("#playerGrid");
                            this.aiShot(playerGrid, playerSea, ai);
                        }
                    }
                });
                grid.add(label, j, i);
            }

        }


        grid.setStyle("-fx-background-color: black; -fx-padding: 2; -fx-hgap: 2; -fx-vgap: 2;");
        grid.setSnapToPixel(false);

        ColumnConstraints column = new ColumnConstraints();
        column.setPercentWidth(10);
        column.setHalignment(HPos.CENTER);
        RowConstraints row = new RowConstraints();
        row.setPercentHeight(10);
        row.setValignment(VPos.CENTER);
        for (int i = 0; i < 10; i++) {
            grid.getColumnConstraints().add(column);
            grid.getRowConstraints().add(row);
        }
        return grid;
    }

    private void aiShot(GridPane grid, Sea playerSea, Ai ai) {
        List<int[]> field = ai.getFields();
        Random rand = new Random();
        int randomIndex = rand.nextInt(field.size());
        int[] randomCoordinate = field.get(randomIndex);
        field.remove(randomIndex);
        ai.setFields(field);

        this.shot(randomCoordinate[0], randomCoordinate[1], grid, playerSea, ai);
    }

    private void shot(int longitude, int latitude, GridPane grid, Sea aiSea, Player player) {

        String id = "#"+longitude+"-"+latitude;
        Label label = (Label) grid.lookup(id);
        if(label.getUserData() == null || !label.getUserData().equals("hit")) {

            Ship shot = aiSea.shot(longitude, latitude, player);

            label.setUserData("hit");

            if (shot != null) {
                label.setText("X");
                if (shot.isSunken()) {
                    System.out.println("Zatopiony");

                    for (int i = 0; i < shot.getLength(); i++) {
                        String shipId;

                        if (shot.getPosition().equals(Ship.Position.horizontal)) {
                            shipId = "#" + shot.getLongitude() + "-" + (shot.getLatitude() + i);
                        } else {
                            shipId = "#" + (shot.getLongitude() + i) + "-" + shot.getLatitude();
                        }
                        Label next = (Label) grid.lookup(shipId);
                        next.setStyle("-fx-background-color: #e03b3c; -fx-alignment: center;");
                    }

                    if(player.getPoints() == 20){
                        gameStatus = 3;
                        Text welcome = (Text) grid.getParent().lookup("#welcome");
                        welcome.setText("Winner: "+ player.getName());
                    }


                }
            } else {
                label.setText("O");
            }
        }

    }

    private void drawShip(int longitude, int latitude, GridPane grid, Player player) {

        Ship ship = player.getShips().stream().filter(s -> !s.isAdded()).findFirst().orElse(null);

        if(ship != null){
            if(ship.getPosition().equals(Ship.Position.horizontal) && latitude + ship.getLength() <= 10 ||
                ship.getPosition().equals(Ship.Position.vertical) && longitude + ship.getLength() <= 10) {
                for (int i = 0; i < ship.getLength(); i++) {

                    System.out.println("test");
                    String id;

                    if(ship.getPosition().equals(Ship.Position.horizontal)){
                        id = "#"+longitude+"-"+(latitude+i);
                    }else {
                        id = "#"+(longitude+i)+"-"+latitude;
                    }
                    Label next = (Label) grid.lookup(id);

                    next.setStyle("-fx-background-color: #e0db82; -fx-alignment: center;");
                    System.out.println(next);
                }

            }
        }
    }

    private void drawShot(int longitude, int latitude, GridPane grid) {
        String id = "#"+longitude+"-"+latitude;
        Label label = (Label) grid.lookup(id);
        if(label.getUserData() == null) {
            label.setStyle("-fx-background-color: #e0db82; -fx-alignment: center;");
        }
    }

    private void clearShot(int longitude, int latitude, GridPane grid) {
        String id = "#"+longitude+"-"+latitude;
        Label label = (Label) grid.lookup(id);
        if(label.getUserData() == null) {
            label.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center;");
        }
    }

    private void addShip(int longitude, int latitude, GridPane grid, Player player, Sea sea) {

        Ship ship = player.getShips().stream().filter(s -> !s.isAdded()).findFirst().orElse(null);

        if(ship != null){

            if(sea.addShip(ship, longitude, latitude)){
                ship.setAdded(true);
                for (int i = 0; i < ship.getLength(); i++) {
                    String id;

                    if(ship.getPosition().equals(Ship.Position.horizontal)){
                        id = "#"+longitude+"-"+(latitude+i);
                    }else {
                        id = "#"+(longitude+i)+"-"+latitude;
                    }
                    Label next = (Label) grid.lookup(id);
                    next.setStyle("-fx-background-color: #575fe0; -fx-alignment: center;");
                    next.setUserData("added");
                }
            }
        }else{
            gameStatus = 2;
        }
    }

    private void shipRoration(Player player) {

        Ship ship = player.getShips().stream().filter(s -> !s.isAdded()).findFirst().orElse(null);

        if(ship != null){

            if(ship.getPosition().equals(Ship.Position.vertical)){
                ship.setPosition(Ship.Position.horizontal);
            }else{
                ship.setPosition(Ship.Position.vertical);
            }
        }
    }

    private void clearShip(int longitude, int latitude, GridPane grid) {

        for (int i = 0; i < 10; i++) {
            Label next = (Label) grid.lookup("#"+longitude+"-"+i);
            Object test = next.getUserData();
            if(test == null){
                next.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center;");
            }else {
                next.setStyle("-fx-background-color: #575fe0; -fx-alignment: center;");
            }
        }
        for (int i = 0; i < 10; i++) {
            Label next = (Label) grid.lookup("#"+i+"-"+latitude);
            if(next.getUserData() == null){
                next.setStyle("-fx-background-color: #e0e0e0; -fx-alignment: center;");
            }else {
                next.setStyle("-fx-background-color: #575fe0; -fx-alignment: center;");
            }
        }

    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setMaxSize(30, 30);
        button.setOnAction(e -> System.out.println(text));
//        button.styleProperty().setValue("-fx-background-color: black");
        return button ;
    }



}
