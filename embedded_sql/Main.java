import java.sql.*;
import java.util.*;

import static java.sql.Types.NULL;

public class Main {
    public static void main(String[] args) throws SQLException{

        Connection con;
        try{
            String url      = "jdbc:mysql://localhost:3306/online_retail_store";
            String uname    = "root";
            String pass     = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");

            con  = DriverManager.getConnection(url, uname, pass);
        }catch(Exception e){
            System.out.println("Encountered Error while connecting : "+e);
            return;
        }

        try{

            while(true) {

                System.out.println("\nWelcome to the Online Shopping Store!!!");
                System.out.println("\n                    MENU                             ");
                System.out.println("\n1) Admin Login");
                System.out.println("2) Seller Login");
                System.out.println("3) Customer Login");
                System.out.println("4) Delivery Partner Login");
                System.out.println("5) Customer Signup");                   // TBD
                System.out.println("6) Check Trending Categories");
                System.out.println("7) Check Trending Products");
                System.out.println("8) Exit");

                System.out.println("\nSelect one of the options");

                int op;
                Scanner sc = new Scanner(System.in);
                op = sc.nextInt();
                sc.nextLine();

                if (op == 1) {
                    System.out.println("Enter your username");
                    String usrida = sc.nextLine();                     // (14, 'aruded', 'kncbFp');

                    System.out.println("Enter your password");
                    String pwda = sc.nextLine();

                    String q1 = "Select * FROM Admin WHERE admin_username=? AND admin_password=?";
                    PreparedStatement st1 = con.prepareStatement(q1);

                    st1.setString(1, usrida);
                    st1.setString(2, pwda);
                    int ans = -1;

                    ResultSet rs1 = st1.executeQuery();

                    while (rs1.next()) {
                        if (rs1.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st1.close();


                    if (ans == 1) {
                        while (true) {
                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1)  Add a Delivery Partner");         //done
                            System.out.println("2)  Remove a Delivery Partner");        //done
                            System.out.println("3)  Add a Seller");                     //done
                            System.out.println("4)  Remove a Seller");                  //done
                            System.out.println("5)  Remove a Customer");                //done
                            System.out.println("6)  View Customer Details");            //done
                            System.out.println("7)  View Delivery Partner Details");    //done
                            System.out.println("8)  [OLAP] View Customers with most money spent");      //done
                            System.out.println("9)  [OLAP] View Delivery Partner with most number of orders delivered");        //done
                            System.out.println("10) Show Customer History");
                            System.out.println("11) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {

                                String dpname;
                                String dppass;
                                String dp_firstname;
                                String dp_lastname;
                                String dp_email;
                                String dp_city;
                                String dp_pincode;
                                double dp_mobile;

                                System.out.println("Enter Username   : ");
                                dpname = sc.nextLine();

                                System.out.println("\nEnter Password   : ");
                                dppass = sc.nextLine();

                                System.out.println("\nEnter First Name : ");
                                dp_firstname = sc.nextLine();

                                System.out.println("\nEnter Last Name  : ");
                                dp_lastname = sc.nextLine();

                                System.out.println("\nEnter Mobile No. : ");
                                dp_mobile = sc.nextDouble();

                                System.out.println("\nEnter E mail     : ");
                                dp_email = sc.nextLine();

                                sc.nextLine();

                                System.out.println("\nEnter City       : ");
                                dp_city = sc.nextLine();

                                System.out.println("\nEnter Pin Code   : ");
                                dp_pincode = sc.nextLine();

                                String q11 = "INSERT INTO Delivery_Partner (dp_username,dp_password," +
                                        "dp_firstname,dp_lastname,dp_mobile,dp_email,dp_city," +
                                        "dp_pin_code) VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement st11 = con.prepareStatement(q11);

                                st11.setString(1, dpname);
                                st11.setString(2, dppass);
                                st11.setString(3, dp_firstname);
                                st11.setString(4, dp_lastname);
                                st11.setDouble(5, dp_mobile);
                                st11.setString(6, dp_email);
                                st11.setString(7, dp_city);
                                st11.setString(8, dp_pincode);

                                int rs11;

                                try {
                                    rs11 = st11.executeUpdate();
                                    if (rs11 == 1) {
                                        System.out.println("\nDelivery Partner Added Successfully");
                                    } else {
                                        System.out.println("\nEncountered unknown error while adding Delivery Partner");
                                    }
                                } catch (SQLIntegrityConstraintViolationException e) {
                                    System.out.println("\nDelivery Partner with username " + dpname + " already exists.");
                                }
                                st11.close();

                            } else if (opt == 2) {

                                while (true) {
                                    System.out.println("\n1. Remove Delivery Partner via ID");
                                    System.out.println("2. Remove Delivery Partner via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if (opt2 == 1) {
                                        String q121 = "DELETE FROM Delivery_Partner WHERE dp_id = ?";
                                        PreparedStatement st121 = con.prepareStatement(q121);

                                        System.out.println("\nEnter ID : ");
                                        int dpid = sc.nextInt();

                                        st121.setInt(1, dpid);

                                        int rs121 = 0;
                                        try {
                                            rs121 = st121.executeUpdate();
                                            if (rs121 == 1) {
                                                System.out.println("Delivery Partner removed successfully");
                                            } else {
                                                System.out.println("Encountered error while removing delivery partner");
                                            }
                                        }catch(SQLException e){
                                            if (e.getSQLState().equals("23000")) {
                                                System.out.println("You cannot remove Delivery Partner with pending Deliveries!");
                                            }
                                        }

                                        st121.close();

                                    } else if (opt2 == 2) {
                                        String q122 = "DELETE FROM Delivery_Partner WHERE dp_username = ?";
                                        PreparedStatement st122 = con.prepareStatement(q122);

                                        System.out.println("\nEnter username : ");
                                        String dpname = sc.nextLine();

                                        st122.setString(1, dpname);

                                        int rs122 = 0;
                                        try {
                                            rs122 = st122.executeUpdate();
                                            if (rs122 == 1) {
                                                System.out.println("Delivery Partner removed successfully");
                                            } else {
                                                System.out.println("Encountered error while removing delivery partner");
                                            }
                                        }catch(SQLException e) {
                                            if (e.getSQLState().equals("23000")) {
                                                System.out.println("You cannot remove Delivery Partner with pending Deliveries!");
                                            }
                                        }

                                        st122.close();

                                    } else if (opt2 == 3) {
                                        break;

                                    } else {
                                        System.out.println("Invalid Choice!");
                                    }
                                }
                            } else if (opt == 3) {

                                String sname;
                                String spass;
                                String s_firstname;
                                String s_lastname;
                                String s_email;
                                String s_city;
                                String s_pincode;
                                double s_mobile;

                                System.out.println("Enter Username   : ");
                                sname = sc.nextLine();

                                System.out.println("\nEnter Password   : ");
                                spass = sc.nextLine();

                                System.out.println("\nEnter First Name : ");
                                s_firstname = sc.nextLine();

                                System.out.println("\nEnter Last Name  : ");
                                s_lastname = sc.nextLine();

                                System.out.println("\nEnter Mobile No. : ");
                                s_mobile = sc.nextDouble();

                                System.out.println("\nEnter E mail     : ");
                                s_email = sc.nextLine();

                                sc.nextLine();

                                System.out.println("\nEnter City       : ");
                                s_city = sc.nextLine();

                                System.out.println("\nEnter Pin Code   : ");
                                s_pincode = sc.nextLine();

                                String q13 = "INSERT INTO Seller (s_username,s_password," +
                                        "s_firstname,s_lastname,s_mobile,s_email,s_city," +
                                        "s_pin_code) VALUES (?,?,?,?,?,?,?,?)";

                                PreparedStatement st13 = con.prepareStatement(q13);

                                st13.setString(1, sname);
                                st13.setString(2, spass);
                                st13.setString(3, s_firstname);
                                st13.setString(4, s_lastname);
                                st13.setDouble(5, s_mobile);
                                st13.setString(6, s_email);
                                st13.setString(7, s_city);
                                st13.setString(8, s_pincode);

                                int rs13;

                                try{
                                    rs13 = st13.executeUpdate();
                                    if (rs13 == 1) {
                                        System.out.println("\nSeller Added Successfully");
                                    } else {
                                        System.out.println("\nEncountered error while adding Seller");
                                    }
                                } catch (SQLIntegrityConstraintViolationException e) {
                                    System.out.println("\nSeller with username " + sname + " already exists.");
                                }

                                st13.close();

                            } else if (opt == 4 ) {

                                while (true) {
                                    System.out.println("\n1. Remove Seller via ID");
                                    System.out.println("2. Remove Seller via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if (opt2 == 1) {
                                        String q141 = "DELETE FROM Seller WHERE s_id = ?";
                                        PreparedStatement st141 = con.prepareStatement(q141);

                                        System.out.println("\nEnter ID : ");
                                        int sid = sc.nextInt();

                                        st141.setInt(1, sid);

                                        int rs141 = st141.executeUpdate();

                                        if (rs141 == 1) {
                                            System.out.println("Seller removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing seller");
                                        }
                                        st141.close();

                                    } else if (opt2 == 2) {
                                        String q142 = "DELETE FROM Seller WHERE s_username = ?";
                                        PreparedStatement st142 = con.prepareStatement(q142);

                                        System.out.println("\nEnter username : ");
                                        String sname = sc.nextLine();

                                        st142.setString(1, sname);

                                        int rs142 = st142.executeUpdate();

                                        if (rs142 == 1) {
                                            System.out.println("Seller removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing seller");
                                        }
                                        st142.close();

                                    } else if (opt2 == 3) {
                                        break;

                                    } else {
                                        System.out.println("Invalid Choice!");
                                    }
                                }

                            } else if (opt == 5) {

                                while(true) {
                                    System.out.println("\n1. Remove Customer via ID");
                                    System.out.println("2. Remove Customer via username");
                                    System.out.println("3. Back");
                                    int opt2;
                                    System.out.println("\nSelect one of the options");
                                    opt2 = sc.nextInt();
                                    sc.nextLine();

                                    if(opt2 == 1){
                                        String q151 = "DELETE FROM Customer WHERE cus_id = ?";
                                        PreparedStatement st151 = con.prepareStatement(q151);

                                        System.out.println("\nEnter ID : ");
                                        int cid = sc.nextInt();

                                        st151.setInt(1,cid);

                                        int rs151 = st151.executeUpdate();

                                        if (rs151 == 1) {
                                            System.out.println("Customer removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing Customer");
                                        }
                                        st151.close();

                                    }else if (opt2 == 2) {
                                        String q152 = "DELETE FROM Customer WHERE cus_username = ?";
                                        PreparedStatement st152 = con.prepareStatement(q152);

                                        System.out.println("\nEnter username : ");
                                        String cname = sc.nextLine();

                                        st152.setString(1,cname);

                                        int rs152 = st152.executeUpdate();

                                        if (rs152 == 1) {
                                            System.out.println("Customer removed successfully");
                                        } else {
                                            System.out.println("Encountered error while removing customer");
                                        }
                                        st152.close();

                                    }else if (opt2 ==3){
                                        break;

                                    }else{
                                        System.out.println("Invalid Choice!");
                                    }
                                }

                            } else if (opt == 6) {
                                String q16 = "Select * FROM Customer WHERE cus_id=?";
                                PreparedStatement st16 = con.prepareStatement(q16);

                                System.out.println("Enter the customer id");
                                int cid = sc.nextInt();
                                sc.nextLine();

                                st16.setInt(1, cid);

                                ResultSet rs16 = st16.executeQuery();

                                while (rs16.next()) {
                                    System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s , Wallet Balance =%d ",
                                            rs16.getInt(1), rs16.getString(4), rs16.getString(5), rs16.getInt(13)
                                    ));
                                }

                                st16.close();

                            } else if (opt == 7) {
                                String q17 = "Select *  FROM Delivery_Partner WHERE dp_id=?";
                                PreparedStatement st17 = con.prepareStatement(q17);

                                System.out.println("Enter the delivery partner id");
                                int dpid = sc.nextInt();
                                sc.nextLine();

                                st17.setInt(1, dpid);

                                ResultSet rs17 = st17.executeQuery();

                                while (rs17.next()) {
                                    System.out.println(String.format("Customer id = %s , Firstname = %s , Lastname = %s ,  City =%s , Salary =%d ",
                                            rs17.getInt(1), rs17.getString(2), rs17.getString(3), rs17.getString(6), rs17.getInt(4)
                                    ));
                                }

                                st17.close();

                            } else if (opt == 8) {
                                String q18 = "SELECT cus_id, cus_firstname, cus_lastname, " +
                                        "SUM(cp_quantity * cp_price) AS total_spent " +
                                        "FROM Customer " +
                                        "JOIN Cart ON Customer.cus_cart_id = Cart.cart_id " +
                                        "JOIN Cart_Product_List ON Cart.cart_id = Cart_Product_List.cp_cart_id " +
                                        "GROUP BY cus_id " +
                                        "ORDER BY total_spent DESC " +
                                        "LIMIT ?";

                                PreparedStatement st18 = con.prepareStatement(q18);

                                System.out.println("Enter how many customers from the top do you want to see ");
                                int cno = sc.nextInt();
                                sc.nextLine();

                                st18.setInt(1, cno);

                                ResultSet rs18 = st18.executeQuery();

                                while (rs18.next()) {
                                    System.out.println(String.format("Customer id = %d , Firstname = %s , Lastname = %s ,  Total Spent =%d ",
                                            rs18.getInt(1), rs18.getString(2), rs18.getString(3), rs18.getInt(4)
                                    ));
                                }

                                st18.close();

                            } else if (opt == 9) {
                                String q19 = "SELECT dp.dp_id ,dp.dp_firstname, dp.dp_lastname, COUNT(o.order_id) AS num_deliveries " +
                                        "FROM Delivery_Partner dp " +
                                        "JOIN _Order o ON dp.dp_id = o.order_dp_id " +
                                        "GROUP BY dp.dp_id " +
                                        "ORDER BY num_deliveries DESC " +
                                        "LIMIT ? ";

                                PreparedStatement st19 = con.prepareStatement(q19);

                                System.out.println("Enter how many delivery partners from the top do you want to see ");
                                int dpno = sc.nextInt();
                                sc.nextLine();

                                st19.setInt(1, dpno);

                                ResultSet rs19 = st19.executeQuery();

                                while (rs19.next()) {
                                    System.out.println(String.format("Delivery Partner ID = %d , Firstname = %s , Lastname = %s ,Total Deliveries Completed =%d ",
                                            rs19.getInt(1), rs19.getString(2), rs19.getString(3), rs19.getInt(4)
                                    ));
                                }

                                st19.close();

                            } else if (opt == 10) {
                                String q110 = "Select * FROM Cust_History ";
                                PreparedStatement st110 = con.prepareStatement(q110);

                                ResultSet rs110 = st110.executeQuery();

                                while (rs110.next()) {

                                    System.out.println("Customer id =" + rs110.getString(1));
                                    System.out.println("Firstname:" + rs110.getString(2));
                                    System.out.println("Lastname:" + rs110.getString(3));
                                    System.out.println("Date of Leaving :" + rs110.getTimestamp(4));
                                    System.out.println(" ");
                                }

                                st110.close();

                            } else if (opt == 11) {
                                break;
                            }
                        }

                    }


                } else if (op == 2) {
                    System.out.println("Enter your username");     //'dbagwell0', 'T7UbhttD'
                    String usrids = sc.nextLine();
                    System.out.println("Enter your password");
                    String pwds = sc.nextLine();

                    String q2 = "Select * FROM Seller WHERE s_username=? AND s_password=?";
                    PreparedStatement st2 = con.prepareStatement(q2);

                    st2.setString(1, usrids);
                    st2.setString(2, pwds);
                    int ans = -1;

                    ResultSet rs2 = st2.executeQuery();

                    while (rs2.next()) {
                        if (rs2.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st2.close();

                    if (ans == 1) {

                        while (true) {

                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1) Update Stock"); // done
                            System.out.println("2) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                System.out.println("Enter the product id ");
                                int product_id_update = sc.nextInt();
                                sc.nextLine();

                                System.out.println("Enter the amount by which you want to increase the quantity");
                                int stk = sc.nextInt();
                                sc.nextLine();

                                String q21 = "UPDATE Product SET p_stock = p_stock + ? WHERE p_id=?";
                                PreparedStatement st21 = con.prepareStatement(q21);

                                st21.setInt(1, stk);
                                st21.setInt(2, product_id_update);

                                int rs21 = st21.executeUpdate();

                                if (rs21 == 1) {
                                    System.out.println("Stock added succesfully");
                                } else {
                                    System.out.println("Encountered error while adding product");
                                }

                                st21.close();
                            }else if (opt==2){
                                break;
                            }
                        }
                    }

                } else if (op == 3) {
                    System.out.println("Enter your username");    //'robey17', '6gTZMPZR4'
                    String usridc = sc.nextLine();

                    System.out.println("Enter your password");
                    String pwdc = sc.nextLine();

                    String q3 = "Select * FROM Customer WHERE cus_username=? AND cus_password=?";
                    PreparedStatement st3 = con.prepareStatement(q3);

                    st3.setString(1, usridc);
                    st3.setString(2, pwdc);
                    int ans = -1;

                    ResultSet rs3 = st3.executeQuery();

                    while (rs3.next()) {
                        if (rs3.getInt(1) != NULL) {
                            ans = 1;
                        }
                    }

                    st3.close();

                    if (ans == 1) {
                        while (true) {
                            System.out.println("\nChoose one of the options");
                            System.out.println("\n1) Check wallet balance");
                            System.out.println("2) Update wallet balance");     //TBD
                            System.out.println("3) View cart");
                            System.out.println("4) Check Subscription");        //TBD
                            System.out.println("5) Upgrade Subscription");      //TBD
                            System.out.println("6) Delete Account");
                            System.out.println("7) Checkout");
                            System.out.println("8) Back");


                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                String q31 = "Select * FROM Customer WHERE cus_username=?";
                                PreparedStatement st31 = con.prepareStatement(q31);

                                st31.setString(1, usridc);

                                ResultSet rs31 = st31.executeQuery();

                                while (rs31.next()) {
                                    System.out.println(String.format("Wallet Balance =%d",
                                            rs31.getInt(13)
                                    ));
                                }

                                st31.close();

                            } else if (opt == 3) {
                                String q33 = "SELECT P.p_name,P.p_cost,CP.cp_quantity,CP.cp_price FROM Product as P , Cart_Product_List as CP , Customer as C WHERE P.p_id=CP.cp_pid AND CP.cp_cart_id=C.cus_cart_id AND C.cus_username=?;";
                                PreparedStatement st33 = con.prepareStatement(q33);

                                st33.setString(1, usridc);

                                ResultSet rs33 = st33.executeQuery();

                                while (rs33.next()) {
                                    System.out.println(String.format("Product name = %s , Product cost = %d , product quantity = %d , total price =%d ",
                                            rs33.getString(1), rs33.getInt(2), rs33.getInt(3), rs33.getInt(4)
                                    ));
                                }

                                st33.close();

                            } else if (opt == 6) {
                                String q36 = "DELETE FROM Customer WHERE cus_username=?";
                                PreparedStatement st36 = con.prepareStatement(q36);

                                st36.setString(1, usridc);

                                int rs36 = st36.executeUpdate();

                                if (rs36 == 1) {
                                    System.out.println("Account deleted succesfully , Sorry if you have had a bad experience");
                                    st36.close();
                                    break;
                                } else {
                                    System.out.println("Encountered error while deleting your account!!");
                                }

                                st36.close();

                            } else if (opt == 7) {

                                try {
                                    con.setAutoCommit(false);

                                    String q371 = "SELECT cp_pid,cp_quantity FROM Cart_Product_List" +
                                                  " WHERE cp_cart_id = (SELECT cus_cart_id FROM Customer" +
                                                  " WHERE cus_username=?)";

                                    PreparedStatement st371 = con.prepareStatement(q371);

                                    st371.setString(1, usridc);

                                    ResultSet rs371 = st371.executeQuery();

                                    while (rs371.next()) {
                                        String q372 = "SELECT p_stock FROM Product where p_id = ?";
                                        PreparedStatement st372 = con.prepareStatement(q372);

                                        st372.setInt(1, rs371.getInt(1));
                                        ResultSet rs372 = st372.executeQuery();

                                        if (rs372.next()) {
                                            if (rs372.getInt(1) < rs371.getInt(2)) {
                                                st372.close();
                                                throw new SQLException("One or More Product is out of stock! View cart to know more");
                                            }
                                        }
                                        st372.close();
                                    }
                                    st371.close();

                                    int balance = 0;
                                    String q370 = "Select cus_wallet FROM Customer WHERE cus_username= ? ";
                                    PreparedStatement st370 = con.prepareStatement(q370);

                                    st370.setString(1, usridc);

                                    ResultSet rs370 = st370.executeQuery();

                                    if (rs370.next()) {
                                        balance = rs370.getInt(1);
                                    }

                                    st370.close();

                                    String q373 = "SELECT total_price FROM Cart WHERE cart_id = " +
                                                  "(SELECT cus_cart_id FROM Customer WHERE cus_username=?)";

                                    PreparedStatement st373 = con.prepareStatement(q373);

                                    st373.setString(1, usridc);

                                    ResultSet rs373 = st373.executeQuery();

                                    int o_price = 0;
                                    if( rs373.next() ){
                                        o_price = rs373.getInt(1);
                                    }

                                    if ( o_price > balance) {
                                        st373.close();
                                        throw new SQLException("You are " + (balance - o_price) + " INR short! Remove Items from Cart or Add more Credits to continue");
                                    }
                                    st373.close();

                                    String o_d_stat         = "ordered";
                                    int o_dp_id             = 0;
                                    int o_cus_id            = 0;
                                    String o_cus_firstname  = "";
                                    String o_cus_lastname   = "";
                                    Double o_cus_mobile     = 0.0 ;
                                    String o_cus_email      = "";
                                    String o_cus_street     = "";
                                    String o_cus_city       = "";
                                    String o_cus_pin_code   = "";

                                    String q374 = "SELECT cus_id, cus_firstname, cus_lastname, cus_mobile," +
                                                  " cus_email, cus_street, cus_city, cus_pin_code FROM Customer" +
                                                  " WHERE cus_username=?";

                                    PreparedStatement st374 = con.prepareStatement(q374);

                                    st374.setString(1, usridc);

                                    ResultSet rs374 = st374.executeQuery();

                                    if(rs374.next()){
                                        o_cus_id        = rs374.getInt(1);
                                        o_cus_firstname = rs374.getString(2);
                                        o_cus_lastname  = rs374.getString(3);
                                        o_cus_mobile    = rs374.getDouble(4);
                                        o_cus_email     = rs374.getString(5);
                                        o_cus_street    = rs374.getString(6);
                                        o_cus_city      = rs374.getString(7);
                                        o_cus_pin_code  = rs374.getString(8);
                                    }
                                    st374.close();

                                    String q375 = "SELECT COUNT(*) FROM Delivery_Partner";
                                    PreparedStatement st375 = con.prepareStatement(q375);

                                    ResultSet rs375 = st375.executeQuery();

                                    int mod=0;
                                    if(rs375.next()){
                                        mod = rs375.getInt(1);
                                    }

                                    o_dp_id = o_cus_id%mod +1;

                                    String q376 = "INSERT INTO _Order (order_delivery_status, order_cus_id," +
                                                  " order_dp_id, order_date, order_ship_date, order_cus_firstname," +
                                                  " order_cus_lastname, order_cus_mobile, order_cus_email," +
                                                  " order_cus_street, order_cus_city, order_cus_pin_code)" +
                                                  " VALUES (?, ?, ?, CURRENT_DATE(), DATE_ADD(CURRENT_DATE()," +
                                                  " INTERVAL FLOOR(RAND() * 2) + 4 DAY),?, ?, ?, ?, ?, ?, ?)";

                                    PreparedStatement st376 = con.prepareStatement(q376);

                                    st376.setString(1,o_d_stat);
                                    st376.setInt(2,o_cus_id);
                                    st376.setInt(3,o_dp_id);
                                    st376.setString(4,o_cus_firstname);
                                    st376.setString(5,o_cus_lastname);
                                    st376.setDouble(6,o_cus_mobile);
                                    st376.setString(7,o_cus_email);
                                    st376.setString(8,o_cus_street);
                                    st376.setString(9,o_cus_city);
                                    st376.setString(10,o_cus_pin_code);

                                    int rs376 = st376.executeUpdate();

                                    if (rs376 == 1){
                                        System.out.println("Order Assembled Successfully!");
                                    } else {
                                        st376.close();
                                        throw new SQLException("Encountered unknown error while assembling Order");
                                    }
                                    st376.close();


                                    String q377 = "SELECT LAST_INSERT_ID()";
                                    PreparedStatement st377 = con.prepareStatement(q377);

                                    ResultSet rs377 = st377.executeQuery();
                                    int o_id = -999;

                                    if(rs377.next()){
                                        o_id = rs377.getInt(1);
                                    }
                                    st377.close();

                                    String q378 = "SELECT cus_cart_id FROM Customer WHERE cus_id=?";
                                    PreparedStatement st378 = con.prepareStatement(q378);

                                    st378.setInt(1,o_cus_id);
                                    ResultSet rs378 = st378.executeQuery();
                                    int o_cart_id = -999;

                                    if(rs378.next()){
                                        o_cart_id = rs378.getInt(1);
                                    }

                                    String q379 = "INSERT INTO Places (pl_cus_id, pl_order_id, pl_cart_id)" +
                                                  " VALUES (?,?,?)";

                                    PreparedStatement st379 = con.prepareStatement(q379);
                                    st379.setInt(1,o_cus_id);
                                    st379.setInt(2,o_id);
                                    st379.setInt(3,o_cart_id);

                                    int rs379 = st379.executeUpdate();
                                    if( rs379 == 1 ){
                                        System.out.println("Order Placed Successfully");
                                    }else{
                                        st379.close();
                                        throw new SQLException("Encountered unknown error while placing Order");
                                    }
                                    st379.close();


                                    String q3710 = "INSERT INTO Payment (pt_amount, pt_date_time)" +
                                                   " VALUES (?,CURRENT_DATE())";

                                    PreparedStatement st3710 = con.prepareStatement(q3710);

                                    st3710.setInt(1,o_price);

                                    int rs3710 = st3710.executeUpdate();

                                    if (rs3710 == 1){
                                        System.out.println("Payment done Successfully!");
                                    } else {
                                        st3710.close();
                                        throw new SQLException("Encountered unknown error during Payment.");
                                    }
                                    st3710.close();

                                    String q3711 = "SELECT LAST_INSERT_ID()";
                                    PreparedStatement st3711 = con.prepareStatement(q3711);

                                    ResultSet rs3711 = st3711.executeQuery();
                                    int pt_id = -999;

                                    if(rs3711.next()){
                                        pt_id = rs3711.getInt(1);
                                    }
                                    st3711.close();

                                    String q3712 = "INSERT INTO Pays (pys_pt_id, pys_cus_id, pys_order_id)" +
                                                   " VALUES (?,?,?)";

                                    PreparedStatement st3712 = con.prepareStatement(q3712);
                                    st3712.setInt(1,pt_id);
                                    st3712.setInt(2,o_cus_id);
                                    st3712.setInt(3,o_id);

                                    int rs3712 = st3712.executeUpdate();
                                    if( rs3712 == 1 ){
                                        System.out.println("Payment processed Successfully");
                                    }else{
                                        st3712.close();
                                        throw new SQLException("Encountered unknown error while processing Payment");
                                    }
                                    st3712.close();

                                    String q3713 = "DELETE FROM Cart_Product_List WHERE cp_cart_id = ?";
                                    PreparedStatement st3713 = con.prepareStatement(q3713);

                                    st3713.setInt(1,o_cart_id);
                                    
                                    con.commit();

                                } catch (SQLException e){
                                    System.out.println(e.getMessage());
                                    con.rollback();
                                } finally {
                                    con.setAutoCommit(true);
                                }

                            } else if (opt == 8) {
                                break;
                            } else {
                                System.out.println("Invalid Choice!");
                            }
                        }
                    }

                } else if (op == 4) {
                    System.out.println("Enter your username");    //'fbenziep', 'l4yBKhdUbdg' sample usernames   'abucher1g', 'CNZyMTnN'
                    String usriddp = sc.nextLine();

                    //check with embedded sql
                    System.out.println("Enter your password");
                    String pwddp = sc.nextLine();

                    String q4 = "Select dp_id FROM Delivery_Partner WHERE dp_username=? AND dp_password=?";
                    PreparedStatement st4 = con.prepareStatement(q4);

                    st4.setString(1, usriddp);
                    st4.setString(2, pwddp);
                    int ans = -1;

                    ResultSet rs4 = st4.executeQuery();

                    while (rs4.next()) {
                        if (rs4.getInt(1) != NULL) {
                            ans = 1;

                        }
                    }

                    st4.close();

                    if (ans == 1) {
                        while (true) {
                            System.out.println("Choose one of the options");
                            System.out.println("\n1) Check Compensation");  // embedded sql
                            System.out.println("2) Check orders to be delivered ");     //TBD
                            System.out.println("3) Update orders list");                //TBD
                            System.out.println("4) Back");

                            System.out.println("\nEnter your option");
                            int opt = sc.nextInt();
                            sc.nextLine();

                            if (opt == 1) {
                                String q41 = "Select dp_salary FROM Delivery_Partner WHERE dp_username=?";
                                PreparedStatement st41 = con.prepareStatement(q41);

                                st41.setString(1, usriddp);

                                ResultSet rs41 = st41.executeQuery();

                                while (rs41.next()) {
                                    System.out.println(String.format("Salary = %d",
                                            rs41.getInt(1)
                                    ));
                                }

                                st4.close();

                            } else if (opt == 4) {
                                break;
                            }
                        }
                    }


                } else if (op == 5) {

                    try {
                        String cname;
                        String cpass;
                        String c_firstname;
                        String c_lastname;
                        String c_email;
                        String c_street;
                        String c_city;
                        String c_pincode;
                        double c_mobile;
                        int c_wallet;

                        System.out.println("Enter Username       : ");
                        cname = sc.nextLine();

                        System.out.println("\nEnter Password       : ");
                        cpass = sc.nextLine();

                        System.out.println("\nEnter First Name     : ");
                        c_firstname = sc.nextLine();

                        System.out.println("\nEnter Last Name      : ");
                        c_lastname = sc.nextLine();

                        System.out.println("\nEnter Mobile No.     : ");
                        c_mobile = sc.nextDouble();

                        System.out.println("\nEnter E mail         : ");
                        c_email = sc.nextLine();

                        sc.nextLine();

                        System.out.println("\nEnter Street         : ");
                        c_street = sc.nextLine();

                        System.out.println("\nEnter City           : ");
                        c_city = sc.nextLine();

                        System.out.println("\nEnter Pin Code       : ");
                        c_pincode = sc.nextLine();

                        System.out.println("\nEnter Wallet Balance : ");
                        c_wallet = sc.nextInt();

                        sc.nextLine();


                        con.setAutoCommit(false);
                        String q50 = "INSERT INTO Cart (total_price) VALUES (0)";

                        PreparedStatement st50 = con.prepareStatement(q50);
                        int rs50 = st50.executeUpdate();

                        if (rs50 == 1) {
                            System.out.println("\nNew Cart Added Successfully!");
                        } else {
                            throw new SQLException("Encountered unknown error while adding Cart");
                        }
                        st50.close();

                        String q51 = "SELECT LAST_INSERT_ID()";

                        PreparedStatement st51 = con.prepareStatement(q51);

                        ResultSet rs51 = st51.executeQuery();
                        int c_cart_id = -999;

                        if (rs51.next()) {
                            c_cart_id = rs51.getInt(1);
                        }

                        st51.close();

                        String q52 = "INSERT INTO Subscription (sub_name) VALUES ('silver')";

                        PreparedStatement st52 = con.prepareStatement(q52);
                        int rs52 = st52.executeUpdate();

                        if (rs52 == 1) {
                            System.out.println("\nNew Subscription Created Successfully!");
                        } else {
                            throw new SQLException("Encountered unknown error while creating Subscription");
                        }
                        st52.close();

                        String q53 = "SELECT LAST_INSERT_ID()";

                        PreparedStatement st53 = con.prepareStatement(q53);

                        ResultSet rs53 = st53.executeQuery();

                        int c_sub_id = -999;

                        if (rs53.next()) {
                            c_sub_id = rs53.getInt(1);
                        }

                        st53.close();

                        String q5 = "INSERT INTO Customer (cus_username,cus_password," +
                                "cus_firstname,cus_lastname,cus_mobile,cus_email,cus_street," +
                                "cus_city,cus_pin_code,cus_cart_id,cus_sub_id,cus_wallet) " +
                                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

                        PreparedStatement st5 = con.prepareStatement(q5);

                        st5.setString(1, cname);
                        st5.setString(2, cpass);
                        st5.setString(3, c_firstname);
                        st5.setString(4, c_lastname);
                        st5.setDouble(5, c_mobile);
                        st5.setString(6, c_email);
                        st5.setString(7, c_street);
                        st5.setString(8, c_city);
                        st5.setString(9, c_pincode);
                        st5.setInt(10, c_cart_id);
                        st5.setInt(11, c_sub_id);
                        st5.setInt(12, c_wallet);

                        int rs5;

                        rs5 = st5.executeUpdate();
                        if (rs5 == 1) {
                            System.out.println("\nCustomer Added Successfully");
                            con.commit();
                        } else {
                            throw new SQLException("Encountered unknown error while adding Customer");
                        }
                        st5.close();
                    } catch (SQLIntegrityConstraintViolationException e) {
                        System.out.println("\nUsername is already taken.");
                        con.rollback();
                    } catch (SQLException e){
                        System.out.println(e.getMessage());
                        con.rollback();
                    } finally {
                        con.setAutoCommit(true);
                    }

                } else if (op == 6) { // check trending categories

                    String q6 = "SELECT p.p_name, COUNT(DISTINCT cpl.cp_cart_id) as cart_count " +
                            "FROM Product p " +
                            "JOIN Cart_Product_List cpl ON p.p_id = cpl.cp_pid " +
                            "GROUP BY p.p_id " +
                            "ORDER BY cart_count DESC " +
                            "LIMIT 5";

                    PreparedStatement st6 = con.prepareStatement(q6);

                    ResultSet rs6 = st6.executeQuery(q6);

                    while (rs6.next()) {
                        System.out.println(String.format("Product name = %s ",
                                rs6.getString(1)
                        ));
                    }

                    st6.close();

                } else if (op == 7) { // check trending products

                    String q7 = "SELECT Category.cat_name, " +
                            "SUM(cp_quantity) AS total_in_cart " +
                            "FROM Category " +
                            "JOIN Product ON Category.cat_id = Product.p_cat_id " +
                            "JOIN Cart_Product_List ON Product.p_id = Cart_Product_List.cp_pid " +
                            "GROUP BY Category.cat_name " +
                            "ORDER BY total_in_cart DESC " +
                            "LIMIT 5";

                    PreparedStatement st7 = con.prepareStatement(q7);

                    ResultSet rs7 = st7.executeQuery(q7);

                    while (rs7.next()) {
                        System.out.println(String.format("Category name = %s ",
                                rs7.getString(1)
                        ));
                    }

                    st7.close();

                }
                else if(op==8){
                    break;
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        con.close();
    }
}