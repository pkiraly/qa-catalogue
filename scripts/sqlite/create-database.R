# library(Rcpp)
library(dplyr)
library(dbplyr)
suppressPackageStartupMessages(library(tidyverse))

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  dir <- args[1]
}

my_db_file <- paste0(dir, "/qa_catalogue.sqlite")
con <- DBI::dbConnect(RSQLite::SQLite(), my_db_file)

print('transforming issue summary')
issue_summary <- read_csv(paste0(dir, '/issue-summary.csv'), 
                          col_types = 'iciicccii')
copy_to(con, issue_summary, overwrite = TRUE, temporary = FALSE)

#' transforming issue details
print('transforming issue details')
issue_details <- read_csv(paste0(dir, '/issue-details-normalized.csv'), col_types = 'cii')

# df <- read_csv(paste0(dir, '/issue-details.csv'), col_types = 'cc')

# issue_details <- df %>% 
#   separate(errors, LETTERS, sep = ';', fill="right") %>% 
#   pivot_longer(
#     all_of(LETTERS),
#     names_to = 'key',
#     values_to = 'value',
#     values_drop_na = TRUE
#   ) %>% 
#   select(recordId, value) %>% 
#   separate(value, c('errorId', 'instances'), sep=':', fill='right') %>% 
#   mutate(
#     errorId = as.integer(errorId),
#     instances = as.integer(instances)
#   )
copy_to(con, issue_details, overwrite = TRUE, temporary = FALSE)

DBI::dbListTables(con)
DBI::dbDisconnect(con)
