# library(tidyverse)
suppressPackageStartupMessages(library(readr))
#library(magrittr)
suppressPackageStartupMessages(library(dplyr))
library(ggplot2)

#' In RStudio you can run this script in the console:
#' system("Rscript scripts/network-nodes-degrees.R [directory-of-shelf-ready-completeness.csv]")

args = commandArgs(trailingOnly=TRUE)
tag <- '658'
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  output_dir <- args[1]
} else if (length(args) == 2) {
  output_dir <- args[1]
  tag <- args[2]
}

output_dir <- '/home/kiru/bin/marc/_output/gent/network-scores'
# prefix <- 'network-nodes-components-histogram'
prefix <- paste0('network-scores-', tag, '-degrees-histogram')

csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(
  csv,
  col_types = cols(
    degree = col_double(),
    count = col_double()
  ),
  show_col_types = FALSE
)

plot <- df %>% 
  ggplot(aes(x=degree, y=count)) +
  geom_point(alpha = 0.7) +
  scale_x_continuous(
    trans='log10',
    breaks = c(1, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  scale_y_continuous(
    trans='log10',
    breaks = c(1, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  ggtitle(
    "Degreees (connectedness) of MARC records",
    sub = "How many other records are connected to a single record?") +
  xlab("Degree (the number of connections)") +
  ylab("Number of records")

plot
img_path <- paste0(output_dir, '/../img/network-scores-', tag, '-degrees-histogram.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))

prefix <- paste0('network-scores-', tag, '-degrees')
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(
  csv,
  col_types = cols(
    id = col_double(),
    degree = col_double()
  ),
  show_col_types = FALSE
)

plot <- df %>% 
  select(degree) %>% 
  arrange(desc(degree)) %>% 
  mutate(id = seq(n())) %>% 
  ggplot(aes(y = degree, x = id)) +
  geom_point() +
  # geom_point(aes(size=degree)) +
  # scale_colour_manual(values = c("black", "red")) +
  scale_x_continuous(
    trans='log10',
    breaks = c(1, 2, 3, 5, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 2, 3, 5, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  scale_y_continuous(
    trans='log10',
    breaks = c(1, 2, 3, 5, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 2, 3, 5, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  ggtitle(
    "Connected components in the catalogue",
    sub = "How many other records are connected to a single record?") +
  ylab("number of connected records") +
  xlab(paste0("records ordered by connections  (n=", count(df), ')')) +
  theme(legend.position = "none")

plot
img_path <- paste0(output_dir, '/../img/network-scores-', tag, '-degrees.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))
