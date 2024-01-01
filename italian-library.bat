@echo off

REM Replace placeholders with your actual values
set host=opac.sbn.it
set port=2100
set db=nopac

REM Search for all records (use a wildcard)
set query=*

yaz-client %host%:%port%/%db% "find %query%" -o search_result.xml

REM Retrieve all records
for /f "tokens=2 delims= " %%A in ('findstr /C:"ResultSetId:" search_result.xml') do set result_set_id=%%A
yaz-client %host%:%port%/%db% -u %user% -p %pass% "fetch %result_set_id%" -o all_records.xml

REM Save all records locally
REM Adjust the file format as needed (e.g., MARCXML, JSON, etc.)
rename all_records.xml all_records.mrc

echo Done!