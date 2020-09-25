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
prefix <- 'network-nodes-pagerank'
csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(csv)

plot <- df %>% 
  select(score) %>% 
  arrange(desc(score)) %>% 
  mutate(id = seq(n())) %>% 
  ggplot(aes(y = score, x = id)) +
  geom_line() +
  geom_point(size=0.1) +
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
  ggtitle("PageRank of the records") +
  ylab("PageRank score") +
  xlab(paste0("records ordered by their PageRank score (n=", count(df), ')')) +
  theme(legend.position = "none")

plot

img_path <- paste0(output_dir, '/img/network-nodes-pagerank-sorted.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))
