#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
* Nate Webber
* Advent of Code 2021
* Day 3 - 12/03/2021
* Problem 2
*/

/*
* C is a fun language sometimes
* I haven't decided if this is one of those times
* This code is far from elegant, but I decided to challenge myself by working in C
* I might come back to it to make a nicer solution someday...
*/


//small little helper function, takes a string and checks if it has been marked (i.e. if there's an 'X' in it somewhere)
int containsX(char* s){
    for (int i = 0; i < 12; i++){
        if (s[i] == 'X')
            return 1;
    }
    return 0;
}

/*
* This is the meat of the solution.
* Basically, following the patten described on the webpage means iterating through the input a few times, and doing a certain operation each time
* Each iteration through the inputs is reliant on information produced by a full previous iteration, so I don't think they can be condensed into fewer loops
*/
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
                if (currentLine[i] == '1')
                    oneCount++;
                else
                    zeroCount++;
            }
        }
        //add to the string of most common bits based on the counts in the previous loop
        if (oneCount >= zeroCount)
            commonBitString[i] = '1';
        else
            commonBitString[i] = '0';

        //iterate through the inputs again now that the most common bits have been updated, and mark the inputs that don't match
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            if (currentLine[i] != commonBitString[i])
                currentLine[i] = 'X';
        }
        /*
        * Now that the inputs are marked, we iterate through the inputs one more time
        * If there's only one unmarked number remaining, that's the number we're looking for
        */
        int surviveCount;
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            if (!containsX(inputs[j]))
                surviveCount++;
            if (surviveCount > 1) //we can stop iterating here because we only care if there's exactly one unmarked input
                break;
        }
        //break out here if there is only one unmarked input
        if (surviveCount == 1)
            break;
        
        //reset these values
        oneCount = 0;
        zeroCount = 0;
        surviveCount = 0;
    }
    //if we've reached this point, there's only one valid input left, so we find and return it
    for (int x = 0; x < (sizeof(inputs) / sizeof(inputs[0])); x++){
        if (!containsX(inputs[x])){
            printf("surviving line: %s\n", inputs[x]);
            return (unsigned int) strtol(inputs[x], NULL, 2);
        }  
    }
    //we should never get here (and in practice we don't since there's only one input to this file)
    printf("this is bad 2\n");
    return 0;

}

/*
* This function is almost completely identical to getOxygen(), so it's not as extensively commented
* I found that with the awkwardness of input handling, the easiest solution was just to parse the input twice
* Since we're only working with 1000 inputs, any inefficient use of time/space is negligible
*/
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
                if (currentLine[i] == '1')
                    oneCount++;
                else
                    zeroCount++;
            }
        }
        if (oneCount >= zeroCount)
            commonBitString[i] = '1';
        else
            commonBitString[i] = '0';

        //iterate through the inputs again now that the most common bits have been updated, but this time mark when they match the common bits
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            char* currentLine = inputs[j];
            if (currentLine[i] == commonBitString[i])
                currentLine[i] = 'X';
        }
        int surviveCount;
        for (int j = 0; j < (sizeof(inputs) / sizeof(inputs[0])); j++){
            if (!containsX(inputs[j]))
                surviveCount++;
            if (surviveCount > 1) //we can stop iterating here because we only care if there's exactly one unmarked input
                break;
        }
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

//Call the actual main functions and print their results
int main(){
    int oxygen = getOxygen();
    printf("OXYGEN RATING: %d\n", oxygen);
    int carbon = getCO2();
    printf("CO2 RATING: %d\n", carbon);
    printf("LIFE SUPPORT RATING: %d\n", oxygen * carbon); //the solution to this problem is the product of these two values
    return 0;
}



