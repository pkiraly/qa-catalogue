suppressPackageStartupMessages(library(tidyverse))
library(httr)

getCount <- function(groupId, errorIds) {
  if (length(errorIds) == 1) {
    errors <- errorIds
  } else {
    errors <- sprintf('(%s)', paste(errorIds, collapse = ' OR '))
  }
  q <- sprintf('groupId_is:%d AND errorId_is:%s', groupId, errors)
  r <- GET(sprintf(URL, URLencode(q)))
  p <- content(r, "parsed", encoding="UTF-8")
  as.integer(p$response$numFound)
}

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file)", call.=FALSE)
} else if (length(args) == 1) {
  # default output dir
  OUTPUT_DIR <- args[1]
  SOLR_HOST <- 'http://localhost:8983'
  SOLR_CORE <- 'validation'
} else if (length(args) == 2) {
  # default output dir
  OUTPUT_DIR <- args[1]
  SOLR_CORE <- args[2]
  SOLR_HOST <- 'http://localhost:8983'
} else if (length(args) == 3) {
  # default output dir
  OUTPUT_DIR <- args[1]
  SOLR_HOST <- args[2]
  SOLR_CORE <- args[3]
}
print(sprintf('[parameters] OUTPUT_DIR: %s, Solr at %s/%s', OUTPUT_DIR, SOLR_HOST, SOLR_CORE))
URL <- paste0(SOLR_HOST, '/solr/', SOLR_CORE, '/select?q=%s&rows=0')

print('reading issue-summary.csv')
summary <- read_csv(sprintf('%s/%s', OUTPUT_DIR, 'issue-summary.csv'))
summary <- summary %>% 
  select(-c(type, message, url, records)) %>% 
  mutate(
    groupId = ifelse(groupId == 'all', 0, groupId),
    groupId = as.integer(groupId))
gc(verbose = FALSE)

groupIds <- summary %>% select(groupId) %>% distinct() %>% 
  unlist(use.names = FALSE)

typesDF <- NULL
pathsDF <- NULL
categoriesDF <- NULL
len <- length(groupIds)
print(sprintf("get statistics for %d groups", len))
for (i in 1:len) {
  currentGroupId = groupIds[i]
  if (i == 1 | i == len | i %% 25 == 0) {
    print(sprintf("%d/%d: for group id %s", i, len, currentGroupId))
  }
  s <- summary %>% filter(groupId == currentGroupId)
  types <- s %>% select(typeId) %>% distinct() %>% unlist(use.names = FALSE)
  for (currentType in types) {
    instances <- s %>% filter(typeId == currentType) %>% 
      summarise(n = sum(instances)) %>% unlist(use.names = FALSE)
    errorIds <- s %>% filter(typeId == currentType) %>% 
      select(id) %>% distinct() %>% unlist(use.names = FALSE)
    records <- getCount(currentGroupId, errorIds)
    if (is.null(typesDF)) {
      typesDF <- tibble(groupId = currentGroupId,
                        typeId = currentType,
                        record_nr = records,
                        instance_nr = instances)
    } else {
      typesDF <- typesDF %>% add_row(groupId = currentGroupId,
                                     typeId = currentType,
                                     record_nr = records,
                                     instance_nr = instances)
    }

    paths <- s %>% filter(typeId == currentType) %>% select(MarcPath) %>% 
      distinct() %>% unlist(use.names = FALSE)
    for (path in paths) {
      instances <- s %>% filter(typeId == currentType & MarcPath == path) %>%
        summarise(n = sum(instances)) %>% 
        unlist(use.names = FALSE)
      errorIds <- s %>% filter(typeId == currentType & MarcPath == path) %>%
        select(id) %>% distinct() %>% unlist(use.names = FALSE)
      records <- getCount(currentGroupId, errorIds)
      if (is.null(pathsDF)) {
        pathsDF <- tibble(groupId = currentGroupId, typeId = currentType,
                          path = path,
                          records = records, instances = instances)
      } else {
        pathsDF <- pathsDF %>% 
          add_row(groupId = currentGroupId,
                  typeId = currentType, 
                  path = path,
                  records = records, instances = instances)
      }
    }
  }
  
  categories <- s %>% select(categoryId) %>% distinct() %>% unlist(use.names = FALSE)
  for (currentCategory in categories) {
    errorIds <- s %>% filter(categoryId == currentCategory) %>% select(id) %>% 
      distinct() %>% unlist(use.names = FALSE)
    instances <- s %>% filter(categoryId == currentCategory) %>% 
      summarise(n = sum(instances)) %>% unlist(use.names = FALSE)
    records <- getCount(currentGroupId, errorIds)
    if (is.null(categoriesDF)) {
      categoriesDF <- tibble(groupId = currentGroupId,
                             categoryId = currentCategory,
                             records = records,
                             instances = instances)
    } else {
      categoriesDF <- categoriesDF %>%
        add_row(groupId = currentGroupId,
                categoryId = currentCategory,
                records = records,
                instances = instances)
    }
  }
}

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-grouped-types.csv')
print(paste("creating", file))
write_csv(typesDF, file)

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-grouped-categories.csv')
print(paste("creating", file))
write_csv(categoriesDF, file)

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-grouped-paths.csv')
print(paste("creating", file))
write_csv(pathsDF, file)
