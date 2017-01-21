#include<stdio.h>
#include<stdlib.h>
#include<ctype.h>
#include<assert.h>
#include<string.h>

#define MAX_STRING_LENGTH 100

void extract_alpha_num(char* s, char* a, char* d, char* p, char* w);
void print_data(char* a, char* d, char* p, char* w, int line_num, FILE* outf);

int main(int argc, char *argv[]) {
	FILE* in;
	FILE* out;
	char* line;
	char* alpha_num;
	char* puncts_file; 
	char* space_file;
	char* nums_file; 
	int temp=0;


	if( argc != 3){
		printf("Usage: %s input-file output-file\n", argv[0]);
		exit(EXIT_FAILURE);
	}

	if( (in=fopen(argv[1], "r")) == NULL){
		printf("Unable to read from file %s\n", argv[1]);
		exit(EXIT_FAILURE);
	}

	if( (out=fopen(argv[2], "w"))==NULL ){
		printf("Unable to write to file %s\n", argv[2]);
		exit(EXIT_FAILURE);
	}

	line = calloc(MAX_STRING_LENGTH + 1, sizeof(char));
	alpha_num = calloc(MAX_STRING_LENGTH + 1, sizeof(char));
	nums_file = calloc(20, sizeof(char));
	puncts_file = calloc(MAX_STRING_LENGTH + 1, sizeof(char));
	space_file = calloc(MAX_STRING_LENGTH + 1, sizeof(char));

	assert( line != NULL && alpha_num != NULL && nums_file != NULL && puncts_file != NULL &&  space_file != NULL);

	while( fgets(line, MAX_STRING_LENGTH, in) != NULL ){
		temp++;

		extract_alpha_num(line, alpha_num, nums_file, puncts_file, space_file);

		print_data(alpha_num, nums_file, puncts_file, space_file, temp, out);
	}

	free(line);
	free(alpha_num);
	free(nums_file);
	free(puncts_file);
	free(space_file);

	fclose(in);
	fclose(out);

	return EXIT_SUCCESS;
}

void print_data(char* a, char* d, char* p, char* w, int line_num, FILE* outf){

	fprintf(outf, "Line %d contains: \n", line_num);
	fprintf(outf, "%d alphabetic characters: %s\n", (int)strlen(a), a);
	fprintf(outf, "%d numeric characters: %s\n", (int)strlen(d), d);
	fprintf(outf, "%d punctuation characters: %s\n", (int)strlen(p), p);
	fprintf(outf, "%d whitespace characters: \n \n", (int)strlen(w));
}

void extract_alpha_num(char* s, char* a, char* d, char* p, char* w){
	int i=0, al=0, dl=0, pl=0, wl=0;


	while(s[i] != '\0' && i<MAX_STRING_LENGTH){
		if(isalpha((int)s[i])){ 
			a[al++] = s[i];
			i++;
		}
		else if(isdigit((int)s[i])){
			d[dl++] = s[i];
			i++;
		}
		else if(ispunct((int)s[i])){
			p[pl++] = s[i];
			i++;
		}
		else if(isspace((int)s[i])){
			w[wl++] = s[i];
			i++;
		}
	}
	a[al] = '\0';
	d[dl] = '\0';
	p[pl] = '\0';
	w[wl] = '\0';
}

