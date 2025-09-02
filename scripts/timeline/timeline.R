options(tidyverse.quiet = TRUE)
suppressPackageStartupMessages(library(tidyverse))

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
print(paste('processing', input_file))
df <- read_csv(input_file)
.min_version <- df %>% select(version) %>% filter(version == min(version)) %>% distinct() %>% unlist(use.names = FALSE)

print('set levels')
.levels <- df %>% 
  filter(version == .min_version) %>% 
  arrange(desc(percent)) %>% 
  select(category) %>% 
  unlist(use.names = FALSE)

print('create g')
g <- df %>% 
  mutate(category = factor(category, levels = .levels)) %>% 
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

input_file <- paste0(dir, '/timeline-by-type.csv')
df <- read_csv(input_file)

categories <- df %>% select(category) %>% distinct() %>% unlist(use.names = FALSE)

for (.cat in categories) {

  .levels <- df %>% 
    filter(category == .cat & version == .min_version) %>% 
    arrange(desc(percent)) %>% 
    select(type) %>% 
    unlist(use.names = FALSE)

  g <- df %>%
    filter(category == .cat) %>% 
    mutate(type = factor(type, levels = .levels)) %>% 
    #mutate(id = factor(id)) %>% 
    ggplot(aes(x = version, y = percent)) +
    geom_point() +
    geom_line(aes(color = type)) +
    theme_bw() +
    labs(
      title = sprintf('How different MARC issues changed over time - on %s level', .cat),
      # subtitle = .cat,
      color = 'issue type') +
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

  image_file <- sprintf('%s/timeline-by-type-%s.png', dir, str_replace(.cat, ' ', '-'))
  ggsave(image_file, g, device = 'png', scale = 1,
         width = 1200, height = 600, units = "px", 
         dpi = 150) # 1300, 1500
}
