You can follow this blog to make this application run on OpenShift https://openshift.redhat.com/community/blogs/polyglot-persistence-spring-applications-on-openshift-part-1

# For Developers in Hurry

## Create an Application 

rhc app create -a notebook -t jbossas-7 -l <openshift_login_email> -d

## Adding MongoDB and RockMongo Client Cartridge

```
rhc cartridge add -a notebook -c mongodb-2.2
rhc cartridge add -a notebook -c rockmongo-1.1
```

## Pulling code from github and pushing to OpenShift

After you have created the application using rhc create app command and added MongoDB and RockMongoDB client cartridge using rhc app cartridge add command you have to checkout the code from my github. To do that follow the steps mentioned below.

```
git remote add notebook -m master git://github.com/shekhargulati/notebook-part1.git
 
git pull -s recursive -X theirs notebook master
 
git push
```
