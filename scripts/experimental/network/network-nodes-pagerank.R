suppressPackageStartupMessages(library(tidyverse))
library(ggplot2)

#' In RStudio you can run this script in the console:
#' system("Rscript scripts/network-nodes-pagerank.R [directory-of-shelf-ready-completeness.csv]")

args = commandArgs(trailingOnly=TRUE)
tag <- '811'
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  output_dir <- args[1]
} else if (length(args) == 2) {
  output_dir <- args[1]
  tag <- args[2]
}

output_dir <- '/home/kiru/bin/marc/_output/gent/network-scores'
prefix <- paste0('network-scores-', tag, '-pagerank')

csv <- sprintf("%s/%s-stat.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df_stat <- read_csv(
  csv,
  col_types = cols(
    statistic = col_character(),
    value = col_double()
  )
)

stat <- function(name) {
  unlist(df_stat[df_stat$statistic == name, 2], use.names = F)
}

csv <- sprintf("%s/%s.csv", output_dir, prefix)
if (!file.exists(csv)) {
  stop(paste("input file", csv, "does not exist!"))
}
df <- read_csv(
  csv,
  col_types = cols(
    id = col_double(),
    score = col_double()
  )
)

df

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
  ggtitle(
    paste(
      "PageRank of",
      ifelse(tag=="all", 'all records', paste("tag", tag))
    ),
    subtitle = paste0(stat('min'), '-', stat('max'))
  ) +
  geom_hline(yintercept=stat('mean'), colour='blue') +
  geom_hline(yintercept=stat('25%'), colour='cornflowerblue') +
  geom_hline(yintercept=stat('50%'), colour='cornflowerblue') +
  geom_hline(yintercept=stat('75%'), colour='cornflowerblue') +
  ylab("PageRank score") +
  xlab(paste0("records ordered by their PageRank score (n=", count(df), ')')) +
  theme(legend.position = "none")

plot

img_path <- paste0(output_dir, '/../img/network-scores-', tag, '-pagerank-sorted.png') 
ggsave(plot, device="png", filename=img_path, width=10, height=5)
print(paste('creating', img_path))
