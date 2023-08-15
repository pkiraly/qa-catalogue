suppressPackageStartupMessages(library(tidyverse))
library(stringr)
library(lubridate)
library(scales)
library(viridisLite)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # output dir
  output_dir <- args[1]
}
# output_dir <- '/home/kiru/Documents/marc21/_output/k10plus_pica'

file <- 'marc-history-grouped.csv'
path <- paste0(output_dir, '/', file)

if (!file.exists(path)) {
  stop(paste("The input file", path, "does not exist", call.=FALSE))
}

input <- read.csv(
  path,
  stringsAsFactors=FALSE,
  colClasses = "character",
  col.names = c('count', 'publication', 'record'))
# names(input) <- c('publication', 'record')
input$count <- as.integer(input$count)

original_count <- dim(input)[1]
print(paste0("original_count: ", original_count))

# filter(str_length(record) != 6)
# filter(str_length(publication) != 4)

d1 <- input %>%
  mutate(publication = substr(publication, 1, 4)) %>% 
  mutate(publication2 = as.Date(parse_date_time(publication, "y"))) %>% 
  mutate(record = paste0(ifelse(substr(record, 0, 2) <= 30, 
                                "20", 
                                "19"), record))

view(d1)

data <- input %>%
  filter(str_length(record) == 6) %>%
  mutate(
    publication2 = as.Date(parse_date_time(publication, "y")),
    recording = as.Date(parse_date_time(
      paste0(ifelse(substr(record, 0, 2) <= 30, "20", "19"), record),
      "ymd"
    )),
    cat_ym = as.Date(parse_date_time(
      substr(paste0(ifelse(substr(record, 0, 2) <= 30, "20", "19"), record), 0, 6),
      "ym"
    )),
    cat_year = year(recording),
    cat_month = month(recording),
    cat_week = week(recording)
  ) %>%
  filter(!is.na(publication2)) %>%
  filter(!is.na(cat_year))

print("data done")
print(dim(data))

filtered_count <- dim(data)[1]
print(paste0("filtered_count: ", filtered_count))

invalid_dates <- original_count - filtered_count

min(data$cat_year)
max(data$cat_year)

str(data)
data %>%
  group_by(cat_year) %>%
  summarise(n = sum(count)) %>% 
  view()


start_year <- 1450
start_date <- as.Date(paste0(start_year, "-01-01"))

outliers <- data %>%
  select(count, publication2, cat_ym) %>%
  filter(
    publication2 >= as.Date("2021-01-01")
    | cat_ym >= as.Date("2021-01-01") # cat_ym
    | cat_ym <= as.Date("1965-01-01")
  ) %>%
  summarise(count2 = sum(count)) %>% 
  select(count2) %>% 
  unlist(use.names = FALSE)

filtered <- data %>%
  select(count, publication2, cat_ym) %>%
  filter(publication2 < as.Date("2023-01-01")) %>%
  filter(publication2 > start_date) %>%
  filter(cat_ym < as.Date("2023-01-01")) %>%
  filter(cat_ym > as.Date("1965-01-01")) %>%
  group_by(publication2, cat_ym) %>%
  summarize(nr=sum(count)) %>%
  ungroup() %>%
  mutate(
    pub = as.integer(year(publication2)),
    cat = cat_ym
  ) %>%
  select(pub, cat, nr)

filtered$nr <- as.factor(filtered$nr)
dim(filtered)

plot <- filtered %>%
  ggplot(aes(x=pub, y=cat)) +
  geom_tile(aes(fill = nr)) + #, colour = "green") +
  # theme_classic() +
  theme(
    legend.position = "none",
    plot.title = element_text(size=22)
  ) +
#  scale_fill_gradientn(colours = rev(magma(5))) +
#  scale_fill_gradientn(
#    "Number\nof books\ncataloged",
#    trans="log10",
#    colors = rev(magma(5)),
#    breaks = outer(c(1,2,5,10),c(1,10,100,1000,10000),"*") %>%
#      as.vector %>%
#      unique
#  ) +
  labs(
    x=paste0('Publication year of the bibliographic item (', start_year, '-)'), #-2020
    y="Month when the bib record created (1965-)",
    title="History of cataloging",
    subtitle=paste0(
      "number of invalid records: ", invalid_dates,
      ", number of outliers: ", outliers
    )
  )

img_dir <- sprintf('%s/img', output_dir)
if (!dir.exists(img_dir)) {
  dir.create(img_dir)
}

img_path <- paste0(img_dir, '/marc-history.png')
ggsave(plot, device="png", filename=img_path, width=10, height=5)

print('DONE')
