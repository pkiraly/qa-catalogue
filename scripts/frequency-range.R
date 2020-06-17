library(tidyverse)
#library(readr)
#library(dplyr)

source(file="scripts/frequency-range-per-types.R")

#' Reads serial-score and creates histogram files for all
#' column. The files are saved under the BASE_OUTPUT_DIR directory specified
#' in setdir.sh in serial-score-histogram-[column name].csv name form where
#' [column name] is a lower case, hypen separated form of the column name stored
#' in the input file. Each file has a 'count' and a 'frequency' column.
#' 
#' In RStudio you can run this script in the console:
#' system("Rscript scripts/scores-histogram.R szte")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  catalogue <- args[1]
}

print(catalogue)

prefix <- 'serial-score'
command <- "grep BASE_OUTPUT_DIR= setdir.sh | sed 's/.*=\\(.*\\)/\\1/'"
base_output_dir <- system(command, intern = TRUE)
print(base_output_dir)

field <- 'number-of-instances'
file <- 'marc-elements.csv'
create_all_pictures(paste0(base_output_dir, '/', catalogue), catalogue, field, file)
