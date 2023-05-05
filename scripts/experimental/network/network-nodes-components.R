suppressPackageStartupMessages(library(tidyverse))
library(ggplot2)

#' In RStudio you can run this script in the console:
#' system("Rscript scripts/network-nodes-components.R [directory-of-shelf-ready-completeness.csv]")

args = commandArgs(trailingOnly=TRUE)
tag <- '100'
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
prefix <- paste0('network-scores-', tag, '-components-histogram')

csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}

df <- read_csv(
  csv,
  col_types = cols(
    size = col_double(),
    count = col_double()
  ),
  show_col_types = FALSE
)

df2 <- df %>% 
  mutate(
    is_doubled = size > (lag(size) * 2)
  )

count_doubled <- df2 %>% 
  filter(is_doubled == TRUE) %>% 
  count() %>% 
  unlist(use.names = FALSE)

if (count_doubled > 0) {
  first_doubled <- df2 %>% 
    filter(is_doubled == TRUE) %>% 
    select(size, is_doubled) %>% 
    filter(size == min(size)) %>% 
    select(size) %>% 
    unlist(use.names = FALSE)
  df3 <- df2 %>% 
    mutate(outlier = size >= first_doubled)
} else {
  df3 <- df2 %>% 
    mutate(outlier = FALSE)
}

plot <- df3 %>% 
  ggplot(aes(x=size, y=count)) +
  # geom_point() +
  # geom_line() +
  geom_point(aes(colour = factor(outlier))) +
  scale_colour_manual(values = c("black", "red")) +
  scale_x_continuous(
    trans='log10',
    breaks = c(1, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  scale_y_continuous(
    trans='log10',
    breaks = c(1, 2, 3, 5, 10, 100, 1000, 10000, 100000, 1000000),
    labels = c(1, 2, 3, 5, 10, 100, 1000, "10K", "100K", "1M")
  ) +
  ggtitle(
    "Connected components in the catalogue",
    sub = "How many other records are connected together via subject heading or authority entries?") +
  xlab("number of records in the cluster (extralarge clusters are in red)") +
  ylab("number of clusters") +
  theme(legend.position = "none")

plot
img_path <- paste0(output_dir, '/../img/network-scores-', tag, '-components-histogram.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))

prefix <- paste0('network-scores-', tag, '-components')
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(
  csv,
  col_types = cols(
    componentId = col_double(),
    size = col_double()
  ),
  show_col_types = FALSE
)

df2 <- df %>% 
  select(size) %>% 
  mutate(
    id = seq(n()),
    is_halfed = size <= lag(size) / 2
  )

count_halfed <- df2 %>% 
  filter(is_halfed == TRUE) %>% 
  count() %>% 
  unlist(use.names = FALSE)

if (count_halfed > 0) {
  last_halfed <- df2 %>% 
    filter(is_halfed == TRUE) %>% 
    select(size, is_halfed) %>% 
    filter(size == min(size)) %>% 
    select(size) %>% 
    unlist(use.names = FALSE)
  df3 <- df2 %>% 
    mutate(outlier = size > last_halfed)
} else {
  df3 <- df2 %>% 
    mutate(outlier = FALSE)
}

plot <- df3 %>% 
  select(size, id, outlier) %>% 
  ggplot(aes(y = size, x = id)) +
  geom_line() +
  geom_point(aes(colour = factor(outlier), size=size)) +
  scale_colour_manual(values = c("black", "red")) +
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
    sub = paste(
      'How many other records are connected together via',
      ifelse(
        tag == 'all',
        'subject heading or authority entries?',
        paste('tag', tag)
      )
    )
  ) +
  ylab("number of records in the cluster") +
  xlab(paste0("clusters ordered by size (1-", count(df), ')')) +
  theme(legend.position = "none")

plot
img_path <- paste0(output_dir, '/../img/network-scores-', tag, '-components-sorted.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))
