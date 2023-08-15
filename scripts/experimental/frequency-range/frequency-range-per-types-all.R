suppressPackageStartupMessages(library(tidyverse))
library(grid)
library(gridExtra)

source(file="scripts/frequency-range-per-types.R")

dir <- '~/bin/marc/_output/'
file <- 'marc-elements.csv'

names <- tribble(
  ~name, ~label,
  'gent', 'Gent',
  'szte', 'SzTE',
  'mtak', 'MTAK',
  'nfi',  'NFI'
)

field <- 'number-of-instances'
create_full_picture(paste0(dir, '/szte'), 'szte', 'A', field)
create_full_picture(paste0(dir, '/mtak'), 'mtak', 'B', field)
create_full_picture(paste0(dir, '/gent'), 'gent', 'C', field)
create_full_picture(paste0(dir, '/nfi'), 'nfi',  'D', field)

# create_all_pictures('szte', field)
nr_rows <- dim(names)[1]
for (i in 1:nr_rows) {
  row <- names[i,]
  create_all_pictures(paste0(dir, '/', row$name), row$name, field)
}

names <- c('szte', 'mtak', 'gent', 'nfi')
for (name in names) {
  print(name)
  df <- read_csv(sprintf('%s/%s/%s', dir, name, file))
  df_summary <- df %>% 
    group_by(type) %>% 
    summarise(count = n()) %>% 
    mutate(machine_name = sub(' ', '-', tolower(type)))
  print(df_summary)
}

nr_rows <- dim(df_summary)[1]
for (i in 1:nr_rows) {
  row <- df_summary[1,]
  print(row$type)
  print(row$count)
} 
