library(tidyverse)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file).n", call.=FALSE)
} else if (length(args) == 1) {
  # default output file
  dir <- args[1]
}

input_file <- paste0(dir, '/timeline-by-category.csv')
df <- read_csv(input_file)
df

p <- df %>% 
  #mutate(id = factor(id)) %>% 
  ggplot(aes(x = version, y = percent)) +
  geom_line(aes(color = category)) +
  theme_bw() +
  labs(
    title = 'How different MARC errors occured over time in a catalogue',
    subtitle = 'based on weekly measurements',
    color = 'Location') +
  ylab('Percentage of records with issues') +
  xlab('timeline') +
  theme(
    axis.text.x = element_text(angle = 45, hjust = 1, vjust = 1),
    legend.title = element_text('Location')
  ) +
  # scale_x_date(date_breaks = '1 month', date_minor_breaks='1 week')
  scale_x_date(date_breaks = '1 week', date_minor_breaks='1 day')

image_file <- paste0(dir, '/timeline-by-category.png')
ggsave(image_file, g, device = 'png', scale = 1,
       # width = .width, height = .height, units = "px", 
       dpi = 150) # 1300, 1500
