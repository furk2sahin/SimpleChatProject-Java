

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class DatabaseOperations {
    private static final DatabaseConnection databaseConnection = new DatabaseConnection();
    
    private Statement statement = null;
    
    private PreparedStatement preparedStatement = null;
       
    private String query;
    
    public ArrayList<User> getUsers(String userEmail){
        ArrayList<User> users = new ArrayList<User>();
        query = "SELECT * FROM USER WHERE EMAIL != ?";;
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userEmail);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setAge(resultSet.getInt("AGE"));
                user.setPhoneNumber(resultSet.getString("PHONENUMBER"));
                user.setEmail(resultSet.getString("EMAIL"));
                users.add(user);
            }
            return users;
        } catch (SQLException ex) {
            System.out.println("Cannot get users");
        }
        return null;
        
    }
    
    public ArrayList<Email> getEmails(int senderUser, int receiverUser){
        ArrayList<Email> emails = new ArrayList<Email>();
        query = "SELECT * FROM MAIL WHERE USER_SENDER = ? AND USER_RECEIVER = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, senderUser);
            preparedStatement.setInt(2, receiverUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                Email email = new Email();
                email.setId(resultSet.getInt("EMAIL_ID"));
                email.setSenderUser(resultSet.getInt("USER_SENDER"));
                email.setReceiverUser(resultSet.getInt("USER_RECEIVER"));
                email.setContent(resultSet.getString("MAIL_CONTENT"));
                email.setTimestamp(resultSet.getTimestamp("SENDINGDATE"));
                emails.add(email);
            }
            return emails;
        } catch (SQLException ex) {
            System.out.println("Cannot get emails.");
        }
       return null;
    }
    
    public void deleteEmail(int mailId){
        query = "DELETE FROM MAIL WHERE EMAIL_ID = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, mailId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot delete mail");
        } 
    }
    
    public void addUser(User user){
        query = "INSERT INTO USER(NAME, SURNAME, AGE, EMAIL, PASSWORD, PHONENUMBER, REGISTERDATE)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setTimestamp(7, new Timestamp(new Date().getTime()));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("User add operation failed.");
        }
    }
    
    public User getUser(String email){
        query = "SELECT * FROM USER WHERE EMAIL = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            User user = new User();
            user.setId(resultSet.getInt("ID"));
            user.setName(resultSet.getString("NAME"));
            user.setSurname(resultSet.getString("SURNAME"));
            user.setAge(resultSet.getInt("AGE"));
            user.setPhoneNumber(resultSet.getString("PHONENUMBER"));
            user.setEmail(resultSet.getString("EMAIL"));
            return user;
        } catch (SQLException ex) {
            System.out.println("Cannot get user");
        }
        return null;
    }
    
    public boolean addEmail(int senderId, int receiverId, String content){
        query = "SELECT * FROM MAIL WHERE USER_SENDER = ? AND USER_RECEIVER = ? AND MAIL_CONTENT = ?";
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, senderId);
            preparedStatement.setInt(2, receiverId);
            preparedStatement.setString(3, content);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                query = "INSERT INTO MAIL (USER_SENDER, USER_RECEIVER, MAIL_CONTENT, SENDINGDATE)"
                + "VALUES (?, ?, ?, ?)";
        
                try {
                    preparedStatement = databaseConnection.getConnection().prepareStatement(query);
                    preparedStatement.setInt(1, senderId);
                    preparedStatement.setInt(2, receiverId);
                    preparedStatement.setString(3, content);
                    preparedStatement.setTimestamp(4, new Timestamp(new Date().getTime()));
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Cannot add email");
                }
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Cannot get mails");
        }
        return false;
    }
    
    public void deleteUser(int userId){        
        query = "DELETE FROM MAIL WHERE USER_SENDER = ? OR USER_RECEIVER = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot delete mails");
        }

        query = "DELETE FROM USER WHERE ID = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Cannot delete user");
        }
    }
    
    public User login(String email, String password){
        query = "SELECT * FROM USER WHERE EMAIL = ? AND PASSWORD = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setSurname(resultSet.getString("SURNAME"));
                user.setAge(resultSet.getInt("AGE"));
                user.setEmail(resultSet.getString("EMAIL"));
                user.setPassword(resultSet.getString("PASSWORD"));
                user.setPhoneNumber(resultSet.getString("PHONENUMBER"));
                user.setRegisterDate(resultSet.getTimestamp("REGISTERDATE"));
                return user;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Login error");
        }
        return null;
    }
    
    public boolean editUser(String name, String surname, int age, String password, String phoneNumber, int id){
        query = "SELECT * FROM USER WHERE ID = ?";
        String userName;
        String userSurname;
        int userAge;
        String userPassword;
        String userPhoneNumber;
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userName = resultSet.getString("NAME");
            userSurname = resultSet.getString("SURNAME");
            userAge = resultSet.getInt("AGE");
            userPassword = resultSet.getString("PASSWORD");
            userPhoneNumber = resultSet.getString("PHONENUMBER");
            if(userName.equals(name) && userSurname.equals(surname) && userAge == age 
                    && userPassword.equals(password) && userPhoneNumber.equals(userPhoneNumber)){
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("Cannot get informations");
        }
        
        
        query = "UPDATE USER SET NAME = ?, SURNAME = ?, AGE = ?, PASSWORD = ?,"
                + "PHONENUMBER = ? WHERE ID = ?";
        
        try {
            preparedStatement = databaseConnection.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, age);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, phoneNumber);
            preparedStatement.setInt(6, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Cannot edit user");
        }
        return false;
    }
    
    public boolean mailControl(String mail){
        query = "SELECT * FROM USER WHERE EMAIL ='" + mail + "'";
        
        try {
            statement = databaseConnection.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return resultSet.next();
        } catch (SQLException ex) {
            System.out.println("Mail control error.");
        }
        return false;
    }
}
