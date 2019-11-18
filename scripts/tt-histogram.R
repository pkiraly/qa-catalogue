library(tidyverse)
#' Reads tt-completeness.csv and creates histogram files for all
#' column. The files are saved under the BASE_OUTPUT_DIR directory specified
#' in setdir.sh in tt-completeness-histogram-[column name].csv name form where
#' [column name] is a lower case, hypen separated form of the column name stored
#' in the input file. Each file has a 'count' and a 'frequency' column.
#' 
#' The script also create a tt-completeness-names.csv file with two columns:
#' 'name' contains the original name (from the input file), 'transformed' contains
#' the transformated names.

#' In RStudio you can run this script in the console:
#' system("Rscript scripts/tt-histogram.R szte")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  catalogue <- args[1]
}

prefix <- 'tt-completeness'
command <- "grep BASE_OUTPUT_DIR= setdir.sh | sed 's/.*=\\(.*\\)/\\1/'"
base_output_dir <- system(command, intern = TRUE)

csv <- sprintf("%s/%s/%s.csv", base_output_dir, catalogue, prefix)
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
  # field transformation
  # field <- gsub(" ", "-", name) %>%
  #   gsub("/", "-", .) %>%
  #   str_replace_all(., "([a-z])([A-Z])", "\\1-\\2") %>%
  #   tolower()
  # transformed_names[[i]] <- field

  col <- rlang::sym(name)
  histogram <- df %>% 
    select(!!col) %>% 
    group_by(!!col) %>% 
    count() %>%
    rename(count = name, frequency = n)
  # print(count)
  histogram_file <- sprintf("%s/%s/%s-histogram-%s.csv",
                            base_output_dir, catalogue, prefix, name)
  write_csv(histogram, histogram_file)
}

# df_names <- tibble(names = names, transformed = transformed_names)
# histogram_file <- sprintf("%s/%s/%s-names.csv",
#                           base_output_dir, catalogue, prefix)
# write_csv(df_names, histogram_file)
