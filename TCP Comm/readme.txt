NAME: DIPAYAN KARMAKAR
USC ID: 7890549028
EMAIL ID: DKARMAKA@USC.EDU



This file briefs about the files submitted, insrtuctions on running the project, the way this project is executed in the programs, the format 
of all the masseges exchanged and references used to make this project.

The .zip file for this project dipayan_karmakar.zip consists of 4 file, viz. server.cpp, client.cpp, Makefile and readme.txt.

To run this project, first exatract the files. 
Run the "make" command on the extracted project folder.
In the terminal window, execute "./server" and the "./client".
You can execute as many clients as you want at the same time.
The IP address used is "127.0.0.1" and port number used is 19028.


Description of execution of project in the two c++ files.

#1 SERVER:

1. 	The value of PORT is defined in the begining as 19028. The program uses two programmer defined functions, viz, countones and error.
2. 	The countones is a recursive function that takes an integer as an input. In case if the input is zero, it returns zero.
	If the input is non-zero, it divides the input by two and returns the sum of the following two elements, the remainder of the 
	division and the output of the countones function of the quotient.
3.	The error is a function that is called when there is an error in executing any other function. It takes a string input as an error tag.
	It prints the error message along with the tag and uses exit function to stop the program execution.
4.	There are five(5) integer variables used, viz. "server_sock" to store the server socket number we will generate, "client_sock" to store 
	the socket number of the client which will connect to the server, "n" to catch any error at any function execution, "count" to store 
	the count of ones in the integer client will send and "data" to store the integer sent by user to process it.
5. 	One variable of sockaddr_in data type named "server" is used to store the details of the server.
6.	A character array named "buffer" is used to store messages received or to be sent.
7.	To create a server communication socket, the socket function is called with AF_INET as domain for IP addressing format, SOCK_STREAM as 
	communication type for TCP communication and 0 as protocol. The returned socket number is stored in server_sock.
8.	If the value returned to server_sock is less than zero, there is an error in socket creation and hence error function is called.
9.	The server variable is filled with data viz., AF_INET in connection family, value of PORT converted into network byte order in server 
	port number and INADDR_LOOPBACK in server IP address for the value "127.0.0.1".
10.	The server socket server_sock is bind with the server address defined above. In case of an error in binding, the error function is
	called.
11.	The listen function is called to listen (or wait) for any client connection. In case of an error in listening, the error function is 
	called.
12.	To make this server attend each and every client forever, a infinite while loop is started and all further functions are called inside 
	it.
13.	The program displays on the console "Server is running and ready to receive connections on port 19028…".
14.	When a client tries to connect to the server socket using the same IP address and port number, the accept function is called on the 
	server_sock and the returned client socket number is stored in client_sock. In case of an error in accepting, the error function is 
	called.
15.	The receive function is called to receive any message from the client through client_sock, and stored in buffer. In case of an error in 
	receiving, the error function is called.
16.	The received data is converted from string to integer and stored in data.
17.	The program displays the number received and intimates the user that it's computing the number of ones.
18.	The countones function is called with data as argument and the result is stored in count. The value of count is displayed on console.
19.	All the elements of the buffer array is cleared using memset. The value of count is converted to a string and stored in buffer.
20.	The string buffer is sent to the client using sent funtion on the client socket number. In case of an error in sending, the error 
	function is called.
21.	The user is intimated that the program has sent the result to the client.
22.	The program runs back to the beginning of the while loop and waits for another client or attends another client if in queue.


#2 CLIENT:

1. 	The value of PORT is defined in the begining as 19028. 
2.	The program uses a programmer defined function named error. The error is a function that is called when there is an error in executing 
	any other function. It takes a string input as an error tag. It prints the error message along with the tag and uses exit function to 
	stop the program execution.
3.	There are three(3) integer variables used, viz. "client_socket" to store the client socket number we will generate, "n" to catch any 
	error at any function execution and "flag" as a flag variable to identify if a user input variable is as per requirement.
4.	One variable of sockaddr_in data type named "server_address" is used to store the details of the server.
5.	A character array named "buffer" is used to store messages received or to be sent.
6.	A long long integer "a" is used in verifying if user input data is as per requirement.
7.	To create a client communication socket, the socket function is called with AF_INET as domain for IP addressing format, SOCK_STREAM as 
	communication type for TCP communication and 0 as protocol. The returned socket number is stored in client_socket. 
8.	If the value returned to client_socket is less than zero, there is an error in socket creation and hence error function is called.
9.	An infinite while loop is generated to ask for input from the user until the user gives a valid input.
10.	The flag is made zero and the value of buffer is reset to be used to store new string. The program displays on console "Please provide 
	an integer in the range [0, 2147483647]:".
11.	The user input is taken and stored in buffer. Firstly, a for loop is used to check if data input by user contains only integers. Second,
	it is checked if the input number is within 10 digit. Lastly, it is checked if the number is within 0 to 2147483647. If any one of 
	these tests fail, the flag is raised and the user is asked to enter a new number. Here, the program goes back to step 10. If all test 
	conditions are fulfilled, the program breaks the loop and proceeds with step 12.
12.	The server_address variable is filled with data viz., AF_INET in connection family, value of PORT converted into network byte order in 
	server port number and INADDR_LOOPBACK in server IP address for the value "127.0.0.1".
13.	The connect function is called to connect the client socket to the server at the server_address. In case of an error in connecting, the 
	error function is called.
14.	The send function is called to send the buffer to the server connected to the client_socket. In case of an error in sending, the error 
	function is called. The user is intimated that the data has been sent to server.
15.	The buffer is cleared to be reused. The recv function is used to receive data sent to the client_socket and store it in the buffer. In 
	case of an error in sending, the error function is called. 
16.	The received data is displayed on the console and the client_socket is closed.


Declaration of references used in this project.

1.	"TCP/IP Programming in C" by Vinod Pillai. Link to video: https://www.youtube.com/watch?v=V6CohFrRNTo&t=52s
	This video is referred to for parts of the program like the programmer defined function error etc.
2.	"Socket Programming Tutorial In C For Beginners | Part 1 | Eduonix" by Eduonix Learning Solutions. 
	Link to video: https://www.youtube.com/watch?v=LtXEMwSG5-8
	This video is referred to create all the socket programming codes. The codes are not exactly same but will have a similar arrangement 
	and arguments used in this video.



-----------------------------------------------------------------THANK YOU-----------------------------------------------------------------
