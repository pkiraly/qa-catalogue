#' it creates a statistics from the status columns counting the number
#' of distinct values from all possible values (that are 0, 1, NA).
#' The header of the file are
# id,0,1,NA
# the rows are the individual rules identified by their IDs
library(tidyverse)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  outputDir <- args[1]
}

inputFile <- paste0(outputDir, '/shacl4bib.csv')

df <- read_csv(inputFile)
columns <- names(df)
columns <- columns[2:length(columns)]

df_stat <- tibble(
  'id' = character(),
  '0' = numeric(),
  '1' = numeric(),
  'NA' = numeric()
)
keys <- names(df_stat)
for (row in 1:length(columns)) {
  column <- columns[row]
  df_stat[row, 1] <- column
  
  values <- df %>% select(all_of(column))
  t <- table(values, useNA = "always")
  names <- names(t)
  names[is.na(names)] <- 'NA'
  values <- as.numeric(t)
  for (i in 2:4) {
    k <- keys[i]
    if (sum(names == k) == 0) {
      v <- 0
    } else {
      v <- values[names == k]
    }
    df_stat[row, i] <- v
  }
}
df_stat

write_csv(df_stat, paste0(outputDir, '/shacl4bib-stat.csv'))
