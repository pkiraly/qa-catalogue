#' reads
#' - percents.csv
#' - [library]-summary-norm.csv
#' writes
#' - result.csv
library(tidyverse)

dir <- '/media/kiru/Elements/projects/marc21/_reports/'
col_names <- c('marcPath', 'type', 'message', 'url', 'count')
catalogs <- c(
  'bayern', 'bzbw', 'cerl', 'columbia', 'dnb', 'gent', 'harvard', 'loc',
  'michigan', 'nfi', 'rism', 'sfpl', 'stanford', 'szte', 'tib', 'toronto'
)

infobox <- tribble(
  ~n, ~name, ~abbreviation, ~record_number,
  #--|--|----
  1, 'bayern', 'bay', 27291431,
  2, 'bzbw', 'bzb', 23058913,
  3, 'cerl', 'crl', 6026652,
  4, 'columbia', 'col', 6735585,
  5, 'dnb', 'dnb', 16734811,
  6, 'gent', 'gnt', 1764210,
  7, 'harvard', 'har', 13711915,
  8, 'loc', 'loc', 10091977,
  9, 'michigan', 'mic', 1315695,
  10, 'nfi', 'nfi',1007884,
  11, 'rism', 'ris', 1284488,
  12, 'sfpl', 'sfp', 931048,
  13, 'stanford', 'stn', 9443083,
  14, 'szte', 'szt', 1216697,
  15, 'tib', 'tib', 3519186,
  16, 'toronto', 'tor', 2504565
)

number_of_records <- infobox %>% 
  select(record_number) %>% 
  sum()
print(paste("the total number of records:", number_of_records))

percents <- read_csv(paste0(dir, 'percents.csv'))
percents

base <- tibble(
  catalog = 'dummy',
  type = 'dummy',
  ratio = '1'
)

for (catalog in catalogs) {
  print(paste('catalog:', catalog))
  input <- paste0(dir, catalog, '-summary-norm.csv')
  # df <- read_csv(input, col_names = col_names)
  df <- read_csv(input)
  infobox %>% filter(name == catalog)
  record_count <- infobox %>%
    filter(name == catalog) %>%
    select('record_number') %>%
    unlist(use.names = FALSE)
  record_count <- sprintf("%.1f", record_count / 1000000)
  print(paste('record_count:', record_count))

  percent <- percents %>%
    filter(name == catalog) %>%
    select('all', 'filtered') %>%
    unlist(use.names = FALSE)
  print(paste('percent:', percent))
  errors_total <- sprintf("%.1f", percent[1])
  print(paste('errors_total:', errors_total))
  errors_filtered <- sprintf("%.1f", percent[2])
  print(paste('errors_filtered:', errors_filtered))
  
  tmp <- df %>% 
    group_by(type) %>% 
    summarise(n = sum(count)) %>% 
    mutate(catalog = catalog) %>% 

    # records
    mutate(type = ifelse(
      type == 'record: ambiguous linkage',
              'R1 ambig. link', type)) %>% 
    mutate(type = ifelse(
      type == 'record: invalid linkage',
              'R2 invalid link', type)) %>% 
    mutate(type = ifelse(
      type == 'record: undetectable type',
              'R3 type error', type)) %>% 

    # control subfield
    mutate(type = ifelse(
      type == 'control subfield: invalid code',
              'C1 invalid code', type)) %>% 
    mutate(type = ifelse(
      type == 'control subfield: invalid value',
              'C2 invalid value', type)) %>% 

    # field
    mutate(type = ifelse(
      type == 'field: missing reference subfield (880$6)',
              'F1 missing ref', type)) %>% 
    mutate(type = ifelse(
      type == 'field: repetition of non-repeatable field',
              'F2 non-repeatable', type)) %>% 
    mutate(type = ifelse(
      type == 'field: undefined field',
              'F3 undefined', type)) %>% 
    
    # indicator
    mutate(type = ifelse(
      type == 'indicator: invalid value',
              'I1 invalid', type)) %>% 
    mutate(type = ifelse(
      type == 'indicator: non-empty indicator',
              'I2 non-empty', type)) %>% 
    mutate(type = ifelse(
      type == 'indicator: obsolete value',
              'I3 obsolete', type)) %>% 

    # subfield
    mutate(type = ifelse(
      type == 'subfield: invalid classification reference',
              'S1 classification', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: invalid ISBN',
              'S2 ISBN', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: invalid ISSN',
              'S3 ISSN', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: invalid length',
              'S4 length', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: invalid value',
              'S5 invalid value', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: repetition of non-repeatable subfield',
              'S6 repetition', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: undefined subfield',
              'S7 undefined', type)) %>% 
    mutate(type = ifelse(
      type == 'subfield: content is not well-formatted',
              'S8 format', type)) %>% 
    
    select(catalog, type, n)

  total_errors_count <- tmp %>%
    select(n) %>%
    summarize(total = sum(n)) %>%
    unlist(use.names = FALSE)

  with_ratio <- tmp %>% 
    mutate(ratio = sprintf("%.1f", (n * 100 / total_errors_count[1]))) %>% 
    select(catalog, type, ratio)
    
  with_ratio
  
  tmp <- add_row(
    with_ratio,
    catalog = catalog,
    type = 'zz0 errors_total',
    ratio = errors_total)
  tmp <- add_row(
    tmp,
    catalog = catalog,
    type = 'zz1 errors_filtered',
    ratio = errors_filtered)
  tmp <- add_row(
    tmp,
    catalog = catalog,
    type = 'zz2 number of records',
    ratio = record_count)
  
  # print(tmp, n=30)
  base <- union(base, tmp)
}

base <- base %>%
  filter(catalog != 'dummy')
base

result <- base %>%
  spread(key = catalog, value = ratio) %>% 
  arrange(type) %>% 
  mutate(type = ifelse(type == 'zz0 errors_total',
                       'records with issues', type)) %>% 
  mutate(type = ifelse(type == 'zz1 errors_filtered',
                       '-- -- excl. undocumented el.', type)) %>% 
  mutate(type = ifelse(type == 'zz2 number of records',
                       'number of records (millions)', type)) %>% 
  replace_na(list(
    bayern = '--',
    cerl = '--',
    columbia = '--',
    dnb = '--',
    gent = '--',
    harvard = '--',
    loc = '--',
    michigan = '--',
    nfi = '--',
    rism = '--',
    sfpl = '--',
    stanford = '--',
    szte = '--',
    bzbw = '--',
    tib = '--',
    toronto = '--'
  ))

print(result, n=30)

write_csv(result, paste0(dir, 'result.csv'))

print("DONE")
