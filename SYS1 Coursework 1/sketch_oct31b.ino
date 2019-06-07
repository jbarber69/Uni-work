#include <Servo.h>

// assign LEDs, buttons and speaker to pins
int greenLedPin[] = {4, 5, 6};
int redLedPin[] = {10, 11, 12};
int playerOneButton = 2;
int playerTwoButton = 3;
int difficultyButton = 7;
int whiteLED = 9;
int speakerPin = 8;

// declare variables
int delayTimeHigh[] = {500, 250, 100}; // time delay between lights on/off
int delayTimeLow = 1000; // time delay between lights on/off
int score1 = 0; //p1 score
int score2 = 0; //p2 score
int randNumber1; //pins for green LEDS
int randNumber2; //pins for red LEDS
int whiteLEDOn; //whether white LED is on or off
int buttonState; //holds state of p1 button
int ledState; //holds state of current green LED that is on
int buttonState2; //holds state of p2 button
int ledState2; //holds state of current red LED that is on
int difficulty = 0; //value for difficulty
int t; // timer
Servo myServo; //create servo object
int servoPin = 13; // pin of the servo
int limits[2] = {10, 165}; // limits of rotation

//setup interrupt, button input and LED outputs
void setup() {
  for (int i = 0; i < 3; i++) {
    pinMode(greenLedPin[i], OUTPUT);
  }
  for (int i = 0; i < 3; i++) {
    pinMode(redLedPin[i], OUTPUT);
  }
  pinMode(playerOneButton, INPUT);
  pinMode(playerTwoButton, INPUT);
  pinMode(difficultyButton, INPUT);
  pinMode(whiteLED, OUTPUT);
  pinMode(speakerPin, OUTPUT);
  Serial.begin(9600);
  pinMode(1, OUTPUT);
  myServo.attach(servoPin); // set up servo
  myServo.write((limits[1] - limits[0]) / 2); //start servo between the two limits
}

//from ardx.org/circ06
//plays a sound when called
void playTone(int tone, int duration) {
  for (long i = 0; i < duration * 1000L; i += tone * 2) {
    digitalWrite(speakerPin, HIGH);
    delayMicroseconds(duration);
    digitalWrite(speakerPin, LOW);
    delayMicroseconds(duration);
  }
}

void playerOneInput(int rled, int t) {

  // read the state of the button
  int ledPin = rled;
  int timer = t / 2;
  ledState = digitalRead(ledPin);
  // check if the pushbutton is pressed
  if (ledState == 1) {
    int control = 0;
    while (control == 0) {
      delay(timer);
      buttonState = digitalRead(playerOneButton);
      delay(timer);
      if (buttonState == 0) {
        // turn LED on
        digitalWrite(whiteLED, HIGH);
        //increment score
        score1 ++;
        //output a sound
        playTone(10, 25);
        control = 1;
      }
      else {
        control = 1;
      }
    }
  }
  digitalWrite(whiteLED, LOW);
}


void playerTwoInput(int gled, int t) {
  // read the state of the button
  int ledPin = gled;
  int timer = t / 2;
  ledState = digitalRead(ledPin);
  // check if the pushbutton is pressed
  if (ledState == 1) {
    int control = 0;
    while (control == 0) {
      delay(timer);
      buttonState = digitalRead(playerTwoButton);
      delay(timer);
      if (buttonState == 0) {
        // turn LED on
        digitalWrite(whiteLED, HIGH);
        //increment score
        score2 ++;
        //output a sound
        playTone(1, 10);
        control = 1;
      }
      else {
        control = 1;
      }
    }
  }
  digitalWrite(whiteLED, LOW);
}

//function to change difficulty
void changeDifficulty() {
  buttonState = digitalRead(difficultyButton);
  if (buttonState == LOW && difficulty < 2) {
    difficulty++;
    Serial.println(difficulty);
  }
}

//run main program loop
void loop() {

  changeDifficulty();
  int t = delayTimeHigh[difficulty];
  randNumber1 = random(3); // select a random number
  randNumber2 = random(3);
  digitalWrite(redLedPin[randNumber2], HIGH); // light the LED with this number
  playerTwoInput(randNumber2, t);
  digitalWrite(redLedPin[randNumber2], LOW);
  delay(delayTimeLow);
  //turn off white LED
  delay(100);
  digitalWrite(whiteLED, LOW);
  //check to see whether max score has been reached
  // if true, light all LEDS and stop program
  if (score1 == 10) {
    digitalWrite(4, HIGH);
    digitalWrite(5, HIGH);
    digitalWrite(6, HIGH);
    Serial.println("1");
    exit(0);
  }

  digitalWrite(greenLedPin[randNumber1], HIGH); // light the LED with this number
  playerOneInput(randNumber1, t);
  digitalWrite(greenLedPin[randNumber1], LOW);
  delay(delayTimeLow);
  //turn off white LED
  delay(100);
  digitalWrite(whiteLED, LOW);
  //check to see whether max score has been reached
  // if true, light all LEDS and stop program
  if (score2 == 10) {
    digitalWrite(10, HIGH);
    digitalWrite(11, HIGH);
    digitalWrite(12, HIGH);
    Serial.println("2");
    exit(0);
  }

  if (score1 > score2) {
    myServo.write(limits[0]);
  }
  else if (score2 > score1) {
    myServo.write(limits[1]);
  }
}





