#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
* Nate Webber
* Advent of Code 2021
* Day 3 - 12/03/2021
* Problem 2
*/

//C is a fun language sometimes

int containsX(char* s){
    for (int i = 0; i < 12; i++){
        if (s[i] == 'X')
            return 1;
    }
    return 0;
}

int getOxygen(){
    FILE* inFile = fopen("/home/nate/personal/advent2021/3/in.txt", "r");
    char** inputs[1000][12];

    //grab all the inputs from the file. I'm choosing to keep them as strings/chars for ease of indexing
    for (int x = 0; x < (sizeof(inputs) / sizeof(inputs[0])); x++)
        fscanf(inFile, "%s", inputs[x]);

    //this will store the most common bit at each step
    char* commonBitString = (char*)malloc(sizeof(char) * 12);
    for (int x = 0; x < 12; x++)
        commonBitString[x] = 'N'; //initialize to junk
    
    //for every character except the last
    for (int i = 0; i < 12; i++){
        int oneCount, zeroCount;
        //for every input line
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            //if the line has an X, it has been eliminated and should not be considered
            if (!containsX(currentLine)){
                //printf("considering line: %s\n", currentLine);
                if (currentLine[i] == '1')
                    oneCount++;
                else
                    zeroCount++;
            }
        }
        //printf("oneCount + zeroCount = %d\n", oneCount + zeroCount);
        if (oneCount >= zeroCount)
            commonBitString[i] = '1';
        else
            commonBitString[i] = '0';

        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            if (currentLine[i] != commonBitString[i])
                currentLine[i] = 'X';
        }
        int surviveCount;
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            if (!containsX(inputs[j]))
                surviveCount++;
        }
        //printf("survive count: %d\n", surviveCount);
        if (surviveCount == 1)
            break;
        oneCount = 0;
        zeroCount = 0;
        surviveCount = 0;
    }
    for (int x = 0; x < (sizeof(inputs) / sizeof(inputs[0])); x++){
        if (!containsX(inputs[x])){
            printf("surviving line: %s\n", inputs[x]);
            return (unsigned int) strtol(inputs[x], NULL, 2);
        }  
    }
    printf("this is bad 2\n");
    return 0;

}

int getCO2(){
    FILE* inFile = fopen("/home/nate/personal/advent2021/3/in.txt", "r");
    char** inputs[1000][12];
    char returnString[12];
    //grab all the inputs from the file. I'm choosing to keep them as strings/chars for ease of indexing
    for (int x = 0; x < (sizeof(inputs) / sizeof(inputs[0])); x++)
        fscanf(inFile, "%s", inputs[x]);

    //this will store the most common bit at each step
    char* commonBitString = (char*)malloc(sizeof(char) * 12);
    for (int x = 0; x < 12; x++)
        commonBitString[x] = 'N'; //initialize to junk
    
    //for every character except the last
    for (int i = 0; i < 12; i++){
        int oneCount, zeroCount;
        //for every input line
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            //if the line has an X, it has been eliminated and should not be considered
            if (!containsX(currentLine)){
                //printf("considering line: %s\n", currentLine);
                if (currentLine[i] == '1')
                    oneCount++;
                else
                    zeroCount++;
            }
        }
        //printf("oneCount + zeroCount = %d\n", oneCount + zeroCount);
        if (oneCount >= zeroCount)
            commonBitString[i] = '1';
        else
            commonBitString[i] = '0';

        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            if (currentLine[i] == commonBitString[i])
                currentLine[i] = 'X';
        }
        int surviveCount;
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            if (!containsX(inputs[j]))
                surviveCount++;
        }
        //printf("survive count: %d\n", surviveCount);
        if (surviveCount == 1)
            break;
        oneCount = 0;
        zeroCount = 0;
        surviveCount = 0;
    }
    for (int x = 0; x < (sizeof(inputs) / sizeof(inputs[0])); x++){
        if (!containsX(inputs[x])){
            printf("surviving line: %s\n", inputs[x]);
            return (unsigned int) strtol(inputs[x], NULL, 2);
        }
    }
    return 0;
}

int main(){
    int oxygen = getOxygen();
    printf("OXYGEN RATING: %d\n", oxygen);
    int carbon = getCO2();
    printf("CO2 RATING: %d\n", carbon);
    printf("LIFE SUPPORT RATING: %d\n", oxygen * carbon);
    return 1;
}



