# Java-Network_Protocols
Concurrent Calculation Server (CCS)
The CCS (Concurrent Calculation Server) is a Java-based multi-threaded application designed for concurrent handling of arithmetic operations over TCP and UDP. It supports multiple clients simultaneously and tracks operational statistics.

Key Features:
Dual Protocol Support: Handles both TCP and UDP communication protocols.
Multi-threaded Architecture: Separate threads manage UDP discovery, TCP connections, and client interactions for efficient concurrency.
Operation Handling: Supports basic arithmetic operations (ADD, SUB, MUL, DIV) with syntax validation.
Statistics Tracking: Monitors operation metrics, such as solved operations, operation types, and errors.
Periodic Reporting: Prints operation statistics every 10 seconds for real-time monitoring.
Components:
CCS.java: Entry point for the application, initializes TCP and UDP threads.
ClientHandler.java: Manages individual client connections and processes arithmetic commands.
TCPThread.java: Listens for and handles TCP connections.
UDPThread.java: Handles UDP-based discovery requests.
PrintStats.java: Periodically displays server statistics.
Usage:
Run the server by specifying a port:

bash
Kopiuj
Edytuj
java -jar CCS.jar <port>
This project demonstrates multi-threaded server design, protocol handling, and real-time statistics in a distributed computing environment.
