package de.gwdg.metadataqa.marc.definition.general.codelist;

import de.gwdg.metadataqa.marc.Utils;

/**
 * Copyright and Legal Deposit Number Source Codes
 * https://www.loc.gov/standards/sourcelist/copyright-legal-deposit.html
 * used in Bibliographic records 017 $2 (Copyright or Legal Deposit Number / Source)
 */
public class CopyrightAndLegalDepositNumberSourceCodes extends CodeList {

  private void initialize() {
    name = "Copyright and Legal Deposit Number Source Codes";
    url = "https://www.loc.gov/standards/sourcelist/copyright-legal-deposit.html";
    codes = Utils.generateCodes(
      "rocgpt", "R.O.C. Government Publications Catalogue (Taipei: Research, Development and Evaluation Commission, Executive Yuan)"
    );
    indexCodes();
  }

  private static CopyrightAndLegalDepositNumberSourceCodes uniqueInstance;

  private CopyrightAndLegalDepositNumberSourceCodes() {
    initialize();
  }

  public static CopyrightAndLegalDepositNumberSourceCodes getInstance() {
    if (uniqueInstance == null)
      uniqueInstance = new CopyrightAndLegalDepositNumberSourceCodes();
    return uniqueInstance;
  }
}