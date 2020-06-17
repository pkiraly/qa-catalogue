library(tidyverse)
#library(readr)
#library(dplyr)

source(file="scripts/frequency-range-per-types.R")

#' Reads marc-elements.csv and creates field distribution charts for all
#' document types. The files are saved in an 'img' directory
#' under the BASE_OUTPUT_DIR directory specified
#' in setdir.sh as frequency-explained-[type].png name form where
#' [type] is a lower case, hypen separated form of the document type.
#' 
#' In RStudio you can run this script in the console:
#' system("Rscript scripts/frequency-range-per-types.R gent")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  catalogue <- args[1]
}

print(paste('catalogue: ', catalogue))

command <- "grep BASE_OUTPUT_DIR= setdir.sh | sed 's/.*=\\(.*\\)/\\1/'"
base_output_dir <- system(command, intern = TRUE)
print(paste('base_output_dir: ', base_output_dir))

field <- 'number-of-instances'
file <- 'marc-elements.csv'
create_all_pictures(paste0(base_output_dir, '/', catalogue), catalogue, field, file)

print('DONE')
