#!/bin/bash
echo Your container args are: "$@"
# It looks like there are some tunings i cannot use with opendjk8!
# ORIGINAL java -jar -Xmx128m --XX:+IdleTuningGcOnIdle -Xtune:virtualized -Xscmx128m -Xscmaxaot100m -Xshareclasses:cacheDir=/opt/shareclasses /opt/app/app.jar /opt/app/tipos.txt /opt/app/tarjetas.txt

java -jar -Xmx128m /opt/app/app.jar 0.0.0.0:9000 /opt/app/ferrari.jpg
