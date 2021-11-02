package de.gwdg.metadataqa.marc.datastore;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class MarcSQLiteClient {

  Connection conn = null;

  public void connect(String path) {
    try {
      String url = "jdbc:sqlite:" + path;;
      // create a connection to the database
      SQLiteConfig config = new SQLiteConfig();
      config.enforceForeignKeys(true);
      conn = DriverManager.getConnection(url, config.toProperties());
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    } finally {
      /*
      try {
        if (conn != null) {
          conn.close();
        }
      } catch (SQLException ex) {
        System.out.println(ex.getMessage());
      }
       */
    }
  }

  public void createSchema() {
    try {
      final Statement stmt = conn.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS course (course_id INTEGER, title TEXT, seats_available INTEGER, PRIMARY KEY(course_id))");
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS student (student_id INTEGER, name TEXT, PRIMARY KEY(student_id))");
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS take (course_id INTEGER, student_id INTEGER, enroll_date TEXT, PRIMARY KEY(student_id, course_id))");
    } catch (SQLException e) {
      System.err.println("[ERROR] createSchema : " + e.getMessage());
    }
  }

  public void initSchema() {
    final String[] courses = new String[] {
      "1,CMPUT291,200", "2,CMPUT274,70", "3,CMPUT301,80"
    };
    final String[] students = new String[] {
      "11,John", "12,Mary", "13,Steve", "14,Bob", "15,Seth",
      "16,Samantha", "17,Emily", "18,Paul", "19,Emma", "20,Ross"
    };
    final String[] takes = new String[] {
      "1,11,2017-08-01",
      "2,13,2017-09-01", "2,14,2017-08-15",
      "3,11,2017-09-01", "3,12,2017-08-15"
    };
    try {

      try (PreparedStatement crsStmt = conn.prepareStatement("INSERT INTO course VALUES (?, ?, ?)")) {
        for (String c : courses) {
          final String[] cols = c.split(",");
          crsStmt.setLong(1, Long.valueOf(cols[0]));
          crsStmt.setString(2, cols[1]);
          crsStmt.setInt(3, Integer.valueOf(cols[2]));
          crsStmt.executeUpdate();
        }
      }

      try (PreparedStatement stdStmt = conn.prepareStatement("INSERT INTO student VALUES (?, ?)")) {
        for (String s : students) {
          final String[] cols = s.split(",");
          stdStmt.setLong(1, Long.valueOf(cols[0]));
          stdStmt.setString(2, cols[1]);
          stdStmt.executeUpdate();
        }
      }

      try (PreparedStatement tkStmt = conn.prepareStatement("INSERT INTO take VALUES (?, ?, ?)")) {
        for (String t : takes) {
          final String[] cols = t.split(",");
          tkStmt.setLong(1, Long.valueOf(cols[0]));
          tkStmt.setLong(2, Long.valueOf(cols[1]));
          tkStmt.setString(3, cols[2]);
          tkStmt.executeUpdate();
        }
      }

    } catch (SQLException e) {
      System.err.println("[ERROR] initSchema : " + e.getMessage());
    }
  }
}
