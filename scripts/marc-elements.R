library(tidyverse)

dir <- '~/temp/marc21'
output_dir <- '/media/kiru/Elements/projects/marc21/_reports/'

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

i <- 1
for (i in infobox$n) {
  info <- infobox %>% filter(n == i)
  catalog <- info$name
  print(catalog)
  record_number <- info$record_number
  file <- paste(dir, catalog, 'marc-elements.csv', sep = '/')
  elements <- read_csv(file)

  names(elements)
  
  elements %>% filter(is.na(subfield))
  
  nas <- elements %>% filter(is.na(subfield)) %>% nrow()
  
  fields <- elements %>%
    mutate(percent = `number-of-record` * 100 / record_number) %>% 
    select(path, percent)
  
  total <- nrow(fields)
  one <- fields %>% filter(percent >= 1.0) %>% nrow()
  ten <- fields %>% filter(percent >= 10.0) %>% nrow()
  fif <- fields %>% filter(percent >= 50.0) %>% nrow()
  eig <- fields %>% filter(percent >= 80.0) %>% nrow()
  nin <- fields %>% filter(percent >= 90.0) %>% nrow()
  
  field_stat <- tribble(
    ~name, ~total, ~nas, ~one, ~ten, ~fif, ~eig, ~nin,
    #--|--|----
    info$abbreviation, total, nas, one, ten, fif, eig, nin
  )
  field_stat

  if (i == 1) {
    all <- field_stat
  } else {
    all <- union(all, field_stat)
  }
}

all
