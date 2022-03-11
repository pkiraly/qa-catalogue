options(tidyverse.quiet = TRUE)
library(tidyverse)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  dir <- args[1]
  frequency <- 'monthly'
} else if (length(args) == 2) {
  dir <- args[1]
  frequency <- args[2]
}

if (! frequency %in% c('weekly', 'monthly')) {
  frequency <- 'monthly'
}

input_file <- paste0(dir, '/timeline-by-category.csv')
df <- read_csv(input_file, show_col_types = FALSE)

g <- df %>% 
  #mutate(id = factor(id)) %>% 
  ggplot(aes(x = version, y = percent)) +
  geom_point() +
  geom_line(aes(color = category)) +
  theme_bw() +
  labs(
    title = 'How different MARC issues changed over time',
#    subtitle = 'based on weekly measurements',
    color = 'Location') +
  ylab('records with issues (%)') +
  xlab('timeline') +
  theme(
    axis.text.x = element_text(angle = 45, hjust = 1, vjust = 1),
    legend.title = element_text('Location')
  )

if (frequency == 'monthly') {
  g <- g + scale_x_date(date_breaks = '1 month', date_minor_breaks='1 week')
} else {
  g <- g + scale_x_date(date_breaks = '1 week', date_minor_breaks='1 day')
}

image_file <- paste0(dir, '/timeline-by-category.png')
ggsave(image_file, g, device = 'png', scale = 1,
       width = 1200, height = 600, units = "px", 
       dpi = 150) # 1300, 1500
