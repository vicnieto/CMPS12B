Recursion: Recursion.class
	echo Main-class: Recursion > Manifest
	jar cvfm Recursion Manifest Recursion.class
	rm Manifest
	chmod +x Recursion

Recursion.class: Recursion.java
	javac -Xlint Recursion.java

clean: 
	rm -f Recursion Recursion.class

