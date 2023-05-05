suppressPackageStartupMessages(library(tidyverse))
library(grid)
library(gridExtra)

dir <- '~/bin/marc/_output/'
file <- 'marc-elements.csv'

names <- tribble(
  ~name, ~label,
  'gent', 'Gent',
  'szte', 'SzTE',
  'mtak', 'MTAK',
  'nfi',  'NFI'
)

number-of-record
create_cumulation <- function(df, total, field) {
  count <- nrow(df)
  col <- rlang::sym(field)
  
  # sumtotal <- sum(df$`number-of-instances`)
  
  cumulation <- df %>% 
    select(!!col) %>% 
    arrange(desc(!!col)) %>% 
    mutate(
      percent = !!col / total,
      rownum = row_number(),
      cumpercent = rownum * (1 / count) * 100,
      cumsum = cumsum(percent) * 100
    ) %>% 
    select(rownum, cumpercent, cumsum)
}

draw_plot <- function(cumulation, label, instance_count, pareto) {
  pareto_field <- pareto$rownum
  pareto_percent <- ceiling(pareto$cumpercent * 100) / 100
  pareto_count <- pareto$cumsum
  
  total <- nrow(cumulation)
  at80 <- cumulation %>% 
    filter(floor(cumsum) <= 80) %>% 
    filter(cumsum == max(cumsum))

  at80_count <- at80$rownum
  at80_percent <- at80$cumpercent
  cumsum <- at80$cumsum

  at1 <- cumulation %>% 
    filter(floor(cumsum) >= 99) %>% 
    filter(cumsum == min(cumsum))
  print(at1)

  at1_field <- at1$rownum
  at1_percent <- at1$cumpercent
  at1_count <- total - at1_field
  at1_range <- 100 - at1_percent
  print(sprintf("%d (%.2f%%)", at1_count, at1_range))
  
  medium_count <- (at1_field - at80_count)
  medium_range <- (100 - at80_percent - at1_range)
  print(paste(total, at80_count, at1_field))
  print(medium_count)
  
  line_color <- "#D55E00"
  
  plot <- cumulation %>% 
    ggplot(aes(x = cumpercent, y = cumsum)) +
    # geom_hline(yintercept = 80, color = 'red') +
    # geom_vline(xintercept = at80_percent, color = 'cornflowerblue') +
    # geom_vline(xintercept = pareto_percent, color = 'cornflowerblue') +
    # geom_vline(xintercept = at1_percent, color = 'cornflowerblue') +
    geom_line() +
    ggtitle(
      sprintf(
        "%s; balance: %d/%d (at %d)",
        label, round(pareto_percent), 100-round(pareto_percent), pareto_field),
      # sprintf('Data element frequency in %s', label),
      subtitle = sprintf(
        'individual subfields: %d, # of occurrence: %s',
        total, format(instance_count, big.mark=" ", small.interval=3)
      )) +
      # subtitle = sprintf(
      #  '%d elements of %d (%.2f%%) makes %.2f%% of all occurances\n%d elements (%.2f%%) makes together only 1%%',
      #  at80_count, total, at80_percent, cumsum, at1_count, at1_range
      # )) +
    xlab('subfields (%)') +
    ylab('cumulative occurrence (%)') +
    scale_y_continuous(
      breaks = c(0, 20, 40, 60, 80, 100),
    ) +
    annotate("segment", x = 0, xend = at80_percent, y = 10, yend = 10,
             colour = line_color) +
    annotate("text", x = at80_percent + 1, y = 10, hjust = 0, colour = line_color,
             label = sprintf("%d subfields (%.2f%%) → top 80%% occurrence", at80_count, at80_percent)) + 

    annotate("segment", x = at80_percent, xend = at1_percent, y = 20, yend = 20,
             colour = line_color) +
    annotate("text", x = at1_percent + 1, y = 20, hjust = 0, colour = line_color,
             label = sprintf("%d subfields (%.2f%%) → middle 19%% occurrence", medium_count, medium_range)) +
    
    annotate("segment", x = at1_percent, xend = 100, y = 35, yend = 35,
             colour = line_color) +
    annotate("text", x = at1_percent + 3, y = 30, hjust = 0, colour = line_color,
           label = sprintf("%d subfields (%.2f%%) → lower 1%% occurrence", at1_count, at1_range)) +

    annotate("segment", x = 0, xend = at80_percent, y = 80, yend = 80, color = "cornflowerblue") +
    annotate("segment", x = at80_percent, xend = at80_percent, y = 0, yend = 80, color = "cornflowerblue") +
    annotate("segment", x = 0, xend = at1_percent, y = 99, yend = 99, color = "cornflowerblue") +
    annotate("segment", x = at1_percent, xend = at1_percent, y = 0, yend = 99, color = "cornflowerblue") +
    
    annotate("segment", x = pareto_percent, xend = pareto_percent + 5,
           y = pareto_count, yend = pareto_count -5, color = line_color) +
    annotate("point", x = pareto_percent, y = pareto_count, color = line_color) +
    annotate("text", x = pareto_percent + 5, y = pareto_count - 5,
             hjust = 0, vjust = 1, colour = line_color,
             label = sprintf(
               "balancing point:\n%d subfields (%.2f%%) → %.2f%% occurrence\n%d subfields (%.2f%%) → %.2f%% occurrence",
               pareto_field, pareto_percent, 100 - pareto_percent,
               total - pareto_field, 100 - pareto_percent, pareto_percent
               ))
    
  return(plot)
}

create_plot <- function(name, label, count, field) {
  df <- read_csv(sprintf('%s/%s/%s', dir, name, file), show_col_types = FALSE)
  if (field == 'number-of-record') {
    total <- count
  } else {
    total <- sum(df$`number-of-instances`)
  }
  
  cumulation <- create_cumulation(df, total, field)
  View(cumulation)

  pareto <- cumulation %>% 
    filter(cumpercent + cumsum <= 100) %>% 
    filter(rownum == max(rownum))

  current_plot <- draw_plot(
    cumulation, label, total, pareto
  )
  return(current_plot)
}

df <- read_csv(sprintf('%s/%s/%s', dir, 'szte', file), show_col_types = FALSE)
field <- 'number-of-record'  # number-of-instances
count <- 1282172
if (field == 'number-of-record') {
  total <- count
} else {
  total <- sum(df$`number-of-instances`)
}
total
count <- nrow(df)
col <- rlang::sym(field)

cumulation <- df %>% 
  select(!!col) %>% 
  arrange(desc(!!col)) %>% 
  mutate(
    percent = !!col / total,
    rownum = row_number(),
    cumpercent = rownum * (1 / count) * 100
  )
View(cumulation)
cumulation %>% 
  # filter(!!col > 100) %>% 
  ggplot(aes(rownum, !!col)) +
  geom_line() +
  scale_y_continuous(trans="log2") + # breaks = scales::pretty_breaks(n = 10)
  scale_x_continuous(trans="log2")   # breaks = scales::pretty_breaks(n = 10)

cumulation <- df %>% 
  select(!!col) %>% 
  arrange(desc(!!col)) %>% 
  mutate(
    percent = !!col / total,
    rownum = row_number(),
    cumpercent = rownum * (1 / count) * 100,
    cumsum = cumsum(percent) * 100
  ) %>% 
  select(rownum, cumpercent, cumsum)
cumulation

p1 <- create_plot('szte', 'Catalogue A', 1282172, field) # 'University of Szeged')
p2 <- create_plot('mtak', 'Catalogue B', 1052288, field) # 'Hungarian Academy of Science')
p3 <- create_plot('gent', 'Catalogue C', 1039107, field) # University of Gent')
p4 <- create_plot('nfi',  'Catalogue D', 1764210, field) # 'Finnish National Library')
full_picture <- grid.arrange(
  p1, p2, p3, p4, ncol=2, 
  top = textGrob(
    "Subfield usage in 4 library catalogues",
    gp = gpar(fontsize=20, font=1, lineheight=100, col="#D55E00"),
    just = "right"
  )
)

ggsave('~/Pictures/marc/frequency-explained.png', full_picture)
