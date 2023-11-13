suppressPackageStartupMessages(library(tidyverse))

#' input:
#'   classifications-by-schema (concept, id)
#' output:
#'   classifications-by-type (concept, count, ids)
#'
#' In RStudio you can run this script in the console:
#' system("Rscript scripts/classifications-type.R <catalogues>")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  output_dir <- args[1]
}

prefix <- 'classifications-by-schema'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(
        csv,
        col_types=list(
          id = col_double(),
          field = col_character(),
          location = col_character(),
          scheme = col_character(),
          abbreviation = col_character(),
          abbreviation4solr = col_character(),
          recordcount = col_double(),
          instancecount = col_double(),
          type = col_character()
        )
      )

types <- df %>% 
  select(scheme, abbreviation, type) %>% 
  group_by(abbreviation) %>% 
  distinct()

count <- df %>% 
  group_by(scheme) %>% 
  summarise(
    recordcount = sum(recordcount),
  ) %>% 
  arrange(desc(recordcount))

df2 <- count %>% 
  left_join(types)

prefix <- 'classifications-by-type'
csv <- sprintf("%s/%s.csv", output_dir, prefix)

write_csv(df2, csv)
