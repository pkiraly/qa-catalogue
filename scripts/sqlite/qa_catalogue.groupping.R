library(tidyverse)

args = commandArgs(trailingOnly=TRUE)
if (length(args) == 0) {
  stop("At least one argument must be supplied (input file)", call.=FALSE)
} else if (length(args) == 1) {
  # default output dir
  OUTPUT_DIR <- args[1]
}

details <- read_csv(sprintf('%s/%s',
                            OUTPUT_DIR, 'issue-details-normalized.csv'),
                    show_col_types = FALSE)
summary <- read_csv(sprintf('%s/%s',
                            OUTPUT_DIR, 'issue-summary.csv'),
                    show_col_types = FALSE)
summary <- summary %>% 
  select(-c(type, message, url))
id_groupid <- read_csv(sprintf('%s/%s',
                            OUTPUT_DIR, 'id-groupid.csv'),
                       show_col_types = FALSE)

groupIds <- summary %>% select(groupId) %>% distinct() %>% 
  unlist(use.names = FALSE)

typesDF <- NULL
categoriesDF <- NULL
pathsDF <- NULL
len <- length(groupIds)
for (i in 1:len) {
  currentGroupId = groupIds[i]
  print(sprintf("%d/%d: %s", i, len, currentGroupId))
  idg <- id_groupid %>% filter(groupId == currentGroupId) %>% select(id)
  d <- idg %>% left_join(details, by = join_by(id), multiple = "all")
  s <- summary %>% filter(groupId == currentGroupId)
  types <- s %>% select(typeId) %>% distinct() %>% unlist(use.names = FALSE)
  for (currentType in types) {
    ids <- s %>% filter(typeId == currentType) %>% select(id) %>% 
      distinct() %>% unlist(use.names = FALSE)
    d2 <- d %>% filter(errorId %in% ids)
    instances <- d2 %>% summarise(n = sum(instances)) %>% 
      unlist(use.names = FALSE)
    records <- d2 %>% select(id) %>% distinct() %>% count() %>% 
      unlist(use.names = FALSE)
    if (is.null(typesDF)) {
      typesDF <- tibble(groupId = currentGroupId, typeId = currentType, records = records, instances = instances)
    } else {
      typesDF <- typesDF %>% add_row(groupId = currentGroupId, typeId = currentType, records = records, instances = instances)
    }

    paths <- s %>% filter(typeId == currentType) %>% select(MarcPath) %>% 
      distinct() %>% unlist(use.names = FALSE)
    for (path in paths) {
      ids <- s %>% filter(typeId == currentType & MarcPath == path) %>%
        select(id) %>% 
        distinct() %>% unlist(use.names = FALSE)
      d2 <- d %>% filter(errorId %in% ids)
      instances <- d2 %>% summarise(n = sum(instances)) %>% 
        unlist(use.names = FALSE)
      records <- d2 %>% select(id) %>% distinct() %>% count() %>% 
        unlist(use.names = FALSE)
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
    ids <- s %>% filter(categoryId == currentCategory) %>% select(id) %>% 
      distinct() %>% unlist(use.names = FALSE)
    d2 <- d %>% filter(errorId %in% ids)
    instances <- d2 %>% summarise(n = sum(instances)) %>% 
      unlist(use.names = FALSE)
    records <- d2 %>% select(id) %>% distinct() %>% count() %>% 
      unlist(use.names = FALSE)
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

# typesDF
# categoriesDF
# pathsDF

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-groupped-types.csv')
print(file)
write_csv(typesDF, file)

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-groupped-categories.csv')
print(file)
write_csv(categoriesDF, file)

file <- sprintf('%s/%s', OUTPUT_DIR, 'issue-groupped-paths.csv')
print(file)
write_csv(pathsDF, file)
