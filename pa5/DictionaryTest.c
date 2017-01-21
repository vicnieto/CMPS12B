#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include"Dictionary.h"

int main(int argc, char *argv[]) {
	Dictionary test = newDictionary();
	insert(test, "cmps12b", "intermediate");
	insert(test, "cmps5p", "legendary");
	insert(test, "HAVC 10", "novice");
	printf("%s\n", lookup(test, "cmps12b"));
	delete(test, "cmps5p");
	// lookup(test, "cmps5p");
	insert(test, "math 23B", "legendary");
	printf("%d\n", size(test));
	makeEmpty(test);
	printf("%d\n", size(test));
	if(isEmpty(test)) printf("test dictionary is empty\n");
	freeDictionary(&test);
}
