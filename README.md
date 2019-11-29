# Metadata quality assessment tool for MARC records

This tool reads MARC dump files (in binary MARC or MARCXML formats), analyses different aspects of quality, and saves the results into CSV files. These CSV files could be used in different context, we provide a lightweight, web-based [user interface](#user-interface) for that.

* For more info
  * main project page: [Metadata Quality Assurance Framework](http://pkiraly.github.io)
  * Validating 126 million MARC records at DATeCH 2019 [paper](https://doi.org/10.1145/3322905.3322929), [slides](http://bit.ly/qa-datech2019), [thesis chapter](https://10.13140/RG.2.2.33177.77920)
  * Empirical evaluation of library catalogues at SWIB 2019 [slides](http://bit.ly/qa-swib2019) 
* If you would like to play with this project, but you don't have MARC21 please to download some recordsets mentioned in [Appendix I: Where can I get MARC records?](#datasources) of this document.

## Table of Contents
* [Build](#build)
* [Download](#-or-download)
* [Run](#run)
* [helper-scripts](#helper-scripts)
  * [run](#run-1)
  * [configuration](#configuration)
* [Detailed instructions](#detailed-instructions)
  * [Validating MARC records](#validating-marc-records)
  * [Display one MARC record](#display-one-marc-record)
  * [Calculating simple completeness](#calculating-simple-completeness)
  * [Calculating Thompson-Traill completeness](#calculating-thompson-traill-completeness)
  * [Indexing MARC records with Solr](#indexing-marc-records-with-solr)
    * [MARC tags format](#marc-tags-format)
    * [Human readable format](#human-readable-format)
    * [Mixed format](#mixed-format)
  * [Indexing MARC JSON records with Solr](#indexing-marc-json-records-with-solr)
  * [Export mapping table](#export-mapping-table)
* [Extending the functionalities](#extending-the-functionalities)
* [User interface](#user-interface)
* [Appendix I. Where can I get MARC records](#appendix-i-where-can-i-get-marc-records)
  * [United States of America](#united-states-of-america)
  * [Germany](#germany)
  * [Elsewhere](#others)
* [Appendix II. Handling MARC versions](#appendix-ii-handling-marc-versions)
* [Appendix III. Special build process](#appendix-iii-special-build-process)

## build

Prerequisites: Java 8 (I use OpenJDK), and Maven 3

1. Optional step: clone and build the parent library, metadata-qa-api project:

```
git clone https://github.com/pkiraly/metadata-qa-api.git
cd metadata-qa-api
mvn clean install
cd ..
```

2. Mandatory step: clone and build the current metadata-qa-marc project

```
git clone https://github.com/pkiraly/metadata-qa-marc.git
cd metadata-qa-marc
mvn clean install
```

### ... or download

The released versions of the software is available from Maven Central repository. The stable releases (currently 0.1) is available from all Maven repos, the developer version (0.2-SNAPSHOT) is avalable from the [Sonatype Maven repository](https://oss.sonatype.org/content/repositories/snapshots/de/gwdg/metadataqa/metadata-qa-marc/0.2-SNAPSHOT/). What you need to select is the file `metadata-qa-marc-0.2-[timestamp]-1-jar-with-dependencies.jar`. 

Be aware that no automation exists for creating a this current developer version as nightly build, so there is a chance that the latest features are not available in this version. If you want to use the latest version, do build it.

Since the jar file doesn't contain the helper scipts, you might also consider to download them from this GitHib repository:

```
wget https://raw.githubusercontent.com/pkiraly/metadata-qa-marc/master/common-script
wget https://raw.githubusercontent.com/pkiraly/metadata-qa-marc/master/validator
wget https://raw.githubusercontent.com/pkiraly/metadata-qa-marc/master/formatter
wget https://raw.githubusercontent.com/pkiraly/metadata-qa-marc/master/tt-completeness
```

You should adjust `common-script` to point to the jar file you just downloaded.

## run

<a name="helper-scripts"></a>
### Helper scripts

The tool comes with some bash helper scripts to run all these with default values. The generic scripts locate in the root directory and library specific configuration like scripts exist in the `scripts` directory. You can find predefined scripts for 19 library catalogues (if you want to run it, first you have to configure it).

#### run

```
scripts/[your script] [command]
```

The following commands are supported:

* `validate` -- runs validation
* `completeness` -- runs completeness analysis
* `classifications` -- runs classification analysis
* `authorities` -- runs authorities analysis
* `tt-completeness` -- runs Thomson-Trail completeness analysis
* `serial-score` -- calculates the serial scores
* `format` -- runs formatting records
* `functional-analysis` -- runs functional analysis
* `prepare-solr` -- prepare Solr index (you should already have Solr running, and index created)
* `index` -- runs indexing with Solr
* `all-analyses` this runs the following commands in one step: validate, completeness, classifications, authorities, tt_completeness, serial_score, functional_analysis

You can find information about these functionalities below this document.

#### configuration

1. create the configuration file (setdir.sh)
```
cp setdir.sh.template setdir.sh
```

2. edit the file configuration file. Two lines are important here

```
BASE_INPUT_DIR=your/path
BASE_OUTPUT_DIR=your/path
```

* `BASE_INPUT_DIR` is the parent directory where your MARC records exists
* `BASE_OUTPUT_DIR` is where the analysis results will be stored

3. edit the library specific file

Here is an example file for analysing Library of Congress' MARC records

```
#!/usr/bin/env bash

. ./setdir.sh
NAME=loc
MARC_DIR=${BASE_INPUT_DIR}/loc/marc
MASK=*.mrc

. ./common-script

echo "DONE"
exit 0
```
Three variable are important here:

1. `NAME` is a name for the output directory. The analysis result will land under $BASE_OUTPUT_DIR/$NAME directory
2. `MARC_DIR` is the location of MARC files. All the files should be in the same direcory
3. `MASK` is a file mask, such as *.mrc or *.marc

You can add here any other parameters this document mentioned at the description of individual command, wrapped in TYPE_PARAMS variable e.g. for the Deutche Nationalbibliothek's config file, one can find this

```
TYPE_PARAMS="--marcVersion DNB --marcxml"
```

This line sets the DNB's MARC version (to cover fields defined within DNB's MARC version), and XML as input format.

## Detailed instructions


We will use the same jar file in every command, so we save its path into a variable.

```
export JAR=target/metadata-qa-marc-0.2-SNAPSHOT-jar-with-dependencies.jar
```

### Validating MARC records
```
java -cp $JAR de.gwdg.metadataqa.marc.cli.Validator [options] [file]
```
or with a bash script
```
./validator [options] [file]
```

options:

* `-s`, `--summary` creating a summary report instead of record level reports
* `-m [MARC version name]`, `--marcVersion [MARC version name]` specify a MARC version. Crrently supported version names:
   * `MARC21`, Library of Congress MARC21
   * `DNB`, the Deuthche Nationalbibliothek's MARC version
   * `OCLC`, the OCLCMARC
   * `GENT`, fields available in the catalog of Gent University (Belgium)
   * `SZTE`, fields available in the catalog of Szegedi Tudományegyetem (Hungary)
   * `FENNICA`, fields available in the Fennica catalog of Finnish National Library
* `-l [number]`, `--limit [number]` validates only given number of records
* `-o [number]`, `--offset [number]` starts validation at the given Nth record
* `-f [file name]`, `--fileName [file name]` the name of report the program produces. Default is `validation-report.txt`. If you use "stdout", it won't create file, but put results into the standard output.
* `-r [format]`, `--format [format]` format specification of the output. Possible values: `text` (default), `tab-separated` or `tsv`, `comma-separated` or `csv`
* `-n`, `--nolog` do not display log messages
* `-d [record type]`, `--defaultRecordType [record type]` the default record type to be used if the record's type is undetectable. The record type is calculated from the combination of Leader/06 (Type of record) and Leader/07 (bibliographic level), however sometimes the combination doesn't fit to the standard. In this case the tool will use the given record type. Possible values of the record type argument:
   * BOOKS
   * CONTINUING_RESOURCES
   * MUSIC
   * MAPS
   * VISUAL_MATERIALS
   * COMPUTER_FILES
   * MIXED_MATERIALS
* `-q`, `--fixAlephseq` ALEPH export contains '^' characters instead spaces in control fields (006, 007, 008). This flag replace them to spaces before the validation
* `-x`, `--marcxml` the input files are not binary MARC, but MARCXML files

The `file` argument might contain any wildcard the operating system supports ('*', '?', etc.)

It creates a file given at `fileName` parameter.

Currently it detects the following errors:

Leader specific errors:

* Leader/[position] has an invalid value: '[value]' (e.g. `Leader/19 (leader19) has an invalid value: '4'`)

Control field specific errors:

* 006/[position] ([name]) contains an invalid code: '[code]' in '[value]' (e.g. ` 006/01-05 (tag006book01) contains an invalid code: 'n' in '  n '`)
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

An example:

```
Error in '   00000034 ': 
  110$ind1 has invalid code: '2'
Error in '   00000056 ': 
  110$ind1 has invalid code: '2'
Error in '   00000057 ': 
  082$ind1 has invalid code: ' '
Error in '   00000086 ': 
  110$ind1 has invalid code: '2'
Error in '   00000119 ': 
  700$ind1 has invalid code: '2'
Error in '   00000234 ': 
  082$ind1 has invalid code: ' '
Errors in '   00000294 ': 
  050$ind2 has invalid code: ' '
  260$ind1 has invalid code: '0'
  710$ind2 has invalid code: '0'
  710$ind2 has invalid code: '0'
  710$ind2 has invalid code: '0'
  740$ind2 has invalid code: '1'
Error in '   00000322 ': 
  110$ind1 has invalid code: '2'
Error in '   00000328 ': 
  082$ind1 has invalid code: ' '
Error in '   00000374 ': 
  082$ind1 has invalid code: ' '
Error in '   00000395 ': 
  082$ind1 has invalid code: ' '
Error in '   00000514 ': 
  082$ind1 has invalid code: ' '
Errors in '   00000547 ': 
  100$ind2 should be empty, it has '0'
  260$ind1 has invalid code: '0'
Errors in '   00000571 ': 
  050$ind2 has invalid code: ' '
  100$ind2 should be empty, it has '0'
  260$ind1 has invalid code: '0'
...
```

#### some post processing usage examples

After running the validation as tab separated file `validation-report.txt`

get the number of errors:

```
wc -l validation-report.txt
```

get the number of records having errors

```
awk -F "\t" '{print $1}' validation-report.txt | uniq -c | wc -l
```

### Display one MARC record

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.Formatter [options] [file]
```
or with a bash script
```
./formatter [options] [file]
```

* `-f`, `--format` the name of the format (at time of writing there is no any)
* `-d [record ID]`, `--id [record ID]` specify a MARC record ID (field 001)
* `-c [count number]`, `-countNr [count number]` count number of the record (e.g. 1 means the first record)
* `-s [path=query]`, `-search [path=query]` print records matching the query. The query part is the content of the element. The path should be one of the following types:
1. control field tag (e.g. `001`, `002`, `003`)
2. control field position (e.g. `Leader/0`, `008/1-2`)
3. data field (`655\$2`, `655\$ind1`)
4. named control field position (`tag006book01`)
* `-n`, `--nolog` do not display log messages

The output of the script is something like this one:

```
LEADER 01697pam a2200433 c 4500
001 1023012219
003 DE-101
005 20160912065830.0
007 tu
008 120604s2012    gw ||||| |||| 00||||ger  
015   $a14,B04$z12,N24$2dnb
016 7 $2DE-101$a1023012219
020   $a9783860124352$cPp. : EUR 19.50 (DE), EUR 20.10 (AT)$9978-3-86012-435-2
024 3 $a9783860124352
035   $a(DE-599)DNB1023012219
035   $a(OCoLC)864553265
035   $a(OCoLC)864553328
040   $a1145$bger$cDE-101$d1140
041   $ager
044   $cXA-DE-SN
082 04$81\u$a622.0943216$qDE-101$222/ger
083 7 $a620$a660$qDE-101$222sdnb
084   $a620$a660$qDE-101$2sdnb
085   $81\u$b622
085   $81\u$z2$s43216
090   $ab
110 1 $0(DE-588)4665669-8$0http://d-nb.info/gnd/4665669-8$0(DE-101)963486896$aHalsbrücke$4aut
245 00$aHalsbrücke$bzur Geschichte von Gemeinde, Bergbau und Hütten$chrsg. von der Gemeinde Halsbrücke anlässlich des Jubliäums "400 Jahre Hüttenstandort Halsbrücke". [Hrsg.: Ulrich Thiel]
264  1$a[Freiberg]$b[Techn. Univ. Bergakad.]$c2012
300   $a151 S.$bIll., Kt.$c31 cm, 1000 g
653   $a(Produktform)Hardback
653   $aGemeinde Halsbrücke
653   $aHüttengeschichte
653   $aFreiberger Bergbau
653   $a(VLB-WN)1943: Hardcover, Softcover / Sachbücher/Geschichte/Regionalgeschichte, Ländergeschichte
700 1 $0(DE-588)1113208554$0http://d-nb.info/gnd/1113208554$0(DE-101)1113208554$aThiel, Ulrich$d1955-$4edt$eHrsg.
850   $aDE-101a$aDE-101b
856 42$mB:DE-101$qapplication/pdf$uhttp://d-nb.info/1023012219/04$3Inhaltsverzeichnis
925 r $arb
```

### Calculating simple completeness
```
java -cp $JAR de.gwdg.metadataqa.marc.cli.Completeness [options] [file]
```
or with a bash script
```
./completeness [options] [file]
```

* `-t [directory]`, `--outputDir [directory]` the directory inside which the output files will be created
* `-r [format]`, `--format [format]` format specification of the output. Possible values: `tab-separated` or `tsv`, `comma-separated` or `csv`

The process will create two files in the output directory:

* marc-elements.csv is list of MARC elements (field$subfield) and their occurences in two ways: 'number-of-record' means how many records they are available, 'number-of-instances' means how many instances are there in total (some records might contain more than one instances, while others don't have them at all)
* libraries.csv list the content of the 852$a (it is useful only if the catalog is an aggregated catalog)

### Calculating Thompson-Traill completeness

Kelly Thompson and Stacie Traill recently published their approach to calculate the quality of ebook records comming from different data sources. Their article is _Implementation of the scoring algorithm described in Leveraging Python to improve ebook metadata selection, ingest, and management._ In Code4Lib Journal, Issue 38, 2017-10-18. http://journal.code4lib.org/articles/12828

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.ThompsonTraillCompleteness [options] [file]
```
or with a bash script
```
./tt-completeness [options] [file]
```

* `-l [number]`, `--limit [number]` validates only given number of records
* `-o [number]`, `--offset [number]` starts validation at the given Nth record
* `-f [file name]`, `--fileName [file name]` the name of report the program produces. Default is `tt-completeness.csv`.
* `-n`, `--nolog` do not display log messages

It produces a CSV file like this:

```
id,ISBN,Authors,Alternative Titles,Edition,Contributors,Series,TOC,Date 008,Date 26X,LC/NLM, \
LoC,Mesh,Fast,GND,Other,Online,Language of Resource,Country of Publication,noLanguageOrEnglish,RDA,total
"010002197",0,0,0,0,0,0,0,1,2,0,0,0,0,0,0,0,1,0,0,0,4
"01000288X",0,0,1,0,0,1,0,1,2,0,0,0,0,0,0,0,0,0,0,0,5
"010004483",0,0,1,0,0,0,0,1,2,0,0,0,0,0,0,0,1,0,0,0,5
"010018883",0,0,0,0,1,0,0,1,2,0,0,0,0,0,0,0,1,1,0,0,6
"010023623",0,0,3,0,0,0,0,1,2,0,0,0,0,0,0,0,1,0,0,0,7
"010027734",0,0,3,0,1,2,0,1,2,0,0,0,0,0,0,0,1,0,0,0,10
```

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

In schema.xml (or in Solr web interface):
```XML
<dynamicField name="*_sni" type="string" indexed="false" stored="true"/>
<copyField source="*_ss" dest="_text_"/>
```

or use Solr API:

```bash
NAME=dnb
SOLR=http://localhost:8983/solr/$NAME/schema

// add copy field
curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-dynamic-field":{
     "name":"*_sni",
     "type":"string",
     "indexed":false,
     "stored":true}
}' $SOLR

curl -X POST -H 'Content-type:application/json' --data-binary '{
  "add-copy-field":{
     "source":"*_ss",
     "dest":["_text_"]}
}' $SOLR
```

Run indexer:
```
java -cp $JAR de.gwdg.metadataqa.marc.cli.MarcToSolr [options] [file]
```

options

* `-s [Solr URL]`, `--solrUrl [Solr URL]` the URL of Solr server
* `-c`, `--doCommit` send commits to Solr regularly (not needed if you set up Solr the above described way)
* `-t [Solr field type]`, `--solrFieldType [Solr field type]` a Solr field type, one of the predefined values. See examples below.
   * `marc-tags` - the field names are MARC codes
   * `human-readable` - the field names are [Self Descriptive MARC code](http://pkiraly.github.io/2017/09/24/mapping/)
   * `mixed` - the field names are mixed of the aboves (e.g. `245a_Title_mainTitle`)
* `-n`, `--nolog` do not display log messages

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

#### "marc-tags" format
```
"100a_ss":["Jung-Baek, Myong Ja"],
"100ind1_ss":["Surname"],
"245c_ss":["Vorgelegt von Myong Ja Jung-Baek."],
"245ind2_ss":["No nonfiling characters"],
"245a_ss":["S. Tret'jakov und China /"],
"245ind1_ss":["Added entry"],
"260c_ss":["1987."],
"260b_ss":["Georg-August-Universität Göttingen,"],
"260a_ss":["Göttingen :"],
"260ind1_ss":["Not applicable/No information provided/Earliest available publisher"],
"300a_ss":["141 p."],
```

#### "human-readable" format
```
"MainPersonalName_type_ss":["Surname"],
"MainPersonalName_personalName_ss":["Jung-Baek, Myong Ja"],
"Title_responsibilityStatement_ss":["Vorgelegt von Myong Ja Jung-Baek."],
"Title_mainTitle_ss":["S. Tret'jakov und China /"],
"Title_titleAddedEntry_ss":["Added entry"],
"Title_nonfilingCharacters_ss":["No nonfiling characters"],
"Publication_sequenceOfPublishingStatements_ss":["Not applicable/No information provided/Earliest available publisher"],
"Publication_agent_ss":["Georg-August-Universität Göttingen,"],
"Publication_place_ss":["Göttingen :"],
"Publication_date_ss":["1987."],
"PhysicalDescription_extent_ss":["141 p."],
```

#### "mixed" format
```
"100a_MainPersonalName_personalName_ss":["Jung-Baek, Myong Ja"],
"100ind1_MainPersonalName_type_ss":["Surname"],
"245a_Title_mainTitle_ss":["S. Tret'jakov und China /"],
"245ind1_Title_titleAddedEntry_ss":["Added entry"],
"245ind2_Title_nonfilingCharacters_ss":["No nonfiling characters"],
"245c_Title_responsibilityStatement_ss":["Vorgelegt von Myong Ja Jung-Baek."],
"260b_Publication_agent_ss":["Georg-August-Universität Göttingen,"],
"260a_Publication_place_ss":["Göttingen :"],
"260ind1_Publication_sequenceOfPublishingStatements_ss":["Not applicable/No information provided/Earliest available publisher"],
"260c_Publication_date_ss":["1987."],
"300a_PhysicalDescription_extent_ss":["141 p."],
```

I have created a distinct project [metadata-qa-marc-web](https://github.com/pkiraly/metadata-qa-marc-web), which provised a single page web application to build a facetted search interface for this type of Solr index.

### Indexing MARC JSON records with Solr

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.utils.MarcJsonToSolr [Solr url] [MARC JSON file]
```

The MARC JSON file is a JSON serialization of binary MARC file. See more the [MARC Pipeline](https://github.com/pkiraly/marc-pipeline/) project.

## Export mapping table

To export the HTML table described at [Self Descriptive MARC code](http://pkiraly.github.io/2017/09/24/mapping/)

```
java -cp $JAR de.gwdg.metadataqa.marc.cli.utils.MappingToHtml > mapping.html
```

## Extending the functionalities

The project is available from Maven Central, the central respository of open source Java projects as jar files. If you want to use it in your Java or Scala application, put this code snippet into the list of dependencies:

pom.xml

```
<dependency>
  <groupId>de.gwdg.metadataqa</groupId>
  <artifactId>metadata-qa-marc</artifactId>
  <version>0.1</version>
</dependency>
```

build.sbt

```
libraryDependencies += "de.gwdg.metadataqa" % "metadata-qa-marc" % "0.1"
```


or you can directly download the jars from [http://repo1.maven.org](http://repo1.maven.org/maven2/de/gwdg/metadataqa/metadata-qa-marc/0.1/)

## User interface

There is a web application for displaying and navigation through the output of the tool (written in PHP):

https://github.com/pkiraly/metadata-qa-marc-web/

<a name="datasources"></a>
## Appendix I: Where can I get MARC records?

Here is a list of data sources I am aware of so far:

### United States of America
* Library of Congress &mdash; https://www.loc.gov/cds/products/marcDist.php. MARC21 (UTF-8 and MARC8 encoding), MARCXML formats, open access.
* Harvard University Library &mdash; https://library.harvard.edu/open-metadata. MARC21 format, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/). Institution specific features are documented [here](http://library.harvard.edu/sites/default/files/news_uploaded/Harvard_Library_Bibliographic_Dataset_Documentation.pdf)
* Columbia University Library &mdash; https://library.columbia.edu/bts/clio-data.html. 10M records, MARC21 and MARCXML format, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).
* University of Michigan Library &mdash; https://www.lib.umich.edu/open-access-bibliographic-records. 1,3M records, MARC21 and MARCXML formats, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).

### Germany
* Deutsche Nationalbibliothek &mdash; http://www.dnb.de/EN/Service/DigitaleDienste/Datendienst/datendienst_node.html (note: it is not a direct link, you have to register and contact with librarians to get access to the downloadable dataset). 16.7M records, MARC21 and MARCXML format, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).
* Bibliotheksservice-Zentrum Baden Würtemberg &mdash; https://wiki.bsz-bw.de/doku.php?id=v-team:daten:openaccess:swb. 17,5 records, MARCXML format, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).
* Bibliotheksverbundes Bayern &mdash; https://www.bib-bvb.de/web/b3kat/open-data. 27M records, MARCXML format, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).
* Leibniz-Informationszentrum Technik und Naturwissenschaften Universitätsbibliothek (TIB) &mdash; https://www.tib.eu/de/die-tib/bereitstellung-von-daten/katalogdaten-als-open-data/. (no download link, use OAI-PMH instead) Dublin Core, MARC21, MARCXML, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).

### Others
* Universiteitsbibliotheek Gent &mdash; https://lib.ugent.be/info/exports. Weeky data dump in Aleph Sequential format. It contains some Aleph fields above the standard MARC21 fields. [ODC ODbL](https://opendatacommons.org/licenses/odbl/).
* Toronto Public Library &mdash; https://opendata.tplcs.ca/. 2.5 million MARC21 records, [Open Data Policy](http://www.torontopubliclibrary.ca/terms-of-use/library-policies/open-data.jsp)
* Répertoire International des Sources Musicales &mdash; https://opac.rism.info/index.php?id=8&id=8&L=1. 800K records, MARCXML, RDF/XML, [CC-BY](https://creativecommons.org/licenses/by/4.0/).
* ETH-Bibliothek (Swiss Federal Institute of Technology in Zurich) &mdash; http://www.library.ethz.ch/ms/Open-Data-an-der-ETH-Bibliothek/Downloads. 2.5M records, MARCXML format.
* British library &mdash; http://www.bl.uk/bibliographic/datafree.html#m21z3950 (no download link, use z39.50 instead after asking for permission). MARC21, usage will be strictly for non-commercial purposes.
* Talis &mdash; https://archive.org/details/talis_openlibrary_contribution. 5.5 million MARC21 records contributed by Talis to Open Library under the [ODC PDDL](https://opendatacommons.org/licenses/pddl/).
* Fennica &mdash; the Finnish National Bibliography provided by the Finnish National Library &mdash; http://data.nationallibrary.fi/download/. 1 million  records, MARCXML, [CC0](https://creativecommons.org/share-your-work/public-domain/cc0/).
* Biblioteka Narodawa (Polish National Library) &mdash; https://data.bn.org.pl/databases. 6.5 million record, MARC21. 

Thanks [Johann Rolschewski](https://github.com/jorol/) and [Phú](https://twitter.com/herr_tu) for their help in collecting this list! Do you know some more data sources? Please let me know.

There are two more datasource worth mention, however they do not provide MARC records, but derivatives:

* [Linked Open British National Bibliography](https://data.bl.uk/lodbnb/) 3.2M book records in N-Triplets and RDF/XML format, CC0 license
* [Linked data of Bibliothèque nationale de France](http://data.bnf.fr/semanticweb). N3, NT and RDF/XML formats, [Licence Ouverte/Open Licence](http://data.bnf.fr/docs/Licence-Ouverte-Open-Licence-ENG.pdf)

## Appendix II: handling MARC versions

The tool provides two levels of customization: 

* project specific tags can be defined in their own Java package, such as these classes for Gent data:
https://github.com/pkiraly/metadata-qa-marc/tree/master/src/main/java/de/gwdg/metadataqa/marc/definition/tags/genttags
* for existing tags one can use the API described below

The different MARC versions has an identifier. This is defined in the code as an enumeration:


```Java
public enum MarcVersion {
  MARC21("MARC21", "MARC21"),
  DNB("DNB", "Deutsche Nationalbibliothek"),
  OCLC("OCLC", "OCLC"),
  GENT("GENT", "Universiteitsbibliotheek Gent"),
  SZTE("SZTE", "Szegedi Tudományegyetem"),
  FENNICA("FENNICA", "National Library of Finland")
  ;
  ...
}
```

When you add version specific modification, you have to use one of these values.


1. Defining version specific indicator codes:

```Java
Indicator::putVersionSpecificCodes(MarcVersion, List<Code>)
```

Code is a simple object, it has two property: code and label.

example:

```Java
public class Tag024 extends DataFieldDefinition {
   ...
   ind1 = new Indicator("Type of standard number or code")
             .setCodes(...)
              .putVersionSpecificCodes(
                 MarcVersion.SZTE,
                 Arrays.asList(
                    new Code(" ", "Not specified")
                 )
              )
   ...
}
```

2. Defining version specific subfields:

```Java
DataFieldDefinition::putVersionSpecificSubfields(MarcVersion, List<SubfieldDefinition>)
```

SubfieldDefinition contains a definition of a subfield. You can construct it with three String parameters: a code, a label and a cardinality code which denotes whether the subfield can be repeatable ("R") or not ("NR").

example:

```Java
public class Tag024 extends DataFieldDefinition {
   ...
   putVersionSpecificSubfields(
      MarcVersion.DNB,
      Arrays.asList(
         new SubfieldDefinition("9", "Standardnummer (mit Bindestrichen)", "NR")
      )
   );
}
```

3. Marking indicator codes as obsolete:
```Java
Indicator::setHistoricalCodes(List<String>)
```

The list should be pairs of code and description.

```Java
public class Tag082 extends DataFieldDefinition {
   ...
   ind1 = new Indicator("Type of edition")
              .setCodes(...)
              .setHistoricalCodes(
                 " ", "No edition information recorded (BK, MU, VM, SE) [OBSOLETE]",
                 "2", "Abridged NST version (BK, MU, VM, SE) [OBSOLETE]"
              )
   ...
}
```

4. Marking subfields as obsolete:

```Java
DataFieldDefinition::setHistoricalSubfields(List<String>)
```
The list should be pairs of code and description.

```Java
public class Tag020 extends DataFieldDefinition {
   ...
   setHistoricalSubfields(
      "b", "Binding information (BK, MP, MU) [OBSOLETE]"
   );
}
```

## Appendix III: Special build process

"deployment" build (when deploying artifacts to Maven Central)
```
mvn clean deploy -Pdeploy
```

Any feedbacks are welcome!

[![Build Status](https://travis-ci.org/pkiraly/metadata-qa-marc.svg?branch=master)](https://travis-ci.org/pkiraly/metadata-qa-marc)
[![Coverage Status](https://coveralls.io/repos/github/pkiraly/metadata-qa-marc/badge.svg?branch=master)](https://coveralls.io/github/pkiraly/metadata-qa-marc?branch=master)
