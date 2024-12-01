package de.gwdg.metadataqa.marc.datastore;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class MarcSQLiteClient {

  private static final Logger logger = Logger.getLogger(MarcSQLiteClient.class.getCanonicalName());

  Connection conn = null;

  public void connect(String path) {
    try {
      String url = "jdbc:sqlite:" + path;
      SQLiteConfig config = new SQLiteConfig();
      config.enforceForeignKeys(true);
      conn = DriverManager.getConnection(url, config.toProperties());
    } catch (SQLException e) {
      logger.warning(e.getMessage());
    }
  }

  public void close() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
