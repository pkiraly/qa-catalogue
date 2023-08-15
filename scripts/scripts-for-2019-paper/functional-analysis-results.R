library(tidyverse)

dir <- '~/temp/marc21'
output_dir <- '/media/kiru/Elements/projects/marc21/_reports/'

infobox <- tribble(
  ~n, ~name, ~abbreviation, ~record_number,
  #--|--|----
  1, 'bayern', 'bay', 27291431,
  2, 'bzbw', 'bzb', 23058913,
  3, 'cerl', 'crl', 6026652,
  4, 'columbia', 'col', 6735585,
  5, 'dnb', 'dnb', 16734811,
  6, 'gent', 'gnt', 1764210,
  7, 'harvard', 'har', 13711915,
  8, 'loc', 'loc', 10091977,
  9, 'michigan', 'mic', 1315695,
  10, 'nfi', 'nfi',1007884,
  11, 'rism', 'ris', 1284488,
  12, 'sfpl', 'sfp', 931048,
  13, 'stanford', 'stn', 9443083,
  14, 'szte', 'szt', 1216697,
  15, 'tib', 'tib', 3519186,
  16, 'toronto', 'tor', 2504565
)

i <- 1
for (i in infobox$n) {
  info <- infobox %>% filter(n == i)
  catalog <- info$name
  print(catalog)
  record_number <- info$record_number
  file <- paste(dir, catalog, 'functional-analysis.csv', sep = '/')
  elements <- read_csv(file)
  
  elements

  elements <- elements %>% 
    mutate(
      score = score * 100,
      catalog = info$abbreviation
    ) %>% 
    select(frbrfunction=`frbr-function`, score, catalog) %>% 
    mutate(
      frbrfunction = ifelse(
        frbrfunction == 'UseIdentify',
        'ManagementIdentify',
        frbrfunction
      )
    )
  if (i == 1) {
    all <- elements
  } else {
    all <- union(all, elements)
  }
}

all %>% 
  distinct(frbrfunction) %>% unlist(use.names = FALSE)

function_levels <- c(
  "DiscoverySearch",   "DiscoveryIdentify", "DiscoverySelect",
  "DiscoveryObtain",   "UseRestrict",       "UseManage",
  "UseOperate",        "UseInterpret",      "ManagementDisIdentify",
  "ManagementProcess", "ManagementSort",    "ManagementDisplay"
)

function_levels2 <- c(
  "Resource Discovery / Search", "Resource Discovery / Identify",
  "Resource Discovery / Select", "Resource Discovery / Obtain",
  "Resource Use / Restrict", "Resource Use / Manage",
  "Resource Use / Operate", "Resource Use / Interpret",
  "Data Management / Identify", "Data Management / Process",
  "Data Management / Sort", "Data Management / Display"
)

with_factors <- all %>% 
  mutate(
    frbrfunction = gsub(
                     '(Management)', 'Data \\1 / ',
                     gsub(
                       '(Use)', 'Resource \\1 / ',
                       gsub(
                         '(Discovery)', 'Resource \\1 / ',
                         frbrfunction,
                         perl = TRUE),
                       perl = TRUE),
                     perl = TRUE)
  ) %>% 
  mutate(
    frbrfunction = factor(frbrfunction, level = function_levels2)
  )
with_factors
  
all$frbrfunction <- factor(all$frbrfunction, level = function_levels)
View(all)

all$frbrfunction <- reorder(all$frbrfunction)

with_factors %>% 
  ggplot() +
  geom_point(aes(x = catalog, y = score, color = catalog), stat="identity") +
  facet_wrap(~frbrfunction) +
  theme(
    axis.text.x = element_text(
      angle = 60,
      hjust = 1,
      vjust = .8
    )
  ) +
  ggtitle('How do catalogs support FRBR functions?',
          subtitle = '16 catalogues, 126 million MARC21 records, grouped by functions') +
  xlab('libraries') +
  ggsave(
    '/home/kiru/Pictures/functional-analysis-of-catalogs-by-catalogs.png',
    width = 10, height = 6, units = 'in', dpi = 150, scale = 1
  )

with_factors %>% 
  ggplot() +
  geom_point(aes(x = frbrfunction, y = score, color = frbrfunction), stat="identity") +
  facet_wrap(~catalog) +
  theme(
    axis.text.x = element_text(
      angle = 60,
      hjust = 1,
      vjust = 1
    )
  ) +
  ggtitle('How do catalogs support FRBR functions?',
          subtitle = '16 catalogues, 126 million MARC21 records, grouped by libraries') +
  xlab('FRBR functions') +
  ggsave(
    '/home/kiru/Pictures/functional-analysis-of-catalogs-by-functions.png',
    width = 10, height = 6, units = 'in', dpi = 150, scale = 1
  )


result <- all %>% 
  mutate(
    score = sprintf("%.1f", score),
  ) %>% 
  spread(key = catalog, value = score)

write_csv(result, paste0(output_dir, 'functional-analysis.csv'))
