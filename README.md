# JavaRocNetNode

## Summary

This is a Demo Implementation of a RocNetNode used by the RocRail System for managing model trains.

## Intention

Implement a Queue for a train departure information system.

RocRail send the next train coming to a station with id, target text and time of departure. 
The text is then queued up and the top most is displayed. 

## Specs

http://wiki.rocrail.net/doku.php?id=rocnet:rocnet-prot-de

## Run (dev)

run the client with (you will need nodejs for this)
    
    npm start

run the server with (the server will start when you connect to the debug port) 

    mvn clean install tomee:debug 
    
## Run (Prod)

start your TomEE installation and deploy the server.war file.