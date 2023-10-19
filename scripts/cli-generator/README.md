The content of this directory helps to generate the CLI scripts' help and getopt parts

Usage:
```bash
php generate.php <file>
```

To run all:

```bash
php generate.php validate.txt > validate.sh
php generate.php completeness.txt > completeness.sh
php generate.php classifications.txt > classifications.sh
php generate.php authorities.txt > authorities.sh
php generate.php serials.txt > serials.sh
php generate.php tt-completeness.txt > tt-completeness.sh
php generate.php shelf-ready-completeness.txt > shelf-ready-completeness.sh
php generate.php functions.txt > functions.sh
php generate.php shacl4bib.txt > shacl4bib.sh
```

The *.txt files are manually curated via copied from the ...Parameters classes.