# library(tidyverse)
library(readr)
library(dplyr)

#' input:
#'   network.csv (concept, id)
#' output:
#'   network-by-concepts.csv (concept, count, ids)
#'   network-by-record.csv (id, count, concepts)
#'   network-statistics.csv (type, total, single, multi)
#'
#' In RStudio you can run this script in the console:
#' system("Rscript scripts/network-transform.R szte")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  output_dir <- args[1]
}

prefix <- 'network'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
csv
df <- read_csv(csv)

df %>% 
  group_by(concept) %>% 
  mutate(
    count = n(),
    ids = paste0(id, collapse = ';')
  ) %>% 
  distinct(concept, count, ids)

by_concepts <- df %>% 
  group_by(concept) %>% 
  mutate(
    count = n(),
    ids = paste0(id, collapse = ';')
  ) %>% 
  distinct(concept, count, ids)
write_csv(by_concepts, sprintf('%s/network-by-concepts.csv', output_dir))

tags <- df %>% 
  group_by(tag) %>% 
  summarise(count = n()) %>% 
  arrange(desc(count))
write_csv(tags, sprintf('%s/network-by-concepts-tags.csv', output_dir))

len <- nrow(tags)
for (i in 1:len) {
  row <- tags[i,]
  currTag <- row$tag
  
  print(currTag)

  by_concepts <- df %>% 
    filter(tag == currTag) %>% 
    select(concept, id) %>% 
    group_by(concept) %>% 
    mutate(
      count = n(),
      ids = paste0(id, collapse = ';')
    ) %>% 
    distinct(concept, count, ids)
  c <- dim(by_concepts)
  
  file <- sprintf('%s/network-by-concepts-%s.csv', output_dir, currTag)
  write_csv(by_concepts, file)
}

total_concept <- dim(by_concepts)[1]
single_concepts <- by_concepts %>% 
  filter(count == 1) %>% 
  nrow()
multi_concepts <- total_concept - single_concepts

by_record <- df %>% 
  group_by(id) %>% 
  mutate(
    count = n(),
    concepts = paste0(concept, collapse = ';')
  ) %>% 
  distinct(id, count, concepts)
write_csv(by_record, sprintf('%s/network-by-record.csv', output_dir))

total_records <- dim(by_record)[1]
single_records <- by_record %>% 
  filter(count == 1) %>% 
  nrow()
multi_records <- total_records - single_records

statistics <- tribble(
  ~type, ~total, ~single, ~multi,
  'concepts', total_concept, single_concepts, multi_concepts,
  'records',  total_records, single_records,  multi_records
)
write_csv(statistics, sprintf('%s/network-statistics.csv', output_dir))
