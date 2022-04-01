package JDBC;

import Enteties.Phone;
import lombok.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class JDBCWorker {

    public static final String defaultUrl = "jdbc:postgresql://localhost:5433/phones";

    public static final String defaultUser = "postgres";

    public static final String defaultPassword = "1234";

    private Connection connection;

    public JDBCWorker(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
    }

    /**
     * user
     */
    public List<Phone> read() throws SQLException {
        List<Phone> phones=new ArrayList<>();
        try(Statement stmt= connection.createStatement()) {
            try (ResultSet result=stmt.executeQuery(SqlRequests.findAll) ){

                while (result.next()){
                    Phone phone=new Phone();
                    phone.setPhoneNumber(result.getString("phone_number"));
                    phone.setAddress(result.getString("address"));
                    phone.setOwner(result.getString("owner"));
                    phone.setId(result.getInt("id"));
                    phones.add(phone);
                }
            }
        }
        catch (SQLException e){}
        Collections.sort(phones);
        return phones;
    }

    /**
     * user
     */
    public List<Phone> findByAddress(String address) throws SQLException {
        List<Phone> phones=new ArrayList<>();
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.findByAddress)) {
            pstmt.setString(1,address);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    Phone phone=new Phone();
                    phone.setPhoneNumber(result.getString("phone_number"));
                    phone.setAddress(result.getString("address"));
                    phone.setOwner(result.getString("owner"));
                    phone.setId(result.getInt("id"));
                    phones.add(phone);
                }
            }
        }
        catch (SQLException e){}
        Collections.sort(phones);
        return phones;
    }

    /**
     * user
     */
    public List<Phone> findByOwner(String owner) throws SQLException {
        List<Phone> phones=new ArrayList<>();
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.findByOwner)) {
            pstmt.setString(1,owner);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    Phone phone=new Phone();
                    phone.setPhoneNumber(result.getString("phone_number"));
                    phone.setAddress(result.getString("address"));
                    phone.setOwner(result.getString("owner"));
                    phone.setId(result.getInt("id"));
                    phones.add(phone);
                }
            }
        }
        catch (SQLException e){}
        Collections.sort(phones);
        return phones;
    }

    /**
     * user
     */
    public List<Phone> findByPhoneNumber(String phoneNumber) throws SQLException {
        List<Phone> phones=new ArrayList<>();
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.findByPhoneNumber)) {
            pstmt.setString(1,phoneNumber);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    Phone phone=new Phone();
                    phone.setPhoneNumber(result.getString("phone_number"));
                    phone.setAddress(result.getString("address"));
                    phone.setOwner(result.getString("owner"));
                    phone.setId(result.getInt("id"));
                    phones.add(phone);
                }
            }
        }
        Collections.sort(phones);
        return phones;
    }

    /**
     * admin
     */
    public void create(String address,String owner, String phoneNumber) throws SQLException {
        PreparedStatement p1=connection.prepareStatement(SqlRequests.insertAddress);
        PreparedStatement p2=connection.prepareStatement(SqlRequests.insertOwner);
        PreparedStatement p3=connection.prepareStatement(SqlRequests.insertPhone);
        p1.setString(1,address);
        p2.setString(1,owner);
        p3.setString(1,phoneNumber);

        try{
            p1.executeUpdate();

        }
        catch (SQLException e){}

        try {
            p2.executeUpdate();
        }
        catch (SQLException e){}

        try {
            p3.executeUpdate();
        }
        catch (SQLException e){}
        PreparedStatement pstmt2=connection.prepareStatement(SqlRequests.insertIntoMain);

        pstmt2.setInt(1,getOwnerId(owner));
        pstmt2.setInt(2,getAddressId(address));
        pstmt2.setInt(3,getPhoneNumberId(phoneNumber));

        try {
            pstmt2.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("Такие данные в справочнике уже есть, попробуйте ввести новые");
        }
    }

    /**
     * admin
     */
    public void update(int id, String address,String owner, String phoneNumber){
        try {
            PreparedStatement p1=connection.prepareStatement(SqlRequests.insertAddress);
            PreparedStatement p2=connection.prepareStatement(SqlRequests.insertOwner);
            PreparedStatement p3=connection.prepareStatement(SqlRequests.insertPhone);
            p1.setString(1,address);
            p2.setString(1,owner);
            p3.setString(1,phoneNumber);

            try{
                p1.executeUpdate();

            }
            catch (SQLException e){}

            try {
                p2.executeUpdate();
            }
            catch (SQLException e){}

            try {
                p3.executeUpdate();
            }
            catch (SQLException e){}

            try {
                PreparedStatement ptsmt2=connection.prepareStatement(SqlRequests.updateDict);
                ptsmt2.setInt(1,getAddressId(address));
                ptsmt2.setInt(2,getOwnerId(owner));
                ptsmt2.setInt(3,getPhoneNumberId(phoneNumber));
                ptsmt2.setInt(4,id);

                ptsmt2.executeUpdate();
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("Введены некорректные данные, попробуйте снова");
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * admin
     */
    public void delete(int id){

    }

    public int getAddressId(String address) throws SQLException {
        int id=0;
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.searchAddressId)) {
            pstmt.setString(1,address);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    id=result.getInt("id");
                }
            }
        }
        return id;
    }

    public int getOwnerId(String owner) throws SQLException {
        int id=0;
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.searchOwnerId)) {
            pstmt.setString(1,owner);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    id=result.getInt("id");
                }
            }
        }
        return id;
    }

    public int getPhoneNumberId(String number) throws SQLException {
        int id=0;
        try(PreparedStatement pstmt= connection.prepareStatement(SqlRequests.searchPhoneNumberId)) {
            pstmt.setString(1,number);
            try (ResultSet result=pstmt.executeQuery() ){
                while (result.next()){
                    id=result.getInt("id");
                }
            }
        }
        return id;
    }


}
