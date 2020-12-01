package com.chatwindow.service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.*;

//Gets image from the Encryption system's SQL database
public class ImageService {
    //JDBC driver name and database url
    static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";

    //credentials
    static final String USER = "tsitzlar";
    static final String PASS = "ajokreer";

    public void getImage() throws Exception {
        Connection conn = null;
        Statement stmt = null;

        //Register the JDBC driver
        Class.forName(JDBC_DRIVER);//"com.mysql.jdbc.Driver"

        //Open a connection to the database
        conn = DriverManager.getConnection(DB_URL,USER,PASS);

        //Do a query
        stmt= conn.createStatement();

        //star means 'all' columns from movie table
        //String sql = "SELECT * FROM v_MOVIE";

        ResultSet rs = rs = stmt.executeQuery("SELECT * FROM IMAGES ");//stmt.executeQuery(sql);

        //Extract data
        System.out.println("15");
        rs.next();rs.next();rs.next();rs.next();
        System.out.println("15.5");
        Blob imgasblob = rs.getBlob("image");
        System.out.println("16");
        byte[] imgasbytes2 = imgasblob.getBytes(1, (int)imgasblob.length());
        System.out.println("17");
        InputStream in = new ByteArrayInputStream(imgasbytes2);
        System.out.println("18");
        BufferedImage imgFromDb = ImageIO.read(in);
        System.out.println("19");

        ImageIO.write(imgFromDb,"png",new File("D:\\Thomas\\Memes\\help\\34a(2).png"));
        System.out.println("20");
        //Clean-up environment
        rs.close();
        stmt.close();
        conn.close();
    }
}
