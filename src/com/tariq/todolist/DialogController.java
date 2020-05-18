package com.tariq.todolist;

import com.tariq.todolist.datamodel.TodoItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class DialogController {
    @FXML
    private TextField shortDesTextField;
    @FXML
    private TextArea detailsTextArea;
    @FXML
    private DatePicker datePicker;
    @FXML
    public TodoItem ProcessResult(){
        TodoItem todoItem=new TodoItem();
        todoItem.setShortDescription(shortDesTextField.getText());
        todoItem.setDetails(detailsTextArea.getText());
        todoItem.setDeadline(datePicker.getValue());
        DataStore dataStore=new DataStore();
        dataStore.insertTodoItem(todoItem);
        //System.out.println(todoItem);
        return todoItem;
    }


}
