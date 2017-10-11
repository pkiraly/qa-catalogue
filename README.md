# metadata-qa-marc
Metadata assessment for MARC records

# build:

```
git clone git@github.com:pkiraly/metadata-qa-marc.git
cd metadata-qa-marc
mvn clean install
```

# run
```
export JAR=target/metadata-qa-marc-0.1-SNAPSHOT-jar-with-dependencies.jar
```

## validate MARC file
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
