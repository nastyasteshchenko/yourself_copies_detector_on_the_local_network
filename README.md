**To run the app:**  
mvn compile  
mvn exec:java -Dexec.mainClass="networks.lab1.Main"  
**_______________________________**  

**The app aim** is to find the copy of itself in local networks using multicast UDP.  
**The program works as follows:**  
Firstly, the user needs to enter ip of the multicast group (IPv4 or IPv6).   
Secondly, the user needs to choose the mode of working app:  
  * only send messages (mode "s")  
  * only receive messages (mode "r")  
  * both send and receive messages (mode "sr")

Then the program starts the necessary threads depending on the mode.  
The Sender is responsible for sending packets to other apps in multicast group.  
The Receiver is responsible for receiving packets from other apps in multicast group.   
To check changes in list of copies the Receiver uses CopiesTracker, which checks whether the packet came from a new copy and whether other copies of the application have disappeared.  
List of copies' ip is printed to the terminal every time changes occur.  
If the user wants to end the program, he needs to write 'end'.  
