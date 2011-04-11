/*
 * tokenizer.c - A simple token recognizer.
 * author: Randall Hunt
 * date: 9/28/2010
 * NOTE: The terms 'token' and 'lexeme' are used interchangeably in this
 *       program.
 */

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#define LINE  100       // lines will not be greater than 100 chars
#define TSIZE 20        // tokens will not be greater than 20 chars
#define TRUE  1         // Define TRUE and FALSE for read-ability
#define FALSE 0

/* TOKEN CODES */
#define TOK_NONE            100
#define TOK_EQ              1
#define TOK_LTEQ            2
#define TOK_GTEQ            3
#define TOK_NOTEQ           4
#define TOK_NOT             5
#define TOK_PLUS            6
#define TOK_MINUS           7
#define TOK_MULT            8
#define TOK_DIV             9
#define TOK_LPAR            10
#define TOK_RPAR            11
#define TOK_EXP             12
#define TOK_ASSIGN          13
#define TOK_LT              14
#define TOK_GT              15
#define TOK_TERM            16
#define TOK_NL              17
/*
 * Function Prototypes, global variables
 */
void get_number(char* token_ptr);   //method to get up to 20 digits
void get_token(char* token_ptr);    // Grabs one token (really a lexeme)
char* line;                         // Global pointer to line of input
int  which_token(char* string);     // This will return an int corresponding
                                    // to the token.
/*
 * This is a lookup table, one way of dealing with tokens, you don't
 * really need it here since you're never dealing with the tokens,
 * just grabbing them. But it is a nice data structure.
 */
static struct lookup_table {
    char* string;
    int code;
} table[] = {
    {"==",  TOK_EQ},
    {"<=",  TOK_LTEQ},
    {">=",  TOK_GTEQ},
    {"!=",  TOK_NOTEQ},
    {"!",   TOK_NOT},
    {"+",   TOK_PLUS},
    {"-",   TOK_MINUS},
    {"*",   TOK_MULT},
    {"/",   TOK_DIV},
    {"(",   TOK_LPAR},
    {")",   TOK_RPAR},
    {"^",   TOK_EXP},
    {"=",   TOK_ASSIGN},
    {"<",   TOK_LT},
    {">",   TOK_GT},
    {";",   TOK_TERM},
    {"\n",  TOK_NL}
};

//This method will run through the table looking for two character
//strings first, then 1 character strings if none of the two character
//strings matched.
int which_token(char* string) {
    int i = 0;
    //loop through the table for two character strings
    for(i = 0; i < sizeof(table) / sizeof(table[0]); i++) {
        if (strncmp(table[i].string, string, 2) == 0) {
            return table[i].code;
        }
    }
    //loop through the table for single character strings
    //starts at 3, where the single character strings start
    for(i = 3; i < sizeof(table) / sizeof(table[0]); i++) {
        if(strncmp(table[i].string, string, 1) == 0) {
            return table[i].code;
        }
    }
    return TOK_NONE;
}

int main() {
    char token[TSIZE];      /* Spot to hold a token, fixed size */
    char input_line[LINE];  /* Line of input, fixed size        */
    char filename[LINE];    /* Space for a filename             */
    FILE *fp = NULL;        /* Pointer to a file                */
    int token_num = 0;      /* Which token are we on?           */
    int statement_num = 0;  /* Which statement are we on?       */
    int keep_getting_tokens = FALSE; /* start a new statement? */

    printf("Please enter expressions file >");
    scanf("%s", filename); 

    // check for proper operenage
    if ((fp = fopen(filename, "r")) == NULL) {
        perror("Error:");
        exit(1);
    }

    while (fgets(input_line, LINE, fp) != NULL) {
        line = input_line;  // Sets a global pointer to the memory location
        // where the input line resides.
        int i = 0;
        //If we should keep getting tokens do nothing
        //otherwise increment the statement number
        //reset the token number
        //and print out the new statment heading
        if(!keep_getting_tokens) {
            statement_num++;
            token_num = 0;
            printf("Statement %d\n", statement_num);
        }

        //get as many tokens from the line as you can
        for(i = 0; i < LINE; i++) {
            //if we have a new line then get the next line in the file
            //but don't reset the token count.
            if(*line == '\n') {
                keep_getting_tokens = TRUE;
                break;
            }
            if(isspace(*line)) {
                keep_getting_tokens = TRUE;
                line++;
                continue;
            }
            get_token(token);
            printf("Token %d is %s\n", token_num, token);
            //if we have a semicolon start a new statement
            if(*token == ';') {
                keep_getting_tokens = FALSE;
                printf("-------------------------\n");
                break;
            }
            token_num++;
        }
    }
    fclose(fp);
    return 0;
}
/*
 * This function puts the last token discovered into the *token_ptr.
 * then it increments the pointer line the specified number of times.
 */
void get_token(char* token_ptr) {
    memset(token_ptr, 0, 20); // Clear the memory in token
    if(isdigit(*line)) { 
        //if next token is a digit then get the number. 
        get_number(token_ptr);
        return;
    }
    /*
     * Let similar cases fall through each other. Increment line by the
     * length of the token grabbed.
     * Could also just return instead of break.
     */
    switch(which_token(line)) {
        case TOK_EQ:
        case TOK_LTEQ:
        case TOK_GTEQ:
        case TOK_NOTEQ:
            strncpy(token_ptr, line, 2);
            line+=2;
            break;
        case TOK_NOT:
        case TOK_PLUS:
        case TOK_MINUS:
        case TOK_MULT:
        case TOK_DIV:
        case TOK_LPAR:
        case TOK_RPAR:
        case TOK_EXP:
        case TOK_ASSIGN:
        case TOK_LT:
        case TOK_GT:
        case TOK_TERM:
            *token_ptr =  *line;
            line++;
            break;
        case TOK_NONE:
            *token_ptr = *line;
            line++;
            printf("Bad Token: ");
            break;
       case TOK_NL:
            *token_ptr = *line;
            break;
    }  
}
/*
 * this function will grab a number from a given *char line, up to 20
 */
void get_number(char* token_ptr) {
    int i = 0;
    for(; i < TSIZE-1; i++) {
        //A token can only be TSIZE-1 characters long because the last
        //char in the array is reserved for the \0 char to terminate
        //the string
        if(isdigit(line[i])) { //Append the digit to the token
            int token_length = strlen(token_ptr);
            token_ptr[token_length] = line[i];
            token_ptr[token_length+1] = '\0';
        } else {
            break;
        }
    }
    line = line+i;
}
