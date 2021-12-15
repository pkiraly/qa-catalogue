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

for (i in infobox$n) {
  info <- infobox %>% filter(n == i)
  catalog <- info$name
  print(catalog)
  record_number <- info$record_number
  file <- paste(dir, catalog, 'packages.csv', sep = '/')

  if (file.exists(file)) {
    print(file)
    elements <- read_csv(file)
    nulls <- elements %>% 
      filter(label == "null" & name != 'holdings') %>%
      summarise(sum = sum(count)) %>% 
      unlist(use.names = FALSE)
    elements <- add_row(
      elements, 
      name = 'other',
      label = 'other',
      count = nulls
    )

    elements <- elements %>% 
      filter(label != "null" | name == 'holdings') %>%
      mutate(
        ratio = sprintf("%.1f", (count * 100 / record_number)),
        catalog = info$abbreviation
      ) %>% 
      select(-c(count))

    # print(elements)
    if (i == 1) {
      base <- elements
    } else {
      base <- union(base, elements)
    }
  }
}

group_levels1 <- c(
  'tags01x', 'tags1xx', 'tags20x', 'tags25x', 
  'tags3xx', 'tags4xx', 'tags5xx', 'tags6xx',
  'tags70x', 'tags76x', 'tags80x', 'tags84x',
  'holdings', 'other'
)

group_levels <- c(
  '01x', '1xx', '20x', '25x', 
  '3xx', '4xx', '5xx', '6xx',
  '70x', '76x', '80x', '84x',
  'hld', 'oth'
)

base2 <- base %>% 
  mutate(
    ratio = as.numeric(ratio),
    name = factor(
      gsub('other', 'oth', 
           gsub('holdings', 'hld', 
                gsub('tags', '', name)
                )
           ),
      group_levels
    )
  )

base2 %>% count(name)

base2$group <- as.numeric(base2$name)

ggplot(base2, aes(name,
                  ratio,
                  group = 1,
                  colour = catalog)) +
  geom_line(na.rm = TRUE)

base2 %>% 
  ggplot(
    aes(
      name,
      ratio,
      group = 1)) +
  geom_line() +
  facet_wrap(vars(catalog)) +
  theme(
    axis.text.x = element_text(
      angle = 60,
      hjust = 1,
      vjust = 1
    )
  ) +
  ggtitle('Availability of field groups') +
  xlab('field groups') +
  ylab('proportion of records')

result <- base %>%
  spread(key = catalog, value = ratio) %>% 
  select(-name) %>% 
  replace_na(list(
    bay = '--',
    bzb = '--',
    crl = '--',
    col = '--',
    dnb = '--',
    gnt = '--',
    har = '--',
    loc = '--',
    mic = '--',
    nfi = '--',
    ris = '--',
    sfp = '--',
    stn = '--',
    szt = '--',
    tib = '--',
    tor = '--'
  ))

write_csv(result, paste0(output_dir, 'completeness.csv'))

result  
