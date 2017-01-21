FileReverse: FileReverse.class
	echo Main-class: FileReverse > Manifest
	jar cvfm FileReverse Manifest FileReverse.class
	rm Manifest
	chmod +x FileReverse

FileReverse.class: FileReverse.java
	javac -Xlint FileReverse.java

clean: 
	rm -f FileReverse FileReverse.class

