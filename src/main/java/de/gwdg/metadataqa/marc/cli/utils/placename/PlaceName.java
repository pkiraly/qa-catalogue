package de.gwdg.metadataqa.marc.cli.utils.placename;

public class PlaceName {
  private String city;
  private int geoid;
  private String geoname;
  private String country;
  private float latitude;
  private float longitude;

  public PlaceName(String city, int geoid, String geoname, String country, float latitude, float longitude) {
    this.city = city;
    this.country = country;
    this.geoname = geoname;
    this.geoid = geoid;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public PlaceName(String[] row) {
    this(row[0], Integer.parseInt(row[1]), row[2], row[3], Float.parseFloat(row[4]), Float.parseFloat(row[5]));
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getGeoname() {
    return geoname;
  }

  public int getGeoid() {
    return geoid;
  }

  public float getLatitude() {
    return latitude;
  }

  public float getLongitude() {
    return longitude;
  }
}
