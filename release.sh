#!/usr/bin/bash
set -e

#https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release

#############################

ssh -t romanivanov,checkstyle@shell.sourceforge.net create

mvn -Pgpg release:prepare -B -Darguments="-DskipTests -DskipITs -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true -Dxml.skip=true"

mvn -Pgpg release:perform -Darguments='-Dcheckstyle.ant.skip=true'

#############################

ssh romanivanov,checkstyle@shell.sourceforge.net << EOF

PREV_RELEASE=6.14.1

#Swap html content
cd /home/project-web/checkstyle
mv htdocs/new-site/ .
mv htdocs htdocs-$PREV_RELEASE
mv new-site htdocs
ln -s /home/project-web/checkstyle/reports htdocs/reports

#Archiving
tar cfz htdocs-$PREV_RELEASE.tar.gz htdocs-$PREV_RELEASE/
rm -rf htdocs-$PREV_RELEASE/

EOF

##############################

$RELEASE=6.15
git checkout checkstyle-$RELEASE
mvn -Passembly clean package

$FRS_PATH=/home/frs/project/checkstyle/checkstyle/$RELEASE
ssh romanivanov,checkstyle@shell.sourceforge.net "mkdir -p $FRS_PATH"
scp target/*.jar romanivanov@frs.sourceforge.net:$FRS_PATH

git checkout master

##############################
