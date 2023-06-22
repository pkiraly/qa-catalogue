suppressPackageStartupMessages(library(tidyverse))

df <- read_csv('~/bin/marc/_output/szte/network.csv')
dim(df)

by_concepts <- df %>% 
  count(concept)
dim(by_concepts)

multi <- by_concepts %>% 
  filter(n > 1)
multi

df2 <- tribble(
  ~a, ~b
)
df2

for (c in multi$concept) {
  nodes <- df %>% 
    filter(concept == c) %>% 
    select(id) %>% 
    unlist(use.names = FALSE)
  len <- length(nodes)
  print(paste('len', len))
  for (i in 1:(len - 1)) {
    for (j in (i+1):len) {
      add_row(df2, a = nodes[i], b = nodes[j])
    }
  }
}

by_concepts %>% 
  mutate(k = log1p(n)) %>% 
  select(k) %>% 
  arrange(desc(k))


by_concepts %>% 
  mutate(k = log1p(n)) %>% 
  ggplot(aes(x = k)) +
  geom_histogram() +
  scale_y_log10()

df %>% 
  filter(concept == 176424750)
