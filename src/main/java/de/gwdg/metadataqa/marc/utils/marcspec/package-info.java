/**
 * Implementation of Carsten Klee's MARCspec
 *
 * MARCspec - A common MARC record path language
 * Carsten Klee (ZDB)
 * 2017-11-20 (version 0.16beta+2)
 * https://marcspec.github.io/MARCspec/marc-spec.html
 *
 * 9.2 Reference to field data
 * fieldSpec  = fieldTag [index] [characterSpec]
 * ---
 * LDR        field data of the leader
 * 00.        all field data of fields having a field tag starting with 00
 * 7..        all field data of fields having a field tag starting with 7
 * 100        data elements of all repetitions of the ‘100’ field.
 *
 * 9.3 Reference to substring
 * characterSpec   = "/" positionOrRange
 * position        = positiveInteger / "#"
 * range           = position "-" position
 * positionOrRange = range / position
 * ---
 * LDR/0-4    substring of field data in the leader from character position ‘0’ to character position ‘4’
 * LDR/6      data in the leader at character position ‘6’
 * 007/0      data in the control field ‘007’ at character position ‘0’
 * 007/1-#    all data but the first character in the control field ‘007’
 * 007/#      the last character in the control field ‘007’.
 * 245$a/#-1  the last two characters of the value of the subfield ‘a’ of field ‘245’.
 *
 * 9.4 Reference to data content
 * abrSubfieldSpec = (subfieldCode / subfieldCodeRange) [index] [characterSpec]
 * subfieldSpec    = fieldTag [index] abrSubfieldSpec
 * ---
 * 245$a      value of the subfield ‘a’ of field ‘245’.
 * 245$a$b$c  the value of the subfields ‘a’, ‘b’ and ‘c’ of field ‘245’.
 * 245$a-c    Same as above, but with the use of a subfield code range.
 * ...$_$$    values of subfields ’_‘ and ’$’.
 *
 * 9.5 Reference to occurrence
 * index = "[" positionOrRange "]"
 * ---
 * 300[0]     the first ‘300’ field.
 * 300[1]     the second of the ‘300’ field.
 * 300[0-2]   the first, second and third of the ‘300’ field.
 * 300[1-#]   all but the first of the ‘300’ field.
 * 300[#]     the last of the ‘300’ field.
 * 300[#-1]   the last two of the ‘300’ field.
 * 300[0]$a   value of the subfield ‘a’ of the first ‘300’ field.
 * 300$a[0]   the value of the first subfield ‘a’ of the field ‘300’
 * 300$a[#]   the value of the last subfield ‘a’ of the field ‘300’
 * 300$a[#-1] the value of the last two repetitions of subfield ‘a’ of the field ‘300’
 *
 * 9.6 Reference to indicator values
 * abrIndicatorSpec = [index] "^" ("1" / "2")
 * indicatorSpec    = fieldTag abrIndicatorSpec
 * ---
 * 880^1      value(s) of indicator 1 of all occurrences of field ‘880’.
 * 880[1]^2   value of indicator 2 of first repetition of field ‘880’.
 */
package de.gwdg.metadataqa.marc.utils.marcspec;