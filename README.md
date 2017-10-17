# metadata-qa-marc
Metadata quality assessment for MARC records

* If you would like to play with this project, but you don't have MARC21 I suggest to download records from the Library of Congress: https://www.loc.gov/cds/products/MDSConnect-books_all.html
* This is an early phase of the project, nor the software, neighter the documentation are ready. But still it is in a state which I thought worth sharing
* For more info see the main project page: [Metadata Quality Assurance Framework](http://pkiraly.github.io)

## build

Prerequisites: Java 8 (I use OpenJDK), and Maven 3

First clone and build the parent library, metadata-qa-api project:

```
git clone https://github.com/pkiraly/metadata-qa-api.git
cd metadata-qa-api
mvn clean install
cd ..
```

then the current metadata-qa-marc project
```
git clone https://github.com/pkiraly/metadata-qa-marc.git
cd metadata-qa-marc
mvn clean install
```

## run

We will use the same jar file in every command, so we save its path into a variable.

```
export JAR=target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar
```

### Validating MARC records
```
java -cp $JAR de.gwdg.metadataqa.marc.cli.Validator [file]
```

it creates `validation-report.txt` which looks like this

```
Error in '   00000034 ':
 - 110$ind1 has invalid code: '2'
Error in '   00000056 ':
 - 110$ind1 has invalid code: '2'
Error in '   00000057 ':
 - 082$ind1 has invalid code: ' '
Error in '   00000086 ':
 - 110$ind1 has invalid code: '2'
Error in '   00000119 ':
 - 700$ind1 has invalid code: '2'
Error in '   00000234 ':
 - 082$ind1 has invalid code: ' '
Errors in '   00000294 ':
 - 050$ind2 has invalid code: ' '
 - 260$ind1 has invalid code: '0'
 - 710$ind2 has invalid code: '0'
 - 710$ind2 has invalid code: '0'
 - 710$ind2 has invalid code: '0'
 - 740$ind2 has invalid code: '1'
Error in '   00000322 ':
 - 110$ind1 has invalid code: '2'
Error in '   00000328 ':
 - 082$ind1 has invalid code: ' '
Error in '   00000374 ':
 - 082$ind1 has invalid code: ' '
Error in '   00000395 ':
 - 082$ind1 has invalid code: ' '
Error in '   00000514 ':
 - 082$ind1 has invalid code: ' '
Errors in '   00000547 ':
 - 100$ind2 should be empty, it has '0'
 - 260$ind1 has invalid code: '0'
Error in '   00010971 ': 
 - 260 has invalid subfield: d
...
```

Currently it detects the following errors:

Leader specific errors:

* Leader/[position] has an invalid value: '[value]' (e.g. `Leader/19 (leader19) has an invalid value: '4'`)

Control field specific errors:

* 006/[position] ([name]) contains an invalid code: '[code]' in '[value]' (e.g. `	006/01-05 (tag006book01) contains an invalid code: 'n' in '  n '`)
* 006/[position] ([name]) has an invalid value: '[value]' (e.g. `006/13 (tag006book13) has an invalid value: ' '`)
* 007/[position] ([name]) contains an invalid code: '[code]' in '[value]'
* 007/[position] ([name]) has an invalid value: '[value]' (e.g. `007/01 (tag007microform01) has an invalid value: ' '`)
* 008/[position] ([name]) contains an invalid code: '[code]' in '[value]' (e.g. `008/18-22 (tag008book18) contains an invalid code: 'u' in 'u   '`)
* 008/[position] ([name]) has an invalid value: '[value]' (e.g. `008/06 (tag008all06) has an invalid value: ' '`)

Data field specific errors

* Unhandled tag(s): [tags] (e.g. `Unhandled tag: 265`)
* [tag] is not repeatable, however there are [number] instances
* [tag] has invalid subfield(s): [subfield codes] (e.g. `110 has invalid subfield: s`)
* [tag]$[indicator] has invalid code: '[code]' (e.g. `110$ind1 has invalid code: '2'`)
* [tag]$[indicator] should be empty, it has '[code]' (e.g. `110$ind2 should be empty, it has '0'`)
* [tag]$[subfield code] is not repeatable, however there are [number] instances (e.g. `072$a is not repeatable, however there are 2 instances`)
* [tag]$[subfield code] has an invalid value: [value] (e.g. `046$a has an invalid value: 'fb-----'`)

Errors of specific fields:

* 045$a error in '[value]': length is not 4 char (e.g. `045$a error in '2209668': length is not 4 char`)
* 045$a error in '[value]': '[part]' does not match any patterns
* 880 should have subfield $a
* 880 refers to field [tag], which is not defined (e.g. `880 refers to field 590, which is not defined`)


### Indexing MARC records with Solr

Set autocommit the following way in solrconfig.xml (inside Solr):

```XML
    <autoCommit>
      <maxTime>${solr.autoCommit.maxTime:15000}</maxTime>
      <maxDocs>5000</maxDocs>
      <openSearcher>true</openSearcher>
    </autoCommit>
...
    <autoSoftCommit>
      <maxTime>${solr.autoSoftCommit.maxTime:-1}</maxTime>
    </autoSoftCommit>
```
It needs because in the library's code there is no commit, which makes the parallel indexing faster.

Run indexer:
```
java -cp $JAR de.gwdg.metadataqa.marc.cli.MarcToSolr [Solr url] [file]
```

The Solr URL is something like this: http://localhost:8983/solr/loc. It uses the [Self Descriptive MARC code](http://pkiraly.github.io/2017/09/24/mapping/), in which encoded values are decoded to human readble values (e.g. Leader/5 = "c" becames Leader_recordStatus = "Corrected or revised") so a record looks like this:

```JSON
{
        "id":"   00004081 ",
        "type_ss":["Books"],
        "Leader_ss":["00928cam a22002531  4500"],
        "Leader_recordLength_ss":["00928"],
        "Leader_recordStatus_ss":["Corrected or revised"],
        "Leader_typeOfRecord_ss":["Language material"],
        "Leader_bibliographicLevel_ss":["Monograph/Item"],
        "Leader_typeOfControl_ss":["No specified type"],
        "Leader_characterCodingScheme_ss":["UCS/Unicode"],
        "Leader_indicatorCount_ss":["2"],
        "Leader_subfieldCodeCount_ss":["2"],
        "Leader_baseAddressOfData_ss":["0025"],
        "Leader_encodingLevel_ss":["Full level, material not examined"],
        "Leader_descriptiveCatalogingForm_ss":["Non-ISBD"],
        "Leader_multipartResourceRecordLevel_ss":["Not specified or not applicable"],
        "Leader_lengthOfTheLengthOfFieldPortion_ss":["4"],
        "Leader_lengthOfTheStartingCharacterPositionPortion_ss":["5"],
        "Leader_lengthOfTheImplementationDefinedPortion_ss":["0"],
        "ControlNumber_ss":["   00004081 "],
        "ControlNumberIdentifier_ss":["DLC"],
        "LatestTransactionTime_ss":["20070911080437.0"],
        "PhysicalDescription_ss":["cr||||"],
        "PhysicalDescription_categoryOfMaterial_ss":["Electronic resource"],
        "PhysicalDescription_specificMaterialDesignation_ss":["Remote"],
        "PhysicalDescription_color_ss":["No attempt to code"],
        "PhysicalDescription_dimensions_ss":["22 cm."],
        "PhysicalDescription_sound_ss":["No attempt to code"],
        "PhysicalDescription_fileFormats_ss":["No attempt to code"],
        "PhysicalDescription_qualityAssuranceTargets_ss":["No attempt to code"],
        "PhysicalDescription_antecedentOrSource_ss":["No attempt to code"],
        "PhysicalDescription_levelOfCompression_ss":["No attempt to code"],
        "PhysicalDescription_reformattingQuality_ss":["No attempt to code"],
        "GeneralInformation_ss":["870303s1900    iauc          000 0 eng  "],
        "GeneralInformation_dateEnteredOnFile_ss":["870303"],
        "GeneralInformation_typeOfDateOrPublicationStatus_ss":["Single known date/probable date"],
        "GeneralInformation_date1_ss":["1900"],
        "GeneralInformation_date2_ss":["    "],
        "GeneralInformation_placeOfPublicationProductionOrExecution_ss":["iau"],
        "GeneralInformation_language_ss":["eng"],
        "GeneralInformation_modifiedRecord_ss":["Not modified"],
        "GeneralInformation_catalogingSource_ss":["National bibliographic agency"],
        "GeneralInformation_illustrations_ss":["Portraits, No illustrations"],
        "GeneralInformation_targetAudience_ss":["Unknown or not specified"],
        "GeneralInformation_formOfItem_ss":["None of the following"],
        "GeneralInformation_natureOfContents_ss":["No specified nature of contents"],
        "GeneralInformation_governmentPublication_ss":["Not a government publication"],
        "GeneralInformation_conferencePublication_ss":["Not a conference publication"],
        "GeneralInformation_festschrift_ss":["Not a festschrift"],
        "GeneralInformation_index_ss":["No index"],
        "GeneralInformation_literaryForm_ss":["Not fiction (not further specified)"],
        "GeneralInformation_biography_ss":["No biographical material"],
        "IdentifiedByLccn_ss":["   00004081 "],
        "SystemControlNumber_organizationCode_ss":["OCoLC"],
        "SystemControlNumber_ss":["(OCoLC)15259056"],
        "SystemControlNumber_recordNumber_ss":["15259056"],
        "AdminMetadata_transcribingAgency_ss":["GU"],
        "AdminMetadata_catalogingAgency_ss":["United States, Library of Congress"],
        "AdminMetadata_modifyingAgency_ss":["United States, Library of Congress"],
        "ClassificationLcc_ind1_ss":["Item is in LC"],
        "ClassificationLcc_itemPortion_ss":["M6"],
        "ClassificationLcc_ss":["E612.A5"],
        "ClassificationLcc_ind2_ss":["Assigned by LC"],
        "MainPersonalName_personalName_ss":["Miller, James N."],
        "MainPersonalName_ind1_ss":["Surname"],
        "MainPersonalName_fullerForm_ss":["(James Newton)"],
        "Title_ind1_ss":["No added entry"],
        "Title_ind2_ss":["4"],
        "Title_responsibilityStatement_ss":["by James N. Miller ..."],
        "Title_mainTitle_ss":["The story of Andersonville and Florence,"],
        "Publication_agent_ss":["Welch, the Printer,"],
        "Publication_ind1_ss":["Not applicable/No information provided/Earliest available publisher"],
        "Publication_place_ss":["Des Moines, Ia.,"],
        "Publication_date_ss":["1900."],
        "PhysicalDescription_extent_ss":["47 p. incl. front. (port.)"],
        "AdditionalPhysicalFormAvailable_ss":["Also available in digital form on the Library of Congress Web site."],
        "CorporateNameSubject_ind2_ss":["Library of Congress Subject Headings"],
        "CorporateNameSubject_ss":["Florence Prison (S.C.)"],
        "CorporateNameSubject_ind1_ss":["Name in direct order"],
        "Geographic_ss":["United States"],
        "Geographic_generalSubdivision_ss":["Prisoners and prisons."],
        "Geographic_chronologicalSubdivision_ss":["Civil War, 1861-1865"],
        "Geographic_ind2_ss":["Library of Congress Subject Headings"],
        "ElectronicLocationAndAccess_materialsSpecified_ss":["Page view"],
        "ElectronicLocationAndAccess_ind2_ss":["Version of resource"],
        "ElectronicLocationAndAccess_uri_ss":["http://hdl.loc.gov/loc.gdc/scd0001.20000719001an.2"],
        "ElectronicLocationAndAccess_ind1_ss":["HTTP"],
        "_version_":1580884716765052928},
}
```

I have created a distinct project [metadata-qa-marc-web](https://github.com/pkiraly/metadata-qa-marc-web), which provised a single page web application to build a facetted search interface for this type of Solr index.

### Indexing MARC JSON records with Solr

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.MarcJsonToSolr [Solr url] [MARC JSON file]
```

The MARC JSON file is a JSON serialization of binary MARC file. See more the [MARC Pipeline](https://github.com/pkiraly/marc-pipeline/) project.

### Export mapping table

To export the HTML table described at [Self Descriptive MARC code](http://pkiraly.github.io/2017/09/24/mapping/)

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.MappingToHtml > mapping.html
```

Any feedbacks are welcome!
