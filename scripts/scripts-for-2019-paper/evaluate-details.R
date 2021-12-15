#' reads
#' [library]-all-ids-norm.csv
#' [library]-filtered-ids-norm.csv
#' writes
#' [library]-histogram.csv
#' percents.csv

library(tidyverse)

dir <- '/media/kiru/Elements/projects/marc21/_reports/'
# col_names <- c('recordId', 'marcPath', 'type', 'message', 'url')
catalogs <- c(
  'bayern', 'bzbw', 'cerl', 'columbia', 'dnb', 'gent', 'harvard', 'loc',
  'michigan', 'nfi', 'rism', 'sfpl', 'stanford', 'szte', 'tib', 'toronto'
)

infobox <- tribble(
  ~n, ~name, ~record_number,
  #--|--|----
  1, 'bayern', 27291431,
  2, 'bzbw', 23058913,
  3, 'cerl', 6026652,
  4, 'columbia', 6735585,
  5, 'dnb', 16734811,
  6, 'gent', 1764210,
  7, 'harvard', 13711915,
  8, 'loc', 10091977,
  9, 'michigan', 1315695,
  10, 'nfi', 1007884,
  11, 'rism', 1284488,
  12, 'sfpl', 931048,
  13, 'stanford', 9443083,
  14, 'szte', 1216697,
  15, 'tib', 3519186,
  16, 'toronto', 2504565
)

base <- tibble(
  count = 0,
  record_count = 0,
  catalog = 'dummy'
)

percents <- tibble(
  all = 0,
  filtered = 0,
  name = 'dummy'
)

catalog <- 'bzbw'
for (catalog in catalogs) {
  print(catalog)
  record_count <- infobox %>%
    filter(name == catalog) %>%
    select('record_number') %>%
    unlist(use.names = FALSE)
  file <- paste0(dir, catalog, '-all-ids-norm.csv')
  wo_local_fields_file <- paste0(dir, catalog, '-filtered-ids-norm.csv')
  if (!file.exists(file)) {
    next
  }
  # df <- read_csv(file, col_names = col_names)
  df <- read_csv(file)
  
  # df <- df %>% select('recordId')
  
  # ids_with_count <- df %>% 
  #   group_by(recordId) %>% 
  #   summarise(error_count = n())

  records_with_errors <- df %>%
    count() %>%
    unlist(use.names = FALSE)
  records_with_errors
  record_count
  percent <- records_with_errors * 100 / record_count
  print(percent)
  
  wo_local_fields <- read_csv(wo_local_fields_file) %>%
    count() %>%
    unlist(use.names = FALSE)
  percent_wo_local_fields <- wo_local_fields * 100 / record_count
  print(percent_wo_local_fields)
  
  percents <- union(
    percents, 
    tibble(all = percent, filtered = percent_wo_local_fields, name = catalog)
  )
  percents

  histogram_data <- df %>% 
    select(count) %>% 
    group_by(count) %>% 
    summarise(record_count = n()) %>% 
    mutate(catalog = catalog)

  histogram_data
  write_csv(histogram_data, paste0(dir, catalog, '-histogram.csv'))
  base <- union(base, histogram_data)

  # histogram_data %>% 
  #   ggplot() + 
  #   geom_line(aes(x = count, y = record_count)) +
  #   scale_y_log10()
  rm(df, histogram_data)
}

base <- base %>%
  filter(catalog != 'dummy')

base %>% 
  filter(count < 20) %>% 
  ggplot() + 
  geom_line(aes(x = count, y = record_count, color = catalog, linetype = catalog)) +
  scale_y_log10()

percents <- percents %>%
  filter(name != 'dummy')
percents
write_csv(percents, paste0(dir, 'percents.csv'))

print("DONE")
