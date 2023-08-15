suppressPackageStartupMessages(library(tidyverse))
library(grid)
library(gridExtra)

col_types <- cols(
  documenttype = col_character(),
  path = col_character(),
  package = col_character(),
  tag = col_character(),
  subfield = col_character(),
  `number-of-record` = col_double(),
  `number-of-instances` = col_double(),
  min = col_double(),
  max = col_double(),
  mean = col_double(),
  stddev = col_double(),
  histogram = col_character()
)

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

draw_plot <- function(cumulation, label, instance_count, pareto, record_count) {
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

  at1_field <- at1$rownum
  at1_percent <- at1$cumpercent
  at1_count <- total - at1_field
  at1_range <- 100 - at1_percent

  medium_count <- (at1_field - at80_count)
  medium_range <- (100 - at80_percent - at1_range)

  line_color <- "#D55E00"

  plot <- cumulation %>%
    ggplot(aes(x = cumpercent, y = cumsum)) +
    geom_line() +
    ggtitle(
      label,
      subtitle = sprintf(
        'balance: %d/%d (at %d)\n%s records, %d subfields, %s subfield occurrences',
        round(pareto_percent), 100-round(pareto_percent), pareto_field,
        format(record_count, big.mark=" ", small.interval=3),
        total,
        format(instance_count, big.mark=" ", small.interval=3)
      )) +
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

create_plot <- function(dir, field, .type) {
  df <- read_csv(sprintf('%s/%s', dir, file), col_types = col_types)
  df <- df %>%
    filter(`documenttype` == .type)
  record_count <- max(df$`number-of-record`)

  if (field == 'number-of-record') {
    total <- record_count
  } else {
    total <- sum(df$`number-of-instances`)
  }

  cumulation <- create_cumulation(df, total, field)

  pareto <- cumulation %>%
    filter(cumpercent + cumsum <= 100) %>%
    filter(rownum == max(rownum))

  label <- ifelse(.type == 'all', 'All records', .type)
  current_plot <- draw_plot(
    cumulation, label, total, pareto, record_count
  )
  return(current_plot)
}

create_full_picture <- function(dir, name, abbreviation, field) {
  p1 <- create_plot(name, field, "Books")
  p2 <- create_plot(name, field, "Computer Files")
  p3 <- create_plot(name, field, "Continuing Resources")
  p4 <- create_plot(name, field, "Maps")
  p5 <- create_plot(name, field, "Music")
  p6 <- create_plot(name, field, "Visual Materials")
  full_picture <- grid.arrange(
    p1, p2, p3, p4, p5, p6, ncol=3,
    top = textGrob(
      sprintf("Subfield usage in Catalogue %s per formats", abbreviation),
      gp = gpar(fontsize=20, font=1, lineheight=100, col="#D55E00"),
      just = "centre"
    )
  )

  ggsave(
    sprintf(
      '~/Pictures/marc/frequency-explained-by-type-%s.png',
      abbreviation
    ),
    full_picture,
    width = 15, height = 9
  )
}

create_all_pictures <- function(dir, field, file_name) {
  csv_file <- sprintf('%s/%s', dir, file_name)
  print(paste('create_all_pictures from ', csv_file))
  df <- read_csv(csv_file, col_types = col_types)
  print(df)
  df_summary <- df %>%
    group_by(documenttype) %>%
    summarise(count = n()) %>%
    mutate(machine_name = sub(' ', '-', tolower(documenttype)))

  print(df_summary)

  nr_rows <- dim(df_summary)[1]
  for (i in 1:nr_rows) {
    row <- df_summary[i,]
    print(paste('generating', row$documenttype))
    machine_name <- row$machine_name
    picture <- create_plot(dir, field, row$documenttype)

    img_dir <- sprintf('%s/img', dir)
    if (!dir.exists(img_dir)) {
      dir.create(img_dir)
    }
    ggsave(
      sprintf(
        '%s/frequency-explained-%s.png',
        img_dir, row$machine_name
      ),
      picture, width = 8, height = 8 * 0.6
    )
  }
}
