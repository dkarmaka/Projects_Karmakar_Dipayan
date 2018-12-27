/*
This is the Server program. Refer to Readme file for the detailed process 
*/

// List of header files

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>
#include <netdb.h>
#include <unistd.h>
#include <string.h>
#include <iostream>

#define PORT 19028	//The port number for the TCP communication is defined as 19028

using namespace std;

/*
Function to count number of '1's in binary form of input number
*/
int countones(int num){
	if(num==0){
		return 0;
	}
	return(num%2 + countones(num/2));
}

/*
Function to display error and stop execution in case of an error
reference: https://www.youtube.com/watch?v=V6CohFrRNTo&t=52s
*/
void error(char *msg){
	perror(msg);		//Display error
	exit(0);		//Stop program execution
}

int main()
{

	int server_sock, client_sock, n, count, data;
	struct sockaddr_in server;
	char buffer[256];



	server_sock= socket(AF_INET, SOCK_STREAM, 0);	//Creating a TCP server socket
	if(server_sock<0){				//Checking if there is a socket creation error
		error("Socket Creation error");
	}



	server.sin_family= AF_INET;		//Defining server domain of the communication
	server.sin_port= htons(PORT);		//Setting the server port number for communication
	server.sin_addr.s_addr= htonl(INADDR_LOOPBACK);		//Using Loopback IP address 127.0.0.1 for server

	n= bind(server_sock, (struct sockaddr *)&server, sizeof(server));	//Bind server socket with server address
	if(n==-1){		//Checking if there is a binding error
        error("Binding Error");
	}



	n= listen(server_sock,10);	//Start listening for a client connection
	if(n == -1){			//Checking if there is a listening error
		error("Listening error");
	}



	while(1){	//Infinite loop to keep server active for all client requests

		printf("Server is running and ready to receive connections on port %d…\n",PORT);



		client_sock = accept(server_sock,NULL, NULL);		//Accept a client cennection request
		if (client_sock < 0){					//Checking if there is a accepting error
			error("Client Accepting error");
		}



		n = recv(client_sock, &buffer, sizeof(buffer), 0);	//Receive the data from client
		if (n < 0){						//Checking error in receivng messages
			error("ERROR reading from socket");
		}
		data= atoi(buffer);		//Convert client data from string to long
		printf("Received number %d from some client.\nComputing the number of ‘1’s in binary representation…\n",data);


		count= countones(data);		//Counting number of ones in binary form of data using countones functions
		printf("The number of ‘1’s in the binary representation of %d is: %d\n", data, count);



		memset(buffer, 0 , sizeof(buffer));	//Reseting buffer for reuse
		sprintf(buffer, "%d", count);		//Convert output result to string for transmission
		n = send(client_sock, buffer, sizeof(buffer), 0);	//Send results to the client
		if (n < 0){				//Checking error in sending messages
			error("ERROR writing to socket");
		}
		printf("Sent result back to that client.\n\n");
		close(client_sock);		//Close the server-client socket and wait for a new one
	}		//Close loop for attending all clients



return 0;
    
}

