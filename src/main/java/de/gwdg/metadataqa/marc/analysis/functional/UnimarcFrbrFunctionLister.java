package de.gwdg.metadataqa.marc.analysis.functional;

public class UnimarcFrbrFunctionLister extends FrbrFunctionLister {

  public UnimarcFrbrFunctionLister() {
    super();
  }

  public void prepareBaseline() {
    // There's a file which for each FRBR function (task) lists the fields and subfields that support it. Since there are
    // no flat fields in UNIMARC, it's only in the format of "field$subfield" (e.g. "100$a"). In addition, the source
    // paper doesn't list control fields, leader or indicators, so they are not included here.
    // Also, the paper defines only four user tasks which are part of the Discovery group, namely: Search (Find),
    // Identify, Select and Obtain. The other two groups are not defined in the paper.
    // A similar definition of tasks is evident in the following paper:
    // https://repository.ifla.org/bitstream/123456789/811/2/ifla-functional-requirements-for-bibliographic-records-frbr.pdf
  }
}
