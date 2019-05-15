library(tidyverse)
library(stringr)

dir <- '/home/kiru/Dropbox/phd/cikkek/general/library/frbr'

col_names <- c(
  'related', 'id', 'Entity', 'Attribute', 'Field', 'Subfield', 'Position',
  'frbr.DataElement', 'el2', 'frbr.Entity', 'frbr.Attribute',
  'DiscoverySearch', 'DiscoveryIdentify', 'DiscoverySelect', 'DiscoveryObtain',
  'UsageRestrict', 'UsageManage', 'UsageOperate', 'UsageInterpret',
  'ManagementIdentify', 'ManagementProcess', 'ManagementSort', 'ManagementDisplay',
  'extra1', 'extra2'
)

file <- paste(dir, 'FRBR_Web_Copy.csv', sep = '/')
definitions <- read_csv(file, col_names = col_names) %>% 
  mutate(
    code = ifelse(
      Position != 'n/a' & !is.na(Position),
      ifelse(
        Subfield == 'n/a' | is.na(Subfield),
        paste0('ind', Position), 
        paste(Subfield, Position, sep="/")
      ),
      Subfield
    )
  )

all <- definitions %>% 
  mutate(key = paste(Attribute, code, sep = '$')) %>% 
  select(key, DiscoverySearch:ManagementDisplay) %>% 
  gather(
    DiscoverySearch:ManagementDisplay,
    key="frbr",
    value="is_available"
  ) %>% 
  filter(!is.na(is_available)) %>% 
  select(-is_available) %>% 
  arrange(key) %>% 
  group_by(key) %>% 
  summarise(val = paste0('[', paste(frbr, collapse = ', '), ']'))
write_delim(all, paste0(dir, '/functions-by-marc-elements.csv'), delim = ': ')

definitions %>% 
  #  count(Attribute) %>% 
  View()

atrs <- c('Attribute 2', 'Attribute 3')
grepl(" ", atrs)

definitions %>% 
  filter(grepl("....", Attribute)) %>% 
  filter(!(Attribute %in% c(
    '006Book', '006Comp/ER', '006Map', '006Music', '006Continuing',
    '006Visual', '006Mixed', '007Map', '007Electronic', '007Globe',
    '007Tact', '007Proj', '007Micro', '007NPG', '007MP', '007Kit',
    '007Music', '007RSI', '007SR', '007Text', '007Video', '007Unsp',
    '008All', '008Book', '008Comp', '008Map', '008Music',
    '008Continuing', '008Visual', '008Mixed', '008Hldgs'
  ))) %>% 
  select(Attribute)

tag <- 243
show('008Hldgs')
show <- function(tag) {
  long_form <- definitions %>% 
    filter(Attribute == tag) %>% 
    select(code, DiscoverySearch:ManagementDisplay) %>% 
    gather(
      DiscoverySearch:ManagementDisplay,
      key="frbr",
      value="is_available"
    ) %>% 
    filter(!is.na(is_available)) %>% 
    arrange(code) %>% 
    select(-is_available)

  final <- long_form %>% group_by(code) %>% 
    summarise(val = paste0(
      'functions = Arrays.asList(', 
      paste(frbr, collapse = ', '),
      ');'
      )
    )
  print(tag)
  print(final, n = 1000)
} 

tag <- '762'
definitions %>% 
  filter(Attribute == tag) %>% 
  mutate(
    no_position = (Position == 'n/a' | is.na(Position)),
    code = ifelse(Position != 'n/a' & !is.na(Position), Position, Subfield)
  ) %>% 
  select(Position, Subfield, no_position, code) %>% 
  print(n = 200)
  
  select(code, DiscoverySearch:ManagementDisplay)
  
  

  