CC = javac

all: client1 server1

client1: 
	$(CC) client1.java

server1:
	$(CC) server1.java

clean:
	rm -r *.class