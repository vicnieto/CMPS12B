#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "Dictionary.h"

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
	Node head;
	int numPairs;
} DictionaryObj;

Dictionary newDictionary(void){
	Dictionary D = malloc(sizeof(DictionaryObj));
	assert(D != NULL);
	D->head = NULL;
	D->numPairs = 0;
	return D;
}

int isEmpty(Dictionary D){
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: calling isEmpty() on NULL Dictionary reference\n");
		exit(EXIT_FAILURE);
	}
	return D->numPairs == 0;
}

int size(Dictionary D){
	return D->numPairs;
}

Node findKey(Dictionary D, char* k){
	Node N = D->head;
	while(N !=NULL){
		if(strcmp(k, N->key) == 0) return N;
		N = N->next;
	}
	return NULL;
}

Node findPrev(Dictionary D, char* k){
	Node N = D->head;
	while(N->next != NULL){
		if(N->next->key == k) return N;
		N = N->next;
	}
	freeNode(&N);
	return NULL;

}




char* lookup(Dictionary D, char* k){
	Node N;
	N = findKey(D, k);
	if(N == NULL) return NULL;
	return N->value;
}

void insert(Dictionary D, char* k, char* v){
	Node N;
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: insert() called on NULL Dictionary reference");
		exit(EXIT_FAILURE);
	}
	if(lookup(D, k) != NULL){
		fprintf(stderr, "Dictionary Error: insert() called with existing key");
		exit(EXIT_FAILURE);
		}
	N = newNode(k, v);
	N->next = D->head;
	D->head = N;
	D->numPairs++;
}

void delete(Dictionary D, char* k){
	Node N;
	Node P;
	if(lookup(D, k) == NULL){
		fprintf(stderr, "Dictionary Error: delete() called on non-existing key");
		exit(EXIT_FAILURE);
	}
	N = findKey(D, k);
	if(N == D->head){
		D->head = D->head->next;
		freeNode(&N);
	}
	else{
		N = D->head;
		while(N->next->key != k){
			N = N->next;
			} 
		P = N->next;
		N->next = P->next;
		freeNode(&P);
	}
	D->numPairs--;
}

void deleteAll(Dictionary D){
	Node N = D->head;
	Node P;
	while(N != NULL){
		P = N;
		N = N->next;
		freeNode(&P);
	}
}

void makeEmpty(Dictionary D){
	Node N, M;
	if(D != NULL){
		N = D->head;
		M = N->next;
		freeNode(&N);
		N = M;
		M = N->next;
	}
	D->head = NULL;
	D->numPairs = 0;
}


void freeDictionary(Dictionary* pD){
	if(pD != NULL && *pD != NULL){
		if(!isEmpty(*pD)) makeEmpty(*pD);
		free(*pD);
		*pD = NULL; 
	}
}

void print_recurse(Node N, FILE* out){
	if(N!=NULL){
		print_recurse(N->next, out);
		fprintf(out, "%s %s\n", N->key, N->value);
		}
	}

void printDictionary(FILE* out, Dictionary D){
	if(D == NULL){
		fprintf(stderr, "Dictionary Error: printDictionary() called on NULL Dictionary reference");
		exit(EXIT_FAILURE);
		}
	if(D->numPairs == 0){
		fprintf(stderr, "Dictionary Error: printDictionary() called on empty Dictionary");
		exit(EXIT_FAILURE);
		}
 	print_recurse(D->head, out);
	
}


