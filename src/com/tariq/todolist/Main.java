package com.tariq.todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Parent root = FXMLLoader.load(getClass().getResource("main_window.fxml"));
            primaryStage.setTitle("Todo List");
            primaryStage.setScene(new Scene(root, 900, 500));
            primaryStage.show();
        }catch (Exception e){
            System.out.println(e.getMessage()+"\n"+e.getCause());
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        DataStore dataStore=new DataStore();
       // dataStore.update(7, "Do homework1", "Homework of database", LocalDate.now());
       // dataStore.loadData();
       // dataStore.deleteTodoItem(5);
        //load data
    }

    @Override
    public void stop() throws Exception {
        //save data before exit
    }
}
