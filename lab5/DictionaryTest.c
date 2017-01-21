#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "Dictionary.h"

int main(int argc, char* argv[]){
	Dictionary D = newDictionary();
	insert(D, "class", "cs12b");
	//char* test = lookup(D, "class");
	//printf("%s\n", test);
	insert(D, "food", "pizza");
	int sizeDic = size(D);
	printf("%d\n", sizeDic);
	if(isEmpty(D) == 0) printf("Not Empty\n");
	//insert(D, "Dog", "venus fly trap");
	insert(D, "Dog", "mans freind");
	delete(D, "Dog");
	printDictionary(stdout, D);
	makeEmpty(D);
	if(isEmpty(D)) printf("Empty\n");
	freeDictionary(&D);
}
