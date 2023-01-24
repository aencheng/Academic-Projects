#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<time.h>
int boardingProgram();
#define TOTALPASSENGERS 25
extern char passengersFN[25][15];
extern void createPassengerFirstNameList();
extern char passengersLN[25][15];
extern void createPassengerLastNameList();
const int row = 20;
const int seats = 7;
void printSeatingChart(int seatsArray[row][seats], char letters[], char numbers[]);
void blockingSeats(int seatsArray[row][seats], int blockingRow, int blockingSeat);
void unblockSeats(int arr[row][seats], int rows, int seat);
void sortingSeats();
void sortingAlphabetically();
int initPassengerInfo();
void choosingSeat(int i, int j, int pNumber, int arr[row][seats], int *initRow, int *initSeat);
int isFull(int arr[row][seats], char class);

struct passengerInfo{
  char *firstName;
  char *lastName;
  int row;
  int seat;
  char pref;
};

struct passengerInfo passengerList[TOTALPASSENGERS];

int main(){
  srand(time(NULL)); // for rand() to work
  initPassengerInfo(); // calls to initialize List
  boardingProgram(); // calls on the program to start it
}

int initPassengerInfo(){ // initializes and creates the passengerList of struct passengerInfo
  createPassengerFirstNameList();
  createPassengerLastNameList();
  int r = 0;
  for(int i = 0; i < TOTALPASSENGERS; i++){
    struct passengerInfo passenger;
    passenger.firstName = passengersFN[i];
    passenger.lastName = passengersLN[i];
    passenger.row = -1;
    passenger.seat = -1;
    r = rand() & 2;
    if(i >= 10){
      passenger.pref = 'N';
    }
    else if(r == 0){
      passenger.pref = 'W';
    }
    else{
      passenger.pref = 'A';
    }
    passengerList[i] = passenger;
  }
}

/*void printManifest(){
  for(int i = 0; i < TOTALPASSENGERS; i++){
    printf("Name: %s %s, Row: %d, Seat: %d, Pref: %c\n", arr[i].firstName, arr[i].lastName, arr[i].row, arr[i].seat, arr[i].pref);
  }
  }*/

int boardingProgram(){
  char letters[] = "ABCDEFGHIJKLMNOPQRST"; // for row names
  char numbers[] = "1234567"; // for seat numbers
  int seatsArray[row][seats]; // used as an array for the seating chart
  for(int i = 0; i < row; i++){ // below for loop initializes every seat to 0, unassigned
    for(int j = 0; j < seats; j++){
      seatsArray[i][j] = 0;
    }
  }
  char input; // 
  int randomRow; 
  int randomSeat;
  char class[20]; // For boarding pass information to copy and the print the Level
  char choice; // seating choices that is used later
  while(input != 'Q'){
    printf("Please choose one of the following options\n");
    printf("Option 'A' - Choose a passenger and choose a seat for them\n");
    printf("Option 'B' - Print a boarding pass for the chosen seat\n");
    printf("Option 'C' - Print the seating chart\n");
    printf("Option 'D' - Print a passenger manifest\n");
    printf("Option 'Q' - Quit the Program\n");
    scanf(" %c", &input); // scans user input
    if(input == 'A' || input == 'B' || input == 'C' || input == 'D' || input == 'Q'){
      switch(input){
      case 'A':
	printf("\n"); // below prints all passenger info for the user to see and choose the next passenger they want to deal with.
	printf("Please select a passenger number\n");
	for(int i = 0; i < TOTALPASSENGERS; i++){
	  printf("Passenger #%d: Name: %s %s, Row: %d, Seat: %d, Pref: %c\n", i+1, passengerList[i].firstName, passengerList[i].lastName, passengerList[i].row, passengerList[i].seat, passengerList[i].pref);
	}
	printf("\n");
	int passengerNumber;
	int r, s;
	scanf(" %d", &passengerNumber);
	// base case for checking if the passenger Chosen is already not able to board this flight
	if(passengerList[passengerNumber - 1].row == -3){
	  printf("Passenger Will Not Be Boarding This Flight\n");
	  break;
	}
	// This checks to see if the selected passenger has already chosen a seat before. If so, it will as the user if they want to downgrade from their class. (Only applicable to first class)
	if(passengerList[passengerNumber - 1].row != -1){
	  if(passengerList[passengerNumber - 1].row > 0 && passengerList[passengerNumber - 1].row < 4){
	    printf("Would you like to downgrade? 'Y' or 'N'\n");
	    scanf(" %c", &choice);
	    switch(choice){
	    case 'Y':
	      choosingSeat(3, 4, passengerNumber, seatsArray, &r, &s);
	      printf("Is Business Class, Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
	      scanf(" %c", &choice);
	      if(choice == 'Y'){
		unblockSeats(seatsArray, passengerList[passengerNumber - 1].row - 1, passengerList[passengerNumber - 1].seat - 1);
		passengerList[passengerNumber - 1].row = r + 1;
		passengerList[passengerNumber - 1].seat = s + 1;
		seatsArray[r][s] = 1;
		blockingSeats(seatsArray, r, s);
	      }
	      else{
		printf("Seat Assignment will stay the same\n");
	      }
	      break;
	    case 'N':
	      printf("Seat Assignment stays the same\n");
	      break;
	    }
	  }
	  break;
	}
	
	printf("Please choose one of the following options:\n");
	printf("Option 'A' - Passenger chooses their seat\n");
	printf("Option 'B' - Computer chooses seat\n");
	char seatSelectionInput;
	char c;
	scanf(" %c", &seatSelectionInput);
	switch(seatSelectionInput){
	case 'A':
	  /* To put long story short, when first class is chosen, it will check if the level is full before giving the passenger options to choose their next option. If the chosen seat is not to their liking, if they take more than 3 times to choose their seat, they will have to find another flight. If the level is full, it will then check if Business class is full and essentially rinse and repeat what happened with First Class but with Business class Parameters. Same with Business to Economy Class if it is full.
The Business class option essentially does the same as first class but just without the functions and methods checking first class parameters and Economy class option is also reflective of that as well. If Level is full but the next level down isn't it will randomly choose a seat from the level and ask the passenger if the seat is ok. If not they will have to board the next flight. If it is ok then it will book that seat.
	   */
	  printf("\n");
	  printf("Please type 'F' for first class, 'B' for business class, or 'E' for economy class\n");
	  char classSelectionInput;
	  scanf(" %c", &classSelectionInput);
	  switch(classSelectionInput){
	  case 'F':
	    if(isFull(seatsArray, classSelectionInput) == 0){
	      choosingSeat(0, 3, passengerNumber, seatsArray, &r, &s);
	      printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
	      scanf(" %c", &input);
	      c = input;
	      int counter = 0;
	      if(c == 'Y'){
		passengerList[passengerNumber - 1].row = r + 1;
		passengerList[passengerNumber - 1].seat = s + 1;
		seatsArray[r][s] = 1;
		blockingSeats(seatsArray, r, s);
	      }
	      else{
		while((counter < 3) && (c != 'Y')){
		  counter++;
		  if(counter == 3){
		    passengerList[passengerNumber - 1].row = -3;
		    passengerList[passengerNumber - 1].seat = -3;
		    printf("Unfortunately, you will have to board the next flight\n");
		  }
		  else{
		    choosingSeat(0, 3, passengerNumber, seatsArray, &r, &s);
		    printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		    scanf(" %c", &input);
		    c = input;
		    if(c == 'Y'){
		      passengerList[passengerNumber - 1].row = r + 1;
		      passengerList[passengerNumber - 1].seat = s + 1;
		      seatsArray[r][s] = 1;
		      blockingSeats(seatsArray, r, s);
		    }
		  }
		}
	      }
	    }
	    else{
	      printf("First Class is currently full. Is Business Class acceptable? 'Y' or 'N'\n");
	      scanf(" %c", &input);
	      switch(input){
	      case 'Y':
		if(isFull(seatsArray, 'B') == 0){
		  choosingSeat(3, 4, passengerNumber, seatsArray, &r, &s);
		  printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		  scanf(" %c", &input);
		  c = input;
		  int counter = 0;
		  if(c == 'Y'){
		    passengerList[passengerNumber - 1].row = r + 1;
		    passengerList[passengerNumber - 1].seat = s + 1;
		    seatsArray[r][s] = 1;
		    blockingSeats(seatsArray, r, s);
		  }
		  else{
		    while((counter < 3) && (c != 'Y')){
		      counter++;
		      if(counter == 3){
			passengerList[passengerNumber - 1].row = -3;
			passengerList[passengerNumber - 1].seat = -3;
			printf("Unfortunately, you will have to board the next flight\n");
		      }
		      else{
			choosingSeat(3, 4, passengerNumber, seatsArray, &r, &s);
			printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
			scanf(" %c", &input);
			c = input;
			if(c == 'Y'){
			  passengerList[passengerNumber - 1].row = r + 1;
			  passengerList[passengerNumber - 1].seat = s + 1;
			  seatsArray[r][s] = 1;
			  blockingSeats(seatsArray, r, s);
			}
		      }
		    }
		  }
		}
		else if(isFull(seatsArray, 'B') == 1){
		  printf("Business Class is currently full. Is Economy Class acceptable? 'Y' or 'N'\n");
		  scanf(" %c", &input);
		  switch(input){
		  case 'Y':
		    if(isFull(seatsArray, 'E') == 0){
		      choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
		      printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		      scanf(" %c", &input);
		      c = input;
		      int counter = 0;
		      if(c == 'Y'){
			passengerList[passengerNumber - 1].row = r + 1;
			passengerList[passengerNumber - 1].seat = s + 1;
			seatsArray[r][s] = 1;
			blockingSeats(seatsArray, r, s);
		      }
		      else{
			while((counter < 3) && (c != 'Y')){
			  counter++;
			  if(counter == 3){
			    passengerList[passengerNumber - 1].row = -3;
			    passengerList[passengerNumber - 1].seat = -3;
			    printf("Unfortunately, you will have to board the next flight\n");
			  }
			  else{
			    choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
			    printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
			    scanf(" %c", &input);
			    c = input;
			    if(c == 'Y'){
			      passengerList[passengerNumber - 1].row = r + 1;
			      passengerList[passengerNumber - 1].seat = s + 1;
			      seatsArray[r][s] = 1;
			      blockingSeats(seatsArray, r, s);
			    }
			  }
			}
		      }
		    }
		    else if(isFull(seatsArray, 'E') == 1){
		      passengerList[passengerNumber - 1].row = -3;
		      passengerList[passengerNumber - 1].seat = -3;
		      printf("Economy Class is currently full. Please find the next available flight!\n");
		    }
		    break;
		  case 'N':
		    passengerList[passengerNumber - 1].row = -3;
		    passengerList[passengerNumber - 1].seat = -3;
		    printf("Please find the next flight available. Have a great day!\n");
		    break;
		  }
		}
		break;
	      case 'N':
		printf("Please find the next flight available. Have a great day!\n");
		break;
	      }
	    }
	    break;
	      
	  case 'B':
	    if(isFull(seatsArray, classSelectionInput) == 0){
	      choosingSeat(3, 4, passengerNumber, seatsArray, &r, &s);
	      printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
	      scanf(" %c", &input);
	      c = input;
	      int counter = 0;
	      if(c == 'Y'){
		passengerList[passengerNumber - 1].row = r + 1;
		passengerList[passengerNumber - 1].seat = s + 1;
		seatsArray[r][s] = 1;
		blockingSeats(seatsArray, r, s);
	      }
	      else{
		while((counter < 3) && (c != 'Y')){
		  counter++;
		  if(counter == 3){
		    passengerList[passengerNumber - 1].row = -3;
		    passengerList[passengerNumber - 1].seat = -3;
		    printf("Unfortunately, you will have to board the next flight\n");
		  }
		  else{
		    choosingSeat(3, 4, passengerNumber, seatsArray, &r, &s);
		    printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		    scanf(" %c", &input);
		    c = input;
		    if(c == 'Y'){
		      passengerList[passengerNumber - 1].row = r + 1;
		      passengerList[passengerNumber - 1].seat = s + 1;
		      seatsArray[r][s] = 1;
		      blockingSeats(seatsArray, r, s);
		    }
		  }
		}
	      }
	    }
	    else if(isFull(seatsArray, classSelectionInput) == 1){
	      printf("Business Class is currently full. Is Economy Class acceptable? 'Y' or 'N'\n");
	      scanf(" %c", &input);
	      switch(input){
	      case 'Y':
		if(isFull(seatsArray, 'E') == 0){
		  choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
		  printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		  scanf(" %c", &input);
		  c = input;
		  int counter = 0;
		  if(c == 'Y'){
		    passengerList[passengerNumber - 1].row = r + 1;
		    passengerList[passengerNumber - 1].seat = s + 1;
		    seatsArray[r][s] = 1;
		    blockingSeats(seatsArray, r, s);
		  }
		  else{
		    while((counter < 3) && (c != 'Y')){
		      counter++;
		      if(counter == 3){
			passengerList[passengerNumber - 1].row = -3;
			passengerList[passengerNumber - 1].seat = -3;
			printf("Unfortunately, you will have to board the next flight\n");
		      }
		      else{
			choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
			printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
			scanf(" %c", &input);
			c = input;
			if(c == 'Y'){
			  passengerList[passengerNumber - 1].row = r + 1;
			  passengerList[passengerNumber - 1].seat = s + 1;
			  seatsArray[r][s] = 1;
			  blockingSeats(seatsArray, r, s);
			}
		      }
		    }
		  }
		}
		else if(isFull(seatsArray, 'E') == 1){
		  passengerList[passengerNumber - 1].row = -3;
		  passengerList[passengerNumber - 1].seat = -3;
		  printf("Economy Class is currently full. Please find the next available flight!\n");
		}
		break;
	      case 'N':
		passengerList[passengerNumber - 1].row = -3;
		passengerList[passengerNumber - 1].seat = -3;
		printf("Please find the next flight available. Have a great day!\n");
		break;
	      }
	    }
	    break;
	    
	   case 'E':
	    if(isFull(seatsArray, classSelectionInput) == 0){
	      choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
	      printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
	      scanf(" %c", &input);
	      c = input;
	      int counter = 0;
	      if(c == 'Y'){
		passengerList[passengerNumber - 1].row = r + 1;
		passengerList[passengerNumber - 1].seat = s + 1;
		seatsArray[r][s] = 1;
		blockingSeats(seatsArray, r, s);
	      }
	      else{
		while((counter < 3) && (c != 'Y')){
		  counter++;
		  if(counter == 3){
		    passengerList[passengerNumber - 1].row = -3;
		    passengerList[passengerNumber - 1].seat = -3;
		    printf("Unfortunately, you will have to board the next flight\n");
		  }
		  else{
		    choosingSeat(7, 13, passengerNumber, seatsArray, &r, &s);
		    printf("Is Row: %d, Seat: %d ok? 'Y' or 'N'\n", r + 1, s + 1);
		    scanf(" %c", &input);
		    c = input;
		    if(c == 'Y'){
		      passengerList[passengerNumber - 1].row = r + 1;
		      passengerList[passengerNumber - 1].seat = s + 1;
		      seatsArray[r][s] = 1;
		      blockingSeats(seatsArray, r, s);
		    }
		  }
		}
	      }
	    }
	    else if(isFull(seatsArray, classSelectionInput) == 1){
	      passengerList[passengerNumber - 1].row = -3;
	      passengerList[passengerNumber - 1].seat = -3;
	      printf("Economy Class is currently full. Please find the next available flight!\n");
	    }
	  break;
	  }	  
	case 'B': // Selects the seat at random for the passenger
	  if(seatSelectionInput == 'B'){
	    randomRow = rand() % 20;
	    randomSeat = rand() % 7;
	    while(seatsArray[randomRow][randomSeat] == 1 || seatsArray[randomRow][randomSeat] == -1){
	      randomRow = rand() % 20;
	      randomSeat = rand() % 7;
	    }
	    passengerList[passengerNumber - 1].row = randomRow + 1;
	    passengerList[passengerNumber - 1].seat = randomSeat + 1;
	    seatsArray[randomRow][randomSeat] = 1;
	    blockingSeats(seatsArray, randomRow, randomSeat);
	    break;
	  }
	}
	break;
      case 'B':
	printf("Please Select a Row from 1 - 20: ");
	  scanf(" %d", &randomRow);
	  printf("please Select a Seat from 1 - 7: ");
	  scanf(" %d", &randomSeat);
	  if(seatsArray[randomRow - 1][randomSeat - 1] == 0){ // if seat value equals 0, seat is unassigned
	    printf("Seat Unassigned - No Boarding pass available. Please Try Again!\n");
	    printf("\n");
	  }
	  if(seatsArray[randomRow - 1][randomSeat - 1] == -1){ // seat value equals -1, then it's blocked
            printf("Seat Blocked due to COVID Regulations - No Boarding pass available. Please Try Again!\n");
	    printf("\n");
	  }
	  else{
	    for(int i = 0; i < 25; i++){ // checks for passenger info is equal to row and seat inputted
	      if(passengerList[i].row == randomRow && passengerList[i].seat == randomSeat){
		if(randomRow >= 1){ // returns Class Level depending on seat given
		  strncpy(class, "First Class", 20);
		}
		else if(randomRow >= 4){
		  strncpy(class, "Business Class", 20);
		}
		else{
		  strncpy(class, "Economy Class", 20);
		}
		printf("Boarding Pass - Name: %s %s, Row: %d, Seat: %d, Level: %s\n", passengerList[i].firstName, passengerList[i].lastName, passengerList[i].row, passengerList[i].seat, class); // prints the boarding pass of the seat after finding the correct passenger in the List
		printf("\n");
	      }
	    }
	  }
	break;
      case 'C':
	printf("\n");
	printf("Below is the plane's seating chart\n"); // prints info
	printf("Reminder: 0 = Open/Available  1 = Unavailable/Taken  -1 = Blocked/Unavailable\n");
	printSeatingChart(seatsArray, letters, numbers); // prints seating Chart (make sure to have window open fully)
	printf("\n");
	break;
      case 'D': // Prints Manifest of passengers
	printf("Please choose 'S' for Seating Manifest or 'A' for Alphabetical Manifest\n");
	scanf(" %c", &input);
	switch(input){
	case 'S':
	  sortingSeats(); // prints based on sorted seating
	  break;
	case 'A':
	  sortingAlphabetically(); // prints based on sorted name Alphabetically
	  break;
	}
	break;
      case 'Q':
	printf("You Have Quit The Program\n");
	break;
      }
    }
    else{ // repeats if non-valid option
      printf("Please choose a valid option!\n");
    }
  }
}

void choosingSeat(int i, int j, int pNumber, int arr[row][seats], int *initRow, int *initSeat){ // inputs i and j are parameters to find the seats of a certain level.
  // initRow and initSeat is used later back in the boardingProgram() to be used 
  int randomRow;
  int randomSeat;
  randomRow = i + rand() % j; // takes i and j to set parameters to find seats of the needed class
  if(passengerList[pNumber-1].pref == 'W'){ //below all checks preference of passenger and gives them an appropriate seat based on it
    while((randomSeat > 0) && (randomSeat < 6)){
      randomSeat = rand() % 7;
    }
  }
  else if(passengerList[pNumber-1].pref == 'A'){
    while((randomSeat != 1) && (randomSeat != 2) && (randomSeat != 4) && (randomSeat != 5)){
      randomSeat = rand() % 7;
    }
  }
  else{
    randomSeat = rand() % 7;
  }
  // below checks if the seat is taken or blocked and if it is, it will continuously loop until it finds an open seat
  while(arr[randomRow][randomSeat] == 1 || arr[randomRow][randomSeat] == -1){ // inside for loop is a repeat of above
    randomRow = i + rand() % j;
    if(passengerList[pNumber - 1].pref == 'W'){
      while((randomSeat > 0) && (randomSeat < 6)){
	randomSeat = rand() % 7;
      }
    }
    else if(passengerList[pNumber - 1].pref == 'A'){
      while((randomSeat != 1) && (randomSeat != 2) && (randomSeat != 4) && (randomSeat != 5)){
	randomSeat = rand() % 7;
      }
    }
    else{
      randomSeat = rand() % 7;
    }
  }
  // below are the parameters given that is set to a random row and seat that is open, later to be used back in the program method
  *initRow = randomRow;
  *initSeat = randomSeat;
}

void sortingSeats(){
  struct passengerInfo temp; 
  int counter;
  for(int i = 0; i < TOTALPASSENGERS; i++){ // this for loop counts how many passengers has their seats chosen already 
    if((passengerList[i].row != -1) && (passengerList[i].row != 3)){
      counter++;
    }
  }
  struct passengerInfo tempList[counter]; // creates a list of the size of how many seats have been chosen
  int tempListSize = counter; // copies old counter
  counter = 0; // resets counter
  for(int i = 0; i < TOTALPASSENGERS; i++){ // resetted counter is used to iterate through the temp list and add passengers
    if((passengerList[i].row != -1) && (passengerList[i].row != -3)){
      tempList[counter] = passengerList[i];
      counter++;
    }
  }
  for(int i = 0; i < tempListSize; i++){ // sorts the tempList in correct seat order
    for(int j = i + 1; j < tempListSize; j++){
      if(tempList[i].row > tempList[j].row){ // checks row
	temp = tempList[i];
	tempList[i] = tempList[j];
	tempList[j] = temp;
      }
      else if(tempList[i].row == tempList[j].row){ // if rows are equal, checks seat
	if(tempList[i].seat > tempList[j].seat){
	  temp = tempList[i];
	  tempList[i] = tempList[j];
	  tempList[j] = temp;
	}
      }
    }
  }
  struct passengerInfo newList[TOTALPASSENGERS]; // new list to contain all passengers in proper order
  for(int i = 0; i < tempListSize; i++){ // adds all seats that were chosen and sorted into the newList
    newList[i] = tempList[i];
  }
  for(int i = 0; i < TOTALPASSENGERS; i++){ // picks out and adds seats with -1 as row and seat and then adds seats with -3 as row and seat
    if(passengerList[i].row == -1){
      newList[tempListSize] = passengerList[i];
      tempListSize++;
    }
  }
  for(int i = 0; i < TOTALPASSENGERS; i++){
    if(passengerList[i].row == -3){
      newList[tempListSize] = passengerList[i];
      tempListSize++;
    }
  }
  // Below prints out the manifest
  printf("\n");
  printf("Rows and Seats that = -1 indicates Passenger Seat hasn't been chosen\n");
  printf("Rows and Seats that = -3 indicates Passenger will not be boarding this flight\n");
  printf("\n");
  for(int i = 0; i < TOTALPASSENGERS; i++){
    printf("Name: %s %s, Row: %d, Seat: %d\n", newList[i].firstName, newList[i].lastName, newList[i].row, newList[i].seat);
  }
  printf("\n");
}

void sortingAlphabetically(){
  struct passengerInfo temp;
  struct passengerInfo tempList[TOTALPASSENGERS]; 
  for(int i = 0; i < TOTALPASSENGERS; i++){ // copies all passengers from original list into a tempList
    tempList[i] = passengerList[i];
  }
  for(int i = 0; i < TOTALPASSENGERS; i++){ // sorts based on alphabetical order
    for(int j = i + 1; j < TOTALPASSENGERS; j++){
      if(strcmp(tempList[i].firstName, tempList[j].firstName) > 0){
	temp = tempList[i];
	tempList[i] = tempList[j];
	tempList[j] = temp;
      }
    }
  }
  // prints Manifest 1 by 1 after being sorted
  printf("Rows and Seats that = -1 means Passenger Seat hasn't been chosen\n");
  for(int i = 0; i < TOTALPASSENGERS; i++){
    printf("Name: %s %s, Row: %d, Seat: %d\n", tempList[i].firstName, tempList[i].lastName, tempList[i].row, tempList[i].seat);
  }
  printf("\n");
}

void printSeatingChart(int seatsArray[row][seats], char letters[], char numbers[]){ // prints seating chart
  for(int i = 0; i < row; i++){
    for(int j = 0; j < seats; j++){
      printf("\t%c%c \t- \t%d ", letters[i], numbers[j], seatsArray[i][j]);
      if(j == seats - 1){ // if it reaches 6, then it will move to a new row/line
	printf("\n");
      }
    }
  }
}

void blockingSeats(int seatsArray[row][seats], int blockingRow, int blockingSeat){
  if(blockingSeat == 0 || blockingSeat == 2 || blockingSeat == 5){ // blocks seat next to seat 1, 3, and 6
    seatsArray[blockingRow][blockingSeat + 1] = -1; // seat 3 will not block 2 and 6 will not block 5 as there is buffer for aisle between
  }
  else if(blockingSeat == 1 || blockingSeat == 4 || blockingSeat == 6){ // blocks seat next to 2, 5, and 7
    seatsArray[blockingRow][blockingSeat - 1] = -1; // will not block 3 or 6 since there is buffer for aisle
  }
  else if(blockingSeat == 3){ // blocks seats next to seat 4
    seatsArray[blockingRow][blockingSeat - 1] = -1;
    seatsArray[blockingRow][blockingSeat + 1] = -1;
  }
}

void unblockSeats(int arr[row][seats], int rows, int seat){ // essentially the same as blocking seats but just unblocks it and also changes selected seat to be available again
  if(seat == 0 || seat == 2 || seat == 5){
    arr[rows][seat] = 0;
    arr[rows][seat + 1] = 0;
  }
  else if(seat == 1 || seat == 4 || seat == 6){
    arr[rows][seat] = 0;
    arr[rows][seat - 1] = 0;
  }
  else if(seat == 3){
    arr[rows][seat] = 0;
    arr[rows][seat - 1] = 0;
    arr[rows][seat + 1] = 0;
  }
}

int isFull(int arr[row][seats], char class){ // checks if the given class is full
  int counter = 0;
  if(class == 'F'){
    counter = 0;
    for(int i = 0; i < 3; i++){
      for(int j = 0; j < 7; j++){
	if(arr[i][j] == 1){counter++;}
	else if(arr[i][j] == -1){counter++;}
      }
    }
    if(counter == 21){return 1;} // total seats is 21
    else{
      return 0;}
  }
  else if(class == 'B'){
    counter = 0;
    for(int i = 3; i < 7; i++){
      for(int j = 0; j < 7; j++){
	if(arr[i][j] == 1){counter++;}
	else if(arr[i][j] == -1){counter++;}
      }
    }
    if(counter == 28){return 1;} // total seats in Business Class is 28
    else{return 0;}
  }
  else{
    counter == 0;
    for(int i = 7; i < 20; i++){
      for(int j = 0; j < 7; j++){
	if(arr[i][j] == 1){counter++;}
	else if(arr[i][j] == -1){counter++;}
      }
    }
    if(counter == 91){return 1;} // total seats in Economy class is 91
    else{return 0;}
  }
  return 0;
}
