#library(tidyverse)
library(readr)
library(dplyr)

#' Reads serial-score and creates histogram files for all
#' column. The files are saved under the BASE_OUTPUT_DIR directory specified
#' in setdir.sh in serial-score-histogram-[column name].csv name form where
#' [column name] is a lower case, hypen separated form of the column name stored
#' in the input file. Each file has a 'count' and a 'frequency' column.
#' 
#' In RStudio you can run this script in the console:
#' system("Rscript scripts/serial-score/scores-histogram.R szte")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output fir
  output_dir <- args[1]
}

prefix <- 'serial-score'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}

df <- read_csv(csv)
df <- df %>% 
  select(-id)
names <- names(df)

transformed_names <- vector("character", length(names))
for (i in seq_along(names)) {
  name <- names[[i]]

  col <- rlang::sym(name)
  histogram <- df %>% 
    select(!!col) %>% 
    group_by(!!col) %>% 
    count() %>%
    rename(count = name, frequency = n)

  histogram_file <- sprintf("%s/%s-histogram-%s.csv",
                            output_dir, prefix, name)
  write_csv(histogram, histogram_file)
  print(sprintf("saving %s into %s", name, histogram_file))
}

print("DONE with serial-scores-histogram.R")