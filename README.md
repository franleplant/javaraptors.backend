Javaraptors Backend 
========================

Library books, copys, authors, editorials, location and lends managment system.

Software Engineering curse academic project



### How to develop?

* Visit the wiki and [set your local environment](https://github.com/franleplant/javaraptors.backend/wiki/Setting-backend-Development-environment)
* Download the code

**Clone the repo**

    git clone https://github.com/franleplant/javaraptors.backend.git
    
    
    
    
**Download the frontend submodule**



    cd javaraptors.backend
    git submodule init
    git submodule update
    
    
    
    
    
    
    
* Import your project into Eclipse or Jboss IDE, follow this [instructions](https://github.com/franleplant/javaraptors.backend/wiki/Import-Maven-project-into-Eclipse).

* Change the Front End Root Route  and you are good to go. Follow this [instructions](https://github.com/franleplant/javaraptors.backend/wiki/Front-End-integration)


### Deploy

    cd /pathr/to/project/root
    git add remote openshift ssh://52a8ed94e0b8cd488900002a@jbosseap-javaraptors.rhcloud.com/~/git/jbosseap.git/ 
    //do some work
    git add .
    git commit -m "some changes"
    //Save
    git push origin master
    //Deploy!
    git push openshift master


The OpenShift `jbosseap` cartridge documentation can be found at:

https://github.com/openshift/origin-server/tree/master/cartridges/openshift-origin-cartridge-jbosseap/README.md

