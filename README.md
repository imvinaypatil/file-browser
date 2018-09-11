##File-Server 

#### Installation ####

+ `git clone https://github.com/imvinaypatil/file-browser.git`
+ `mvn clean compile install` install maven dependencies
+ `mvn spring-boot:run` will start the application server on port 8080.

#### Requirements ####
+ MongoDB Server running on default port.
+ Maven Build tool

#### APIs ####

 **list files**
+ POST: 
    url: localhost:8080/files/user/list 
    path: '/root'
    
 **get raw file**
+ GET:
    url: localhost:8080/files/user/filename.txt/raw
    
**upload file**
+ POST:
    url: localhost:8080/files/user/upload
    RequestPart: 'file'
    'path': '/root'
**delete file**
+ DELETE:
    url: localhost:8080/files/user/delete/[fileid]
    
**create directory**
+ POST
    url: localhost:8080/files/user/create/directory
    name: 'dir'
    path: '/root'

######Known issues
* file doesn't get copied to local storage 
 
    