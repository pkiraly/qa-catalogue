<?php
define('LN', "\n");
define('URL', 'https://github.com/pkiraly/qa-catalogue');
define('TITLE', 'QA catalogue %s');

$fileName = $argv[1];
switch ($fileName) {
  case 'validate.txt':
    $url = URL . '#validating-marc-records';
    $title = sprintf(TITLE, 'validation');
    break;

  case 'completeness.txt':
  default:
    $url = URL . '#calculating-data-element-completeness';
    $title = sprintf(TITLE, 'completeness analysis');
    break;
}

$maxLong = 0;
$options = readOptions($fileName);

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

  $lines = [];
  foreach ($options as $option) {
    $line = sprintf('    -%s|--%s) ', $option->short, $option->long);
    $line .= str_pad(' ', $maxLong + 1 - strlen($option->long));
    if ($option->hasArg) {
      $line .= sprintf('PARAMS="$PARAMS --%s $2" ;%sshift 2 ;;', $option->long, str_pad(' ', $maxLong + 1 - strlen($option->long)));
    } else {
      $line .= sprintf(
        'PARAMS="$PARAMS --%s" ;%s%s shift   ;;',
        $option->long,
        ($option->long == 'help' ? ' HELP=1; ' : ''),
        str_pad(' ', $maxLong + ($option->long == 'help' ? -6 : 3) - strlen($option->long))
      );
    }
    $lines[] = $line;
  }
  $cases = join(LN, $lines);

  print <<<END
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

function readOptions($fileName) {
  global $maxLong;
  $data = [];
  $handle = fopen($fileName, "r");
  $shorts = $longs = [];
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

        if (in_array($matches[1], $shorts)) {
          echo 'repeated short: ' . $matches[1] . ' -- ' . $line;
        }
        $shorts[] = $matches[1];

        if (in_array($matches[2], $longs)) {
          echo 'repeated long: ' . $matches[2] . ' -- ' . $line;
        }
        $longs[] = $matches[2];

      } else {
        echo 'line does not fit: ', $line;
      }
    }
    fclose($handle);
  }
  return $data;
}

