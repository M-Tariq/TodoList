package com.tariq.todolist;
import com.tariq.todolist.datamodel.TodoItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class Controller {
    ObservableList<TodoItem> todoItems;
    @FXML
    private ListView listView;
    @FXML
    private Label deadlineLabel;
    @FXML
    private TextArea detailsTextArea;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private ContextMenu listContextMenu;

    public void initialize() {
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                TodoItem item = (TodoItem) listView.getSelectionModel().getSelectedItem();
                DataStore dataStore=new DataStore();
                dataStore.deleteTodoItem(item);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);
        DataStore dataStore=new DataStore();
        dataStore.loadData();
        todoItems=dataStore.getTodoItems();
        listView.setItems(todoItems);
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                TodoItem todoItem= (TodoItem) listView.getSelectionModel().getSelectedItem();
                detailsTextArea.setText(todoItem.getDetails());
                DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM d, YYYY");
                deadlineLabel.setText(dateTimeFormatter.format(todoItem.getDeadline()));
            }
        });

        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.getSelectionModel().selectFirst();
    }

    @FXML
    public void showItemDialog(javafx.event.ActionEvent event) {
        Dialog<ButtonType> dialog=new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("todo_item_dialog.fxml"));
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("todo_item_dialog.fxml"));

            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        DialogController controller=fxmlLoader.getController();

        Optional<ButtonType> result=dialog.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK){
            TodoItem newItem= controller.ProcessResult();
            todoItems.add(newItem);
           listView.getSelectionModel().select(newItem);
        }else{
            System.out.println("Cancel Pressed");
        }
    }
//    @FXML
//    public void handleClickListView(){
//        TodoItem todoItem= (TodoItem) listView.getSelectionModel().getSelectedItem();
//        StringBuilder stringBuilder=new StringBuilder(todoItem.getDetails());
//        stringBuilder.append("\n\n\n\n");
//        deadlineLabel.setText(todoItem.getDeadline().toString());
//        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("MMMM d, y");
//        detailsTextArea.setText(stringBuilder.toString());
//    }
}
