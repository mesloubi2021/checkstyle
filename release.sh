#!/usr/bin/bash
set -e

#https://github.com/checkstyle/checkstyle/wiki/How-to-make-a-release

#############################

echo "exit" | ssh -t romanivanov,checkstyle@shell.sourceforge.net create

# Version bump in pom.xml - https://github.com/checkstyle/checkstyle/commits/master
mvn -Pgpg release:prepare -B -Darguments="-DskipTests -DskipITs -Dpmd.skip=true -Dfindbugs.skip=true -Dcobertura.skip=true -Dcheckstyle.ant.skip=true -Dcheckstyle.skip=true -Dxml.skip=true"

# deployment of jars to maven central and publication of site to http://checkstyle.sourceforge.net/new-site/
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

RELEASE=6.15
git checkout checkstyle-$RELEASE

#Generate all binaries
mvn -Passembly clean package

#Publish them to sourceforce
FRS_PATH=/home/frs/project/checkstyle/checkstyle/$RELEASE
ssh romanivanov,checkstyle@shell.sourceforge.net "mkdir -p $FRS_PATH"
scp target/*.jar romanivanov@frs.sourceforge.net:$FRS_PATH
scp target/*.tar.gz romanivanov@frs.sourceforge.net:$FRS_PATH
scp target/*.zip romanivanov@frs.sourceforge.net:$FRS_PATH

git checkout master

##############################
