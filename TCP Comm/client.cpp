/*
This is the Client program. Refer to Readme file for the detailed process 
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
Function to display error and stop execution in case of an error
reference: https://www.youtube.com/watch?v=V6CohFrRNTo&t=52s
*/
void error(char *msg)
{
perror(msg);		//Display error
exit(0);		//Stop program execution
}

int main()
{

int client_socket, n, flag;
struct sockaddr_in server_address;
char buffer[256];
long long int a;



client_socket = socket(AF_INET, SOCK_STREAM, 0);	//Creating a TCP client socket
if (client_socket <0){					//Checking if there is a socket creation error
	error("Error opening SOCKET");
}



while(1){	//An infinite loop to keep on asking for input until the user provides a valid input

	flag=0;		//Flag to identify if any issue has been found in input
	memset(buffer, 0 , sizeof(buffer));	//Clear buffer memory to store a new input
	printf("\nPlease provide an integer in the range [0, 2147483647]:\n");

	fgets(buffer, 256, stdin);	//Reading user input to buffer

	for(int i=0; buffer[i]!=10; i++){	//Loop to check if there is any character other than integers in the input
		if(buffer[i]<'0' || buffer[i]>'9'){
			flag=1;
			break;
		}
	}

	if(buffer[11]!=0){		//Condition to check if the user has entered a number of more than 10 digits
		flag=1;
	}

	if(flag==0){
		a= atoll(buffer);	//Converting user input to lonf long int

		if(a<0 || a>2147483647){	//Condition to check if the user input is with the specified range
			flag=1;
		}
	}

	if(flag==0){		//If all conditions are satisfied, break the infinite loop and use the input
		break;
	}
	else{			//If any condition fails, ask user to try again
		printf("Invalid Data Entered. Try Again!!\n");
	}

}



server_address.sin_family= AF_INET;		//Defining server domain of the communication
server_address.sin_port = htons(PORT);		//Setting the server port number for communication
server_address.sin_addr.s_addr = htonl(INADDR_LOOPBACK);	//Using Loopback IP address 127.0.0.1 for server



n = connect(client_socket, (struct sockaddr*)&server_address, sizeof(server_address));		//Initiate connection with server
if (n < 0)	//Check error in initiating connection
{
	error("ERROR connecting");
}



n = send(client_socket, buffer, sizeof(buffer), 0);	//Send the Data to the server
     if (n < 0)		//Check error in sending the data
     {
          error("ERROR writing to socket");
     }
printf("Sent %lld to server on port %d.\n", a, PORT);



memset(buffer, 0 , sizeof(buffer));	//Clear the buffer for reuse
n = recv(client_socket, &buffer, sizeof(buffer), 0);		//Receive results from the server
if (n < 0)		//Check error in receiving data
{
error("ERROR reading from socket");
}
printf("Received the following result from server:\n%s\nDone\n\n",buffer);


close(client_socket);		//Close the communication socket

return 0;
}
