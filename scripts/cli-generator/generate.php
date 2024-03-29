<?php
define('LN', "\n");
define('URL', 'https://github.com/pkiraly/qa-catalogue');
define('TITLE', 'QA catalogue %s');

$fileName = $argv[1];
$extraOptions = [];
$existentialFlags = [];
switch ($fileName) {
  case 'validate.txt':
    $url = URL . '#validating-marc-records';
    $title = sprintf(TITLE, 'validation'); break;

  case 'classifications.txt':
    $url = URL . '#classification-analysis';
    $title = sprintf(TITLE, 'subject analysis'); break;

  case 'authorities.txt':
    $url = URL . '#authority-name-analysis';
    $title = sprintf(TITLE, 'authority name analysis'); break;

  case 'serials.txt':
    $url = URL . '#serial-score-analysis';
    $title = sprintf(TITLE, 'serials analysis'); break;

  case 'tt-completeness.txt':
    $url = URL . '#calculating-thompson-traill-completeness';
    $title = sprintf(TITLE, 'Thompson—Traill completeness analysis'); break;

  case 'shelf-ready-completeness.txt':
    $url = URL . '#shelf-ready-completeness-analysis';
    $title = sprintf(TITLE, 'shelf-ready completeness analysis'); break;

  case 'functions.txt':
    $url = URL . '#fbrb-functional-requirement-analysis';
    $title = sprintf(TITLE, 'functional analysis'); break;

  case 'shacl4bib.txt':
    $url = URL . '#shacl4bib';
    $title = sprintf(TITLE, 'custom validation'); break;

  case 'index.txt':
    $url = URL . '#indexing-marc-records-with-solr';
    $title = sprintf(TITLE, 'Indexing MARC records with Solr');
    $extraOptions = [
      (object)['short' => 'Z', 'long' => 'core',      'hasArg' => true,  'help' => 'The index name (core)',                        'var' => 'CORE'],
      (object)['short' => 'Y', 'long' => 'file-path', 'hasArg' => true,  'help' => 'File path',                                    'var' => 'FILE_PATH'],
      (object)['short' => 'X', 'long' => 'file-mask', 'hasArg' => true,  'help' => 'File mask',                                    'var' => 'FILE_MASK'],
      (object)['short' => 'W', 'long' => 'purge',     'hasArg' => false, 'help' => 'Purge index',                                  'var' => 'DO_PURGE'],
      (object)['short' => 'V', 'long' => 'status',    'hasArg' => false, 'help' => 'Show the status of index(es)',                 'var' => 'DO_STATUS'],
      (object)['short' => 'U', 'long' => 'no-delete', 'hasArg' => false, 'help' => 'Do not delete index before starting indexing', 'var' => 'SKIP_DELETE']
    ];
    $existentialFlags = ['solrUrl'];
    break;

  case 'completeness.txt':
  default:
    $url = URL . '#calculating-data-element-completeness';
    $title = sprintf(TITLE, 'completeness analysis'); break;
}

$maxLong = 0;
$index = (object)['longs' => [], 'shorts' => []];
$options = readOptions('common.txt', $index);
$options = array_merge($options, readOptions($fileName, $index));
if (!empty($extraOptions))
  $options = array_merge($options, $extraOptions);

createHelp($options);
echo LN;
createGetopt($options);
echo LN;
createParser($options);
echo LN;
createCommandArguments($options);

function createHelp($options) {
  global $maxLong, $title, $url;
  $shorts = [];
  $longs = [];
  foreach ($options as $option) {
    $shorts[] = sprintf('[-%s|--%s%s]', $option->short, $option->long, ($option->hasArg ? ' <arg>' : ''));
    $longs[] = sprintf(' -%s, --%s %s %s%s', $option->short, $option->long, ($option->hasArg ? '<arg>' : ''), str_pad(' ', $maxLong + ($option->hasArg ? 0 : 5) - strlen($option->long)+1), $option->help);
  }
  $shorts = join(' ', $shorts) . ' <files>';
  $longs = join(LN, $longs);
  print <<<END
ME=\$(basename \$0)

show_usage() { # display help message
  cat <<EOF
$title

usage:
 \${ME} [options] <files>

options:
$longs

more info: $url

EOF
  exit 1
}

if [ \$# -eq 0 ]; then
  show_usage
fi

END;
}

function createCommandArguments($options) {
  echo 'echo "${ME} $PARAMS $@"', LN;
}

function createParser($options) {
  global $maxLong;

  $variables = [];
  $lines = [];
  foreach ($options as $option) {
    $line = sprintf('    -%s|--%s) ', $option->short, $option->long);
    $line .= str_pad(' ', $maxLong + 1 - strlen($option->long));
    if ($option->hasArg) {
      if (isset($option->var)) {
        $variables[] = sprintf('%s=0', $option->var);
        $line .= sprintf('%s="$2" ;%sshift 2 ;;', $option->var, str_pad(' ', $maxLong + 18 - strlen($option->var)));
      } else {
        $line .= sprintf('PARAMS="$PARAMS --%s $2" ;%sshift 2 ;;', $option->long, str_pad(' ', $maxLong + 1 - strlen($option->long)));
      }
    } else {
      if (isset($option->var)) {
        $variables[] = sprintf('%s=0', $option->var);
        $line .= sprintf('%s=1 ;%sshift   ;;', $option->var,
          str_pad(' ', $maxLong + 21 - strlen($option->var))
        );
      } else {
        $line .= sprintf(
          'PARAMS="$PARAMS --%s" ;%s%s shift   ;;',
          $option->long,
          ($option->long == 'help' ? ' HELP=1; ' : ''),
          str_pad(' ', $maxLong + ($option->long == 'help' ? -6 : 3) - strlen($option->long))
        );
      }
    }
    $lines[] = $line;
  }
  $allVariables = join(LN, $variables);
  $cases = join(LN, $lines);

  print <<<END
$allVariables
PARAMS=""
HELP=0
while true ; do
  case "\$1" in
$cases
    --) shift ; break ;;
    *) echo "Internal error!: \$1" ; exit 1 ;;
  esac
done

if [[ \$HELP -eq 1 ]]; then
  show_usage
fi

END;

}

function createGetopt($options) {
  $short = [];
  $long = [];
  foreach ($options as $option) {
    $short[] = $option->short . ($option->hasArg ? ':' : '');
    $long[] = $option->long . ($option->hasArg ? ':' : '');
  }
  $shortOpts = join('', $short);
  $longOpts = join(',', $long);

  print <<<END
SHORT_OPTIONS="$shortOpts"
LONG_OPTIONS="$longOpts"

GETOPT=$(getopt \
  -o \${SHORT_OPTIONS} \
  --long \${LONG_OPTIONS} \
  -n \${ME} -- "\$@")
eval set -- "\${GETOPT}"

END;
}

function readOptions($fileName, $index) {
  global $maxLong;
  $data = [];
  $handle = fopen($fileName, "r");
  if ($handle) {
    while (($line = fgets($handle)) !== false) {
      if (preg_match('/\s*options.addOption\("(.)", "([^"]+)", (true|false), "([^"]+)"\);$/', $line, $matches)) { //
        $data[] = (object)[
          'short' => $matches[1],
          'long' => $matches[2],
          'hasArg' => $matches[3] == 'true',
          'help' => $matches[4]
        ];
        if ($maxLong < strlen($matches[2]))
          $maxLong = strlen($matches[2]);

        if (in_array($matches[1], $index->shorts)) {
          error_log('repeated short: ' . $matches[1] . ' -- ' . $line);
        }
        $index->shorts[] = $matches[1];

        if (in_array($matches[2], $index->longs)) {
          error_log('repeated long: ' . $matches[2] . ' -- ' . $line);
        }
        $index->longs[] = $matches[2];

      } else {
        error_log('line does not fit to the pattern: ', $line);
      }
    }
    fclose($handle);
  }
  return $data;
}
