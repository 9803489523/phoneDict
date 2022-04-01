package Menu;

import Enteties.Phone;
import JDBC.JDBCWorker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@ToString
@Getter
@Setter
@EqualsAndHashCode
public class MyMenu {

    private Scanner scanner=new Scanner(System.in);

    private JDBCWorker jdbcWorker;

    private String adminname;

    private String password;

    public MyMenu(String username, String password) throws SQLException {
        this.adminname = username;
        this.password=password;
        jdbcWorker=new JDBCWorker(JDBCWorker.defaultUrl,JDBCWorker.defaultUser,JDBCWorker.defaultPassword);
    }

    public void worAsUser() throws SQLException {
        String userMenu=
                """
                Выберете режим работы:
                1-посмотреть весь справочник
                2-найти по адресу
                3-найти по номеру
                4-найти по имени
                0-выход
                """;
        int choose=10;
        System.out.println("Вы в режиме пользователя!");
        while (choose!=0){
            System.out.println(userMenu);
            choose=scanner.nextInt();
            if(choose==1){
                print(jdbcWorker.read());
            }
            else {
                if (choose == 2) {
                    searchByAddress();
                }
                else {
                    if(choose==3){
                        searchByPhone();
                    }
                    else{
                        if(choose==4){
                            searchByOwner();
                        }
                        else
                            if(choose==0)
                                break;
                            else
                                System.out.println("Введите корректные данные");
                    }
                }
            }


        }

    }

    public void work() throws SQLException {
        if(autorization())
            workAsAdmin();
        else
            worAsUser();
    }

    public void workAsAdmin() throws SQLException {
        String userMenu=
                """
                Выберете режим работы:
                1-посмотреть весь справочник
                2-найти по адресу
                3-найти по номеру
                4-найти по имени
                5-создать запись
                6-редактировать запись
                0-выход
                """;
        int choose=10;
        System.out.println("Вы в режиме админа!!!");
        while (choose!=0){
            System.out.println(userMenu);
            choose=scanner.nextInt();
            if(choose==1){
                print(jdbcWorker.read());
            }
            else {
                if (choose == 2) {
                    searchByAddress();
                }
                else {
                    if(choose==3){
                        searchByPhone();
                    }
                    else{
                        if(choose==4){
                            searchByOwner();
                        }
                        else
                            if(choose==5) {
                            create();
                            }
                            else{
                                if(choose==6) {
                                    update();
                                }
                                else {
                                    if(choose==0)
                                        break;
                                    else
                                        System.out.println("Введите корректные данные");
                                }
                            }

                    }
                }
            }


        }
    }

    public boolean autorization(){
        System.out.println("Введите имя администратора ");
        String name=scanner.next();
        System.out.println("Введите пароль ");
        String password=scanner.next();
        return name.equals(adminname) && password.equals(this.password);
    }

    public void searchByAddress() throws SQLException {
        System.out.println("Введите адрес");
        String address=scanner.next();
        if(jdbcWorker.findByAddress(address).isEmpty())
            System.out.println("Результатов не найдено");
        else
            print(jdbcWorker.findByAddress(address));
    }

    public void searchByOwner() throws SQLException {
        System.out.println("Введите имя владельца");
        String address=scanner.next();
        if(jdbcWorker.findByOwner(address).isEmpty())
            System.out.println("Результатов не найдено");
        else
            print(jdbcWorker.findByOwner(address));
    }

    public void searchByPhone() throws SQLException {
        System.out.println("Введите номер телефона");
        String address=scanner.next();
        if(jdbcWorker.findByPhoneNumber(address).isEmpty()) {
            System.out.println("Результатов не найдено");
        }
        else
            print(jdbcWorker.findByPhoneNumber(address));
    }

    public void create() throws SQLException {
        System.out.println("Введите данные контакта (адрес, владелец, номер телефона)");
        String a=scanner.next();
        String v=scanner.next();
        String n=scanner.next();
        jdbcWorker.create(a,v,n);
    }

    public void update() throws SQLException {
        print(jdbcWorker.read());
        System.out.println("Введите id контакта, который необходимо изменить");
        int id=scanner.nextInt();
        System.out.println("Введите данные контакта (адрес, владелец, номер телефона)");
        String a=scanner.next();
        String v=scanner.next();
        String n=scanner.next();
        jdbcWorker.update(id,a,v,n);
    }

    public void print(List<Phone> phones){
        System.out.println("\t\t\t\t\t\t\t\t\t\tСправочник:");
        for(Phone phone:phones){
            System.out.print(phone);
        }
    }
}
