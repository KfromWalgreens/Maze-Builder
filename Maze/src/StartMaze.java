import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;


public class StartMaze extends Application{
    private Button[][] but;

    private char getColorChar(Button b)
    {
        if (b.getStyle().indexOf("blue") > -1) {
            //System.out.print("0");
            return '0';
        }
        else if (b.getStyle().indexOf("white") > -1) {
            //System.out.print("1");
            return '1';
        }
        else if (b.getStyle().indexOf("green") > -1) {
            //System.out.print("S");
            return 'S';
        }
        else {
            //System.out.print("E");
            return 'E';
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        TextInputDialog dialog = new TextInputDialog("0x0");
        dialog.setTitle("Maze Builder");
        dialog.setHeaderText("Welcome to Maze Builder!");
        dialog.setContentText("Please enter your dimensions (in format rows x columns):");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String[] input = (result.get()).split("x");
            int rows = Integer.parseInt(input[0]);
            int columns = Integer.parseInt(input[1]);
            Stage stage = new Stage();

            GridPane gridPane = new GridPane();

            but = new Button[rows][columns];

            for(int i = 0; i < rows; i++){
                for(int j = 0; j < columns; j++){
                    but[i][j] = new Button();
                    gridPane.add(but[i][j], i, j);
                    but[i][j].setStyle("-fx-background-color: blue;");
                    but[i][j].setPrefSize(30, 30);
                    but[i][j].setOnAction(new EventHandler<ActionEvent>(){
                        @Override
                        public void handle( ActionEvent e)
                        {
                            //e.getSource() returns the button that was clicked.
                            Button b = (Button) e.getSource();

                            //a maze button was clicked

                                char val = getColorChar(b);
                                if (val == '0') {  //the button is currently blue so change it to white
                                    b.setStyle("-fx-background-color: white;");
                                }
                                else if (val == '1') {
                                    //TODO: Set background color to green
                                    b.setStyle("-fx-background-color: green;");
                                }
                                else if (val == 'S') {
                                    //TODO: Set background color to red
                                    b.setStyle("-fx-background-color: red;");
                                }
                                else {
                                    //TODO: Set background color back to blue
                                    b.setStyle("-fx-background-color: blue;");
                                }
                            }
                    });
                }
            }

            FileChooser fileChooser = new FileChooser();
            Button save = new Button("Save");
            save.setOnAction(e -> {
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                PrintStream output = null;
                try {
                    output = new PrintStream(new File(String.valueOf(selectedFile)));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                output.println(rows + " " + columns);
                int count = 0;
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < columns; j++){
                        output.print(getColorChar(but[i][j]));
                        count++;
                        if(count == rows){
                            output.println();
                            count = 0;
                        }
                    }
                }
            });
            Button exit = new Button("Exit");
            exit.setOnAction(e -> {
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                PrintStream output = null;
                try {
                    output = new PrintStream(new File(String.valueOf(selectedFile)));
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                output.println(rows + " " + columns);
                int count = 0;
                for(int i = 0; i < rows; i++){
                    for(int j = 0; j < columns; j++){
                        output.print(getColorChar(but[i][j]));
                        count++;
                        if(count == rows){
                            output.println();
                            count = 0;
                        }
                    }
                }
                System.exit(0);
            });

            HBox hbox = new HBox(save, exit);
            Label label1 = new Label("");
            Label label2 = new Label("");
            Label label3 = new Label("");
            hbox.setAlignment(Pos.BASELINE_CENTER);
            BorderPane border_pane = new BorderPane(gridPane, label1, label2, hbox, label3);
            border_pane.setAlignment(hbox, Pos.CENTER);

            Scene scene = new Scene(border_pane, rows * 50, columns *50);
            stage.setScene(scene);
            stage.show();

        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

