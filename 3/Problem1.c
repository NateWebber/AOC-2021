#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
* Nate Webber
* Advent of Code 2021
* Day 3 - 12/03/2021
* Problem 1
*/

//Bitwise operations and stuff today, which means we're writing in C

void invertBinaryString(char* s){
    for (int i = 0; i < 12; i++){
        if (s[i] == '0')
            s[i] = '1';
        else
            s[i] = '0';
    }
}

int main(){
    FILE* inFile = fopen("/home/nate/personal/advent2021/3/in.txt", "r");
    char** inputs[1000][12];

    //grab all the inputs from the file. I'm choosing to keep them as strings/chars for ease of indexing
    for (int x = 0; x < 1000; x++)
        fscanf(inFile, "%s", inputs[x]);
 
    //I'm going to store the results as strings until the very end too
    char* gammaString = (char*)malloc(sizeof(char) * 12);

    //for every bit in a line (there are 12)
    for (int i = 11; i >= 0; i--){
        int zeroCount = 0;
        int oneCount = 0;

        //for every line
        for (int j = 0; j < 1000; j++){
            char* currentLine = inputs[j];
            //add to the running sum based on which value we see
            if (currentLine[i] == '1')
                oneCount++;
            else
                zeroCount++;
        }
        //this position had more 1's than 0's
        if (oneCount > zeroCount)
            gammaString[i] = '1';
        //this position had more 0's than 1's
        else
            gammaString[i] = '0';
        
    }

    printf("gamma string: %s\n", gammaString);
    //strtol is a standard library function that converts a string into a long. You can provide a radix, in this case 2 will cause it to parse as binary
    unsigned int gammaRate = (unsigned int) strtol(gammaString, NULL, 2);
    printf("gamma rate: %d\n", gammaRate);

    //make and invert the epsilon rate string
    char epsilonString[12];
    strcpy(epsilonString, gammaString);
    invertBinaryString(epsilonString);

    printf("epsilon string: %s\n", epsilonString);
    unsigned int epsilonRate = (unsigned int) strtol(epsilonString, NULL, 2);
    printf("epsilon rate: %d\n", epsilonRate);

    //finally, multiply the two together
    printf("Power Consumption: %d\n", epsilonRate * gammaRate);
    return 0;
}

