package com.tariq.todolist;

import com.mysql.jdbc.PreparedStatement;
import com.tariq.todolist.datamodel.TodoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataStore {
    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter df;
    private Connection connection;
    //constructor
    public DataStore() {
        todoItems= FXCollections.observableArrayList();
        df=DateTimeFormatter.ofPattern("MMMM d, yyyy");
        connectToDataBase();
    }
    //load data
    public void loadData(){
        try {
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery("SELECT * FROM todotable;");
            while(resultSet.next()){
                TodoItem todoItem=new TodoItem();
                todoItem.setShortDescription(resultSet.getString("shortDes"));
                todoItem.setDetails(resultSet.getString("details"));
                todoItem.setDeadline(resultSet.getDate("deadline").toLocalDate());
                if(todoItem!=null) {
                    todoItems.add(todoItem);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public ObservableList<TodoItem> getTodoItems() {
        //loadData();
        return todoItems;
    }
    private void connectToDataBase() {
        String url="jdbc:mysql://localhost:3306/todolist?characterEncoding=UTF-8&useSSL=false";
        String user="root";
        String pass="root";
        try {
            connection= DriverManager.getConnection(url, user, pass);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void insertTodoItem(TodoItem todoItem){
        String query="INSERT INTO todotable(shortDes, details, deadline)" +
                "VALUES(?, ?, ?)";
        try {

            PreparedStatement preparedStatement= (PreparedStatement) connection.prepareStatement(query);
            preparedStatement.setString(1, todoItem.getShortDescription());
            preparedStatement.setString(2, todoItem.getDetails());
            preparedStatement.setString(3, todoItem.getDeadline().toString());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
    public void update(int ID, String shortDes, String details, LocalDate deadline){
        String qry="UPDATE todotable SET shortDes=?, details=?, deadline=? where ID=?;";
        String query = "UPDATE `couplet` SET LINE1=?, LINE2=? WHERE coupletId=? and poemId=?;";
        try {
            PreparedStatement preparedStatement= (PreparedStatement) connection.prepareStatement(qry);
            preparedStatement.setString(1, shortDes);
            preparedStatement.setString(2, details);
            preparedStatement.setString(3, deadline.toString());
            preparedStatement.setInt(4, ID);
            preparedStatement.executeUpdate();
            //statement.executeUpdate();
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }
    public boolean deleteTodoItem(TodoItem todoItem){
        String query="DELETE FROM todotable WHERE shortDes=?;";
        try {
            PreparedStatement preparedStatement= (PreparedStatement) connection.prepareStatement(query);
            preparedStatement.setString(1, todoItem.getShortDescription());
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            return false;
        }
    }

}
