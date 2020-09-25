library(tidyverse)
library(ggplot2)

#' In RStudio you can run this script in the console:
#' system("Rscript scripts/network-nodes-components.R [directory-of-shelf-ready-completeness.csv]")

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  output_dir <- args[1]
}

# output_dir <- '/home/kiru/bin/marc/_output/gent'
prefix <- 'network-nodes-components-histogram'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(csv)

df2 <- df %>% 
  mutate(
    is_doubled = size > (lag(size) * 2)
  )

first_doubled <- df2 %>% 
  filter(is_doubled == TRUE) %>% 
  select(size, is_doubled) %>% 
  filter(size == min(size)) %>% 
  select(size) %>% 
  unlist(use.names = FALSE)

df3 <- df2 %>% 
  mutate(outlier = size >= first_doubled)

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
img_path <- paste0(output_dir, '/img/network-nodes-components-histogram.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))

prefix <- 'network-nodes-components'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(csv)

df2 <- df %>% 
  select(size) %>% 
  mutate(
    id = seq(n()),
    is_halfed = size <= lag(size) / 2
  )

last_halfed <- df2 %>% 
  filter(is_halfed == TRUE) %>% 
  select(size, is_halfed) %>% 
  filter(size == min(size)) %>% 
  select(size) %>% 
  unlist(use.names = FALSE)

plot <- df2 %>% 
  mutate(outlier = size > last_halfed) %>% 
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
    sub = "How many other records are connected together via subject heading or authority entries?") +
  ylab("number of records in the cluster") +
  xlab(paste0("clusters ordered by size (1-", count(df), ')')) +
  theme(legend.position = "none")

plot
img_path <- paste0(output_dir, '/img/network-nodes-components-sorted.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))
