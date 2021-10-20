# library(Rcpp)
library(dplyr)
library(dbplyr)
library(tidyverse)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  dir <- args[1]
}

issue_summary <- read_csv(paste0(dir, '/issue-summary.csv'), 
                          col_types = 'iciicccii')

str(issue_summary)

my_db_file <- paste0(dir, "/qa_catalogue.sqlite")
con <- DBI::dbConnect(RSQLite::SQLite(), my_db_file)

copy_to(con, issue_summary, overwrite = TRUE, temporary = FALSE)
DBI::dbListTables(con)

DBI::dbDisconnect(con)
