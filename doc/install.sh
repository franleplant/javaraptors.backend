
# related to Virtual guest additions
sudo apt-get update
sudo apt-get upgrade
sudo apt-get install build-essential module-assistant
sudo m-a prepare

sudo apt-get install build-essential module-assistant



#install java

sudo apt-get -y install openjdk-7-jdk openjdk-7-jre

#remove old java versions

sudo apt-get remove --purge openjdk-6-jre openjdk-6-jre-headless openjdk-6-jre-lib 


#download java ee 7 alternative http://www.oracle.com/technetwork/java/javaee/downloads/java-ee-7-sdk-update1-2025644.html


#install postgres
sudo apt-get -y install postgresql postgresql-client postgresql-contrib libpq-dev

#install git latest
sudo add-apt-repository -y ppa:git-core/ppa
sudo apt-get update
sudo apt-get -y install git
#install maven
sudo apt-get -y install maven

#unistall openoffice
sudo apt-get remove --purge openoffice.org


#install eclipse

cd /tmp

sudo wget http://eclipse.c3sl.ufpr.br/technology/epp/downloads/release/kepler/SR1/eclipse-jee-kepler-SR1-linux-gtk.tar.gz
sudo tar -xvf eclipse-jee-kepler-SR1-linux-gtk.tar.gz
sudo mv eclipse /opt/
cd /usr/bin
sudo ln -s /opt/eclipse/eclipse
cd /home/franleplant/Desktop/
sudo ln -s /opt/eclipse/eclipse



# install heroku toolbelt
cd /tmp
sudo wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh




# install glash fish
cd /tmp

sudo wget http://dlc.sun.com.edgesuite.net/glassfish/3.1.2.2/release/glassfish-3.1.2.2-unix.sh
sudo bash glassfish-3.1.2.2-unix.sh

# Install it in /opt/


#installar netbeans


sudo /tmp
sudo wget http://dlc.sun.com.edgesuite.net/netbeans/7.4/final/bundles/netbeans-7.4-javaee-linux.sh
sudo bash netbeans-7.4-javaee-linux.sh


