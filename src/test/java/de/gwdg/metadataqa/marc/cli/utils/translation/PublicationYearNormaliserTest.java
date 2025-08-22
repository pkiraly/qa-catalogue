package de.gwdg.metadataqa.marc.cli.utils.translation;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test cases are taken from Leo Lahti et al. Bibliographica R package
 * see  https://github.com/COMHIS/bibliographica
 */
public class PublicationYearNormaliserTest {

  @Test
  public void uncertainYears() {
    expect_equal("[179-?]", "1790");
    // expect_equal("17--?]", "1700");
    // expect_equal("[17--?]", "1700");
    // expect_equal("[1690 or later]", "1690");
    expect_equal("18--]", "1800");
    expect_equal("183-]", "1830");
    expect_equal("[18??]", "1800");
    expect_equal("[19??]", "1900");
  }

  @Test
  public void copyright() {
    expect_equal("copyright  2019.", "2019");
    expect_equal("[©2019].", "2019");
    expect_equal("℗ 2019.", "2019");
  }

  @Test
  public void month() {
    expect_equal("druk janvier 2016.", "2016");
    expect_equal("enero de 2020.", "2020");
    expect_equal("februari 2020.", "2020");
    expect_equal("druk février 2020.", "2020");
    expect_equal("marzo de 2019.", "2019");
    expect_equal("druk mars 2016.", "2016");
    expect_equal("avril 2016.", "2016");
    expect_equal("abril de 2017.", "2017");
    expect_equal("mai 2019.", "2019");
    expect_equal("mayo de 2011.", "2011");
    expect_equal("juny 2019.", "2019");
    expect_equal("junio de 2019.", "2019");
    expect_equal("julio de 2019.", "2019");
    expect_equal("agosto de 2017.", "2017");
    expect_equal("août 2019.", "2019");
    expect_equal("septembre 2017.", "2017");
    expect_equal("druk settembre 2019.", "2019");
    expect_equal("druk novembre 2019.", "2019");
    expect_equal("diciembre de 2017.", "2017");
    expect_equal("druk décembre 2018.", "2018");
  }

  @Test
  public void roman() {
    expect_equal("MDCCLXXX. [1780]", "1780");
    expect_equal("M.DC.XXI. [1621]", "1621");
    expect_equal("MCMXXXVII [1937]", "1937");
    expect_equal("anno M.DC.XX. [1620]", "1620");
    expect_equal("an. Dom. MDCCII. [1702]", "1702");
    expect_equal("annô MDCCXLIV. [1744]", "1744");

    expect_equal("MDCCXCV.", "1795");
    expect_equal("M.DCCXCV.", "1795");
    expect_equal("M.D.CCXCV.", "1795");
    expect_equal("M D CCXCV.", "1795");
    expect_equal("M,D,CCXCV", "1795");
  }

  @Test
  public void name() {
    expect_equal("[ok. 2019].", "2019");
    expect_equal("Shō 15 [1940].", "1940");
    expect_equal("min kuo 76 [1987]", "1987"); // Taiwan
    expect_equal("Min kuo 4 [1915]", "1915"); // Taiwan
    expect_equal("Min-kuo 21 [1932]", "1932"); // Taiwan
    expect_equal("Meiji 40 [1907]", "1907"); // Taiwan
    // expect_equal("1798. (price one dollar)", "1798");
    expect_equal("1776.", "1776");
    // expect_equal("[1768.]", "1768");
    // expect_equal("[-1768]", "1768");
    expect_equal("1524]", "1524");
    expect_equal("1524?]", "1524");
    expect_equal("[1524?]", "1524");
    expect_equal("[1993] ", "1993");
    expect_equal("an. 1719", "1719");
    expect_equal("2015[!2017]", "2017");
    expect_equal("ccop. 2006", "2006");
    expect_equal("cp. 2006", "2006");
    expect_equal("anno 1799", "1799");

    expect_equal("[ca. 1618]", "1618");
    expect_equal("MDCCLXVIII. [1768]", "1768");
    expect_equal("MDCCLXVIII. 1768", "1768");

    expect_equal("August 5, 1799.", "1799");
    expect_equal("1726[1727]", "1727");

    expect_equal("May 27, 1643.", "1643");
    expect_equal("Iune 8, 1642.", "1642");

    expect_equal("168[5].", "1685");
    expect_equal("166[1].", "1661");
    expect_equal("164[2]", "1642");
    expect_equal("16[99]", "1699");
    expect_equal("16[65?]", "1665");
    expect_equal("[1930!]", "1930");
    expect_equal("1348", "1348");
    expect_equal("[18]91", "1891");

  }

  @Test
  public void hungarian() {
    expect_equal("[1936 után]", "1936");
    expect_equal("[1914 előtt]", "1914");
    expect_equal("1782 eszt.", "1782");
    expect_equal("1785. esztend.", "1785");
  }

  // @Test
  public void normalizedYears() {
    expect_equal("1741-1810.", "1741");
    // expect_equal("1741-1810.")$till, 1810");
    expect_equal("1617 [] 1618", "1617");
    // expect_equal("1617 [] 1618")$till, 1618");

    expect_equal("[1524-1528]", "1524");
    // expect_equal("[1524-1528]")$till, 1528");

    // expect_equal("-1768")$till, 1768");
    // expect_equal("-1776.")$till, 1776");

    expect_equal("--1524.---", "1524");

    // expect_equal("active 1781.")$till, 1781");

    expect_equal("1741?-1821.", "1741");
    // expect_equal("1741?-1821.")$till, 1821");

    expect_equal("1650 or 1651-1705.", "1651");
    // expect_equal("1650 or 1651-1705.")$till, 1705");

    expect_equal("[between 1790 and 1800?]", "1790");
    // expect_equal("[between 1790 and 1800?]")$till, 1800");
    expect_equal("1700/1.", "1700");

    expect_equal("MDCXLI. [1641, i.e. 1642]", "1642");
    // expect_equal("MDCXLI. [1641, i.e. 1642]")$till, 1642");

    expect_equal("1670/71.]", "1670");
    expect_equal("re-printed in the year, 1689]", "1689");
    expect_equal("M.DC.XXVII [1627, i.e. 1628]", "1628");
    expect_equal("Re-printed in the year, 1689]", "1689");
    expect_equal("1726[7]", "1727");

    expect_equal("Anno M.DC.XVIII [1618, i.e. 1619]", "1619");
    expect_equal("27 Octob. 1643.", "1643");
    expect_equal("1797-98.", "1797");
    // expect_equal("1797-98.")$till, 1798");
    expect_equal("re-printed; 1688.", "1688");
    expect_equal("Re-printed in the year. [sic, i.e. 1680?]", "1680");
    expect_equal("re-printed by George Mosman,;1700.", "1700");
    expect_equal("Printed on the day of Jacobs trouble, and to make way (in hope) for his deliverance out of it. May 25. 1643.", "1643");
    expect_equal("Printed July 17, in the yeer 1648.", "1648");
    expect_equal("Printed in the Yeare,;1648.", "1648");
    expect_equal("Anno. M.D.XXXVIII [1538, i.e. ca. 1562]", "1562");
    expect_equal("&lt;1776?>", "1776");
    expect_equal("&lt;1766- >", "1766");
    expect_equal("--March--1797--", "1797");
    expect_equal("Prändätty tänä wuonna [1764]", "1764");
    expect_equal("prändätty wuonna 1690,;1690.", "1690");
    expect_equal("1893[-95]", "1893");
    // expect_equal("1893[-95]")$till, 1895");
    expect_equal("[1917];[1917]", "1917");
    expect_equal("[1915?]-[1916?]", "1915");
    // expect_equal("[1915?]-[1916?]")$till, 1916");
    expect_equal("[1913]-19??", "1913");
    expect_equal("[1908}", "1908");
    expect_equal("[1908] (painettu osakeyhtiö F. Tilgmannilla)", "1908");
    expect_equal("[1899]-[1899]", "1899");
    // expect_equal("[1899]-[1899]")$till, 1899");
    expect_equal("[1897-]-1898.", "1897");
    // expect_equal("[1897-]-1898.")$till, 1898");
    expect_equal("[1801],;1801.", "1801");
    expect_equal("[1784];4:o.", "1784");
    expect_equal("[1768],;2:o.", "1768");
    expect_equal("[1686],;1686.", "1686");
    expect_equal("[1680],;1680.", "1680");
    expect_equal("[1675] Turku,;[1:o?]", "1675");
    expect_equal("[1672] Strängnäs.", "1672");
    expect_equal("1906-[27]", "1906");
    // expect_equal("1906-[27]")$till, 1927");
    expect_equal("anno Dni 1642. 16 aprilis.", "1642");
    expect_equal("anno 1631 [po. 1632]", "1632");
    expect_equal("anno 1622. 4. Octob.", "1622");
    expect_equal("anno 1622. 25. Ian.", "1622");
    expect_equal("anno 1599 die 15. Februarii.", "1599");
    expect_equal("1916-[19--?].", "1916");
    expect_equal("1915-[19??].", "1915");
    expect_equal("1906-[19??]", "1906");
    expect_equal("1905 [po. 1906]", "1906");
    expect_equal("1901 [i. e. 1900]", "1900");
    expect_equal("1896-[19--].", "1896");
    expect_equal("1757 [po. 1751]", "1751");
    expect_equal("1738 [po. 1752].", "1752");
    expect_equal("[anno Dni 1544. Nouembris 12]", "1544");
    expect_equal("[anno 1575 septembris 12]", "1575");
    expect_equal("[a:1680]", "1680");
    expect_equal("[1955?]-[19--?]", "1955");
    expect_equal("[1950?]-19??", "1950");
    // expect_equal("[19--?]-1913?")$till, 1913");
    expect_equal("[1896?]-[19--?].", "1896");
    expect_equal("[1899-]1900.", "1899");
    // expect_equal("[1899-]1900.")$till, 1900");
    expect_equal("1641. [1642]", "1642");
    expect_equal("1677. [i.e. 1689.].", "1689");
    expect_equal("Printed in the Year MDCLXXX.", "1680");
    expect_equal("Printed in the first yeare of Jubilee,1643.", "1643");
    expect_equal("MDCCX.-MDCCXI. [1710-1711]", "1710");
    // expect_equal("MDCCX.-MDCCXI. [1710-1711]")$till, 1711");
    expect_equal("anno ut supra [=1629]", "1629");
    expect_equal("Octob. 23,1647.", "1647");
    expect_equal("M.DC.XXVIII.", "1628");
    expect_equal("M,DCC,LXXV.", "1775");
    expect_equal("Febr. 8. 1646[7]", "1647");
    expect_equal("Anno Dom. MDCLXXXV.", "1685");
    expect_equal("Anno 1643. Sept. 16.", "1643");

    expect_equal("179[2?]", "1792");
    expect_equal("1757. Price 6 d.", "1757");
    expect_equal("1700. [i.e. 1700-1701]", "1700");
    // expect_equal("1700. [i.e. 1700-1701]")$till, 1701");
    expect_equal("1698 [i.e. 1699}", "1699");
    expect_equal("169[7]]", "1697");
    expect_equal("1689 [i.e. 1689-1690]", "1689");
    // expect_equal("1689 [i.e. 1689-1690]")$till, 1690");
    expect_equal("1688 [i.e., 1689, New Style].", "1689");
    expect_equal("1679 [i.e. 1679-1680]", "1679");
    // expect_equal("1679 [i.e. 1679-1680]")$till, 1680");
    expect_equal("166[5?]]", "1665");
    expect_equal("1642. Novemb, 5.", "1642");
    expect_equal("1642. June, 23.", "1642");
    expect_equal("1642 [Dec. 20]", "1642");
    expect_equal("1577. March, 7.", "1577");
    expect_equal("(1602.)", "1602");
    expect_equal("An. Dom. M.DC.XLII. [i.e. 1643-1645]", "1643");
    // expect_equal("An. Dom. M.DC.XLII. [i.e. 1643-1645]")$till, 1645");
    expect_equal("30 March, 1646.", "1646");
    expect_equal("26. March, 1649.", "1649");
    expect_equal("21 of August, 1644]", "1644");
    expect_equal("18 March, 1645 [i.e. 1646]", "1646");
    expect_equal("[25 February, 1650]", "1650");
    expect_equal("1695/6. [1696]", "1695");
    expect_equal("the 27. of Iuly, 1585]", "1585");
    expect_equal("January 26. 1646. [i.e. 1647]", "1647");
    expect_equal("<1794>-", "1794");
    expect_equal("<1755?>-", "1755");
    expect_equal("Anno Domini 1552 [i.e. R. Tottell, 1565?]]", "1565");
    expect_equal("[1639 (17 or 18 February)]]", "1639");
    expect_equal("Anno.1564. Mense Iulii [1564]", "1564");
    expect_equal("Septemb. 6. 1634 [i.e. 1643-1647]", "1643");
    // expect_equal("Septemb. 6. 1634 [i.e. 1643-1647]")$till, 1647");
    expect_equal("1738 [po. 1746--1752].", "1746");
    // expect_equal("1738 [po. 1746--1752].")$till, 1752");
    expect_equal("1738 [po 1746--1752].", "1746");
    // expect_equal("1738 [po 1746--1752].")$till, 1752");
    expect_equal("1738 [po. 1746]", "1746");
    expect_equal("1738 [po 1746]", "1746");
    expect_equal("prÃ¤ndÃ¤tty wuonna 1739 [po. 1771--1784]", "1771");
    // expect_equal("prÃ¤ndÃ¤tty wuonna 1739 [po. 1771--1784]")$till, 1784");
    expect_equal("1905 [oik. 15]", "1915");
    expect_equal("[1904-] 1905.", "1904");
    // expect_equal("[1904-] 1905.")$till, 1905");
    expect_equal("1722?,;2:o.", "1722");
    expect_equal("1731,;4:o.", "1731");
    expect_equal("1893-1913[?]", "1893");
    // expect_equal("1893-1913[?]")$till, 1913");
    expect_equal("1913, 1915, 1916.", "1913");
    // expect_equal("1913, 1915, 1916.")$till, 1916");
    expect_equal("a[...] 1666-1670.", "1666");
    // expect_equal("a[...] 1666-1670.")$till, 1670");
    expect_equal("wuona jelken Christuxen syndymän 1616,;1616", "1616");
    expect_equal("MDCCI. [1701] [1702]", "1701");
    // expect_equal("MDCCI. [1701] [1702]")$till, 1702");
    expect_equal("1889, 1-1890, 12. 1890 1890 4", "1889");
    // expect_equal("1889, 1-1890, 12. 1890 1890 4")$till, 1890");
    expect_equal("Läseåret 1885/1886-Läseåret 1889/1890. 1885 1885 2", "1885");
    expect_equal("1890/1891-1913/1914. 1890 1890 2", "1890");
    // expect_equal("1890/1891-1913/1914. 1890 1890 2")$till, 1914");
    expect_equal("1908, 1-1909, 2. 1909 1909 2", "1908");
    // expect_equal("1908, 1-1909, 2. 1909 1909 2")$till, 1909");
    expect_equal("1891, 0-1905, 51. 1905 1905 2", "1891");
    // expect_equal("1891, 0-1905, 51. 1905 1905 2")$till, 1905");
    expect_equal("2 (1911)-1919. 1919 1919 1", "1911");
    // expect_equal("2 (1911)-1919. 1919 1919 1")$till, 1919");
    expect_equal("1900/1901-1930/1931. 1900 1900 1", "1900");
    // expect_equal("1900/1901-1930/1931. 1900 1900 1")$till, 1931");
    expect_equal("1903, häft 1-1904, häft 49. 1904 1904 1", "1903");
    // expect_equal("1903, häft 1-1904, häft 49. 1904 1904 1")$till, 1904");
    expect_equal("1901-2, (1902). 1901 1901 1", "1901");
    // expect_equal("1901-2, (1902). 1901 1901 1")$till, 1902");
    expect_equal("1852/1887-1899/1914. 1852 1852 1", "1852");
    // expect_equal("1852/1887-1899/1914. 1852 1852 1")$till, 1914");
    // Could also be 1895-1900
    expect_equal("1893, 1895-1899/1900. 1893 1893 1", "1893");
    // expect_equal("1893, 1895-1899/1900. 1893 1893 1")$till, 1900");
    expect_equal("1903-6(1908) 1903 1903 1", "1903");
    // expect_equal("1903-6(1908) 1903 1903 1")$till, 1908");
    expect_equal("9(1898)-1910. 1910 1910 1", "1898");
    // expect_equal("9(1898)-1910. 1910 1910 1")$till, 1910");
    expect_equal("1903, 1-1911, 11. 1911 1911 1", "1903");
    // expect_equal("1903, 1-1911, 11. 1911 1911 1")$till, 1911");
    expect_equal("1865, 1-1869, 4. 1869 1869 1", "1865");
    // expect_equal("1865, 1-1869, 4. 1869 1869 1")$till, 1869");
    expect_equal("1907, provn:r-1912, 1. 1912 1912 1", "1907");
    // expect_equal("1907, provn:r-1912, 1. 1912 1912 1")$till, 1912");
    expect_equal("1894, profn:r-1895, 6. 1895 1895 1", "1894");
    // expect_equal("1894, profn:r-1895, 6. 1895 1895 1")$till, 1895");
    expect_equal("1911, näyten:o 1-1913, 51. 1913 1913 1", "1911");
    // expect_equal("1911, näyten:o 1-1913, 51. 1913 1913 1")$till, 1913");
    expect_equal("Läseåret 1885/1886-Läseåret 1894/1895.", "1885");
    // expect_equal("Läseåret 1885/1886-Läseåret 1894/1895.")$till, 1895");
    expect_equal("1(1861/1865)-8(1896/1900)", "1861");
    // expect_equal("1(1861/1865)-8(1896/1900)")$till, 1900");

    expect_equal("1(1861/1865)-8(1900)", "1861");
    // expect_equal("1(1861/1865)-8(1900)")$till, 1900");

    expect_equal("13(1861/1865)-8(1900)", "1861");
    // expect_equal("13(1861/1865)-18(1900)")$till, 1900");

    expect_equal("1(1865)-8(1900)", "1865");
    // expect_equal("1(1865)-8(1900)")$till, 1900");

    expect_equal("1(1865)-8 (1900)", "1865");
    // expect_equal("1 (1865)-8(1900)")$till, 1900");

    expect_equal("1884, [0] ; 1885, 1-1892, 12", "1884");
    // expect_equal("1884, [0] ; 1885, 1-1892, 12")$till, 1892");

    expect_equal("1896, [1] ; 1897, n:o 0 ; 1898, n:o 0-1898, n:o 24", "1896");
    // expect_equal("1896, [1] ; 1897, n:o 0 ; 1898, n:o 0-1898, n:o 24.")$till, 1898");

    expect_equal("1902, [1].", "1902");
    expect_equal("1903,1.", "1903");

    expect_equal("1:nen vsk. (1899/1900)", "1899");
    // expect_equal("1:nen vsk. (1899/1900)")$till, 1900");

    expect_equal("1904-1905;1904", "1904");
    // expect_equal("1904-1905;1904")$till, 1905");

    expect_equal("1921-1922;1921-1922;1922", "1921");
    // expect_equal("1921-1922;1921-1922;1922")$till, 1922");

    expect_equal("[19]75-[19]76", "1975");
    // expect_equal("[19]75-[19]76")$till, 1976");

    expect_equal("[1901.']", "1901");

    expect_equal("1781:1-13", "1781");

    expect_equal("18[29-]1830", "1829");
    // expect_equal("18[29-]1830")$till, 1830");

    expect_equal("18[30-]1833", "1830");
    // expect_equal("18[30-]1833")$till, 1833");

    expect_equal("18[35-18]42", "1835");
    // expect_equal("18[35-18]42")$till, 1842");

    expect_equal("1817: 13/12-27/12", "1817");

    expect_equal("1853,54(1,2)", "1853");

    expect_equal("3:dje årgången, [1-26] ([1904/1905]) ; 1:sta årgången, n:o 1-54 (1905/1906)", "1904");
    // expect_equal("3:dje årgången, [1-26] ([1904/1905]) ; 1:sta årgången, n:o 1-54 (1905/1906)")$till, 1906");

    expect_equal("wuonna 1754 s. 13 päiwänä touco cuusa.", "1754");
    expect_equal("1897, 31.3.-1897, 31.3.", "1897");

    expect_equal("1896, 0 (30.9.)-1897, 31.1.", "1896");
    // expect_equal("1896, 0 (30.9.)-1897, 31.1.")$till, 1897");

    expect_equal("Pendant l'hiver 1895/1896-Pendant l'hiver 1897/1898.", "1895");
    // expect_equal("Pendant l'hiver 1895/1896-Pendant l'hiver 1897/1898.")$till, 1898");

    expect_equal("1. årg. (1880)-7. årg. (1887/1888) = 1. vsk. (1880)-7. vsk. (1887/1888)", "1880");
    // expect_equal("1. årg. (1880)-7. årg. (1887/1888) = 1. vsk. (1880)-7. vsk. (1887/1888)")$till, 1888");

    expect_equal("1. årg. (1889/1890)-27. årg. (1915/1916)", "1889");
    // expect_equal("1. årg. (1889/1890)-27. årg. (1915/1916)")$till, 1916");

    expect_equal("God' 1 (1914/1915), n:o 1-god' 3 (1916/1917), n:o 3. ", "1914");
    // expect_equal("God' 1 (1914/1915), n:o 1-god' 3 (1916/1917), n:o 3. ")$till, 1917");

    expect_equal("Printed Anno M. DC. XL.", "1640");
    expect_equal("Printed by S. Green and sold by Samuel Phillips,1̂689.", "1689");
    expect_equal("Printed for Thomas Guy,1̂689. ", "1689");
    expect_equal("sold [by Michael Perry],[̂1698]", "1698");
    expect_equal("&lt;1786&gt;", "1786");
    expect_equal("&lt;1786&gt;-", "1786");
    expect_equal("in the yeare of our Lorde God. 1.5.4.6]", "1546");
    expect_equal("Anno Domini. 1.5.5.4. 24. Iulij]", "1554");
    expect_equal("Anno Domini 169[0].", "1690");
  }

  private void expect_equal(String actual, String expected) {
    assertEquals(expected, PublicationYearNormaliser.normalizeYear(actual));
  }
}