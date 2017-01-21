#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

const int tableSize=101;

typedef struct NodeObj{
	char* key;
	char* value;
	struct NodeObj* next;
} NodeObj;

typedef NodeObj* Node;

Node newNode(char* k, char* v);

Node findKey(Dictionary D, char* key);

void freeNode(Node* pN);

Node newNode(char* k, char* v){
	Node N = malloc(sizeof(NodeObj));
	assert(N != NULL);
	N->key = k;
	N->value = v;
	N->next = NULL;
	return N;
}

void freeNode(Node* pN){
	if( pN != NULL && *pN != NULL){
		free(*pN);
		*pN = NULL;
	}
}



typedef struct DictionaryObj{
	Node* table;
	int numItems;
}DictionaryObj;


Dictionary newDictionary(){
	Dictionary D = malloc(sizeof(Dictionary));
	assert(D != NULL);
	D->table = calloc(tableSize, sizeof(Node));
	assert(D->table != NULL);
	D->numItems = 0;
	return D;
}

void freeDictionary(Dictionary* pD){
	if(pD != NULL && *pD != NULL){
		makeEmpty(*pD);
		free((*pD)->table);
		free(*pD);
		*pD = NULL;
	}
}



// rotate_left()
// rotate the bits in an unsigned int
unsigned int rotate_left(unsigned int value, int shift) {
 int sizeInBits = 8*sizeof(unsigned int);
 shift = shift & (sizeInBits - 1);
 if ( shift == 0 )
 return value;
 return (value << shift) | (value >> (sizeInBits - shift));
}
// pre_hash()
// turn a string into an unsigned int
unsigned int pre_hash(char* input) {
 unsigned int result = 0xBAE86554;
 while (*input) {
 result ^= *input++;
 result = rotate_left(result, 5);
 }
 return result;
}
// hash()
// turns a string into an int in the range 0 to tableSize-1
int hash(char* key){
 return pre_hash(key)%tableSize;
}


int isEmpty(Dictionary D){
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: isEmpty() called on NULL Dictionary reference\n");
		exit(EXIT_FAILURE);
	}
	return(D->numItems == 0);
}

int size(Dictionary D){
	return(D->numItems);
}

Node findKey(Dictionary D, char* k){
	Node N = D->table[hash(k)];
	while(N != NULL){
		if(strcmp(N->key, k) == 0) return N;
		N = N->next;
	}
	return N;
}

char* lookup(Dictionary D, char* k){
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: lookup() called on NULL Dictionary reference\n");
		exit(EXIT_FAILURE);
	}
	if(findKey(D, k) == NULL) return(NULL);
	Node N = findKey(D, k);
	return N->value;
}

void insert(Dictionary D, char* k, char* v){
	int index = hash(k);
	Node N = newNode(k, v);
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: insert() called on NULL Dictionary reference\n");
		exit(EXIT_FAILURE);
	}
	if(findKey(D, k) != NULL){
		fprintf(stderr, "Dictionary Error: insert() called with existing key\n");
		exit(EXIT_FAILURE);
	}
	
	
	if(D->table[index] != NULL){
		N->next = D->table[index];
		D->table[index] = N;
	}
	else{
		D->table[index] = N;
	}
	D->numItems++;
}

void makeEmpty(Dictionary D){
	for(int i = 0; i < tableSize; i++){
		while(D->table[i] != NULL){
			Node N = D->table[i];
			D->table[i] = N->next;
			freeNode(&N);
			N = NULL;
		}
	}
	D->numItems = 0;
}

void delete(Dictionary D, char* k){
	int i = hash(k);
	Node P;
	Node N = D->table[i];
	
	if(findKey(D, k) == NULL){
		fprintf(stderr, "Dictionary Error: delete() called with non-existing key\n");
		exit(EXIT_FAILURE);
	}
	
	if(strcmp(N->key, k) == 0){
		D->table[i] = N->next;
		freeNode(&N);
		N = NULL;
	}
	else{
		P = D->table[i];
		N = findKey(D, k);
		while(P->next != N){
			P = P->next;
		}
		P->next = N->next;
		freeNode(&N);
		N = NULL;
	}
	D->numItems--;
}

void printDictionary(FILE* out, Dictionary D){
	Node N;
	for(int i = 0; i < tableSize; i++){
		N = D->table[i];
		while(N != NULL){
			fprintf(out, "%s %s\n", N->key, N->value);
			N = N->next;
		}
	}
}
